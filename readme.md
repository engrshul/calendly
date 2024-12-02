# Calendly Availability Management System

## Overview
This is a simple Calendly Availability Management System built using Spring Boot. It provides APIs for users to manage their availability and find overlapping schedules between users. The project is designed as an MVP to showcase core functionality with extensibility in mind.

---

## Questions and Answers

### 1. Have you thought through what a good MVP looks like? Does your API support that?
**Answer:**  
A good MVP for the availability management system ensures:
- Basic CRUD operations for managing user availability.
- The ability to check overlapping schedules between users.
- Proper error handling for missing or invalid users.

The API supports this by providing the following endpoints:
- `setAvailability` for creating or updating availability.
- `getAvailability` for retrieving availability for a user.
- `findOverlappingSchedule` for identifying overlapping schedules between multiple users.
- Exception handling with meaningful messages and HTTP status codes.

---

### 2. What trade-offs are you making in your design?
**Answer:**
- **Performance vs. Simplicity**: The overlapping schedule logic assumes small-scale usage and does not optimize for larger datasets. A more scalable solution would involve advanced database queries or caching mechanisms.
- **Feature Scope**: Focused only on the essential features (CRUD and overlap detection) for an MVP, leaving out advanced features like recurring availability or time zone support.
- **Flexibility vs. Rigidity**: The `findOverlappingSchedule` logic assumes the availability data is clean and doesn't account for edge cases.

---

### 3. Working code - we should be able to pull and hit the code locally. Bonus points if deployed somewhere.
**Answer:**  
The code requires Java 17 and is designed to run locally using Spring Boot. The code uses inmemory H2 database.
Simple clone the repo and run CalendlyApplication.java file.


---

### 4. Any good engineer will make hacks when necessary - what are your hacks and why?
**Answer:**
- **Simple Overlap Logic**: The overlapping schedule function is designed with a straightforward approach rather than advanced optimization to prioritize execution speed.
- **In-Memory Database**: Using an H2 database for simplicity in local development and testing instead of setting up a full database.
- **Basic DTO Conversion**: Manual mapping between entity and DTO instead of using libraries like MapStruct to keep dependencies minimal.

---

## Features
- **Set Availability**: Create or update a user's availability.
- **Get Availability**: Retrieve availability for a specific user.
- **Find Overlapping Schedules**: Identify overlapping time slots between multiple users.
- **Error Handling**: Meaningful exception messages and appropriate HTTP status codes.

---

## API Endpoints

1. **Set Availability**
    - `POST /availabilities`
    - **Request Body**:
      ```json
      {
        "userId": 1,
        "startTime": "2024-12-01T10:00:00",
        "endTime": "2024-12-01T12:00:00"
      }
      ```  
    - **Response**:
      ```json
      {
        "id": 1,
        "userId": 1,
        "startTime": "2024-12-01T10:00:00",
        "endTime": "2024-12-01T12:00:00"
      }
      ```

2. **Get Availability**
    - `GET /availabilities/{userId}`
    - **Response**:
      ```json
      [
        {
          "id": 1,
          "userId": 1,
          "startTime": "2024-12-01T10:00:00",
          "endTime": "2024-12-01T12:00:00"
        }
      ]
      ```

3. **Find Overlapping Schedule**
    - `POST /availabilities/overlap`
    - **Request Body**:
      ```json
      [1, 2]
      ```  
    - **Response**:
      ```json
      [
        {
          "id": 3,
          "userId": 1,
          "startTime": "2024-12-01T11:00:00",
          "endTime": "2024-12-01T12:00:00"
        }
      ]
      ```

---


