/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import beans.Autobusi;
import beans.Koordinate;
import beans.Korisnik;
import beans.Markice;
import beans.Medjugradske;
import beans.Prevoznici;
import beans.Rezervacija;
import beans.Vozaci;
import java.io.IOException;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
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
public class Admin implements Serializable {

    private List<Korisnik> registracije;
    private List<Rezervacija> rezervacije;
    private List<Markice> markice;
    private String vozacIme;
    private String vozacPrezime;
    private Date vozacDatumRodj;
    private Date vozacGodinaPocetka;
    private String prevoznik;
    private String polaziste;
    private String odrediste;
    private String medjustanice;
    private Integer novaMedjugradskaId;
    private Date polazak;
    private Date dolazak;
    private List<Autobusi> slobodniAutobusi;
    private Autobusi odabraniAutobus;
    private List<Vozaci> slobodniVozaci;
    private Vozaci odabraniVozac;
    private String grad;
    private Double latitude;
    private Double longitude;
    private Integer updateMedjugradskaId;

    public Integer getUpdateMedjugradskaId() {
        return updateMedjugradskaId;
    }

    public void setUpdateMedjugradskaId(Integer updateMedjugradskaId) {
        this.updateMedjugradskaId = updateMedjugradskaId;
    }

    public String getGrad() {
        return grad;
    }

