# Movies Application

## How to run with Docker

Run command:
`docker-compose up --build`

This command starts the MoviesApplication and runs it in a Docker container. It also starts the MongoDB database and saves the movie data from the `resources/movies-compact.json` file to the database.

You can also start only the MongoDB database with the command:
`docker-compose up mongodb` and run the application from the command line with the command:
`mvn spring-boot:run`

## REST endpoints

Base path: `/movies`

- `GET /` — home page (Thymeleaf view)
- `GET /movies` — get all movies
- `GET /movies/summaries` — get all movie summaries (DTOs)
- `GET /movies/{id}` — get a movie by id
- `POST /movies` — create a new movie
- `PUT /movies/{id}` — update an existing movie
- `DELETE /movies/{id}` — delete a movie by id
- `GET /movies/search` — search movies by query string, for example `/movies/search?query=Aliens`

## Authentication (OAuth 2.0)

The application supports authentication via **GitHub** and **Google**.

### Configuration
To run the application, you must provide the OAuth 2.0 credentials as environment variables:

| Provider | Client ID Variable | Client Secret Variable                                            |
| :--- | :--- |:------------------------------------------------------------------|
| **GitHub** | `SPRING_SECURITY_OAUTH2_CLIENT_REGISTRATION_GITHUB_CLIENT_ID` | `SPRING_SECURITY_OAUTH2_CLIENT_REGISTRATION_GITHUB_CLIENT_SECRET` |
| **Google** | `GOOGLE_CLIENT_ID` | `GOOGLE_CLIENT_SECRET`                                            |

The `SPRING_SECURITY_OAUTH2_CLIENT_REGISTRATION_GITHUB_CLIENT_ID` and `SPRING_SECURITY_OAUTH2_CLIENT_REGISTRATION_GITHUB_CLIENT_SECRET` environment variables are required for GitHub authentication. 
The `GOOGLE_CLIENT_ID` and `GOOGLE_CLIENT_SECRET` environment variables are required for Google authentication. 
The variables can be set in Windows PowerShell like this: `$env:GITHUB_CLIENT_ID="your_client_id_here"` and `$env:GITHUB_CLIENT_SECRET="your_client_secret_here"`.

### User Persistence
The application uses a "Registration on First Login" pattern:
1. When a user logs in for the first time, a new record is created in the `users` collection in MongoDB.
2. The user's name, email, and provider information are saved.
3. Subsequent logins update the existing record to ensure the latest profile information is stored.

