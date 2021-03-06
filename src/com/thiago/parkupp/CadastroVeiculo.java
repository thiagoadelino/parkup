package com.thiago.parkupp;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

public class CadastroVeiculo extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cadastro_veiculo);

		Button botao = (Button) findViewById(R.id.botaoexcluirtudo);
		botao.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
					
				com.thiago.modelo.VeiculoPU veiculo = new com.thiago.modelo.VeiculoPU();
				
				EditText modelo = (EditText) findViewById(R.id.editText1);
				
				veiculo.setNome(modelo.getText().toString());
				
				RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radiogroup);
				int radio = radioGroup.getCheckedRadioButtonId();
				
				if (radio == R.id.radioButton1)
					veiculo.setCarro(true);
				else
					veiculo.setCarro(false);
				
				com.thiago.dao.VeiculoDao dao = new com.thiago.dao.VeiculoDao(getApplicationContext());
				dao.salvar(veiculo);
				
				Intent i = new Intent();
				i.putExtra("novo_veiculo", veiculo);
				setResult(RESULT_OK, i);
				finish();
				
			}
			
		});
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.cadastro_veiculo, menu);
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
