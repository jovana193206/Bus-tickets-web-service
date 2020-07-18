/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import beans.Cenemarkica;
import beans.Gradske;
import beans.Koordinate;
import beans.Korisnik;
import beans.Markice;
import beans.Medjugradske;
import beans.Redvoznje;
import beans.Rezervacija;
import beans.Vozaci;
import beans.Vozigradsku;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;
import util.HibernateUtil;

/**
 *
 * @author Jovana
 */
@ManagedBean
@SessionScoped
public class Korisnicki implements Serializable {

    private double mesecnaCena;
    private double godisnjaCena;
    private List<Rezervacija> rezervacije;
    private List<Medjugradske> sveMedjugradske;
    private Medjugradske detaljnoMedjugradska;
    private MapModel mymapModel;
    private List<String> autobusSlike;
    private List<Vozaci> gradskaVozaci;
    private Redvoznje smer1;
    private Redvoznje smer2;

    public MapModel getMymapModel() {
        return mymapModel;
    }

    public void setMymapModel(MapModel mymapModel) {
        this.mymapModel = mymapModel;
    }

    public Redvoznje getSmer1() {
        return smer1;
    }

    public void setSmer1(Redvoznje smer1) {
        this.smer1 = smer1;
    }

    public Redvoznje getSmer2() {
        return smer2;
    }

    public void setSmer2(Redvoznje smer2) {
        this.smer2 = smer2;
    }

    public List<Vozaci> getGradskaVozaci() {
        return gradskaVozaci;
    }

    public void setGradskaVozaci(List<Vozaci> gradskaVozaci) {
        this.gradskaVozaci = gradskaVozaci;
    }

    public Medjugradske getDetaljnoMedjugradska() {
        return detaljnoMedjugradska;
    }

    public void setDetaljnoMedjugradska(Medjugradske detaljnoMedjugradska) {
        this.detaljnoMedjugradska = detaljnoMedjugradska;
    }

    public List<String> getAutobusSlike() {
        return autobusSlike;
    }

    public void setAutobusSlike(List<String> autobusSlike) {
        this.autobusSlike = autobusSlike;
    }

    public List<Medjugradske> getSveMedjugradske() {
        return sveMedjugradske;
    }

    public void setSveMedjugradske(List<Medjugradske> sveMedjugradske) {
        this.sveMedjugradske = sveMedjugradske;
    }

    public List<Rezervacija> getRezervacije() {
        return rezervacije;
    }

    public void setRezervacije(List<Rezervacija> rezervacije) {
        this.rezervacije = rezervacije;
    }

    public double getMesecnaCena() {
        return mesecnaCena;
    }

    public void setMesecnaCena(double mesecnaCena) {
        this.mesecnaCena = mesecnaCena;
    }

    public double getGodisnjaCena() {
        return godisnjaCena;
    }

    public void setGodisnjaCena(double godisnjaCena) {
        this.godisnjaCena = godisnjaCena;
    }

    public String idiNaMarkice() {
        SessionFactory sessionFactory;
        Transaction tx = null;
        try {
            sessionFactory = HibernateUtil.getSessionFactory();
            Session session = sessionFactory.openSession();
            HttpSession httpsession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
            Korisnik k = (Korisnik) httpsession.getAttribute("korisnik");
            String kategorija = k.getZaposlenje();
            tx = session.beginTransaction();
            Query query = session.createQuery("from Cenemarkica where kategorijakorisnika = :kat and godisnja = 0");
            query.setParameter("kat", kategorija);
            Cenemarkica cena = (Cenemarkica) query.uniqueResult();
            mesecnaCena = cena.getCena();
            query = session.createQuery("from Cenemarkica where kategorijakorisnika = :kat and godisnja = 1");
            query.setParameter("kat", kategorija);
            cena = (Cenemarkica) query.uniqueResult();
            godisnjaCena = cena.getCena();
            tx.commit();
            session.close();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        }
        return "korisnikKartice";
    }

