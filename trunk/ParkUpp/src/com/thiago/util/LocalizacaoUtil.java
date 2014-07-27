package com.thiago.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.thiago.dao.EstacionamentoDao;
import com.thiago.modelo.EstacionamentoPU;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

public class LocalizacaoUtil extends AsyncTask<Void, Void, Void>{

	public String enderecoResult;
	
	public Context contexto;
	
	public LocalizacaoUtil(){
		
	}
	
	public LocalizacaoUtil(Context contexto){
		this.contexto = contexto;
	}
	
	public String buscarEndereco(double lat, double lon, boolean titulo) {

		String endereco = "";
		String bairro = "";
		String cidade = "";
		String estado = "";
		try {

			JSONObject jsonObj = getJSONfromURL("http://maps.googleapis.com/maps/api/geocode/json?latlng="
					+ lat + "," + lon + "&sensor=true");
			String Status = jsonObj.getString("status");
			if (Status.equalsIgnoreCase("OK")) {
				JSONArray Results = jsonObj.getJSONArray("results");
				JSONObject zero = Results.getJSONObject(0);
				JSONArray address_components = zero
						.getJSONArray("address_components");

				for (int i = 0; i < address_components.length(); i++) {
					JSONObject zero2 = address_components.getJSONObject(i);
					String long_name = zero2.getString("short_name");
					JSONArray mtypes = zero2.getJSONArray("types");
					String Type = mtypes.getString(0);

					if (TextUtils.isEmpty(long_name) == false
							|| !long_name.equals(null)
							|| long_name.length() > 0 || long_name != "") {
						if (Type.equalsIgnoreCase("route")) {
							endereco = long_name;
						} else if (Type.equalsIgnoreCase("neighborhood")) {
							bairro = long_name;
						} else if (Type.equalsIgnoreCase("locality")) {
							cidade = long_name;
						} else if (Type.equalsIgnoreCase("administrative_area_level_1")) {
							estado = long_name;
						}

					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		if(titulo)
			return bairro + " - " + cidade+"/"+estado;
		else
			return endereco;
	}

	public static JSONObject getJSONfromURL(String url) {

		// initialize
		InputStream is = null;
		String result = "";
		JSONObject jObject = null;

		// http post
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(url);
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();

		} catch (Exception e) {
			Log.e("log_tag", "Error in http connection " + e.toString());
		}

		// convert response to string
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "utf-8"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			result = sb.toString();
		} catch (Exception e) {
			Log.e("log_tag", "Error converting result " + e.toString());
		}

		// try parse the string to a JSON object
		try {
			jObject = new JSONObject(result);
		} catch (JSONException e) {
			Log.e("log_tag", "Error parsing data " + e.toString());
		}

		return jObject;
	}

	@Override
	protected Void doInBackground(Void... params) {
	
		
		EstacionamentoDao dao = new EstacionamentoDao(this.contexto);
		List<EstacionamentoPU> estacionamentos = dao.findAll();
		
		for(EstacionamentoPU est: estacionamentos){
			double lat = Double.parseDouble(est.getCoordenadaX());
			double lon = Double.parseDouble(est.getCoordenadaY());
			if(lat!=0.0 && lon !=0.0){
				est.setObservacao(buscarEndereco(lat, lon, true));
				est.setOutrasInformacoes(buscarEndereco(lat, lon, false));
			}
			dao.atualizar(est);
		}
		
		return null;
	}

}
