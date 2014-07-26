package com.thiago.parkupp;

import java.util.Date;

import com.thiago.dao.EstacionamentoDao;
import com.thiago.dao.LocalDao;
import com.thiago.modelo.EstacionamentoPU;
import com.thiago.modelo.LocalPU;
import com.thiago.modelo.VeiculoPU;
import com.thiago.negocio.SqliteCrud;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class Main extends FragmentActivity {

	private EstacionamentoPU estacionamento;
	private VeiculoPU veiculo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		EstacionamentoDao dao = new EstacionamentoDao(getApplicationContext());
		EstacionamentoPU emAberto = dao.findEstacionamentoEmAberto();
		
		if(emAberto!=null){
			this.estacionamento = emAberto; 
			LocalDao ldao = new LocalDao(getApplicationContext());
			this.estacionamento.setLocal(ldao.findById(this.estacionamento.getLocal().getId()));
			
			Intent i = new Intent(Main.this, Retornar.class);
			i.putExtra("estacionamento", estacionamento);
			startActivity(i);
			finish();
		}else{
			this.estacionamento = new EstacionamentoPU();
			
			
			
		}
		
		Button botao = (Button) findViewById(R.id.botaopark);
		botao.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(Main.this, Retornar.class);
				
				estacionamento.setObservacao("");
				estacionamento.setHoraInicio(new Date());
				LocalPU locPu = new LocalPU();
				locPu.setBairro("");
				locPu.setCidade("");
				locPu.setCoordenadaX("0");
				locPu.setCoordenadaY("0");
				locPu.setEstado("");
				
				LocalDao ldao = new LocalDao(getApplicationContext());
				ldao.salvar(locPu);
				
				estacionamento.setLocal(locPu);
				
				EstacionamentoDao dao = new EstacionamentoDao(getApplicationContext());
				dao.salvar(estacionamento);
				

				
				
				i.putExtra("estacionamento", estacionamento);
				startActivity(i);
				finish();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
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
