package Tienda.ApiArticuloN;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApiArticuloNServicio {

    @Autowired
    private ApiArticuloNRepositorio api;

    public Articulo guardar(Articulo articulo) {
        return api.save(articulo);
    }

    public Articulo buscar(Long id) {
        return api.findById(id).orElse(null);
    }
    public void eliminar(Long id){
        api.deleteById(id);
    }

    public List<Articulo> listar() {
        return api.findAll();
    }

    public void limpiar(){
        api.deleteAll();
    }

}
