package com.thiago.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.thiago.modelo.EstacionamentoPU;
import com.thiago.modelo.VeiculoPU;
import com.thiago.negocio.SqliteCrud;

public class EstacionamentoDao {
	private SQLiteDatabase db = null;
	private SqliteCrud sqliteCrud = null;
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	public EstacionamentoDao(Context context) {
		sqliteCrud = new SqliteCrud(context);
		
		
		
	}

	
	public EstacionamentoPU findEstacionamentoEmAberto() {
		SQLiteDatabase db = sqliteCrud.getWritableDatabase();
		Cursor cursor = db.rawQuery(
				"SELECT * FROM estacionamento WHERE hora_fim is null ;", null);

		EstacionamentoPU estacionamento = null;
		if (cursor.moveToFirst()) {
			do {
				int i = 0;
				estacionamento = new EstacionamentoPU();
				estacionamento.setId(Integer.parseInt(cursor.getString(i++)));
				estacionamento.setCoordenadaX(cursor.getString(i++));
				estacionamento.setCoordenadaY(cursor.getString(i++));
				estacionamento.setObservacao(cursor.getString(i++));
				estacionamento.setOutrasInformacoes(cursor.getString(i++));
				estacionamento.setQualificacao(Integer.parseInt(cursor.getString(i++)));
				estacionamento.setVeiculo(new VeiculoPU());

				String str = cursor.getString(i++);
				
				if ( str != null)
					estacionamento.getVeiculo().setId(Integer.parseInt(str));

				try {
					estacionamento.setHoraInicio(sdf.parse(cursor.getString(i++)));
					
					String dataFim = cursor.getString(i++); 
					
					if ( dataFim!= null)
						estacionamento.setHoraFim(sdf.parse(dataFim));
					
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
				int i = 0;
				estacionamento = new EstacionamentoPU();
				estacionamento.setId(Integer.parseInt(cursor.getString(i++)));
				
				estacionamento.setCoordenadaX(cursor.getString(i++));
				estacionamento.setCoordenadaY(cursor.getString(i++));
				
				estacionamento.setObservacao(cursor.getString(i++));
				estacionamento.setOutrasInformacoes(cursor.getString(i++));
				
				estacionamento.setQualificacao(Integer.parseInt(cursor
						.getString(i++)));
				
				String str1 = cursor.getString(i++);
				
				if(str1!=null){
					estacionamento.setVeiculo(new VeiculoPU());
					estacionamento.getVeiculo().setId(
							Integer.parseInt(str1));
				}
				try {
					estacionamento
							.setHoraInicio(sdf.parse(cursor.getString(i++)));

					String str2 = cursor.getString(i++);
					if (str2!=null)
						estacionamento
								.setHoraFim(sdf.parse(str2));

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
		String a =" (name_id INTEGER PRIMARY KEY AUTOINCREMENT, "+
	    		 " coordenada_x TEXT, " +
	    		 " coordenada_y TEXT, " +
	     		 " observacao TEXT, " +
	     		 " qualificacao INTEGER, " +
	     		 " id_veiculo INTEGER, " +
	     		 " hora_inicio TEXT, " +
	     		 " hora_fim TEXT); ";
		
		cv.put("coordenada_x", estacionamento.getCoordenadaX());
		cv.put("coordenada_y", estacionamento.getCoordenadaY());
		cv.put("observacao", estacionamento.getObservacao());
		cv.put("outras_informacoes", estacionamento.getOutrasInformacoes());
		cv.put("qualificacao", estacionamento.getQualificacao());
		if (estacionamento.getVeiculo() != null)
			cv.put("id_veiculo", estacionamento.getVeiculo().getId());
		cv.put("hora_inicio", sdf.format(estacionamento.getHoraInicio()));

		if (estacionamento.getHoraFim() != null) {
			cv.put("hora_fim", sdf.format(estacionamento.getHoraFim()));
		}
		this.db.update("estacionamento", cv, "name_id=?",
				new String[] { estacionamento.getId() + "" });
	}

	public void salvar(EstacionamentoPU estacionamento) {

		ContentValues cv = new ContentValues();
		cv.put("coordenada_x", estacionamento.getCoordenadaX());
		cv.put("coordenada_y", estacionamento.getCoordenadaY());
		cv.put("observacao", estacionamento.getObservacao());
		cv.put("outras_informacoes", estacionamento.getOutrasInformacoes());
		cv.put("qualificacao", estacionamento.getQualificacao());

		if (estacionamento.getVeiculo() != null)
			cv.put("id_veiculo", estacionamento.getVeiculo().getId());

		if(estacionamento.getHoraInicio()==null)
			estacionamento.setHoraInicio(new Date());
		
		cv.put("hora_inicio", sdf.format(estacionamento.getHoraInicio()));

		if (estacionamento.getHoraFim() != null) {
			cv.put("hora_fim", sdf.format(estacionamento.getHoraFim()));
		}
		
		estacionamento.setId(Integer.parseInt(""+sqliteCrud.getWritableDatabase().insert("estacionamento", null, cv)));
	}

	public void remover(int id) {
		this.db = sqliteCrud.getWritableDatabase();
		this.db.delete("estacionamento", "name_id=?", new String[] { id + "" });
	}

}
