package com.SistemaDeGestion.TP5.servicio.Implementacion;

import com.SistemaDeGestion.TP5.modelo.Departamento;
import com.SistemaDeGestion.TP5.repositorio.DepartamentoRepository;
import com.SistemaDeGestion.TP5.servicio.Interface.DepartamentoService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class DepartamentoServiceImpl implements DepartamentoService {

    private final DepartamentoRepository departamentoRepository;

    public DepartamentoServiceImpl(DepartamentoRepository departamentoRepository) {
        this.departamentoRepository = departamentoRepository;
    }

    @Override
    public Departamento guardar(Departamento departamento) {
        return departamentoRepository.save(departamento);
    }

    @Override
    public Departamento buscarPorId(Long id) {
        return departamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Departamento no encontrado con ID: " + id));
    }

    @Override
    public Departamento buscarPorNombre(String nombre) {
        return departamentoRepository.findByNombre(nombre)
                .orElseThrow(() -> new RuntimeException("Departamento no encontrado con nombre: " + nombre));
    }

    @Override
    public List<Departamento> obtenerTodos() {
        return departamentoRepository.findAll();
    }

    @Override
    public Departamento actualizar(Long id, Departamento departamento) {
        if (!departamentoRepository.existsById(id)) {
            throw new RuntimeException("Departamento no encontrado con ID: " + id);
        }
        departamento.setId(id);
        return departamentoRepository.save(departamento);
    }

    @Override
    public void eliminar(Long id) {
        if (!departamentoRepository.existsById(id)) {
            throw new RuntimeException("Departamento no encontrado con ID: " + id);
        }
        departamentoRepository.deleteById(id);
    }
}
