package ru.startandroid.develop.p0571gridview;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class myAdapter extends BaseAdapter {

	Context mContext;
	LayoutInflater lInflater;
	ArrayList<String> vDays;
	public static List<String> dayString;
	Calendar month, selectedDate;
	final String LOG_TAG = "myLogs";
	int PastDays;


	public myAdapter(Context c, ArrayList<String> days, int iDay) 
	{
		Log.d(LOG_TAG, "myAdapter");
		myAdapter.dayString = new ArrayList<String>();
		vDays = days;
		mContext = c;
		PastDays = iDay;
		lInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	
	  }
	
	@Override
	public int getCount() 
	{
		Log.d(LOG_TAG, "getCount");
		return(vDays.size());
	}

	@Override
	public Object getItem(int position) 
	{
		Log.d(LOG_TAG, "getItem");
		return(vDays.get(position));
	}

	@Override
	public long getItemId(int position) 
	{
		Log.d(LOG_TAG, "getItemId");
		//return(vDays.get(position));
		return(position);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		Log.d(LOG_TAG, "getView");
		View view = convertView;
		TextView dayView;
		if (convertView == null) 
		{
			view = lInflater.inflate(R.layout.days, parent, false);					
		}
						
		dayView = (TextView) view.findViewById(R.id.textDays);
		//if (((position % 7) == 0) || ((position % 8) == 0))
		if ((position == 6) || (position == 13) || (position == 20) || (position == 27) || (position == 34) 
				|| (position == 5) || (position == 12) || (position == 19) || (position == 26) || (position == 33))
		{
			dayView.setTextColor(Color.RED);
			dayView.setClickable(false);
			dayView.setFocusable(false);
		}
		
		if (position < (PastDays - 1))
			dayView.setTextColor(Color.WHITE);
		dayView.setText(getItem(position).toString());
		return view;
	}

}
