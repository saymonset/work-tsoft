package com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.indeval.portalinternacional.middleware.servicios.modelo.Guardable;


@Entity
@Table(name = "T_ADJUNTOS_EVCO")
@SequenceGenerator(name = "foliador", sequenceName = "SEQ_T_ADJUNTOS_EVCO", allocationSize = 1, initialValue = 1)
public class AdjuntosEventoCorporativo implements Guardable {
	
	private Long idAdjuntos;
	private Long idEventoCorporativo;
	private byte[] archivo;
	private String descripcion;
	private String borrado;
	private boolean guardable = true;
	
	
	public AdjuntosEventoCorporativo(){
		
	}
	
	public AdjuntosEventoCorporativo(Long idAdjuntos, Long idEventoCorporativo, String descripcion, String borrado){
		this.idAdjuntos=idAdjuntos;
		this.idEventoCorporativo=idEventoCorporativo;
		this.descripcion=descripcion;
		this.borrado=borrado;
	}
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "foliador")
	@Column(name = "ID_ADJUNTOS", unique = true, nullable = false)
	public Long getIdAdjuntos() {
		return idAdjuntos;
	}
	
	public void setIdAdjuntos(Long idAdjuntos) {
		this.idAdjuntos = idAdjuntos;
	}
	
	@Column(name="ID_EVENTO_CORPORATIVO", unique = true, nullable = false)
	public Long getIdEventoCorporativo() {
		return idEventoCorporativo;
	}
	
	public void setIdEventoCorporativo(Long idEventoCorporativo) {
		this.idEventoCorporativo = idEventoCorporativo;
	}
	
	@Column(name="ARCHIVO", unique = true, nullable = false)
	@Lob
	public byte[] getArchivo() {
		return archivo;
	}
	
	public void setArchivo(byte[] archivo) {
		this.archivo = archivo;
	}
	
	@Column(name="DESCRIPCION", unique = false, nullable = false)
	public String getDescripcion() {
		return descripcion;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	@Column(name="BORRADO", unique = false, nullable = false)
	public String getBorrado() {
		return borrado;
	}
	
	public void setBorrado(String borrado) {
		this.borrado = borrado;
	}
	@Transient
	public boolean esGuardable() {		
		return this.guardable;
	}

	/**
	 * @param guardable the guardable to set
	 */
	public void setGuardable(boolean guardable) {
		this.guardable = guardable;
	}
}
