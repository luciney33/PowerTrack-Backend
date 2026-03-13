package com.powertrack.backend.data.entity;

import jakarta.persistence.*;
import lombok.*;
import com.powertrack.backend.common.Constantes;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = Constantes.TABLE_RUTINAS)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "ejercicios")
@ToString(exclude = "ejercicios")
public class RutinaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String nombre;
    @Column
    private String descripcion;

    @Column(nullable = false)
    private Integer tipo;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = Constantes.TABLE_RUTINA_EJERCICIOS,
            joinColumns = @JoinColumn(name = Constantes.RUTINA_ID),
            inverseJoinColumns = @JoinColumn(name = Constantes.EJERCICIO_ID)
    )
    private Set<EjercicioEntity> ejercicios = new HashSet<>();
}
