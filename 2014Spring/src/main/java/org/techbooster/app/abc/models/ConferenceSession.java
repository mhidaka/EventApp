package org.techbooster.app.abc.models;

public class ConferenceSession {

    private String mTrackName;
    private String mSessionTitle;
    private String mSpeakerName;
    private String mSpeakerProfile;
    private String mDescription;
    private String mBeginTime;
    private String mRoom;

    public String getTrackName() {
        return mTrackName;
    }

    public void setTrackName(String trackName) {
        mTrackName = trackName;
    }

    public String getSessionTitle() {
        return mSessionTitle;
    }

    public void setSessionTitle(String sessionTitle) {
        mSessionTitle = sessionTitle;
    }

    public String getSpeakerName() {
        return mSpeakerName;
    }

    public void setSpeakerName(String speakerName) {
        mSpeakerName = speakerName;
    }

    public String getSpeakerProfile() {
        return mSpeakerProfile;
    }

    public void setSpeakerProfile(String speakerProfile) {
        mSpeakerProfile = speakerProfile;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getBeginTime() {
        return mBeginTime;
    }

    public void setBeginTime(String beginTime) {
        mBeginTime = beginTime;
    }

    public String getRoom() {
        return mRoom;
    }

    public void setRoom(String room) {
        mRoom = room;
    }
}
