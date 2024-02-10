package com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;



@Entity
@Table(name = "C_LISTA_DISTRIBUCION_EVCO")
@SequenceGenerator(name = "foliador", sequenceName = "SEQ_C_Lista_Distribucion_EVCO", allocationSize = 1, initialValue = 1)
public class ListaDistribucion implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long idLista;
	private String nombre;	
	private Long inactivo;	
	private String descripcion;
	
	/**
	 * Constructor de la clase
	 */
	public ListaDistribucion() {
		super();
	}

	/**
	 * Constructor de la clase
	 * 
	 * @param idLista Id de la lista
	 * @param nombre Nombre de la lista
	 * @param inactivo Estatus de la lista
	 */
	public ListaDistribucion(Long idLista, String nombre, Long inactivo, String descripcion) {
		super();
		this.idLista = idLista;
		this.nombre = nombre;
		this.inactivo = inactivo;
		this.descripcion = descripcion;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "foliador")
	@Column(name = "ID_LISTA", unique = true, nullable = false)
	public Long getIdLista() {
		return idLista;
	}
	
	public void setIdLista(Long idLista) {
		this.idLista = idLista;
	}
	
	@Column(name = "NOMBRE", unique = true, nullable = false)
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	@Column(name = "INACTIVO", unique = false, nullable = false)
	public Long getInactivo() {
		return inactivo;
	}
	
	public void setInactivo(Long inactivo) {
		this.inactivo = inactivo;
	}

	/**
	 * @return the descripcion
	 */
	@Column(name = "DESCRIPCION", unique = false, nullable = true)
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	
}
