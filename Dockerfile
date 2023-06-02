# Użyj obrazu bazowego z Javą 17
FROM gradle:latest

# Utwórz katalog aplikacji
WORKDIR /wolo-app-api

# Skopiuj pliki aplikacji do kontenera
COPY . /wolo-app-api

# Ustaw zmienne środowiskowe dla bazy danych
ENV SPRING_DATASOURCE_URL=jdbc:postgresql://dbpostgresql:5432/mydb
ENV SPRING_DATASOURCE_USERNAME=postgres
ENV SPRING_DATASOURCE_PASSWORD=password

# Otwórz port, na którym działa aplikacja Spring Boot
EXPOSE 8080

# Uruchom aplikację
CMD ["gradle", "bootRun", "-x", "test"]