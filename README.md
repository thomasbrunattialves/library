# 📚 Library Management System

### Java | Spring Boot | PostgreSQL | REST API

> 🚧 **Work in Progress**
>
> This is an ongoing personal portfolio project. The backend is currently under development, and new features are being implemented and tested progressively.

## 📌 About the Project

**Library Management System** is a full-stack web application that I am building to improve my software development skills, with a particular focus on Java backend development.

The goal is to create a library management platform where users can browse books, check the availability of physical copies, make reservations, and borrow books.

Rather than building a simple CRUD application, I want to explore real-world backend challenges, including business logic, database relationships, transaction management, data integrity, and concurrent operations.

I am developing this project independently, from database modeling and REST API development to the planned frontend implementation.

## 🛠️ Tech Stack

**Backend**
- Java 17
- Spring Boot
- Spring Data JPA
- Hibernate
- Jakarta Bean Validation
- Maven

**Database**
- PostgreSQL
- SQL
- Relational Database Modeling

**Development Tools**
- IntelliJ IDEA
- Postman
- Git & GitHub

**Planned Frontend**
- HTML5
- CSS3
- JavaScript

**Planned Technologies and Integrations**
- Spring Security
- Authentication and Role-Based Authorization
- Docker
- Application Deployment

## ⚙️ Features

### Implemented

- CRUD operations for books, authors, users, and physical book copies.
- Entity relationships using JPA and Hibernate.
- Data Transfer Objects (DTOs).
- Input validation using Jakarta Bean Validation.
- Global exception handling with appropriate HTTP responses.
- Book and book-copy status management.

### Currently in Development

- Book reservation management.
- Transaction management and pessimistic locking to handle concurrent reservation requests.
- Business rules for reservation dates and availability.
- Loan, return, and renewal workflows.
- Conflict detection between reservations and active loans.

### Planned Features

- Automatic expiration of uncollected reservations.
- User authentication and authorization.
- Administrative dashboard.
- Book catalog with search and filtering.
- Physical book location tracking.
- Responsive web interface.
- Frontend and backend integration.
- Automated tests.
- Docker containerization and deployment.

## 🧠 Technical Challenges

One of the main challenges in this project is handling concurrent requests.

For example, two users may attempt to reserve the same physical book copy for the same date.

To address this, I am implementing transaction management and pessimistic database locking using Spring Data JPA and PostgreSQL.

Another important challenge is coordinating reservations and loans while considering return dates, renewals, and future availability.

These features are still being developed and refined.

## 🎯 Project Goals

I am currently studying **Computer Engineering at Universidade Aberta, Portugal**, and focusing on becoming a Java backend developer.

This project is part of my professional portfolio and allows me to apply what I learn through practical software development.

My main goals are to:

- Strengthen my Java and Spring Boot skills.
- Design and implement RESTful APIs.
- Work with relational databases and complex entity relationships.
- Understand transaction management and concurrency.
- Develop maintainable and well-structured code.
- Gain experience building a complete application from scratch.

I am building this project step by step, prioritizing understanding the technologies and architectural decisions rather than simply completing features.

## 🚀 Project Status

**Current status: Backend development in progress.**

The application is not yet complete or deployed.

The repository reflects my actual development progress, including implemented functionality and features that are still being improved.

Documentation, API examples, setup instructions, and frontend screenshots will be added as the project evolves.

---

**Developed by Thomas Alves**

Computer Engineering Student | Aspiring Java Backend Developer
