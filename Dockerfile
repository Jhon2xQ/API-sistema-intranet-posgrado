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

# -------- Etapa 3: Imagen final Distroless --------
FROM gcr.io/distroless/java21-debian12

WORKDIR /app
# Copiamos las capas de forma separada → Docker las cachea mejor
COPY --from=extract /app/dependencies/ ./dependencies/
COPY --from=extract /app/spring-boot-loader/ ./spring-boot-loader/
COPY --from=extract /app/snapshot-dependencies/ ./snapshot-dependencies/
COPY --from=extract /app/application/ ./application/

EXPOSE 8080
ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]
