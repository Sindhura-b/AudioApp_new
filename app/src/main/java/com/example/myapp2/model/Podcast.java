package com.example.myapp2.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Podcast implements Parcelable {

    private String title;
    private String media_id;
    private String location_id;

    public Podcast(String title, String media_id, String location_id){
        this.title = title;
        this.media_id = media_id;
        this.location_id = location_id;
    }

    public Podcast(){
    }

    protected Podcast(Parcel in) {
        title = in.readString();
        media_id = in.readString();
        location_id = in.readString();
    }

    public static final Creator<Podcast> CREATOR = new Creator<Podcast>() {
        @Override
        public Podcast createFromParcel(Parcel in) {
            return new Podcast(in);
        }

        @Override
        public Podcast[] newArray(int size) {
            return new Podcast[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(media_id);
        parcel.writeString(location_id);
    }

    public String getTitle(){return title;}

    public void setTitle(String title){this.title = title;}

    public String getLocation_id(){return location_id;}

    public void setLocation_id(String location_id){this.location_id = location_id;}

    public String getMedia_id(){return media_id;}

    public void setMedia_id(String media_id){this.media_id = media_id;}
}
