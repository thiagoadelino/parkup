package com.thiago.parkupp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.thiago.dao.EstacionamentoDao;
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
				startActivity(i);
			}
		});
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
