/**
 * CMMV - Portal Dali
 * Copyrigth (c) 2016 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.model.operacionesMatch;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.indeval.portaldali.middleware.model.util.ConsultaOperacionesMatch;

/**
 * Modelo que representa la vista V_CONS_OPER_VALOR
 * 
 * @author Pablo Balderas
 */
@Entity
@Table(name = "V_CONS_OPER_VALOR")
public class ConsultaOperacionValor extends ConsultaOperacionesMatch implements Serializable {

	/** Id para la serializacion */
	private static final long serialVersionUID = -8608229878075455151L;

    /**
     * Metodo para obtener el atributo idInstruccionOperacion
     * @return El atributo idInstruccionOperacion
     */
    @Id
    @Column(name="ID_INSTRUCCION_OPERACION", insertable=false, updatable=false)
    public Long getIdInstruccionOperacion() {
        return super.getIdInstruccionOperacion();
    }

    /**
     * Método para establecer el atributo idInstruccionOperacion
     * @param idInstruccionOperacion El valor del atributo idInstruccionOperacion a establecer.
     */
    public void setIdInstruccionOperacion(Long idInstruccionOperacion) {
        super.setIdInstruccionOperacion(idInstruccionOperacion);
    }

    /**
     * Metodo para obtener el atributo idBitacoraMatch
     * @return El atributo idBitacoraMatch
     */
    @Column(name="ID_BITACORA_MATCH", insertable=false, updatable=false)
    public Long getIdBitacoraMatch() {
        return super.getIdBitacoraMatch();
    }

    /**
     * Método para establecer el atributo idBitacoraMatch
     * @param idBitacoraMatch El valor del atributo idBitacoraMatch a establecer.
     */
    public void setIdBitacoraMatch(Long idBitacoraMatch) {
        super.setIdBitacoraMatch(idBitacoraMatch); 
    }

	/**
	 * Método para obtener el atributo folioControl
	 * @return El atributo folioControl
	 */
	@Column(name="FOLIO_CONTROL", insertable=false, updatable=false)
	public Long getFolioControl() {
		return super.getFolioControl();
	}

	/**
	 * Método para establecer el atributo folioControl
	 * @param folioControl El valor del atributo folioControl a establecer.
	 */
	public void setFolioControl(Long folioControl) {
		super.setFolioControl(folioControl);
	}

	/**
	 * Método para obtener el atributo porPaquete
	 * @return El atributo porPaquete
	 */
	@Column(name="POR_PAQUETE", insertable=false, updatable=false)
	public boolean isPorPaquete() {
		return super.isPorPaquete();
	}

	/**
	 * Método para establecer el atributo porPaquete
	 * @param porPaquete El valor del atributo porPaquete a establecer.
	 */
	public void setPorPaquete(boolean porPaquete) {
		super.setPorPaquete(porPaquete);
	}

	/**
	 * Método para obtener el atributo referenciaPaquete
	 * @return El atributo referenciaPaquete
	 */
	@Column(name="REFERENCIA_PAQUETE", insertable=false, updatable=false)
	public String getReferenciaPaquete() {
		return super.getReferenciaPaquete();
	}

	/**
	 * Método para establecer el atributo referenciaPaquete
	 * @param referenciaPaquete El valor del atributo referenciaPaquete a establecer.
	 */
	public void setReferenciaPaquete(String referenciaPaquete) {
		super.setReferenciaPaquete(referenciaPaquete);
	}

	/**
	 * Método para obtener el atributo folioOrigen
	 * @return El atributo folioOrigen
	 */
	@Column(name="FOLIO_ORIGEN", insertable=false, updatable=false)
	public String getFolioOrigen() {
		return super.getFolioOrigen();
	}

	/**
	 * Método para establecer el atributo folioOrigen
	 * @param folioOrigen El valor del atributo folioOrigen a establecer.
	 */
	public void setFolioOrigen(String folioOrigen) {
		super.setFolioOrigen(folioOrigen);
	}

	/**
	 * Método para obtener el atributo nombreCortoTipoInstruccion
	 * @return El atributo nombreCortoTipoInstruccion
	 */
	@Column(name="NOMBRE_CORTO_TIPO_INSTRUCCION", insertable=false, updatable=false)
	public String getNombreCortoTipoInstruccion() {
		return super.getNombreCortoTipoInstruccion();
	}

	/**
	 * Método para establecer el atributo nombreCortoTipoInstruccion
	 * @param nombreCortoTipoInstruccion El valor del atributo nombreCortoTipoInstruccion a establecer.
	 */
	public void setNombreCortoTipoInstruccion(String nombreCortoTipoInstruccion) {
		super.setNombreCortoTipoInstruccion(nombreCortoTipoInstruccion);
	}

