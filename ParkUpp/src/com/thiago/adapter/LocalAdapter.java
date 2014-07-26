package com.thiago.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.thiago.modelo.EstacionamentoPU;
@Deprecated
public class LocalAdapter extends BaseAdapter{

	private List<EstacionamentoPU> historico;

	private Context contexto;
	
	public LocalAdapter(List<EstacionamentoPU> historico, Context contexto) {
		this.historico = historico;
		this.contexto = contexto;
	}

	@Override
	public int getCount() {
		if (historico != null)
			return historico.size();
		return 0;
	}

	@Override
	public Object getItem(int position) {
		if (historico != null)
			return historico.get(position);
		return null;
	}

	@Override
	public long getItemId(int position) {
		if (historico != null)
			return historico.get(position).getId();
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater inflater = ((Activity) contexto).getLayoutInflater();

			convertView = inflater.inflate(android.R.layout.simple_list_item_2, parent, false);

		}

		TextView textViewItem = (TextView) convertView.findViewById(android.R.id.text1);
		textViewItem.setText(historico.get(position).getObservacao());
		
		TextView textViewItem2 = (TextView) convertView.findViewById(android.R.id.text2);
		textViewItem2.setText(historico.get(position).getCoordenadaX()+"-"+historico.get(position).getCoordenadaY());
		

		return convertView;
	}
}
