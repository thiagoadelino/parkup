package com.thiagoadelino.negocio;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SqliteCrud extends SQLiteOpenHelper{

		private static final String DATABASE_NAME = "database.db";
		public static final int DATABASE_VERSION = 1;
		
		public SqliteCrud(Context context) {
		    super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}
					 
		@Override
		public void onCreate(SQLiteDatabase db) {
		     db.execSQL("CREATE TABLE " + "estacionamento" + "(id INTEGER PRIMARY KEY AUTOINCREMENT, id_local INTEGER, " +
		     		"observacao TEXT, qualificacao INTEGER, id_veiculo INTEGER, hora_inicio DATE, hora_fim DATE)");
		     db.execSQL("CREATE TABLE " + "local" + "(id INTEGER PRIMARY KEY AUTOINCREMENT, bairro TEXT, " +
			     		"cidade TEXT, coordenadaX TEXT, coordenadaY TEXT, estado TEXT)");
		     db.execSQL("CREATE TABLE " + "veiculo" + "(id INTEGER PRIMARY KEY AUTOINCREMENT, foto TEXT, nome TEXT, id_veiculo INTEGER)");
		}
			
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		     if ((newVersion - oldVersion) > 2) {
		    	 db.execSQL("DROP TABLE IF EXISTS " + "estacionamento");
		    	 db.execSQL("DROP TABLE IF EXISTS " + "local");
		    	 db.execSQL("DROP TABLE IF EXISTS " + "veiculo");
		    	 onCreate(db);
		     }
			  
		}
}
