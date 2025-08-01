package com.SistemaDeGestion.TP5.modelo;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Entity  // Indica que esta clase es una entidad JPA (se mapea a una tabla de la base de datos)
@Table(name = "empleados")  // Define el nombre de la tabla
@Data  // Lombok: genera getters, setters, toString, hashCode, equals
@NoArgsConstructor  // Constructor sin parámetros
@AllArgsConstructor  // Constructor con todos los parámetros
public class Empleado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Autoincremental en la BD
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, length = 100)
    private String apellido;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(name = "fecha_contratacion", nullable = false)
    private LocalDate fechaContratacion;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal salario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "departamento_id")  // FK en la tabla empleados
    private Departamento departamento;

    @ManyToMany
    @JoinTable(
            name = "empleado_proyecto",  // tabla intermedia
            joinColumns = @JoinColumn(name = "empleado_id"),
            inverseJoinColumns = @JoinColumn(name = "proyecto_id")
    )
    private Set<Proyecto> proyectos = new HashSet<>();




}
