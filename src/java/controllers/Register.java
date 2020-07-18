/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import beans.Korisnik;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.hibernate.SessionFactory;
import util.HibernateUtil;
import org.hibernate.Session;
import javax.faces.context.FacesContext;
import org.hibernate.Query;
import javax.faces.application.FacesMessage;
import org.hibernate.Transaction;

/**
 *
 * @author Jovana
 */
@ManagedBean
@ViewScoped
public class Register {
    private String ime;
    private String prezime;
    private String user;
    private String pass;
    private String passAgain;
    private String adresa;
    private Date rodjenje;
    private String telefon;
    private String zaposlenje;
    private String email = null;

    public String getPassAgain() {
        return passAgain;
    }

    public void setPassAgain(String passAgain) {
        this.passAgain = passAgain;
    }
    
    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
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

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public Date getRodjenje() {
        return rodjenje;
    }

    public void setRodjenje(Date rodjenje) {
        this.rodjenje = rodjenje;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getZaposlenje() {
        return zaposlenje;
    }

    public void setZaposlenje(String zaposlenje) {
        this.zaposlenje = zaposlenje;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public String register() {
        SessionFactory sessionFactory;
        Transaction tx = null;
        try {
            sessionFactory = HibernateUtil.getSessionFactory();
            Session session = sessionFactory.openSession();
            tx = session.beginTransaction();
            Korisnik k = new Korisnik(user, pass, 1, ime, prezime, adresa, rodjenje, telefon, zaposlenje, 0);
            k.setEmail(email);
            session.save(k);
            tx.commit();
            session.close();
            FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Zahtev za registraciju je uspesno podnet!",
                                "Zahtev za registraciju je uspesno podnet!"));
        } catch (Exception e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }
        return "";
    }
    
    public void checkUser() {
        SessionFactory sessionFactory;
        try {
            sessionFactory = HibernateUtil.getSessionFactory();
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            Query query = session.createQuery("from Korisnik where username = :user ");
            query.setParameter("user", user);
            Korisnik k = (Korisnik) query.uniqueResult();
            session.getTransaction().commit();
            session.close();
            if (k != null) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Korisnik sa unetim korisnickim imenom vec postoji!",
                                "Korisnik sa unetim korisnickim imenom vec postoji!"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void checkPass() {
        if(pass.length() < 6 || pass.length() > 12) {
            FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Lozinka mora imati izmedju 6 i 12 karaktera!",
                                "Lozinka mora imati izmedju 6 i 12 karaktera!"));
            return;
        }
        if(!Character.isLetter(pass.charAt(0))) {
            FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Lozinka mora poceti slovom!",
                                "Lozinka mora poceti slovom!"));
            return;
        }
        int up = 0, down = 0, num = 0, spec = 0, uzastopno = 0;
        char prev, c = '\0';
        for(int i = 0; i < pass.length(); i++) {
            prev = c;
            c = pass.charAt(i);
            if(prev == c) {
                uzastopno++;
                if(uzastopno > 2) {
                    FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Maksimalan broj uzastopnih karaktera u lozinci je 2!",
                                "Maksimalan broj uzastopnih slova u lozinci je 2!"));
                    return;
                }
            }
            else uzastopno = 1;
            if(Character.isUpperCase(c)) {
                up++;
                continue;
            }
            if(Character.isLowerCase(c)) {
                down++;
                continue;
            }
            if(Character.isDigit(c)) {
                num++;
                continue;
            }
            spec++;
        }
        if(up < 1) {
            FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Minimalan broj velikih slova u lozinci je 1!",
                                "Minimalan broj velikih slova u lozinci je 1!"));
        }
        if(down < 3) {
            FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Minimalan broj malih slova u lozinci je 3!",
                                "Minimalan broj malih slova u lozinci je 3!"));
        }
        if(num < 1) {
            FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Minimalan broj numerika u lozinci je 1!",
                                "Minimalan broj numerika u lozinci je 1!"));
        }
        if(spec < 1) {
            FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Minimalan broj specijalnih znakova u lozinci je 1!",
                                "Minimalan broj specijalnih znakova u lozinci je 1!"));
        }
    }
    
    public void checkPassMatch() {
        if(!pass.equals(passAgain)) {
            FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Lozinka nije potvrdjena!",
                                "Lozinka nije potvrdjena!"));
        }
    }
    
}
