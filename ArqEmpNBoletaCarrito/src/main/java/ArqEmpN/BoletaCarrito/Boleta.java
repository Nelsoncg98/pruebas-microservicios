/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ArqEmpN.BoletaCarrito;

import java.util.List;

public class Boleta {
    private String fec;
    private double tot;
    private List<Linea>lis;

    public Boleta() {
    }

    public Boleta(String fec, double tot, List<Linea> lis) {
        this.fec = fec;
        this.tot = tot;
        this.lis = lis;
    }

    public String getFec() {
        return fec;
    }

    public void setFec(String fec) {
        this.fec = fec;
    }

    public double getTot() {
        return tot;
    }

    public void setTot(double tot) {
        this.tot = tot;
    }

    public List<Linea> getLis() {
        return lis;
    }

    public void setLis(List<Linea> lis) {
        this.lis = lis;
    }
    
}
