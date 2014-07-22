package com.thiagoadelino.modelo;

import java.io.Serializable;

import com.thiagoadelino.arq.PersistDB;

public class Local implements PersistDB, Serializable{
	
	private int id;
	
	private String coordenadaX;
	
	private String coordenadaY;

	private String cidade;
	
	private String estado;
	
	private String bairro;

	public String getCoordenadaX() {
		return coordenadaX;
	}
	
	public void setCoordenadaX(String coordenadaX) {
		this.coordenadaX = coordenadaX;
	}
	
	public String getCoordenadaY() {
		return coordenadaY;
	}
	
	public void setCoordenadaY(String coordenadaY) {
		this.coordenadaY = coordenadaY;
	}
	
	public String getCidade() {
		return cidade;
	}
	
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	
	public String getEstado() {
		return estado;
	}
	
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	public String getBairro() {
		return bairro;
	}
	
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
