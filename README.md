# Weather Analyzer

Weather Analyzer is a Java application developed with the Spring Boot framework. It interacts with the WeatherAPI.com to fetch weather information for a specified city at regular intervals. The collected data is stored in a database, and the application provides REST API endpoints for accessing weather information.

## Table of Contents

1. [Getting Started](#getting-started)
   - [Prerequisites](#prerequisites)
   - [Installation](#installation)
2. [Usage](#usage)
   - [Endpoint 1: Current Weather](#endpoint-1-current-weather)
   - [Endpoint 2: Average Daily Temperature](#endpoint-2-average-daily-temperature)
3. [Database Schema](#database-schema)
4. [Error Handling and Logging](#error-handling-and-logging)
5. [Unit Tests](#unit-tests)
6. [Testing](#testing)
   - [Run the tests using Maven](#run-the-tests-using-maven)
7. [Contributing](#contributing)
   - [Fork the repository](#fork-the-repository)
   - [Create a new branch](#create-a-new-branch)
   - [Commit your changes](#commit-your-changes)
   - [Push to the branch](#push-to-the-branch)
   - [Create a new pull request](#create-a-new-pull-request)

## Getting Started

### Prerequisites

- Java 8 or higher
- Maven
- Docker (optional, for database setup using Docker Compose)

### Installation

1. **Clone the repository:**
   ```bash
   git clone https://github.com/ZVVDY/springboot_weather_analyzer.git
   cd springboot_weather_analyzer


The response will include the following information:

- Temperature
- Wind Speed
- Atmospheric Pressure
- Humidity
- Weather Conditions
- Location

### Endpoint 2: Average Daily Temperature

To calculate the average daily temperature for a specified period, make a POST request to the following endpoint:

## GET /api/weathe/current


The response will contain the following information:

-Temperature
-Wind Speed
-Atmospheric Pressure
-Humidity
-Weather Conditions
-Location

### Endpoint 2: Average Daily Temperature

To calculate the average daily temperature for a specified period, make a POST request to the following endpoint:

## POST /api/weather/average

### The request body should be in JSON format and include the from and to dates:

{
    "from": "2023-11-31",
    "to": "2023-12-01"
}

The response will contain the average temperature and other relevant information.

### Database Schema
The application uses a MySQL database:

set the parameter to create database tables:
spring.jpa.hibernate.ddl-auto=create

or copy the create.sql script to src\main\resources\. 

After creating the tables, you need to change the parameters in
```bash
spring.jpa.hibernate.ddl-auto=update


### Error Handling and Logging

The application includes robust error handling and logging for better traceability. 
Any errors or exceptions encountered during the execution of API requests are logged with detailed information.


### Unit Tests
The project includes a comprehensive set of unit tests to ensure the correctness and reliability of the 
implemented functionality. You can run the tests using the following command:

mvn test


