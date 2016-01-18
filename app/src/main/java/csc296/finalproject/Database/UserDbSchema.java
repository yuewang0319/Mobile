package csc296.finalproject.Database;

/**
 * Created by yuewang on 12/8/15.
 */


public class UserDbSchema {

    public static final int VERSION=1;
    public static final String DATABASE_NAME="final_project.db";




    public static final class UserTable{
        public static final String NAME="users";

        public static final class Cols{

            public static final String ID = "id";
            public static final String USERNAME="username";
            public static final String PASSWORD="password";
            public static final String SONG_NAME="song_name";
            public static final String PHOTO="photo";
            public static final String VIDEO="video";
            public static final String COMMENT="comment";


        }
    }

    public static final class FeedItemTable{
        public static final String NAME="feed_items";

        public static final class Cols{

            public static final String ID = "id";
            public static final String USERNAME="username";
            public static final String SONG_NAME="song_name";
            public static final String PHOTO="photo";
            public static final String VIDEO="video";
            public static final String COMMENT="comment";
            public static final String POST_DATE="post_date";
        }
    }

}
