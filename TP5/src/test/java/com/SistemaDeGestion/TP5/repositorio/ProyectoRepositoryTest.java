package com.SistemaDeGestion.TP5.repositorio;

import com.SistemaDeGestion.TP5.modelo.Proyecto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class ProyectoRepositoryTest {

    @Autowired ProyectoRepository proyectoRepository;

    @Test
    void findByFechaFinAfter_debeRetornarSoloActivos() {
        Proyecto activo = new Proyecto(null, "Activo", "Desc", LocalDate.now().minusDays(10), LocalDate.now().plusDays(10), null);
        Proyecto vencido = new Proyecto(null, "Vencido", "Desc", LocalDate.now().minusDays(20), LocalDate.now().minusDays(1), null);
        proyectoRepository.saveAll(List.of(activo, vencido));

        List<Proyecto> activos = proyectoRepository.findByFechaFinAfter(LocalDate.now());

        assertThat(activos).hasSize(1);
        assertThat(activos.get(0).getNombre()).isEqualTo("Activo");
    }
}
