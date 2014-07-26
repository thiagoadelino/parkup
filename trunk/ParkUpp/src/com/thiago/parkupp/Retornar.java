package com.thiago.parkupp;

import java.text.SimpleDateFormat;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.thiago.dao.EstacionamentoDao;
import com.thiago.dao.LocalDao;
import com.thiago.modelo.EstacionamentoPU;
import com.thiago.modelo.LocalPU;
import com.thiago.modelo.VeiculoPU;
import com.thiago.util.CameraUtil;

public class Retornar extends FragmentActivity  implements LocationListener{

	/** Constante que representa o código de captura de imagem */
	private static final int CODIGO_CAPTURA_IMAGEM = 100;
	/** uri do arquivo de imagem gerado. */
	private Uri uri;
	private LatLng localizacaoCarro = new LatLng(0.0,0.0);
	
	private LatLng localizacaoPessoa = new LatLng(0.0,0.0);;
	private GoogleMap map;
	private Location location;
	private LocationManager locationManager;
	private EstacionamentoPU estacionamento;

	private MarkerOptions markerCarro;
	private MarkerOptions markerPessoa;
	//true é marcacao, false é retorno
	private boolean marcacao;
	

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
        setContentView(R.layout.retornar);
        
        locationManager = (LocationManager)getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 3000, 10, this);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 10, this);
        
        FragmentManager fmanager = getSupportFragmentManager();
        Fragment fragment = fmanager.findFragmentById(R.id.map);
        SupportMapFragment supportmapfragment = (SupportMapFragment)fragment;
        GoogleMap supportMap = supportmapfragment.getMap();
		
        map = supportMap;
        map.setMyLocationEnabled(true);
		map.getUiSettings().setZoomControlsEnabled(true);
		map.getUiSettings().setScrollGesturesEnabled(false);

		markerPessoa = new MarkerOptions().position(localizacaoPessoa).icon(BitmapDescriptorFactory.fromResource(R.drawable.pessoa));
		markerPessoa.visible(false);
		map.addMarker(markerPessoa);
        
		Location localizacao = (Location) getIntent().getSerializableExtra("localizacao");
        if(localizacao != null){
        	//Persiste Dados
        	EstacionamentoDao dao = new EstacionamentoDao(getApplicationContext());
        	LocalDao ldao = new LocalDao(getApplicationContext());
        	this.estacionamento = dao.findEstacionamentoEmAberto();
        	
        	this.estacionamento.getLocal().setCoordenadaX(localizacao.getLatitude()+"");
        	this.estacionamento.getLocal().setCoordenadaY(localizacao.getLongitude()+"");
        	ldao.atualizar(this.estacionamento.getLocal());
        	//TODO remover se der certo.
        }else{
	        EstacionamentoPU estacionamento = (EstacionamentoPU) getIntent().getSerializableExtra("estacionamento");
	        if(estacionamento != null)
	        	this.estacionamento = estacionamento;
	        else
	        {
	        	EstacionamentoDao dao = new EstacionamentoDao(getApplicationContext());
	        	this.estacionamento = dao.findEstacionamentoEmAberto();
	        	LocalDao ldao = new LocalDao(getApplicationContext());
	        	this.estacionamento.setLocal(ldao.findById(this.estacionamento.getLocal().getId()));
	        	
	        }
        }
        
		double lat = Double.parseDouble(estacionamento.getLocal().getCoordenadaX());
		double lon = Double.parseDouble(estacionamento.getLocal().getCoordenadaY());
    	
		//Renderiza botões
		if(lat!=0.0  && lon!=0.0){
			marcacao = false;
			Button retornar = (Button) findViewById(R.id.botaoretornar);
			retornar.setVisibility(View.VISIBLE);
			
			Button marcar = (Button) findViewById(R.id.botaomarcarlocal);
			marcar.setVisibility(View.GONE);
			
			localizacaoPessoa = new LatLng(lat, lon);
    		map.moveCamera(CameraUpdateFactory.newLatLngZoom(localizacaoPessoa , 15));
    		
		}else{
			marcacao = true;
			Button retornar = (Button) findViewById(R.id.botaoretornar);
			retornar.setVisibility(View.GONE);
			
			Button marcar = (Button) findViewById(R.id.botaomarcarlocal);
			marcar.setVisibility(View.VISIBLE);
			
//			if(location!=null){
//				localizacaoCarro = new LatLng(location.getLatitude(), location.getLongitude());
//				map.moveCamera(CameraUpdateFactory.newLatLngZoom(localizacaoCarro , 15));
//			}
		}
		
        
		ImageButton botaoCamera = (ImageButton) findViewById(R.id.imageView1);
		botaoCamera.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {

				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				uri = CameraUtil.getOutputMediaFileUri(CameraUtil.MEDIA_TYPE_IMAGE);
				intent.putExtra(MediaStore.EXTRA_OUTPUT, uri); 
				startActivityForResult(intent, CODIGO_CAPTURA_IMAGEM);
				
			}
		});
		
		Button retornar = (Button) findViewById(R.id.botaoretornar);
		retornar.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(Retornar.this, Caminho.class);
				startActivity(i);
			}
		});
		
		Button marcar = (Button) findViewById(R.id.botaomarcarlocal);
		marcar.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
