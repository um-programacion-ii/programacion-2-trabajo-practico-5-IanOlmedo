package com.SistemaDeGestion.TP5.repositorio;

import com.SistemaDeGestion.TP5.modelo.Departamento;
import com.SistemaDeGestion.TP5.modelo.Empleado;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class EmpleadoRepositoryTest {

    @Autowired EmpleadoRepository empleadoRepository;
    @Autowired DepartamentoRepository departamentoRepository;

    private Departamento it;

    @BeforeEach
    void setup() {
        it = new Departamento();
        it.setNombre("IT");
        it.setDescripcion("Tecnología");
        it = departamentoRepository.save(it);
        empleadoRepository.deleteAll();
    }

    private Empleado nuevoEmpleado(String email, BigDecimal salario) {
        Empleado e = new Empleado();
        e.setNombre("Nombre");
        e.setApellido("Apellido");
        e.setEmail(email);
        e.setFechaContratacion(LocalDate.now());
        e.setSalario(salario);
        e.setDepartamento(it);
        return e;
    }

    @Test
    void findByEmail_debeRetornarEmpleadoCuandoExiste() {
        Empleado guardado = empleadoRepository.save(nuevoEmpleado("juan@empresa.com", new BigDecimal("50000")));
        Optional<Empleado> hallado = empleadoRepository.findByEmail("juan@empresa.com");
        assertThat(hallado).isPresent();
        assertThat(hallado.get().getId()).isEqualTo(guardado.getId());
    }

    @Test
    void findByNombreDepartamento_debeListarEmpleadosDelDepto() {
        empleadoRepository.save(nuevoEmpleado("a@empresa.com", new BigDecimal("40000")));
        empleadoRepository.save(nuevoEmpleado("b@empresa.com", new BigDecimal("60000")));
        List<Empleado> lista = empleadoRepository.findByNombreDepartamento("IT");
        assertThat(lista).hasSize(2);
        assertThat(lista).allMatch(e -> e.getDepartamento().getNombre().equals("IT"));
    }

    @Test
    void findBySalarioBetween_debeFiltrarPorRango() {
        empleadoRepository.save(nuevoEmpleado("a@empresa.com", new BigDecimal("30000")));
        empleadoRepository.save(nuevoEmpleado("b@empresa.com", new BigDecimal("50000")));
        empleadoRepository.save(nuevoEmpleado("c@empresa.com", new BigDecimal("80000")));
        List<Empleado> lista = empleadoRepository.findBySalarioBetween(new BigDecimal("40000"), new BigDecimal("70000"));
        assertThat(lista).extracting(Empleado::getEmail).containsExactlyInAnyOrder("b@empresa.com");
    }
}

