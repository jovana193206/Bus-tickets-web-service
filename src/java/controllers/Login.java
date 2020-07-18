/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import beans.Gradske;
import beans.Korisnik;
import beans.Markice;
import beans.Otkazane;
import beans.Rezervacija;
import beans.Vozaci;
import beans.Vozigradsku;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.hibernate.Hibernate;
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
public class Login {

    private String user;
    private String pass;
    private List<Gradske> sveGradske;
    private List<Vozaci> slobodniVozaci;
    private String brLinijeStr;
    private int brLinije;

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

    public List<Vozaci> getSlobodniVozaci() {
        return slobodniVozaci;
    }

    public void setSlobodniVozaci(List<Vozaci> slobodniVozaci) {
        this.slobodniVozaci = slobodniVozaci;
    }

    public List<Gradske> getSveGradske() {
        return sveGradske;
    }

    public void setSveGradske(List<Gradske> sveGradske) {
        this.sveGradske = sveGradske;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String login() {
        SessionFactory sessionFactory;
        Transaction tx = null;
        try {
            sessionFactory = HibernateUtil.getSessionFactory();
            Session session = sessionFactory.openSession();
            tx = session.beginTransaction();
            Query query = session.createQuery("from Korisnik where username = :user ");
            query.setParameter("user", user);
            Korisnik k = (Korisnik) query.uniqueResult();
            Hibernate.initialize(k.getRezervacijas());
            for (Object o : k.getRezervacijas()) {
                Rezervacija r = (Rezervacija) o;
                Hibernate.initialize(r.getMedjugradske());
            }
            Hibernate.initialize(k.getMarkices());
            tx.commit();
            session.close();
            if (k != null && k.getPassword().equals(pass) && k.getStatus() == 1) {
                FacesContext fc = FacesContext.getCurrentInstance();
                HttpSession hs = (HttpSession) fc.getExternalContext().getSession(false);
                hs.setAttribute("korisnik", k);
                if (k.getTip() == 0) {
                    session = sessionFactory.openSession();
                    tx = session.beginTransaction();
                    query = session.createQuery("from Vozaci where slobodan = 1");
                    slobodniVozaci = query.list();
                    tx.commit();
                    session.close();
                    return "admin";
                    /*try {
                        FacesContext.getCurrentInstance().getExternalContext().redirect("faces/admin.xhtml");
                    } catch (IOException ex) {
                        Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                    }*/
                } else {
                    for (Object r : k.getRezervacijas()) {
                        Rezervacija rez = (Rezervacija) r;
                        if (rez.getObavesti() == 1) {
                            if (rez.getOdobrena() == 1) {
                                session = sessionFactory.openSession();
                                tx = session.beginTransaction();
                                rez.setObavesti(0);
                                tx.commit();
                                session.close();
                                FacesContext.getCurrentInstance().addMessage(null,
                                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Vasa rezervacija za liniju " + rez.getMedjugradske().getId() + " je odobrena",
                                                "Vasa rezervacija za liniju " + rez.getMedjugradske().getId() + " je odobrena"));
                            } else {
                                session = sessionFactory.openSession();
                                tx = session.beginTransaction();
                                session.delete(rez);
                                tx.commit();
                                session.close();
                                FacesContext.getCurrentInstance().addMessage(null,
                                        new FacesMessage(FacesMessage.SEVERITY_WARN, "Vasa rezervacija za liniju " + rez.getMedjugradske() + " je odbijena",
                                                "Vasa rezervacija za liniju " + rez.getMedjugradske() + " je odbijena"));

                            }
                        }
                    }
                    for (Object o : k.getMarkices()) {
                        Markice m = (Markice) o;
                        if (m.getObavesti() == 1) {
                            if (m.getOdobrena() == 1) {
                                session = sessionFactory.openSession();
                                Markice mar = (Markice) session.load(Markice.class, m.getId());
                                tx = session.beginTransaction();
                                mar.setObavesti(0);
                                tx.commit();
                                session.close();
                                if (m.getGodisnja() == 1) {
                                    FacesContext.getCurrentInstance().addMessage(null,
                                            new FacesMessage(FacesMessage.SEVERITY_INFO, "Vas zahtev za izdavanje godisnje karte je odobren",
                                                    "Vas zahtev za izdavanje godisnje karte je odobren"));
                                } else {
                                    FacesContext.getCurrentInstance().addMessage(null,
                                            new FacesMessage(FacesMessage.SEVERITY_INFO, "Vas zahtev za izdavanje mesecne karte je odobren",
                                                    "Vas zahtev za izdavanje mesecne karte je odobren"));
                                }
                            } else {
                                session = sessionFactory.openSession();
                                if (m.getGodisnja() == 1) {
                                    FacesContext.getCurrentInstance().addMessage(null,
                                            new FacesMessage(FacesMessage.SEVERITY_WARN, "Vas zahtev za izdavanje godisnje karte je odbijen",
                                                    "Vas zahtev za izdavanje godisnje karte je odbijen"));
                                } else {
                                    FacesContext.getCurrentInstance().addMessage(null,
                                            new FacesMessage(FacesMessage.SEVERITY_WARN, "Vas zahtev za izdavanje mesecne karte je odbijen",
                                                    "Vas zahtev za izdavanje mesecne karte je odobren"));
                                }
                                tx = session.beginTransaction();
                                session.delete(m);
                                tx.commit();
                                session.close();
                            }
                        }
                    }
                    session = sessionFactory.openSession();
                    tx = session.beginTransaction();
                    query = session.createQuery("from Otkazane");
                    List<Otkazane> otkazane = query.list();
                    for (Otkazane o : otkazane) {
                        Hibernate.initialize(o.getGradske());
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_WARN, "Linija broj " + o.getGradske().getBrLinije()
                                        + "je otkazana od " + o.getPeriodOd() + " do " + o.getPeriodDo() + " !",
                                        "Linija broj " + o.getGradske().getBrLinije()
                                        + " je otkazana od " + o.getPeriodOd() + " do " + o.getPeriodDo() + " !"));
                    }
                    query = session.createQuery("from Gradske");
                    sveGradske = query.list();
                    for (Gradske g : sveGradske) {
                        Hibernate.initialize(g.getRedvoznjes());
                        Hibernate.initialize(g.getVozigradskus());
                    }
                    tx.commit();
                    session.close();
                    return "korisnik";
                }

            } else if (k == null || k.getStatus() == 0) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Korisnik sa unetim korisnickim imenom ne postoji!",
                                "Korisnik sa unetim korisnickim imenom ne postoji!"));
            } else if (!k.getPassword().equals(pass)) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Pogresna lozinka!",
                                "Pogresna lozinka!"));
            }
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        }
        return "";
    }

    public String korisnikGradske() {
        SessionFactory sessionFactory;
        Transaction tx = null;
        try {
            sessionFactory = HibernateUtil.getSessionFactory();
            Session session = sessionFactory.openSession();
            tx = session.beginTransaction();
            Query query = session.createQuery("from Gradske");
            sveGradske = query.list();
            for (Gradske g : sveGradske) {
                Hibernate.initialize(g.getRedvoznjes());
                Hibernate.initialize(g.getVozigradskus());
            }
            tx.commit();
            session.close();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        }
        return "korisnik";
    }

    public String adminGradske() {
        SessionFactory sessionFactory;
        Transaction tx = null;
        try {
            brLinije = Integer.parseInt(brLinijeStr);
            sessionFactory = HibernateUtil.getSessionFactory();
            Session session = sessionFactory.openSession();
            tx = session.beginTransaction();
            Query query = session.createQuery("from Vozaci where slobodan = 1");
            slobodniVozaci = query.list();
            tx.commit();
            session.close();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        }
        return "admin";
    }

    public void dodeliVozaca(Vozaci vozac) {
        SessionFactory sessionFactory;
        Transaction tx = null;
        try {
            brLinije = Integer.parseInt(brLinijeStr);
            sessionFactory = HibernateUtil.getSessionFactory();
            Session session = sessionFactory.openSession();
            Vozaci v = (Vozaci) session.load(Vozaci.class, vozac.getId());
            Gradske linija = (Gradske) session.load(Gradske.class, brLinije);
            Vozigradsku vozi = new Vozigradsku(linija, v);
            tx = session.beginTransaction();
            v.setSlobodan(0);
            session.save(vozi);
            tx.commit();
            session.close();
            slobodniVozaci.remove(v);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Vozac je dodeljen",
                            "Vozac je dodeljen"));
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

    public String logout() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        session.removeAttribute("korisnik");
        session.invalidate();
        return "index";
    }

}
