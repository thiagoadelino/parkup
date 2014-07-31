package com.thiago.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

/**
 * Classe utilitária criada para manipulação de imagens.
 * @author thiagoadelino
 *
 */
public class CameraUtil {
	
	public static final int IMAGEM = 1;
	
	/**
	 * Método que retorna um bitmap com base na uri e na propoção desejada para carregar a imagem.
	 * @param uri
	 * @param proporcao
	 * @return
	 */
	public static Bitmap getBitmapByUri(Uri uri, int proporcao) {
		return getBitmapByUri(uri.getPath(), proporcao);
	}
	
	/**
	 * Método que retorna um bitmap com base no caminho do arquivo e na propoção desejada para carregar a imagem.
	 * @param uri
	 * @param proporcao
	 * @return
	 */
	public static Bitmap getBitmapByUri(String path, int proporcao) {
		File imgFile = new  File(path);
		if(imgFile.exists()){
			BitmapFactory.Options op = new BitmapFactory.Options();
			op.inSampleSize = proporcao;
			
		    Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath(), op);
		    return bitmap;
		}
		return null;
	}
	
	
	public static Uri getOutputMediaFileUri(int type){
	      return Uri.fromFile(getOutputMediaFile(type));
	}

	private static File getOutputMediaFile(int type){

	    File diretorio = new File(Environment.getExternalStoragePublicDirectory(
	              Environment.DIRECTORY_PICTURES), "ParkApp");
	    if (! diretorio.exists()){
	        if (! diretorio.mkdirs()){
	            Log.d("ParkUp", "falha ao criar o diretorio");
	            return null;
	        }
	    }

	    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
	    File mediaFile;
	    if (type == IMAGEM){
	        mediaFile = new File(diretorio.getPath() + File.separator +
	        "PARK_"+ timeStamp + ".jpg");
	    } else {
	        return null;
	    }

	    return mediaFile;
	}
}
