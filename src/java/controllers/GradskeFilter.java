/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import beans.Gradske;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedProperty;

/**
 *
 * @author Jovana
 */
@ManagedBean
@ViewScoped
public class GradskeFilter {

    private List<Gradske> sveGradske;
    private List<Gradske> filteredGradske;
    private String ciljnaStanica;
    @ManagedProperty(value = "#{login}")
    private Login loginBean;

    @PostConstruct
    public void postConstruct() {
        sveGradske = loginBean.getSveGradske();
    }

    public void pretraziOdrediste() {
        List<Gradske> filtrirano = new ArrayList<Gradske>();
        if (!ciljnaStanica.isEmpty()) {
            for (Gradske g : sveGradske) {
                if (ciljnaStanica.equals(g.getPolaziste()) || ciljnaStanica.equals(g.getOdrediste())) {
                    filtrirano.add(g);
                    continue;
                }
                String[] medjustanice = g.getMedjustanice().split("-");
                for (String stanica : medjustanice) {
                    if (ciljnaStanica.equals(stanica)) {
                        filtrirano.add(g);
                        break;
                    }
                }
            }
            sveGradske = filtrirano;
        }
        else sveGradske = loginBean.getSveGradske();
    }

    public String getCiljnaStanica() {
        return ciljnaStanica;
    }

    public void setCiljnaStanica(String ciljnaStanica) {
        this.ciljnaStanica = ciljnaStanica;
    }

    public Login getLoginBean() {
        return loginBean;
    }

    public void setLoginBean(Login loginBean) {
        this.loginBean = loginBean;
    }

    public List<Gradske> getSveGradske() {
        return sveGradske;
    }

    public void setSveGradske(List<Gradske> sveGradske) {
        this.sveGradske = sveGradske;
    }

    public List<Gradske> getFilteredGradske() {
        return filteredGradske;
    }

    public void setFilteredGradske(List<Gradske> filteredGradske) {
        this.filteredGradske = filteredGradske;
    }

}