	/**
	 * Método para obtener el atributo instruccion
	 * @return El atributo instruccion
	 */
	@Column(name="INSTRUCCION", insertable=false, updatable=false)
	public String getInstruccion() {
		return super.getInstruccion();
	}

	/**
	 * Método para establecer el atributo instruccion
	 * @param instruccion El valor del atributo instruccion a establecer.
	 */
	public void setInstruccion(String instruccion) {
		super.setInstruccion(instruccion);
	}
	
    /**
     * Método para obtener el atributo descTipoInstruccion
     * @return El atributo descTipoInstruccion
     */
    @Column(name="DESC_TIPO_INSTRUCCION", insertable=false, updatable=false)
    public String getDescTipoInstruccion() {
        return super.getDescTipoInstruccion();
    }

    /**
     * Método para establecer el atributo descTipoInstruccion
     * @param descTipoInstruccion El valor del atributo descTipoInstruccion a establecer.
     */
    public void setDescTipoInstruccion(String descTipoInstruccion) {
        super.setDescTipoInstruccion(descTipoInstruccion);
    }

	/**
	 * Método para obtener el atributo origen
	 * @return El atributo origen
	 */
	@Column(name="ORIGEN", insertable=false, updatable=false)
	public String getOrigen() {
		return super.getOrigen();
	}

	/**
	 * Método para establecer el atributo origen
	 * @param origen El valor del atributo origen a establecer.
	 */
	public void setOrigen(String origen) {
		super.setOrigen(origen);
	}

	/**
	 * Método para obtener el atributo tv
	 * @return El atributo tv
	 */
	@Column(name="TV", insertable=false, updatable=false)
	public String getTv() {
		return super.getTv();
	}

	/**
	 * Método para establecer el atributo tv
	 * @param tv El valor del atributo tv a establecer.
	 */
	public void setTv(String tv) {
		super.setTv(tv);
	}

	/**
	 * Método para obtener el atributo emisora
	 * @return El atributo emisora
	 */
	@Column(name="EMISORA", insertable=false, updatable=false)
	public String getEmisora() {
		return super.getEmisora();
	}

	/**
	 * Método para establecer el atributo emisora
	 * @param emisora El valor del atributo emisora a establecer.
	 */
	public void setEmisora(String emisora) {
		super.setEmisora(emisora);
	}

	/**
	 * Método para obtener el atributo serie
	 * @return El atributo serie
	 */
	@Column(name="SERIE", insertable=false, updatable=false)
	public String getSerie() {
		return super.getSerie();
	}

	/**
	 * Método para establecer el atributo serie
	 * @param serie El valor del atributo serie a establecer.
	 */
	public void setSerie(String serie) {
		super.setSerie(serie);
	}

	/**
	 * Método para obtener el atributo cupon
	 * @return El atributo cupon
	 */
	@Column(name="CUPON", insertable=false, updatable=false)
	public String getCupon() {
		return super.getCupon();
	}

	/**
	 * Método para establecer el atributo cupon
	 * @param cupon El valor del atributo cupon a establecer.
	 */
	public void setCupon(String cupon) {
		super.setCupon(cupon);
	}

	/**
	 * Método para obtener el atributo isin
	 * @return El atributo isin
	 */
	@Column(name="ISIN", insertable=false, updatable=false)
	public String getIsin() {
		return super.getIsin();
	}

	/**
	 * Método para establecer el atributo isin
	 * @param isin El valor del atributo isin a establecer.
	 */
	public void setIsin(String isin) {
		super.setIsin(isin);
	}

	/**
	 * Método para obtener el atributo bovedaValores
	 * @return El atributo bovedaValores
	 */
	@Column(name="BOVEDA_VALORES", insertable=false, updatable=false)
	public String getBovedaValores() {
		return super.getBovedaValores();
	}

	/**
	 * Método para establecer el atributo bovedaValores
	 * @param bovedaValores El valor del atributo bovedaValores a establecer.
	 */
	public void setBovedaValores(String bovedaValores) {
		super.setBovedaValores(bovedaValores);
	}

	/**
	 * Método para obtener el atributo claveEstadoInstruccionCat
	 * @return El atributo claveEstadoInstruccionCat
	 */
	@Column(name="CLAVE_ESTADO_INSTRUCCION_CAT", insertable=false, updatable=false)
	public String getClaveEstadoInstruccionCat() {
		return super.getClaveEstadoInstruccionCat();
	}

	/**
	 * Método para establecer el atributo claveEstadoInstruccionCat
	 * @param claveEstadoInstruccionCat El valor del atributo claveEstadoInstruccionCat a establecer.
	 */
	public void setClaveEstadoInstruccionCat(String claveEstadoInstruccionCat) {
		super.setClaveEstadoInstruccionCat(claveEstadoInstruccionCat);
	}

