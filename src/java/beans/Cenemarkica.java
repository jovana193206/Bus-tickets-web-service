package beans;
// Generated Sep 22, 2018 4:10:50 PM by Hibernate Tools 4.3.1



/**
 * Cenemarkica generated by hbm2java
 */
public class Cenemarkica  implements java.io.Serializable {


     private Integer id;
     private String kategorijakorisnika;
     private int godisnja;
     private double cena;

    public Cenemarkica() {
    }

    public Cenemarkica(String kategorijakorisnika, int godisnja, double cena) {
       this.kategorijakorisnika = kategorijakorisnika;
       this.godisnja = godisnja;
       this.cena = cena;
    }
   
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public String getKategorijakorisnika() {
        return this.kategorijakorisnika;
    }
    
    public void setKategorijakorisnika(String kategorijakorisnika) {
        this.kategorijakorisnika = kategorijakorisnika;
    }
    public int getGodisnja() {
        return this.godisnja;
    }
    
    public void setGodisnja(int godisnja) {
        this.godisnja = godisnja;
    }
    public double getCena() {
        return this.cena;
    }
    
    public void setCena(double cena) {
        this.cena = cena;
    }




}


