# Movies Application

## How to run with Docker

Run command:
`docker-compose up --build`

This command starts the MoviesApplication and runs it in a Docker container. It also starts the MongoDB database and saves the movie data from the `resources/movies-compact.json` file to the database.

## REST endpoints

Base path: `/movies`

- `GET /movies` — get all movies
- `GET /movies/{id}` — get a movie by id
- `POST /movies` — create a new movie
- `PUT /movies/{id}` — update an existing movie
- `DELETE /movies/{id}` — delete a movie by id
- `GET /movies/search` — search movies by query string, for example `/movies/search?query=Aliens`
