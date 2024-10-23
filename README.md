## Steps to Build and Run the Voiture Application with Docker

### 1. Clone the Repository
```shell
$ git clone https://github.com/ismailtelhouni/SpringDataRest.git
$ cd SpringDataRest
```
### 2. Build the Backend Application Image

First, build the backend application using Maven with the build-image profile. This will package the Spring Boot application into a Docker image.
```shell
$ mvn clean package -Pbuild-image
```
This command will create a Docker image for the backend service based on your Spring Boot application.

### 3. Clone the Frontend Application:

Next, clone the front-end repository from GitHub:
```shell
$ git clone https://github.com/ismailtelhouni/VoitureAppFrontend.git
$ cd VoitureAppFrontend
```
This will download the front-end application code to your local machine.

### 4. Build the Frontend Application Image

Navigate to the cloned front-end application's workspace. Then, build the Docker image for the front-end application:
```shell
$ docker build -t voiture-app-frontend .
```
This will create a Docker image for the React front-end application.

### 5. Run All Containers Using Docker Compose

Once both the backend and frontend images are ready, use Docker Compose to run all the services. Ensure that your docker-compose.yml is configured correctly, as mentioned in the earlier steps.

Run the following command to start the containers:
```shell
$ docker compose up -d
```

### 6. Accessing Prometheus

Once Docker Compose is running, Prometheus will be available at the following URL:
    *Prometheus URL*: [http://localhost:9090](http://localhost:9090)
### 7.  Accessing Grafana

Grafana will also be available via Docker Compose. To access Grafana, visit:

The default login credentials are:

- *Username*: admin
- *Password*: admin

#### Add Prometheus as a Data Source
1. Go to *Configuration* -> *Data Sources* -> *Add data source*.
2. Select *Prometheus* and set the URL to http://prometheus:9090 (if running through Docker).

#### Import a Dashboard

1. Download the SpringBoot APM Dashboard JSON file from this [link](https://grafana.com/api/dashboards/12900/revisions/3/download).
2. Open Grafana and navigate to Dashboard -> Import.
3. Upload the downloaded JSON file to load the dashboard into Grafana.

### 8. Accessing the Application

Once the services are running, you can access the App at:

- *Application URL Frontend*: [http://localhost:3000](http://localhost:3000)
- *Application URL backend*: [http://localhost:8081](http://localhost:8081)
- *Prometheus URL*: [http://localhost:9090](http://localhost:9090)
- *Grafana URL*: [http://localhost:3001](http://localhost:3001)

