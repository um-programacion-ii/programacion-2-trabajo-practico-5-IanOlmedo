## Prompt Base para saber crear bien las dependencias con JPA

Bien, quiero que tengas en cuenta el archivo .md que adjunte para que tengas en cuenta las consignas de este trabajo practico. A partir de ellas vamos a resolver lo que te vaya pidiendo y las dudas que surgen seran consultadas. La idea principal es que cada cosa que vayamos haciendo me ayudes a hacer la primer ejemplo para despues poder continuar con las demas por mi cuenta y que la que la hagas bien explicada asi puedo comprender de manera mas eficiente. Comencemos por las primeras clases de la Etapa 1 (Empleado).

Perfecto, vamos a construir la clase Empleado paso a paso con explicaciones claras para que entiendas cómo y por qué se hace cada cosa.

🧱 Clase Empleado – Paso a Paso con Explicaciones
📌 1. 📁 Ubicación del archivo
Creá la clase en el siguiente paquete (estructura típica en Spring Boot):

swift
Copiar
Editar
src/main/java/com/sistemadegestion/modelo/Empleado.java
📌 2. 📦 Paquetes y librerías necesarias
java
Copiar
Editar
package com.sistemadegestion.modelo;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
📌 3. 🔧 Anotaciones de la clase
java
Copiar
Editar
@Entity  // Indica que esta clase es una entidad JPA (se mapea a una tabla de la base de datos)
@Table(name = "empleados")  // Define el nombre de la tabla
@Data  // Lombok: genera getters, setters, toString, hashCode, equals
@NoArgsConstructor  // Constructor sin parámetros
@AllArgsConstructor  // Constructor con todos los parámetros
public class Empleado {
📌 4. 🔑 Atributos y explicaciones
java
Copiar
Editar
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Autoincremental en la BD
    private Long id;
java
Copiar
Editar
    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, length = 100)
    private String apellido;
java
Copiar
Editar
    @Column(unique = true, nullable = false)
    private String email;
java
Copiar
Editar
    @Column(name = "fecha_contratacion", nullable = false)
    private LocalDate fechaContratacion;
java
Copiar
Editar
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal salario;
📌 5. 🔗 Relaciones con otras entidades
✅ Muchos empleados pertenecen a un mismo departamento (ManyToOne)
java
Copiar
Editar
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "departamento_id")  // FK en la tabla empleados
    private Departamento departamento;
✅ Muchos empleados pueden participar en muchos proyectos (ManyToMany)
java
Copiar
Editar
    @ManyToMany
    @JoinTable(
        name = "empleado_proyecto",  // tabla intermedia
        joinColumns = @JoinColumn(name = "empleado_id"),
        inverseJoinColumns = @JoinColumn(name = "proyecto_id")
    )
    private Set<Proyecto> proyectos = new HashSet<>();
}
✅ Resultado Final: Clase completa Empleado
¿Querés que te la genere ahora como archivo completo para que copies directamente? ¿O querés que practiquemos escribiéndola juntos y me vayas preguntando?

📘 También puedo proponerte después ejercicios simples para verificar que entendiste bien las relaciones JPA si querés reforzar.

¿Qué preferís?

Este Prompt fue hecho con la finalidad de ver como era la estructura de las tablas y para tener en cuenta un boceto de como deberian ser las relaciones.

## Prompt para tener una recordar la composicion y las dependencias a importar en los repositories

Avancemos con la composicion de lo repositories, quiero que me des las dependencias y unos ejemplos para empleados y yo continuare con los que falten.

Vamos a trabajar paso a paso. El primer subpaso es:

🔹 Paso 1: Crear los Repositories
Perfecto Ian, vamos a generar las interfaces en com.SistemaDeGestion.TP5.repositorio:

📁 EmpleadoRepository.java
java
Copiar
Editar
package com.SistemaDeGestion.TP5.repositorio;

import com.SistemaDeGestion.TP5.modelo.Empleado;
import com.SistemaDeGestion.TP5.modelo.Departamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {

    Optional<Empleado> findByEmail(String email);

    List<Empleado> findByDepartamento(Departamento departamento);

    List<Empleado> findBySalarioBetween(BigDecimal salarioMin, BigDecimal salarioMax);

    List<Empleado> findByFechaContratacionAfter(LocalDate fecha);

    @Query("SELECT e FROM Empleado e WHERE e.departamento.nombre = :nombreDepartamento")
    List<Empleado> findByNombreDepartamento(@Param("nombreDepartamento") String nombreDepartamento);

    @Query("SELECT AVG(e.salario) FROM Empleado e WHERE e.departamento.id = :departamentoId")
    Optional<BigDecimal> findAverageSalarioByDepartamento(@Param("departamentoId") Long departamentoId);
}

¿Querés que ahora avancemos con los Services (interfaces + implementación) para cada entidad?


## Prompt para Service

Quiero que me pases la primera parte de Services, ya organice 2 carpetas dentro de servicio, por un lado Implementacion y por otro Interfaces. 
Hagamos el primer caso juntos y despues continuo yo. 

Perfecto, Ian. Vamos con la parte de Services para la Etapa 2. Siguiendo la arquitectura multicapa, vamos a generar:

Las interfaces del servicio: EmpleadoService, DepartamentoService, ProyectoService

Las implementaciones: EmpleadoServiceImpl, DepartamentoServiceImpl, ProyectoServiceImpl

