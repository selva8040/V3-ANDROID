package com.elancier.healthzone.Common;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import com.elancier.healthzone.Common.ScalingUtilities.ScalingLogic;

@SuppressLint("NewApi")
public class CommonFunctions {
	
	public static boolean checkLogin(String resp) {
		try{
		JSONObject json = new JSONObject(resp);
		Log.i("resp", resp);
		if(json.getBoolean("status") == true){
			return true;
		}else if(json.getBoolean("status") == false){
			if(json.has("data")){
				JSONTokener jsonTok = new JSONTokener(json.getString("data"));
				JSONObject jObj = new JSONObject(jsonTok);
				if(jObj.has("redirectToLogin") && jObj.getBoolean("redirectToLogin") == true){
					return false;
				}else{
					return true;
				}
			}else{
				return true;
			}
		}else{
			return true;
		}
		}catch(Exception e){
			e.printStackTrace();
			return true;
		}
	}
	public static String decodeFile(Context context, String path, int DESIREDWIDTH, int DESIREDHEIGHT) {
		String strMyImagePath = null;
		Bitmap scaledBitmap = null;

		try {

			Bitmap unscaledBitmap = ScalingUtilities.decodeFile(path,
					DESIREDWIDTH, DESIREDHEIGHT, ScalingLogic.FIT);

			if (!(unscaledBitmap.getWidth() <= DESIREDWIDTH && unscaledBitmap
					.getHeight() <= DESIREDHEIGHT)) {

				scaledBitmap = ScalingUtilities.createScaledBitmap(
						unscaledBitmap, DESIREDWIDTH, DESIREDHEIGHT,
						ScalingLogic.FIT);
			} else {
				scaledBitmap = unscaledBitmap;
			}
			File file = new File(path);
			String filename =file.getName().replace(" ","-");
			ContextWrapper contextWrapper = new ContextWrapper(context);
			File directory = contextWrapper.getFilesDir();
			File to = new File(directory , filename);
			strMyImagePath=to.getAbsolutePath();
			try{
				FileOutputStream fos = new FileOutputStream(to);
				scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 75, fos);
				fos.flush();
				fos.close();

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}

			scaledBitmap.recycle();
		} catch (Throwable e) {

		}

