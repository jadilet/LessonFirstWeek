package com.developer.artisla.lessonfirstweek;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.developer.artisla.lessonfirstweek.model.Company;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * Created by artisla on 2/2/16.
 */
public class CompanyReaderDbHelper extends SQLiteOpenHelper {
    private static final String TEXT_TYPE = " TEXT";
    private static final String DATE_TYPE = " DATETIME";
    private static final String COMMA_SEP = " ,";
    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE " + CompanyReaderContract.CompanyEntry.TABLE_NAME +
            " (" + CompanyReaderContract.CompanyEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + CompanyReaderContract.CompanyEntry.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
            CompanyReaderContract.CompanyEntry.COLUMN_NAME_DESCRIPTION + TEXT_TYPE + COMMA_SEP + CompanyReaderContract.CompanyEntry.COLUMN_NAME_IMAGE_PATH + TEXT_TYPE + COMMA_SEP +
            CompanyReaderContract.CompanyEntry.COLUMN_NAME_CREATED_AT + DATE_TYPE + " )";
    private static final String SQL_DELETE_ENTRIES = "DRP TABLE IF EXISTS " + CompanyReaderContract.CompanyEntry.TABLE_NAME;

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "CompanyReader.db";

    public CompanyReaderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES);
        onCreate(sqLiteDatabase);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }

    public Long addCompany(Company company) {
        SQLiteDatabase db = getWritableDatabase();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ");
        Calendar calendar = Calendar.getInstance();
        ContentValues values = new ContentValues();

        values.put(CompanyReaderContract.CompanyEntry.COLUMN_NAME_TITLE, company.getTitle());
        values.put(CompanyReaderContract.CompanyEntry.COLUMN_NAME_DESCRIPTION, company.getDescription());
        values.put(CompanyReaderContract.CompanyEntry.COLUMN_NAME_IMAGE_PATH, company.getImagePath());
        values.put(CompanyReaderContract.CompanyEntry.COLUMN_NAME_CREATED_AT, format.format(calendar.getTime()));

        Long newRowId = db.insert(CompanyReaderContract.CompanyEntry.TABLE_NAME, CompanyReaderContract.CompanyEntry.COLUMN_NAME_NULLABLE, values);

        return newRowId;
    }

    public boolean deleteById() {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(CompanyReaderContract.CompanyEntry.TABLE_NAME, CompanyReaderContract.CompanyEntry._ID + "=?", new String[]{String.valueOf(CustomAdapter.ViewHolder.COMPANY.getId())}) > 0;
    }


    public void updateCompanyById(Company company){

        SQLiteDatabase db = getWritableDatabase();

        ContentValues data = new ContentValues();
        data.put(CompanyReaderContract.CompanyEntry.COLUMN_NAME_TITLE,company.getTitle());
        data.put(CompanyReaderContract.CompanyEntry.COLUMN_NAME_DESCRIPTION,company.getDescription());
        data.put(CompanyReaderContract.CompanyEntry.COLUMN_NAME_IMAGE_PATH, company.getImagePath());

        db.update(CompanyReaderContract.CompanyEntry.TABLE_NAME,data,"_id=?",new String[]{String.valueOf(company.getId())});




    }


    public List<Company> getAllCompanies() {
        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                CompanyReaderContract.CompanyEntry._ID,
                CompanyReaderContract.CompanyEntry.COLUMN_NAME_TITLE,
                CompanyReaderContract.CompanyEntry.COLUMN_NAME_DESCRIPTION,
                CompanyReaderContract.CompanyEntry.COLUMN_NAME_IMAGE_PATH,
                CompanyReaderContract.CompanyEntry.COLUMN_NAME_CREATED_AT
        };

        List<Company> companyList = new ArrayList<Company>();
        SQLiteDatabase db = getReadableDatabase();
        String sortOrder = CompanyReaderContract.CompanyEntry.COLUMN_NAME_CREATED_AT + " DESC";

        Cursor cursor = db.query(CompanyReaderContract.CompanyEntry.TABLE_NAME, projection, null, null, null, null, sortOrder);

        if (cursor.moveToFirst()) {
            do {
                Company company = new Company();
                company.setId(Integer.valueOf(cursor.getString(0)));
                company.setTitle(cursor.getString(1));
                company.setDescription(cursor.getString(2));
                company.setImagePath(cursor.getString(3));
                company.setCreated_at(cursor.getString(4));

                companyList.add(company);

            } while (cursor.moveToNext());
        }
        cursor.close();
        return companyList;
    }

    public List<Company> getAllCompaniesByTitle() {
        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                CompanyReaderContract.CompanyEntry._ID,
                CompanyReaderContract.CompanyEntry.COLUMN_NAME_TITLE,
                CompanyReaderContract.CompanyEntry.COLUMN_NAME_DESCRIPTION,
                CompanyReaderContract.CompanyEntry.COLUMN_NAME_IMAGE_PATH,
                CompanyReaderContract.CompanyEntry.COLUMN_NAME_CREATED_AT
        };

        List<Company> companyList = new ArrayList<Company>();
        SQLiteDatabase db = getReadableDatabase();
        String sortOrder = CompanyReaderContract.CompanyEntry.COLUMN_NAME_TITLE + " ASC";

        Cursor cursor = db.query(CompanyReaderContract.CompanyEntry.TABLE_NAME, projection, null, null, null, null, sortOrder);

        if (cursor.moveToFirst()) {
            do {
                Company company = new Company();
                company.setId(Integer.valueOf(cursor.getString(0)));
                company.setTitle(cursor.getString(1));
                company.setDescription(cursor.getString(2));
                company.setImagePath(cursor.getString(3));
                company.setCreated_at(cursor.getString(4));

                companyList.add(company);

            } while (cursor.moveToNext());
        }
        cursor.close();
        return companyList;
    }

    public List<Company> getAllCompaniesByCreated_At() {
        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                CompanyReaderContract.CompanyEntry._ID,
                CompanyReaderContract.CompanyEntry.COLUMN_NAME_TITLE,
                CompanyReaderContract.CompanyEntry.COLUMN_NAME_DESCRIPTION,
                CompanyReaderContract.CompanyEntry.COLUMN_NAME_IMAGE_PATH,
                CompanyReaderContract.CompanyEntry.COLUMN_NAME_CREATED_AT
        };

        List<Company> companyList = new ArrayList<Company>();
        SQLiteDatabase db = getReadableDatabase();
        String sortOrder = CompanyReaderContract.CompanyEntry.COLUMN_NAME_TITLE + " ASC";

        Cursor cursor = db.query(CompanyReaderContract.CompanyEntry.TABLE_NAME, projection, null, null, null, null, sortOrder);

        if (cursor.moveToFirst()) {
            do {
                Company company = new Company();
                company.setId(Integer.valueOf(cursor.getString(0)));
                company.setTitle(cursor.getString(1));
                company.setDescription(cursor.getString(2));
                company.setImagePath(cursor.getString(3));
                company.setCreated_at(cursor.getString(4));

                companyList.add(company);

            } while (cursor.moveToNext());
        }
        cursor.close();
        return companyList;
    }
}



