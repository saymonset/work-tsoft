/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

/**
 * Clase que realiza el mapeo con la tabla T_REGISTRO_INSTRUCCIONES_MATCH.
 *
 * @author Imelda Vel&aacute;zquez L&oacute;pez
 */
@Entity
@Table(name = "T_REGISTRO_INSTRUCCIONES_MATCH")
public class MensajeBean implements Serializable, Cloneable {

    /** Versi&oacute;n. */
    private static final long serialVersionUID = 1L;

    /** Estados que puede tomar una instrucci&oacute;n durante el proceso de match. */
    public enum EstadoInstruccion {
        /** Estado invalido. */
        INVALIDA,
        /** Estado de Pre Match. */
        PRE_MATCH,
        /** Estado Sin Match. */
        SIN_MATCH,
        /** Estado Con Match. */
        CON_MATCH,
        /** Estado Son Posible Match. */
        POSIBLE_MATCH,
        /** Estado Expirado. */
        EXPIRADA,
        /** Estado Cancelado. */
        CANCELADA,
        /** Estado No Requiere Match. */
        NO_REQUIERE_MATCH,
        /** Estado Cancelado No Requiere Macth. */
        CANCELADA_NO_REQUIERE_MATCH,
        /** Cancelar aplicado */
        CANCELAR_APLICADO,
        /** Cancelar no aplicado */
        CANCELAR_NO_APLICADO
    }
    
    public static final Character SI = new Character('S');
    
    public static final Character NO = new Character('N');

    /** Identificador de la instrucci&oacute;n en la base de datos. */
    private BigInteger idInstruccion;

    /** Valor del folio asignado a la instrucci&oacute;n. */
    private String folioInstruccion;

    /** Valor del folio asignado a la instrucci&oacute;n con la que hace match. */
    private String folioInstruccionMatch;

    /** Valor del tipo de la operaci&oacute;n. */
    private String tipoOperacionInstruccion;

    /** Valor del tipo de la instrucci&oacute;n. */
    private String tipoMensaje;

    /** Identifica el medio de entrega del mensaje o corro. */
    private String origen;

    /** Estado descriptivo del mensaje dentro del proceso match. */
    private EstadoInstruccion estadoMensaje;

    /** Mapa con los campos del mensaje xml. */
    private Map < String, CampoBean > campos;

    /** Cadena con el contenido del mensaje recibido en el m&oacute;dulo match. */
    private String xml;

    /** Valor de la llave que identifica una pareja con match. */
    private int matchKey;

    /** Valor de la llave que identifica una pareja con un posible match. */
    private int posibleMatchKey;

    /** Indica si un mensaje ha sido procesado por el m&oacute;dulo match. */
    private boolean procesado;

    /** Valor de la fecha en que fu&eacute; recibido el mensaje por el m&oacute;dulo match. */
    private Timestamp fechaHoraRecepcion;

    /** Valor de la fecha en que debe ser liquidada la transacci&oacute;n. */
    private Date fechaLiquidacion;

    /** Indica en que proceso entr&oacute; el mensaje. */
    private Integer numeroProceso = -1;

    /**
     * Indica si una instrucci&oacute;n se encuentra: en proceso(EP), sin procesar(SP) o si ha finalizado su proceso(PF).
     */
    private String estadoProceso;

    /** Variable de versionamiento utilizada por hibernate*/
    private Integer version;
    
    /** Indica si una instrucción expira o no. */
    private Character expira;
    
    /** Id y folio del participante receptor. */
    private String idFolioReceptor;
    
    /** Cuenta del participante receptor. */
    private String cuentaReceptor;
    
    /** Id y folio del participante traspasante. */
    private String idFolioTraspasante;
    
    /** Cuenta del participante traspasante. */
    private String cuentaTraspasante;
    
    /** Tipo valor. */
    private String tipoValor;
    
    /** El instrumento que corresponde al tipo de valor */
    private Instrumento instrumento;
    
    /** Valor de la emisora. */
    private String emisora;
    
    /** Valor de la serie. */
    private String serie;
    
    /** Valor del cupon. */
    private String cupon;
    
    /** Fecha del reporto. */
    private Date fechaReporto;
    
    /** Precio titulo. */
    private String precioTitulo;
    
    /** Tasa negociada. */
    private String tasaNegociada;
    
    /** Tasa referencia. */
    private String tasaReferencia;
    
    /** Cantidad de titulos. */
    private String cantidadTitulos;
    
