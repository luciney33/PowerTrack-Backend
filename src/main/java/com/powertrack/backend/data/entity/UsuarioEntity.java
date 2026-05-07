package com.powertrack.backend.data.entity;

import com.powertrack.backend.domain.model.Rol;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.powertrack.backend.common.Constantes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = Constantes.TABLE_USUARIOS)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String username;
    @Column
    private String password;
    @Column
    private String email;
    @Column
    private String nombre;
    @Column
    private boolean activo;
    @Column
    private String codigoActivacion;
    @Column
    private LocalDateTime expiracionCodigo;

    @Enumerated(EnumType.STRING)
    private Rol rol;

    private Integer genero;

    private Integer edad;

    private Integer objetivo;

    private Integer nivel;

    @Column(name = "dias_entrenamiento")
    private Integer diasEntrenamiento;

    private Integer lesion;

    private Integer preferencia;

    private Integer recomendacion;

    @Column(name = "peso_cat")
    private Integer pesoCat;

    @Column(name = "formulario_completado", nullable = false)
    private boolean formularioCompletado = false;

    @Column(columnDefinition = "TEXT")
    private String descripcionRutina;

    @Column(columnDefinition = "TEXT")
    private String consejosNutricion;
}
