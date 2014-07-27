package com.thiago.negocio;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SqliteCrud extends SQLiteOpenHelper{

		private static final String DATABASE_NAME = "asdfg.db";
		public static final int DATABASE_VERSION = 1;
		
		public SqliteCrud(Context context) {
		    super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}
					 
		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL("CREATE TABLE " +
		    		 "estacionamento " + 
		    		 " (name_id INTEGER PRIMARY KEY AUTOINCREMENT, "+
		    		 " coordenada_x TEXT, " +
		    		 " coordenada_y TEXT, " +
		     		 " observacao TEXT, " +
		     		 " outras_informacoes TEXT, " +
		     		 " qualificacao INTEGER, " +
		     		 " id_veiculo INTEGER, " +
		     		 " hora_inicio TEXT, " +
		     		 " hora_fim TEXT); ");
			db.execSQL("CREATE TABLE " + "veiculo" + 
					"  (name_id INTEGER PRIMARY KEY AUTOINCREMENT, "+
		     		 " foto TEXT, " +
		     		 " nome TEXT, " +
		     		 " id_veiculo INTEGER); ");
		}
			
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		     if ((newVersion - oldVersion) > 2) {
		    	 db.execSQL("DROP TABLE IF EXISTS " + "estacionamento");
		    	 db.execSQL("DROP TABLE IF EXISTS " + "veiculo");
		    	 onCreate(db);
		     }
			  
		}
}
