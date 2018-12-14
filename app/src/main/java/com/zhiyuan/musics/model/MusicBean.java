package com.zhiyuan.musics.model;

/**
 * Created by Administrator on 2018/12/12.
 */

public class MusicBean {
    private String singer;
    private String music;
    private String songId;

    public MusicBean(){}

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public String getMusic() {
        return music;
    }

    public void setMusic(String music) {
        this.music = music;
    }

    public String getSongId() {
        return songId;
    }

    public void setSongId(String songId) {
        this.songId = songId;
    }

    @Override
    public String toString() {
        return "MusicBean{" +
                "singer='" + singer + '\'' +
                ", music='" + music + '\'' +
                ", songId=" + songId +
                '}';
    }
}
