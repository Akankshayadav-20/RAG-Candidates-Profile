# Candidate Profile AI Agent (RAG-Based)

## Overview

Candidate Profile AI Agent is a Retrieval-Augmented Generation (RAG) based web application that answers interview questions using a structured candidate profile stored as a JSON knowledge base.

The application retrieves relevant candidate information and sends it to a Large Language Model (Groq LLM) to generate accurate, context-aware responses.

The system also provides secure authentication using Spring Security with both local login and Google OAuth2.

## Features

* User Registration & Login
* Google OAuth2 Authentication
* Secure Password Encryption (BCrypt)
* Forgot Password & Reset Password
* AI-powered Candidate Profile Chatbot
* Retrieval-Augmented Generation (RAG)
* JSON Knowledge Base
* Groq LLM API Integration
* PostgreSQL Database
* Dockerized Application
* Ready for Cloud Deployment (Render)

## Tech Stack

### Backend

* Java 17
* Spring Boot
* Spring Security
* Spring MVC
* Spring Data JPA
* Hibernate

### Frontend

* HTML
* CSS
* JavaScript
* Thymeleaf

### Database

* PostgreSQL

### AI

* Groq API (Llama 3.3 70B Versatile)

### Authentication

* Spring Security
* Google OAuth2

### Deployment

* Docker
* Render

# Project Architecture

                User

                  │

                  ▼

      Spring Boot Web Application

                  │

        Spring Security Authentication

                  │

             Chat Controller

                  │

              RagService

                  │

        RetrieverService

                  │

        candidate-profile.json

                  │

      Prompt Construction

                  │

            Groq LLM API

                  │

          AI Generated Answer

                  │

                  ▼

               Browser


# RAG Implementation

This project follows a simple Retrieval-Augmented Generation pipeline.

### Step 1

The candidate profile is stored as a structured JSON dataset.

candidate-profile.json


### Step 2

When a user asks a question, the RetrieverService loads the candidate profile from the JSON file.

### Step 3

The retrieved profile information is combined with the user's question to build a prompt.

Example:

Candidate Profile:

Interview Question:
Tell me about yourself

### Step 4

The prompt is sent to the Groq API.

### Step 5

Groq generates a response strictly based on the provided profile.

# Dataset Used

The application uses a structured JSON dataset containing:

* Basic Details
* Education
* Skills
* Projects
* Technologies
* Outcomes
* Hobbies
* Interview Responses

Location:
src/main/resources/data/candidate-profile.json

# Embedding Approach

This project does not use vector embeddings.

Instead, it uses a lightweight retrieval strategy by loading the candidate profile JSON and supplying the relevant context directly to the LLM.

This approach is suitable because the dataset is small and structured.

# APIs Used

* Groq API
* Google OAuth2 API

# Authentication

The application supports:

* Local Registration
* Local Login
* Google OAuth Login
* Password Encryption using BCrypt
* Forgot Password
* Reset Password

---

# Project Structure

src
 ├── controller
 ├── service
 ├── service/rag
 ├── security
 ├── repository
 ├── entity
 ├── dto
 ├── config
 ├── resources
 │     ├── templates
 │     ├── static
 │     └── data
 │          └── candidate-profile.json


# Installation

Clone the repository

git clone https://github.com/YOUR_USERNAME/RAG-Candidates-Profile.git

Move into the project

cd RAG-Candidates-Profile

Configure environment variables:

SPRING_DATASOURCE_URL
SPRING_DATASOURCE_USERNAME
SPRING_DATASOURCE_PASSWORD

GOOGLE_CLIENT_ID
GOOGLE_CLIENT_SECRET

GROQ_API_KEY

Run the project
mvn spring-boot:run

# Docker

Build the Docker image

docker build -t rag-candidate-profile .


Run the container

docker run -p 8080:8080 \
-e SPRING_DATASOURCE_URL=... \
-e SPRING_DATASOURCE_USERNAME=... \
-e SPRING_DATASOURCE_PASSWORD=... \
-e GOOGLE_CLIENT_ID=... \
-e GOOGLE_CLIENT_SECRET=... \
-e GROQ_API_KEY=... \
rag-candidate-profile

# Deployment

Application URL

```
https://rag-candidates-profile.onrender.com
```

# Future Improvements

* Vector Database Integration (Pinecone/ChromaDB)
* Semantic Search using Embeddings
* Conversation History
* Resume Upload Support
* PDF Knowledge Base
* Streaming AI Responses


# Author

**Akanksha Yadav**
 Aspiring Associate Software Engineer
