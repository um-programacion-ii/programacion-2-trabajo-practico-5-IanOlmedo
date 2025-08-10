package com.SistemaDeGestion.TP5.servicio;

import com.SistemaDeGestion.TP5.modelo.Empleado;
import com.SistemaDeGestion.TP5.repositorio.DepartamentoRepository;
import com.SistemaDeGestion.TP5.repositorio.EmpleadoRepository;
import com.SistemaDeGestion.TP5.servicio.Implementacion.EmpleadoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmpleadoServiceImplTest {

    @Mock EmpleadoRepository empleadoRepository;
    @Mock DepartamentoRepository departamentoRepository;

    @InjectMocks EmpleadoServiceImpl service;

    private Empleado empleado;

    @BeforeEach
    void init() {
        empleado = new Empleado();
        empleado.setNombre("Ana");
        empleado.setApellido("García");
        empleado.setEmail("ana@empresa.com");
        empleado.setFechaContratacion(LocalDate.now());
        empleado.setSalario(new BigDecimal("55000"));
    }

    @Test
    void guardar_conSalarioNegativo_debeLanzarExcepcion() {
        empleado.setSalario(new BigDecimal("-1"));
        assertThatThrownBy(() -> service.guardar(empleado))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("salario");
        verifyNoInteractions(empleadoRepository);
    }

    @Test
    void guardar_ok_debePersistirEmpleado() {
        when(empleadoRepository.save(any())).thenAnswer(inv -> {
            Empleado e = inv.getArgument(0);
            e.setId(1L);
            return e;
        });

        Empleado result = service.guardar(empleado);

        assertThat(result.getId()).isEqualTo(1L);
        ArgumentCaptor<Empleado> captor = ArgumentCaptor.forClass(Empleado.class);
        verify(empleadoRepository).save(captor.capture());
        assertThat(captor.getValue().getEmail()).isEqualTo("ana@empresa.com");
    }

    @Test
    void buscarPorId_noExiste_debeLanzarExcepcion() {
        when(empleadoRepository.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.buscarPorId(99L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("no encontrado");
    }
}
