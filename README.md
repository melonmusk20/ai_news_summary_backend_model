# AI News Intelligence Platform — Backend

Spring Boot backend for the AI News Intelligence Platform. Fetches technology news from NewsData.io, processes each article with Groq AI (summarization, sentiment analysis, key insights), and stores results in PostgreSQL.

## Tech Stack

- Java 17 + Spring Boot
- PostgreSQL
- Groq API (LLaMA 3.1) for AI processing
- NewsData.io for news data
- Deployed on Render

## Setup (under 5 minutes)

### Prerequisites
- Java 17+
- PostgreSQL running locally
- Groq API key — [console.groq.com](https://console.groq.com)
- NewsData.io API key — [newsdata.io](https://newsdata.io)

### Steps

1. Clone the repo
   ```bash
   git clone https://github.com/melonmusk20/ai_news_summary_backend_model.git
   cd ai_news_summary_backend_model
   ```

2. Create a PostgreSQL database
   ```sql
   CREATE DATABASE news_db;
   ```

3. Set environment variables (copy `.env.example`)
   ```bash
   export DB_URL=jdbc:postgresql://localhost:5432/news_db
   export DB_USERNAME=postgres
   export DB_PASSWORD=your_password
   export GROQ_API_KEY=your_groq_api_key
   export NEWSDATA_API_KEY=your_newsdata_api_key
   ```

4. Run the app
   ```bash
   ./mvnw spring-boot:run
   ```

App starts on `http://localhost:8080`

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/` | Health check |
| GET | `/fetch-news` | Fetch fresh news, process with AI, save to DB |
| GET | `/articles` | Return all stored articles |

## Structure

```
ai_news_summary_backend_model/
│
├── Dockerfile
├── pom.xml                          # Spring Boot 4.0.6, Java 21
├── .env.example
│
└── src/
    └── main/
        ├── java/com/ai_summary/backend/
        │   │
        │   ├── BackendApplication.java          # Entry point
        │   │
        │   ├── config/
        │   │   └── CorsConfig.java              # CORS configuration
        │   │
        │   ├── controller/
        │   │   └── NewsController.java          # REST API endpoints
        │   │
        │   ├── service/
        │   │   ├── Aiservice.java               # Groq AI integration (summarization)
        │   │   ├── ArticleProcessor.java        # Article processing logic
        │   │   ├── ArticleService.java          # Business logic for articles
        │   │   └── NewsApiService.java          # NewsData.io API integration
        │   │
        │   ├── model/
        │   │   └── Article.java                 # JPA Entity (DB table)
        │   │
        │   ├── dto/
        │   │   ├── NewsArticleDto.java          # DTO for news article data
        │   │   └── NewsResponse.java            # DTO for API response
        │   │
        │   ├── repository/
        │   │   └── ArticleRepository.java       # JPA Repository (DB queries)
        │   │
        │   └── util/
        │       └── TextCleaner.java             # Text cleanup utility
        │
        └── resources/
            └── application.properties           # App config (DB, API keys, port)
```
## Data Pipeline

1. Fetches up to 5 pages of technology news from NewsData.io
2. Cleans and validates each article (strips HTML, whitespace)
3. Deduplicates by article URL
4. Processes each new article through Groq AI:
   - 2-sentence summary
   - Sentiment classification (Positive / Negative / Neutral)
   - 3 key insights as bullet points
5. Saves to PostgreSQL

## Environment Variables

See `.env.example` for all required variables.
