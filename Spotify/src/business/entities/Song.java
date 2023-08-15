package business.entities;

public class Song {
    private int id;
    private final String title;
    private final String artist;
    private final String album;
    private final String genre;
    private float duration;
    private final String owner;
    private final int position;

    public Song(int id, String title, String artist, String album, String genre, float duration, String owner) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.genre = genre;
        this.duration = duration;
        this.owner = owner;
        this.position = -1;
    }

    public Song(int id, String title, String artist, String album, String genre, float duration, String owner, int position) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.genre = genre;
        this.duration = duration;
        this.owner = owner;
        this.position = position;
    }

    public Song(String title, String artist, String album, String genre, String owner){
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.genre = genre;
        this.owner = owner;
        this.position = -1;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getAlbum() {
        return album;
    }

    public String getGenre() {
        return genre;
    }

    public float getDuration() {
        return duration;
    }

    public String getOwner() {
        return owner;
    }

    public int getPosition(){
        return position;
    }
}
