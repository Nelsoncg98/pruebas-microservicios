/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Tienda.ApiBoletaN;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/boleta")
public class ApiBoletaNControl {
    @Autowired
    private RestTemplate resTem;
    @GetMapping
    public List<Articulo>listarArticulos(){
        // Resuelve por nombre de servicio registrado en Eureka (via RestTemplate @LoadBalanced)
        // Nota: el controlador de ApiArticuloN mapea bajo "/articulo", por lo que el path correcto es "/articulo/listar".
        String url="http://ApiArticuloN/articulo/listar";
        return resTem.getForObject(url,List.class);
    }
}
