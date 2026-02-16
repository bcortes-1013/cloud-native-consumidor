# ---------- ETAPA 1: CONSTRUCCIÓN (BUILD) ----------
# Usamos una imagen con Maven y Java 17 para compilar
FROM maven:3.9-eclipse-temurin-17 AS buildstage
WORKDIR /app

# Copiamos primero el pom.xml para descargar dependencias (aprovecha caché)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copiamos el código fuente y compilamos
COPY src ./src
RUN mvn clean package -DskipTests

# ---------- ETAPA 2: EJECUCIÓN (RUNTIME) ----------
# Usamos una imagen ligera solo con JRE 17 para correr
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copiamos el JAR generado en la etapa anterior
# OJO: Ajusta el nombre si tu artifactId no es 'ms-consumidor'
COPY --from=buildstage /app/target/*.jar app.jar

# Copiamos la carpeta Wallet desde tu PC hacia adentro de la imagen
# IMPORTANTE: La carpeta 'Wallet' debe estar al lado de este Dockerfile
COPY Wallet /app/wallet

# Ejecutamos pasando la propiedad de Oracle
ENTRYPOINT ["java", "-Doracle.net.tns_admin=/app/wallet", "-jar", "app.jar"]