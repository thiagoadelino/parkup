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

	    File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
	              Environment.DIRECTORY_PICTURES), "ParkUp");
	    if (! mediaStorageDir.exists()){
	        if (! mediaStorageDir.mkdirs()){
	            Log.d("ParkUp", "falha ao criar o diretorio");
	            return null;
	        }
	    }

	    // Create a media file name
	    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
	    File mediaFile;
	    if (type == IMAGEM){
	        mediaFile = new File(mediaStorageDir.getPath() + File.separator +
	        "IMG_"+ timeStamp + ".jpg");
	    } else {
	        return null;
	    }

	    return mediaFile;
	}
}
