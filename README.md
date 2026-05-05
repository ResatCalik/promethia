Promethia

Promethia is a full-stack social content platform that combines feed-based social interaction with wiki-style detailed content pages. Users can register, log in, create content-rich posts with images, follow other users, like posts, and browse a personalized feed.

Tech Stack
Backend
Java 21
Spring Boot
Spring Security
JWT Authentication
Spring Data JPA
MySQL
Frontend
React
Vite
React Router
Axios
Features
User registration and login
JWT-based authentication and protected routes
Current user profile retrieval
Create and view posts
Detailed post pages
Like and unlike posts
Follow and unfollow users
Followers and following pages
Personalized feed based on followed users
React frontend integrated with secured REST APIs
Project Structure
Promethia-Fullstack
├── promethia
└── promethia-ui
promethia → Spring Boot backend
promethia-ui → React frontend
Backend Setup
Open the backend project in IntelliJ IDEA.
Configure MySQL.
Update application.yml with your database credentials.
Run the Spring Boot application.

Example database:

CREATE DATABASE promethia;
Frontend Setup

Open the frontend folder and run:

npm install
npm run dev

The frontend will start on a local Vite port such as:

http://localhost:5173

or

http://localhost:5175
API Highlights
Auth
POST /api/v1/auth/register
POST /api/v1/auth/login
User
GET /api/v1/users/me
Posts
POST /api/v1/posts
GET /api/v1/posts/feed
GET /api/v1/posts/{id}
GET /api/v1/posts/me
GET /api/v1/posts/user/{userId}
Follow
POST /api/v1/users/{userId}/follow
DELETE /api/v1/users/{userId}/follow
GET /api/v1/users/{userId}/followers
GET /api/v1/users/{userId}/following
Like
POST /api/v1/posts/{postId}/like
DELETE /api/v1/posts/{postId}/like
Screens

Suggested screenshots to include:

Login page
Feed page
Post detail page
Followers / Following pages
Future Improvements
Create post UI on frontend
Like and follow actions directly from UI
AWS S3 image upload
Docker setup
Better UI/UX polish
Search and filtering
Pagination
Notes

This project was developed as a portfolio-oriented full-stack application to demonstrate backend architecture, authentication flow, REST API design, relational data modeling, and frontend-backend integration.