//				Location localizacao = map.getMyLocation();
				
				localizacaoPessoa = new LatLng(location.getLatitude(), location.getLongitude());
				
				//Persiste Dados
	        	EstacionamentoDao dao = new EstacionamentoDao(getApplicationContext());
	        	LocalDao ldao = new LocalDao(getApplicationContext());
	        	
	        	estacionamento = dao.findEstacionamentoEmAberto();
	        	estacionamento.getLocal().setCoordenadaX(location.getLatitude()+"");
	        	estacionamento.getLocal().setCoordenadaY(location.getLongitude()+"");

	        	ldao.atualizar(estacionamento.getLocal());
	        	
	        	markerCarro = new MarkerOptions().position(localizacaoPessoa).icon(BitmapDescriptorFactory.fromResource(R.drawable.pin));
	        	markerCarro.visible(true);
	        	map.addMarker(markerCarro);

	        	Button btnMark = (Button) findViewById(R.id.botaomarcarlocal);
	        	btnMark.setVisibility(View.GONE);
	        	Button btnRet = (Button) findViewById(R.id.botaoretornar);
	        	btnRet.setVisibility(View.VISIBLE);
	        	
				Intent i = new Intent(Retornar.this, Retornar.class);
				startActivity(i);
				finish();
			}
		});
		
		TextView horaInicio = (TextView) findViewById(R.id.horarioinicio);
		TextView dataInicio = (TextView) findViewById(R.id.datainicioest);
		
		SimpleDateFormat sdfHora = new SimpleDateFormat("HH:mm:ss");
		SimpleDateFormat sdfData = new SimpleDateFormat("dd/MM/yyyy");
		
		horaInicio.setText(sdfHora.format(estacionamento.getHoraInicio()));
		dataInicio.setText(sdfData.format(estacionamento.getHoraInicio()));
		
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
	protected void onStart() {
		super.onStart();
//		if(markerCarro!=null)
//			markerCarro.visible(true);
//		if(markerPessoa!=null)
//			markerPessoa.visible(true);
	}
	
	/**
	 * Método chamado no retorno da Activity
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CODIGO_CAPTURA_IMAGEM) {
			if (resultCode == RESULT_OK) {
				ImageButton imagem = (ImageButton) findViewById(R.id.imageView1);
				imagem.setImageURI(uri);
				
//				veiculo.setFoto(uri.getPath());
//				
//				VeiculoDao dao = new VeiculoDao(getApplicationContext());
//				dao.alterar(veiculo);
				
				
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.retornar, menu);
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

	@Override
	public void onLocationChanged(Location location) {
		this.location= location; 
		if(location!=null){
			
			localizacaoPessoa = new LatLng(location.getLatitude(), location.getLongitude());
			
			if(marcacao){
				localizacaoCarro = new LatLng(location.getLatitude(), location.getLongitude());
//				markerPessoa = new MarkerOptions().position(localizacaoCarro).icon(BitmapDescriptorFactory.fromResource(R.drawable.pessoa));
//				markerPessoa.visible(true);
//				map.moveCamera(CameraUpdateFactory.newLatLngZoom(localizacaoCarro , 15));
			}
			
			markerPessoa = new MarkerOptions().position(localizacaoPessoa).icon(BitmapDescriptorFactory.fromResource(R.drawable.pin));
			markerPessoa.visible(true);
			map.moveCamera(CameraUpdateFactory.newLatLngZoom(localizacaoPessoa , 15));
		}
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
