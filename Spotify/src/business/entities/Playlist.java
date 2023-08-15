package business.entities;

import java.util.ArrayList;
import java.util.List;

public class Playlist {
    private int id;
    private final String name;
    private final String owner;
    private final String description;
    private final List<Song> songs;

    public Playlist(int id, String name, String owner, String description) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.description = description;
        songs = new ArrayList<>();
    }

    public Playlist(String name, String owner, String description) {
        this.name = name;
        this.owner = owner;
        this.description = description;
        songs = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getOwner() {
        return owner;
    }

    public String getDescription() {
        return description;
    }

    public List<Song> getSongs() {
        return List.copyOf(songs);
    }

    public void addSong(Song song) {
        songs.add(song);
    }
}
