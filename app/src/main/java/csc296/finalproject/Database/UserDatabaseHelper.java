package csc296.finalproject.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import csc296.finalproject.Database.UserDbSchema.UserTable;
import csc296.finalproject.Database.UserDbSchema.FeedItemTable;


/**
 * Created by yuewang on 12/8/15.
 */

public class UserDatabaseHelper extends SQLiteOpenHelper {
    public UserDatabaseHelper(Context context) {
        super(context, UserDbSchema.DATABASE_NAME, null, UserDbSchema.VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("create table "+ UserTable.NAME
                +"(_id integer primary key autoincrement, "
                +UserTable.Cols.ID+", "
                +UserTable.Cols.USERNAME+", "
                +UserTable.Cols.PASSWORD+", "
                +UserTable.Cols.SONG_NAME+", "
                +UserTable.Cols.PHOTO+", "
                +UserTable.Cols.VIDEO+", "
                +UserTable.Cols.COMMENT+")");

        db.execSQL("create table " + FeedItemTable.NAME + "( "
                + FeedItemTable.Cols.ID + ", "
                + FeedItemTable.Cols.USERNAME + ", "
                + FeedItemTable.Cols.SONG_NAME + ", "
                + FeedItemTable.Cols.PHOTO + ", "
                + FeedItemTable.Cols.VIDEO + ", "
                + FeedItemTable.Cols.POST_DATE + ", "
                + FeedItemTable.Cols.COMMENT + ")");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}

