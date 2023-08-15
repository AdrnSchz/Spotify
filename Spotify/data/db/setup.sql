CREATE USER oopd_ice6 WITH PASSWORD 'K3j$8Sz@';

CREATE DATABASE espotifai OWNER oopd_ice6;

-- psql specific command, won't work in other SQL clients
\c espotifai oopd_ice6

DROP TABLE IF EXISTS playlist_song;
DROP TABLE IF EXISTS song;
DROP TABLE IF EXISTS playlist;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
    username VARCHAR(50) PRIMARY KEY NOT NULL,
    email VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(50) NOT NULL
);

CREATE TABLE song (
    id SERIAL PRIMARY KEY,
    title VARCHAR(50) NOT NULL,
    artist VARCHAR(50) NOT NULL,
    album VARCHAR(50) NOT NULL,
    genre VARCHAR(50) NOT NULL,
    duration DECIMAL NOT NULL,
    owner VARCHAR(50) NOT NULL,
    FOREIGN KEY (owner)
        REFERENCES users (username)
);

CREATE TABLE playlist (
    id SERIAL PRIMARY KEY,
    title VARCHAR(50) NOT NULL,
    owner VARCHAR(50) NOT NULL,
    description TEXT,
    FOREIGN KEY (owner)
        REFERENCES users (username)
);

CREATE TABLE playlist_song (
    playlist_id INTEGER NOT NULL,
    song_id INTEGER NOT NULL,
    position SERIAL NOT NULL,
    FOREIGN KEY (playlist_id)
        REFERENCES playlist (id),
    FOREIGN KEY (song_id)
        REFERENCES song (id)
);

GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public to oopd_ice6;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public to oopd_ice6;
GRANT ALL PRIVILEGES ON ALL FUNCTIONS IN SCHEMA public to oopd_ice6;