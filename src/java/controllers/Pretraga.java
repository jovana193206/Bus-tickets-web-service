/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import beans.Medjugradske;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import util.HibernateUtil;

/**
 *
 * @author Jovana
 */
@ManagedBean
@SessionScoped
public class Pretraga {

    private String kriterijum;
    private String prevoznik;
    private Date vremeOd;
    private Date vremeDo;
    private String polaziste;
    private String odrediste;
    private Date datumPolaska;
    private List<Medjugradske> medjugradske;
    private List<Medjugradske> najskorije;

    public List<Medjugradske> getNajskorije() {
        return najskorije;
    }

    public void setNajskorije(List<Medjugradske> najskorije) {
        this.najskorije = najskorije;
    }

    public List<Medjugradske> getMedjugradske() {
        return medjugradske;
    }

    public void setMedjugradske(List<Medjugradske> medjugradske) {
        this.medjugradske = medjugradske;
    }

    public String getKriterijum() {
        return kriterijum;
    }

    public void setKriterijum(String kriterijum) {
        this.kriterijum = kriterijum;
    }

    public String getPrevoznik() {
        return prevoznik;
    }

    public void setPrevoznik(String prevoznik) {
        this.prevoznik = prevoznik;
    }

    public Date getVremeOd() {
        return vremeOd;
    }

    public void setVremeOd(Date vremeOd) {
        this.vremeOd = vremeOd;
    }

    public Date getVremeDo() {
        return vremeDo;
    }

