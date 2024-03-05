/**
 * CMMV - Portal Dali
 * Copyrigth (c) 2016 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.model.util;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;


/**
 * Clase padre de la cual heredaran los modelos para realizar las consultas
 * de operaciones y match.
 * 
 * @author Pablo Balderas
 */
public class ConsultaOperacionesMatch implements Serializable {

	/** Id para la serializacion */
	private static final long serialVersionUID = -4227076504859602422L;

    private Long idInstruccionOperacion;

    private Long idBitacoraMatch;

	private Long folioControl;
	
	private boolean porPaquete;
	
	private String referenciaPaquete;
	
	private String folioOrigen;
	
	private String nombreCortoTipoInstruccion;
    private String instruccion;
    private String descTipoInstruccion;
	
	private String origen;
	  
	private String tv;
   
	private String emisora;

	private String serie;
	
	private String cupon;
	
	private String isin;
	
	private String bovedaValores;
	
	private String claveEstadoInstruccionCat;
    
    private String descEstadoInstruccionCat;

    private Long idInstitucionTraspasante;
	
	private String idFolioInstitucionTraspasante;
	
	private String cuentaTraspasante;

    private Long idInstitucionReceptora;
	
	private String idFolioInstitucionReceptora;
	
	private String cuentaReceptora;
	
	private Double precioTitulo;
	
	private String fechaReporto;
	
	private String claveTipoMensajeCat;
    
    private String descTipoMensajeCat;
	
	private Date fechaConcertacion;
	
	private Date fechaLiquidacion;
	
	private Date fechaHoraCierreOperTra;
	
	private Date fechaHoraCierreOperRec;
	
	private Date fechaHoraEncolamientoTra;
	
	private Date fechaHoraEncolamientoRec;
	
	private Long cantidadTitulos;
	
	private Double importe;
	
	private String bovedaEfectivo;
	
	private Double tasaNegociada;
	
	private Long plazoReporto;
	
	private String claveDivisa;
    
    private Integer idError;
	
	private String claveError;
    
    private String descError;

    private Long idMercado;
    
    private String cveTipoInstitucionTraspasante;
    
    private String cveTipoInstitucionReceptora;

    private String folioUsuario;

    private String nombreCortoInstitTraspasante;

    private String nombreCortoInstitReceptora;

    private Long idInstitucionBancoTrabajo;

    private String cveTipoInstitBancoTrabajo;

    private String folioInstitBancoTrabajo;

    private String nombreCortoInstBancoTrab;
    
    private String cuentaBancoTrabajo;

    private String folioInstruccionReceptora;

    private String folioInstruccionTraspasante;
    
    private boolean tieneParcialidades;

    private Long idEstadoInstruccionCat;

    private String totalOperacionesPaquete;

    private String numeroOperacionPaquete;

    private String totalTitulosPaquete;

    private String totalImportePaquete;
    
    private boolean conMiscelaneaFiscal;
    
    private boolean puedeConfirmar;

    private boolean puedeCancelar = false;

    private String nombreCuentaTraspasante;

    private String nombreCuentaReceptora;
    
    private Double interesesGenerados;

    private String mensajeXml;
    
    private Date fechaVencimiento;
    
    private Long idVencimientoAnticipado;
    
    private Long idInstitucionSolicitud;
    private Long idInstitucionAutoriza;
    
    private boolean posibleVencerAnticipadamente;
    
	public Long getIdInstruccionOperacion() {
        return idInstruccionOperacion;
    }

    public void setIdInstruccionOperacion(Long idInstruccionOperacion) {
        this.idInstruccionOperacion = idInstruccionOperacion;
    }

    public Long getIdBitacoraMatch() {
        return idBitacoraMatch;
    }

    public void setIdBitacoraMatch(Long idBitacoraMatch) {
        this.idBitacoraMatch = idBitacoraMatch;
    }

    /**
	 * Método para obtener el atributo folioControl
	 * @return El atributo folioControl
	 */
	public Long getFolioControl() {
		return folioControl;
	}

	/**
	 * Método para establecer el atributo folioControl
	 * @param folioControl El valor del atributo folioControl a establecer.
	 */
	public void setFolioControl(Long folioControl) {
		this.folioControl = folioControl;
	}

	/**
	 * Método para obtener el atributo porPaquete
	 * @return El atributo porPaquete
	 */
	public boolean isPorPaquete() {
		return porPaquete;
	}

