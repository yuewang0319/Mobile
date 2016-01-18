package csc296.finalproject.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;


import java.util.LinkedList;
import java.util.List;

import csc296.finalproject.Model.User;
import csc296.finalproject.Model.FeedItem;
/**
 * Created by yuewang on 12/8/15.
 */

import csc296.finalproject.Database.UserDbSchema.UserTable;
import csc296.finalproject.Database.UserDbSchema.FeedItemTable;

public class UserDbCrud {

    private static UserDbCrud sUserDbCrud;
    private Context mContext;
    private SQLiteDatabase mDatabase;
    private List<User> mUsers;
    private List<FeedItem> mFeedItems;


    private UserDbCrud(Context context){
        mContext=context;
    }

    public static UserDbCrud get(Context context){
        if(sUserDbCrud==null){
            sUserDbCrud=new UserDbCrud(context);
        }
        return sUserDbCrud;
    }

    public int createUsers(User user){
        mDatabase=new UserDatabaseHelper(mContext).getWritableDatabase();
        ContentValues values=new ContentValues();

        values.put(UserTable.Cols.ID,user.getId().toString());
        values.put(UserTable.Cols.USERNAME, user.getUserName());
        values.put(UserTable.Cols.PASSWORD, user.getPassword());

        long rowInserted=mDatabase.insert(UserTable.NAME, null, values);
        mDatabase.close();

        return (int) rowInserted;
    }

    public int insertRecords(User user){
        mDatabase=new UserDatabaseHelper(mContext).getWritableDatabase();
        ContentValues values=new ContentValues();

        values.put(UserTable.Cols.ID, user.getId().toString());
        values.put(UserTable.Cols.USERNAME, user.getUserName());
        values.put(UserTable.Cols.PHOTO, user.getPhoto());
        values.put(UserTable.Cols.VIDEO,user.getVideo());
        values.put(UserTable.Cols.SONG_NAME,user.getSongName());

        long rowInserted=mDatabase.insert(UserTable.NAME, null, values);
        mDatabase.close();

        return (int) rowInserted;
    }

    public int createFeedItems(User user){
        mDatabase=new UserDatabaseHelper(mContext).getWritableDatabase();
        ContentValues values=new ContentValues();

        values.put(FeedItemTable.Cols.USERNAME, user.getUserName());
        values.put(FeedItemTable.Cols.SONG_NAME,user.getSongName());
        values.put(FeedItemTable.Cols.PHOTO,user.getPhoto());
        values.put(FeedItemTable.Cols.VIDEO,user.getVideo());
        values.put(FeedItemTable.Cols.ID, user.getId().toString());
//        values.put(FeedItemTable.Cols.POST_DATE, user.getPosted().getTime());

        long rowInserted=mDatabase.insert(FeedItemTable.NAME, null, values);
        mDatabase.close();

        return (int) rowInserted;
    }

    public void updateComments(User user){
        mDatabase=new UserDatabaseHelper(mContext).getWritableDatabase();
        ContentValues values=new ContentValues();

        values.put(UserTable.Cols.COMMENT, user.getComment());
        values.put(FeedItemTable.Cols.COMMENT,user.getComment());

        long rowUpdated_userTable=mDatabase.update(UserDbSchema.UserTable.NAME, values, UserTable.Cols.ID + "=?", new String[]{user.getId().toString()});
        long rowUpdated_feedItemTable=mDatabase.update(UserDbSchema.FeedItemTable.NAME, values, FeedItemTable.Cols.ID + "=?", new String[]{user.getId().toString()});

        if(rowUpdated_userTable >0 && rowUpdated_feedItemTable >0)
            Toast.makeText(mContext, "Post Successful!", Toast.LENGTH_SHORT).show();

        else
            Toast.makeText(mContext, "Something wrong", Toast.LENGTH_SHORT).show();

        mDatabase.close();

    }
    public void deleteRecords(User user){
        mDatabase=new UserDatabaseHelper(mContext).getWritableDatabase();

        long rowDeleted_userTable=mDatabase.delete(UserTable.NAME, UserTable.Cols.ID + "=" + user.getId().toString(), null);
        long rowDeleted_feedItemTable=mDatabase.delete(FeedItemTable.NAME, FeedItemTable.Cols.ID + "=" + user.getId().toString(), null);

        if(rowDeleted_userTable >0 && rowDeleted_feedItemTable >0)
            Toast.makeText(mContext, "Delete Successful!", Toast.LENGTH_SHORT).show();

        else
            Toast.makeText(mContext, "Something wrong", Toast.LENGTH_SHORT).show();

        mDatabase.close();
    }

    public List<User> readUsers(String where, String[] args){
        mUsers=new LinkedList<>();
        mUsers.clear();
        mDatabase=new UserDatabaseHelper(mContext).getReadableDatabase();
        UserCursorWrapper wrapper=queryUsers(where, args);
        try{
            wrapper.moveToFirst();
            while(wrapper.isAfterLast()==false){
                User user=wrapper.getUser();
                mUsers.add(user);
                wrapper.moveToNext();
            }
        }
        finally{
            wrapper.close();
        }
        mDatabase.close();
        return mUsers;
    }

    public List<FeedItem> readFeedItems(String where, String[] args){
        mFeedItems=new LinkedList<>();
        mFeedItems.clear();
        mDatabase=new UserDatabaseHelper(mContext).getReadableDatabase();
        UserCursorWrapper wrapper=queryFeedItems(where, args);
        try{
            wrapper.moveToFirst();
            while(wrapper.isAfterLast()==false){
                FeedItem feedItem=wrapper.getFeedItem();
                mFeedItems.add(feedItem);
                wrapper.moveToNext();
            }
        }
        finally{
            wrapper.close();
        }

        mDatabase.close();
        return mFeedItems;

    }



    public UserCursorWrapper queryUsers(String where, String[] args){
        mDatabase=new UserDatabaseHelper(mContext).getReadableDatabase();
        Cursor cursor=mDatabase.query(
                UserDbSchema.UserTable.NAME,
                null,
                where,
                args,
                null,
                null,
                null
        );
        return new UserCursorWrapper(cursor);
    }

    public UserCursorWrapper queryFeedItems(String where, String[] args){
        mDatabase=new UserDatabaseHelper(mContext).getReadableDatabase();
        Cursor cursor=mDatabase.query(
                UserDbSchema.FeedItemTable.NAME,
                null,
                where,
                args,
                null,
                null,
                null
        );
        return new UserCursorWrapper(cursor);
    }

}
