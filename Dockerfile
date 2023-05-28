# Użyj obrazu bazowego z Javą 17
FROM openjdk:17

# Utwórz katalog aplikacji
WORKDIR /app

# Skopiuj pliki aplikacji do kontenera
COPY . /app

# Wykonaj komendę bootRun
CMD ["gradle", "bootRun"]

# Ustaw zmienne środowiskowe dla bazy danych
ENV SPRING_DATASOURCE_URL=jdbc:postgresql://dbpostgresql:5432/mydb
ENV SPRING_DATASOURCE_USERNAME=postgres
ENV SPRING_DATASOURCE_PASSWORD=password

# Otwórz port, na którym działa aplikacja Spring Boot
EXPOSE 8080

# Uruchom aplikację
CMD ["java", "-jar", "build/libs/wolo-app-api.jar"]
