package com.thiago.parkupp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.thiago.adapter.LocalAdapter;

public class Local extends Activity {

//	private List<LocalPU> hist;
//	
	private LocalAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.local);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.local, menu);
		ListView lista = (ListView) findViewById(R.id.listViewLocal);
		recuperarItensListagem(lista);
		
//		if ( hist==null || hist.size() == 0 )
			Toast.makeText(Local.this, "Nenhum local cadastrado", Toast.LENGTH_SHORT).show();
		
		lista.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position,
					long id) {
				
//				LocalPU local = (LocalPU) adapter.getItemAtPosition(position);
				Intent i = new Intent(Local.this, DetalheLocal.class);
//				i.putExtra("local", local);
				startActivityForResult(i, 1);
				
			}
		});
//		Toast.makeText(Local.this, "Nenhum local cadastrado", Toast.LENGTH_SHORT).show();
		return true;
	}

	private void recuperarItensListagem(ListView lista) {
//		LocalDao dao = new LocalDao(getApplicationContext());
		
//		hist = dao.findAll();  
//		if ( hist == null ){
//			hist = new ArrayList<LocalPU>();
//		}
		
//		adapter = new LocalAdapter(hist, Local.this);
		lista.setAdapter(adapter);
		
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
