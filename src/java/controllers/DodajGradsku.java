/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import beans.Gradske;
import beans.Otkazane;
import beans.Redvoznje;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import util.HibernateUtil;

/**
 *
 * @author Jovana
 */
@ManagedBean
@SessionScoped
public class DodajGradsku {

    private String brLinijeStr;
    private int brLinije;
    private String polaziste;
    private String odrediste;
    private String medjustanice;
    private String polaziste1;
    private String odrediste1;
    private String polasci1;
    private String polaziste2;
    private String odrediste2;
    private String polasci2;
    private Date otkazanaOd;
    private Date otkazanaDo;

    public Date getOtkazanaOd() {
        return otkazanaOd;
    }

    public void setOtkazanaOd(Date otkazanaOd) {
        this.otkazanaOd = otkazanaOd;
    }

    public Date getOtkazanaDo() {
        return otkazanaDo;
    }

    public void setOtkazanaDo(Date otkazanaDo) {
        this.otkazanaDo = otkazanaDo;
    }

    public String getBrLinijeStr() {
        return brLinijeStr;
    }

    public void setBrLinijeStr(String brLinijeStr) {
        this.brLinijeStr = brLinijeStr;
    }

    public int getBrLinije() {
        return brLinije;
    }

    public void setBrLinije(int brLinije) {
        this.brLinije = brLinije;
    }

    public String getPolaziste() {
        return polaziste;
    }

    public void setPolaziste(String polaziste) {
        this.polaziste = polaziste;
    }

    public String getOdrediste() {
        return odrediste;
    }

    public void setOdrediste(String odrediste) {
        this.odrediste = odrediste;
    }

    public String getMedjustanice() {
        return medjustanice;
    }

    public void setMedjustanice(String medjustanice) {
        this.medjustanice = medjustanice;
    }

    public String getPolaziste1() {
        return polaziste1;
    }

    public void setPolaziste1(String polaziste1) {
        this.polaziste1 = polaziste1;
    }

    public String getOdrediste1() {
        return odrediste1;
    }

    public void setOdrediste1(String odrediste1) {
        this.odrediste1 = odrediste1;
    }

    public String getPolasci1() {
        return polasci1;
    }

    public void setPolasci1(String polasci1) {
        this.polasci1 = polasci1;
    }

    public String getPolaziste2() {
        return polaziste2;
    }

    public void setPolaziste2(String polaziste2) {
        this.polaziste2 = polaziste2;
    }

    public String getOdrediste2() {
        return odrediste2;
    }

    public void setOdrediste2(String odrediste2) {
        this.odrediste2 = odrediste2;
    }

    public String getPolasci2() {
        return polasci2;
    }

    public void setPolasci2(String polasci2) {
        this.polasci2 = polasci2;
    }

    public void dodajLiniju() {
        SessionFactory sessionFactory;
        Transaction tx = null;
        try {
            brLinije = Integer.parseInt(brLinijeStr);
            sessionFactory = HibernateUtil.getSessionFactory();
            Session session = sessionFactory.openSession();
            tx = session.beginTransaction();
            Query query = session.createQuery("from Gradske where brLinije = :linija ");
            query.setParameter("linija", brLinije);
            Gradske postoji = (Gradske) query.uniqueResult();
            tx.commit();
            if (postoji != null) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Gradska linija sa zadatim brojem vec postoji!",
                                "Gradska linija sa zadatim brojem vec postoji!"));
                return;
            }
            tx = null;
            Gradske nova = new Gradske(brLinije, polaziste, odrediste, medjustanice);
            tx = session.beginTransaction();
            session.save(nova);
            tx.commit();
            session.close();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Nova gradska linija je dodata",
                            "Nova gradska linija je dodata"));
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

    public void dodajRedVoznje() {
        SessionFactory sessionFactory;
        Transaction tx = null;
        try {
            brLinije = Integer.parseInt(brLinijeStr);
            sessionFactory = HibernateUtil.getSessionFactory();
            Session session = sessionFactory.openSession();
            tx = session.beginTransaction();
            Query query = session.createQuery("from Redvoznje where brLinije = :linija ");
            query.setParameter("linija", brLinije);
            List<Redvoznje> postoji = query.list();
            tx.commit();
            if (postoji != null && !postoji.isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Red voznje za zadatu liniju vec postoji!",
                                "Red voznje za zadatu liniju vec postoji!"));
                return;
            }
            tx = null;
            tx = session.beginTransaction();
            query = session.createQuery("from Gradske where brLinije = :linija ");
            query.setParameter("linija", brLinije);
            Gradske gradska = (Gradske) query.uniqueResult();
            Redvoznje smer1 = new Redvoznje(gradska, polaziste1, odrediste1, polasci1);
            Redvoznje smer2 = new Redvoznje(gradska, polaziste2, odrediste2, polasci2);
            session.save(smer1);
            session.save(smer2);
            tx.commit();
            session.close();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Red voznje je dodat",
                            "Red voznje je dodat"));
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

    public void otkaziLiniju() {
        SessionFactory sessionFactory;
        Transaction tx = null;
        try {
            brLinije = Integer.parseInt(brLinijeStr);
            sessionFactory = HibernateUtil.getSessionFactory();
            Session session = sessionFactory.openSession();
            Gradske linija = (Gradske) session.load(Gradske.class, brLinije);
            Otkazane o = new Otkazane(linija, otkazanaOd, otkazanaDo);
            tx = session.beginTransaction();
            session.save(o);
            tx.commit();
            session.close();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Linija je otkazana",
                            "Linija je otkazana"));
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
