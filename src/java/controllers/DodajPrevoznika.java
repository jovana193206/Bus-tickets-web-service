/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import beans.Prevoznici;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import org.apache.commons.io.FilenameUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.primefaces.model.UploadedFile;
import util.HibernateUtil;

/**
 *
 * @author Jovana
 */
@ManagedBean
@SessionScoped
public class DodajPrevoznika {

    private Integer prevoznikId;
    private String naziv;
    private String prevoznikAdresa;
    private String prevoznikTelefon;
    private UploadedFile file;

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getPrevoznikAdresa() {
        return prevoznikAdresa;
    }

    public void setPrevoznikAdresa(String prevoznikAdresa) {
        this.prevoznikAdresa = prevoznikAdresa;
    }

    public String getPrevoznikTelefon() {
        return prevoznikTelefon;
    }

    public void setPrevoznikTelefon(String prevoznikTelefon) {
        this.prevoznikTelefon = prevoznikTelefon;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public void upload() {
        if (file == null) {
            return;
        }
        SessionFactory sessionFactory;
        Transaction tx = null;
        try {
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
            Prevoznici prevoznik = (Prevoznici) session.load(Prevoznici.class, prevoznikId);
            tx = session.beginTransaction();
            prevoznik.setLogo(zaBazu);
            tx.commit();
            session.close();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Logo je dodat!",
                            "Logo je dodat!"));
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Doslo je do greske, pokusajte ponovo.",
                        "Doslo je do greske, pokusajte ponovo."));
    }

    public void dodaj() {
        SessionFactory sessionFactory;
        Transaction tx = null;
        try {
            sessionFactory = HibernateUtil.getSessionFactory();
            Session session = sessionFactory.openSession();
            tx = session.beginTransaction();
            Query query = session.createQuery("from Prevoznici where naziv = :prevoznik");
            query.setParameter("prevoznik", naziv);
            Prevoznici p = (Prevoznici) query.uniqueResult();
            tx.commit();
            tx = null;
            if (p != null) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Prevoznik sa zadatim nazivom vec postoji!",
                                "Prevoznik sa zadatim nazivom vec postoji!"));
            }
            tx = session.beginTransaction();
            Prevoznici novi = new Prevoznici(naziv, prevoznikAdresa, prevoznikTelefon);
            session.save(novi);
            tx.commit();
            session.close();
            prevoznikId = novi.getId();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Prevoznik je dodat u bazu!",
                            "Prevoznik je dodat u bazu!"));
            return;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        }
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Doslo je do greske, pokusajte ponovo.",
                        "Doslo je do greske, pokusajte ponovo."));
    }

}