    /** Importe. */
    private String importe;
    
    /** Folio control. */
    private String folioControl;
    
    /** Fecha hora registro. */
    private Timestamp fechaHoraRegistro; 
    
    /** Id y folio del participante que envia la operacion. */
    private String idFolioParticipanteVO;
    
    /** El plazo para las operaciones de reporto */
    private BigInteger plazo;
    
     
    /** La boveda de valores*/
    private String boveda;
        
    /** La boveda de efectivo*/
    private String bovedaEfectivo;
    
    /** La divisa del efectivo*/
    private String divisa;    
    
    
    /*Inicia campos nuevos para sincronizacion con efectivo.*/

//    comentando TEFV     
    
    private BigInteger referenciaParticipante;
    
//  comentando TEFV    
//    private String importeIntereses;
    
//    private String tipoLiquidacionInicio;
    
//    private String tipoLiquidacionVencimiento;
    
    
    //---------------------
    
    private String referenciaPaquete;
	
	private String totalOperacionesPaquete;
	
	private String numeroOperacionPaquete;
	
	private String totalTitulosPaquete;
	
	private String totalImportePaquete;
    
    
    public MensajeBean() {
    	this.expira = NO;
    }
    
    /**
	 * Obtiene el valor del atributo instrumento
	 *
	 * @return el valor del atributo instrumento
	 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="TIPO_VALOR", insertable = false, updatable = false, referencedColumnName="CLAVE_TIPO_VALOR")
	public Instrumento getInstrumento() {
		return instrumento;
	}

	/**
	 * Establece el valor del atributo instrumento
	 *
	 * @param instrumento el valor del atributo instrumento a establecer.
	 */
	public void setInstrumento(Instrumento instrumento) {
		this.instrumento = instrumento;
	}

	/**
	 * Obtiene el valor del atributo plazo
	 *
	 * @return el valor del atributo plazo
	 */
	@Column(name = "PLAZO")
	public BigInteger getPlazo() {
		return plazo;
	}
	
//  comentando TEFV
	
 
//	
//	@Column(name = "TIPO_LIQUIDACION_INICIO")
//	public String getTipoLiquidacionInicio() {
//		return tipoLiquidacionInicio;
//	}
//	
//	@Column(name = "TIPO_LIQUIDACION_VENCIMIENTO")
//	public String getTipoLiquidacionVencimiento() {
//		return tipoLiquidacionVencimiento;
//	}
//	
//	@Column(name = "IMPORTE_INTERESES")
//	public String getImporteIntereses() {
//		return importeIntereses;
//	}
	
	@Column(name = "REFERENCIA_PARTICIPANTE")
	public BigInteger getReferenciaParticipante() {
		return referenciaParticipante;
	}
	

	/**
	 * Establece el valor del atributo plazo
	 *
	 * @param plazo el valor del atributo plazo a establecer
	 */
	public void setPlazo(BigInteger plazo) {
		this.plazo = plazo;
	}

//  comentando TEFV
 
//	
//	public void setTipoLiquidacionInicio(String tipoLiquidacionInicio) {
//		this.tipoLiquidacionInicio = tipoLiquidacionInicio;
//	}
//	
//	public void setTipoLiquidacionVencimiento(String tipoLiquidacionVencimiento) {
//		this.tipoLiquidacionVencimiento = tipoLiquidacionVencimiento;
//	}
//	
//	
//	public void setImporteIntereses(String importeIntereses) {
//		this.importeIntereses = importeIntereses;
//	}
	
	public void setReferenciaParticipante(BigInteger referenciaParticipante) {
		this.referenciaParticipante = referenciaParticipante;
	}
	
	
	/**
     * Get de idInstruccion.
     * @return Long - obtiene valor del identificador.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_INSTRUCCION_SEQ_2")
    @SequenceGenerator(name = "ID_INSTRUCCION_SEQ_2", sequenceName = "ID_INSTRUCCION_SEQ_2")
    @Column(name = "ID_BITACORA_MATCH", unique = true, nullable = false, insertable = true, updatable = true, scale = 0)
    public  BigInteger getIdInstruccion() {
        return idInstruccion;
    }
    
    /**
     * Set de idInstruccion.
     * @param idInstruccion -
     *            establece valor del identificador.
     */
    public  void setIdInstruccion(final BigInteger idInstruccion) {
        this.idInstruccion = idInstruccion;
    }
    
