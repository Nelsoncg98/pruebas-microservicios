/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ArqEmpN.Carrito;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/carrito")
public class Control {
    @Autowired
    private Servicio serv;
    @PostMapping("/agregar")
    public Linea agregar(@RequestBody Linea lin){
        return serv.agregar(lin);
    }
    @DeleteMapping("/quitar/{id}")
    public void quitar(@PathVariable Long id){
        serv.quitar(id);
    }
    @GetMapping("/listar")
    public List<Linea>listar(){
        return serv.listar();
    }
    @GetMapping("/total")
    public double total(){
        return serv.total();
    }
    @DeleteMapping("/nuevo")
    public void nuevo(){
        serv.nuevo();
    }   
}
