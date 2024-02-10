package com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "T_NOTAS_EVCO")
@SequenceGenerator(name = "foliador", sequenceName = "SEQ_T_NOTAS_EVCO", allocationSize = 1, initialValue = 1)
public class NotasEventoCorporativo implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long idNota;
	private Long idEventoCorporativo;
	private String cuerpo;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "foliador")
	@Column(name = "ID_NOTA", unique = true, nullable = false)
	public Long getIdNota() {
		return idNota;
	}
	
	public void setIdNota(Long idNota) {
		this.idNota = idNota;
	}
	
	@Column(name="ID_EVENTO_CORPORATIVO", unique = true, nullable = false)
	public Long getIdEventoCorporativo() {
		return idEventoCorporativo;
	}
	
	public void setIdEventoCorporativo(Long idEventoCorporativo) {
		this.idEventoCorporativo = idEventoCorporativo;
	}
	
	@Column(name="CUERPO", unique = true, nullable = false)
	public String getCuerpo() {
		return cuerpo;
	}
	
	public void setCuerpo(String cuerpo) {
		this.cuerpo = cuerpo;
	}
}
