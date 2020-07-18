/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
/**
 *
 * @author Jovana
 */
@ManagedBean
@ViewScoped
public class Gost {

    @ManagedProperty(value="#{pretraga}")
    private Pretraga pretragaBean;
    private boolean unosPrevoznika = false;
    private boolean unosVremena = false;

    public Pretraga getPretragaBean() {
        return pretragaBean;
    }

    public void setPretragaBean(Pretraga pretragaBean) {
        this.pretragaBean = pretragaBean;
    }

    public boolean isUnosPrevoznika() {
        return unosPrevoznika;
    }

    public void setUnosPrevoznika(boolean unosPrevoznika) {
        this.unosPrevoznika = unosPrevoznika;
    }

    public boolean isUnosVremena() {
        return unosVremena;
    }

    public void setUnosVremena(boolean unosVremena) {
        this.unosVremena = unosVremena;
    }


    public void postaviKriterijum() {
        if (pretragaBean.getKriterijum().equals("prevoznik")) {
            unosPrevoznika = true;
            unosVremena = false;
            return;
        }
        if (pretragaBean.getKriterijum().equals("vreme")) {
            unosPrevoznika = false;
            unosVremena = true;
            return;
        }
        unosPrevoznika = false;
        unosVremena = false;
    }

}