    public void setVremeDo(Date vremeDo) {
        this.vremeDo = vremeDo;
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

    public Date getDatumPolaska() {
        return datumPolaska;
    }

    public void setDatumPolaska(Date datumPolaska) {
        this.datumPolaska = datumPolaska;
    }

    public void polazisteIodrediste(Medjugradske m) {
        boolean pol = false, odr = false;
        if (polaziste.equals(m.getPolaziste())) {
            pol = true;
        }
        if (odrediste.equals(m.getOdrediste())) {
            odr = true;
        }
        if (pol && odr) {
            medjugradske.add(m);
            return;
        }
        String[] medjustanice = m.getMedjustanice().split("-");
        for (String stanica : medjustanice) {
            if (!pol && polaziste.equals(stanica)) {
                pol = true;
            }
            if (pol && !odr && odrediste.equals(stanica)) {
                odr = true;
            }
            if (pol && odr) {
                break;
            }
        }
        if (pol && odr) {
            medjugradske.add(m);
        }
    }

    public void zadatoPolaziste(Medjugradske m) {
        if (polaziste.equals(m.getPolaziste())) {
            medjugradske.add(m);
            return;
        }
        String[] medjustanice = m.getMedjustanice().split("-");
        for (String stanica : medjustanice) {
            if (polaziste.equals(stanica)) {
                medjugradske.add(m);
                break;
            }
        }
    }

    public void zadatoOdrediste(Medjugradske m) {
        if (odrediste.equals(m.getOdrediste())) {
            medjugradske.add(m);
            return;
        }
        String[] medjustanice = m.getMedjustanice().split("-");
        for (String stanica : medjustanice) {
            if (odrediste.equals(stanica)) {
                medjugradske.add(m);
                break;
            }
        }
    }

    public String pretrazi() {
        if (polaziste.isEmpty() && odrediste.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Morate popuniti bar jedno od polja: polaziste, odrediste",
                            "Morate popuniti bar jedno od polja: polaziste, odrediste"));
            return "";
        }
        SessionFactory sessionFactory;
        Transaction tx = null;
        try {
            medjugradske = new ArrayList<Medjugradske>();
            if (kriterijum.equals("polaziste") || kriterijum.equals("odrediste") || kriterijum.equals("prevoznik")) {
                List<Medjugradske> pom;
                sessionFactory = HibernateUtil.getSessionFactory();
                Session session = sessionFactory.openSession();
                tx = session.beginTransaction();
                Query query = session.createQuery("from Medjugradske");
                pom = query.list();
                for (Medjugradske m : pom) {
                    Hibernate.initialize(m.getPrevoznici());
                }
                tx.commit();
                session.close();
                tx = null;
                if (kriterijum.equals("polaziste")) {
                    if (polaziste.isEmpty()) {
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Morate popuniti polje polaziste!",
                                        "Morate popuniti polje polaziste!"));
                        return "";
                    }
                    for (Medjugradske m : pom) {
                        Calendar cal = Calendar.getInstance(); // locale-specific
                        cal.setTime(m.getPolazak());
                        cal.set(Calendar.HOUR_OF_DAY, 0);
                        cal.set(Calendar.MINUTE, 0);
                        cal.set(Calendar.SECOND, 0);
                        cal.set(Calendar.MILLISECOND, 0);
                        Date polazak = new Date(cal.getTimeInMillis());
                        if (!polazak.equals(datumPolaska)) {
                            continue;
                        }
                        if (odrediste.isEmpty()) {
                            zadatoPolaziste(m);
                        } else {
                            polazisteIodrediste(m);
                        }
                    }
                    return "gostRezultati";
                }
                if (kriterijum.equals("odrediste")) {
                    if (odrediste.isEmpty()) {
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Morate popuniti polje odrediste!",
                                        "Morate popuniti polje odrediste!"));
                        return "";
                    }
                    for (Medjugradske m : pom) {
                        Calendar cal = Calendar.getInstance(); // locale-specific
                        cal.setTime(m.getPolazak());
                        cal.set(Calendar.HOUR_OF_DAY, 0);
                        cal.set(Calendar.MINUTE, 0);
                        cal.set(Calendar.SECOND, 0);
                        cal.set(Calendar.MILLISECOND, 0);
                        Date polazak = new Date(cal.getTimeInMillis());
                        if (!polazak.equals(datumPolaska)) {
                            continue;
                        }
                        if (polaziste.isEmpty()) {
                            zadatoOdrediste(m);
                        } else {
                            polazisteIodrediste(m);
                        }
                    }
                    return "gostRezultati";
                }
                if (kriterijum.equals("prevoznik")) {
                    if (prevoznik.isEmpty()) {
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Morate popuniti polje prevoznik!",
                                        "Morate popuniti polje prevoznik!"));
                        return "";
                    }
                    for (Medjugradske m : pom) {
                        Calendar cal = Calendar.getInstance(); // locale-specific
                        cal.setTime(m.getPolazak());
                        cal.set(Calendar.HOUR_OF_DAY, 0);
                        cal.set(Calendar.MINUTE, 0);
                        cal.set(Calendar.SECOND, 0);
                        cal.set(Calendar.MILLISECOND, 0);
                        Date polazak = new Date(cal.getTimeInMillis());
                        if (!polazak.equals(datumPolaska)) {
                            continue;
                        }
                        if (!prevoznik.equals(m.getPrevoznici().getNaziv())) {
                            continue;
                        }
                        if (polaziste.isEmpty()) {
                            zadatoOdrediste(m);
                            continue;
                        }
                        if (odrediste.isEmpty()) {
                            zadatoPolaziste(m);
                            continue;
                        }
                        polazisteIodrediste(m);
                    }
                    return "gostRezultati";
                }
            }
            sessionFactory = HibernateUtil.getSessionFactory();
            Session session = sessionFactory.openSession();
            tx = session.beginTransaction();
            Query query = session.createQuery("from Medjugradske where polazak between :od and :do");
            query.setParameter("od", new Timestamp(vremeOd.getTime()));
            query.setParameter("do", new Timestamp(vremeDo.getTime()));
            List<Medjugradske> pom = query.list();
            for (Medjugradske m : pom) {
                Hibernate.initialize(m.getPrevoznici());
            }
            tx.commit();
            session.close();
            for (Medjugradske m : pom) {
                if (polaziste.isEmpty()) {
                    zadatoOdrediste(m);
                    continue;
                }
                if (odrediste.isEmpty()) {
                    zadatoPolaziste(m);
                    continue;
                }
                polazisteIodrediste(m);
            }
            return "gostRezultati";
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        }
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Desila se greska, pokusajte ponovo!",
                        "Desila se greska, pokusajte ponovo!"));
        return "";
    }

    public String najskorije() {
        SessionFactory sessionFactory;
        Transaction tx = null;
        try {
            sessionFactory = HibernateUtil.getSessionFactory();
            Session session = sessionFactory.openSession();
            tx = session.beginTransaction();
            Criteria criteria = session.createCriteria(Medjugradske.class);
            criteria.addOrder(Order.asc("polazak")).add(Restrictions.gt("polazak", new Date()));
            criteria.setMaxResults(10);
            najskorije = criteria.list();
            for (Medjugradske m : najskorije) {
                Hibernate.initialize(m.getPrevoznici());
            }
            tx.commit();
            session.close();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        }

        return "gostLista";
    }
}
