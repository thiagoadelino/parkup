package com.thiago.parkupp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.support.v4.app.FragmentActivity;

import com.google.android.maps.MapActivity;
import com.thiago.util.CameraUtil;

public class Retornar extends FragmentActivity {

	/** Constante que representa o código de captura de imagem */
	private static final int CODIGO_CAPTURA_IMAGEM = 100;
	/** uri do arquivo de imagem gerado. */
	private Uri uri;
//	private LatLng frameworkSystemLocation = new LatLng(-19.92550, -43.64058);
//	private GoogleMap map;
	  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.retornar);
 
//        FragmentManager fmanager = getSupportFragmentManager();
//        Fragment fragment = fmanager.findFragmentById(R.id.mapadeveiculo);
//        SupportMapFragment supportmapfragment = (SupportMapFragment)fragment;
//        GoogleMap supportMap = supportmapfragment.getMap();
//		
//		
//		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.mapadeveiculo))
//		        .getMap();
//		    Marker frameworkSystem = map.addMarker(new MarkerOptions()
//		                                               .position(frameworkSystemLocation)
//		                                               .title("Framework System"));
//		    // Move a câmera para Framework System com zoom 15. 
//		    map.moveCamera(CameraUpdateFactory.newLatLngZoom(frameworkSystemLocation , 15));
//		    map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
//		
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
	
	/**
	 * Método chamado no retorno da Activity
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CODIGO_CAPTURA_IMAGEM) {
			if (resultCode == RESULT_OK) {
				ImageButton imagem = (ImageButton) findViewById(R.id.imageView1);
				imagem.setImageURI(uri);
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
}
