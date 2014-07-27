package com.thiago.util;

import android.app.Activity;
import android.view.Window;

/**
 * Controller genérico de onde os demais devem herdar.
 * @author thiagoadelino
 *
 */
public class ParkUpActivity extends Activity{
	/**
	 * Método que seta o título da tela selecionada.
	 * @param titulo
	 */
	protected void setarTitulo(String titulo){
    	Window w = getWindow();
        w.setTitle(titulo);
    }
}
