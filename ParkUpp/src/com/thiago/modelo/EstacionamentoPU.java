package com.thiago.modelo;

import java.io.Serializable;
import java.util.Date;

import com.thiago.arq.PersistDB;

public class EstacionamentoPU implements PersistDB, Serializable{

	private static final long serialVersionUID = 3595870217408496615L;

	private int id;
	
	private VeiculoPU veiculo;
	
	private LocalPU local;
	
	private Date horaInicio;
	
	private Date horaFim;
	
	private String observacao;
	
	private int qualificacao;

	public VeiculoPU getVeiculo() {
		return veiculo;
	}

	public void setVeiculo(VeiculoPU veiculo) {
		this.veiculo = veiculo;
	}

	public LocalPU getLocal() {
		return local;
	}

	public void setLocal(LocalPU local) {
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