    /**
     * Get de folioInstruccion.
     * @return String - obtiene el valor del folio de la instrucci&oacute;n
     */
    @Column(name = "FOLIO_INSTRUCCION", unique = false, nullable = false, insertable = true, updatable = true)
    public String getFolioInstruccion() {
        return folioInstruccion;
    }
    
    /**
     * Set de folioInstruccion.
     * @param folioInstruccion -
     *            establece el valor del folio de la instrucci&oacute;n
     */
    public void setFolioInstruccion(final String folioInstruccion) {
        this.folioInstruccion = folioInstruccion;
    }
    
    /**
     * Get de tipoMensaje.
     * @return String - obtiene el valor del tipo de mensaje.
     */
    @Column(name = "TIPO_MENSAJE", unique = false, nullable = false, insertable = true, updatable = true)
    public String getTipoMensaje() {
        return tipoMensaje;
    }
    
    /**
     * Set de tipoMensaje.
     * @param tipoMensaje -
     *            establece el valor del tipo de mensaje.
     */
    public void setTipoMensaje(final String tipoMensaje) {
        this.tipoMensaje = tipoMensaje;
    }
    
    /**
     * Get de tipoOperacionInstruccion.
     * @return String - obtiene el valor del tipo de operaci&oacute;n.
     */
    @Column(name = "TIPO_OPERACION", unique = false, nullable = false, insertable = true, updatable = true)
    public String getTipoOperacionInstruccion() {
        return tipoOperacionInstruccion;
    }
    
    /**
     * Set de tipoOperacionInstruccion.
     * @param tipoOperacionInstruccion -
     *            establece el valor del tipo de operaci&oacute;n.
     */
    public void setTipoOperacionInstruccion(final String tipoOperacionInstruccion) {
        this.tipoOperacionInstruccion = tipoOperacionInstruccion;
    }
    
    /**
     * Get de campos.
     * @return Map < String, CampoBean > - obtiene los valores de los campos del mensaje.
     */
    @Transient
    public Map < String, CampoBean > getCampos() {
        return campos;
    }
    
    /**
     * Set de campos.
     * @param campos -
     *            establece los valores de los campos del mensaje.
     */
    public void setCampos(final Map < String, CampoBean > campos) {
        this.campos = campos;
        /*if(campos != null && campos.get(MensajeBeanPeer.ORIGEN)!=null) {
        	this.origen = this.campos.get(MensajeBeanPeer.ORIGEN).getValor();
        }*/
    }
    
    /**
     * Get de xml.
     * @return Devuelve xml.
     */
    @Lob
    @Column(name = "MENSAJE", unique = false, nullable = true, insertable = true, updatable = true)
    public String getXml() {
        return xml;
    }
    
    /**
     * Set de xml.
     * @param xml -
     *            El xml a establecer.
     */
    public void setXml(final String xml) {
        this.xml = xml;
    }
    
    /**
     * Get de origen.
     * @return String - obtiene el valor del origen del mensaje.
     */
    @Column(name="ORIGEN", unique = false, nullable=true, insertable=true, updatable=true)
    public String getOrigen() {
        return origen;
    }
    
    /**
     * Set de origen.
     * @param origen -
     *            establece el valor del origen del mensaje.
     */
    public  void setOrigen(final String origen) {
        this.origen = origen;
    }
    
    /**
     * Get de estadoMensaje.
     * @return EstadoInstruccion - obtiene el valor del estado del mensaje.
     */
    @Transient
    public  EstadoInstruccion getEstadoMensaje() {
        return estadoMensaje;
    }
    
    /**
     * Set de estadoMensaje.
     * @param estadoMensaje -
     *            establece el valor del estado del mensaje.
     */
    public void setEstadoMensaje( EstadoInstruccion estadoMensaje) {
        this.estadoMensaje = estadoMensaje;
    }
    
    /**
     * Get de estadoMensajeString.
     * @return String - obtiene la cadena que describe el estado del mensaje.
     */
    @Column(name = "ESTADO_INSTRUCCION", unique = false, nullable = false, insertable = true, updatable = true)
    public String getEstadoMensajeString() {
        return this.estadoMensaje.toString();
    }
    
    /**
     * Set de estadoMensajeString.
     * @param estadoInstruccion -
     *            establece la cadena que describe el estado del mensaje.
     */
    public void setEstadoMensajeString(final String estadoInstruccion) {
        this.estadoMensaje = EstadoInstruccion.valueOf(estadoInstruccion);
    }
    
