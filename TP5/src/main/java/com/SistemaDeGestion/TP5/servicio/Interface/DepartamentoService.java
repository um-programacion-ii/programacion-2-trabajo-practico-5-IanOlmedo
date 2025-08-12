package com.SistemaDeGestion.TP5.servicio.Interface;

import com.SistemaDeGestion.TP5.modelo.Departamento;

import java.util.List;

public interface DepartamentoService {
    Departamento guardar(Departamento departamento);
    Departamento buscarPorId(Long id);
    Departamento buscarPorNombre(String nombre);
    List<Departamento> obtenerTodos();
    Departamento actualizar(Long id, Departamento departamento);
    void eliminar(Long id);
}