	/**
	 * Método para establecer el atributo porPaquete
	 * @param porPaquete El valor del atributo porPaquete a establecer.
	 */
	public void setPorPaquete(boolean porPaquete) {
		this.porPaquete = porPaquete;
	}

	/**
	 * Método para obtener el atributo referenciaPaquete
	 * @return El atributo referenciaPaquete
	 */
	public String getReferenciaPaquete() {
		return referenciaPaquete;
	}

	/**
	 * Método para establecer el atributo referenciaPaquete
	 * @param referenciaPaquete El valor del atributo referenciaPaquete a establecer.
	 */
	public void setReferenciaPaquete(String referenciaPaquete) {
		this.referenciaPaquete = referenciaPaquete;
	}

	/**
	 * Método para obtener el atributo folioOrigen
	 * @return El atributo folioOrigen
	 */
	public String getFolioOrigen() {
		return folioOrigen;
	}

	/**
	 * Método para establecer el atributo folioOrigen
	 * @param folioOrigen El valor del atributo folioOrigen a establecer.
	 */
	public void setFolioOrigen(String folioOrigen) {
		this.folioOrigen = folioOrigen;
	}

	/**
	 * Método para obtener el atributo nombreCortoTipoInstruccion
	 * @return El atributo nombreCortoTipoInstruccion
	 */
	public String getNombreCortoTipoInstruccion() {
		return nombreCortoTipoInstruccion;
	}

	/**
	 * Método para establecer el atributo nombreCortoTipoInstruccion
	 * @param nombreCortoTipoInstruccion El valor del atributo nombreCortoTipoInstruccion a establecer.
	 */
	public void setNombreCortoTipoInstruccion(String nombreCortoTipoInstruccion) {
		this.nombreCortoTipoInstruccion = nombreCortoTipoInstruccion;
	}

	public String getDescTipoInstruccion() {
        return descTipoInstruccion;
    }

    public void setDescTipoInstruccion(String descTipoInstruccion) {
        this.descTipoInstruccion = descTipoInstruccion;
    }

    /**
	 * Método para obtener el atributo origen
	 * @return El atributo origen
	 */
	public String getOrigen() {
		return origen;
	}

	/**
	 * Método para establecer el atributo origen
	 * @param origen El valor del atributo origen a establecer.
	 */
	public void setOrigen(String origen) {
		this.origen = origen;
	}

	/**
	 * Método para obtener el atributo tv
	 * @return El atributo tv
	 */
	public String getTv() {
		return tv;
	}

	/**
	 * Método para establecer el atributo tv
	 * @param tv El valor del atributo tv a establecer.
	 */
	public void setTv(String tv) {
		this.tv = tv;
	}

	/**
	 * Método para obtener el atributo emisora
	 * @return El atributo emisora
	 */
	public String getEmisora() {
		return emisora;
	}

	/**
	 * Método para establecer el atributo emisora
	 * @param emisora El valor del atributo emisora a establecer.
	 */
	public void setEmisora(String emisora) {
		this.emisora = emisora;
	}

	/**
	 * Método para obtener el atributo serie
	 * @return El atributo serie
	 */
	public String getSerie() {
		return serie;
	}

	/**
	 * Método para establecer el atributo serie
	 * @param serie El valor del atributo serie a establecer.
	 */
	public void setSerie(String serie) {
		this.serie = serie;
	}

	/**
	 * Método para obtener el atributo cupon
	 * @return El atributo cupon
	 */
	public String getCupon() {
		return cupon;
	}

	/**
	 * Método para establecer el atributo cupon
	 * @param cupon El valor del atributo cupon a establecer.
	 */
	public void setCupon(String cupon) {
		this.cupon = cupon;
	}

	/**
	 * Método para obtener el atributo isin
	 * @return El atributo isin
	 */
	public String getIsin() {
		return isin;
	}

	/**
	 * Método para establecer el atributo isin
	 * @param isin El valor del atributo isin a establecer.
	 */
	public void setIsin(String isin) {
		this.isin = isin;
	}

	/**
	 * Método para obtener el atributo bovedaValores
	 * @return El atributo bovedaValores
	 */
	public String getBovedaValores() {
		return bovedaValores;
	}

	/**
	 * Método para establecer el atributo bovedaValores
	 * @param bovedaValores El valor del atributo bovedaValores a establecer.
	 */
	public void setBovedaValores(String bovedaValores) {
		this.bovedaValores = bovedaValores;
	}

