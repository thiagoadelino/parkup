package com.thiago.parkupp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.thiago.adapter.LocalAdapter;
import com.thiago.dao.EstacionamentoDao;
import com.thiago.modelo.EstacionamentoPU;

public class Local extends Activity {

	private List<EstacionamentoPU> hist;

	private LocalAdapter adapter;
	
	private Context contextoTela;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.local);
		
		contextoTela = this;
		
		
		
		Button botao = (Button) findViewById(R.id.botaoexcluirtudo);
		botao.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AlertDialog.Builder adb = new AlertDialog.Builder(contextoTela);
				adb.setTitle("ParkUp!");
				adb.setMessage("Deseja excluir todos os locais?");
				adb.setPositiveButton("Sim", 
						new DialogInterface.OnClickListener() { 
		 					public void onClick(DialogInterface arg0, int arg1) { 
								
		 						EstacionamentoDao dao = new EstacionamentoDao(getApplicationContext());
								List<EstacionamentoPU> estacionamentos = dao.findAll();
								
								for(EstacionamentoPU est: estacionamentos){
									dao.remover(est.getId());
								}
								
								ListView lista = (ListView) findViewById(R.id.listViewLocal);
								recuperarItensListagem(lista);
								
								Toast.makeText(getApplicationContext(), "Remoção realizada com sucesso!", Toast.LENGTH_SHORT);
							} 
						});
				
				adb.setNegativeButton("Não", 
						new DialogInterface.OnClickListener() { 
							public void onClick(DialogInterface arg0, int arg1) { 
							} });

				AlertDialog popUp = adb.create();
				popUp.show();
	
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		System.gc();
		getMenuInflater().inflate(R.menu.local, menu);
		ListView lista = (ListView) findViewById(R.id.listViewLocal);
		recuperarItensListagem(lista);
		
		if ( hist==null || hist.size() == 0 )
			Toast.makeText(Local.this, "Nenhum local cadastrado", Toast.LENGTH_SHORT).show();
		
		lista.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position,
					long id) {
				
				EstacionamentoPU local = (EstacionamentoPU) adapter.getItemAtPosition(position);
				Intent i = new Intent(Local.this, DetalheLocal.class);
				i.putExtra("estacionamento", local);
				startActivityForResult(i, 1);
				
			}
		});
		return true;
	}

	private void recuperarItensListagem(ListView lista) {
		EstacionamentoDao dao = new EstacionamentoDao(getApplicationContext());
		
		hist = dao.findAll();  
		if ( hist == null ){
			hist = new ArrayList<EstacionamentoPU>();
		}
		
		adapter = new LocalAdapter(hist, Local.this);
		lista.setAdapter(adapter);
		
	}
	
	
	

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
