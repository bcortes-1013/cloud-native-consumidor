package com.cloud.ms_consumidor.repository;

import com.cloud.ms_consumidor.model.HorarioRuta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HorarioRutaRepository extends JpaRepository<HorarioRuta, Long> {
    // Aquí puedes agregar métodos de búsqueda personalizados si necesitas después
}