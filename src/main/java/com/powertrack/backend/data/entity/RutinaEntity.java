package com.powertrack.backend.data.entity;

import jakarta.persistence.*;
import lombok.*;
import com.powertrack.backend.common.Constantes;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = Constantes.TABLE_RUTINAS)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "rutinaEjercicios")
@ToString(exclude = "rutinaEjercicios")
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

    @OneToMany(mappedBy = "rutina", fetch = FetchType.EAGER)
    private List<RutinaEjercicioEntity> rutinaEjercicios = new ArrayList<>();
}
