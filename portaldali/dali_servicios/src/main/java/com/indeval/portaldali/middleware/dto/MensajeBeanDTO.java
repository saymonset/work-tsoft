/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.dto;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Map;

/**
 * Clase que realiza el mapeo con la tabla T_REGISTRO_INSTRUCCIONES_MATCH.
 *
 * @author Fernando Vazquez Ulloa
 */
public class MensajeBeanDTO implements Serializable {

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
    private Long idInstruccion;

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
    private Map < String, CampoBeanDTO > campos;

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
    private InstrumentoDTO instrumento;
    
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
    /*Inicia campos nuevos para sincronizacion con efectivo.*/
    private String divisa;
    
    private String boveda;
    
    private BigInteger referenciaParticipante;
    
    private String importeIntereses;
    
    private String tipoLiquidacionInicio;
    
    private String tipoLiquidacionVencimiento;
    
    
    //---------------------
    
    
    
    public MensajeBeanDTO() {
    	this.expira = NO;
    }
    
    /**
	 * Obtiene el valor del atributo instrumento
	 *
	 * @return el valor del atributo instrumento
	 */
    
	public InstrumentoDTO getInstrumento() {
		return instrumento;
	}

	/**
	 * Establece el valor del atributo instrumento
	 *
	 * @param instrumento el valor del atributo instrumento a establecer.
	 */
	public void setInstrumento(InstrumentoDTO instrumento) {
		this.instrumento = instrumento;
	}

	/**
	 * Obtiene el valor del atributo plazo
	 *
	 * @return el valor del atributo plazo
	 */
	
	public BigInteger getPlazo() {
		return plazo;
	}
	
	
	public String getDivisa() {
		return divisa;
	}
	
	
	public String getBoveda() {
		return boveda;
	}
	
	
	public String getTipoLiquidacionInicio() {
		return tipoLiquidacionInicio;
	}
	
	
	public String getTipoLiquidacionVencimiento() {
		return tipoLiquidacionVencimiento;
	}
	
	
	
	
	public String getImporteIntereses() {
		return importeIntereses;
	}
	
	
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

	public void setDivisa(String divisa) {
		this.divisa = divisa;
	}
	
	public void setBoveda(String boveda) {
		this.boveda = boveda;
	}
	
	public void setTipoLiquidacionInicio(String tipoLiquidacionInicio) {
		this.tipoLiquidacionInicio = tipoLiquidacionInicio;
	}
	
	public void setTipoLiquidacionVencimiento(String tipoLiquidacionVencimiento) {
		this.tipoLiquidacionVencimiento = tipoLiquidacionVencimiento;
	}
	
	
	public void setImporteIntereses(String importeIntereses) {
		this.importeIntereses = importeIntereses;
	}
	
	public void setReferenciaParticipante(BigInteger referenciaParticipante) {
		this.referenciaParticipante = referenciaParticipante;
	}
	
	
	/**
     * Get de idInstruccion.
     * @return Long - obtiene valor del identificador.
     */
    
    public final Long getIdInstruccion() {
        return idInstruccion;
    }
    
    /**
     * Set de idInstruccion.
     * @param idInstruccion -
     *            establece valor del identificador.
     */
    public final void setIdInstruccion(final Long idInstruccion) {
        this.idInstruccion = idInstruccion;
    }
    
    /**
     * Get de folioInstruccion.
     * @return String - obtiene el valor del folio de la instrucci&oacute;n
     */

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

    public final Map < String, CampoBeanDTO > getCampos() {
        return campos;
    }
    
    /**
     * Set de campos.
     * @param campos -
     *            establece los valores de los campos del mensaje.
     */
    public final void setCampos(final Map < String, CampoBeanDTO > campos) {
        this.campos = campos;
        /*if(campos != null && campos.get(MensajeBeanPeer.ORIGEN)!=null) {
        	this.origen = this.campos.get(MensajeBeanPeer.ORIGEN).getValor();
        }*/
    }
    
    /**
     * Get de xml.
     * @return Devuelve xml.
     */

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

    public final String getOrigen() {
        return origen;
    }
    
    /**
     * Set de origen.
     * @param origen -
     *            establece el valor del origen del mensaje.
     */
    public final void setOrigen(final String origen) {
        this.origen = origen;
    }
    
    /**
     * Get de estadoMensaje.
     * @return EstadoInstruccion - obtiene el valor del estado del mensaje.
     */

    public final EstadoInstruccion getEstadoMensaje() {
        return estadoMensaje;
    }
    
    /**
     * Set de estadoMensaje.
     * @param estadoMensaje -
     *            establece el valor del estado del mensaje.
     */
    public final void setEstadoMensaje(final EstadoInstruccion estadoMensaje) {
        this.estadoMensaje = estadoMensaje;
    }
    
    /**
     * Get de estadoMensajeString.
     * @return String - obtiene la cadena que describe el estado del mensaje.
     */

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

    public final int getMatchKey() {
        return matchKey;
    }
    
    /**
     * Set matchKey.
     * @param matchKey -
     *            establece el valor de la llave generada para el match.
     */
    public final void setMatchKey(final int matchKey) {
        this.matchKey = matchKey;
    }
    
    /**
     * Get de posibleMatchKey.
     * @return int - obtiene el valor de la llave generada para el posible match.
     */

    public final int getPosibleMatchKey() {
        return posibleMatchKey;
    }
    
    /**
     * Set de posibleMatchKey.
     * @param posibleMatchKey -
     *            obtiene el valor de la llave generada para el posible match.
     */
    public final void setPosibleMatchKey(final int posibleMatchKey) {
        this.posibleMatchKey = posibleMatchKey;
    }
    
    /**
     * Get de fechaHoraRecepcion.
     * @return int - obtiene el valor de la fecha en la que se recibi&oacute; el mensaje.
     */

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

    public final boolean isProcesado() {
        return procesado;
    }
    
    /**
     * Set de procesado.
     * @param procesado -
     *            valor para establecer si el mensaje fue procesado.
     */
    public final void setProcesado(final boolean procesado) {
        this.procesado = procesado;
    }
    
    /**
     * Get de numeroProceso.
     * @return int - obtiene el valor del n&uacute;mero de proceso en el que se compar&oacute; el mensaje.
     */

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
}