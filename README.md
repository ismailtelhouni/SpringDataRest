## Steps to Build and Run the Voiture Application with Docker
1. Build the Backend Application Image

First, build the backend application using Maven with the build-image profile. This will package the Spring Boot application into a Docker image.
```shell
$ mvn clean package -Pbuild-image
```
This command will create a Docker image for the backend service based on your Spring Boot application.

2. Clone the Frontend Application:

Next, clone the front-end repository from GitHub:
```shell
$ git clone https://github.com/ismailtelhouni/VoitureAppFrontend.git
```
This will download the front-end application code to your local machine.

3. Build the Frontend Application Image

Navigate to the cloned front-end application's workspace. Then, build the Docker image for the front-end application:
```shell
$ docker build -t voiture-app-frontend .
```
This will create a Docker image for the React front-end application.

4. Run All Containers Using Docker Compose

Once both the backend and frontend images are ready, use Docker Compose to run all the services. Ensure that your docker-compose.yml is configured correctly, as mentioned in the earlier steps.

Run the following command to start the containers:
```shell
$ docker compose up -d
```