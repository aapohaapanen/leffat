#!/bin/sh
curl -X POST -H "Content-Type: application/json" -d @movies-compact.json http://localhost:8080/addMovies