# SEP2 Task Management System

## Overview
This is a Task Management application built with **Java**, **JavaFX**, and **RMI (Remote Method Invocation)**. It allows clients to connect to a remote server to manage and interact with tasks in real time.

## Prerequisites
Make sure you have the following installed:
- **Java 17**
- **Maven**
- **PostgreSQL**

## Setup

### Server
1. Navigate to the project directory.
2. Run the server:
   mvn exec:java -Dexec.mainClass="dk.via.taskmanagement.StartServer"

### Client
1. In the same project directory, run the client:
   mvn clean javafx:run

## Project Structure
- src/main/java/dk/via/taskmanagement/StartServer.java – Entry point for the **server**
- src/main/java/dk/via/taskmanagement/StartClient.java – Entry point for the **client**
- pom.xml – Maven configuration file

## Dependencies
- JavaFX
- JUnit 5
- PostgreSQL
- ControlsFX
- Mockito
- RemoteObserver (custom library)

## Usage
1. Start the **server**.
2. Start the **client**.
