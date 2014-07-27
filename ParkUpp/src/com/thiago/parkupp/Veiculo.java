package com.thiago.parkupp;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.thiago.adapter.VeiculoAdapter;
import com.thiago.dao.VeiculoDao;
import com.thiago.modelo.VeiculoPU;

public class Veiculo extends Activity {

	private Context contextoTela;
	private VeiculoAdapter adapter;
	private List<VeiculoPU> hist;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		contextoTela = this;
		
		setContentView(R.layout.veiculo);
		
		ListView lista = (ListView) findViewById(R.id.listViewVeiculo);
		recuperarItensListagem(lista);
		
		if ( hist==null || hist.size() == 0 )
			Toast.makeText(Veiculo.this, "Nenhum veículo cadastrado", Toast.LENGTH_SHORT).show();
		
		lista.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position,
					long id) {
				
				VeiculoPU veiculo = (VeiculoPU) adapter.getItemAtPosition(position);
				Intent i = new Intent(Veiculo.this, DetalheVeiculo.class);
				i.putExtra("veiculo", veiculo);
				startActivityForResult(i, 1);
				
			}
		});
		
		
		Button botao = (Button) findViewById(R.id.botaoexcluirtudovei);
		botao.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AlertDialog.Builder adb = new AlertDialog.Builder(contextoTela);
				adb.setTitle("ParkUp!");
				adb.setMessage("Deseja excluir todos os veículos?");
				adb.setPositiveButton("Sim", 
						new DialogInterface.OnClickListener() { 
		 					public void onClick(DialogInterface arg0, int arg1) { 
								
		 						VeiculoDao dao = new VeiculoDao(getApplicationContext());
								List<VeiculoPU> veiculos = dao.findAll();
								
								for(VeiculoPU v: veiculos){
									dao.remover(v.getId());
								}
								
								ListView lista = (ListView) findViewById(R.id.listViewVeiculo);
								recuperarItensListagem(lista);
								
								Toast.makeText(getApplicationContext(), "Remoção realizada com sucesso!", Toast.LENGTH_SHORT);
							} 
						});
				
				adb.setNegativeButton("Não", 
						new DialogInterface.OnClickListener() { 
							public void onClick(DialogInterface arg0, int arg1) { 
							} });

				AlertDialog popUp = adb.create();
				popUp.show();
	
			}
		});
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
			if (resultCode == RESULT_OK){
				
				VeiculoPU v = (VeiculoPU) data.getSerializableExtra("novo_veiculo");
				if (hist != null && !hist.contains(v))
					hist.add(v);
				
				adapter.notifyDataSetChanged();
				
				ListView lista = (ListView) findViewById(R.id.listViewVeiculo);
				recuperarItensListagem(lista);

				Toast.makeText(Veiculo.this, "Veiculo cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
			}
	}

	private void recuperarItensListagem(ListView lista) {
		VeiculoDao dao = new VeiculoDao(getApplicationContext());
		
		hist = dao.findAll();  
		if ( hist == null ){
			hist = new ArrayList<VeiculoPU>();
		}
		
		adapter = new VeiculoAdapter(hist, Veiculo.this);
		lista.setAdapter(adapter);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.veiculo, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {

		case R.id.addveiculo:
			adicionar();
			return true;

		case R.id.editarveiculo:
			editar();
			return true;

		case R.id.action_settings:
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	private void editar() {
		
	}

	public void adicionar(){
		Intent i = new Intent(Veiculo.this, CadastroVeiculo.class);
		startActivityForResult(i, 1);
	}

	
	public void listar(){

		ListView lista = (ListView) findViewById(R.id.listViewVeiculo);
		recuperarItensListagem(lista);
		
		lista.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position,
					long id) {
				
				com.thiago.modelo.VeiculoPU veiculo = (com.thiago.modelo.VeiculoPU) adapter.getItemAtPosition(position);
				Intent i = new Intent(Veiculo.this, Main.class);
				i.putExtra("veiculo", veiculo);
				startActivityForResult(i, 1);
				
			}
		});
	}
}