	/**
	 * Método para obtener el atributo claveEstadoInstruccionCat
	 * @return El atributo claveEstadoInstruccionCat
	 */
	public String getClaveEstadoInstruccionCat() {
		return claveEstadoInstruccionCat;
	}

	/**
	 * Método para establecer el atributo claveEstadoInstruccionCat
	 * @param claveEstadoInstruccionCat El valor del atributo claveEstadoInstruccionCat a establecer.
	 */
	public void setClaveEstadoInstruccionCat(String claveEstadoInstruccionCat) {
		this.claveEstadoInstruccionCat = claveEstadoInstruccionCat;
	}

	public String getDescEstadoInstruccionCat() {
        return descEstadoInstruccionCat;
    }

    public void setDescEstadoInstruccionCat(String descEstadoInstruccionCat) {
        this.descEstadoInstruccionCat = descEstadoInstruccionCat;
    }

    public Long getIdInstitucionTraspasante() {
        return idInstitucionTraspasante;
    }

    public void setIdInstitucionTraspasante(Long idInstitucionTraspasante) {
        this.idInstitucionTraspasante = idInstitucionTraspasante;
    }

    /**
	 * Método para obtener el atributo idFolioInstitucionTraspasante
	 * @return El atributo idFolioInstitucionTraspasante
	 */
	public String getIdFolioInstitucionTraspasante() {
		return idFolioInstitucionTraspasante;
	}
	
    /**
	 * Método para obtener el atributo idFolioInstitucionTraspasante
	 * @return El atributo idFolioInstitucionTraspasante
	 */
	public String getIdFolioInstitucionTraspasanteConEspacio() {
		String resultado = null;
		if(StringUtils.isNotBlank(idFolioInstitucionTraspasante)) {
			resultado = 
				idFolioInstitucionTraspasante.substring(0, 2) +
				" " + 
				idFolioInstitucionTraspasante.substring(2, 5);
		}
		return resultado;
	}

	/**
	 * Método para establecer el atributo idFolioInstitucionTraspasante
	 * @param idFolioInstitucionTraspasante El valor del atributo idFolioInstitucionTraspasante a establecer.
	 */
	public void setIdFolioInstitucionTraspasante(
			String idFolioInstitucionTraspasante) {
		this.idFolioInstitucionTraspasante = idFolioInstitucionTraspasante;
	}

	/**
	 * Método para obtener el atributo cuentaTraspasante
	 * @return El atributo cuentaTraspasante
	 */
	public String getCuentaTraspasante() {
		return cuentaTraspasante;
	}

	/**
	 * Método para establecer el atributo cuentaTraspasante
	 * @param cuentaTraspasante El valor del atributo cuentaTraspasante a establecer.
	 */
	public void setCuentaTraspasante(String cuentaTraspasante) {
		this.cuentaTraspasante = cuentaTraspasante;
	}

	public Long getIdInstitucionReceptora() {
        return idInstitucionReceptora;
    }

    public void setIdInstitucionReceptora(Long idInstitucionReceptora) {
        this.idInstitucionReceptora = idInstitucionReceptora;
    }

    /**
	 * Método para obtener el atributo idFolioInstitucionReceptora
	 * @return El atributo idFolioInstitucionReceptora
	 */
	public String getIdFolioInstitucionReceptora() {
		return idFolioInstitucionReceptora;
	}

    /**
	 * Método para obtener el atributo idFolioInstitucionReceptora
	 * @return El atributo idFolioInstitucionReceptora
	 */
	public String getIdFolioInstitucionReceptoraConEspacio() {
		String resultado = null;
		if(StringUtils.isNotBlank(idFolioInstitucionReceptora)) {
			resultado = 
				idFolioInstitucionReceptora.substring(0, 2) +
				" " + 
				idFolioInstitucionReceptora.substring(2, 5);
		}
		return resultado;
	}
	
	/**
	 * Método para establecer el atributo idFolioInstitucionReceptora
	 * @param idFolioInstitucionReceptora El valor del atributo idFolioInstitucionReceptora a establecer.
	 */
	public void setIdFolioInstitucionReceptora(String idFolioInstitucionReceptora) {
		this.idFolioInstitucionReceptora = idFolioInstitucionReceptora;
	}

	/**
	 * Método para obtener el atributo cuentaReceptora
	 * @return El atributo cuentaReceptora
	 */
	public String getCuentaReceptora() {
		return cuentaReceptora;
	}

