package com.thiagoadelino.parkup;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.thiagoadelino.util.ParkUpActivity;


public class MainActivity extends ParkUpActivity {

	private final static String titulo = "Park Up!";
	/**
	 * Define a navegação para o histórico de estacionamentos
	 */
	private void iniciarHistoricoEstacionamento(){
		Button botao = (Button) findViewById(R.id.estacionamentos);
		botao.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(MainActivity.this, EstacionamentoHistActivity.class);
				startActivityForResult(i, 1);
			}
		});
	}
	
	/**
	 * Define a navegação para os veículos cadastrados.
	 */
	private void veiculosCadastrados(){
		Button botao = (Button) findViewById(R.id.veiculos);
		botao.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(MainActivity.this, ListaVeiculoActivity.class);
				startActivityForResult(i, 1);
			}
		});
	}
	
	/**
	 * Define a navegação para iniciar o procedimento de checkin ou retorno ao automóvel
	 */
	private void iniciarCheckinRetornarAutomovel(){
		Button botao = (Button) findViewById(R.id.iniciarretornarcheckin);
		botao.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(MainActivity.this, CheckinActivity.class);
				startActivityForResult(i, 1);
			}
		});
	}
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setarTitulo(titulo);
        veiculosCadastrados();
        iniciarCheckinRetornarAutomovel();
        iniciarHistoricoEstacionamento();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
