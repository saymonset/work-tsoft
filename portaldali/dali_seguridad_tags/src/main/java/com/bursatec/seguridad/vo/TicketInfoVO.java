package com.bursatec.seguridad.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * Implementa el patr&oacute;n de dise&ntilde;o ValueObject para el Ticket.
 *
 * @author Domingo Suarez Torres
 * @version 1.0
 */
public class TicketInfoVO implements Serializable { 
    /**
     * Numero de serie de la clase.
     */
	private static final long serialVersionUID = 1L;

    /**
     * Clave del usuario
     */
	private String claveUsuario;
    
    /**
     * Nombre del sistema
     */
    private String nombreSistema;

    /**
     * Tipo de usuario
     */
    private String tipoUsuario;

    /**
     * Identificador del sistema
     */
    private Long idSistema;

    /**
     * Identificador de la instituci&oacute;n
     */
    private Long idInstitucion;

    /**
     * Nombre de la instituci&oacute;n
     */
    private String nombreInstitucion;

    /**
     * Identificador del ticket
     */
    private String ticket;

    /**
     * identificador del perfil
     */
    private Long idPerfil;

    /**
     * Direccion IP
     */
    private String direccionIp;
    
    private RolesUsuarioVO rolesUsuario;
	/**
	 * D&#237;as de vigencia del password
	 */
	private long diasVigenciaPassword;
	/**
	 * D&#237;as de aviso antes de que el password sea inv&#225;lido
	 */
	private long diasAvisoPassword;
	/**
	 * Fecha en que se verific&#243; el password
	 */
	private Date fechaVerificacionPassword;

	/**
    * Constructor por omisi&oacute;n.
    */
    public TicketInfoVO() {
    }

    public TicketInfoVO(String claveUsuario) {
    	setClaveUsuario(claveUsuario);
    }

    /**
      * Constructor conveniente.
      * Se requieren todas las propiedades para inicializarlas al crear el objeto.
      *
      * @param claveUsuario claveUsuario
      * @param nombreSistema nombreSistema
      * @param idSistema idSistema
      * @param ticket ticket
      * @param idPerfil idPerfil
      * @param ip ip
      */
    public TicketInfoVO(String claveUsuario, String nombreSistema,
        Long idSistema, String ticket, Long idPerfil, String ip) {
        setClaveUsuario(claveUsuario);
        setIdPerfil(idPerfil);
        setIdSistema(idSistema);
        setNombreSistema(nombreSistema);
        setTicket(ticket);
        setDireccionIp(ip);
    }

    /**
     * Regresa el valor de la propiedad <code>nombreSistema</code>
     * @return Nombre del sistema
     */
    public String getNombreSistema() {
        return nombreSistema;
    }

    /**
     * Regresa el valor de la propiedad <code>claveUsuario</code>
     * @return Clave del usuario
	 */
	public String getClaveUsuario() {
		return claveUsuario;
	}

	/**
     * Regresa el valor de la propiedad <code>tipoUsuario</code>
     * @return Tipo de usuario
     */
    public String getTipoUsuario() {
        return tipoUsuario;
    }

    /**
     * Regresa el valor de la propiedad <code>idInstitucion</code>
     * @return Identificador de la instituci&oacute;n
     */
    public Long getIdInstitucion() {
        return idInstitucion;
    }

    /**
     * Cambia el valor de la propiedad <code>idInstitucion</code>
     *
     * @param idInstitucion El identificador de la instituci&oacute;n
     */
    public void setIdInstitucion(Long idInstitucion) {
        this.idInstitucion = idInstitucion;
    }

    /**
     * Regresa el valor de la propiedad <code>idSistema</code>
     * @return Identificador del sistema
     */
    public Long getIdSistema() {
        return idSistema;
    }

    /**
     * Cambia el valor de la propiedad <code>idSistema</code>
     *
     * @param idSistema El identificador del sistema
     */
    public void setIdSistema(Long idSistema) {
        this.idSistema = idSistema;
    }

