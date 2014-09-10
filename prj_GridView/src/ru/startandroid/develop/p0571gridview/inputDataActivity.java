package ru.startandroid.develop.p0571gridview;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.TimePicker;

public class inputDataActivity extends Activity 
{
	final String LOG_TAG = "myLogs";
	TextView tvTimeBegin;
	int DIALOG_TIME_BEGIN = 1;
	
	int myHour = 14;
	int myMinute = 35;
	
	String fDate;
	String fMonth;
	String fYear;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.inputdata);
		tvTimeBegin = (TextView) findViewById(R.id.tvTimeBeginInput);
		
		Intent intent = getIntent();		
		fDate = intent.getStringExtra("fdate");
	    fMonth = intent.getStringExtra("fmonth");
	    fYear = intent.getStringExtra("fyear");
	}
	
	public void onclick(View view)
	{
		showDialog(DIALOG_TIME_BEGIN);
		//Intent intent = new Intent(this, );
	}
	
	protected Dialog onCreateDialog(int id)
	{
		if (id == DIALOG_TIME_BEGIN)
		{
			TimePickerDialog tpd = new TimePickerDialog(this, myCallBack, myHour, myMinute, true);
			return tpd;
		}
		return super.onCreateDialog(id);
	}
	
	OnTimeSetListener myCallBack = new OnTimeSetListener() 
	{
		
		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) 
		{
			myHour = hourOfDay;
			myMinute = minute;
			Log.d(LOG_TAG, myHour + ":" + myMinute + " date: " + fDate + "/" + fMonth + "/" + fYear);
			tvTimeBegin.setText(myHour + ":" + myMinute);
		}
	};
	
}
