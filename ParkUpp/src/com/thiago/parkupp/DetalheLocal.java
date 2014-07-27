package com.thiago.parkupp;

import java.text.SimpleDateFormat;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RatingBar;
import android.widget.TextView;

import com.thiago.modelo.EstacionamentoPU;

public class DetalheLocal extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detalhe_local);
		
		EstacionamentoPU es = (EstacionamentoPU) getIntent().getSerializableExtra("estacionamento");
		
		SimpleDateFormat sdfData = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat sdfHora = new SimpleDateFormat("HH:mm");
		
		TextView textoData = (TextView) findViewById(R.id.textoDATA);
		TextView horaChegada = (TextView) findViewById(R.id.textoHRCh);
		TextView horaSaida = (TextView) findViewById(R.id.textoHRSd);
		TextView cidadeEstado = (TextView) findViewById(R.id.textoCid);
		TextView rua = (TextView) findViewById(R.id.textoEndereco);
		RatingBar rb = (RatingBar) findViewById(R.id.qualificacaoLocal); 
		
		textoData.setText(sdfData.format(es.getHoraInicio()));
		horaChegada.setText(sdfHora.format(es.getHoraInicio()));
		horaSaida.setText(sdfHora.format(es.getHoraFim()));
		cidadeEstado.setText(es.getObservacao());
		rua.setText(es.getOutrasInformacoes());
		
		//TODO CARREGAR IMAGEM.
		rb.setRating(Float.parseFloat(""+es.getQualificacao()));
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.detalhe_local, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
