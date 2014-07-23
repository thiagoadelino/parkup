package com.thiagoadelino.parkup;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.thiagoadelino.adapter.EstacionamentoHistoricoAdapter;
import com.thiagoadelino.dao.EstacionamentoDao;
import com.thiagoadelino.modelo.Estacionamento;
import com.thiagoadelino.util.ParkUpActivity;

public class EstacionamentoHistActivity extends ParkUpActivity {

	private static final String titulo = "Estacionamentos";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_estacionamento_hist);
		setarTitulo(titulo);
		
		ListView lista = (ListView) findViewById(R.id.listView1);
		recuperarItensListagem(lista);
		
		lista.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position,
					long id) {
				
				Estacionamento estacionamentoSelecionado = (Estacionamento) adapter.getItemAtPosition(position);
				Intent i = new Intent(EstacionamentoHistActivity.this, EstacionamentoDetalheActivity.class);
				i.putExtra("estacionamentoSelecionado", estacionamentoSelecionado);
				startActivityForResult(i, 1);
				
			}
		});
		
	}

	private void recuperarItensListagem(ListView lista){
		EstacionamentoDao dao = new EstacionamentoDao(getApplicationContext());
		
		List<Estacionamento> hist = dao.findAll();  
		if ( hist == null )
			hist = new ArrayList<Estacionamento>();
		
		EstacionamentoHistoricoAdapter adapter = new EstacionamentoHistoricoAdapter(hist, EstacionamentoHistActivity.this);
		lista.setAdapter(adapter);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.estacionamento_hist, menu);
		setarTitulo(titulo);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		setarTitulo(titulo);
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
