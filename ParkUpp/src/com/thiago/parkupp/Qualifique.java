package com.thiago.parkupp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.Toast;
import android.widget.RatingBar.OnRatingBarChangeListener;

import com.thiago.dao.EstacionamentoDao;
import com.thiago.modelo.EstacionamentoPU;

public class Qualifique extends Activity {

	
	private EstacionamentoPU estacionamento;
	
	private Context contexto;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.qualifique);
		contexto = this;
		this.estacionamento = (EstacionamentoPU) getIntent().getSerializableExtra("estacionamento");
		
		
		RatingBar qualificacao = (RatingBar) findViewById(R.id.qualificacaoLocal);
		qualificacao.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
			
			@Override
			public void onRatingChanged(RatingBar ratingBar, float rating,
					boolean fromUser) {

				estacionamento.setQualificacao((int) rating);
				
			}
		});

		
		ImageButton botaoConfirmar = (ImageButton) findViewById(R.id.imageButton1);
		botaoConfirmar.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				EstacionamentoDao dao = new EstacionamentoDao(getApplicationContext());
				dao.atualizar(estacionamento);
				
				Intent i = new Intent(Qualifique.this, Main.class);
				startActivity(i);
				finish();
				
				Toast.makeText(contexto, "Estacionamento finalizado!", Toast.LENGTH_SHORT).show();
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.qualifique, menu);
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