    /**
     * Método para obtener el atributo descEstadoInstruccionCat
     * @return El atributo descEstadoInstruccionCat
     */
    @Column(name="DESC_ESTADO_INSTRUCCION_CAT", insertable=false, updatable=false)
    public String getDescEstadoInstruccionCat() {
        return super.getDescEstadoInstruccionCat();
    }

    /**
     * Método para establecer el atributo descEstadoInstruccionCat
     * @param descEstadoInstruccionCat El valor del atributo descEstadoInstruccionCat a establecer.
     */
    public void setDescEstadoInstruccionCat(String descEstadoInstruccionCat) {
        super.setDescEstadoInstruccionCat(descEstadoInstruccionCat);
    }

    /**
     * Método para obtener el atributo idInstitucionTraspasante
     * @return El atributo idInstitucionTraspasante
     */
    @Column(name="ID_INSTITUCION_TRASPASANTE", insertable=false, updatable=false)
    public Long getIdInstitucionTraspasante() {
        return super.getIdInstitucionTraspasante();
    }

    /**
     * Método para establecer el atributo idInstitucionTraspasante
     * @param idInstitucionTraspasante El valor del atributo idInstitucionTraspasante a establecer.
     */
    public void setIdInstitucionTraspasante(Long idInstitucionTraspasante) {
        super.setIdInstitucionTraspasante(idInstitucionTraspasante);
    }

	/**
	 * Método para obtener el atributo idFolioInstitucionTraspasante
	 * @return El atributo idFolioInstitucionTraspasante
	 */
	@Column(name="ID_FOLIO_INSTIT_TRASPASANTE", insertable=false, updatable=false)
	public String getIdFolioInstitucionTraspasante() {
		return super.getIdFolioInstitucionTraspasante();
	}

	/**
	 * Método para establecer el atributo idFolioInstitucionTraspasante
	 * @param idFolioInstitucionTraspasante El valor del atributo idFolioInstitucionTraspasante a establecer.
	 */
	public void setIdFolioInstitucionTraspasante(String idFolioInstitucionTraspasante) {
		super.setIdFolioInstitucionTraspasante(idFolioInstitucionTraspasante);
	}

	/**
	 * Método para obtener el atributo cuentaTraspasante
	 * @return El atributo cuentaTraspasante
	 */
	@Column(name="CUENTA_TRASPASANTE", insertable=false, updatable=false)
	public String getCuentaTraspasante() {
		return super.getCuentaTraspasante();
	}

	/**
	 * Método para establecer el atributo cuentaTraspasante
	 * @param cuentaTraspasante El valor del atributo cuentaTraspasante a establecer.
	 */
	public void setCuentaTraspasante(String cuentaTraspasante) {
		super.setCuentaTraspasante(cuentaTraspasante);
	}

    /**
     * Método para obtener el atributo idInstitucionReceptora
     * @return El atributo idInstitucionReceptora
     */
    @Column(name="ID_INSTITUCION_RECEPTORA", insertable=false, updatable=false)
    public Long getIdInstitucionReceptora() {
        return super.getIdInstitucionReceptora();
    }

    /**
     * Método para establecer el atributo idInstitucionReceptora
     * @param idInstitucionReceptora El valor del atributo idInstitucionReceptora a establecer.
     */
    public void setIdInstitucionReceptora(Long idInstitucionReceptora) {
        super.setIdInstitucionReceptora(idInstitucionReceptora);
    }

	/**
	 * Método para obtener el atributo idFolioInstitucionReceptora
	 * @return El atributo idFolioInstitucionReceptora
	 */
	@Column(name="ID_FOLIO_INSTIT_RECEPTORA", insertable=false, updatable=false)
	public String getIdFolioInstitucionReceptora() {
		return super.getIdFolioInstitucionReceptora();
	}

	/**
	 * Método para establecer el atributo idFolioInstitucionReceptora
	 * @param idFolioInstitucionReceptora El valor del atributo idFolioInstitucionReceptora a establecer.
	 */
	public void setIdFolioInstitucionReceptora(String idFolioInstitucionReceptora) {
		super.setIdFolioInstitucionReceptora(idFolioInstitucionReceptora);
	}

	/**
	 * Método para obtener el atributo cuentaReceptora
	 * @return El atributo cuentaReceptora
	 */
	@Column(name="CUENTA_RECEPTORA", insertable=false, updatable=false)
	public String getCuentaReceptora() {
		return super.getCuentaReceptora();
	}

