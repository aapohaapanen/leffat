create table movie (
  id uuid primary key,
  name varchar,
  relaseyear int,
  agelimit int,
  rating int,
  synopsis varchar
  );

create table genre (
  name varchar primary key
);

create table movie_genre (
  movie uuid,
  genre varchar
);

alter table movie_genre
  add constraint movie_genre_fk foreign key (movie) references movie(id);

alter table movie_genre
  add constraint genre_movie_fk foreign key (genre) references genre(name);
  