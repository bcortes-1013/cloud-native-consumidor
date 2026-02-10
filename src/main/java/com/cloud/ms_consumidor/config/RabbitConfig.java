package com.cloud.ms_consumidor.config; // Ajusta a tu paquete real

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Bean
    public Queue cola() {
        // "true" significa que la cola es duradera (no se borra si Rabbit se reinicia)
        return new Queue("cola-ruta", true);
    }
}