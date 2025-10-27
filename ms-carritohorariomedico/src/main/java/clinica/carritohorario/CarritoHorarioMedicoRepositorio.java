package clinica.carritohorario;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;

@Repository
public class CarritoHorarioMedicoRepositorio {
    private final ConcurrentMap<Long, Linea> store = new ConcurrentHashMap<>();
    private final AtomicLong seq = new AtomicLong(1);

    public Linea save(Linea lin){
        if (lin.getId() == null){
            lin.setId(seq.getAndIncrement());
        }
        store.put(lin.getId(), lin);
        return lin;
    }

    public void deleteById(Long id){
        store.remove(id);
    }

    public List<Linea> findAll(){
        return Collections.unmodifiableList(new ArrayList<>(store.values()));
    }

    public void deleteAll(){
        store.clear();
    }
}