    public void setGrad(String grad) {
        this.grad = grad;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
    
    public Vozaci getOdabraniVozac() {
        return odabraniVozac;
    }

    public void setOdabraniVozac(Vozaci odabraniVozac) {
        this.odabraniVozac = odabraniVozac;
    }

    public List<Vozaci> getSlobodniVozaci() {
        return slobodniVozaci;
    }

    public void setSlobodniVozaci(List<Vozaci> slobodniVozaci) {
        this.slobodniVozaci = slobodniVozaci;
    }

    public Autobusi getOdabraniAutobus() {
        return odabraniAutobus;
    }

    public void setOdabraniAutobus(Autobusi odabraniAutobus) {
        this.odabraniAutobus = odabraniAutobus;
    }

    public List<Autobusi> getSlobodniAutobusi() {
        return slobodniAutobusi;
    }

    public void setSlobodniAutobusi(List<Autobusi> slobodniAutobusi) {
        this.slobodniAutobusi = slobodniAutobusi;
    }

    public Integer getNovaMedjugradskaId() {
        return novaMedjugradskaId;
    }

    public void setNovaMedjugradskaId(Integer novaMedjugradskaId) {
        this.novaMedjugradskaId = novaMedjugradskaId;
    }

    public String getPrevoznik() {
        return prevoznik;
    }

    public void setPrevoznik(String prevoznik) {
        this.prevoznik = prevoznik;
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

    public Date getPolazak() {
        return polazak;
    }

    public void setPolazak(Date polazak) {
        this.polazak = polazak;
    }

    public Date getDolazak() {
        return dolazak;
    }

    public void setDolazak(Date dolazak) {
        this.dolazak = dolazak;
    }

    public String getVozacIme() {
        return vozacIme;
    }

    public void setVozacIme(String vozacIme) {
        this.vozacIme = vozacIme;
    }

    public String getVozacPrezime() {
        return vozacPrezime;
    }

    public void setVozacPrezime(String vozacPrezime) {
        this.vozacPrezime = vozacPrezime;
    }

    public Date getVozacDatumRodj() {
        return vozacDatumRodj;
    }

    public void setVozacDatumRodj(Date vozacDatumRodj) {
        this.vozacDatumRodj = vozacDatumRodj;
    }

    public Date getVozacGodinaPocetka() {
        return vozacGodinaPocetka;
    }

    public void setVozacGodinaPocetka(Date vozacGodinaPocetka) {
        this.vozacGodinaPocetka = vozacGodinaPocetka;
    }

    public List<Markice> getMarkice() {
        return markice;
    }

    public void setMarkice(List<Markice> markice) {
        this.markice = markice;
    }

    public List<Rezervacija> getRezervacije() {
        return rezervacije;
    }

    public void setRezervacije(List<Rezervacija> rezervacije) {
        this.rezervacije = rezervacije;
    }

    public List<Korisnik> getRegistracije() {
        return registracije;
    }

    public void setRegistracije(List<Korisnik> registracije) {
        this.registracije = registracije;
    }

    public void idiNaZahteve() {
        SessionFactory sessionFactory;
        Transaction tx = null;
        try {
            sessionFactory = HibernateUtil.getSessionFactory();
            Session session = sessionFactory.openSession();
            tx = session.beginTransaction();
            Query query = session.createQuery("from Korisnik where status = 0 ");
            registracije = query.list();
            query = session.createQuery("from Rezervacija where odobrena = 0 ");
            rezervacije = query.list();
            for (Rezervacija r : rezervacije) {
                Hibernate.initialize(r.getKorisnik());
                Hibernate.initialize(r.getMedjugradske());
            }
            query = session.createQuery("from Markice where odobrena = 0 ");
            markice = query.list();
            for (Object o : markice) {
                Markice m = (Markice) o;
                Hibernate.initialize(m.getKorisnik());
            }
            tx.commit();
            session.close();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        }
        //return "adminZahtevi";
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("faces/adminZahtevi.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void odobriRegistraciju(String username) {
        SessionFactory sessionFactory;
        Transaction tx = null;
        try {
            sessionFactory = HibernateUtil.getSessionFactory();
            Session session = sessionFactory.openSession();
            Korisnik k = (Korisnik) session.load(Korisnik.class, username);
            tx = session.beginTransaction();
            k.setStatus(1);
            Query query = session.createQuery("from Korisnik where status = 0 ");
            registracije = query.list();
            tx.commit();
            session.close();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Zahtev za registraciju je odobren",
                            "Zahtev za registraciju je odobren"));
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        }
    }

    public void odbijRegistraciju(String username) {
        SessionFactory sessionFactory;
        Transaction tx = null;
        try {
            sessionFactory = HibernateUtil.getSessionFactory();
            Session session = sessionFactory.openSession();
            Korisnik k = (Korisnik) session.load(Korisnik.class, username);
            tx = session.beginTransaction();
            session.delete(k);
            Query query = session.createQuery("from Korisnik where status = 0 ");
            registracije = query.list();
            tx.commit();
            session.close();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Zahtev za registraciju je odbijen",
                            "Zahtev za registraciju je odbijen"));
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        }
    }

    public void odobriRezervaciju(Integer id) {
        SessionFactory sessionFactory;
        Transaction tx = null;
        try {
            sessionFactory = HibernateUtil.getSessionFactory();
            Session session = sessionFactory.openSession();
            Rezervacija rez = (Rezervacija) session.load(Rezervacija.class, id);
            tx = session.beginTransaction();
            rez.setOdobrena(1);
            rez.setObavesti(1);
            Query query = session.createQuery("from Rezervacija where odobrena = 0 and obavesti = 0");
            rezervacije = query.list();
            tx.commit();
            session.close();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Zahtev za rezervaciju je odobren",
                            "Zahtev za rezervaciju je odobren"));
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        }
    }

    public void odbijRezervaciju(Integer id) {
        SessionFactory sessionFactory;
        Transaction tx = null;
        try {
            sessionFactory = HibernateUtil.getSessionFactory();
            Session session = sessionFactory.openSession();
            Rezervacija rez = (Rezervacija) session.load(Rezervacija.class, id);
            tx = session.beginTransaction();
            rez.setObavesti(1);
            Query query = session.createQuery("from Rezervacija where odobrena = 0 and obavesti = 0");
            rezervacije = query.list();
            tx.commit();
            session.close();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Zahtev za rezervaciju je odbijen",
                            "Zahtev za rezervaciju je odbijen"));
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        }
    }

    public void odobriMarkicu(Integer id) {
        SessionFactory sessionFactory;
        Transaction tx = null;
        try {
            sessionFactory = HibernateUtil.getSessionFactory();
            Session session = sessionFactory.openSession();
            Markice m = (Markice) session.load(Markice.class, id);
            tx = session.beginTransaction();
            m.setOdobrena(1);
            m.setObavesti(1);
            Query query = session.createQuery("from Markice where odobrena = 0 and obavesti = 0");
            markice = query.list();
            tx.commit();
            session.close();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Zahtev za izdavanje mesecne ili godisnje karte je odobren",
                            "Zahtev za izdavanje mesecne ili godisnje karte je odobren"));
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        }
    }

    public void odbijMarkicu(Integer id) {
        SessionFactory sessionFactory;
        Transaction tx = null;
        try {
            sessionFactory = HibernateUtil.getSessionFactory();
            Session session = sessionFactory.openSession();
            Markice m = (Markice) session.load(Markice.class, id);
            tx = session.beginTransaction();
            m.setObavesti(1);
            Query query = session.createQuery("from Markice where odobrena = 0 and obavesti = 0");
            markice = query.list();
            tx.commit();
            session.close();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Zahtev za izdavanje mesecne ili godisnje karte je odbijen",
                            "Zahtev za izdavanje mesecne ili godisnje karte je odbijen"));
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        }
    }

    public void dodajVozaca() {
        SessionFactory sessionFactory;
        Transaction tx = null;
        try {
            sessionFactory = HibernateUtil.getSessionFactory();
            Session session = sessionFactory.openSession();
            Calendar now = Calendar.getInstance();
            int nowYear = now.get(Calendar.YEAR);
            Calendar pocetak = Calendar.getInstance();
            pocetak.setTime(vozacGodinaPocetka);
            int godinaPocetka = pocetak.get(Calendar.YEAR);
            int iskustvo = nowYear - godinaPocetka;
            tx = session.beginTransaction();
            Vozaci novi = new Vozaci(vozacIme, vozacPrezime, vozacDatumRodj, iskustvo, 1);
            session.save(novi);
            tx.commit();
            session.close();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Vozac je dodat u bazu!",
                            "Vozac je dodat u bazu!"));
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

    public String dodajMedjugradsku() {
        SessionFactory sessionFactory;
        Transaction tx = null;
        try {
            sessionFactory = HibernateUtil.getSessionFactory();
            Session session = sessionFactory.openSession();
            tx = session.beginTransaction();
            Query query = session.createQuery("from Prevoznici where naziv = :prevoznik");
            query.setParameter("prevoznik", prevoznik);
            Prevoznici p = (Prevoznici) query.uniqueResult();
            Medjugradske nova = new Medjugradske(p, polaziste, odrediste, medjustanice);
            session.save(nova);
            tx.commit();
            novaMedjugradskaId = nova.getId();
            session.close();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Medjugradska linija je dodata u bazu!",
                            "Medjugradska linija je dodata u bazu!"));
            return "dodajMedjugradsku1";
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        }
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Doslo je do greske, pokusajte ponovo.",
                        "Doslo je do greske, pokusajte ponovo."));
        return "";
    }
    
    public String polazakDolazak() {
        SessionFactory sessionFactory;
        Transaction tx = null;
        try {
            sessionFactory = HibernateUtil.getSessionFactory();
            Session session = sessionFactory.openSession();
            Medjugradske linija = (Medjugradske) session.load(Medjugradske.class, novaMedjugradskaId);
            tx = session.beginTransaction();
            linija.setPolazak(polazak);
            linija.setDolazak(dolazak);
            tx.commit();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacije o polasku i dolasku su dodate!",
                            "Informacije o polasku i dolasku su dodate!"));
            session.close();
            return "dodajMedjugradsku2";
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        }
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Doslo je do greske, pokusajte ponovo.",
                        "Doslo je do greske, pokusajte ponovo."));
        return "";
    }
    
    public String dodeliBus() {
        SessionFactory sessionFactory;
        Transaction tx = null;
        try {
            sessionFactory = HibernateUtil.getSessionFactory();
            Session session = sessionFactory.openSession();
            Autobusi bus = (Autobusi) session.load(Autobusi.class, odabraniAutobus.getId());
            Medjugradske linija = (Medjugradske) session.load(Medjugradske.class, novaMedjugradskaId);
            slobodniAutobusi.remove(odabraniAutobus);
            tx = session.beginTransaction();
            bus.setSlobodan(0);
            linija.setAutobusi(bus);
            tx.commit();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Autobus je dodeljen!",
                            "Autobus je dodeljen!"));
            session.close();
            return "dodajMedjugradsku3";
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        }
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Doslo je do greske, pokusajte ponovo.",
                        "Doslo je do greske, pokusajte ponovo."));
        return "";
    }
    
    public String dodeliVozaca() {
        SessionFactory sessionFactory;
        Transaction tx = null;
        try {
            sessionFactory = HibernateUtil.getSessionFactory();
            Session session = sessionFactory.openSession();
            Vozaci vozac = (Vozaci) session.load(Vozaci.class, odabraniVozac.getId());
            Medjugradske linija = (Medjugradske) session.load(Medjugradske.class, novaMedjugradskaId);
            slobodniVozaci.remove(odabraniVozac);
            tx = session.beginTransaction();
            vozac.setSlobodan(0);
            linija.setVozaci(vozac);
            tx.commit();
            session.close();
            odabraniAutobus = null;
            odabraniVozac = null;
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Vozac je dodeljen!",
                            "Vozac je dodeljen!"));
            return "adminMedjugradske";
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        }
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Doslo je do greske, pokusajte ponovo.",
                        "Doslo je do greske, pokusajte ponovo."));
        return "";
    }
    
    public void dodajStajaliste() {
        SessionFactory sessionFactory;
        Transaction tx = null;
        try {
            sessionFactory = HibernateUtil.getSessionFactory();
            Session session = sessionFactory.openSession();
            tx = session.beginTransaction();
            Query query = session.createQuery("from Koordinate where grad = :grad");
            query.setParameter("grad", grad);
            Koordinate k = (Koordinate) query.uniqueResult();
            tx.commit();
            tx = null;
            if (k != null) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Uneto stajaliste je vec registrovano!",
                                "Uneto stajaliste je vec registrovano!"));
            }
            tx = session.beginTransaction();
            Koordinate novi = new Koordinate(grad, latitude, longitude);
            session.save(novi);
            tx.commit();
            session.close();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Stajaliste je dodato u bazu!",
                            "Stajaliste je dodato u bazu!"));
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
    
    public void updateMedjugradska() {
        SessionFactory sessionFactory;
        Transaction tx = null;
        try {
            sessionFactory = HibernateUtil.getSessionFactory();
            Session session = sessionFactory.openSession();
            Medjugradske linija = (Medjugradske) session.load(Medjugradske.class, updateMedjugradskaId);
            if (linija == null) {
                FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Medjugradska linija sa zadatim Id nije nadjena u sistemu.",
                        "Medjugradska linija sa zadatim Id nije nadjena u sistemu."));
                session.close();
                return;
            }
            Vozaci vozac = (Vozaci) session.load(Vozaci.class, odabraniVozac.getId());
            Autobusi bus = (Autobusi) session.load(Autobusi.class, odabraniAutobus.getId());
            tx = session.beginTransaction();
            linija.setPolazak(polazak);
            linija.setDolazak(dolazak);
            vozac.setSlobodan(0);
            linija.setVozaci(vozac);
            bus.setSlobodan(0);
            linija.setAutobusi(bus);
            tx.commit();
            slobodniAutobusi.remove(bus);
            slobodniVozaci.remove(vozac);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacije su dodate!",
                            "Informacije su dodate!"));
            session.close();
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
    
    public String idiNaMedjugradske() {
        SessionFactory sessionFactory;
        Transaction tx = null;
        try {
            sessionFactory = HibernateUtil.getSessionFactory();
            Session session = sessionFactory.openSession();
            tx = session.beginTransaction();
            Query query = session.createQuery("from Autobusi where slobodan = 1");
            slobodniAutobusi = query.list();
            query = session.createQuery("from Vozaci where slobodan = 1");
            slobodniVozaci = query.list();
            tx.commit();
            session.close();
            return "adminMedjugradske";
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        }
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Doslo je do greske, pokusajte ponovo.",
                        "Doslo je do greske, pokusajte ponovo."));
        return "";
    }

}
