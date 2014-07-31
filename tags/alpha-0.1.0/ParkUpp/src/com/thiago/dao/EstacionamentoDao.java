package com.thiago.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.thiago.modelo.EstacionamentoPU;
import com.thiago.modelo.LocalPU;
import com.thiago.modelo.VeiculoPU;
import com.thiago.negocio.SqliteCrud;

public class EstacionamentoDao {
	private SQLiteDatabase db = null;
	private SqliteCrud sqliteCrud = null;
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	public EstacionamentoDao(Context context) {
		sqliteCrud = new SqliteCrud(context);
	}

	public EstacionamentoPU findEstacionamentoEmAberto(){
		SQLiteDatabase db = sqliteCrud.getWritableDatabase();
		
		Cursor cursor = db.rawQuery("SELECT * FROM estacionamento WHERE hora_fim is null", null);

		EstacionamentoPU estacionamento = null;
		if (cursor.moveToFirst()) {
			do {
				
				estacionamento = new EstacionamentoPU();
				estacionamento.setId(Integer.parseInt(cursor.getString(0)));
				estacionamento.setLocal(new LocalPU());
				estacionamento.getLocal().setId(Integer.parseInt(cursor.getString(1)));
				estacionamento.setObservacao(cursor.getString(2));
				estacionamento.setQualificacao(Integer.parseInt(cursor.getString(3)));
				estacionamento.setVeiculo(new VeiculoPU());
				
				if(cursor.getString(4)!=null)
					estacionamento.getVeiculo().setId(Integer.parseInt(cursor.getString(4)));
				
				
				
				try {
					estacionamento.setHoraInicio(sdf.parse(cursor.getString(5)));
					if(cursor.getString(6)!=null)
						estacionamento.setHoraFim(sdf.parse(cursor.getString(6)));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			} while (cursor.moveToNext());
		}
		return estacionamento;
	}
	
	public List<EstacionamentoPU> findAll() {
		SQLiteDatabase db = sqliteCrud.getWritableDatabase();
		Cursor cursor = db.rawQuery("SELECT * FROM estacionamento", null);

		List<EstacionamentoPU> estacionamentos = new ArrayList<EstacionamentoPU>();
		EstacionamentoPU estacionamento = null;
		if (cursor.moveToFirst()) {
			do {
				
				estacionamento = new EstacionamentoPU();
				estacionamento.setId(Integer.parseInt(cursor.getString(0)));
				estacionamento.setLocal(new LocalPU());
				estacionamento.getLocal().setId(Integer.parseInt(cursor.getString(1)));
				estacionamento.setObservacao(cursor.getString(2));
				estacionamento.setQualificacao(Integer.parseInt(cursor.getString(3)));
				estacionamento.setVeiculo(new VeiculoPU());
				estacionamento.getVeiculo().setId(Integer.parseInt(cursor.getString(4)));
				
				try {
					estacionamento.setHoraInicio(sdf.parse(cursor.getString(5)));
					
					if(cursor.getString(6)!=null)
						estacionamento.setHoraFim(sdf.parse(cursor.getString(6)));
					
				} catch (ParseException e) {
					e.printStackTrace();
				}
				estacionamentos.add(estacionamento);
			} while (cursor.moveToNext());
		}
		return estacionamentos;
	}

	public void atualizar(EstacionamentoPU estacionamento) {
		this.db = sqliteCrud.getWritableDatabase();
		
		ContentValues cv = new ContentValues();
		cv.put("id_local", estacionamento.getLocal().getId());
		cv.put("observacao", estacionamento.getObservacao());
		cv.put("qualificacao", estacionamento.getQualificacao());

		if(estacionamento.getVeiculo() != null)
			cv.put("id_veiculo", estacionamento.getVeiculo().getId());
		
		cv.put("hora_inicio", sdf.format(estacionamento.getHoraInicio()));
		
		if(estacionamento.getHoraFim()!=null){
			cv.put("hora_fim", sdf.format(estacionamento.getHoraFim()));
		}
		this.db.update("estacionamento", cv, "id=?", new String[]{estacionamento.getId()+""});
	}
	
	public void salvar(EstacionamentoPU estacionamento) {
		this.db = sqliteCrud.getWritableDatabase();

		ContentValues cv = new ContentValues();
		cv.put("id_local", estacionamento.getLocal().getId());
		cv.put("observacao", estacionamento.getObservacao());
		cv.put("qualificacao", estacionamento.getQualificacao());

		if(estacionamento.getVeiculo() != null)
			cv.put("id_veiculo", estacionamento.getVeiculo().getId());
		
		cv.put("hora_inicio", sdf.format(estacionamento.getHoraInicio()));
		
		if(estacionamento.getHoraFim()!=null){
			cv.put("hora_fim", sdf.format(estacionamento.getHoraFim()));
		}
		
		
		this.db.insert("estacionamento", null, cv);
	}

	public void remover(int id) {
		this.db = sqliteCrud.getWritableDatabase();
		this.db.delete("estacionamento", "id=?", new String[] { id + "" });
	}

}