    /**
     * Regresa el valor de la propiedad <code>nombreInstitucion</code>
     * @return Nombre de la instituci&oacute;n
     */
    public String getNombreInstitucion() {
        return nombreInstitucion;
    }

    /**
     * Cambia el valor de la propiedad <code>nombreInstitucion</code>
     *
     * @param nombreInstitucion El nombre de la instituci&oacute;n
     */
    public void setNombreInstitucion(String nombreInstitucion) {
        this.nombreInstitucion = nombreInstitucion;
    }

    /**
     * Cambia el valor de la propiedad <code>claveUsuario</code>
     *
     * @param claveUsuario La clave del usuario
	 */
	public void setClaveUsuario(String claveUsuario) {
		this.claveUsuario = claveUsuario;
	}

	/**
     * Cambia el valor de la propiedad <code>nombreSistema</code>
     *
     * @param nombreSistema El nombre del sistema
     */
    public void setNombreSistema(String nombreSistema) {
        this.nombreSistema = nombreSistema;
    }

    /**
     * Cambia el valor de la propiedad <code>tipoUsuario</code>
     *
     * @param tipoUsuario El tipo del usuario
     */
    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    /**
     * Regresa el valor de la propiedad <code>ticket</code>
     *
     * @return el ticket
     */
    public String getTicket() {
        return ticket;
    }

    /**
     * Cambia el valor de la propiedad <code>ticket</code>
     *
     * @param ticket el ticket
     */
    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    /**
     * @return Returns the idPerfil.
     */
    public Long getIdPerfil() {
        return idPerfil;
    }

    /**
     * @param idPerfil The idPerfil to set.
     */
    public void setIdPerfil(Long idPerfil) {
        this.idPerfil = idPerfil;
    }

    /**
     * @return Returns the direccionIp.
	 */
	public String getDireccionIp() {
		return direccionIp;
	}

	/**
     * @param direccionIp The direccionIP to set.
	 */
	public void setDireccionIp(String direccionIp) {
		this.direccionIp = direccionIp;
	}

	/**
	 *
	 * @param ru
	 */
    public void setRolesUsuario(RolesUsuarioVO ru) {
		this.rolesUsuario = ru;
	}

	/**
	 *
	 * @return RolesUsuarioVO
	 */
	public RolesUsuarioVO getRolesUsuario() {
		return rolesUsuario;
	}
	/**
     * Obtiene el valor de la propiedad <code>diasAvisoPassword</code>
     * @return D&#237;as de aviso antes que el password sea inv&#225;lido
	 */
	public long getDiasAvisoPassword() {
		return diasAvisoPassword;
	}
	/**
     * Modifica el valor de la propiedad <code>diasAvisoPassword</code>
     * @param diasAvisoPassword D&#237;as de aviso antes que el password sea inv&#225;lido
	 */
	public void setDiasAvisoPassword(long diasAvisoPassword) {
		this.diasAvisoPassword = diasAvisoPassword;
	}
	/**
     * Obtiene el valor de la propiedad <code>diasAvisoPassword</code>
     * @return D&#237;as de aviso antes que el password sea inv&#225;lido
	 */
	public long getDiasVigenciaPassword() {
		return diasVigenciaPassword;
	}
	/**
     * Modifica el valor de la propiedad <code>diasVigenciaPassword</code>
     * @param diasVigenciaPassword Dias de vigencia del password
	 */
	public void setDiasVigenciaPassword(long diasVigenciaPassword) {
		this.diasVigenciaPassword = diasVigenciaPassword;
	}
	/**
     * Obtiene el valor de la propiedad <code>fechaVerificacionPassword</code>
     * @return Fecha en la que se verific&#243; el password
	 */
	public Date getFechaVerificacionPassword() {
		return fechaVerificacionPassword;
	}
    /**
     * Modifica el valor de la propiedad <code>fechaVerificacionPassword</code>
     * @param fechaVerificacionPassword Fecha en la que se verific&#243; el password
     */
	public void setFechaVerificacionPassword(Date fechaVerificacionPassword) {
		this.fechaVerificacionPassword = fechaVerificacionPassword;
	}
}