🧩 1. Interface EmpleadoService
java
Copiar
Editar
package com.SistemaDeGestion.TP5.servicio;

import com.SistemaDeGestion.TP5.modelo.Empleado;

import java.math.BigDecimal;
import java.util.List;

public interface EmpleadoService {

    Empleado guardar(Empleado empleado);
    Empleado buscarPorId(Long id);
    List<Empleado> buscarPorDepartamento(String nombreDepartamento);
    List<Empleado> buscarPorRangoSalario(BigDecimal salarioMin, BigDecimal salarioMax);
    BigDecimal obtenerSalarioPromedioPorDepartamento(Long departamentoId);
    List<Empleado> obtenerTodos();
    Empleado actualizar(Long id, Empleado empleado);
    void eliminar(Long id);
}
✅ 2. Implementación EmpleadoServiceImpl
java
Copiar
Editar
package com.SistemaDeGestion.TP5.servicio.impl;

import com.SistemaDeGestion.TP5.modelo.Departamento;
import com.SistemaDeGestion.TP5.modelo.Empleado;
import com.SistemaDeGestion.TP5.repositorio.DepartamentoRepository;
import com.SistemaDeGestion.TP5.repositorio.EmpleadoRepository;
import com.SistemaDeGestion.TP5.servicio.EmpleadoService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class EmpleadoServiceImpl implements EmpleadoService {

    private final EmpleadoRepository empleadoRepository;
    private final DepartamentoRepository departamentoRepository;

    public EmpleadoServiceImpl(EmpleadoRepository empleadoRepository,
                               DepartamentoRepository departamentoRepository) {
        this.empleadoRepository = empleadoRepository;
        this.departamentoRepository = departamentoRepository;
    }

    @Override
    public Empleado guardar(Empleado empleado) {
        if (empleado.getSalario().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("El salario no puede ser negativo");
        }
        return empleadoRepository.save(empleado);
    }

    @Override
    public Empleado buscarPorId(Long id) {
        return empleadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado con ID: " + id));
    }

    @Override
    public List<Empleado> buscarPorDepartamento(String nombreDepartamento) {
        return empleadoRepository.findByNombreDepartamento(nombreDepartamento);
    }

    @Override
    public List<Empleado> buscarPorRangoSalario(BigDecimal salarioMin, BigDecimal salarioMax) {
        return empleadoRepository.findBySalarioBetween(salarioMin, salarioMax);
    }

    @Override
    public BigDecimal obtenerSalarioPromedioPorDepartamento(Long departamentoId) {
        return empleadoRepository.findAverageSalarioByDepartamento(departamentoId)
                .orElse(BigDecimal.ZERO);
    }

    @Override
    public List<Empleado> obtenerTodos() {
        return empleadoRepository.findAll();
    }

    @Override
    public Empleado actualizar(Long id, Empleado empleado) {
        if (!empleadoRepository.existsById(id)) {
            throw new RuntimeException("Empleado no encontrado con ID: " + id);
        }
        empleado.setId(id);
        return empleadoRepository.save(empleado);
    }

    @Override
    public void eliminar(Long id) {
        if (!empleadoRepository.existsById(id)) {
            throw new RuntimeException("Empleado no encontrado con ID: " + id);
        }
        empleadoRepository.deleteById(id);
    }
}
¿Querés que ahora te pase los services de Departamento y Proyecto, o preferís avanzar con el Controller de Empleado para ir viendo cómo se conectan las capas?

Este prompt más que nada se utilizó para tener en cuenta como era la estructura de las implementaciones.


## Prompt para hacer un ejemplo del controller de empleado

Avancemos con el primer ejemplo de Controllers Rest, sigamos con la dinamica con la que venimos trabajando.

¡MUY BIEN, Ian! Veamos como avanzar con los Controladores...

✅ Estructura sugerida
Crearemos los siguientes controladores en el paquete:
com.SistemaDeGestion.TP5.controlador

🟦 EmpleadoController
java
package com.SistemaDeGestion.TP5.controlador;

import com.SistemaDeGestion.TP5.modelo.Empleado;
import com.SistemaDeGestion.TP5.servicio.Interface.EmpleadoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/empleados")
public class EmpleadoController {

    private final EmpleadoService empleadoService;

    public EmpleadoController(EmpleadoService empleadoService) {
        this.empleadoService = empleadoService;
    }

    @GetMapping
    public List<Empleado> obtenerTodos() {
        return empleadoService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public Empleado obtenerPorId(@PathVariable Long id) {
        return empleadoService.buscarPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Empleado crear(@RequestBody Empleado empleado) {
        return empleadoService.guardar(empleado);
    }

    @PutMapping("/{id}")
    public Empleado actualizar(@PathVariable Long id, @RequestBody Empleado empleado) {
        return empleadoService.actualizar(id, empleado);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        empleadoService.eliminar(id);
    }

    @GetMapping("/departamento/{nombre}")
    public List<Empleado> buscarPorDepartamento(@PathVariable String nombre) {
        return empleadoService.buscarPorDepartamento(nombre);
    }

    @GetMapping("/salario")
    public List<Empleado> buscarPorRangoSalario(@RequestParam BigDecimal min, @RequestParam BigDecimal max) {
        return empleadoService.buscarPorRangoSalario(min, max);
    }
}

¿Querés que ahora avancemos con la configuración de los application.yml para los distintos profiles (dev, mysql, postgres) o pasamos directamente a los tests (@DataJpaTest, @WebMvcTest, etc.)?

