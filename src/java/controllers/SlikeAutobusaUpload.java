/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import beans.Autobusi;
import beans.Prevoznici;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import org.apache.commons.io.FilenameUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import util.HibernateUtil;

/**
 *
 * @author Jovana
 */
@ManagedBean
@ViewScoped
public class SlikeAutobusaUpload {

    private List<UploadedFile> files;
    @ManagedProperty(value = "#{dodajBus}")
    private DodajBus dodajBusBean;

    @PostConstruct
    public void postConstruct() {
        files = new ArrayList<UploadedFile>();
    }

    public DodajBus getDodajBusBean() {
        return dodajBusBean;
    }

    public void setDodajBusBean(DodajBus dodajBusBean) {
        this.dodajBusBean = dodajBusBean;
    }

    public List<UploadedFile> getFiles() {
        return files;
    }

    public void setFiles(List<UploadedFile> files) {
        this.files = files;
    }

    public void handleFileUpload(FileUploadEvent event) {
        files.add(event.getFile());
    }

    public void upload() {
        if (files == null || files.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Lista fajlova je prazna!",
                                "Lista fajlova je prazna!"));
            return;
        }
        SessionFactory sessionFactory;
        Transaction tx = null;
        try {
            for (UploadedFile file : files) {
                InputStream input = file.getInputstream();
                ServletContext context = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
                String pathString = context.getRealPath("/");
                pathString += "..\\..\\web\\resources\\photos";
                Path folder = Paths.get(pathString);
                String filename = FilenameUtils.getBaseName(file.getFileName());
                String extension = FilenameUtils.getExtension(file.getFileName());
                if (!(extension.equals("jpg") || extension.equals("png") || extension.equals("gif"))) {
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Podrzani formati su: JPG, PNG, GIF!",
                                    "Podrzani formati su: JPG, PNG, GIF!"));
                    return;
                }
                Path filePath = Files.createTempFile(folder, filename + "-", "." + extension);
                Files.copy(input, filePath, StandardCopyOption.REPLACE_EXISTING);
                int beginIndex = filePath.toString().lastIndexOf(92) + 1; //92 is the in representation of \
                String zaBazu = "./resources/photos/" + filePath.toString().substring(beginIndex);
                sessionFactory = HibernateUtil.getSessionFactory();
                Session session = sessionFactory.openSession();
                Autobusi bus = (Autobusi) session.load(Autobusi.class, dodajBusBean.getBusId());
                tx = session.beginTransaction();
                if(bus.getSlike() == null || bus.getSlike().isEmpty()) {
                    bus.setSlike(zaBazu);
                }
                else {
                    String slike = bus.getSlike();
                    slike += ", " + zaBazu;
                    bus.setSlike(slike);
                }
                tx.commit();
                session.close();
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Slika" + file.getFileName() + " je dodata!",
                                "Slika" + file.getFileName() + " je dodata!"));
            }
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Doslo je do greske, pokusajte ponovo.",
                        "Doslo je do greske, pokusajte ponovo."));
    }
}
