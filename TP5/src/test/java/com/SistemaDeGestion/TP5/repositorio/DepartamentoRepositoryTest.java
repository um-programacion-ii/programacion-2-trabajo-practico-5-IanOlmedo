package com.SistemaDeGestion.TP5.repositorio;

import com.SistemaDeGestion.TP5.modelo.Departamento;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class DepartamentoRepositoryTest {

    @Autowired DepartamentoRepository departamentoRepository;

    @Test
    void findByNombre_debeRetornarCuandoExiste() {
        Departamento d = new Departamento();
        d.setNombre("RRHH");
        d.setDescripcion("Recursos Humanos");
        departamentoRepository.save(d);

        Optional<Departamento> hallado = departamentoRepository.findByNombre("RRHH");
        assertThat(hallado).isPresent();
        
    }
}
