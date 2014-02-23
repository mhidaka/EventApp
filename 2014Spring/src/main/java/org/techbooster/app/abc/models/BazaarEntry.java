package org.techbooster.app.abc.models;

public class BazaarEntry {

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
        if(summary != null){
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
}
