# ☁️ Microservicio Consumidor: Persistencia Oracle

Este servicio es el encargado de guardar el historial de recorridos. Procesa las coordenadas GPS y las inserta en la base de datos en la nube.

## 📌 Funcionalidad Base
* **Rol:** Consumidor (Listener).
* **Entrada:** Lee mensajes de la cola `cola-gps`.
* **Salida:** Inserta registros en la tabla `UBICACION_BUS` en Oracle Cloud.
* **Seguridad:** Utiliza Oracle Wallet para conexión segura (mTLS).

## 🛠️ Stack Tecnológico
* **Java 17 & Spring Boot**
* **Spring Data JPA:** Para la persistencia de datos.
* **Oracle JDBC Driver:** Conexión a Autonomous Database.