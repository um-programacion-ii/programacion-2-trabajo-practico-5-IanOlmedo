package com.SistemaDeGestion.TP5.repositorio;

import com.SistemaDeGestion.TP5.modelo.Proyecto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ProyectoRepository extends JpaRepository<Proyecto, Long> {

    List<Proyecto> findByFechaFinAfter(LocalDate fecha);  // proyectos activos
}
