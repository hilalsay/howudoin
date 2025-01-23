# **Howudoin App Backend**

## **Overview**
This is the backend part of the CS310 Mobile Application Development course project for learning purposes. The backend of the Howudoin App is a RESTful API built with Java and Spring Boot. It handles message storage, retrieval, and user authentication. The API serves as the communication layer between the frontend and the database.

---

## **Features**
- Store messages with timestamps.
- Retrieve messages exchanged between two users.
- Authenticate users via JWT.
- Handle errors and exceptions gracefully.

---

## **Technologies Used**
- **Java** (Spring Boot framework)
- **MongoDB** for the database
- **JWT** for authentication
- **Gradle** for dependency management

---

## **Setup Instructions**
1. **Clone the Repository**:
   ```bash
   git clone <repository_url>
   cd backend
   ```

2. **Setup Database**:
   - Create a MongoDB database and name it accurately.
   - Update `application.properties` with your database credentials.

3. **Build and Run the Project**:
   - If you're using IntelliJ, you can directly build and run the project by following these steps:
      1. Open the project in IntelliJ.
      2. Make sure Gradle is correctly configured for the project.
      3. Click on the green "Run" button or right-click the main class (e.g., `Application.java`) and select "Run".
    
   - Alternatively, if you're using Gradle from the command line:
    ```bash
    ./gradlew clean build
    ./gradlew bootRun
    ```
---

## **How to Use**
1. Start the backend server.
2. Make API requests using the frontend (howudoinf) or a tool like Postman.
3. Monitor the database for stored messages and users.

---
