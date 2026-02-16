package com.cloud.ms_consumidor.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "UBICACION_BUS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UbicacionBus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ID_BUS")
    private String idBus;

    private String patente;
    private Double latitud;
    private Double longitud;
    
    @Column(name = "VELOCIDAD")
    private Integer velocidadKmH;

    @Column(name = "FECHA_HORA")
    private String timestamp;
}