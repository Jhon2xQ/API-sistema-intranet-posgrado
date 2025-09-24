# -------- Etapa 1: Build con Maven --------
FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src
RUN mvn clean package -DskipTests

# -------- Etapa 2: Extraer capas --------
FROM eclipse-temurin:21-jdk-alpine AS extract
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Extrae las capas definidas en layers.idx
RUN java -Djarmode=layertools -jar app.jar extract

# -------- Etapa 3: Imagen final JRE Alpine con usuario no root --------
FROM eclipse-temurin:21-jre-alpine

# Crear un usuario sin privilegios
RUN addgroup -S appgroup && adduser -S appuser -G appgroup

WORKDIR /app

# Copiar capas
COPY --from=extract /app/dependencies/ ./dependencies/
COPY --from=extract /app/spring-boot-loader/ ./spring-boot-loader/
COPY --from=extract /app/snapshot-dependencies/ ./snapshot-dependencies/
COPY --from=extract /app/application/ ./application/

# Cambiar propietario de los archivos al usuario no root
RUN chown -R appuser:appgroup /app

# Cambiar al usuario no root
USER appuser

EXPOSE 8080

ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]
