package com.example.coviddefender.db.history;

import android.os.Parcel;
import android.os.Parcelable;

public class HistoryModal implements Parcelable {
    public static final Creator<HistoryModal> CREATOR = new Creator<HistoryModal>() {
        @Override
        public HistoryModal createFromParcel(Parcel in) {
            return new HistoryModal(in);
        }

        @Override
        public HistoryModal[] newArray(int size) {
            return new HistoryModal[size];
        }
    };
    // creating variables for our different fields.
    public String location;
    public String time;
    public String IsCheckOut;

    protected HistoryModal(Parcel in) {
        location = in.readString();
        time = in.readString();
        IsCheckOut = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(location);
        dest.writeString(time);
        dest.writeString(IsCheckOut);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getIsCheckOut() {
        return IsCheckOut;
    }

    public void setIsCheckOut(String isCheckOut) {
        IsCheckOut = isCheckOut;
    }
}

