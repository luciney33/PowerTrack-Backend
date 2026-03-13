package com.powertrack.backend.data.entity;

import com.powertrack.backend.common.Constantes;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = Constantes.TABLE_REGISTROS)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistroEntrenamientoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = Constantes.USUARIO_ID, nullable = false)
    private UsuarioEntity usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = Constantes.RUTINA_ID)
    private RutinaEntity rutina;

    @Column(nullable = false)
    private LocalDate fecha;

    @Column(length = 500)
    private String observaciones;

    @OneToMany(mappedBy = "registro", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RegistroDetalleEntity> detalles = new ArrayList<>();
}

