package com.cloud.ms_consumidor.repository;

import com.cloud.ms_consumidor.model.UbicacionBus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UbicacionBusRepository extends JpaRepository<UbicacionBus, Long>{
  
}
