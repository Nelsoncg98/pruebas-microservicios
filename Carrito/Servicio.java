/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ArqEmpN.Carrito;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Servicio {
    @Autowired
    private Repositorio repo;
    public Linea agregar(Linea lin){
        return repo.save(lin);
    }
    public void quitar(Long id){
        repo.deleteById(id);
    }
    public List<Linea>listar(){
        return repo.findAll();
    }
    public double total(){
        return repo.findAll().stream().mapToDouble(Linea::getImporte).sum();
    }
    public void nuevo(){
        repo.deleteAll();
    }
}
