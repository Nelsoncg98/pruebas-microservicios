/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ArqEmpN.BoletaCarrito;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class Servicio {
    @Autowired
    private RestTemplate resTem;
    
    public Boleta nueva(){
        return new Boleta(fecha(),total(),lista());
    }
    private String fecha(){
        Date dat=new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(dat);
    }
    private double total(){
        String url="http://Carrito/carrito/total";
        return resTem.getForObject(url, double.class);
    }
    private List<Linea>lista(){
        String url="http://Carrito/carrito/listar";
        return resTem.getForObject(url, List.class);
    }
}
