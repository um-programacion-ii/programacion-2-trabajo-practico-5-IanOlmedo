package com.SistemaDeGestion.TP5.controlador;

import com.SistemaDeGestion.TP5.modelo.Empleado;
import com.SistemaDeGestion.TP5.servicio.Interface.EmpleadoService;
import org.junit.jupiter.api.Test;
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
}
