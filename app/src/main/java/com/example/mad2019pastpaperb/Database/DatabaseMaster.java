package com.example.mad2019pastpaperb.Database;

import android.provider.BaseColumns;

public class DatabaseMaster {
    private DatabaseMaster() {}

    /* Inner class that defines the table contents */
    public static class Users implements BaseColumns {
        public static final String TABLE_NAME = "user";
        public static final String COLUMN_NAME = "userName";
        public static final String COLUMN_PASSWORD = "password";
        public static final String COLUMN_USER_TYPE = "userType";
    }

    public static class Game implements BaseColumns {
        public static final String TABLE_NAME = "games";
        public static final String COLUMN_NAME = "gameName";
        public static final String COLUMN_YEAR = "gameYear";
    }

    public static class Comments implements BaseColumns {
        public static final String TABLE_NAME = "comments";
        public static final String COLUMN_NAME = "gameName";
        public static final String COLUMN_RATING = "gameRating";
        public static final String COLUMN_COMMENTS = "gameComments";
    }
}
