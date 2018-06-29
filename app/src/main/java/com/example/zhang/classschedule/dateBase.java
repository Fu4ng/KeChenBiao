package com.example.zhang.classschedule;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class dateBase extends SQLiteOpenHelper {
    private static final String _DBNAME = "CLASSDB.db";
    private static final String _TABLE1 = "classes";
    //    private static final String _TABLE2 = "weeks";
    private static final String _ID = "classId";
    private static final String _NAME = "className";
    private static final String _ROOM = "classRoom";
    private static final String _WEEKS = "weeks";
    private static final String _IFDOUBLE = "ifDouble";
    private static final String _IFSINGAL = "ifSingal";
    private static final String _WEEKSTART = "weekStart";
    private static final String _WEEKEND = "weekEnd";
    private static final String _PositionX = "P_X";
    private static final String _PositionY = "P_Y";


    public dateBase(Context context) {
        super(context, _DBNAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        if (db.getVersion() < 1) {
        db.execSQL("CREATE TABLE " + _TABLE1 +
                "(" + _ID + " TEXT PRIMARY KEY,"
                + _NAME + " TEXT," + _ROOM + " TEXT,"
                + _WEEKS + " TEXT, " + _WEEKSTART + " TEXT," + _WEEKEND + " TEXT,"
                + _IFDOUBLE + " TEXT," + _IFSINGAL + " TEXT,"
                + _PositionX + " TEXT," + _PositionY + " TEXT)");
        db.setVersion(1);

        Log.e("数据库", "创建成功！");
//        }


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


    }

    public void addClass(aClass tempClass) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        int count;
        Cursor myCursor = myDB.query(_TABLE1, null, null
                , null, null, null, null);
        if (myCursor.getCount() < 0) {
            count = 0;
        } else {
            count = myCursor.getCount();
        }

        ContentValues aClass = new ContentValues();
        aClass.put(_ID, count++);
        aClass.put(_NAME, tempClass.getClassName());
        aClass.put(_ROOM, tempClass.getClassRoom());
        aClass.put(_WEEKSTART, tempClass.getWeekStart());
        aClass.put(_WEEKEND, tempClass.getWeekEnd());
        aClass.put(_IFSINGAL, tempClass.getIfSingal());
        aClass.put(_IFDOUBLE, tempClass.getIfDouble());
        aClass.put(_PositionX, tempClass.getPoX());
        aClass.put(_PositionY, tempClass.getPoY());

        try{
            myDB.insert(_TABLE1, null, aClass);
            Log.e("数据库", "插入成功！");
        }
        catch (Exception e){
            Log.e("数据库","插入异常！");
        }
        finally {
            myDB.close();
        }
    }

    //查询一条记录
    public aClass getOne(int Po_x, int Po_y) {
        SQLiteDatabase myDB = this.getReadableDatabase();
        aClass tempClass = new aClass();
        String selectQuery = "SELECT * FROM " + _TABLE1 + " WHERE " + _PositionX
                + " = " + Po_x + " AND " + _PositionY + " = " + Po_y;
        Cursor myCursor = myDB.rawQuery(selectQuery, null);
        if (myCursor.getCount() > 0) {
            myCursor.moveToFirst();
            Log.e("数据库", "查询到记录！");
            tempClass.setClassName(myCursor.getString(myCursor.getColumnIndex(_NAME)));
            tempClass.setClassRoom(myCursor.getString(myCursor.getColumnIndex(_ROOM)));
            tempClass.setIfSingal(myCursor.getString(myCursor.getColumnIndex(_IFSINGAL)).equals("1"));
            tempClass.setIfDouble(myCursor.getString(myCursor.getColumnIndex(_IFDOUBLE)).equals("1"));
            tempClass.setWeekStart(myCursor.getInt(myCursor.getColumnIndex(_WEEKSTART)));
            tempClass.setWeekEnd(myCursor.getInt(myCursor.getColumnIndex(_WEEKEND)));
            tempClass.setPositionX(Integer.valueOf(myCursor.getString(myCursor.getColumnIndex(_PositionX))));
            tempClass.setPositionY(Integer.valueOf(myCursor.getString(myCursor.getColumnIndex(_PositionY))));
        }
        myDB.close();
        return tempClass;
    }


    public ArrayList<aClass> getAll() {
        SQLiteDatabase myDB = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + _TABLE1;
//        Cursor myCursor = myDB.query(_TABLE1, null, null
//                , null, null, null, null);
        Cursor myCursor = myDB.rawQuery(selectQuery, null);
        int count = myCursor.getCount();
        ArrayList<aClass> tempList = new ArrayList<aClass>();
        Log.e("数据库", "共查询到" + myCursor.getCount() + "条记录！");
        if (myCursor != null) {
            if (myCursor.moveToFirst()) {
                do {
                    aClass tempClass = new aClass();
                    tempClass.setClassName(myCursor.getString(myCursor.getColumnIndex(_NAME)));
                    tempClass.setClassRoom(myCursor.getString(myCursor.getColumnIndex(_ROOM)));
                    tempClass.setWeekStart(myCursor.getInt(myCursor.getColumnIndex(_WEEKSTART)));
                    tempClass.setWeekEnd(myCursor.getInt(myCursor.getColumnIndex(_WEEKEND)));
                    tempClass.setPositionX(Integer.valueOf(myCursor.getString(myCursor.getColumnIndex(_PositionX))));
                    tempClass.setPositionY(Integer.valueOf(myCursor.getString(myCursor.getColumnIndex(_PositionY))));
                    tempClass.setIfSingal(myCursor.getString(myCursor.getColumnIndex(_IFSINGAL)) == "1");
                    tempClass.setIfDouble(myCursor.getString(myCursor.getColumnIndex(_IFDOUBLE)) == "1");
                    tempList.add(tempClass);
                } while (myCursor.moveToNext());
            }
            myCursor.close();
        }

        return tempList;
    }
}
