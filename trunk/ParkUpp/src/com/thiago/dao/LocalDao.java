//package com.thiago.dao;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//
//import com.thiago.modelo.LocalPU;
//import com.thiago.negocio.SqliteCrud;
//
//public class LocalDao {
//
//	private SQLiteDatabase db = null;
//	private SqliteCrud sqliteCrud = null;
//
//	public LocalDao(Context context){
//		sqliteCrud = new SqliteCrud(context);
//	}
//	
//	public List<LocalPU> findAll() {
//		SQLiteDatabase db = sqliteCrud.getWritableDatabase();
//		Cursor cursor = db.rawQuery("SELECT * FROM local", null);
//
//		List<LocalPU> locais = new ArrayList<LocalPU>();
//		LocalPU local = null;
//		if (cursor.moveToFirst()) {
//			do {
//				local = new LocalPU();
//				local.setId(Integer.parseInt(cursor.getString(0)));
//				local.setBairro(cursor.getString(1));
//				local.setCidade(cursor.getString(2));
//				local.setCoordenadaX(cursor.getString(3));
//				local.setCoordenadaY(cursor.getString(4));
//				local.setEstado(cursor.getString(5));
//				
//				locais.add(local);
//			} while (cursor.moveToNext());
//		}
//		return locais;
//	}
//
//	public void salvar(LocalPU local) {
//		this.db = sqliteCrud.getWritableDatabase();
//		
//		ContentValues cv = new ContentValues();
//		cv.put("bairro", local.getBairro());
//		cv.put("cidade", local.getCidade());
//		cv.put("coordenadaX", local.getCoordenadaX());
//		cv.put("coordenadaY", local.getCoordenadaY());
//		cv.put("estado", local.getEstado());
//		this.db.insert("local", null, cv);
//	}
//
//
//	public void atualizar(LocalPU local) {
//		this.db = sqliteCrud.getWritableDatabase();
//		
//		ContentValues cv = new ContentValues();
//		cv.put("bairro", local.getBairro());
//		cv.put("cidade", local.getCidade());
//		cv.put("coordenadaX", local.getCoordenadaX());
//		cv.put("coordenadaY", local.getCoordenadaY());
//		cv.put("estado", local.getEstado());
//		this.db.update("local", cv, "id=?", new String[]{local.getId()+""});
//	}
//
//	
//	public void remover(int id) {
//		this.db = sqliteCrud.getWritableDatabase();
//		this.db.delete("local", "id=?", new String[] { id + "" });
//	}
//	
//
//	public LocalPU findById(int id) {
//		SQLiteDatabase db = sqliteCrud.getWritableDatabase();
//
//		Cursor cursor = db.rawQuery("SELECT * FROM local WHERE id=?", new String[]{id+""});
//		
//		LocalPU local = null;
//		if (cursor.moveToFirst()) {
//			do {
//				local = new LocalPU();
//				local.setId(Integer.parseInt(cursor.getString(0)));
//				local.setBairro(cursor.getString(1));
//				local.setCidade(cursor.getString(2));
//				local.setCoordenadaX(cursor.getString(3));
//				local.setCoordenadaY(cursor.getString(4));
//				local.setEstado(cursor.getString(5));
//				
//			} while (cursor.moveToNext());
//		}
//		return local;
//	}
//}
