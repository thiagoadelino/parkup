package com.thiagoadelino.parkup;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.thiagoadelino.adapter.VeiculoAdapter;
import com.thiagoadelino.dao.VeiculoDao;
import com.thiagoadelino.modelo.Veiculo;
import com.thiagoadelino.util.ParkUpActivity;

public class ListaVeiculoActivity extends ParkUpActivity {

	private static final String titulo = "Veículos";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lista_veiculo);
		setarTitulo(titulo);
		
		
		Button botao = (Button) findViewById(R.id.addButton);
		botao.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				Intent i = new Intent(MainActivity.this, EstacionamentoHistActivity.class);
//				startActivityForResult(i, 1);
			}
		});
		
		
		ListView lista = (ListView) findViewById(R.id.listViewVeiculo);
		recuperarItensListagem(lista);
		
		lista.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position,
					long id) {
				
				Veiculo veiculo = (Veiculo) adapter.getItemAtPosition(position);
				Intent i = new Intent(ListaVeiculoActivity.this, CheckinActivity.class);
				i.putExtra("veiculo", veiculo);
				startActivityForResult(i, 1);
				
			}
		});
	}

	private void recuperarItensListagem(ListView lista) {
		VeiculoDao dao = new VeiculoDao(getApplicationContext());
		
		List<Veiculo> hist = dao.findAll();  
		if ( hist == null )
			hist = new ArrayList<Veiculo>();
		
		VeiculoAdapter adapter = new VeiculoAdapter(hist, ListaVeiculoActivity.this);
		lista.setAdapter(adapter);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.lista_veiculo, menu);
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
