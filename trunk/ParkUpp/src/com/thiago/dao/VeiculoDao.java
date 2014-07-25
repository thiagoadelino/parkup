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

	private SQLiteDatabase db = null;
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
		this.db = sqliteCrud.getWritableDatabase();
		
		ContentValues cv = new ContentValues();
		cv.put("foto", veiculo.getFoto());
		cv.put("foto", veiculo.getFoto());
		cv.put("nome", veiculo.getNome());
		cv.put("id_veiculo", veiculo.isCarro()?1:0);
		this.db.update("veiculo", cv, "id?", new String[] {veiculo.getId()+""});
	}
	
	public void salvar(VeiculoPU veiculo) {
		this.db = sqliteCrud.getWritableDatabase();
		
		ContentValues cv = new ContentValues();
		cv.put("foto", veiculo.getFoto());
		cv.put("nome", veiculo.getNome());
		cv.put("id_veiculo", veiculo.isCarro()?1:0);
		this.db.insert("veiculo", null, cv);
	}

	public void remover(int id) {
		this.db = sqliteCrud.getWritableDatabase();
		this.db.delete("veiculo", "id=?", new String[] { id + "" });
	}
}