	/**
	 * Método para establecer el atributo cuentaReceptora
	 * @param cuentaReceptora El valor del atributo cuentaReceptora a establecer.
	 */
	public void setCuentaReceptora(String cuentaReceptora) {
		this.cuentaReceptora = cuentaReceptora;
	}

	/**
	 * Método para obtener el atributo precioTitulo
	 * @return El atributo precioTitulo
	 */
	public Double getPrecioTitulo() {
		return precioTitulo;
	}

	/**
	 * Método para establecer el atributo precioTitulo
	 * @param precioTitulo El valor del atributo precioTitulo a establecer.
	 */
	public void setPrecioTitulo(Double precioTitulo) {
		this.precioTitulo = precioTitulo != null ? precioTitulo : 0.0;
	}

	/**
	 * Método para obtener el atributo fechaReporto
	 * @return El atributo fechaReporto
	 */
	public String getFechaReporto() {
		return fechaReporto;
	}

	/**
	 * Método para establecer el atributo fechaReporto
	 * @param fechaReporto El valor del atributo fechaReporto a establecer.
	 */
	public void setFechaReporto(String fechaReporto) {
		this.fechaReporto = fechaReporto;
	}

	/**
	 * Método para obtener el atributo claveTipoMensajeCat
	 * @return El atributo claveTipoMensajeCat
	 */
	public String getClaveTipoMensajeCat() {
		return claveTipoMensajeCat;
	}

	/**
	 * Método para establecer el atributo claveTipoMensajeCat
	 * @param claveTipoMensajeCat El valor del atributo claveTipoMensajeCat a establecer.
	 */
	public void setClaveTipoMensajeCat(String claveTipoMensajeCat) {
		this.claveTipoMensajeCat = claveTipoMensajeCat;
	}

	public String getDescTipoMensajeCat() {
        return descTipoMensajeCat;
    }

    public void setDescTipoMensajeCat(String descTipoMensajeCat) {
        this.descTipoMensajeCat = descTipoMensajeCat;
    }

    /**
	 * Método para obtener el atributo fechaConcertacion
	 * @return El atributo fechaConcertacion
	 */
	public Date getFechaConcertacion() {
		return fechaConcertacion;
	}

	/**
	 * Método para establecer el atributo fechaConcertacion
	 * @param fechaConcertacion El valor del atributo fechaConcertacion a establecer.
	 */
	public void setFechaConcertacion(Date fechaConcertacion) {
		this.fechaConcertacion = fechaConcertacion;
	}

	/**
	 * Método para obtener el atributo fechaLiquidacion
	 * @return El atributo fechaLiquidacion
	 */
	public Date getFechaLiquidacion() {
		return fechaLiquidacion;
	}

	/**
	 * Método para establecer el atributo fechaLiquidacion
	 * @param fechaLiquidacion El valor del atributo fechaLiquidacion a establecer.
	 */
	public void setFechaLiquidacion(Date fechaLiquidacion) {
		this.fechaLiquidacion = fechaLiquidacion;
	}

	public Date getFechaHoraCierreOperTra() {
		return fechaHoraCierreOperTra;
	}

	public void setFechaHoraCierreOperTra(Date fechaHoraCierreOperTra) {
		this.fechaHoraCierreOperTra = fechaHoraCierreOperTra;
	}

	public Date getFechaHoraCierreOperRec() {
		return fechaHoraCierreOperRec;
	}

	public void setFechaHoraCierreOperRec(Date fechaHoraCierreOperRec) {
		this.fechaHoraCierreOperRec = fechaHoraCierreOperRec;
	}

	public Date getFechaHoraEncolamientoTra() {
		return fechaHoraEncolamientoTra;
	}

	public void setFechaHoraEncolamientoTra(Date fechaHoraEncolamientoTra) {
		this.fechaHoraEncolamientoTra = fechaHoraEncolamientoTra;
	}

	public Date getFechaHoraEncolamientoRec() {
		return fechaHoraEncolamientoRec;
	}

	public void setFechaHoraEncolamientoRec(Date fechaHoraEncolamientoRec) {
		this.fechaHoraEncolamientoRec = fechaHoraEncolamientoRec;
	}

	/**
	 * Método para obtener el atributo cantidadTitulos
	 * @return El atributo cantidadTitulos
	 */
	public Long getCantidadTitulos() {
		return cantidadTitulos;
	}