	/**
	 * Método para establecer el atributo cuentaReceptora
	 * @param cuentaReceptora El valor del atributo cuentaReceptora a establecer.
	 */
	public void setCuentaReceptora(String cuentaReceptora) {
		super.setCuentaReceptora(cuentaReceptora);
	}

	/**
	 * Método para obtener el atributo precioTitulo
	 * @return El atributo precioTitulo
	 */
	@Column(name="PRECIO_TITULO", insertable=false, updatable=false)
	public Double getPrecioTitulo() {
		return super.getPrecioTitulo();
	}

	/**
	 * Método para establecer el atributo precioTitulo
	 * @param precioTitulo El valor del atributo precioTitulo a establecer.
	 */
	public void setPrecioTitulo(Double precioTitulo) {
		super.setPrecioTitulo(precioTitulo);
	}

	/**
	 * Método para obtener el atributo fechaReporto
	 * @return El atributo fechaReporto
	 */
	@Column(name="FECHA_REPORTO", insertable=false, updatable=false)
	public String getFechaReporto() {
		return super.getFechaReporto();
	}

	/**
	 * Método para establecer el atributo fechaReporto
	 * @param fechaReporto El valor del atributo fechaReporto a establecer.
	 */
	public void setFechaReporto(String fechaReporto) {
		super.setFechaReporto(fechaReporto);
	}

	/**
	 * Método para obtener el atributo claveTipoMensajeCat
	 * @return El atributo claveTipoMensajeCat
	 */
	@Column(name="CLAVE_TIPO_MENSAJE_CAT", insertable=false, updatable=false)
	public String getClaveTipoMensajeCat() {
		return super.getClaveTipoMensajeCat();
	}

	/**
	 * Método para establecer el atributo claveTipoMensajeCat
	 * @param claveTipoMensajeCat El valor del atributo claveTipoMensajeCat a establecer.
	 */
	public void setClaveTipoMensajeCat(String claveTipoMensajeCat) {
		super.setClaveTipoMensajeCat(claveTipoMensajeCat);
	}

    /**
     * Método para obtener el atributo descTipoMensajeCat
     * @return El atributo descTipoMensajeCat
     */
    @Column(name="DESC_TIPO_MENSAJE_CAT", insertable=false, updatable=false)
    public String getDescTipoMensajeCat() {
        return super.getDescTipoMensajeCat();
    }

    /**
     * Método para establecer el atributo descTipoMensajeCat
     * @param descTipoMensajeCat El valor del atributo descTipoMensajeCat a establecer.
     */
    public void setDescTipoMensajeCat(String descTipoMensajeCat) {
        super.setDescTipoMensajeCat(descTipoMensajeCat);
    }

	/**
	 * Método para obtener el atributo fechaConcertacion
	 * @return El atributo fechaConcertacion
	 */
	@Column(name="FECHA_CONCERTACION", insertable=false, updatable=false)
	public Date getFechaConcertacion() {
		return super.getFechaConcertacion();
	}

	/**
	 * Método para establecer el atributo fechaConcertacion
	 * @param fechaConcertacion El valor del atributo fechaConcertacion a establecer.
	 */
	public void setFechaConcertacion(Date fechaConcertacion) {
		super.setFechaConcertacion(fechaConcertacion);
	}

	/**
	 * Método para obtener el atributo fechaLiquidacion
	 * @return El atributo fechaLiquidacion
	 */
	@Column(name="FECHA_LIQUIDACION", insertable=false, updatable=false)
	public Date getFechaLiquidacion() {
		return super.getFechaLiquidacion();
	}

	/**
	 * Método para establecer el atributo fechaLiquidacion
	 * @param fechaLiquidacion El valor del atributo fechaLiquidacion a establecer.
	 */
	public void setFechaLiquidacion(Date fechaLiquidacion) {
		super.setFechaLiquidacion(fechaLiquidacion);
	}

	/**
	 * Método para obtener el atributo cantidadTitulos
	 * @return El atributo cantidadTitulos
	 */
	@Column(name="CANTIDAD_TITULOS", insertable=false, updatable=false)
	public Long getCantidadTitulos() {
		return super.getCantidadTitulos();
	}

	/**
	 * Método para establecer el atributo cantidadTitulos
	 * @param cantidadTitulos El valor del atributo cantidadTitulos a establecer.
	 */
	public void setCantidadTitulos(Long cantidadTitulos) {
		super.setCantidadTitulos(cantidadTitulos);
	}

	/**
	 * Método para obtener el atributo importe
	 * @return El atributo importe
	 */
	@Column(name="IMPORTE", insertable=false, updatable=false)
	public Double getImporte() {
		return super.getImporte();
	}

