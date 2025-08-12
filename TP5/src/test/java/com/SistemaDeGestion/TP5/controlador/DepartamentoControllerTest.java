package com.SistemaDeGestion.TP5.controlador;

import com.SistemaDeGestion.TP5.modelo.Departamento;
import com.SistemaDeGestion.TP5.servicio.Interface.DepartamentoService;
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

@WebMvcTest(DepartamentoController.class)
@ActiveProfiles("test")
class DepartamentoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DepartamentoService departamentoService;

    @Test
    void obtenerTodos_ok() throws Exception {
        // Stub con objetos vacíos para no suponer estructura
        Mockito.when(departamentoService.obtenerTodos()).thenReturn(List.of(new Departamento()));

        mockMvc.perform(get("/api/departamentos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void obtenerPorId_ok() throws Exception {
        Mockito.when(departamentoService.buscarPorId(10L)).thenReturn(new Departamento());

        mockMvc.perform(get("/api/departamentos/10"))
                .andExpect(status().isOk());
    }

    @Test
    void crear_ok() throws Exception {
        Mockito.when(departamentoService.guardar(any(Departamento.class))).thenReturn(new Departamento());

        mockMvc.perform(post("/api/departamentos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "nombre": "Sistemas"
                                }
                                """))
                .andExpect(status().isCreated());
    }

    @Test
    void actualizar_ok() throws Exception {
        Mockito.when(departamentoService.actualizar(Mockito.eq(3L), any(Departamento.class))).thenReturn(new Departamento());

        mockMvc.perform(put("/api/departamentos/3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "nombre": "RRHH"
                                }
                                """))
                .andExpect(status().isOk());
    }

    @Test
    void eliminar_noContent() throws Exception {
        mockMvc.perform(delete("/api/departamentos/9"))
                .andExpect(status().isNoContent());

        Mockito.verify(departamentoService, times(1)).eliminar(9L);
    }
}

