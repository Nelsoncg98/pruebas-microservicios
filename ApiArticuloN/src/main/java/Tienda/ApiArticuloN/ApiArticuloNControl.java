/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Tienda.ApiArticuloN;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/listar")
public class ApiArticuloNControl {
    private final List<Articulo>articulos=List.of(
        new Articulo("A001","Articulo 1",10),
        new Articulo("A002","Articulo 2",20), 
        new Articulo("A003","Articulo 3",30)
    );
    @GetMapping
    public List<Articulo>listar(){
        return articulos;
    }
    
}
