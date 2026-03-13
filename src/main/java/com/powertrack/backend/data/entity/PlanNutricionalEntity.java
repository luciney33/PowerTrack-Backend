package com.powertrack.backend.data.entity;

import jakarta.persistence.*;
import lombok.*;
import com.powertrack.backend.common.Constantes;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = Constantes.TABLE_PLANES)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlanNutricionalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(length = 1000)
    private String descripcion;

    @Column(nullable = false)
    private Integer tipo;

    @Column(name = "calorias_objetivo")
    private Integer caloriasObjetivo;

    @Column(name = "proteinas_objetivo")
    private Double proteinasObjetivo;

    @Column(name = "carbohidratos_objetivo")
    private Double carbohidratosObjetivo;

    @Column(name = "grasas_objetivo")
    private Double grasasObjetivo;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = Constantes.TABLE_PLAN_COMIDAS,
            joinColumns = @JoinColumn(name = Constantes.PLAN_ID),
            inverseJoinColumns = @JoinColumn(name = Constantes.COMIDA_ID)
    )
    private Set<ComidaEntity> comidas = new HashSet<>();
}
