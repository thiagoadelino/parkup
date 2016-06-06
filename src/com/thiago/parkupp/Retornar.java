package com.thiago.parkupp;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.thiago.arq.LocalizationActivity;
import com.thiago.dao.EstacionamentoDao;
import com.thiago.dao.VeiculoDao;
import com.thiago.modelo.EstacionamentoPU;
import com.thiago.modelo.VeiculoPU;
import com.thiago.util.CameraUtil;

public class Retornar extends LocalizationActivity{

	/** Constante que representa o código de captura de imagem */
	private static final int CODIGO_CAPTURA_IMAGEM = 100;
	/** uri do arquivo de imagem gerado. */
	private Uri uri;

	private LatLng localizacaoPessoa = new LatLng(0.0,0.0);

	private GoogleMap map;
	
	private EstacionamentoPU estacionamento;
	
	private VeiculoPU veiculo;

	private MarkerOptions markerCarro;
	
	private MarkerOptions markerPessoa;
	//true é marcacao, false é retorno
	private boolean marcacao;
	
	private void inicializaMapa(){
		map = getMapaById(R.id.map);
		map.setMyLocationEnabled(true);
		map.getUiSettings().setZoomControlsEnabled(true);
		map.getUiSettings().setScrollGesturesEnabled(false);
	}
	
