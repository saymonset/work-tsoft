package com.indeval.portalinternacional.middleware.servicios.vo;

import com.indeval.portalinternacional.middleware.servicios.modelo.CalendarioDerechos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class CalendarioDerechosVO implements Serializable {

    private static final long serialVersionUID = -3518299119966693232L;
    private Long idCalendario;// ID_CALENDARIO_INT
    private String referencia;// REFERENCIA
    private String folio;// MT_567_910
    private EstadoDerechoIntVO estado;// ID_ESTADO_DERECHO_INT
    private String tipoValor;// TIPO_VALOR
    private String emisora;// EMISORA
    private String serie;// SERIE
    private String cupon;// CUPON
    private String isin;// ISIN
    private Date fechaCorte;// FECHA_CORTE
    private Date fechaPago;// FECHA_PAGO
    private Date fechaValor;// FECHA_VALOR
    private Date periodoInicio;// PERIODO
    private Integer diasMensaje;// DIAS_MENSAJE
    private BigDecimal factor;// FACTOR_MENSAJE
    private BigDecimal tasa;// TASA
    private Long posicionMensaje;// POSICION_MENSAJE
    private String divisa;// MONEDA
    private BigDecimal importe;// IMPORTE
    private BigDecimal importeIndeval;// IMPORTE_INDEVAL
    private BigDecimal factorVerificacion;// VERIFICACION_FACTOR
    private BigDecimal tasaVerificacion;// VERIFICACION_TASA
    private Integer diasVerificacion;// VERIFICACION_DIAS
    private String tipoTasa;// TIPO_TASA
    private BigDecimal posicionIndeval;// POSICION_INDEVAL
    private BigDecimal valorNominal;// VALOR_NOMINAL
    private CustodioVO custodio;// ID_CUSTODIO
    private TipoPagoIntVO tipoPagoCAEV;// ID_TIPO_PAGO
    private BovedaVO bovedaValores;// ID_BOVEDA
    private BovedaVO bovedaEfectivo;// ID_BOVEDA_DESTINO
    private TipoPagoIntVO tipoPagoCAMV;// ID_TIPO_PAGO
    private Date fechaPost; // fecha post
    private Long posicionVNIndeval;// posicion valor nominal
    private BigDecimal importePTSA;
    private BigDecimal importeGRSS;
    private Date periodoFin;
    private Integer diasSP;
    private BigDecimal factorCalculado;
    private Integer idEstadoMax;
    private Long prioridad;
    private Boolean has566;
    private ControlVO control;
    private Date horaEnvio;
    private Date horaRecepcion;
    private Double montoConfirmado;

    //    Solo para euroclear, la fecha de pago y fecha valor si son iguales, debe tener un m567
    private Boolean hasEqualsFpagoAndFvalor;
    private Boolean hasM567;
    private Boolean euroclear;


    /**
     * Constructor por Default
     */
    public CalendarioDerechosVO() {

    }
    public CalendarioDerechosVO(CalendarioDerechos calendarioDerechos) {
        if (null != calendarioDerechos) {
            this.idCalendario = calendarioDerechos.getIdCalendario();
            this.referencia = calendarioDerechos.getReferencia();
            this.folio = calendarioDerechos.getFolio();
            this.estado = new EstadoDerechoIntVO(calendarioDerechos.getEstado());// ID_ESTADO_DERECHO_INT
            this.tipoValor = calendarioDerechos.getTipoValor();// TIPO_VALOR
            this.emisora = calendarioDerechos.getEmisora();// EMISORA
            this.serie = calendarioDerechos.getSerie();// SERIE
            this.cupon = calendarioDerechos.getCupon();// CUPON
            this.isin = calendarioDerechos.getIsin();// ISIN
            this.fechaCorte = calendarioDerechos.getFechaCorte();// FECHA_CORTE
            this.fechaPago = calendarioDerechos.getFechaPago();// FECHA_PAGO
            this.fechaValor = calendarioDerechos.getFechaValor();// FECHA_VALOR
            this.periodoInicio = calendarioDerechos.getPeriodoInicio();// PERIODO
            this.diasMensaje = calendarioDerechos.getDiasMensaje();// DIAS_MENSAJE
            this.factor = calendarioDerechos.getFactor();// FACTOR_MENSAJE
            this.tasa = calendarioDerechos.getTasa();// TASA
            this.posicionMensaje = calendarioDerechos.getPosicionMensaje();// POSICION_MENSAJE
            this.divisa = calendarioDerechos.getDivisa();// MONEDA
            this.importe = calendarioDerechos.getImporte();// IMPORTE
            this.importeIndeval = calendarioDerechos.getImporteIndeval();// IMPORTE_INDEVAL
            this.factorVerificacion = calendarioDerechos.getFactorVerificacion();// VERIFICACION_FACTOR
            this.tasaVerificacion = calendarioDerechos.getTasaVerificacion();// VERIFICACION_TASA
            this.diasVerificacion = calendarioDerechos.getDiasVerificacion();// VERIFICACION_DIAS
            this.tipoTasa = calendarioDerechos.getTipoTasa();// TIPO_TASA
            this.posicionIndeval = calendarioDerechos.getPosicionIndeval();// POSICION_INDEVAL
            this.valorNominal = calendarioDerechos.getValorNominal();// VALOR_NOMINAL
            this.custodio = new CustodioVO(calendarioDerechos.getCustodio());
            this.tipoPagoCAEV = new TipoPagoIntVO(calendarioDerechos.getTipoPagoCAEV());// ID_TIPO_PAGO
            this.bovedaValores = new BovedaVO(calendarioDerechos.getBovedaValores());// ID_BOVEDA
            this.bovedaEfectivo = new BovedaVO(calendarioDerechos.getBovedaEfectivo());// ID_BOVEDA_DESTINO
            this.tipoPagoCAMV = new TipoPagoIntVO(calendarioDerechos.getTipoPagoCAMV()); ;// ID_TIPO_PAGO
            this.fechaPost = calendarioDerechos.getFechaPost(); // fecha post
            this.posicionVNIndeval = calendarioDerechos.getPosicionVNIndeval();// posicion valor nominal
            this.importePTSA = calendarioDerechos.getImportePTSA();
            this.importeGRSS = calendarioDerechos.getImporteGRSS();
            this.periodoFin = calendarioDerechos.getPeriodoFin();
            this.diasSP = calendarioDerechos.getDiasSP();
            this.factorCalculado = calendarioDerechos.getFactorCalculado();
            this.idEstadoMax = calendarioDerechos.getIdEstadoMax();
            this.prioridad = calendarioDerechos.getPrioridad();
            this.has566 = calendarioDerechos.getHas566();
            this.control = new ControlVO(calendarioDerechos.getControl());
            this.horaEnvio = calendarioDerechos.getHoraEnvio();
            this.horaRecepcion = calendarioDerechos.getHoraRecepcion();
            this.montoConfirmado = calendarioDerechos.getMontoConfirmado();
            //    Solo para euroclear, la fecha de pago y fecha valor si son iguales, debe tener un m567
            this.hasEqualsFpagoAndFvalor = false;
            this.hasM567 = false;
            this.euroclear = false;
        }
    }

    public Long getIdCalendario() {
        return idCalendario;
    }

    public void setIdCalendario(Long idCalendario) {
        this.idCalendario = idCalendario;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public EstadoDerechoIntVO getEstado() {
        return estado;
    }

    public void setEstado(EstadoDerechoIntVO estado) {
        this.estado = estado;
    }

    public String getTipoValor() {
        return tipoValor;
    }

    public void setTipoValor(String tipoValor) {
        this.tipoValor = tipoValor;
    }

    public String getEmisora() {
        return emisora;
    }

    public void setEmisora(String emisora) {
        this.emisora = emisora;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getCupon() {
        return cupon;
    }

    public void setCupon(String cupon) {
        this.cupon = cupon;
    }

    public String getIsin() {
        return isin;
    }

    public void setIsin(String isin) {
        this.isin = isin;
    }

    public Date getFechaCorte() {
        return fechaCorte;
    }

    public void setFechaCorte(Date fechaCorte) {
        this.fechaCorte = fechaCorte;
    }

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    public Date getFechaValor() {
        return fechaValor;
    }

    public void setFechaValor(Date fechaValor) {
        this.fechaValor = fechaValor;
    }

    public Date getPeriodoInicio() {
        return periodoInicio;
    }

    public void setPeriodoInicio(Date periodoInicio) {
        this.periodoInicio = periodoInicio;
    }

    public Integer getDiasMensaje() {
        return diasMensaje;
    }

    public void setDiasMensaje(Integer diasMensaje) {
        this.diasMensaje = diasMensaje;
    }

    public BigDecimal getFactor() {
        return factor;
    }

    public void setFactor(BigDecimal factor) {
        this.factor = factor;
    }

    public BigDecimal getTasa() {
        return tasa;
    }

    public void setTasa(BigDecimal tasa) {
        this.tasa = tasa;
    }

    public Long getPosicionMensaje() {
        return posicionMensaje;
    }

    public void setPosicionMensaje(Long posicionMensaje) {
        this.posicionMensaje = posicionMensaje;
    }

    public String getDivisa() {
        return divisa;
    }

    public void setDivisa(String divisa) {
        this.divisa = divisa;
    }

    public BigDecimal getImporte() {
        return importe;
    }

    public void setImporte(BigDecimal importe) {
        this.importe = importe;
    }

    public BigDecimal getImporteIndeval() {
        return importeIndeval;
    }

    public void setImporteIndeval(BigDecimal importeIndeval) {
        this.importeIndeval = importeIndeval;
    }

    public BigDecimal getFactorVerificacion() {
        return factorVerificacion;
    }

    public void setFactorVerificacion(BigDecimal factorVerificacion) {
        this.factorVerificacion = factorVerificacion;
    }

    public BigDecimal getTasaVerificacion() {
        return tasaVerificacion;
    }

    public void setTasaVerificacion(BigDecimal tasaVerificacion) {
        this.tasaVerificacion = tasaVerificacion;
    }

    public Integer getDiasVerificacion() {
        return diasVerificacion;
    }

    public void setDiasVerificacion(Integer diasVerificacion) {
        this.diasVerificacion = diasVerificacion;
    }

    public String getTipoTasa() {
        return tipoTasa;
    }

    public void setTipoTasa(String tipoTasa) {
        this.tipoTasa = tipoTasa;
    }

    public BigDecimal getPosicionIndeval() {
        return posicionIndeval;
    }

    public void setPosicionIndeval(BigDecimal posicionIndeval) {
        this.posicionIndeval = posicionIndeval;
    }

    public BigDecimal getValorNominal() {
        return valorNominal;
    }

    public void setValorNominal(BigDecimal valorNominal) {
        this.valorNominal = valorNominal;
    }

    public CustodioVO getCustodio() {
        return custodio;
    }

    public void setCustodio(CustodioVO custodio) {
        this.custodio = custodio;
    }

    public TipoPagoIntVO getTipoPagoCAEV() {
        return tipoPagoCAEV;
    }

    public void setTipoPagoCAEV(TipoPagoIntVO tipoPagoCAEV) {
        this.tipoPagoCAEV = tipoPagoCAEV;
    }

    public BovedaVO getBovedaValores() {
        return bovedaValores;
    }

    public void setBovedaValores(BovedaVO bovedaValores) {
        this.bovedaValores = bovedaValores;
    }

    public BovedaVO getBovedaEfectivo() {
        return bovedaEfectivo;
    }

    public void setBovedaEfectivo(BovedaVO bovedaEfectivo) {
        this.bovedaEfectivo = bovedaEfectivo;
    }

    public TipoPagoIntVO getTipoPagoCAMV() {
        return tipoPagoCAMV;
    }

    public void setTipoPagoCAMV(TipoPagoIntVO tipoPagoCAMV) {
        this.tipoPagoCAMV = tipoPagoCAMV;
    }

    public Date getFechaPost() {
        return fechaPost;
    }

    public void setFechaPost(Date fechaPost) {
        this.fechaPost = fechaPost;
    }

    public Long getPosicionVNIndeval() {
        return posicionVNIndeval;
    }

    public void setPosicionVNIndeval(Long posicionVNIndeval) {
        this.posicionVNIndeval = posicionVNIndeval;
    }

    public BigDecimal getImportePTSA() {
        return importePTSA;
    }

    public void setImportePTSA(BigDecimal importePTSA) {
        this.importePTSA = importePTSA;
    }

    public BigDecimal getImporteGRSS() {
        return importeGRSS;
    }

    public void setImporteGRSS(BigDecimal importeGRSS) {
        this.importeGRSS = importeGRSS;
    }

    public Date getPeriodoFin() {
        return periodoFin;
    }

    public void setPeriodoFin(Date periodoFin) {
        this.periodoFin = periodoFin;
    }

    public Integer getDiasSP() {
        return diasSP;
    }

    public void setDiasSP(Integer diasSP) {
        this.diasSP = diasSP;
    }

    public BigDecimal getFactorCalculado() {
        return factorCalculado;
    }

    public void setFactorCalculado(BigDecimal factorCalculado) {
        this.factorCalculado = factorCalculado;
    }

    public Integer getIdEstadoMax() {
        return idEstadoMax;
    }

    public void setIdEstadoMax(Integer idEstadoMax) {
        this.idEstadoMax = idEstadoMax;
    }

    public Long getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(Long prioridad) {
        this.prioridad = prioridad;
    }

    public Boolean getHas566() {
        return has566;
    }

    public void setHas566(Boolean has566) {
        this.has566 = has566;
    }

    public ControlVO getControl() {
        return control;
    }

    public void setControl(ControlVO control) {
        this.control = control;
    }

    public Date getHoraEnvio() {
        return horaEnvio;
    }

    public void setHoraEnvio(Date horaEnvio) {
        this.horaEnvio = horaEnvio;
    }

    public Date getHoraRecepcion() {
        return horaRecepcion;
    }

    public void setHoraRecepcion(Date horaRecepcion) {
        this.horaRecepcion = horaRecepcion;
    }

    public Double getMontoConfirmado() {
        return montoConfirmado;
    }

    public void setMontoConfirmado(Double montoConfirmado) {
        this.montoConfirmado = montoConfirmado;
    }

    public Boolean getHasEqualsFpagoAndFvalor() {
        return hasEqualsFpagoAndFvalor;
    }

    public void setHasEqualsFpagoAndFvalor(Boolean hasEqualsFpagoAndFvalor) {
        this.hasEqualsFpagoAndFvalor = hasEqualsFpagoAndFvalor;
    }

    public Boolean getHasM567() {
        return hasM567;
    }

    public void setHasM567(Boolean hasM567) {
        this.hasM567 = hasM567;
    }

    public Boolean getEuroclear() {
        return euroclear;
    }

    public void setEuroclear(Boolean euroclear) {
        this.euroclear = euroclear;
    }
}
