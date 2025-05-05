# grandytojai_backend

## Prerequisites

- Docker Desktop [docker link](https://www.docker.com/)
- Java version 21 (JAVA_HOME environment variable added and PATH variable modified)
- Copy over `.env.development` file contents to a locally created `.env` file

## Start the database
In the project root directory run `docker compose --env-file .env up -d` to start the database and the database viewer
Database will be available on `localhost:5432`

The database viewer will be available on `localhost:8081`
Login username "admin@admin.com"
Login password "admin"

Then press add new server "Add new server"
In the opened up modal add the following info:
- Connection -> Hostname = database
- Connection -> Port = 5432
- Connection -> Username = dbuser
- Connection -> Password = pass
- General -> Name = Any name

To find the tables go under Servers -> database -> Databases -> postgres -> Schemas -> app -> Tables
To query the tables right-click and press query tool, and you can perform basic database queries

## Start the backend server

In the project root directory run `./gradlew bootRun` and the backend service will be started
to shut it down hit `Ctrl + C`.

### Possible issues with backend

Cache is not cleaned to run `./gradlew clean`
JAVA_HOME env variable not set (see online for references how to do it)
JAVA_HOME/bin not added to PATH env variable (see online for references how to do it)