	/**
	 * Método para establecer el atributo importe
	 * @param importe El valor del atributo importe a establecer.
	 */
	public void setImporte(Double importe) {
		super.setImporte(importe);
	}

	/**
	 * Método para obtener el atributo bovedaEfectivo
	 * @return El atributo bovedaEfectivo
	 */
	@Column(name="BOVEDA_EFECTIVO", insertable=false, updatable=false)
	public String getBovedaEfectivo() {
		return super.getBovedaEfectivo();
	}

	/**
	 * Método para establecer el atributo bovedaEfectivo
	 * @param bovedaEfectivo El valor del atributo bovedaEfectivo a establecer.
	 */
	public void setBovedaEfectivo(String bovedaEfectivo) {
		super.setBovedaEfectivo(bovedaEfectivo);
	}

	/**
	 * Método para obtener el atributo tasaNegociada
	 * @return El atributo tasaNegociada
	 */
	@Column(name="TASA_NEGOCIADA", insertable=false, updatable=false)
	public Double getTasaNegociada() {
		return super.getTasaNegociada();
	}

	/**
	 * Método para establecer el atributo tasaNegociada
	 * @param tasaNegociada El valor del atributo tasaNegociada a establecer.
	 */
	public void setTasaNegociada(Double tasaNegociada) {
		super.setTasaNegociada(tasaNegociada);
	}

	/**
	 * Método para obtener el atributo plazoReporto
	 * @return El atributo plazoReporto
	 */
	@Column(name="PLAZO_REPORTO", insertable=false, updatable=false)
	public Long getPlazoReporto() {
		return super.getPlazoReporto();
	}

	/**
	 * Método para establecer el atributo plazoReporto
	 * @param plazoReporto El valor del atributo plazoReporto a establecer.
	 */
	public void setPlazoReporto(Long plazoReporto) {
		super.setPlazoReporto(plazoReporto);
	}

	/**
	 * Método para obtener el atributo claveDivisa
	 * @return El atributo claveDivisa
	 */
	@Column(name="CLAVE_DIVISA", insertable=false, updatable=false)
	public String getClaveDivisa() {
		return super.getClaveDivisa();
	}

	/**
	 * Método para establecer el atributo claveDivisa
	 * @param claveDivisa El valor del atributo claveDivisa a establecer.
	 */
	public void setClaveDivisa(String claveDivisa) {
		super.setClaveDivisa(claveDivisa);
	}

    /**
     * Método para obtener el atributo idError
     * @return El atributo idError
     */
    @Column(name="ID_ERROR_DALI", insertable=false, updatable=false)
    public Integer getIdError() {
        return super.getIdError();
    }

    /**
     * Método para establecer el atributo idError
     * @param idError El valor del atributo idError a establecer.
     */
    public void setIdError(Integer idError) {
        super.setIdError(idError);
    }

	/**
	 * Método para obtener el atributo claveError
	 * @return El atributo claveError
	 */
	@Column(name="CLAVE_ERROR", insertable=false, updatable=false)
	public String getClaveError() {
		return super.getClaveError();
	}

	/**
	 * Método para establecer el atributo claveError
	 * @param claveError El valor del atributo claveError a establecer.
	 */
	public void setClaveError(String claveError) {
		super.setClaveError(claveError);
	}

    /**
     * Método para obtener el atributo descError
     * @return El atributo descError
     */
    @Column(name="DESC_ERROR", insertable=false, updatable=false)
    public String getDescError() {
        return super.getDescError();
    }

    /**
     * Método para establecer el atributo descError
     * @param descError El valor del atributo descError a establecer.
     */
    public void setDescError(String descError) {
        super.setDescError(descError);
    }

    /**
     * Método para obtener el atributo cveTipoInstitucionTraspasante
     * @return El atributo cveTipoInstitucionTraspasante
     */
    @Column(name="CVE_TIPO_INSTIT_TRASPASANTE", insertable=false, updatable=false)
    public String getCveTipoInstitucionTraspasante() {
        return super.getCveTipoInstitucionTraspasante();
    }

    /**
     * Método para establecer el atributo cveTipoInstitucionTraspasante
     * @param cveTipoInstitucionTraspasante El valor del atributo cveTipoInstitucionTraspasante a establecer.
     */
    public void setCveTipoInstitucionTraspasante(String cveTipoInstitucionTraspasante) {
        super.setCveTipoInstitucionTraspasante(cveTipoInstitucionTraspasante);
    }

    /**
     * Método para obtener el atributo cveTipoInstitucionReceptora
     * @return El atributo cveTipoInstitucionReceptora
     */
    @Column(name="CVE_TIPO_INSTIT_RECEPTORA", insertable=false, updatable=false)
    public String getCveTipoInstitucionReceptora() {
        return super.getCveTipoInstitucionReceptora();
    }

