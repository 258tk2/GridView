package ru.startandroid.develop.p0571gridview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class PlanActivity extends Activity implements OnClickListener
{
	// имена атрибутов для Map
	  final String ATTR_DATE_BEGIN = "datebegin";
	  final String ATTR_DATE_END = "dateend";
	  final String ATTR_PLAN_TEXT = "plantext";
	  
	  final int DB_VERSION = 2; // версия БД

	  ListView lvPlan;
	  DB db;
	  Cursor cursor;
	  SimpleCursorAdapter scAdapter;
	  
	  Button btnAddNote;
	  
	  final String LOG_TAG = "myLogs";
	  String[] daychecked;
	  
	  String fDate;
	  String fMonth;
	  String fYear;
	  
	  public void onCreate(Bundle savedInstanceState) 
	  {
		  super.onCreate(savedInstanceState);
		    setContentView(R.layout.planmain);
	
		    Log.d(LOG_TAG, "layout planmain");
		    
		    btnAddNote = (Button) findViewById(R.id.btnAddNote);
		    btnAddNote.setOnClickListener(this);
		    		    		   
		    // массивы данных
		    String[] time1 = { "09:00", "10:00", "11:00" };
		    String[] time2 = { "09:30", "10:30", "11:30" };
		    String[] plan = { "kill humanity", "devastate everything", "destroy universe" };
		   
		    Intent intent = getIntent();		    
		    fDate = intent.getStringExtra("fdate");
		    fMonth = intent.getStringExtra("fmonth");
		    fYear = intent.getStringExtra("fyear");
		    
		    Log.d(LOG_TAG, "date is " + fDate + "/" + fMonth);
		    
		    // упаковываем данные в понятную для адаптера структуру
		    ArrayList<Map<String, Object>> data = new ArrayList<Map<String, Object>>(time1.length);
		    Map<String, Object> m;
		    
		    /*
		    for (int i = 0; i < time1.length; i++) {
		      m = new HashMap<String, Object>();
		      m.put(ATTR_DATE_BEGIN, time1[i]);
		      m.put(ATTR_DATE_END, time2[i]);
		      m.put(ATTR_PLAN_TEXT, plan[i]);
		      data.add(m);
		    }
		    */
		    
		    // открываем подключение к БД
		    db = new DB(this);
		    
		    db.open();
		    //db.dbDelete();
		    db.addRec("2014", "05", "18", "09:00", "10:00", "test");
		    
		    //db.delAllRec();
		   
		    // получаем курсор
		    cursor = db.getAllData();		    		   
		    
		    String[] columnnames = cursor.getColumnNames();
		    Log.d(LOG_TAG, "columnlenght is - " + columnnames.length);
		    for (int i = 0; i < columnnames.length; i++)
		    {
		    	Log.d(LOG_TAG, "column - " + columnnames[i]);
		    }
		    db.getAllTablesName();
		    
		    startManagingCursor(cursor);
		    Log.d(LOG_TAG, "startManagingCursor");
		    		    
		    if (cursor.moveToFirst()) 
		    {
		    	do
		    	{		    		
		         int time_beginIndex = cursor.getColumnIndex("time_begin");
		         int descIndex = cursor.getColumnIndex("description");
		         Log.d(LOG_TAG, cursor.getString(time_beginIndex)  
		        		 + "   " + cursor.getString(cursor.getColumnIndex("time_end")) + "   " + cursor.getString(descIndex)
		        		 + "   " + cursor.getString(cursor.getColumnIndex("year"))
		        		 + "   " + cursor.getString(cursor.getColumnIndex("month"))
		        		 + "   " + cursor.getString(cursor.getColumnIndex("date"))
		        		 );
		    	
		    		//Log.d(LOG_TAG, cursor.getString(time_beginIndex) + "   " + cursor.getString(descIndex));
		    	} while (cursor.moveToNext());
		    }
		    
		    // формируем столбцы сопоставления
		    //String[] from = new String[] { DB.COLUMN_YEAR, DB.COLUMN_MONTH, DB.COLUMN_DATE, DB.COLUMN_TIME_BEGIN,  DB.COLUMN_TIME_END, DB.COLUMN_DESCRIPTION };
		    String[] from = new String[] { DB.COLUMN_TIME_BEGIN,  DB.COLUMN_TIME_END, DB.COLUMN_DESCRIPTION };
		    daychecked = from;
		    int[] to = new int[] { R.id.txtTimeBegin, R.id.txtTimeEndOutput, R.id.txtDescriptionOutput };
		    
/*		    
		    // массив имен атрибутов, из которых будут читаться данные
		    String[] from = { ATTR_DATE_BEGIN, ATTR_DATE_END, ATTR_PLAN_TEXT };
		    // массив ID View-компонентов, в которые будут вставлять данные
		    int[] to = { R.id.txtTimeBegin, R.id.txtTimeEnd, R.id.txtPlan };
*/
		    
		    // создаем адаптер
		    //SimpleAdapter sAdapter = new SimpleAdapter(this, data, R.layout.dayplan, from, to);
		    
		    scAdapter = new SimpleCursorAdapter(this, R.layout.dayplan, cursor, from, to);
		    
		    
		    // определяем список и присваиваем ему адаптер
		    lvPlan = (ListView) findViewById(R.id.lvPlan);
		    //lvPlan.setAdapter(sAdapter);
		    lvPlan.setAdapter(scAdapter);
	  }
	  
	  public void onClick(View view)
	  {
		  Intent intent = new Intent(this, WeekdayActivity.class);
		  intent.putExtra("fdate",  fDate);
  		  intent.putExtra("fmonth",  fMonth);
  		  intent.putExtra("fyear",  fYear);
		  startActivity(intent);
		  //Log.d(LOG_TAG, "checked: " + daychecked[lvPlan.getCheckedItemPosition()]);
		  //Log.d(LOG_TAG, "checked: 111");
	  }

}
