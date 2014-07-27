package com.thiago.parkupp;

import java.io.File;
import java.util.List;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.thiago.dao.EstacionamentoDao;
import com.thiago.dao.VeiculoDao;
import com.thiago.modelo.EstacionamentoPU;
import com.thiago.modelo.VeiculoPU;
import com.thiago.util.LocalizacaoUtil;

public class Main extends FragmentActivity {

	private EstacionamentoPU estacionamento;
	private VeiculoPU veiculo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		veiculo = new VeiculoPU();
		
		LocalizacaoUtil l = new LocalizacaoUtil(getApplicationContext());
		l.execute();
		
		EstacionamentoDao dao = new EstacionamentoDao(getApplicationContext());
		EstacionamentoPU emAberto = dao.findEstacionamentoEmAberto();
		
		if(emAberto!=null){
			this.estacionamento = emAberto; 
			Intent i = new Intent(Main.this, Retornar.class);
			startActivity(i);
			finish();
		}
		
		Button botao = (Button) findViewById(R.id.botaopark);
		botao.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(Main.this, Retornar.class);
				if(veiculo.getId()!=null)
					i.putExtra("veiculo", veiculo);
				startActivity(i);
			}
		});
		
		carregarSpinner();
	}
	
	private void carregarSpinner(){

		Spinner spinner = (Spinner) findViewById(R.id.spinnerVeiculo);

		VeiculoDao vDao = new VeiculoDao(getApplicationContext());
		List<VeiculoPU> veiculos = vDao.findAll();
		
		if(veiculos!=null){
			ArrayAdapter<VeiculoPU> arrayAdapter = new ArrayAdapter<VeiculoPU>(this, android.R.layout.simple_spinner_dropdown_item, veiculos);
			ArrayAdapter<VeiculoPU> spinnerArrayAdapter = arrayAdapter;
			spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
			spinner.setAdapter(spinnerArrayAdapter);
		}
 
		spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
				
				veiculo = (VeiculoPU) parent.getItemAtPosition(position);
				((VeiculoPU) parent.getItemAtPosition(position)).getNome();
				
				Toast.makeText(Main.this, "Veículo Selecionado: " + veiculo.getNome(), Toast.LENGTH_LONG).show();
			}
 
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
 
			}
		});
	}
	
	/**
	 * Método chamado no retorno da Activity
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		carregarSpinner();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {

		case R.id.locais:
			locais();
			return true;

		case R.id.veiculos:
			veiculos();
			return true;

		case R.id.action_settings:
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void locais() {
		Intent i = new Intent(Main.this, Local.class);
		startActivityForResult(i, 1);
	}

	public void veiculos() {
		Intent i = new Intent(Main.this, Veiculo.class);
		startActivityForResult(i, 1);
	}
}
