package csc296.finalproject.Model;

import java.util.Date;
import java.util.UUID;

/**
 * Created by yuewang on 12/8/15.
 */
public class FeedItem {

    private UUID mId;
    private String mUserName;
    private String mSongName;
    private String mPhoto;
    private String mVideo;
    private String mComment;
    private Date mPosted;

    public FeedItem(UUID id){
        mId = id;
    }

    public UUID getId() {
        return mId;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String userName) {
        mUserName = userName;
    }

    public String getSongName() {
        return mSongName;
    }

    public void setSongName(String songName) {
        mSongName = songName;
    }

    public String getVideo() {
        return mVideo;
    }

    public void setVideo(String video) {
        mVideo = video;
    }

    public String getPhoto() {
        return mPhoto;
    }

    public void setPhoto(String photo) {
        mPhoto = photo;
    }

    public String getComment() {
        return mComment;
    }

    public void setComment(String comment) {
        mComment = comment;
    }

    public Date getPosted() {
        return mPosted;
    }

    public void setPosted(Date posted) {
        mPosted = posted;
    }
}
