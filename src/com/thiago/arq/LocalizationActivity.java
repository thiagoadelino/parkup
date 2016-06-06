package com.thiago.arq;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;

/**
 * Activity que manipula a chamada ao serviço de localização e mapas.
 * @author thiagoadelino
 *
 */
public abstract class LocalizationActivity extends FragmentActivity implements LocationListener{

	protected LocationManager locationManager;
	protected Location location;
	
	/**
	 * Método que inicializa o gerenciador de localização do dispositivo. É a partir dele que
	 * será indicado a forma de localização e o tempo de atualização.
	 */
	protected void inicializarLocationManager(){
		locationManager = (LocationManager)getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 10, this);
	}

	/**
	 * Atualiza o atributo da classe sempre que a localização mudar.
	 */
	@Override
	public void onLocationChanged(Location location) {
		this.location= location; 
	}

	/**
	 * Retorna um mapa com base no id do componente passado como parâmetro.
	 * @return
	 */
	protected GoogleMap getMapaById(int id){
		FragmentManager fmanager = getSupportFragmentManager();
		Fragment fragment = fmanager.findFragmentById(id);
		SupportMapFragment supportmapfragment = (SupportMapFragment)fragment;
		return supportmapfragment.getMap();
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