	private void atualizarLocalizacaoEstacionamento(LatLng coordenadas){
		EstacionamentoDao dao = new EstacionamentoDao(getApplicationContext());
		if(estacionamento == null)
			estacionamento = new EstacionamentoPU();
		if(uri!=null)
			estacionamento.setUrlfoto(uri.getPath());
		estacionamento.setCoordenadaX(coordenadas.latitude+"");
    	estacionamento.setCoordenadaY(coordenadas.longitude+"");
    	if(estacionamento.getVeiculo()==null || estacionamento.getVeiculo().getId() == null){
    		estacionamento.setVeiculo(veiculo);
    	}
    	if(estacionamento.getId()!=null && estacionamento.getId()>0)
    		dao.atualizar(estacionamento);
    	else{
    		dao.salvar(estacionamento);
    	}
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
        setContentView(R.layout.retornar);

        EstacionamentoDao dao = new EstacionamentoDao(getApplicationContext());
        this.estacionamento = dao.findEstacionamentoEmAberto();
        veiculo = (VeiculoPU) getIntent().getSerializableExtra("veiculo");
        
        if(veiculo==null){
        	if(estacionamento!=null && estacionamento.getVeiculo()!=null && estacionamento.getVeiculo().getId()!=null){
        		VeiculoDao vDao = new VeiculoDao(getApplicationContext());
        		estacionamento.setVeiculo(vDao.findById(estacionamento.getVeiculo().getId()));
        	}
        }
        
        if(this.estacionamento==null){
        	marcacao = true;
        	estacionamento = new EstacionamentoPU();
        	estacionamento.setObservacao("");
        	estacionamento.setHoraInicio(new Date());
        	estacionamento.setCoordenadaX("0.0");
        	estacionamento.setCoordenadaY("0.0");
        	estacionamento.setVeiculo(veiculo);
        }else{
        	marcacao = false;
        	
        }
        super.inicializarLocationManager();
        inicializaMapa();
        
		double lat = 0.0;
		double lon = 0.0;
    	
		//Renderiza botões
		Button retornar = (Button) findViewById(R.id.botaoretornar);
		retornar.setVisibility(isRenderizaMarcar()?View.GONE:View.VISIBLE);
		
		Button marcar = (Button) findViewById(R.id.botaomarcarlocal);
		marcar.setVisibility(!isRenderizaMarcar()?View.GONE:View.VISIBLE);
		
		marcarCarro(new LatLng(lat, lon));
		markerCarro.visible(true);
	
		marcarPessoa(localizacaoPessoa);
		markerPessoa.visible(false);
		
		map.addMarker(markerPessoa);
		
		ImageButton botaoCamera = (ImageButton) findViewById(R.id.imageView1);
		botaoCamera.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {

				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				uri = CameraUtil.getOutputMediaFileUri(CameraUtil.IMAGEM);
				intent.putExtra(MediaStore.EXTRA_OUTPUT, uri); 
				startActivityForResult(intent, CODIGO_CAPTURA_IMAGEM);
				
			}
		});
		
		retornar = (Button) findViewById(R.id.botaoretornar);
		retornar.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(Retornar.this, Caminho.class);
				startActivity(i);
				finish();
			}
		});
		
		marcar = (Button) findViewById(R.id.botaomarcarlocal);
		marcar.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				atualizarLocalizacaoEstacionamento(localizacaoPessoa);
				
		        marcarCarro(localizacaoPessoa);
	        	map.addMarker(markerCarro);
	        	
	        	Button btnMark = (Button) findViewById(R.id.botaomarcarlocal);
	        	btnMark.setVisibility(View.GONE);
	        	Button btnRet = (Button) findViewById(R.id.botaoretornar);
	        	btnRet.setVisibility(View.VISIBLE);
	        	Toast.makeText(getApplicationContext(), "Marcação realizada!", Toast.LENGTH_SHORT).show();
			}
		});
		
		TextView horaInicio = (TextView) findViewById(R.id.horarioinicio);
		TextView dataInicio = (TextView) findViewById(R.id.datainicioest);
		
		SimpleDateFormat sdfHora = new SimpleDateFormat("HH:mm:ss");
		SimpleDateFormat sdfData = new SimpleDateFormat("dd/MM/yyyy");
		if(estacionamento != null){
			horaInicio.setText(sdfHora.format(estacionamento.getHoraInicio()));
			dataInicio.setText(sdfData.format(estacionamento.getHoraInicio()));
		}else{
			horaInicio.setText("");
			dataInicio.setText("");
		}
		
		if(!marcacao && estacionamento!=null && estacionamento.getId()!=null){
			marcarCarro(new LatLng(
						Double.parseDouble(estacionamento.getCoordenadaX()), 
						Double.parseDouble(estacionamento.getCoordenadaY())));
			
        	map.addMarker(markerCarro);
		}
		
		if(estacionamento.getUrlfoto()!=null){
			Bitmap bm = CameraUtil.getBitmapByUri(estacionamento.getUrlfoto(), 8);
			if(bm!=null)
			    botaoCamera.setImageBitmap(bm);
		}else if(uri!=null){
			
			Bitmap bm = CameraUtil.getBitmapByUri(uri, 8);
			if(bm!=null)
			    botaoCamera.setImageBitmap(bm);
		}
	}
	
	private void marcarPessoa(LatLng loc){
		markerPessoa = new MarkerOptions().position(loc).icon(BitmapDescriptorFactory.fromResource(R.drawable.pessoa));
		markerPessoa.visible(true);
	}
	
	private void marcarCarro(LatLng loc){
		markerCarro = new MarkerOptions().position(loc).icon(BitmapDescriptorFactory.fromResource(R.drawable.car));
		markerCarro.visible(true);
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
	protected void onStart() {
		super.onStart();
	}
	
	/**
	 * Método chamado no retorno da Activity
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CODIGO_CAPTURA_IMAGEM) {
			if (resultCode == RESULT_OK) {
				ImageButton imagem = (ImageButton) findViewById(R.id.imageView1);
				if(uri!=null){
					Bitmap bm = CameraUtil.getBitmapByUri(uri, 8);
					if(bm!=null){
						imagem.setImageBitmap(bm);
					}
				}
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
		super.onLocationChanged(location);
		this.localizacaoPessoa = new LatLng(location.getLatitude(), location.getLongitude());
		marcarPessoa(localizacaoPessoa);
		markerPessoa.visible(true);
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(localizacaoPessoa , 15));
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
	}

	public boolean isRenderizaMarcar(){
		return this.marcacao;
	}
	
	@Override
	public void onProviderEnabled(String provider) {
	}

	@Override
	public void onProviderDisabled(String provider) {
	}
}
