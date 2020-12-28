package com.elancier.healthzone.Common;

import android.util.Log;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;

public class Helper {
	
	public static void getListViewSize(ListView listView) {

	    ListAdapter mAdapter = listView.getAdapter();

	    int totalHeight = 0;

	    for (int i = 0; i < mAdapter.getCount(); i++) {
	        View mView = mAdapter.getView(i, null, listView);

	        mView.measure(
	                MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),

	                MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
	      

	        totalHeight += mView.getMeasuredHeight();
	        Log.w("HEIGHT" + i, String.valueOf(totalHeight));

	    }

	    ViewGroup.LayoutParams params = listView.getLayoutParams();
	    params.height = totalHeight
	            + (listView.getDividerHeight() * (mAdapter.getCount() - 1));
	    listView.setLayoutParams(params);
	    listView.requestLayout();

	}  
	public static void getListViewSize1(ListView listView) {

	    ListAdapter mAdapter = listView.getAdapter();

	    int totalHeight = 0;

	    for (int i = 0; i < mAdapter.getCount(); i++) {
	        View mView = mAdapter.getView(i, null, listView);

	       
	        mView.measure( MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);

	        totalHeight += mView.getMeasuredHeight();
	        Log.w("HEIGHT" + i, String.valueOf(totalHeight));

	    }

	    ViewGroup.LayoutParams params = listView.getLayoutParams();
	    params.height = totalHeight
	            + (listView.getDividerHeight() * (mAdapter.getCount() - 1));
	    listView.setLayoutParams(params);
	    listView.requestLayout();

	}
	public static void getgridViewSize(GridView listView) {

		ListAdapter mAdapter = listView.getAdapter();

		int totalHeight = 0;

		for (int i = 0; i < mAdapter.getCount()/3; i++) {
			View mView = mAdapter.getView(i, null, listView);

			mView.measure(
					MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),

					MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));

			totalHeight += mView.getMeasuredHeight();
			Log.i("HEIGHT" + i, String.valueOf(totalHeight));

		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (mAdapter.getCount() - 1);
		Log.i("heightttttttttttt",
				totalHeight
						+ (mAdapter.getCount() - 1)+"");
		listView.setLayoutParams(params);
		listView.requestLayout();

	}
}