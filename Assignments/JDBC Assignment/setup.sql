CREATE DATABASE IF NOT EXISTS JDBC_Assignment;
USE JDBC_Assignment;

DROP TABLE IF EXISTS tracks;


CREATE TABLE tracks (
id int,
artistName varchar(255),
trackName varchar(255),
albumName varchar(255),
milsecPlayed varchar(255),
danceability float,
energy float,
loudness float,
speechiness float,
acousticness float,
instrumentalness float,
liveness float,
valence float,
tempo float,
milsecDuration varchar(255));

