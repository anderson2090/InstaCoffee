package com.sweetdeveloper.instacoffee.models;


import android.os.Parcel;
import android.os.Parcelable;

public class News implements Parcelable {

    private String heading;
    private String link;

    public News() {
    }

    public News(String heading, String link) {
        this.heading = heading;
        this.link = link;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.heading);
        dest.writeString(this.link);
    }

    protected News(Parcel in) {
        this.heading = in.readString();
        this.link = in.readString();
    }

    public static final Parcelable.Creator<News> CREATOR = new Parcelable.Creator<News>() {
        @Override
        public News createFromParcel(Parcel source) {
            return new News(source);
        }

        @Override
        public News[] newArray(int size) {
            return new News[size];
        }
    };
}
