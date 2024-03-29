package com.zzlan.bing.wallpaper.model;

import java.time.LocalDate;
import java.util.Objects;

public class Image {
    public Image() {
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    private LocalDate date;
    private String url;
    private String desc;

    public Image(LocalDate date, String url, String desc) {
        this.date = date;
        this.url = url;
        this.desc = desc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Image image = (Image) o;
        return Objects.equals(date, image.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date);
    }

    @Override
    public String toString() {
        return "Image{" +
                "date=" + date +
                ", url='" + url + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }
}
