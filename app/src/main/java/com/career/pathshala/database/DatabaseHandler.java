package com.career.pathshala.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.IOException;

public class DatabaseHandler {

	private String query="";
	private String msg="";
	private String question="";
	private String answer="";
	private String A="";
	private String B="";
	private String C="";
	private String D="";
	private String question_no="question_no";
	private String str="";

	public String retrieve_messages(Context context, int counter, String tablename)
	{	
		
		A=B=C=D="";
		DataBaseHelper.getInstance(context).openDataBase();		
		SQLiteDatabase db1=DataBaseHelper.getInstance(context).getReadableDatabase();
		query="select question,answer,A,B,C,D from '"+tablename+"' where id='"+counter+"'";
		Log.e("...","query= "+query);
		Cursor cursor1=db1.rawQuery(query, null);
		if (!cursor1.isAfterLast()) 
		{			
			cursor1.moveToFirst();
			while(!cursor1.isAfterLast())
			{
				question=cursor1.getString(0);
				answer=cursor1.getString(1);
				A=cursor1.getString(2);
				B=cursor1.getString(3);
				C=cursor1.getString(4);
				D=cursor1.getString(5);
				cursor1.moveToNext();
			}
		}		
		cursor1.close();
		db1.close();
		DataBaseHelper.getInstance(context).close();
//		Log.e("","msg= "+question+","+answer+","+A+","+B+","+C+","+D);
		return question+","+answer+","+A+","+B+","+C+","+D;
	}

	public String retreive_userAnswer(Context context, int question_no, String tablename)
	{	
		str="";
		DataBaseHelper.getInstance(context).openDataBase();		
		SQLiteDatabase db1=DataBaseHelper.getInstance(context).getReadableDatabase();
		query="select A,B,C,D from '"+tablename+"' where question_no='"+question_no+"'";
		Log.e("...","query= "+query);
		Cursor cursor1=db1.rawQuery(query, null);
		if (!cursor1.isAfterLast()) 
		{			
			cursor1.moveToFirst();
			while(!cursor1.isAfterLast())
			{				
				A=cursor1.getString(0);
				B=cursor1.getString(1);
				C=cursor1.getString(2);
				D=cursor1.getString(3);
				Log.e("","A= "+A+" B= "+B+" C= "+C+" D= "+D);
				cursor1.moveToNext();
			}
		}		
		cursor1.close();
		db1.close();
		DataBaseHelper.getInstance(context).close();

		if(A.equals("1"))
			str="A";			
		else if(B.equals("1"))
			str="B";
		else if(C.equals("1"))
			str="C";
		else if(D.equals("1"))
			str="D";
		
		return str;
	}
	
	public void delete_quiz_data(Context context, String tablename)
	{		
		DataBaseHelper.getInstance(context).openDataBase();		
		SQLiteDatabase db=DataBaseHelper.getInstance(context).getWritableDatabase();
		db.execSQL("delete from '"+tablename+"'");
		db.close();
		DataBaseHelper.getInstance(context).close();
	}

	public void update_answer(Context context, int counter, int a, int b, int c, int d, String tablename)
	{
		
		DataBaseHelper.getInstance(context).openDataBase();		
		SQLiteDatabase db1=DataBaseHelper.getInstance(context).getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("A", ""+a); 
		values.put("B", ""+b);  
		values.put("C", ""+c);  
		values.put("D", ""+d);  
		db1.update(tablename, values, question_no+ " = ?",new String[]{""+counter});
		db1.close();
		DataBaseHelper.getInstance(context).close();
	}

	public void creatdatabase(Context cntx)
	{
		try 
		{
			DataBaseHelper.getInstance(cntx).createDataBase();
		}
		catch (IOException e)
		{			
			e.printStackTrace();
		}
	}
}
