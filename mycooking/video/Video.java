package com.example.banhnhandau.mycooking.video;

/**
 * Created by CHAMIKO on 12/3/2017.
 */

public class Video {
    private String idVideo;
    private String titleVideo;
    private String thumbnail;

    public Video(String idVideo, String titleVideo, String thumbnail) {
        this.idVideo = idVideo;
        this.titleVideo = titleVideo;
        this.thumbnail = thumbnail;
    }

    public Video() {
    }

    public String getIdVideo() {
        return idVideo;
    }

    public void setIdVideo(String idVideo) {
        this.idVideo = idVideo;
    }

    public String getTitleVideo() {
        return titleVideo;
    }

    public void setTitleVideo(String titleVideo) {
        this.titleVideo = titleVideo;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
