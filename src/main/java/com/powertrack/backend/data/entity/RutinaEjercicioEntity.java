package com.powertrack.backend.data.entity;

import com.powertrack.backend.common.Constantes;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = Constantes.TABLE_RUTINA_EJERCICIOS)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RutinaEjercicioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = Constantes.RUTINA_ID)
    private RutinaEntity rutina;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = Constantes.EJERCICIO_ID)
    private EjercicioEntity ejercicio;

    private Integer series;

    private Integer repeticiones;

    @Column(name = "descanso_seg")
    private Integer descansoSeg;
}