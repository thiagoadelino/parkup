package com.thiago.parkupp;

import java.text.DecimalFormat;
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
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.thiago.dao.EstacionamentoDao;
import com.thiago.modelo.EstacionamentoPU;
import com.thiago.util.LocalizacaoUtil;

public class Caminho extends FragmentActivity implements LocationListener{
	
	private LocationManager locationManager;
	private GoogleMap map;
	private EstacionamentoPU estacionamento;
	private MarkerOptions markerPessoa;
	private MarkerOptions markerCarro;
	private LatLng localizacaoPessoa = new LatLng(0.0,0.0);
	
	private Context contexto;
	
	private double distanciaCarroPessoa;
	
	private void inicializaLocationManager(){
		locationManager = (LocationManager)getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 3000, 10, this);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 10, this);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.caminho);
		
		contexto = this;
		LocalizacaoUtil l = new LocalizacaoUtil(contexto);
		l.execute();
		
		inicializaLocationManager();
		
        EstacionamentoDao dao = new EstacionamentoDao(getApplicationContext());
    	estacionamento = dao.findEstacionamentoEmAberto();
		
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
		markerCarro.visible(true);
		map.addMarker(markerCarro);
		
		markerPessoa = new MarkerOptions().position(localizacaoPessoa).
		icon(BitmapDescriptorFactory.fromResource(R.drawable.pessoa));
		markerPessoa.visible(false);
		map.addMarker(markerPessoa);		
		
		ImageButton botao = (ImageButton) findViewById(R.id.confirmarEncontro);
		botao.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				AlertDialog.Builder adb = new AlertDialog.Builder(contexto);
				adb.setTitle("ParkUp!");
				adb.setMessage("Você encontrou seu veículo?");
				adb.setPositiveButton("Sim", 
						new DialogInterface.OnClickListener() { 
		 					public void onClick(DialogInterface arg0, int arg1) { 
								EstacionamentoDao dao = new EstacionamentoDao(getApplicationContext());
								estacionamento = dao.findEstacionamentoEmAberto();
								estacionamento.setHoraFim(new Date());
								dao.atualizar(estacionamento);

								Intent i = new Intent(Caminho.this, Qualifique.class);
								i.putExtra("estacionamento", estacionamento);
								startActivity(i);
								finish();

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
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.caminho, menu);
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

	public LatLng getLocalizacaoVeiculo(){
		EstacionamentoDao dao = new EstacionamentoDao(getApplicationContext());
    	estacionamento = dao.findEstacionamentoEmAberto();
		return new LatLng(Double.parseDouble(estacionamento.getCoordenadaX()), Double.parseDouble(estacionamento.getCoordenadaY()));
	}
	
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
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
		TextView distancia = (TextView) findViewById(R.id.distancia);
		
		distancia.setText(retornaDistancia(
				markerCarro.getPosition().latitude, 
				markerCarro.getPosition().longitude, 
				markerPessoa.getPosition().latitude,
				markerPessoa.getPosition().longitude));
		
	}
	
	private String retornaDistancia(double lat1, double lon1, double lat2, double lon2) {
			int R = 6371000;
		    double dLat = Math.toRadians(lat2 - lat1);
		    double dLon = Math.toRadians(lon2 - lon1);
		    lat1 = Math.toRadians(lat1);
		    lat2 = Math.toRadians(lat2);

		    double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.sin(dLon / 2) * Math.sin(dLon / 2) * Math.cos(lat1) * Math.cos(lat2);

		    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

		    double resultado = R*c;
		    
		    DecimalFormat distanciaEmMetros = new DecimalFormat("####,#"); 
		    String distanciaFormatada = distanciaEmMetros.format(resultado); 
		    return distanciaFormatada + " metros";
		
	}
	
	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
	}

	@Override
	public void onProviderEnabled(String provider) {
	}

	@Override
	public void onProviderDisabled(String provider) {
	}
}
