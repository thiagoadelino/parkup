package com.thiagoadelino.parkup;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.thiagoadelino.util.CameraUtil;
import com.thiagoadelino.util.ParkUpActivity;

/**
 * Activity responsável por capturar imagem da camera do smartphone
 * @author thiagoadelino
 *
 */
public class CameraActivity extends ParkUpActivity {
	/** Constante que representa o código de captura de imagem */
	private static final int CODIGO_CAPTURA_IMAGEM = 100;
	/** uri do arquivo de imagem gerado. */
	private Uri uri;

	/**
	 * Método chamado na criação da Activity
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_camera);

		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		
		uri = CameraUtil.getOutputMediaFileUri(CameraUtil.MEDIA_TYPE_IMAGE);
		
		intent.putExtra(MediaStore.EXTRA_OUTPUT, uri); 
		
		startActivityForResult(intent, CODIGO_CAPTURA_IMAGEM);

		setarTitulo("Foto do Carro");
	}

	/**
	 * Método chamado no retorno da Activity
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CODIGO_CAPTURA_IMAGEM) {
			if (resultCode == RESULT_OK) {
				ImageView imagem = (ImageView) findViewById(R.id.imageView1);
				imagem.setImageURI(uri);
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.camera, menu);
		setarTitulo("Foto do Carro");
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		setarTitulo("Foto do Carro");
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
