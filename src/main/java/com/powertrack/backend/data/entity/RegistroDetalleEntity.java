package com.powertrack.backend.data.entity;

import jakarta.persistence.*;
import lombok.*;
import com.powertrack.backend.common.Constantes;

@Entity
@Table(name = Constantes.TABLE_REGISTROS_DETALLE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistroDetalleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = Constantes.REGISTRO_ID, nullable = false)
    private RegistroEntrenamientoEntity registro;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = Constantes.EJERCICIO_ID, nullable = false)
    private EjercicioEntity ejercicio;

    @Column(nullable = true)
    private Integer series;

    @Column(nullable = true)
    private Integer repeticiones;

    @Column(nullable = true)
    private Double peso;

    @Column(nullable = true)
    private Integer duracionMinutos;

    @Column(nullable = true)
    private Double velocidad;

    @Column(nullable = true)
    private Double inclinacion;

    @Column(nullable = true)
    private Integer kcalGastadas;
}
