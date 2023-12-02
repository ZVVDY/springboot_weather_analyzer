Weather Analyzer

This project is a Weather Analyzer application developed in Java using the Spring Boot framework. 
The application interacts with a third-party weather API (WeatherAPI.com) to retrieve weather information for a specified city at regular intervals. 
The collected data is stored in a database, and the application provides REST API endpoints to access the weather information.


Table of Contents

Getting Started

Prerequisites

Installation

Usage

Endpoint 1: Current Weather

Endpoint 2: Average Daily Temperature

Database Schema

Error Handling and Logging

Unit Tests


Getting Started

Prerequisites

Java 8 or higher

Maven

Docker (optional, for database setup using Docker Compose)


Installation

Clone the repository: 
git clone https://github.com/ZVVDY/springboot_weather_analyzer.git


Build the project using Maven:

cd springboot_weather_analyzer
mvn clean install


(Optional) Set up the database using Docker Compose:
docker-compose up -d

This will start a MySQL database container with the required schema.


Usage

Endpoint 1: Current Weather
To retrieve information about the current weather, make a GET request to the following endpoint:

GET /api/weathe/current


The response will contain the following information:

Temperature

Wind Speed

Atmospheric Pressure

Humidity

Weather Conditions

Location


Endpoint 2: Average Daily Temperature
To calculate the average daily temperature for a specified period, make a POST request to the following endpoint:

POST /api/weather/average

The request body should be in JSON format and include the from and to dates:
{
    "from": "2023-11-31",
    "to": "2023-12-01"
}

The response will contain the average temperature and other relevant information.


Database Schema
The application uses a MySQL database with the following schema:

location table

id (BIGINT, PRIMARY KEY, AUTO_INCREMENT)

name (VARCHAR(255), NOT NULL)

region (VARCHAR(255))

country (VARCHAR(255))

local_date_time (DATETIME)

weather table

id (BIGINT, PRIMARY KEY, AUTO_INCREMENT)

temperature (DOUBLE, NOT NULL)

wind_speed (DOUBLE, NOT NULL)

pressure (DOUBLE, NOT NULL)

humidity (DOUBLE, NOT NULL)

conditions (VARCHAR(255), NOT NULL)

timestamp (DATETIME, NOT NULL)

location_id (BIGINT, FOREIGN KEY referencing location.id)


Error Handling and Logging

The application includes robust error handling and logging for better traceability. 
Any errors or exceptions encountered during the execution of API requests are logged with detailed information.


Unit Tests
The project includes a comprehensive set of unit tests to ensure the correctness and reliability of the 
implemented functionality. You can run the tests using the following command:

mvn test


