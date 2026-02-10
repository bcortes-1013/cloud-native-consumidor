package com.cloud.ms_consumidor.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "horario_ruta") // Nombre exacto de tu tabla en Oracle
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HorarioRuta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "patente", nullable = false)
    private String patente;

    @Column(name = "numero_ruta", nullable = false)
    private String numeroRuta;

    @Column(name = "nombre_ruta", nullable = false)
    private String nombreRuta;

    @Column(name = "hora_inicio", nullable = false)
    private String horaInicio;

    @Column(name = "hora_fin", nullable = false)
    private String horaFin;

    @Column(name = "frecuencia_minutos", nullable = false)
    private Integer frecuenciaMinutos;

    @Column(name = "estado", nullable = false)
    private String estado;

    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;

    // Esto se ejecuta automáticamente antes de guardar en BD para poner la fecha actual
    @PrePersist
    public void prePersist() {
        this.fechaActualizacion = LocalDateTime.now();
    }
}