    /**
     * Get de folioInstruccionMatch.
     * @return String - obtiene el valor del folio de la instrucci&oacute;n con la que se hizo match.
     */
    @Column(name = "FOLIO_MATCH", unique = false, nullable = true, insertable = true, updatable = true)
    public String getFolioInstruccionMatch() {
        return folioInstruccionMatch;
    }
    
    /**
     * Set de folioInstruccionMatch.
     * @param folioInstruccionMatch -
     *            establece el valor del folio de la instrucci&oacute;n con la que se hizo match.
     */
    public void setFolioInstruccionMatch(final String folioInstruccionMatch) {
        this.folioInstruccionMatch = folioInstruccionMatch;
    }
    
    /**
     * Get matchKey.
     * @return int - obtiene el valor de la llave generada para el match.
     */
    @Transient
    public  int getMatchKey() {
        return matchKey;
    }
    
    /**
     * Set matchKey.
     * @param matchKey -
     *            establece el valor de la llave generada para el match.
     */
    public  void setMatchKey(final int matchKey) {
        this.matchKey = matchKey;
    }
    
    /**
     * Get de posibleMatchKey.
     * @return int - obtiene el valor de la llave generada para el posible match.
     */
    @Transient
    public  int getPosibleMatchKey() {
        return posibleMatchKey;
    }
    
    /**
     * Set de posibleMatchKey.
     * @param posibleMatchKey -
     *            obtiene el valor de la llave generada para el posible match.
     */
    public  void setPosibleMatchKey(final int posibleMatchKey) {
        this.posibleMatchKey = posibleMatchKey;
    }
    
    /**
     * Get de fechaHoraRecepcion.
     * @return int - obtiene el valor de la fecha en la que se recibi&oacute; el mensaje.
     */
    @Column(name = "FECHA_HORA_RECEPCION", unique = false, nullable = true, insertable = false, updatable = true)
    public Timestamp getFechaHoraRecepcion() {
        return this.fechaHoraRecepcion;
    }
    
    /**
     * Set de fechaHoraRecepcion.
     * @param fechaHoraRecepcion -
     *            establece el valor de la fecha en la que se recibi&oacute; el mensaje.
     */
    public void setFechaHoraRecepcion(final Timestamp fechaHoraRecepcion) {
        this.fechaHoraRecepcion = fechaHoraRecepcion;
    }
    
    /**
     * Is de procesado.
     * @return boolean - valor que indica si el mensaje fue procesado.
     */
    @Transient
    public  boolean isProcesado() {
        return procesado;
    }
    
    /**
     * Set de procesado.
     * @param procesado -
     *            valor para establecer si el mensaje fue procesado.
     */
    public  void setProcesado(final boolean procesado) {
        this.procesado = procesado;
    }
    
    /**
     * Get de numeroProceso.
     * @return int - obtiene el valor del n&uacute;mero de proceso en el que se compar&oacute; el mensaje.
     */
    @Column(name = "NUMERO_PROCESO", unique = false, nullable = true, insertable = false, updatable = true, scale = 0)
    public Integer getNumeroProceso() {
        return numeroProceso;
    }
    
    /**
     * Set de numeroProceso.
     * @param numeroProceso -
     *            establece el valor del n&uacute;mero de proceso en el que se compar&oacute; el mensaje.
     */
    public void setNumeroProceso(final Integer numeroProceso) {
        this.numeroProceso = numeroProceso;
    }
    
    /**
     * Get de estadoProceso.
     * @return String - obtiene el valor del estado del proceso en el que se encuentra el mensaje.
     */
    @Column(name = "ESTADO_PROCESO", unique = false, nullable = true, insertable = true, updatable = true)
    public String getEstadoProceso() {
        return estadoProceso;
    }
    
    /**
     * Set de estadoProceso.
     * @param estadoProceso -
     *            esatblece el valor del estado del proceso en el que se encuentra el mensaje.
     */
    public void setEstadoProceso(final String estadoProceso) {
        this.estadoProceso = estadoProceso;
    }
    
    /**
     * Get de fechaLiquidacion.
     * @return Date - obtiene la fecha de liquidaci&oacute;n del mensaje.
     */
    @Column(name = "FECHA_LIQUIDACION", unique = false, nullable = true, insertable = true, updatable = true)
    public Date getFechaLiquidacion() {
        return fechaLiquidacion;
    }
    
