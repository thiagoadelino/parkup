package com.thiago.parkupp;

import java.util.Date;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.thiago.dao.EstacionamentoDao;
import com.thiago.dao.LocalDao;
import com.thiago.modelo.EstacionamentoPU;

public class Caminho extends FragmentActivity implements LocationListener{
	
	private LocationManager locationManager;
	private GoogleMap map;
	private EstacionamentoPU estacionamento;
	private MarkerOptions markerPessoa;
	private MarkerOptions markerCarro;
	private LatLng localizacaoPessoa = new LatLng(0.0,0.0);
	
	private Context contexto;
	
	private double distanciaCarroPessoa;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.caminho);
		contexto = this;
		locationManager = (LocationManager) getSystemService(getApplicationContext().LOCATION_SERVICE);
        locationManager.requestLocationUpdates( LocationManager.GPS_PROVIDER,
                3000, 10, this);
//        locationManager.requestLocationUpdates( LocationManager.NETWORK_PROVIDER,
//                3000, 10, this);
        EstacionamentoDao dao = new EstacionamentoDao(getApplicationContext());
    	LocalDao ldao = new LocalDao(getApplicationContext());
    	estacionamento = dao.findEstacionamentoEmAberto();
		estacionamento.setLocal(ldao.findById(estacionamento.getLocal().getId()));
		
		FragmentManager fmanager = getSupportFragmentManager();
        Fragment fragment = fmanager.findFragmentById(R.id.maparetorno);
        SupportMapFragment supportmapfragment = (SupportMapFragment)fragment;
        GoogleMap supportMap = supportmapfragment.getMap();
        
        map = supportMap;
        map.setMyLocationEnabled(true);
		map.getUiSettings().setZoomControlsEnabled(true);
		map.getUiSettings().setScrollGesturesEnabled(true);

		map.moveCamera(CameraUpdateFactory.newLatLngZoom((getLocalizacaoVeiculo()) , 15));
		
		
		markerCarro = new MarkerOptions().position(getLocalizacaoVeiculo()).
				icon(BitmapDescriptorFactory.fromResource(R.drawable.car));
		map.addMarker(markerCarro);
		
		markerPessoa = new MarkerOptions().position(localizacaoPessoa).
		icon(BitmapDescriptorFactory.fromResource(R.drawable.pessoa));
		markerPessoa.visible(false);
		map.addMarker(markerPessoa);		
		
		
		
		
		
		
		Button botao = (Button) findViewById(R.id.confirmarEncontro);
		botao.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				AlertDialog.Builder adb = new AlertDialog.Builder(contexto);
				adb.setTitle("?");
				adb.setMessage("Você encontrou seu veículo?");
				adb.setPositiveButton("Sim", 
						new DialogInterface.OnClickListener() { 
							public void onClick(DialogInterface arg0, int arg1) { 
								EstacionamentoDao dao = new EstacionamentoDao(getApplicationContext());
								estacionamento = dao.findEstacionamentoEmAberto();
								estacionamento.setHoraFim(new Date());
								dao.atualizar(estacionamento);

								Intent i = new Intent(Caminho.this, Main.class);
								startActivity(i);

								Toast.makeText(contexto, "Estacionamento finalizado!", Toast.LENGTH_SHORT).show(); } 
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
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.caminho, menu);
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

	public LatLng getLocalizacaoVeiculo(){
		EstacionamentoDao dao = new EstacionamentoDao(getApplicationContext());
    	LocalDao ldao = new LocalDao(getApplicationContext());
    	estacionamento = dao.findEstacionamentoEmAberto();
		estacionamento.setLocal(ldao.findById(estacionamento.getLocal().getId()));
		return new LatLng(Double.parseDouble(estacionamento.getLocal().getCoordenadaX()), Double.parseDouble(estacionamento.getLocal().getCoordenadaY()));
	}
	
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onPostCreate(savedInstanceState);
		super.onStart();
		if(markerCarro!=null)
			markerCarro.visible(true);
		if(markerPessoa!=null)
			markerPessoa.visible(true);
	}
	
	@Override
	public void onLocationChanged(Location location) {
		localizacaoPessoa = new LatLng(location.getLatitude(), location.getLongitude());
		markerPessoa = new MarkerOptions().position(localizacaoPessoa).icon(BitmapDescriptorFactory.fromResource(R.drawable.pin));
		markerPessoa.visible(true);
		map.moveCamera(CameraUpdateFactory.newLatLngZoom( localizacaoPessoa, 15));
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}
}
