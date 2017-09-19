package com.example.cann.muzikcalar.Envarter;

/**
 * Created by Cann on 3.12.2016.
 */

public class SongSinifi {

    private String songPath;
    private String songTitle;
    private long songID;

    public SongSinifi(String songPath, String songTitle, long songID) {
        this.songPath = songPath;
        this.songTitle = songTitle;
        this.songID = songID;
    }
    public SongSinifi() {}

    public String getSongPath() {
        return songPath;
    }

    public void setSongPath(String songPath) {
        this.songPath = songPath;
    }

    public String getSongTitle() {
        return songTitle;
    }

    public void setSongTitle(String songTitle) {
        this.songTitle = songTitle;
    }

    public long getSongID() {
        return songID;
    }

    public void setSongID(long songID) {
        this.songID = songID;
    }
}
