package com.thiago.util;

import android.app.Activity;
import android.view.Window;

/**
 * Controller gen�rico de onde os demais devem herdar.
 * @author thiagoadelino
 *
 */
public class ParkUpActivity extends Activity{
	/**
	 * M�todo que seta o t�tulo da tela selecionada.
	 * @param titulo
	 */
	protected void setarTitulo(String titulo){
    	Window w = getWindow();
        w.setTitle(titulo);
    }
}