	/**
	 * Método para establecer el atributo cantidadTitulos
	 * @param cantidadTitulos El valor del atributo cantidadTitulos a establecer.
	 */
	public void setCantidadTitulos(Long cantidadTitulos) {
		this.cantidadTitulos = cantidadTitulos;
	}

	/**
	 * Método para obtener el atributo importe
	 * @return El atributo importe
	 */
	public Double getImporte() {
		return importe;
	}

	/**
	 * Método para establecer el atributo importe
	 * @param importe El valor del atributo importe a establecer.
	 */
	public void setImporte(Double importe) {
		this.importe = importe;
	}

	/**
	 * Método para obtener el atributo bovedaEfectivo
	 * @return El atributo bovedaEfectivo
	 */
	public String getBovedaEfectivo() {
		return bovedaEfectivo;
	}

	/**
	 * Método para establecer el atributo bovedaEfectivo
	 * @param bovedaEfectivo El valor del atributo bovedaEfectivo a establecer.
	 */
	public void setBovedaEfectivo(String bovedaEfectivo) {
		this.bovedaEfectivo = bovedaEfectivo;
	}

	/**
	 * Método para obtener el atributo tasaNegociada
	 * @return El atributo tasaNegociada
	 */
	public Double getTasaNegociada() {
		return tasaNegociada;
	}

	/**
	 * Método para establecer el atributo tasaNegociada
	 * @param tasaNegociada El valor del atributo tasaNegociada a establecer.
	 */
	public void setTasaNegociada(Double tasaNegociada) {
		this.tasaNegociada = tasaNegociada;
	}

	/**
	 * Método para obtener el atributo plazoReporto
	 * @return El atributo plazoReporto
	 */
	public Long getPlazoReporto() {
		if (getFechaReportoDate() != null && fechaLiquidacion != null) {
			Calendar inicio = Calendar.getInstance();
			Calendar fin = Calendar.getInstance();
			inicio.setTime(this.fechaLiquidacion);
			fin.setTime(getFechaReportoDate());
			// Se toman las fechas sin hora
			GregorianCalendar date1 = new GregorianCalendar(inicio
					.get(Calendar.YEAR), inicio.get(Calendar.MONTH), inicio
					.get(Calendar.DAY_OF_MONTH));
			GregorianCalendar date2 = new GregorianCalendar(fin
					.get(Calendar.YEAR), fin.get(Calendar.MONTH), fin
					.get(Calendar.DAY_OF_MONTH));
			// Se restan entre ellas en milisegundos
			long difms = date2.getTimeInMillis() - date1.getTimeInMillis();
			// Se le suma un cuarto de un día para evitar que el redondeo le
			// quite un día
			difms = difms
					+ (com.indeval.portaldali.middleware.services.util.util.Constantes.MILLISECONDXDAY / 4);
			// Se divide entre los milisegundos de un dia
			long difd = difms
					/ com.indeval.portaldali.middleware.services.util.util.Constantes.MILLISECONDXDAY;
			this.plazoReporto = difd;
		}
		return plazoReporto;
	}

	/**
	 * Método para establecer el atributo plazoReporto
	 * @param plazoReporto El valor del atributo plazoReporto a establecer.
	 */
	public void setPlazoReporto(Long plazoReporto) {
		this.plazoReporto = plazoReporto;
	}

	/**
	 * Método para obtener el atributo claveDivisa
	 * @return El atributo claveDivisa
	 */
	public String getClaveDivisa() {
		return claveDivisa;
	}

	/**
	 * Método para establecer el atributo claveDivisa
	 * @param claveDivisa El valor del atributo claveDivisa a establecer.
	 */
	public void setClaveDivisa(String claveDivisa) {
		this.claveDivisa = claveDivisa;
	}

	public Integer getIdError() {
        return idError;
    }

    public void setIdError(Integer idError) {
        this.idError = idError;
    }

    /**
	 * Método para obtener el atributo claveError
	 * @return El atributo claveError
	 */
	public String getClaveError() {
		return claveError;
	}

	/**
	 * Método para establecer el atributo claveError
	 * @param claveError El valor del atributo claveError a establecer.
	 */
	public void setClaveError(String claveError) {
		this.claveError = claveError;
	}

    public String getDescError() {
        return descError;
    }

    public void setDescError(String descError) {
        this.descError = descError;
    }

    /**
     * Metodo para obtener el atributo idMercado
     * @return El atributo idMercado
     */
    public Long getIdMercado() {
        return this.idMercado;
    }

