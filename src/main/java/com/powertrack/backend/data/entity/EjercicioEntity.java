package com.powertrack.backend.data.entity;

import com.powertrack.backend.common.Constantes;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = Constantes.TABLE_EJERCICIOS)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
}