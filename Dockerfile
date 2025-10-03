# --- Estágio 1: Build ---
FROM eclipse-temurin:17-jdk-jammy AS builder
WORKDIR /app
COPY . .
RUN chmod +x mvnw
RUN ./mvnw -B -DskipTests package

# --- Estágio 2: Runtime ---
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar

# Metadados úteis (opcional)
LABEL org.opencontainers.image.description="Gerenciador de Tarefas (Spring Boot + H2)"
LABEL org.opencontainers.image.licenses="MIT"

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]