    /**
     * Metodo para establecer el atributo idMercado
     * @param idMercado El valor del atributo idMercado a establecer.
     */
    public void setIdMercado(Long idMercado) {
        this.idMercado = idMercado;
    }

    public String getCveTipoInstitucionTraspasante() {
        return cveTipoInstitucionTraspasante;
    }

    public void setCveTipoInstitucionTraspasante(String cveTipoInstitucionTraspasante) {
        this.cveTipoInstitucionTraspasante = cveTipoInstitucionTraspasante;
    }

    public String getCveTipoInstitucionReceptora() {
        return cveTipoInstitucionReceptora;
    }

    public void setCveTipoInstitucionReceptora(String cveTipoInstitucionReceptora) {
        this.cveTipoInstitucionReceptora = cveTipoInstitucionReceptora;
    }

    public String getFolioUsuario() {
        return folioUsuario;
    }

    public void setFolioUsuario(String folioUsuario) {
        this.folioUsuario = folioUsuario;
    }

    public String getNombreCortoInstitTraspasante() {
        return nombreCortoInstitTraspasante;
    }

    public void setNombreCortoInstitTraspasante(String nombreCortoInstitTraspasante) {
        this.nombreCortoInstitTraspasante = nombreCortoInstitTraspasante;
    }

    public String getNombreCortoInstitReceptora() {
        return nombreCortoInstitReceptora;
    }

    public void setNombreCortoInstitReceptora(String nombreCortoInstitReceptora) {
        this.nombreCortoInstitReceptora = nombreCortoInstitReceptora;
    }

    public Long getIdInstitucionBancoTrabajo() {
        return idInstitucionBancoTrabajo;
    }

    public void setIdInstitucionBancoTrabajo(Long idInstitucionBancoTrabajo) {
        this.idInstitucionBancoTrabajo = idInstitucionBancoTrabajo;
    }

    public String getCveTipoInstitBancoTrabajo() {
        return cveTipoInstitBancoTrabajo;
    }

    public void setCveTipoInstitBancoTrabajo(String cveTipoInstitBancoTrabajo) {
        this.cveTipoInstitBancoTrabajo = cveTipoInstitBancoTrabajo;
    }

    public String getFolioInstitBancoTrabajo() {
        return folioInstitBancoTrabajo;
    }

    public void setFolioInstitBancoTrabajo(String folioInstitBancoTrabajo) {
        this.folioInstitBancoTrabajo = folioInstitBancoTrabajo;
    }

    public String getNombreCortoInstBancoTrab() {
        return nombreCortoInstBancoTrab;
    }

    public void setNombreCortoInstBancoTrab(String nombreCortoInstBancoTrab) {
        this.nombreCortoInstBancoTrab = nombreCortoInstBancoTrab;
    }

    public String getCuentaBancoTrabajo() {
        return cuentaBancoTrabajo;
    }

    public void setCuentaBancoTrabajo(String cuentaBancoTrabajo) {
        this.cuentaBancoTrabajo = cuentaBancoTrabajo;
    }

    public String getFolioInstruccionReceptora() {
        return folioInstruccionReceptora;
    }

    public void setFolioInstruccionReceptora(String folioInstruccionReceptora) {
        this.folioInstruccionReceptora = folioInstruccionReceptora;
    }

    public String getFolioInstruccionTraspasante() {
        return folioInstruccionTraspasante;
    }

    public void setFolioInstruccionTraspasante(String folioInstruccionTraspasante) {
        this.folioInstruccionTraspasante = folioInstruccionTraspasante;
    }

    public boolean isTieneParcialidades() {
        return tieneParcialidades;
    }

    public void setTieneParcialidades(boolean tieneParcialidades) {
        this.tieneParcialidades = tieneParcialidades;
    }

    public Long getIdEstadoInstruccionCat() {
        return idEstadoInstruccionCat;
    }

    public void setIdEstadoInstruccionCat(Long idEstadoInstruccionCat) {
        this.idEstadoInstruccionCat = idEstadoInstruccionCat;
    }

    public String getTotalOperacionesPaquete() {
        return totalOperacionesPaquete;
    }

    public void setTotalOperacionesPaquete(String totalOperacionesPaquete) {
        this.totalOperacionesPaquete = totalOperacionesPaquete;
    }

    public String getNumeroOperacionPaquete() {
        return numeroOperacionPaquete;
    }

