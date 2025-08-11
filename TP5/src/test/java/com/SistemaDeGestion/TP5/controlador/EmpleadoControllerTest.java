package com.SistemaDeGestion.TP5.controlador;

import com.SistemaDeGestion.TP5.modelo.Empleado;
import com.SistemaDeGestion.TP5.servicio.Interface.EmpleadoService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmpleadoController.class)
@ActiveProfiles("test")
class EmpleadoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmpleadoService empleadoService;

    @Test
    void obtenerTodos_retornaLista() throws Exception {
        Empleado e = new Empleado(1L, "Juan", "Pérez", "jp@test.com", LocalDate.now(), BigDecimal.TEN, null, null);
        Mockito.when(empleadoService.obtenerTodos()).thenReturn(List.of(e));

        mockMvc.perform(get("/api/empleados"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void obtenerPorId_ok() throws Exception {
        Empleado e = new Empleado(5L, "Ana", "García", "a@test.com", LocalDate.now(), BigDecimal.valueOf(1234), null, null);
        Mockito.when(empleadoService.buscarPorId(5L)).thenReturn(e);

        mockMvc.perform(get("/api/empleados/5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(5L))
                .andExpect(jsonPath("$.nombre").value("Ana"));
    }

    @Test
    void crearEmpleado_ok() throws Exception {
        Empleado e = new Empleado(1L, "Juan", "Pérez", "jp@test.com", LocalDate.now(), BigDecimal.TEN, null, null);
        Mockito.when(empleadoService.guardar(any())).thenReturn(e);

        mockMvc.perform(post("/api/empleados")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "nombre":"Ian",
                                  "apellido":"Olmedo",
                                  "email":"ianito@test.com",
                                  "fechaContratacion":"2024-08-10",
                                  "salario": 100.00
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void actualizarEmpleado_ok() throws Exception {
        Empleado actualizado = new Empleado(2L, "Laura", "Suarez", "ls@test.com",
                LocalDate.parse("2023-05-10"), BigDecimal.valueOf(2000), null, null);
        Mockito.when(empleadoService.actualizar(Mockito.eq(2L), any(Empleado.class))).thenReturn(actualizado);

        mockMvc.perform(put("/api/empleados/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "nombre":"Laura",
                                  "apellido":"Suarez",
                                  "email":"ls@test.com",
                                  "fechaContratacion":"2023-05-10",
                                  "salario": 2000.00
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2L))
                .andExpect(jsonPath("$.nombre").value("Laura"));
    }

    @Test
    void eliminarEmpleado_noContent() throws Exception {
        mockMvc.perform(delete("/api/empleados/7"))
                .andExpect(status().isNoContent());

        Mockito.verify(empleadoService, times(1)).eliminar(7L);
    }

    @Test
    void buscarPorDepartamento_ok() throws Exception {
        Empleado e1 = new Empleado(1L, "A", "B", "a@test.com", LocalDate.now(), BigDecimal.TEN, null, null);
        Empleado e2 = new Empleado(2L, "C", "D", "c@test.com", LocalDate.now(), BigDecimal.ONE, null, null);
        Mockito.when(empleadoService.buscarPorDepartamento("Sistemas")).thenReturn(List.of(e1, e2));

        mockMvc.perform(get("/api/empleados/departamento/Sistemas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void buscarPorRangoSalario_ok() throws Exception {
        Empleado e = new Empleado(3L, "X", "Y", "x@test.com", LocalDate.now(), BigDecimal.valueOf(1500), null, null);
        Mockito.when(empleadoService.buscarPorRangoSalario(BigDecimal.valueOf(1000), BigDecimal.valueOf(2000)))
                .thenReturn(List.of(e));

        mockMvc.perform(get("/api/empleados/salario")
                        .param("min", "1000")
                        .param("max", "2000"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }
}