    /**
     * Método para establecer el atributo cveTipoInstitucionReceptora
     * @param cveTipoInstitucionReceptora El valor del atributo cveTipoInstitucionReceptora a establecer.
     */
    public void setCveTipoInstitucionReceptora(String cveTipoInstitucionReceptora) {
        super.setCveTipoInstitucionReceptora(cveTipoInstitucionReceptora);
    }

    /**
     * Método para obtener el atributo nombreCortoInstitTraspasante
     * @return El atributo nombreCortoInstitTraspasante
     */
    @Column(name="NOMBRE_CORTO_INST_TRASPASANTE", insertable=false, updatable=false)
    public String getNombreCortoInstitTraspasante() {
        return super.getNombreCortoInstitTraspasante();
    }

    /**
     * Método para establecer el atributo nombreCortoInstitTraspasante
     * @param nombreCortoInstitTraspasante El valor del atributo nombreCortoInstitTraspasante a establecer.
     */
    public void setNombreCortoInstitTraspasante(String nombreCortoInstitTraspasante) {
        super.setNombreCortoInstitTraspasante(nombreCortoInstitTraspasante);
    }

    /**
     * Método para obtener el atributo nombreCortoInstitReceptora
     * @return El atributo nombreCortoInstitReceptora
     */
    @Column(name="NOMBRE_CORTO_INST_RECEPTORA", insertable=false, updatable=false)
    public String getNombreCortoInstitReceptora() {
        return super.getNombreCortoInstitReceptora();
    }

    /**
     * Método para establecer el atributo nombreCortoInstitReceptora
     * @param nombreCortoInstitReceptora El valor del atributo nombreCortoInstitReceptora a establecer.
     */
    public void setNombreCortoInstitReceptora(String nombreCortoInstitReceptora) {
        super.setNombreCortoInstitReceptora(nombreCortoInstitReceptora);
    }

    /**
     * Método para obtener el atributo idInstitucionBancoTrabajo
     * @return El atributo idInstitucionBancoTrabajo
     */
    @Column(name="ID_INSTITUCION_BANCO_TRABAJO", insertable=false, updatable=false)
    public Long getIdInstitucionBancoTrabajo() {
        return super.getIdInstitucionBancoTrabajo();
    }

    /**
     * Método para establecer el atributo idInstitucionBancoTrabajo
     * @param idInstitucionBancoTrabajo El valor del atributo idInstitucionBancoTrabajo a establecer.
     */
    public void setIdInstitucionBancoTrabajo(Long idInstitucionBancoTrabajo) {
        super.setIdInstitucionBancoTrabajo(idInstitucionBancoTrabajo);
    }

    /**
     * Método para obtener el atributo cveTipoInstitBancoTrabajo
     * @return El atributo cveTipoInstitBancoTrabajo
     */
    @Column(name="CVE_TIPO_INSTIT_BANCO_TRABAJO", insertable=false, updatable=false)
    public String getCveTipoInstitBancoTrabajo() {
        return super.getCveTipoInstitBancoTrabajo();
    }

    /**
     * Método para establecer el atributo cveTipoInstitBancoTrabajo
     * @param cveTipoInstitBancoTrabajo El valor del atributo cveTipoInstitBancoTrabajo a establecer.
     */
    public void setCveTipoInstitBancoTrabajo(String cveTipoInstitBancoTrabajo) {
        super.setCveTipoInstitBancoTrabajo(cveTipoInstitBancoTrabajo);
    }

    /**
     * Método para obtener el atributo folioInstitBancoTrabajo
     * @return El atributo folioInstitBancoTrabajo
     */
    @Column(name="FOLIO_INSTIT_BANCO_TRABAJO", insertable=false, updatable=false)
    public String getFolioInstitBancoTrabajo() {
        return super.getFolioInstitBancoTrabajo();
    }

    /**
     * Método para establecer el atributo folioInstitBancoTrabajo
     * @param folioInstitBancoTrabajo El valor del atributo folioInstitBancoTrabajo a establecer.
     */
    public void setFolioInstitBancoTrabajo(String folioInstitBancoTrabajo) {
        super.setFolioInstitBancoTrabajo(folioInstitBancoTrabajo);
    }

    /**
     * Método para obtener el atributo nombreCortoInstBancoTrab
     * @return El atributo nombreCortoInstBancoTrab
     */
    @Column(name="NOMBRE_CORTO_INST_BANCO_TRAB", insertable=false, updatable=false)
    public String getNombreCortoInstBancoTrab() {
        return super.getNombreCortoInstBancoTrab();
    }

