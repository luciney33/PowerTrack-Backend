package com.powertrack.backend.data.entity;

import jakarta.persistence.*;
import lombok.*;
import com.powertrack.backend.common.Constantes;

@Entity
@Table(name = Constantes.TABLE_COMIDAS)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ComidaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private Integer calorias;

    @Column(nullable = false)
    private Double proteinas;

    @Column(nullable = false)
    private Double carbohidratos;

    @Column(nullable = false)
    private Double grasas;

    @Column(nullable = false, length = 20)
    private String categoria;

    @Column(name = "imagen_url")
    private String imagenUrl;
}