    /**
     * Set de fechaLiquidacion.
     * @param fechaLiquidacion -
     *            establece la fecha de liquidaci&oacute;n del mensaje.
     */
    public void setFechaLiquidacion(final Date fechaLiquidacion) {
        this.fechaLiquidacion = fechaLiquidacion;
    }
    /**
     * Get de version.
     * @return Integer - obtiene el valor de la variable de versionamiento de hibernate para el optimistic locking.
     */
    @Version
    @Column(name="OPTLOCK")
    public Integer getVersion() {
    	return this.version;
    }
    
    /**
     * Set de version.
     * @param version -
     *            establece el valor de la varible de versionamiento. Solamente para uso de hibernate
     */
    public void setVersion(Integer version) {
    	this.version = version;
    }
    
    /**
     * Get de expira.
     * @return Character - obtiene el valor del indicador que seala si expira o no la operacion.
     */
    @Column(name = "EXPIRA", unique = false, nullable = false, insertable = true, updatable = true)
    public Character getExpira() {
    	return expira;
    }
    /**
     * Set de expira.
     * @param expira -
     *            establece el valor del indicador que seala si expira o no la operacion.
     */
    public void setExpira(Character expira) {
    	this.expira = expira;
    }
    
    /**
     * Get de idFolioReceptor.
     * @return String - obtiene el valor del id y el folio del participante receptor.
     */
    @Column(name = "ID_FOLIO_RECEPTOR", unique = false, nullable = true, insertable = true, updatable = false)
    public String getIdFolioReceptor() {
    	return idFolioReceptor;
    }
    /**
     * Set de idFolioReceptor.
     * @param idFolioReceptor -
     *            establece el valor del id y folio del participante receptor.
     */
    public void setIdFolioReceptor(String idFolioReceptor) {
    	this.idFolioReceptor = idFolioReceptor;
    }
    
    /**
     * Get de cuentaReceptor.
     * @return String - obtiene el valor de la cuenta del participante receptor.
     */
    @Column(name = "CUENTA_RECEPTOR", unique = false, nullable = true, insertable = true, updatable = false)
    public String getCuentaReceptor() {
    	return cuentaReceptor;
    }
    
    /**
     * Set de setCuentaReceptor.
     * @param setCuentaReceptor -
     *            establece el valor de la cuenta del receptor.
     */
    public void setCuentaReceptor(String cuentaReceptor) {
    	this.cuentaReceptor = cuentaReceptor;
    }
    
    /**
     * Get de idFolioTraspasante.
     * @return String - obtiene el valor del id y el folio del participante traspasante.
     */
    @Column(name = "ID_FOLIO_TRASPASANTE", unique = false, nullable = true, insertable = true, updatable = false)
    public String getIdFolioTraspasante() {
    	return idFolioTraspasante;
    }
    
    /**
     * Set de idFolioTraspasante.
     * @param idFolioTraspasante -
     *            establece el valor del id y folio del participante Traspasante.
     */
    public void setIdFolioTraspasante(String idFolioTraspasante) {
    	this.idFolioTraspasante = idFolioTraspasante;
    }
    
    /**
     * Get de cuentaTraspasante.
     * @return String - obtiene el valor de la cuenta del participante traspasante.
     */
    @Column(name = "CUENTA_TRASPASANTE", unique = false, nullable = true, insertable = true, updatable = false)
    public String getCuentaTraspasante() {
    	return cuentaTraspasante;
    }
    
    /**
     * Set de cuentaTraspasante.
     * @param cuentaTraspasante -
     *            establece el valor de la cuenta del traspasante.
     */
    public void setCuentaTraspasante(String cuentaTraspasante) {
    	this.cuentaTraspasante = cuentaTraspasante;
    }
    
    /**
     * Get de tipoValor.
     * @return String - obtiene el valor de tipo valor.
     */
    @Column(name = "TIPO_VALOR", unique = false, nullable = true, insertable = true, updatable = false)
    public String getTipoValor() {
    	return tipoValor;
    }
    
    /**
     * Set de tipoValor.
     * @param tipoValor -
     *            establece el valor del tipo valor.
     */
    public void setTipoValor(String tipoValor) {
    	this.tipoValor = tipoValor;
    }
    
    /**
     * Get de emisora.
     * @return String - obtiene el valor de emisora.
     */
    @Column(name = "EMISORA", unique = false, nullable = true, insertable = true, updatable = false)
    public String getEmisora() {
    	return emisora;
    }
    
