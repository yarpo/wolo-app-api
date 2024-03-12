![Logo](https://raw.githubusercontent.com/yarpo/wolo-app/e4cf379186c21a687389fc9755146fadbbef095c/src/images/logo.svg)

<h1 align="center" > WoloApp API </h1>

<p align="center">
REST API for web Application for Regional Volunteer Centre in Gdańsk, which helps volunteers find and partake in events.
</p>

## 🔐 Environment Variables

Create and setup an `.env` file with the following Environment Variables:
```bash
  SPRING_DATASOURCE_URL = jdbc:postgresql://dbpostgresql:5432/mydb2
  SPRING_DATASOURCE_USERNAME = {spring_username}
  SPRING_DATASOURCE_PASSWORD = {spring_password}

  POSTGRES_DB = mydb2
  POSTGRES_USER= {postgres_username}
  POSTGRES_PASSWORD= {postgres_password}
```

## 🐋 Running with Docker

Clone the project

```bash
  git clone https://github.com/yarpo/wolo-app-api.git
```
Go to the project directory

```bash
  cd wolo-app-api
```

Run the Database and Backend with launched Docker

```bash
  docker-compose --env-file .env --profile standalone up
```


## ⚙️ Running Tests

To run tests, run the following command

```bash
  ./gradlew test
```


## 📌 Swagger

To access Swagger, while project is running go to 

`http://localhost:8080/swagger-ui/index.html`


## 💿 Technologies

- Gradle
- SpringBoot
- Postgresql
- Liquibase
- REST Assured
- Docker
- Swagger


## 🔗 Related

 _Link to frontend repository:_
[Wolo-app](https://github.com/yarpo/wolo-app.git)

_Link to project documentation:_
[Jira Confluence](https://woloapp.atlassian.net/wiki/spaces/W/overview?homepageId=13795391)


## 👋 Authors

- [@Patryk Jar](https://github.com/yarpo)
- [@Jakwisnie](https://github.com/Jakwisnie)
- [@MKurshakova](https://github.com/MKurshakova)
- [@Agata Dobrzyniewicz](https://github.com/ayakiriya)
- [@Kinga](https://github.com/KinWaj)
- [@Filiposki54](https://github.com/Filiposki54)
- [@Fylyp19](https://github.com/Fylyp19)
- [@Aleksander Mielczarek](https://github.com/15465)