    public void kupiMarkicu(int godisnja) {
        SessionFactory sessionFactory;
        Transaction tx = null;
        try {
            sessionFactory = HibernateUtil.getSessionFactory();
            Session session = sessionFactory.openSession();
            HttpSession httpsession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
            Korisnik k = (Korisnik) httpsession.getAttribute("korisnik");
            tx = session.beginTransaction();
            Query query = session.createQuery("from Markice where korisnikId = :user and godisnja = :god");
            query.setParameter("user", k.getUsername());
            query.setParameter("god", godisnja);
            Markice m = (Markice) query.uniqueResult();
            if (m != null) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Vec imate trazenu kartu",
                                "Vec imate trazenu kartu"));
            } else {
                m = new Markice(k, godisnja, 0, 0);
                session.save(m);
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Zahtev za izdavanje karte je podnet",
                                "Zahtev za izdavanje karte je podnet"));
            }
            tx.commit();
            session.close();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        }
    }

    public void idiNaRezervacije() {
        rezervacije = new ArrayList<Rezervacija>();
        try {
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpSession hs = (HttpSession) fc.getExternalContext().getSession(false);
            Korisnik k = (Korisnik) hs.getAttribute("korisnik");
            for (Object o : k.getRezervacijas()) {
                Rezervacija r = (Rezervacija) o;
                if (r.getOdobrena() == 1) {
                    rezervacije.add(r);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //return "korisnikRezervacije";
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("faces/korisnikRezervacije.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean mozeOtkaz(Integer id) {
        SessionFactory sessionFactory;
        Transaction tx = null;
        try {
            sessionFactory = HibernateUtil.getSessionFactory();
            Session session = sessionFactory.openSession();
            tx = session.beginTransaction();
            Query query = session.createQuery("from Rezervacija where id = :id ");
            query.setParameter("id", id);
            Rezervacija rez = (Rezervacija) query.uniqueResult();
            Hibernate.initialize(rez.getMedjugradske());
            Hibernate.initialize(rez.getMedjugradske().getPolazak());
            tx.commit();
            session.close();
            long polazakTime = rez.getMedjugradske().getPolazak().getTime();
            long diff = polazakTime - (new Date()).getTime();
            long hour = 3600000;
            if (diff > hour) {
                return true;
            }
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        }
        return false;
    }

    public boolean rezProsla(Integer id) {
        SessionFactory sessionFactory;
        Transaction tx = null;
        try {
            sessionFactory = HibernateUtil.getSessionFactory();
            Session session = sessionFactory.openSession();
            tx = session.beginTransaction();
            Query query = session.createQuery("from Rezervacija where id = :id ");
            query.setParameter("id", id);
            Rezervacija rez = (Rezervacija) query.uniqueResult();
            Hibernate.initialize(rez.getMedjugradske());
            Hibernate.initialize(rez.getMedjugradske().getPolazak());
            tx.commit();
            session.close();
            long diff = rez.getMedjugradske().getPolazak().getTime() - (new Date()).getTime();
            if (diff < 0) {
                return true;
            }
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        }
        return false;
    }

    public String otkaziRez(Integer id) {
        SessionFactory sessionFactory;
        Transaction tx = null;
        try {
            sessionFactory = HibernateUtil.getSessionFactory();
            Session session = sessionFactory.openSession();
            Rezervacija rez = (Rezervacija) session.load(Rezervacija.class, id);
            tx = session.beginTransaction();
            session.delete(rez);
            tx.commit();
            session.close();
            for (Rezervacija r : rezervacije) {
                if (Objects.equals(r.getId(), id)) {
                    FacesContext fc = FacesContext.getCurrentInstance();
                    HttpSession hs = (HttpSession) fc.getExternalContext().getSession(false);
                    Korisnik k = (Korisnik) hs.getAttribute("korisnik");
                    k.getRezervacijas().remove(r);
                    rezervacije.remove(r);
                    break;
                }
            }
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Vasa rezervacija je otkazana",
                            "Vasa rezervacija je otkazana"));
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        }
        return "";
    }

    public String idiNaMedjugradske() {
        SessionFactory sessionFactory;
        Transaction tx = null;
        try {
            sessionFactory = HibernateUtil.getSessionFactory();
            Session session = sessionFactory.openSession();
            tx = session.beginTransaction();
            Query query = session.createQuery("from Medjugradske where polazak > :date");
            query.setParameter("date", new Timestamp((new Date()).getTime()));
            sveMedjugradske = query.list();
            for (Medjugradske m : sveMedjugradske) {
                Hibernate.initialize(m.getAutobusi());
                Hibernate.initialize(m.getPrevoznici());
                Hibernate.initialize(m.getVozaci());
            }
            tx.commit();
            session.close();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        }
        return "korisnikMedjugradske";
    }

    public String prikaziDetalje(Medjugradske linija) {
        detaljnoMedjugradska = linija;
        if (autobusSlike != null) {
            autobusSlike.clear();
        }
        else {
            autobusSlike = new ArrayList<String>();
        }
        if (detaljnoMedjugradska.getAutobusi() != null) {
            String[] slike = detaljnoMedjugradska.getAutobusi().getSlike().split(", ");
            for (String slika : slike) {
                autobusSlike.add(slika);
            }
        }
        mymapModel = new DefaultMapModel();
        SessionFactory sessionFactory;
        Transaction tx = null;
        try {
            sessionFactory = HibernateUtil.getSessionFactory();
            Session session = sessionFactory.openSession();
            List<Koordinate> medjustanice = new ArrayList<Koordinate>();
            tx = session.beginTransaction();
            Query query = session.createQuery("from Koordinate where grad = :grad");
            query.setParameter("grad", detaljnoMedjugradska.getPolaziste());
            Koordinate polaziste = (Koordinate) query.uniqueResult();
            String[] stanice = detaljnoMedjugradska.getMedjustanice().split("-");
            for (String stanica : stanice) {
                query = session.createQuery("from Koordinate where grad = :grad");
                query.setParameter("grad", stanica);
                Koordinate medjustanica = (Koordinate) query.uniqueResult();
                if (medjustanica != null) {
                    medjustanice.add(medjustanica);
                }
            }
            query = session.createQuery("from Koordinate where grad = :grad");
            query.setParameter("grad", detaljnoMedjugradska.getOdrediste());
            Koordinate odrediste = (Koordinate) query.uniqueResult();
            tx.commit();
            session.close();
            Marker polazisteMarker = new Marker(new LatLng(polaziste.getLatitude(), polaziste.getLongitude()), polaziste.getGrad());
            polazisteMarker.setIcon("./resources/photos/blue-dot.png");
            mymapModel.addOverlay(polazisteMarker);
            for (Koordinate k : medjustanice) {
                Marker medjuMarker = new Marker(new LatLng(k.getLatitude(), k.getLongitude()), k.getGrad());
                medjuMarker.setIcon("./resources/photos/yellow-dot.png");
                mymapModel.addOverlay(medjuMarker);
            }
            Marker odredisteMarker = new Marker(new LatLng(odrediste.getLatitude(), odrediste.getLongitude()), odrediste.getGrad());
            odredisteMarker.setIcon("./resources/photos/green-dot.png");
            mymapModel.addOverlay(odredisteMarker);
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        }
        return "medjugradskaDetalji";
        /*try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("faces/medjugradskaDetalji.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }*/
    }

    public void rezervisi(Medjugradske linija) {
        SessionFactory sessionFactory;
        Transaction tx = null;
        try {
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpSession hs = (HttpSession) fc.getExternalContext().getSession(false);
            Korisnik k = (Korisnik) hs.getAttribute("korisnik");
            Rezervacija rez = new Rezervacija(k, linija, linija.getPolaziste(), linija.getOdrediste(), 0, 0);
            sessionFactory = HibernateUtil.getSessionFactory();
            Session session = sessionFactory.openSession();
            tx = session.beginTransaction();
            session.save(rez);
            tx.commit();
            session.close();
            k.getRezervacijas().add(rez);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Zahtev za rezervaciju je prosledjen",
                            "Zahtev za rezervaciju je prosledjen"));
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        }
    }
    
    public void rezervisiDetaljnoMedjugradska() {
        SessionFactory sessionFactory;
        Transaction tx = null;
        try {
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpSession hs = (HttpSession) fc.getExternalContext().getSession(false);
            Korisnik k = (Korisnik) hs.getAttribute("korisnik");
            Rezervacija rez = new Rezervacija(k, detaljnoMedjugradska, detaljnoMedjugradska.getPolaziste(), detaljnoMedjugradska.getOdrediste(), 0, 0);
            sessionFactory = HibernateUtil.getSessionFactory();
            Session session = sessionFactory.openSession();
            tx = session.beginTransaction();
            session.save(rez);
            tx.commit();
            session.close();
            k.getRezervacijas().add(rez);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Zahtev za rezervaciju je prosledjen",
                            "Zahtev za rezervaciju je prosledjen"));
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        }
    }

    public String gradskaDetaljno(Gradske linija) {
        SessionFactory sessionFactory;
        Transaction tx = null;
        try {
            gradskaVozaci = new ArrayList<Vozaci>();
            smer1 = null;
            smer2 = null;
            sessionFactory = HibernateUtil.getSessionFactory();
            Session session = sessionFactory.openSession();
            tx = session.beginTransaction();
            Query query = session.createQuery("from Vozigradsku where linija = :linija");
            query.setParameter("linija", linija.getBrLinije());
            List<Vozigradsku> vozi = query.list();
            for (Vozigradsku v : vozi) {
                Hibernate.initialize(v.getVozaci());
                gradskaVozaci.add(v.getVozaci());
            }
            tx.commit();
            session.close();
            Object[] niz = linija.getRedvoznjes().toArray();
            if (niz.length > 1) {
                smer1 = (Redvoznje) niz[0];
                smer2 = (Redvoznje) niz[1];
            }
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        }
        return "gradskaDetalji";
        /*try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("faces/gradskaDetalji.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }*/
    }

}