    /**
     * Set de emisora.
     * @param emisora -
     *            establece el valor de la emisora.
     */
    public void setEmisora(String emisora) {
    	this.emisora = emisora;
    }
    
    /**
     * Get de serie.
     * @return String - obtiene el valor de la serie.
     */
    @Column(name = "SERIE", unique = false, nullable = true, insertable = true, updatable = false)
    public String getSerie() {
    	return serie;
    }
    
    /**
     * Set de serie.
     * @param serie -
     *            establece el valor de la serie.
     */
    public void setSerie(String serie) {
    	this.serie = serie;
    }
    
    /**
     * Get de cupon.
     * @return String - obtiene el valor del cupon.
     */
    @Column(name = "CUPON", unique = false, nullable = true, insertable = true, updatable = false)
    public String getCupon() {
    	return cupon;
    }
    
    /**
     * Set de cupon.
     * @param cupon -
     *            establece el valor del cupon.
     */
    public void setCupon(String cupon) {
    	this.cupon = cupon;
    }
    
    /**
     * Get de fechaReporto.
     * @return Date - obtiene el valor de la Fecha de reporto.
     */
    @Column(name = "FECHA_REPORTO", unique = false, nullable = true, insertable = true, updatable = false)
    public Date getFechaReporto() {
    	return fechaReporto;
    }
    
    /**
     * Set de fechaReporto.
     * @param fechaReporto -
     *            establece el valor de la fecha del reporto.
     */
    public void setFechaReporto(Date fechaReporto) {
    	this.fechaReporto = fechaReporto;
    }
    
    /**
     * Get de precioTitulo.
     * @return String - obtiene el valor del precio titulo.
     */
    @Column(name = "PRECIO_TITULO", unique = false, nullable = true, insertable = true, updatable = false)
    public String getPrecioTitulo() {
    	return precioTitulo;
    }
    
    /**
     * Set de precioTitulo.
     * @param precioTitulo -
     *            establece el valor del precio titulo.
     */
    public void setPrecioTitulo(String precioTitulo) {
    	this.precioTitulo = precioTitulo;
    }
    
    /**
     * Get de tasaNegociada.
     * @return String - obtiene el valor de la tasa negociada.
     */
    @Column(name = "TASA_NEGOCIADA", unique = false, nullable = true, insertable = true, updatable = false)
    public String getTasaNegociada() {
    	return tasaNegociada;
    }
    
    /**
     * Set de tasaNegociada.
     * @param tasaNegociada -
     *            establece el valor de la tasa negociada.
     */
    public void setTasaNegociada(String tasaNegociada) {
    	this.tasaNegociada = tasaNegociada;
    }
    
    /**
     * Get de tasaReferencia.
     * @return String - obtiene el valor de la tasa de referencia.
     */
    @Column(name = "TASA_REFERENCIA", unique = false, nullable = true, insertable = true, updatable = false)
    public String getTasaReferencia() {
    	return tasaReferencia;
    }
    
    /**
     * Set de tasaReferencia.
     * @param tasaReferencia -
     *            establece el valor de la tasa de referencia.
     */
    public void setTasaReferencia(String tasaReferencia) {
    	this.tasaReferencia = tasaReferencia;
    }
    
    /**
     * Get de cantidadTitulos.
     * @return String - obtiene el valor de la cantidad de titulos.
     */
    @Column(name = "CANTIDAD_TITULOS", unique = false, nullable = true, insertable = true, updatable = false)
    public String getCantidadTitulos() {
    	return cantidadTitulos;
    }
    
    /**
     * Set de cantidadTitulos.
     * @param cantidadTitulos -
     *            establece el valor de la cantidad de titulos.
     */
    public void setCantidadTitulos(String cantidadTitulos) {
    	this.cantidadTitulos = cantidadTitulos;
    }
    
    /**
     * Get de importe.
     * @return String - obtiene el valor del importe.
     */
    @Column(name = "IMPORTE", unique = false, nullable = true, insertable = true, updatable = false)
    public String getImporte() {
    	return importe;
    }
    
    /**
     * Set de importe.
     * @param importe -
     *            establece el valor del importe.
     */
    public void setImporte(String importe) {
    	this.importe = importe;
    }
    
    /**
     * Get de folioControl.
     * @return String - obtiene el valor del folio control.
     */
    @Column(name = "FOLIO_CONTROL", unique = false, nullable = true, insertable = true, updatable = false)
    public String getFolioControl() {
    	return folioControl;
    }
    
