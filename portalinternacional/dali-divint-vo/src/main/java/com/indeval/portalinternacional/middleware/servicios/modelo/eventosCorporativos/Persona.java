package com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.indeval.portaldali.persistence.modelo.Institucion;

@Entity
@Table(name = "T_PERSONA_EVCO")
@SequenceGenerator(name = "foliador", sequenceName = "SEQ_T_Persona_EVCO", allocationSize = 1, initialValue = 1)
public class Persona implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long idPersona;
	private String nombre;
	private String correo;
	private String descripcion;
	private Long inactivo;
	private Institucion institucion;
	private Date fechaActualizacion;
	private List<ListaDistribucion> listas;
	private Long listaDistribucion;

	/**
	 * Constructor de la clase
	 */
	public Persona() {
		super();
	}

	/**
	 * Constructor de la clase
	 * @param idPersona Id persona
	 * @param nombre Nombre persona
	 * @param correo Correo persona
	 * @param descripcion Descripción persona
	 * @param inactivo Estatus
	 */
	public Persona(Long idPersona, String nombre, String correo,
			String descripcion, Long inactivo) {
		super();
		this.idPersona = idPersona;
		this.nombre = nombre;
		this.correo = correo;
		this.descripcion = descripcion;
		this.inactivo = inactivo;
	}	
	
	@Column(name = "NOMBRE", unique = false, nullable = false)
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
	 * @return the idPersona
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "foliador")
	@Column(name = "ID_PERSONA", unique = true, nullable = false)
	public Long getIdPersona() {
		return idPersona;
	}

	/**
	 * @param idPersona the idPersona to set
	 */
	public void setIdPersona(Long idPersona) {
		this.idPersona = idPersona;
	}

	/**
	 * @return the correo
	 */
	@Column(name = "correo", unique = false, nullable = false)
	public String getCorreo() {
		return correo;
	}

	/**
	 * @param correo the correo to set
	 */
	public void setCorreo(String correo) {
		this.correo = correo;
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

	/**
	 * @return the institucion
	 */
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_PARTICIPANTE", referencedColumnName="ID_INSTITUCION",unique = false, nullable = true, insertable=true, updatable=true)	
	public Institucion getInstitucion() {
		return institucion;
	}

	
	
	/**
	 * @param institucion the institucion to set
	 */
	public void setInstitucion(Institucion institucion) {
		this.institucion = institucion;
	}

	/** Lista de personas asociadas al grupo */
	@OneToMany(fetch=FetchType.LAZY)
    @JoinTable(
            name="T_Lista_Persona_EVCO", 
            joinColumns =  @JoinColumn( name="ID_LISTA"),
            inverseJoinColumns = @JoinColumn( name="ID_PERSONA")
    )
	public List<ListaDistribucion> getListas() {
		return listas;
	}

	/**
	 * @param listas the listas to set
	 */
	public void setListas(List<ListaDistribucion> listas) {
		this.listas = listas;
	}

	/**
	 * Para busqueda
	 * @return the listaDistribucion
	 */
	@Transient
	public Long getListaDistribucion() {
		return listaDistribucion;
	}

	/**
	 * @param listaDistribucion the listaDistribucion to set
	 */
	public void setListaDistribucion(Long listaDistribucion) {
		this.listaDistribucion = listaDistribucion;
	}
	
	public void setListaDistribucion(String listaDistribucion) {
		if(listaDistribucion != null){
			this.listaDistribucion = Long.valueOf(listaDistribucion);
		}
	}

	/**
	 * @return the fechaActualizacion
	 */
	@Column(name = "FECHA_ACTUALIZACION", unique = false, nullable = true)
	public Date getFechaActualizacion() {
		return fechaActualizacion;
	}

	/**
	 * @param fechaActualizacion the fechaActualizacion to set
	 */
	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}
	
}
