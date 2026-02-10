package com.cloud.ms_consumidor.service; // Ajusta el paquete si es necesario

import com.cloud.ms_consumidor.model.HorarioRuta;
import com.cloud.ms_consumidor.repository.HorarioRutaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class HorarioRutaConsumer {

    private final Logger log = LoggerFactory.getLogger(HorarioRutaConsumer.class);

    @Autowired
    private HorarioRutaRepository repository;

    private final ObjectMapper mapper = new ObjectMapper();

    // Nombre de la carpeta donde se guardarán los reportes
    private static final String CARPETA_REPORTES = "reportes_generados";

    @RabbitListener(queues = "cola-ruta")
    public void recibir(String mensajeJson) {
        log.info(">>> MENSAJE RECIBIDO: {}", mensajeJson);

        try {
            // --- PASO 1: Guardar en Base de Datos Oracle ---
            HorarioRuta ruta = mapper.readValue(mensajeJson, HorarioRuta.class);
            HorarioRuta guardado = repository.save(ruta);
            log.info(">>> [BD] Ruta guardada en Oracle con ID: {}", guardado.getId());

            // --- PASO 2: Guardar el JSON en un archivo físico ---
            guardarArchivoJson(mensajeJson, ruta.getPatente());

        } catch (Exception e) {
            log.error(">>> [ERROR] Falló el procesamiento del mensaje: {}", e.getMessage());
            e.printStackTrace();
        }
    }

    private void guardarArchivoJson(String contenido, String patente) {
        try {
            // 1. Verificar si existe la carpeta, si no, crearla
            File directorio = new File(CARPETA_REPORTES);
            if (!directorio.exists()) {
                if (directorio.mkdirs()) {
                    log.info(">>> [ARCHIVO] Carpeta creada: {}", CARPETA_REPORTES);
                }
            }

            // 2. Crear nombre único (ej: reporte_PATENTE_123456789.json)
            String nombreArchivo = "reporte_" + patente + "_" + System.currentTimeMillis() + ".json";
            Path rutaArchivo = Paths.get(CARPETA_REPORTES, nombreArchivo);

            // 3. Escribir el archivo
            Files.write(rutaArchivo, contenido.getBytes());

            log.info(">>> [ARCHIVO] JSON generado exitosamente: {}", rutaArchivo.toAbsolutePath());

        } catch (IOException e) {
            log.error(">>> [ERROR ARCHIVO] No se pudo guardar el JSON: {}", e.getMessage());
        }
    }
}