package com.example.transportcostcomparator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "TransportTracker.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "reports";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_MODE = "mode";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_DISTANCE = "distance";
    public static final String COLUMN_COST_PER_KM = "cost_per_km";
    public static final String COLUMN_TRAVEL_DAYS = "travel_days";
    public static final String COLUMN_DAILY_COST = "daily_cost";
    public static final String COLUMN_MONTHLY_COST = "monthly_cost";
    public static final String COLUMN_MONTHLY_DISTANCE = "monthly_distance";
    public static final String COLUMN_DATE = "date_created";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_MODE + " TEXT, " +
                COLUMN_TYPE + " TEXT, " +
                COLUMN_DISTANCE + " REAL, " +
                COLUMN_COST_PER_KM + " REAL, " +
                COLUMN_TRAVEL_DAYS + " INTEGER, " +
                COLUMN_DAILY_COST + " REAL, " +
                COLUMN_MONTHLY_COST + " REAL, " +
                COLUMN_MONTHLY_DISTANCE + " REAL, " +
                COLUMN_DATE + " TEXT)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Insert a new transport report
    public long insertReport(String mode, String type, double distance, double costPerKm,
                             int travelDays, double dailyCost, double monthlyCost, double monthlyDistance) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        String currentDate = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(new Date());

        values.put(COLUMN_MODE, mode);
        values.put(COLUMN_TYPE, type);
        values.put(COLUMN_DISTANCE, distance);
        values.put(COLUMN_COST_PER_KM, costPerKm);
        values.put(COLUMN_TRAVEL_DAYS, travelDays);
        values.put(COLUMN_DAILY_COST, dailyCost);
        values.put(COLUMN_MONTHLY_COST, monthlyCost);
        values.put(COLUMN_MONTHLY_DISTANCE, monthlyDistance);
        values.put(COLUMN_DATE, currentDate);

        long id = db.insert(TABLE_NAME, null, values);
        db.close();
        return id;
    }

    // Retrieve a specific report by its ID for the Results screen
    public Transport getReport(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            Transport transport = new Transport();
            transport.setModeOfTransport(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MODE)));
            transport.setTransportType(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TYPE)));
            transport.setDistancePerDay(cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_DISTANCE)));
            transport.setCostPerKm(cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_COST_PER_KM)));
            transport.setTravelDays(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_TRAVEL_DAYS)));
            transport.setDailyCost(cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_DAILY_COST)));
            transport.setMonthlyCost(cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_MONTHLY_COST)));
            transport.setTotalMonthlyDistance(cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_MONTHLY_DISTANCE)));
            transport.setDateCreated(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE)));

            cursor.close();
            db.close();
            return transport;
        }
        if (cursor != null) {
            cursor.close();
        }
        db.close();
        return null;
    }

    public List<Transport> getAllReports() {
        List<Transport> reports = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, COLUMN_ID + " DESC");

        if (cursor.moveToFirst()) {
            do {
                Transport t = new Transport();
                t.setModeOfTransport(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MODE)));
                t.setTransportType(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TYPE)));
                t.setDistancePerDay(cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_DISTANCE)));
                t.setCostPerKm(cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_COST_PER_KM)));
                t.setTravelDays(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_TRAVEL_DAYS)));
                t.setDailyCost(cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_DAILY_COST)));
                t.setMonthlyCost(cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_MONTHLY_COST)));
                t.setTotalMonthlyDistance(cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_MONTHLY_DISTANCE)));
                t.setDateCreated(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE)));
                reports.add(t);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return reports;
    }
}