create table movie (
  id uuid primary key,
  name varchar,
  release_year int,
  age_limit int,
  rating int,
  synopsis varchar,
  director_id uuid
  );

create table genre (
  name varchar primary key
);

create table person (
  id uuid primary key,
  first_name varchar,
  last_name varchar
);

create table movie_genres (
  movies_id uuid,
  genres_name varchar
);

create table movie_actors (
  movie_id uuid,
  actors_id uuid
);

alter table movie_genres
  add constraint movie_genre_fk foreign key (movies_id) references movie(id);

alter table movie_genres
  add constraint genre_movie_fk foreign key (genres_name) references genre(name);

alter table movie
  add constraint movie_director_fk foreign key (director_id) references person(id);

alter table movie_actors
  add constraint movie_actor_fk foreign key (movie_id) references movie(id);

alter table movie_actors
  add constraint actor_movie_fk foreign key (actors_id) references person(id);