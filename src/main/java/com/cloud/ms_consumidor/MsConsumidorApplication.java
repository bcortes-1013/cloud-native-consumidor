package com.cloud.ms_consumidor;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MsConsumidorApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsConsumidorApplication.class, args);
    }

    // Escucha la cola 'cola-gps'
    @RabbitListener(queues = "cola-gps")
    public void recibir(String mensaje) {
        System.out.println(">>> MENSAJE RECIBIDO: " + mensaje);
    }
}