package com.thiagoadelino.parkup;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.thiagoadelino.modelo.Estacionamento;
import com.thiagoadelino.util.ParkUpActivity;

public class EstacionamentoDetalheActivity extends ParkUpActivity {

	private static final String titulo = "Detalhes";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_estacionamento_detalhe);
		setarTitulo(titulo);
		
		
		Estacionamento estacionamento = (Estacionamento) getIntent().getSerializableExtra("estacionamentoSelecionado");
		
		TextView textView = (TextView)findViewById(R.id.textoestacionamento);
		textView.setText(estacionamento.getLocal().getCidade()+ " - "+ estacionamento.getLocal().getBairro());
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.estacionamento_detalhe, menu);
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