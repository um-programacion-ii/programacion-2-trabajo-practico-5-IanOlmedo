package com.SistemaDeGestion.TP5.servicio;

import com.SistemaDeGestion.TP5.modelo.Departamento;
import com.SistemaDeGestion.TP5.repositorio.DepartamentoRepository;
import com.SistemaDeGestion.TP5.servicio.Implementacion.DepartamentoServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DepartamentoServiceImplTest {

    @Mock DepartamentoRepository repo;
    @InjectMocks DepartamentoServiceImpl service;

    @Test
    void guardar_ok() {
        Departamento d = new Departamento();
        d.setNombre("IT");
        when(repo.save(d)).thenReturn(d);

        Departamento out = service.guardar(d);
        assertThat(out.getNombre()).isEqualTo("IT");
        verify(repo).save(d);
    }

    @Test
    void buscarPorId_inexistente_lanzaExcepcion() {
        when(repo.findById(1L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.buscarPorId(1L))
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    void buscarPorNombre_ok() {
        Departamento d = new Departamento();
        d.setNombre("RRHH");
        when(repo.findByNombre("RRHH")).thenReturn(Optional.of(d));

        Departamento out = service.buscarPorNombre("RRHH");
        assertThat(out.getNombre()).isEqualTo("RRHH");
    }

    @Test
    void obtenerTodos_lista() {
        when(repo.findAll()).thenReturn(List.of(new Departamento()));
        assertThat(service.obtenerTodos()).hasSize(1);
    }

    @Test
    void actualizar_inexistente_lanzaExcepcion() {
        when(repo.existsById(2L)).thenReturn(false);
        assertThatThrownBy(() -> service.actualizar(2L, new Departamento()))
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    void eliminar_ok() {
        when(repo.existsById(5L)).thenReturn(true);
        service.eliminar(5L);
        verify(repo).deleteById(5L);
    }
}
