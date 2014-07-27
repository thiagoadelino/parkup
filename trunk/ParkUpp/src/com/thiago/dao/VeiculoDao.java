package com.thiago.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.thiago.modelo.VeiculoPU;
import com.thiago.negocio.SqliteCrud;

public class VeiculoDao {

	private SqliteCrud sqliteCrud = null;

	public VeiculoDao(Context context){
		sqliteCrud = new SqliteCrud(context);
	}
	
	public List<VeiculoPU> findAll() {
		SQLiteDatabase db = sqliteCrud.getWritableDatabase();
		Cursor cursor = db.rawQuery("SELECT * FROM veiculo", null);

		List<VeiculoPU> veiculos = new ArrayList<VeiculoPU>();
		VeiculoPU veiculo = null;
		if (cursor.moveToFirst()) {
			do {
				veiculo = new VeiculoPU();
				
				
				veiculo.setId(cursor.getInt(0));
				veiculo.setFoto(cursor.getString(1));
				veiculo.setNome(cursor.getString(2));
				veiculo.setCarro(cursor.getInt(3)==0?false:true);
				
				veiculos.add(veiculo);
			} while (cursor.moveToNext());
		}
		return veiculos;
	}

	public void alterar(VeiculoPU veiculo){
		
		ContentValues cv = new ContentValues();
		cv.put("foto", veiculo.getFoto());
		cv.put("foto", veiculo.getFoto());
		cv.put("nome", veiculo.getNome());
		cv.put("id_veiculo", veiculo.isCarro()?1:0);
		sqliteCrud.getWritableDatabase().update("veiculo", cv, "name_id=?", new String[] {veiculo.getId()+""});
	}
	
	public void salvar(VeiculoPU veiculo) {
		
		ContentValues cv = new ContentValues();
		cv.put("foto", veiculo.getFoto());
		cv.put("nome", veiculo.getNome());
		cv.put("id_veiculo", veiculo.isCarro()?1:0);
		veiculo.setId(Integer.parseInt(""+sqliteCrud.getWritableDatabase().insert("veiculo", null, cv)));
	}

	public void remover(int id) {
		sqliteCrud.getWritableDatabase().delete("veiculo", "name_id=?", new String[] { id + "" });
	}

	public VeiculoPU findById(int id) {
		SQLiteDatabase db = sqliteCrud.getWritableDatabase();
		
		Cursor cursor = db.rawQuery("SELECT * FROM veiculo WHERE name_id=?", new String[]{id+""});
		
		VeiculoPU v = null;
		if (cursor.moveToFirst()) {
			do {
				int i = 0;
				v = new VeiculoPU();
				v.setId(Integer.parseInt(cursor.getString(i++)));
				v.setFoto(cursor.getString(i++));
				v.setNome(cursor.getString(i++));
				v.setCarro(Integer.parseInt(cursor.getString(i++))==1?true:false);
				
			} while (cursor.moveToNext());
		}
		return v;
	}
}
