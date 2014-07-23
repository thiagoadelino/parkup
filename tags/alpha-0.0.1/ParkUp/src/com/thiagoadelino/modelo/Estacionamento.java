package com.thiagoadelino.modelo;

import java.io.Serializable;
import java.util.Date;

import com.thiagoadelino.arq.PersistDB;

public class Estacionamento implements PersistDB, Serializable{

	private static final long serialVersionUID = 3595870217408496615L;

	private int id;
	
	private Veiculo veiculo;
	
	private Local local;
	
	private Date horaInicio;
	
	private Date horaFim;
	
	private String observacao;
	
	private int qualificacao;

	public Veiculo getVeiculo() {
		return veiculo;
	}

	public void setVeiculo(Veiculo veiculo) {
		this.veiculo = veiculo;
	}

	public Local getLocal() {
		return local;
	}

	public void setLocal(Local local) {
		this.local = local;
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	} 
}
