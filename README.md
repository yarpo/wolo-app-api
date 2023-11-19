![Logo](https://raw.githubusercontent.com/yarpo/wolo-app/e4cf379186c21a687389fc9755146fadbbef095c/src/images/logo.svg)

# WoloApp Api

REST API for web Application for Regional Volunteer Centre in Gdańsk, which helps volunteers find and partake in events.

## Run Locally

Clone the project

```bash
  git clone https://github.com/yarpo/wolo-app-api.git
```

Go to the project directory

```bash
  cd wolo-app-api
```

## Running with Docker

Start Docker Desktop

Run the Database and Backend
```bash
  docker-compose build
```
```bash
  docker-compose up
```
```bash
  docker-compose down
```
## Running with Docker & Gradle

Start Docker Desktop

Run the Database

```bash
  docker-compose start dbpostgresql
```

Run the Backend

```bash
  ./gradlew bootRun
```

## Running Tests

To run tests, run the following command

```bash
  ./gradlew test
```

Mapping

- http://localhost:8080/profile

- http://localhost:8080/events

- http://localhost:8080/shifts

- http://localhost:8080/categories

- http://localhost:8080/roles

- http://localhost:8080/shiftToUsers

- http://localhost:8080/addresses

- http://localhost:8080/users

- http://localhost:8080/districts

- http://localhost:8080/organisations

- http://localhost:8080/addressToEvents

## Troubleshooting

### Gradle Issues

1. Delete "build" folder:

```bash
  ./gradlew clean
  ./gradlew build
```

2. Delete .gradle folder

### Docker Issues

1. In Docker Desktop, remove data created by the "docker-compose" script in "Containers," "Images," and "Volumes" panels.
2. Restart the previously used script.

### Rare Scenario:

Ensure Docker Desktop is running without any warnings. If there are warnings:
1. Uninstall Docker Desktop.
2. Check for Docker folders in appdata and user directories (delete them if found).
3. Navigate to system settings in Windows:
4. System > Advanced system settings > Environment Variables.
5. Remove any variables containing "docker" in the name.
6. Install Docker Desktop.

## Technologies

- Gradle
- SpringBoot
- Postgresql
- Liquibase
- REST Assured
- Docker


## Related

 _Link to frontend repository:_
[Wolo-app](https://github.com/yarpo/wolo-app.git)

_Link to project documentation:_
[Jira Confluence](https://woloapp.atlassian.net/wiki/spaces/W/overview?homepageId=13795391)


## Authors

- [@Patryk Jar](https://github.com/yarpo)
- [@Jakwisnie](https://github.com/Jakwisnie)
- [@MKurshakova](https://github.com/MKurshakova)
- [@Agata Dobrzyniewicz](https://github.com/ayakiriya)
- [@Kinga](https://github.com/KinWaj)
- [@Filiposki54](https://github.com/Filiposki54)
- [@Fylyp19](https://github.com/Fylyp19)
- [@Aleksander Mielczarek](https://github.com/15465)
  
