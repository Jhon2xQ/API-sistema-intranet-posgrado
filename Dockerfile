# -------- Etapa 1: Build con Maven --------
FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /app

# Copiar pom.xml y descargar dependencias (cache)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copiar el código fuente y compilar
COPY src ./src
RUN mvn clean package -DskipTests

# -------- Etapa 2: Imagen final con JRE Alpine --------
FROM eclipse-temurin:21-jre-alpine

# Crear usuario no-root seguro
RUN addgroup -S appgroup && adduser -S appuser -G appgroup

WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Dar permisos
RUN chown -R appuser:appgroup /app
USER appuser

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
