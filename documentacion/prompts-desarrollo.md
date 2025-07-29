##Prompt Base

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
