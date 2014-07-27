package com.thiago.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.net.Uri;
import android.os.Environment;
import android.util.Log;

public class CameraUtil {
	public static final int IMAGEM = 1;
	
	public static Uri getOutputMediaFileUri(int type){
	      return Uri.fromFile(getOutputMediaFile(type));
	}

	private static File getOutputMediaFile(int type){

	    File diretorio = new File(Environment.getExternalStoragePublicDirectory(
	              Environment.DIRECTORY_PICTURES), "ParkUp");
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
