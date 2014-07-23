package com.thiagoadelino.parkup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.thiagoadelino.dao.VeiculoDao;
import com.thiagoadelino.modelo.Veiculo;

public class CadastroVeiculo extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cadastro_veiculo);
		
		Button botao = (Button) findViewById(R.id.button1);
		botao.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Veiculo veiculo = new Veiculo();
				
				EditText modelo = (EditText) findViewById(R.id.editText1);
				
				veiculo.setNome(modelo.getText().toString());
				
				RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radiogroup);
				int radio = radioGroup.getCheckedRadioButtonId();
				
				if (radio == R.id.radioButton1)
					veiculo.setCarro(true);
				else
					veiculo.setCarro(false);
				
				VeiculoDao dao = new VeiculoDao(getApplicationContext());
				dao.salvar(veiculo);
				
				Intent i = new Intent();
				i.putExtra("novo_veiculo", veiculo);
				setResult(RESULT_OK, i);
				finish();
//				Intent i = new Intent(ListaVeiculoActivity.this, CadastroVeiculo.class);
//				startActivityForResult(i, 1);
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.cadastro_veiculo, menu);
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
