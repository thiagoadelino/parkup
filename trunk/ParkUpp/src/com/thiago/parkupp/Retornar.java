package com.thiago.parkupp;

import java.io.File;
import java.util.Date;

import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.thiago.dao.VeiculoDao;
import com.thiago.modelo.EstacionamentoPU;
import com.thiago.modelo.VeiculoPU;
import com.thiago.util.CameraUtil;

public class Retornar extends FragmentActivity implements GooglePlayServicesClient.ConnectionCallbacks,    
GooglePlayServicesClient.OnConnectionFailedListener{

	/** Constante que representa o código de captura de imagem */
	private static final int CODIGO_CAPTURA_IMAGEM = 100;
	/** uri do arquivo de imagem gerado. */
	private Uri uri;
	private LatLng frameworkSystemLocation = new LatLng(-19.92550, -43.64058);
	private GoogleMap map;
	private VeiculoPU veiculo;
	private double la;
	private double lo;
	private LocationClient locationClient;
	private Location location;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.retornar);
        getLocalizacao();
        locationClient = new LocationClient(this, this, this);
        
        EstacionamentoPU estacionamento = (EstacionamentoPU) getIntent().getSerializableExtra("estacionamento");
        
        veiculo = (VeiculoPU) getIntent().getSerializableExtra("veiculo");
        if(veiculo == null){
        	veiculo = new VeiculoPU();
        	veiculo.setCarro(true);
        	veiculo.setNome("Veiculo:" + new Date().toString());
        	estacionamento.setVeiculo(veiculo);
        	//setarLocal
        }else{
        	
        	File imgFile = new  File(veiculo.getFoto());
        	if(imgFile.exists()){
        	    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
        	    ImageButton imagem = (ImageButton) findViewById(R.id.imageView1);
        	    imagem.setImageBitmap(myBitmap);
        	}
        }
        
        
        
        
        FragmentManager fmanager = getSupportFragmentManager();
        Fragment fragment = fmanager.findFragmentById(R.id.map);
        SupportMapFragment supportmapfragment = (SupportMapFragment)fragment;
        GoogleMap supportMap = supportmapfragment.getMap();
		map = supportMap;
		map.addMarker(new MarkerOptions().position(new LatLng( locationClient.getLastLocation().getLatitude(), locationClient.getLastLocation().getLongitude())).title("Fusca"));
		
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
		
		Button botao = (Button) findViewById(R.id.botaoretornar);
		botao.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(Retornar.this, Main.class);
				startActivityForResult(i, 1);
			}
		});
	}
	
	public void getLocalizacao(){
		LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
	    Criteria criteria = new Criteria();
	    criteria.setAccuracy(Criteria.ACCURACY_FINE);
	    String provider = locationManager.getBestProvider(criteria, false);
	    Location location = locationManager.getLastKnownLocation(provider);
	    if (location != null) {
	        la = location.getLatitude();
	        lo = location.getLongitude();
	        Log.d("create", "la = " + la + " e lo = " + lo);
//	        new Thread(localizacao.this).start();
	    } else {
	        Log.d("update", "location null");
	    }
	}

//  MapFragment mf = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
//	if(mf != null) {
//		map = mf.getMap();
//	    Marker frameworkSystem = map.addMarker(new MarkerOptions()
//	                                               .position(frameworkSystemLocation)
//	                                               .title("Framework System"));
//	    // Move a câmera para Framework System com zoom 15. 
//	    map.moveCamera(CameraUpdateFactory.newLatLngZoom(frameworkSystemLocation , 15));
//	    map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
//	}
//	
	/**
	 * Método chamado no retorno da Activity
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CODIGO_CAPTURA_IMAGEM) {
			if (resultCode == RESULT_OK) {
				ImageButton imagem = (ImageButton) findViewById(R.id.imageView1);
				imagem.setImageURI(uri);
				veiculo.setFoto(uri.getPath());
				
				VeiculoDao dao = new VeiculoDao(getApplicationContext());
				dao.alterar(veiculo);
				
				
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
	    protected void onStart(){
	        super.onStart();

	        //Tentamos nos conectar ao serviço de localização.
	        locationClient.connect();
	    }

	    @Override
	    protected void onStop(){
	       //Disconectamos do serviço de localização quando o app sai do foco.
	       locationClient.disconnect();
	       super.onStop();
	    }


	    @Override
	    public void onConnected(Bundle bundle) {
	        //Estamos devidamente conectados ao serviço de localização.
	        //Podemos pegar posições a vontade agora.
	        //Se você quiser, pode usar uma variavel booleana aqui,
	        //para dizer ao seu app que ele pode pegar posições de localização
	        //diferentes de null.
	        this.location = locationClient.getLastLocation();
	    }

	    @Override
	    public void onDisconnected() {
	        //Aqui você pode alterar a variável booleana para seu app não tentar
	        //pegar mais posições de localização, embora, caso você tenha se
	        //conectado ao serviço, e você tente pegar uma localização,
	        //ele irá retornar a última localização disponível.
	    }

	    @Override
	    public void onConnectionFailed(ConnectionResult connectionResult) {
	        //O Google Play consegue resolver alguns problemas de conexão com
	        //sistema de localização. Aqui a gente verificar se o Google Play
	        //tem a solução para o erro que ocorre.
	        if (connectionResult.hasResolution()) {
	            try {
	                connectionResult.startResolutionForResult(this, 9000);              
	            } catch (IntentSender.SendIntentException e) {
	                e.printStackTrace();
	            }
	        } 

	    }

}