		if (strMyImagePath == null) {
			return path;
		}
		return strMyImagePath;

	}

	public static File createImageFile() {
		try{
			String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
			String imageFileName = "yoga_" + timeStamp + "_";
			File storageDir = Environment.getExternalStoragePublicDirectory(
					Environment.DIRECTORY_DCIM);
			File image = File.createTempFile(
					imageFileName,  /* prefix */
					".jpg",         /* suffix */
					storageDir      /* directory */
			);

			return image;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	public static Bitmap decodeFile1(String path, int DESIREDWIDTH, int DESIREDHEIGHT) {
		Bitmap scaledBitmap = null;
		try {

			Bitmap unscaledBitmap = ScalingUtilities.decodeFile(path,
					DESIREDWIDTH, DESIREDHEIGHT, ScalingUtilities.ScalingLogic.FIT);
			if (!(unscaledBitmap.getWidth() <= DESIREDWIDTH && unscaledBitmap
					.getHeight() <= DESIREDHEIGHT)) {
				scaledBitmap = ScalingUtilities.createScaledBitmap(
						unscaledBitmap, DESIREDWIDTH, DESIREDHEIGHT,
						ScalingLogic.FIT);
			} else {
				scaledBitmap = unscaledBitmap;
			}

		} catch (Throwable e) {

		}
		return scaledBitmap;

	}
	public static Bitmap decodeFile2(String path, int DESIREDWIDTH, int DESIREDHEIGHT) {
		Bitmap scaledBitmap = null;
		try {

			Bitmap unscaledBitmap = ScalingUtilities.decodeFile(path,
					DESIREDWIDTH, DESIREDHEIGHT, ScalingUtilities.ScalingLogic.FIT);
			if(DESIREDHEIGHT>0&&DESIREDHEIGHT>0) {
				if (!(unscaledBitmap.getWidth() <= DESIREDWIDTH && unscaledBitmap
						.getHeight() <= DESIREDHEIGHT)) {
					scaledBitmap = ScalingUtilities.createScaledBitmap(
							unscaledBitmap, DESIREDWIDTH, DESIREDHEIGHT,
							ScalingLogic.FIT);
				} else {
					scaledBitmap = unscaledBitmap;
				}
			}
			else{
				scaledBitmap = unscaledBitmap;
			}

		} catch (Throwable e) {

		}
		return scaledBitmap;

	}


	public static InputStream createfalseJson(){
		try{
		JSONObject json = new JSONObject();
		json.put("success", false);
		JSONObject lo = new JSONObject();
		lo.put("redirectToLogin", true);
		json.put("data", lo);
		return new ByteArrayInputStream(json.toString().getBytes("UTF-8"));
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	/*
	
	 public static ArrayList<DetailBo> getEmptyList(String typeid, int level, String name) {
		ArrayList<DetailBo> val = new ArrayList<DetailBo>();
	    DetailBo bo = new DetailBo();
		bo.setId(typeid);
		bo.setName(name);
		bo.setDesc("");
		bo.setOutletid("0");
		bo.setParentid(typeid);
		bo.setLevel(level);
		bo.setRank(0);
		bo.setMediaurl(null);
		bo.setCount(0);
		val.add(bo);
	    return val;
	  } 
	 */
	  public static String generateRandomEmail(){
	    	String orgKey = "";
	    	String keyInstance = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	    	Random numGen = new Random();
	    	StringBuilder keyCollector = new StringBuilder();
	    	
	    	for(int i=0;i<10;i++){
	    		int keySelector = numGen.nextInt(keyInstance.length());
	    		keyCollector.append(keyInstance.charAt(keySelector));
	    	}
	    	orgKey = new String(keyCollector);
	    	return orgKey+"@tomatotail.com";
	    }
	

	  

	  
	  
	  public static StringBuffer sendJsonHttpPost1(String url, String nameValuePairs) throws IOException{
	    	StringBuffer result = null;
	    	try {
	    			HttpURLConnection conn = null;
	    			DataInputStream inStream = null;
	    			try{
	    				 URL servletURL = new URL(url);	
	    	    		 conn = (HttpURLConnection) servletURL.openConnection();	
	    	    		 conn.setDoInput(true);
	    	             conn.setDoOutput(true);
	    	             conn.setUseCaches(false);
	    	             conn.setRequestMethod("POST");
	    	             conn.setRequestProperty("Accept", "application/json");
	    	             conn.setRequestProperty("Content-type", "application/json");
	    	             OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
	    	             osw.write(nameValuePairs);
	    	             osw.flush();
	    	             osw.close();
	    	      		 inStream = new DataInputStream ( conn.getInputStream() );
	    	      		 result = new StringBuffer();
	    	      		 int chr;
	    	      		 while ((chr=inStream.read())!=-1) {
	    	      			 result.append((char) chr);
	    	      		}
	    			}catch (IOException ioex){
	    	         	 throw ioex;
	    	         }
	    			catch(Exception e){
	    	         	 throw e;
	    			}finally{
	    					try{
	    		        		 if(inStream!=null)inStream.close();
	    			        }catch(Exception e){
	    	        	 }
	    	         }
	    	 	 }catch (Exception ex) {
	    	    	 ex.printStackTrace();
	    	     }   
	    	     return result;
	    }
	  
	  
	  private class PostmanTask extends AsyncTask<String, Void, String> {

			@Override
			protected void onPreExecute() {
				Log.i("PostmanTask", "started");
			}

			protected String doInBackground(String... param) {
				String response = null;
				try {
					
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
				return response;
			}

			protected void onPostExecute(String resp) {
				
			}
		}
}
