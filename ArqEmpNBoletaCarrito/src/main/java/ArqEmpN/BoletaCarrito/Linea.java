/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ArqEmpN.BoletaCarrito;

public class Linea {
    private Long id;
    private String nom;
    private double pre;
    private int can;
    
    public double getImporte(){
        return pre*can;
    }

    public Linea() {
    }

    public Linea(Long id, String nom, double pre, int can) {
        this.id = id;
        this.nom = nom;
        this.pre = pre;
        this.can = can;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public double getPre() {
        return pre;
    }

    public void setPre(double pre) {
        this.pre = pre;
    }

    public int getCan() {
        return can;
    }

    public void setCan(int can) {
        this.can = can;
    }
    
}
