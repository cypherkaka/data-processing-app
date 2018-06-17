## Data Processing Pipeline

#### Detail
This is data processing pipeline application which will take JSON input as POST and perform various operations.

A REST endpoint is taking a dummy JSON input, and the server puts the REST payload on Redis.

A Consumer will be running in the application, taking the freshly received message and persists it in H2 database.

A REST endpoint is implemented for retrieving all the messages persisted in JSON format from the HR database.

The message should also be pushed through Websockets to listening browser clients at the time the message is received on the REST endpoint

A simple HTML page is implemented to show the real time message delivery.

##### Tech Stack
1. Spring Boot - used as foundation
2. Spring Data Redis - Provides easy configuration and access to Redis and messaging from Spring applications
3. Redis - used to store and retrieve messages
4. H2 - used as in-memory database
5. Websockets - used to push messages to listening web browser
6. SockJS.js - WebSocket emulation
7. STOMP.js - used as client for Web browser applications (using Web Sockets).
8. Docker - used as container to deploy spring-boot application and for redis server
9. Maven - used as build tool for spring-boot application 

To review detailed implementation, please start with `DataProcessingController.java`

#### How to build/start this app
* Run locally with maven

1. Set `redis.host=127.0.0.1` in `application.properties` file 
2. cd to parent directory where `Dockerfile` exists
3. run `docker run --rm -p 127.0.0.1:6379:6379 --name redis-data-processing-app -i -t redis:alpine` to start `Redis` server
4. open new terminal and cd to parent directory
5. run `mvn spring-boot:run` to start Spring-boot application

* Run on docker

1. Set `redis.host=redis` in `application.properties` file 
2. cd to parent directory where `Dockerfile` exists
3. run `mvn clean package -DskipTests` to build `jar` file
4. run `docker-compose up --build` to start docker


#### How to test this app

* After successfully starting of spring-boot application.  Open web browser(Chrome/Firefox) and hit
    
    `http://localhost:8080/`
    
* POST a message to process through REST Client
 
`URL            : http://localhost:8080/data-processing-app/process `

`Request        : {"id":1,"message":"MESSAGE 1"} `

`HTTP Method    : POST `

`HTTP Status    : 200 - Success `

* After posting a message you can see this message listed in listening web browser

* GET all messages through REST Client
 
`URL         : http://localhost:8080/data-processing-app/message/get-all?limit=20`

`Response    : [ {"id":1,"message":"MESSAGE 1"} , {"id":2,"message":"MESSAGE 2"} ] `

`HTTP Method : GET`

`HTTP Status : 200 - Success`

