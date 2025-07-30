package com.SistemaDeGestion.TP5.servicio.Implementacion;

import com.SistemaDeGestion.TP5.modelo.Proyecto;
import com.SistemaDeGestion.TP5.repositorio.ProyectoRepository;
import com.SistemaDeGestion.TP5.servicio.Interface.ProyectoService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class ProyectoServiceImpl implements ProyectoService {

    private final ProyectoRepository proyectoRepository;

    public ProyectoServiceImpl(ProyectoRepository proyectoRepository) {
        this.proyectoRepository = proyectoRepository;
    }

    @Override
    public Proyecto guardar(Proyecto proyecto) {
        return proyectoRepository.save(proyecto);
    }

    @Override
    public Proyecto buscarPorId(Long id) {
        return proyectoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado con ID: " + id));
    }

    @Override
    public List<Proyecto> obtenerTodos() {
        return proyectoRepository.findAll();
    }

    @Override
    public List<Proyecto> buscarProyectosActivos() {
        return proyectoRepository.findByFechaFinAfter(LocalDate.now());
    }

    @Override
    public Proyecto actualizar(Long id, Proyecto proyecto) {
        if (!proyectoRepository.existsById(id)) {
            throw new RuntimeException("Proyecto no encontrado con ID: " + id);
        }
        proyecto.setId(id);
        return proyectoRepository.save(proyecto);
    }

    @Override
    public void eliminar(Long id) {
        if (!proyectoRepository.existsById(id)) {
            throw new RuntimeException("Proyecto no encontrado con ID: " + id);
        }
        proyectoRepository.deleteById(id);
    }
}