    public void setNumeroOperacionPaquete(String numeroOperacionPaquete) {
        this.numeroOperacionPaquete = numeroOperacionPaquete;
    }

    public String getTotalTitulosPaquete() {
        return totalTitulosPaquete;
    }

    public void setTotalTitulosPaquete(String totalTitulosPaquete) {
        this.totalTitulosPaquete = totalTitulosPaquete;
    }

    public String getTotalImportePaquete() {
        return totalImportePaquete;
    }

    public void setTotalImportePaquete(String totalImportePaquete) {
        this.totalImportePaquete = totalImportePaquete;
    }

    public boolean isConMiscelaneaFiscal() {
        return conMiscelaneaFiscal;
    }

    public void setConMiscelaneaFiscal(boolean conMiscelaneaFiscal) {
        this.conMiscelaneaFiscal = conMiscelaneaFiscal;
    }

    public boolean isPuedeConfirmar() {
        return puedeConfirmar;
    }

    public void setPuedeConfirmar(boolean puedeConfirmar) {
        this.puedeConfirmar = puedeConfirmar;
    }

    public boolean isPuedeCancelar() {
        return puedeCancelar;
    }

    public void setPuedeCancelar(boolean puedeCancelar) {
        this.puedeCancelar = puedeCancelar;
    }

    public String getNombreCuentaTraspasante() {
        return nombreCuentaTraspasante;
    }

    public void setNombreCuentaTraspasante(String nombreCuentaTraspasante) {
        this.nombreCuentaTraspasante = nombreCuentaTraspasante;
    }

    public String getNombreCuentaReceptora() {
        return nombreCuentaReceptora;
    }

    public void setNombreCuentaReceptora(String nombreCuentaReceptora) {
        this.nombreCuentaReceptora = nombreCuentaReceptora;
    }

    public Double getInteresesGenerados() {
        return interesesGenerados;
    }

    public void setInteresesGenerados(Double interesesGenerados) {
        this.interesesGenerados = interesesGenerados;
    }

    public String getMensajeXml() {
        return mensajeXml;
    }

    public void setMensajeXml(String mensajeXml) {
        this.mensajeXml = mensajeXml;
    }

	/**
	 * Método para obtener el atributo strFolioControl
	 * @return El atributo strFolioControl
	 */
	public String getStrFolioControl() {
		return folioControl != null ? folioControl.toString() : null;
	}
	
	/**
	 * Método para obtener el atributo fechaReporto
	 * @return El atributo fechaReporto
	 */
	public Date getFechaReportoDate() {
		Date resultado = null;
		if(StringUtils.isNotBlank(fechaReporto)) {
			try {
				resultado = DateUtils.parseDate(fechaReporto, new String[]{"dd/MM/yyyy"});
			}
			catch (ParseException e) {
				resultado = null;
			}
		}
		return resultado;
	}

	/**
	 * Método para obtener el atributo instruccion
	 * @return El atributo instruccion
	 */
	public String getInstruccion() {
		return instruccion;
	}

	/**
	 * Método para establecer el atributo instruccion
	 * @param instruccion El valor del atributo instruccion a establecer.
	 */
	public void setInstruccion(String instruccion) {
		this.instruccion = instruccion;
	}

	public Long getIdVencimientoAnticipado() {
		return idVencimientoAnticipado;
	}

	public void setIdVencimientoAnticipado(Long idVencimientoAnticipado) {
		this.idVencimientoAnticipado = idVencimientoAnticipado;
	}

	public Long getIdInstitucionAutoriza() {
		return idInstitucionAutoriza;
	}

	public void setIdInstitucionAutoriza(Long idInstitucionAutoriza) {
		this.idInstitucionAutoriza = idInstitucionAutoriza;
	}

	public boolean isPosibleVencerAnticipadamente() {
		return posibleVencerAnticipadamente;
	}

	public void setPosibleVencerAnticipadamente(boolean posibleVencerAnticipadamente) {
		this.posibleVencerAnticipadamente = posibleVencerAnticipadamente;
	}

	public Date getFechaVencimiento() {
		return fechaVencimiento;
	}

	public void setFechaVencimiento(Date fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}

	public Long getIdInstitucionSolicitud() {
		return idInstitucionSolicitud;
	}

	public void setIdInstitucionSolicitud(Long idInstitucionSolicitud) {
		this.idInstitucionSolicitud = idInstitucionSolicitud;
	}
	
}
