package com.thiago.modelo;

import java.io.Serializable;

import com.thiago.arq.PersistDB;

public class VeiculoPU implements PersistDB, Serializable{
	
	private static final long serialVersionUID = 36491484319661070L;

	private Integer id;
	
	private String nome;
	
	private boolean carro;
	
	private String foto;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public boolean isCarro() {
		return carro;
	}

	public void setCarro(boolean carro) {
		this.carro = carro;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	@Override
	public String toString(){
		String str = "";
		if(nome!=null)
			str +=nome + " ";
		if(isCarro())
			str += " [Carro] ";
		else
			str += " [Moto] ";
		return str;
	}

}
