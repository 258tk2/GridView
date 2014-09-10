package ru.startandroid.develop.p0571gridview;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	// ver 2
	final String LOG_TAG = "myLogs";

	String[] months = { "Январь", "Февраль", "Март", "Апрель", "Май", "Июнь",
						"Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь" };
	
	//String[] data = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k"};
	
	GridView gvMain;
	ArrayAdapter<String> adapter;
	TextView Yr;	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		Yr = (TextView) findViewById(R.id.txtYear);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
		String strYear = formatter.format(new Date());
		Yr.setText(strYear);
		
		//adapter = new ArrayAdapter<String>(this, R.layout.item, R.id.tvText, months);
		adapter = new ArrayAdapter<String>(this, R.layout.months, R.id.textMonths, months);
		gvMain = (GridView) findViewById(R.id.gvMain);
        gvMain.setAdapter(adapter);
        gvMain.setOnItemClickListener(new OnItemClickListener() 
        {        	
        	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        	{
        		Log.d(LOG_TAG, "itemClick: position = " + position + ", id = " + id);
        		Intent intent = new Intent(getApplicationContext(), monthsActivity.class);
        		//intent.putExtra("fposition", new Integer(position).toString());
        		intent.putExtra("fmonth", position);
        		startActivity(intent);
        	}
		});
        adjustGridView(1);
	}

	private void adjustGridView(int numColumns) 
	{
		gvMain.setNumColumns(numColumns);
		gvMain.setVerticalSpacing(5);
	    gvMain.setHorizontalSpacing(5);
	}
	
}
