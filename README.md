# MetChat
Čet aplikacija za Univerzitet Metropolitan.

## Frontend

Frontend je napravljen u [Angular2](https://angular.io/) frejmvorku.

### Instalacija

Instalirajte [Node.js](https://nodejs.org/).

Instalirajte Angular-CLI.
```sh
npm install -g angular-cli
```

Instalirajte node module.
```sh
cd frontend
npm i
```

### Build

Kompilacija i kopiranje Angular 2 aplikacije u resurse Spring projekta.

```sh
cd frontend
ng build
```

## Bekend

Bekend je napravljen u [Spring Boot](https://spring.io/) frejmvorku i kompajlira se pomoću [Gradle](https://gradle.org/) alata.

### Instalacija

Instalirajte [Java Development Kit 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html), [Gradle](https://gradle.org/gradle-download/) i [MongoDB](https://www.mongodb.com/download-center).

### Build

Kompilacija Java aplikacije.

```sh
cd backend
gradlew build
```

### Pokretanje

Pokrenite MongoDB pa pokrenite Java aplikaciju.

```sh
cd backend
java -jar build/libs/met-chat-0.1.0.jar
```

Aplikacija je dostupna na adresi http://localhost:8080

---

<!---
[![Spring Boot](https://spring.io/img/spring-by-pivotal.png)](https://spring.io/)
[![Gradle](https://upload.wikimedia.org/wikipedia/en/a/ab/Updated_logo_for_Gradle.png)](https://gradle.org/)
[![Eclipse](https://eclipse.org/artwork/images/v2/logo-800x188.png)](https://eclipse.org/)
[![MongoDB](https://upload.wikimedia.org/wikipedia/en/thumb/4/45/MongoDB-Logo.svg/800px-MongoDB-Logo.svg.png)](https://www.mongodb.com/)
[![Angular 2](http://drupal.sh/sites/default/files/styles/large/public/2016-06/angular.png?itok=OAeCV0KX)](https://angular.io/)
[![Node.js](https://upload.wikimedia.org/wikipedia/commons/7/7e/Node.js_logo_2015.svg)](https://nodejs.org/)
[![PhpStorm](http://epe.si/images/logo_PhpStorm.png)](https://www.jetbrains.com/phpstorm/)
--->
