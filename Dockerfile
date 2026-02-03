FROM amazoncorretto:17-alpine-jdk

WORKDIR /app

# Копируем JAR
COPY target/*.jar app.jar

# Устанавливаем curl для healthcheck
RUN apk add --no-cache curl

# Создаем непривилегированного пользователя
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

EXPOSE 8080

HEALTHCHECK --interval=30s --timeout=10s --retries=3 \
  CMD curl -f http://localhost:8080/actuator/health || exit 1

ENTRYPOINT ["java", "-jar", "/app/app.jar"]