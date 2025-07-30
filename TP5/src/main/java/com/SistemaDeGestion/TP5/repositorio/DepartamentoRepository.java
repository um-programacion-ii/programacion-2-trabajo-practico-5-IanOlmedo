package com.SistemaDeGestion.TP5.repositorio;

import com.SistemaDeGestion.TP5.modelo.Departamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DepartamentoRepository extends JpaRepository<Departamento, Long> {
    Optional<Departamento> findByNombre(String nombre);
}
