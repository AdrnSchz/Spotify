INSERT INTO users VALUES ('user1', 'email1@gmail.com', 'password1');
INSERT INTO users VALUES ('user2', 'email2@gmail.com', 'password2');
INSERT INTO users VALUES ('user3', 'email3@gmail.com', 'password3');

INSERT INTO song (title, artist, album, genre, duration, owner)
    VALUES ('song1', 'artist1', 'album1','genre1', 1.1, 'user1');
INSERT INTO song (title, artist, album, genre, duration, owner)
    VALUES ('song2', 'artist2', 'album2','genre2', 2.1, 'user2');
INSERT INTO song (title, artist, album, genre, duration, owner)
    VALUES ('song3', 'artist3', 'album3','genre3', 3.1, 'user3');

INSERT INTO playlist (title, owner, description)
    VALUES ('playlist1', 'user1', 'description1');
INSERT INTO playlist (title, owner, description)
    VALUES ('playlist2', 'user2', 'description2');
INSERT INTO playlist (title, owner, description)
    VALUES ('playlist3', 'user3', 'description3');

INSERT INTO playlist_song VALUES (1, 1);
INSERT INTO playlist_song VALUES (1, 2);
INSERT INTO playlist_song VALUES (3, 2);
INSERT INTO playlist_song VALUES (3, 3);

SELECT * FROM users;
SELECT * FROM song;
SELECT * FROM playlist;
SELECT * FROM playlist_song;