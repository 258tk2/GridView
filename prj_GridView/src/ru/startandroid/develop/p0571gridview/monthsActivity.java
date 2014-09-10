package ru.startandroid.develop.p0571gridview;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class monthsActivity extends Activity 
{
	
	final String LOG_TAG = "myLogs";
	String[] data = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k"};	
	GridView gvMain;
	ArrayAdapter<String> adapter;
	String[] d;
	TextView MonYr;	
	TextView tvDays;	
	
	//ArrayList<Integer> days = new ArrayList<Integer>(); 
	ArrayList<String> days = new ArrayList<String>();
			
	Calendar month = Calendar.getInstance();
	Calendar itemmonth = (Calendar) month.clone();	   
	Calendar pastmonth = (Calendar) month.clone();    	
    	
	myAdapter mAdapter;
	
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.weekdays);
		Log.d(LOG_TAG, "start weekdays activity");	
				
		//tvDays = (TextView) findViewById(R.id.textDays);
		//tvDays.setTextColor(Color.RED);
		
		MonYr = (TextView) findViewById(R.id.textMonthYear);	
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
		final String strYear = formatter.format(new Date());
				
		
		Intent intent = getIntent();
		//String fposition = intent.getStringExtra("fposition");
		final int fMonth = intent.getIntExtra("fmonth", 0);
		
		//month.set(Calendar.MONTH, month.get(Calendar.MONTH) + 1);
		month.set(Calendar.MONTH, fMonth);
		Log.d(LOG_TAG, "current year is " + strYear);				
			
		
		itemmonth.set(2014, fMonth, 1);
		// день недели 1-го числа мес€ца в календаре
		final int iDay = itemmonth.get(Calendar.DAY_OF_WEEK) - 1; 
		String tDay = new Integer(iDay).toString();
		Log.d(LOG_TAG, "day is " + tDay);
		
		
		MonYr.setText(getMonth(fMonth) + " " + strYear + "   " + tDay);
		
		// заполн€ем дн€ми из прошлого мес€ца (серые дни)
		pastmonth.set(2014, fMonth - 1, 1);	// устанавливаем прошлый мес€ц
		int pastMonthDaysMax = pastmonth.getActualMaximum(Calendar.DAY_OF_MONTH);
		// цикл до tDay 
		for (int i = 1; i < iDay; i++)
		{
			days.add(String.valueOf(pastMonthDaysMax - iDay + 1 + i));
			//days.add(String.valueOf(i));
			//Log.d(LOG_TAG, days.get(i).toString());			
		}
		
		
		//MonYr.setText(month.MONTH + " " + month.YEAR);
		String sDate = new Integer(month.get(Calendar.MONTH)).toString();
		int max_date = month.getActualMaximum(Calendar.DAY_OF_MONTH);
		Log.d(LOG_TAG, "position = " + fMonth + " month: " + sDate + " " + new Integer(max_date).toString());		
		
		for (int i = 1; i <= max_date; i++)
		{
			days.add(String.valueOf(i));
			//Log.d(LOG_TAG, days.get(i).toString());			
		}
		
		
		mAdapter = new myAdapter(this, days, iDay);
		
		//adapter = new ArrayAdapter<String>(this, R.layout.days, R.id.textDays, days);
		gvMain = (GridView) findViewById(R.id.gvMain3);
        //gvMain.setAdapter(adapter);
		gvMain.setAdapter(mAdapter);
        gvMain.setOnItemClickListener(new OnItemClickListener() 
        {        	
        	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        	{
        		Intent intent = new Intent(getApplicationContext(), PlanActivity.class);
        		// передаем дату в активити плана
        		intent.putExtra("fdate",  new Integer(position - iDay + 2).toString());
        		intent.putExtra("fmonth",  new Integer(fMonth + 1).toString());
        		intent.putExtra("fyear", strYear);
        		startActivity(intent);
        		Log.d(LOG_TAG, "itemClick: position = " + position + ", id = " + id);        		
        	}
		});
        adjustGridView(7);
        
	}
	
	private void adjustGridView(int numColumns) 
	{
		gvMain.setNumColumns(numColumns);
		gvMain.setVerticalSpacing(5);
	    gvMain.setHorizontalSpacing(5);
	}
	
	
	public String getMonth(int mon)
	{
		String[] months = { "январь", "‘евраль", "ћарт", "јпрель", "ћай", "»юнь",
				"»юль", "јвгуст", "—ент€брь", "ќкт€брь", "Ќо€брь", "ƒекабрь" };
		
		return(months[mon]);
	}
		
}