    /**
     * Método para establecer el atributo nombreCortoInstBancoTrab
     * @param nombreCortoInstBancoTrab El valor del atributo nombreCortoInstBancoTrab a establecer.
     */
    public void setNombreCortoInstBancoTrab(String nombreCortoInstBancoTrab) {
        super.setNombreCortoInstBancoTrab(nombreCortoInstBancoTrab);
    }

    /**
     * Método para obtener el atributo cuentaBancoTrabajo
     * @return El atributo cuentaBancoTrabajo
     */
    @Column(name="CUENTA_BANCO_TRABAJO", insertable=false, updatable=false)
    public String getCuentaBancoTrabajo() {
        return super.getCuentaBancoTrabajo();
    }

    /**
     * Método para establecer el atributo cuentaBancoTrabajo
     * @param cuentaBancoTrabajo El valor del atributo cuentaBancoTrabajo a establecer.
     */
    public void setCuentaBancoTrabajo(String cuentaReceptora) {
        super.setCuentaBancoTrabajo(cuentaReceptora);
    }

    /**
     * Método para obtener el atributo folioInstruccionReceptora
     * @return El atributo folioInstruccionReceptora
     */
    @Column(name="FOLIO_INSTRUCCION_RECEPTORA", insertable=false, updatable=false)
    public String getFolioInstruccionReceptora() {
        return super.getFolioInstruccionReceptora();
    }

    /**
     * Método para establecer el atributo folioInstruccionReceptora
     * @param folioInstruccionReceptora El valor del atributo folioInstruccionReceptora a establecer.
     */
    public void setFolioInstruccionReceptora(String folioInstruccionReceptora) {
        super.setFolioInstruccionReceptora(folioInstruccionReceptora);
    }

    /**
     * Método para obtener el atributo folioInstruccionTraspasante
     * @return El atributo folioInstruccionTraspasante
     */
    @Column(name="FOLIO_INSTRUCCION_TRASPASANTE", insertable=false, updatable=false)
    public String getFolioInstruccionTraspasante() {
        return super.getFolioInstruccionTraspasante();
    }

    /**
     * Método para establecer el atributo folioInstruccionTraspasante
     * @param folioInstruccionTraspasante El valor del atributo folioInstruccionTraspasante a establecer.
     */
    public void setFolioInstruccionTraspasante(String folioInstruccionReceptora) {
        super.setFolioInstruccionTraspasante(folioInstruccionReceptora);
    }

    /**
     * Método para obtener el atributo tieneParcialidades
     * @return El atributo tieneParcialidades
     */
    @Column(name="TIENE_PARCIALIDADES", insertable=false, updatable=false)
    public boolean isTieneParcialidades() {
        return super.isTieneParcialidades();
    }

    /**
     * Método para establecer el atributo tieneParcialidades
     * @param tieneParcialidades El valor del atributo tieneParcialidades a establecer.
     */
    public void setTieneParcialidades(boolean tieneParcialidades) {
        super.setTieneParcialidades(tieneParcialidades);
    }

    /**
     * Método para obtener el atributo idEstadoInstruccionCat
     * @return El atributo idEstadoInstruccionCat
     */
    @Column(name="ID_ESTADO_INSTRUCCION_CAT", insertable=false, updatable=false)
    public Long getIdEstadoInstruccionCat() {
        return super.getIdEstadoInstruccionCat();
    }

    /**
     * Método para establecer el atributo idEstadoInstruccionCat
     * @param idEstadoInstruccionCat El valor del atributo idEstadoInstruccionCat a establecer.
     */
    public void setIdEstadoInstruccionCat(Long idEstadoInstruccionCat) {
        super.setIdEstadoInstruccionCat(idEstadoInstruccionCat);
    }

    /**
     * Método para obtener el atributo totalOperacionesPaquete
     * @return El atributo totalOperacionesPaquete
     */
    @Column(name="TOTAL_OPERACIONES_PAQUETE", insertable=false, updatable=false)
    public String getTotalOperacionesPaquete() {
        return super.getTotalOperacionesPaquete();
    }

    /**
     * Método para establecer el atributo totalOperacionesPaquete
     * @param totalOperacionesPaquete El valor del atributo totalOperacionesPaquete a establecer.
     */
    public void setTotalOperacionesPaquete(String totalOperacionesPaquete) {
        super.setTotalOperacionesPaquete(totalOperacionesPaquete);
    }

    /**
     * Método para obtener el atributo numeroOperacionPaquete
     * @return El atributo numeroOperacionPaquete
     */
    @Column(name="NUMERO_OPERACION_PAQUETE", insertable=false, updatable=false)
    public String getNumeroOperacionPaquete() {
        return super.getNumeroOperacionPaquete();
    }

