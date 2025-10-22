/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Tienda.ApiArticuloN;

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
@RequestMapping("/articulo")
public class ApiArticuloNControl {

    @Autowired
    private ApiArticuloNServicio api;
    
    @GetMapping("/listar")
    public List<Articulo>listar(){
        return api.listar();
    }

    @PostMapping("/guardar")
    public Articulo guardar(@RequestBody Articulo articulo) {
        return api.guardar(articulo);
        
    }

    @GetMapping("/buscar/{id}")
    public Articulo buscar(@PathVariable Long id) {
        return api.buscar(id);
    }

    @DeleteMapping("/eliminar/{id}")
    public void eliminar(@PathVariable Long id){
        api.eliminar(id);
    }

    @DeleteMapping("/limpiar")
    public void limpiar(){
        api.limpiar();
    }




    
}
