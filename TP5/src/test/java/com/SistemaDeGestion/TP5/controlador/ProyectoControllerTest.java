package com.SistemaDeGestion.TP5.controlador;

import com.SistemaDeGestion.TP5.modelo.Proyecto;
import com.SistemaDeGestion.TP5.servicio.Interface.ProyectoService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProyectoController.class)
@ActiveProfiles("test")
class ProyectoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProyectoService proyectoService;

    @Test
    void obtenerTodos_ok() throws Exception {
        Mockito.when(proyectoService.obtenerTodos()).thenReturn(List.of(new Proyecto()));

        mockMvc.perform(get("/api/proyectos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void obtenerPorId_ok() throws Exception {
        Mockito.when(proyectoService.buscarPorId(4L)).thenReturn(new Proyecto());

        mockMvc.perform(get("/api/proyectos/4"))
                .andExpect(status().isOk());
    }

    @Test
    void crear_ok() throws Exception {
        Mockito.when(proyectoService.guardar(any(Proyecto.class))).thenReturn(new Proyecto());

        mockMvc.perform(post("/api/proyectos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "nombre":"TP Integrador",
                                  "activo": true
                                }
                                """))
                .andExpect(status().isCreated());
    }

    @Test
    void actualizar_ok() throws Exception {
        Mockito.when(proyectoService.actualizar(Mockito.eq(8L), any(Proyecto.class))).thenReturn(new Proyecto());

        mockMvc.perform(put("/api/proyectos/8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "nombre":"TP Final",
                                  "activo": false
                                }
                                """))
                .andExpect(status().isOk());
    }

    @Test
    void eliminar_noContent() throws Exception {
        mockMvc.perform(delete("/api/proyectos/12"))
                .andExpect(status().isNoContent());

        Mockito.verify(proyectoService, times(1)).eliminar(12L);
    }

    @Test
    void buscarActivos_ok() throws Exception {
        Mockito.when(proyectoService.buscarProyectosActivos()).thenReturn(List.of(new Proyecto(), new Proyecto()));

        mockMvc.perform(get("/api/proyectos/activos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }
}