    /**
     * Método para establecer el atributo numeroOperacionPaquete
     * @param numeroOperacionPaquete El valor del atributo numeroOperacionPaquete a establecer.
     */
    public void setNumeroOperacionPaquete(String numeroOperacionPaquete) {
        super.setNumeroOperacionPaquete(numeroOperacionPaquete);
    }

    /**
     * Método para obtener el atributo totalTitulosPaquete
     * @return El atributo totalTitulosPaquete
     */
    @Column(name="TOTAL_TITULOS_PAQUETE", insertable=false, updatable=false)
    public String getTotalTitulosPaquete() {
        return super.getTotalTitulosPaquete();
    }

    /**
     * Método para establecer el atributo totalTitulosPaquete
     * @param totalTitulosPaquete El valor del atributo totalTitulosPaquete a establecer.
     */
    public void setTotalTitulosPaquete(String totalTitulosPaquete) {
        super.setTotalTitulosPaquete(totalTitulosPaquete);
    }

    /**
     * Método para obtener el atributo totalImportePaquete
     * @return El atributo totalImportePaquete
     */
    @Column(name="TOTAL_IMPORTE_PAQUETE", insertable=false, updatable=false)
    public String getTotalImportePaquete() {
        return super.getTotalImportePaquete();
    }

    /**
     * Método para establecer el atributo totalImportePaquete
     * @param totalImportePaquete El valor del atributo totalImportePaquete a establecer.
     */
    public void setTotalImportePaquete(String totalImportePaquete) {
        super.setTotalImportePaquete(totalImportePaquete);
    }

    /**
     * Método para obtener el atributo conMiscelaneaFiscal
     * @return El atributo conMiscelaneaFiscal
     */
    @Column(name="CON_MISCELANEA_FISCAL", insertable=false, updatable=false)
    public boolean isConMiscelaneaFiscal() {
        return super.isConMiscelaneaFiscal();
    }

    /**
     * Método para establecer el atributo conMiscelaneaFiscal
     * @param conMiscelaneaFiscal El valor del atributo conMiscelaneaFiscal a establecer.
     */
    public void setConMiscelaneaFiscal(boolean conMiscelaneaFiscal) {
        super.setConMiscelaneaFiscal(conMiscelaneaFiscal);
    }

    /**
     * Método para obtener el atributo puedeConfirmar
     * @return El atributo puedeConfirmar
     */
    @Column(name="PUEDE_CONFIRMAR", insertable=false, updatable=false)
    public boolean isPuedeConfirmar() {
        return super.isPuedeConfirmar();
    }

    /**
     * Método para establecer el atributo puedeConfirmar
     * @param puedeConfirmar El valor del atributo puedeConfirmar a establecer.
     */
    public void setPuedeConfirmar(boolean puedeConfirmar) {
        super.setPuedeConfirmar(puedeConfirmar);
    }

    /**
     * Método para obtener el atributo nombreCuentaTraspasante
     * @return El atributo nombreCuentaTraspasante
     */
    @Column(name="NOMBRE_CUENTA_TRASPASANTE", insertable=false, updatable=false)
    public String getNombreCuentaTraspasante() {
        return super.getNombreCuentaTraspasante();
    }

    /**
     * Método para establecer el atributo nombreCuentaTraspasante
     * @param nombreCuentaTraspasante El valor del atributo nombreCuentaTraspasante a establecer.
     */
    public void setNombreCuentaTraspasante(String nombreCuentaTraspasante) {
        super.setNombreCuentaTraspasante(nombreCuentaTraspasante);
    }

    /**
     * Método para obtener el atributo nombreCuentaReceptora
     * @return El atributo nombreCuentaReceptora
     */
    @Column(name="NOMBRE_CUENTA_RECEPTORA", insertable=false, updatable=false)
    public String getNombreCuentaReceptora() {
        return super.getNombreCuentaReceptora();
    }

    /**
     * Método para establecer el atributo nombreCuentaReceptora
     * @param nombreCuentaReceptora El valor del atributo nombreCuentaReceptora a establecer.
     */
    public void setNombreCuentaReceptora(String nombreCuentaReceptora) {
        super.setNombreCuentaReceptora(nombreCuentaReceptora);
    }

    /**
     * Método para obtener el atributo interesesGenerados
     * @return El atributo interesesGenerados
     */
    @Column(name="INTERESES_GENERADOS", insertable=false, updatable=false)
    public Double getInteresesGenerados() {
        return super.getInteresesGenerados();
    }

    /**
     * Método para establecer el atributo interesesGenerados
     * @param interesesGenerados El valor del atributo interesesGenerados a establecer.
     */
    public void setInteresesGenerados(Double interesesGenerados) {
        super.setInteresesGenerados(interesesGenerados);
    }

}
