package com.SistemaDeGestion.TP5.servicio;

import com.SistemaDeGestion.TP5.modelo.Proyecto;
import com.SistemaDeGestion.TP5.repositorio.ProyectoRepository;
import com.SistemaDeGestion.TP5.servicio.Implementacion.ProyectoServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProyectoServiceImplTest {

    @Mock ProyectoRepository repo;
    @InjectMocks ProyectoServiceImpl service;

    @Test
    void guardar_ok() {
        Proyecto p = new Proyecto();
        p.setNombre("Sistema X");
        when(repo.save(p)).thenReturn(p);

        Proyecto out = service.guardar(p);
        assertThat(out.getNombre()).isEqualTo("Sistema X");
    }

    @Test
    void buscarPorId_inexistente_lanzaExcepcion() {
        when(repo.findById(10L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.buscarPorId(10L))
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    void obtenerTodos_lista() {
        when(repo.findAll()).thenReturn(List.of(new Proyecto()));
        assertThat(service.obtenerTodos()).hasSize(1);
    }

    @Test
    void buscarProyectosActivos_ok() {
        when(repo.findByFechaFinAfter(any(LocalDate.class))).thenReturn(List.of(new Proyecto()));
        assertThat(service.buscarProyectosActivos()).hasSize(1);
        verify(repo).findByFechaFinAfter(any(LocalDate.class));
    }

    @Test
    void actualizar_inexistente_lanzaExcepcion() {
        when(repo.existsById(3L)).thenReturn(false);
        assertThatThrownBy(() -> service.actualizar(3L, new Proyecto()))
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    void eliminar_ok() {
        when(repo.existsById(4L)).thenReturn(true);
        service.eliminar(4L);
        verify(repo).deleteById(4L);
    }
}
