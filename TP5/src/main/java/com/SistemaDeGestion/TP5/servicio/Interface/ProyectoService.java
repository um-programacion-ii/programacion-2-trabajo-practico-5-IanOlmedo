package com.SistemaDeGestion.TP5.servicio.Interface;

import com.SistemaDeGestion.TP5.modelo.Proyecto;

import java.time.LocalDate;
import java.util.List;

public interface ProyectoService {
    Proyecto guardar(Proyecto proyecto);
    Proyecto buscarPorId(Long id);
    List<Proyecto> obtenerTodos();
    List<Proyecto> buscarProyectosActivos();
    Proyecto actualizar(Long id, Proyecto proyecto);
    void eliminar(Long id);
}
