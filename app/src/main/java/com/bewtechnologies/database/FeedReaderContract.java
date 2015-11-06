package com.bewtechnologies.database;

import android.provider.BaseColumns;

/**
 * Created by aman on 6/11/15.
 */
public final class FeedReaderContract {
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public FeedReaderContract() {}

    /* Inner class that defines the table contents */
    public static abstract class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "news";
        public static final String COLUMN_NAME_NEWS_DATA = "news_data";
        public static final String COLUMN_NAME_NEWS_DATE = "news_date";
        public static final String COLUMN_NAME_NEWS_URL = "url";
        public static final String COLUMN_NAME_NEWS_IMAGE = "image";

    }
}