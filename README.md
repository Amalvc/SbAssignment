# Customer Management
## Project Description
This project is a Customer Management System (CMS) that allows users to perform CRUD (Create, Read, Update, Delete) operations on customer records. The application consists of both frontend and backend components..

## Table of Contents

1. [Prerequisites](#prerequisites)
2. [How to Run the Code](#how-to-run-the-code)
3. [Database Schema](#database-schema)
4. [Code flow](#code-flow)
5. [Project Structure](#project-structure)


# Prerequisites

Before you run the Customer Management project, ensure that you have the following tools installed on your machine:

## 1. IntelliJ IDEA
- You'll need IntelliJ IDEA, an integrated development environment (IDE) for Java development.
- **Download:** [IntelliJ IDEA](https://www.jetbrains.com/idea/)

## 2. MySQL
- Ensure MySQL is installed on your machine. MySQL is used for database management.
- **Download:** [MySQL Community Server](https://dev.mysql.com/downloads/mysql/)

## 3. MySQL Workbench
- MySQL Workbench is essential for accessing databases running on your local MySQL instances via a graphical user interface (GUI).
- **Download:** [MySQL Workbench](https://dev.mysql.com/downloads/workbench/)

## 4. Postman
- Postman is a popular API client used for testing APIs. It facilitates API development and testing.
- **Download:** [Postman](https://www.postman.com/downloads/)

## 5. Java Development Kit (JDK)
- You'll need the Java Development Kit (JDK) installed on your machine to compile and run Java applications.
- **Download:** [Java SE Development Kit](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)

Make sure to have these prerequisites installed and configured correctly before running the project.


## How to Run the Code:

### 1. Clone this repository and navigate to the project folder:

   ```sh
   git clone https://github.com/Amalvc/Apiwiz-assignment.git
   ```

### 2. Open in IntelliJ :
- Open IntelliJ IDEA .
- Import the project by selecting the project directory.
- Allow the IDE to download dependencies and configure the project.

### 3. Update application.properties:
- Modify the `application.properties` file in the project's `src/main/resources` directory.
- Add your database username, password, and the name of the database you created:
  ```properties
      server.port=9900
      spring.datasource.url=jdbc:mysql://localhost:3306/database_name
      spring.datasource.username=root
      spring.datasource.password=your_password
      spring.jpa.hibernate.ddl-auto=true
      spring.security.user.name=custom-username
      spring.security.user.password=custom-password
      spring.jpa.properties.hibernate.hbm2ddl.auto=update

      auth.token.expirationInMils=3600000
      auth.token.jwtSecret=secret_key
  ```

### 4. Run the Application in IntelliJ :
- In your IDE, locate the main application class (typically named `Application` or similar).
- Right-click on the main class and select "Run" or "Debug" to start the application.
- Wait for the application to start. You should see console output indicating that the application has started successfully.


## Database Schema

### Customer Table

| Field        | Type         |
|--------------|--------------|
| id           | bigint       |
| uuid         | varchar(255) |
| email        | varchar(255) |
| first_name   | varchar(255) |
| last_name    | varchar(255) |
| phone        | varchar(10)  |
| city         | varchar(255) |
| State        | varchar(255) |

### User Table

| Field         | Type         |
|---------------|--------------|
| id            | bigint       |
| name          | varchar(255) |
| email         | varchar(255) |
| password      | varchar(255) |


## Code Flow

**1. we can create new admin using signup api or we can add credential in application.properties**

**2. After signup, admin can login using his credential and JWT token will generate as response**

**3. Now admin can perform different crud operations like create customer,update customer,delete customer,get customer by id,get all customer with pagination,searching and sorting**

## Project Structure

```Structure
|-- src
| |-- main
| | |-- java
| | | |-- com.amal.sunbase
| | | | |-- Controller
| | | | |-- Dto
| | | | |-- Exception
| | | | |-- Model
| | | | |-- Repository
| | | | |-- Security
| | | | |-- Service
| | | | |-- Transformr
| | | | | |-- SunbaseApplication
| |-- resources
| | |-- application.properties
|-- test
|-- README.md
|-- pom.xml
```



