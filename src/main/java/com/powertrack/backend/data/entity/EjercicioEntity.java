package com.powertrack.backend.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.powertrack.backend.common.Constantes;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = Constantes.TABLE_EJERCICIOS)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "rutinas")
@ToString(exclude = "rutinas")
public class EjercicioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(name = Constantes.TIPO_ENTRENAMIENTO, nullable = false)
    private String tipoEntrenamiento;

    @Column(name = Constantes.IMAGEN_URL)
    private String imagenUrl;

    @Column
    private String descripcion;

    @ManyToMany(mappedBy = Constantes.EJERCICIOS)
    @JsonIgnore
    private Set<RutinaEntity> entrenamientos = new HashSet<>();
}

