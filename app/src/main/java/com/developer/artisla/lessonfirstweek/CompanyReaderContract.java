package com.developer.artisla.lessonfirstweek;

import android.provider.BaseColumns;

/**
 * Created by artisla on 2/1/16.
 */
public final class CompanyReaderContract {

    public CompanyReaderContract() {
    }

    public static abstract class CompanyEntry implements BaseColumns {
        public static final String TABLE_NAME = "company";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_IMAGE_PATH = "image";
        public static final String COLUMN_NAME_CREATED_AT = "created_at";
        public static final String COLUMN_NAME_NULLABLE = null;
    }
}
