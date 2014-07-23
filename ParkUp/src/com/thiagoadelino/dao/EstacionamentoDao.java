package com.thiagoadelino.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.thiagoadelino.modelo.Estacionamento;
import com.thiagoadelino.modelo.Local;
import com.thiagoadelino.modelo.Veiculo;
import com.thiagoadelino.negocio.SqliteCrud;

public class EstacionamentoDao {
	private SQLiteDatabase db = null;
	private SqliteCrud sqliteCrud = null;

	public EstacionamentoDao(Context context) {
		sqliteCrud = new SqliteCrud(context);
	}

	public List<Estacionamento> findAll() {
		SQLiteDatabase db = sqliteCrud.getWritableDatabase();
		Cursor cursor = db.rawQuery("SELECT * FROM estacionamento", null);

		List<Estacionamento> estacionamentos = new ArrayList<Estacionamento>();
		Estacionamento estacionamento = null;
		if (cursor.moveToFirst()) {
			do {
				
				estacionamento = new Estacionamento();
				estacionamento.setId(Integer.parseInt(cursor.getString(0)));
				estacionamento.setLocal(new Local());
				estacionamento.getLocal().setId(Integer.parseInt(cursor.getString(1)));
				estacionamento.setObservacao(cursor.getString(2));
				estacionamento.setQualificacao(Integer.parseInt(cursor.getString(3)));
				estacionamento.setVeiculo(new Veiculo());
				estacionamento.getVeiculo().setId(Integer.parseInt(cursor.getString(4)));
				
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
				
				try {
					estacionamento.setHoraInicio(sdf.parse(cursor.getString(5)));
					estacionamento.setHoraFim(sdf.parse(cursor.getString(6)));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				estacionamentos.add(estacionamento);
			} while (cursor.moveToNext());
		}
		return estacionamentos;
	}

	public void salvar(Estacionamento estacionamento) {
		this.db = sqliteCrud.getWritableDatabase();

		ContentValues cv = new ContentValues();
		cv.put("id_local", estacionamento.getLocal().getId());
		cv.put("observacao", estacionamento.getObservacao());
		cv.put("qualificacao", estacionamento.getQualificacao());
		cv.put("id_veiculo", estacionamento.getVeiculo().getId());
		cv.put("hora_inicio", estacionamento.getHoraInicio() + "");
		cv.put("hora_fim", estacionamento.getHoraInicio() + "");
		this.db.insert("estacionamento", null, cv);
	}

	public void remover(int id) {
		this.db = sqliteCrud.getWritableDatabase();
		this.db.delete("estacionamento", "id=?", new String[] { id + "" });
	}

}
