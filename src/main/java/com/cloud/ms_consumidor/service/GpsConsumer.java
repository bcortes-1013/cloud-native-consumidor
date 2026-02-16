package com.cloud.ms_consumidor.service;

import com.cloud.ms_consumidor.model.UbicacionBus;
import com.cloud.ms_consumidor.repository.UbicacionBusRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel; // Importar para el ACK
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders; // Importar para los Headers
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class GpsConsumer {

    private final Logger log = LoggerFactory.getLogger(GpsConsumer.class);

    @Autowired
    private UbicacionBusRepository repository;

    private final ObjectMapper mapper = new ObjectMapper();

    // Configurado con ACK Manual
    @RabbitListener(queues = "cola-gps", ackMode = "MANUAL")
    public void recibirGps(String mensajeJson, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) {
        log.info(">>> [GPS] Mensaje recibido: {}", mensajeJson);

        try {
            // 1. Convertir JSON a Entidad Java
            UbicacionBus bus = mapper.readValue(mensajeJson, UbicacionBus.class);

            // 2. Guardar en Oracle (Pauta consumidor 1)
            UbicacionBus guardado = repository.save(bus);
            
            log.info(">>> [ORACLE] Ubicación guardada. ID: {}, Patente: {}", guardado.getId(), guardado.getPatente());

            // 3. Confirmar a RabbitMQ (ACK)
            // Solo si llegó aquí, el mensaje se borra de la cola
            channel.basicAck(tag, false);

        } catch (Exception e) {
            log.error(">>> [ERROR] Falló el guardado en BD: {}", e.getMessage());
            
            // 4. Manejo de Error (NACK)
            // Si falla la BD, rechazamos el mensaje (requeue=false para no bloquear, o true para reintentar)
            try {
                // Se envía a false para descartarlo y que no cicle infinitamente
                channel.basicNack(tag, false, false); 
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}