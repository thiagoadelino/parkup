package com.thiago.parkupp;

import com.thiago.modelo.VeiculoPU;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class DetalheVeiculo extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detalhe_veiculo);
		
		TextView nome = (TextView) findViewById(R.id.nomeveiculo);
		TextView tipoVeiculo = (TextView) findViewById(R.id.tipoveiculo);
		
		VeiculoPU veiculo = (VeiculoPU) getIntent().getSerializableExtra("veiculo");
		nome.setText(veiculo.getNome());
		tipoVeiculo.setText(veiculo.isCarro()?"Carro":"Moto");
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.detalhe_veiculo, menu);
		return true;
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
