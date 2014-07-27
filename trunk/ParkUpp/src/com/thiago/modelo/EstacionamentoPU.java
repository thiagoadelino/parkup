package com.thiago.modelo;

import java.io.Serializable;
import java.util.Date;

import com.thiago.arq.PersistDB;

public class EstacionamentoPU implements PersistDB, Serializable{

	private static final long serialVersionUID = 3595870217408496615L;

	private Integer id;
	
	private VeiculoPU veiculo;
	
	private String coordenadaX;
	
	private String coordenadaY;
	
	private Date horaInicio;
	
	private Date horaFim;
	
	private String observacao;
	
	private String outrasInformacoes;
	
	private int qualificacao;

	public VeiculoPU getVeiculo() {
		return veiculo;
	}

	public void setVeiculo(VeiculoPU veiculo) {
		this.veiculo = veiculo;
	}

	public Date getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(Date horaInicio) {
		this.horaInicio = horaInicio;
	}

	public Date getHoraFim() {
		return horaFim;
	}

	public void setHoraFim(Date horaFim) {
		this.horaFim = horaFim;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public int getQualificacao() {
		return qualificacao;
	}

	public void setQualificacao(int qualificacao) {
		this.qualificacao = qualificacao;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

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

	public String getOutrasInformacoes() {
		return outrasInformacoes;
	}

	public void setOutrasInformacoes(String outrasInformacoes) {
		this.outrasInformacoes = outrasInformacoes;
	} 
}
