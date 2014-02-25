package org.techbooster.app.abc.models;

import android.os.Parcel;
import android.os.Parcelable;

public class BazaarEntry implements Parcelable {

    private final static String EXCLUDE_SUMMARY_STRING = "[出展者のサイトへ]";

    private String mName;
    private String mTitle;
    private String mSummary;
    private String mUrl;

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getSummary() {
        return mSummary;
    }

    public void setSummary(String summary) {
        if (summary != null) {
            mSummary = summary.replace(EXCLUDE_SUMMARY_STRING, "").trim();
        }
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }


    @Override
    public String toString() {
        return "BazaarEntry{" +
                "mName='" + mName + '\'' +
                ", mTitle='" + mTitle + '\'' +
                ", mSummary='" + mSummary + '\'' +
                ", mUrl='" + mUrl + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mName);
        dest.writeString(this.mTitle);
        dest.writeString(this.mSummary);
        dest.writeString(this.mUrl);
    }

    public BazaarEntry() {
    }

    private BazaarEntry(Parcel in) {
        this.mName = in.readString();
        this.mTitle = in.readString();
        this.mSummary = in.readString();
        this.mUrl = in.readString();
    }

    public static Parcelable.Creator<BazaarEntry> CREATOR = new Parcelable.Creator<BazaarEntry>() {
        public BazaarEntry createFromParcel(Parcel source) {
            return new BazaarEntry(source);
        }

        public BazaarEntry[] newArray(int size) {
            return new BazaarEntry[size];
        }
    };
}
