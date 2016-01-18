package csc296.finalproject.Database;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.Date;
import java.util.UUID;

import csc296.finalproject.Model.FeedItem;
import csc296.finalproject.Model.User;
import csc296.finalproject.Database.UserDbSchema.UserTable;
import csc296.finalproject.Database.UserDbSchema.FeedItemTable;

/**
 * Created by yuewang on 12/8/15.
 */
public class UserCursorWrapper extends CursorWrapper {


    public UserCursorWrapper(Cursor cursor){
        super(cursor);
    }

    public User getUser(){



        UUID id = UUID.fromString(getString(getColumnIndex(UserTable.Cols.ID)));
        String username=getString(getColumnIndex(UserTable.Cols.USERNAME));
        String password=getString(getColumnIndex(UserTable.Cols.PASSWORD));
        String song_name=getString(getColumnIndex(UserTable.Cols.SONG_NAME));
        String photo=getString(getColumnIndex(UserTable.Cols.PHOTO));
        String video=getString(getColumnIndex(UserTable.Cols.VIDEO));
        String comment=getString(getColumnIndex(UserTable.Cols.COMMENT));


        User user=new User(id);
        user.setUserName(username);
        user.setPassword(password);
        user.setSongName(song_name);
        user.setPhoto(photo);
        user.setVideo(video);
        user.setComment(comment);



        return user;
    }

    public FeedItem getFeedItem(){


        UUID id = UUID.fromString(getString(getColumnIndex(FeedItemTable.Cols.ID)));
        String username=getString(getColumnIndex(FeedItemTable.Cols.USERNAME));
        String song_name=getString(getColumnIndex(FeedItemTable.Cols.SONG_NAME));
        String photo=getString(getColumnIndex(FeedItemTable.Cols.PHOTO));
        String video=getString(getColumnIndex(UserTable.Cols.VIDEO));
        String comment=getString(getColumnIndex(UserTable.Cols.COMMENT));
        Date postDate=new Date(getLong(getColumnIndex(FeedItemTable.Cols.POST_DATE)));

        FeedItem feedItem=new FeedItem(id);

        feedItem.setUserName(username);
        feedItem.setSongName(song_name);
        feedItem.setPhoto(photo);
        feedItem.setVideo(video);
        feedItem.setComment(comment);
        feedItem.setPosted(postDate);

        return feedItem;
    }

}
