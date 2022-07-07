# MobileAction Bootcamp Final Project - Air Quality Microservices

## About the project

This project's aim is to provide a Restfull Spring Boot Application which takes **city name**, **start date** and **end date** as parameters and returns air pollution status in the city between start and end dates using [OpenWeather's API endpoints](https://openweathermap.org/) while taking microservice architecture into consideration.

## How to start using it

In order to use the Air Quality Microservices, start all 5 services and make sure you have RabbitMQ and MongoDB installed. If you want to tweak the connection settings of MongoDB and RabbitMQ, you may change them in the application.properties files of respective Spring Boot applications.

Once all 5 services are up and running, make an API request to `http://localhost:8084/air-quality-api/v1/air-quality/{cityName}` or `http://localhost:8084/air-quality-api/v1/air-quality/{cityName}/{startDate}/{endDate}` and make sure to send date formatted as `dd-MM-yyyy`

## Microservices Architecture
![architecture](https://user-images.githubusercontent.com/54290546/177798579-659e8616-1e63-4132-9a9d-11b09f4e644b.PNG)


## Port Configuration
- `Geocing Service :  localhost:8081/` 
- `Air Pollution Service :  localhost:8082/`
- `Classification Service :  localhost:8083/`
- `Air Quality Service :  localhost:8084/`
- `Log Service :  localhost:8085/`

# Geocoding Service

The purpose of this service is to get `location` and return that location's `latitude` and `longitude` values by making an API call to OpenWeather API endpoint.

## Dependencies of Geocoding Service

- Lombok
- Spring Web
- Spring Boot Starter WebFlux
- OpenAPI Swagger

## Example Request and Response
![geo](https://user-images.githubusercontent.com/54290546/177805547-ccfe5925-f28f-4105-864b-2cd7f1bd2b9b.PNG)

# Air Pollution Service

The purpose of this service is to get `location`, `start date`, `end date` parameters and return a list of chemical values by date. This service makes 2 API calls. First one is to the Geocoding Service to get latitude and longitude values of the location. Second API request is made to OpenWeather's endpoint.
Between these 2 requests dates are turned to Unix Time as requested by OpenWeather. OpenWeather returns a list containing chemical values seperated by unix timestamps. The Air Pollution Service takes this list determines which unix timestamps belong to the same date and then takes the average of different chemical values using JsonPath.

## Dependencies of Air Pollution Service

- Lombok
- Spring Web
- Spring Boot Starter Webflux
- OpenAPI Swagger
- Json Path

## Example Request and Response

### Response from OpenWeather endpoint
![2owexample](https://user-images.githubusercontent.com/54290546/177809224-7f71eece-8a6f-4452-afc3-63e0f8f4a0b1.png)

### Request and Response from Air Pollution Service
![aip](https://user-images.githubusercontent.com/54290546/177809387-fd92656c-ad73-4796-9fdd-bd046b3fb948.PNG)

# Classification Service

The purpose of this service is to determine the danger levels of chemical values returned from Air Pollution Service. Classification is done by the use of  [AQI Category,
Pollutants and Health Breakpoints Table in this wiki page](https://en.wikipedia.org/wiki/Air_quality_index#CAQI).

Classification Service takes city, start date and end date parameters and makes an API call to Air Pollution Service. Using the response object from Air Polllution Service, it classifies the chemical values into a category of Good, Satisfactory, Moderate, Poor, Severe and Hazardous and returns a list of categories seperated by date.

## Dependencies of Classification Service

- Lombok
- Spring Web
- Spring Boot Starter Webflux
- OpenAPI Swagger

## Example Request and Response

![examplecls](https://user-images.githubusercontent.com/54290546/177811999-e7b4e736-9b96-4b29-b4b5-3a4abf6ad5af.PNG)


# Air Quality Service

The purpose of Air Quality Service is to take city, start date and end date as parameters and to return a list of air quality results seperated by date. 
Air Quality Service is responsible from 

- returning the air quality results sorted by date
- returning last 7 days of air quality results if dates are not specified
- making smart decisions to minimize extensive API requests.
- saving new air quality results into database
- deleting air quality results from database
- not making API calls for existing air quality results
- Sending log messages to Log Service via RabbitMQ (logging the origin of returned air quality results whether from DB or API)

## Dependencies of Air Quality Service

- Lombok
- Spring Web
- Spring Boot Starter Webflux
- OpenAPI Swagger
- Spring Data MongoDB
- Mapstruct
- RabbitMQ


## Example Request and Response

![aqssave](https://user-images.githubusercontent.com/54290546/177826394-24bb3ab0-3524-468d-9ec4-36f2cdc6f9a5.png)

![aqsdelete](https://user-images.githubusercontent.com/54290546/177826751-56748aa9-2dea-40f3-afaa-da5fd9c9adcd.png)


## MongoDB Screenshots
![aqsdb](https://user-images.githubusercontent.com/54290546/177826451-b4809c84-026c-4e8d-a1c0-2da4367f54f9.png)


# Log Service

The purpose of Log Service is to be a centralized log service for future microservices and existing ones. The log messages are received via RabbitMQ queue and logged into console and MongoDB.

## Dependencies of Log Service
- Lombok
- Spring Web
- Spring Data MongoDB
- RabbitMQ
- Slf4j (Lombok)


## Console and MongoDB Screenshots

![logconsole](https://user-images.githubusercontent.com/54290546/177828349-4f7ebaf6-c1bc-44a9-b1bc-863fe63f3287.PNG)
![logdb](https://user-images.githubusercontent.com/54290546/177828357-43c5ab09-08a8-49c7-a2f6-ca2eba0e5a1c.PNG)


