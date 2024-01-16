# Introduction 
Application uses java 17, spring boot 3, maven and h2 database.

Application uses business template pattern i.e every controller action that is exposed in API has to implement a business service template, 
which makes it very easy to maintain understand and debug, so that way we are forced to implement pattern of validate, execture, handle failure and post execution.

Please go through the AbstractBusinessService to undertand on this business template more.

Application used test containers rather than conventional unit test cases, test cases are written for every controller method that cuts across all services and tests each and every condition in the code anywhere in the application, which makes the test cases alot more logical as 
I can pass the exact params in my API call and expect what part of my which service to fail or to behave in a certain way, rather than mocking all classes one by one and unit testing them.

Its a conference room booking system for a bank, it has 4 rooms with different capacities, rooms are booked on first come first serve basis.

# Getting Started
1. Installation process
    must have java 17 and maven and docker.
    mvn clean install will install add all the libraries required

# Build and Test

In order to build and run the application, required tools : 
    Java 17
    Maven
    Docker

buid by running command : mvn clean install
it creates a test container and deploys application in it and runs test cases there.

then go in target folder and run : java -jar meeting-service-1.0.0.jar

# API curls 

APi to get All available rooms : 

curl --location 'localhost:8080/api/v1/rooms?fromTime=10%3A00&toTime=10%3A15' \
--header 'Content-Type: application/json'

Api to create a new meeting : 
curl --location 'localhost:8080/api/v1/meetings' \
--header 'Content-Type: application/json' \
--data '{
    "title": "Meeting Request 1",
    "people": 5,
    "startTime": "10:00",
    "endTime": "10:15"

}'

Api to get all scheduled meetings :

curl --location 'localhost:8080/api/v1/meetings' \
--header 'Content-Type: application/json'