    /**
     * Set de folioControl.
     * @param folioControl -
     *            establece el valor del folio control.
     */
    public void setFolioControl(String folioControl) {
    	this.folioControl = folioControl;
    }
    
    /**
     * Get de fechaHoraRegistro.
     * @return String - obtiene el valor de la fecha hora regitrso de la operacion.
     */
    @Column(name = "FECHA_HORA_REGISTRO", unique = false, nullable = true, insertable = true, updatable = false)
    public Timestamp getFechaHoraRegistro() {
    	return fechaHoraRegistro;
    }

    /**
     * Set de fechaHoraRegistro.
     * @param fechaHoraRegistro -
     *            establece el valor de la fecha/hora de registro.
     */
    public void setFechaHoraRegistro(Timestamp fechaHoraRegistro) {
    	this.fechaHoraRegistro = fechaHoraRegistro;
    }
    
    
    /**
     * Get de idFolioParticipanteVO.
     * @return String - obtiene el valor id y folio del participante que envio la operacion
     */
    @Transient
    public String getIdFolioParticipanteVO()
    {
        return idFolioParticipanteVO;
    }

    /**
     * Set de idFolioParticipanteVO.
     * @param idFolioParticipanteVO -
     *            establece el id y folio del participante que envio la operacion.
     */
    public void setIdFolioParticipanteVO(String idFolioParticipanteVO)
    {
        this.idFolioParticipanteVO = idFolioParticipanteVO;
    }

    /** 
     * Muestra el contenido del mensaje en 
     * XML.
     */
    @Override
    public String toString() {
    	return this.xml;
    }
    
    public Object clone(){
        Object obj=null;
        try{
            obj=super.clone();
        }catch(CloneNotSupportedException ex){
            throw new InternalError(ex.toString());
        }
        return obj;
    }

	/**
	 * @return the boveda
	 */
    @Column(name = "BOVEDA_VALORES" )
	public String getBoveda() {
		return boveda;
	}

	/**
	 * @return the bovedaEfectivo
	 */
    @Column(name = "BOVEDA_EFECTIVO" )
	public String getBovedaEfectivo() {
		return bovedaEfectivo;
	}

	/**
	 * @return the divisa
	 */
    @Column(name = "DIVISA" )
	public String getDivisa() {
		return divisa;
	}

	/**
	 * @param boveda the boveda to set
	 */
	public void setBoveda(String boveda) {
		this.boveda = boveda;
	}

	/**
	 * @param bovedaEfectivo the bovedaEfectivo to set
	 */
	public void setBovedaEfectivo(String bovedaEfectivo) {
		this.bovedaEfectivo = bovedaEfectivo;
	}

	/**
	 * @param divisa the divisa to set
	 */
	public void setDivisa(String divisa) {
		this.divisa = divisa;
	}
	@Column(name ="REFERENCIA_PAQUETE")
	public String getReferenciaPaquete() {
		return referenciaPaquete;
	}

	public void setReferenciaPaquete(String referenciaPaquete) {
		this.referenciaPaquete = referenciaPaquete;
	}

	@Column(name ="TOTAL_OPERACIONES_PAQUETE")
	public String getTotalOperacionesPaquete() {
		return totalOperacionesPaquete;
	}

	public void setTotalOperacionesPaquete(String totalOperacionesPaquete) {
		this.totalOperacionesPaquete = totalOperacionesPaquete;
	}

	@Column(name ="NUMERO_OPERACION_PAQUETE")
	public String getNumeroOperacionPaquete() {
		return numeroOperacionPaquete;
	}

	public void setNumeroOperacionPaquete(String numeroOperacionPaquete) {
		this.numeroOperacionPaquete = numeroOperacionPaquete;
	}

	@Column(name ="TOTAL_TITULOS_PAQUETE")
	public String getTotalTitulosPaquete() {
		return totalTitulosPaquete;
	}

	public void setTotalTitulosPaquete(String totalTitulosPaquete) {
		this.totalTitulosPaquete = totalTitulosPaquete;
	}
	
	@Column(name ="TOTAL_IMPORTE_PAQUETE")
	public String getTotalImportePaquete() {
		return totalImportePaquete;
	}

	public void setTotalImportePaquete(String totalImportePaquete) {
		this.totalImportePaquete = totalImportePaquete;
	}

}