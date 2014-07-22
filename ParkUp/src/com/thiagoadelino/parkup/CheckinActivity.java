package com.thiagoadelino.parkup;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.thiagoadelino.util.ParkUpActivity;

public class CheckinActivity extends ParkUpActivity {

	private static final String titulo = "Check-in!";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_checkin);
		setarTitulo(titulo);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.checkin, menu);
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
