
FROM gradle:latest

# Utwórz katalog aplikacji
WORKDIR /wolo-app-api

# Skopiuj pliki aplikacji do kontenera
COPY . /wolo-app-api

# Ustaw zmienne środowiskowe dla bazy danych
ENV SPRING_DATASOURCE_URL=jdbc:postgresql://172.19.0.2:5432/mydb2
ENV SPRING_DATASOURCE_USERNAME=ADMIN1
ENV SPRING_DATASOURCE_PASSWORD=ADMIN1

# Otwórz port, na którym działa aplikacja Spring Boot
EXPOSE 8080

# Uruchom aplikację
CMD ["gradle", "bootRun", "-x", "test"]