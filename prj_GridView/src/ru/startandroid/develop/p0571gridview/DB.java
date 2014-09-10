package ru.startandroid.develop.p0571gridview;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DB 
{
  
  private static final String DB_NAME = "mydb";
  private static final int DB_VERSION = 2;
  private static final String DB_TABLE = "mytab";
  
  public static final String COLUMN_ID = "_id";
  
  public static final String COLUMN_YEAR = "year";
  public static final String COLUMN_MONTH = "month";
  public static final String COLUMN_DATE = "date";
  public static final String COLUMN_TIME_BEGIN = "time_begin";
  public static final String COLUMN_TIME_END = "time_end";
  public static final String COLUMN_DESCRIPTION = "description";
  
  final String LOG_TAG = "myLogs";

/*  
  private static final String DB_CREATE = 
    "create table " + DB_TABLE + "(" +
      COLUMN_ID + " integer primary key autoincrement, " +
      COLUMN_TIME + " text, " +
      COLUMN_DESCRIPTION + " text" +
    ");";
*/
  
  private static final String DB_CREATE = 
		    "create table " + DB_TABLE + "(" +
		      COLUMN_YEAR + " varchar(4) not null, " +
		      COLUMN_MONTH + " varchar(2) not null, " +		
		      COLUMN_DATE + " varchar(2) not null, " +
		      COLUMN_TIME_BEGIN + " time, " +
		      COLUMN_TIME_END + " time, " +
		      COLUMN_DESCRIPTION + " text, " +
		      "primary key (" + COLUMN_YEAR + "," + COLUMN_MONTH + "," + COLUMN_DATE + "," + COLUMN_TIME_BEGIN + "," + COLUMN_TIME_END + ")" + 
		    ");";
  
  private final Context mCtx;
  
  
  private DBHelper mDBHelper;
  private SQLiteDatabase mDB;
  
  public DB(Context ctx) 
  {
    mCtx = ctx;
  }
  
  /*
  public void dbFill(SQLiteDatabase db)
  {
  	ContentValues cv = new ContentValues();
      for (int i = 1; i < 5; i++) 
      {
        cv.put(COLUMN_TIME, "09-0" + i);
        cv.put(COLUMN_DESCRIPTION, "text_" + i);
        db.insert(DB_TABLE, null, cv);
        Log.d(LOG_TAG, "i =  " + i);
      }
  }
  */
  
  public void dbDelete()
  {
	  mDB.execSQL("drop table mytab");
	  Log.d(LOG_TAG, "table mytab was deleted");
  }
  
  public void dbCreate(SQLiteDatabase db)
  {
	  db.execSQL(DB_CREATE);      
      Log.d(LOG_TAG, "table was created");
  }
  
  // открыть подключение
  public void open() 
  {
    mDBHelper = new DBHelper(mCtx, DB_NAME, null, DB_VERSION);
    mDB = mDBHelper.getWritableDatabase();
    
    //dbCreate(mDB);
    //dbFill(mDB);
    //dbDelete(mDB);    
    Log.d(LOG_TAG, "DB.open");
  }
  
  // закрыть подключение
  public void close() 
  {
    if (mDBHelper != null) 
    	mDBHelper.close();
  }
  
  // получить все данные из таблицы DB_TABLE
  public Cursor getAllData() 
  {
	Log.d(LOG_TAG, "cursor getalldata"); 
    return mDB.query(DB_TABLE, null, null, null, null, null, null);    
  }
  
  // getAllTablesName() показать имена всех таблиц в базе
  public void getAllTablesName()
  {
	  Cursor c = mDB.rawQuery("SELECT name FROM sqlite_master WHERE type = 'table'", null);
	  
	  if (c.moveToFirst()) 
		  do
	  {
		  Log.d(LOG_TAG, "Table Name is : " + c.getString(0));
	  } while (c.moveToNext());
  }
  
  // добавить запись в DB_TABLE
  public void addRec(String txtYear, String txtMonth, String txtDate, String txtTimeBegin, String txtTimeEnd, String desc) 
  {
	  try
	  {
		Log.d(LOG_TAG, "add rec start");  
	    ContentValues cv = new ContentValues();
	    cv.put(COLUMN_YEAR, txtYear);
	    cv.put(COLUMN_MONTH, txtMonth);
	    cv.put(COLUMN_DATE, txtDate);
	    cv.put(COLUMN_TIME_BEGIN, txtTimeBegin);
	    cv.put(COLUMN_TIME_END, txtTimeEnd);
	    cv.put(COLUMN_DESCRIPTION, desc);
	    mDB.insert(DB_TABLE, null, cv);
	    Log.d(LOG_TAG, "add rec end");
	  }
	  catch(SQLiteException e)
	  {
	        Log.d("LOG_TAG", e.getMessage());
	  }
  }
  
  // удалить запись из DB_TABLE
  public void delRec(long id) 
  {
    mDB.delete(DB_TABLE, COLUMN_ID + " = " + id, null);
  }
  
//удалить все записи из DB_TABLE
 public void delAllRec() 
 {
   mDB.delete(DB_TABLE, null, null);
 }
 
  // класс по созданию и управлению Ѕƒ
  private class DBHelper extends SQLiteOpenHelper 
  {	

    public DBHelper(Context context, String name, CursorFactory factory, int version) 
    {
      super(context, name, factory, DB_VERSION);
      Log.d(LOG_TAG, "DBHelper");             
    }
    
    // создаем и заполн€ем Ѕƒ
    @Override
    public void onCreate(SQLiteDatabase db) 
    {
      Log.d(LOG_TAG, "DB onCreate");
      db.execSQL(DB_CREATE);      
      Log.d(LOG_TAG, "DB was created");

      /*
      ContentValues cv = new ContentValues();
      for (int i = 1; i < 5; i++) 
      {
        cv.put(COLUMN_TIME, "09-0" + i);
        cv.put(COLUMN_DESCRIPTION, "text_" + i);
        db.insert(DB_TABLE, null, cv);
        Log.d(LOG_TAG, "i =  " + i);
      }
      */
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
    {    	
    	Log.d(LOG_TAG, " --- onUpgrade database from " + oldVersion
    	          + " to " + newVersion + " version --- ");
    	
    	db.beginTransaction();
        try 
        {
        	
        	Log.d(LOG_TAG, "DB onCreate");
            db.execSQL(DB_CREATE);      
            Log.d(LOG_TAG, "DB was created");
        
            db.setTransactionSuccessful();
        } finally {
        	db.endTransaction();
        }
        	
    	/*
    	if (oldVersion == 1 && newVersion == 2) 
    	{
            ContentValues cv = new ContentValues();
    	}
    	*/
    	
    	//if (newVersion == 2)
    	//	db.execSQL("drop table mytab;");
    }
        
  }
}