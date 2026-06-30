# 🧠 Candidate Profile AI Agent (RAG-Based)

## 📌 Overview

Candidate Profile AI Agent is a Retrieval-Augmented Generation (RAG) based web application that answers interview questions using a structured candidate profile stored as a JSON knowledge base.

The system retrieves relevant candidate information and sends it to a Large Language Model (Groq LLM) to generate accurate, context-aware responses.

The application also supports secure authentication using Spring Security with local login and Google OAuth2.

## 🚀 Features

### 👤 Authentication
- User Registration & Login
- Google OAuth2 Authentication
- Secure Password Encryption (BCrypt)
- Forgot Password & Reset Password

### 💬 AI Chat System
- AI-powered Candidate Profile Chatbot
- Retrieval-Augmented Generation (RAG)
- Groq LLM API Integration
- JSON Knowledge Base

### 💬 Chat Session Management (Latest Improvement)

The application now includes an improved Chat Session system that makes the chatbot experience more structured and scalable.

#### 🔹 What was improved
- Introduced **ChatSession entity** to manage multiple conversations per user
- Each chat message is linked to a specific session using `session_id`
- Prevents mixing of conversations between different chats
- Allows users to maintain multiple independent chat threads (like ChatGPT)

#### 🔹 Key Benefits
- Better conversation organization
- Clean separation of chat histories
- Easy navigation between previous chats
- Improved scalability for future enhancements such as:
  - Renaming chat sessions
  - Deleting sessions
  - Searching within conversations

#### 🔹 Flow
User → Select/Create Session → Ask Question → Store ChatHistory → Retrieve by Session → Display Chat Thread

### 🗄️ Database
- PostgreSQL Database
- Session-wise chat storage
- Chat history tracking

### 🐳 Deployment
- Dockerized Application
- Ready for Render Cloud Deployment

## 🛠️ Tech Stack

### Backend
- Java 17
- Spring Boot
- Spring Security
- Spring MVC
- Spring Data JPA
- Hibernate

### Frontend
- HTML
- CSS
- JavaScript
- Thymeleaf

### Database
- PostgreSQL

### AI
- Groq API (Llama 3.3 70B Versatile)

### Authentication
- Spring Security
- Google OAuth2

### Deployment
- Docker
- Render


## 🏗️ Project Architecture


User
│
▼
Spring Boot Web Application
│
Spring Security Authentication
│
Chat Controller
│
ChatSession Manager
│
RAG Service
│
Retriever Service
│
candidate-profile.json
│
Prompt Construction
│
Groq LLM API
│
AI Generated Response
│
ChatHistory Saved (Session-wise)
│
Browser UI

## 🧠 RAG Implementation

### Step 1
Candidate profile is stored as structured JSON:
src/main/resources/data/candidate-profile.json

### Step 2
User question is received by the backend.

### Step 3
RetrieverService loads relevant candidate data.

### Step 4
Prompt is constructed using:
- Candidate profile context
- User question

### Step 5
Prompt is sent to Groq API.

### Step 6
LLM generates response based only on provided context.

## 📊 Dataset Used

The JSON dataset contains:

- Basic Details
- Education
- Skills
- Projects
- Technologies
- Outcomes
- Hobbies
- Interview Responses

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
