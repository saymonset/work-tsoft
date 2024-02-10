package com.indeval.portalinternacional.presentation.controller.derechos;

import java.io.Serializable;
import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;

import com.indeval.portalinternacional.middleware.servicios.constantes.BeneficiariosConstantes;
import com.indeval.portalinternacional.middleware.servicios.constantes.Constantes;
import com.indeval.portalinternacional.middleware.servicios.modelo.DerechoBeneficiario;
import com.indeval.portalinternacional.middleware.servicios.modelo.Domicilio;
import com.indeval.portalinternacional.middleware.servicios.modelo.VConsultaBeneficiarioDerechosCuenta;

public class BeneficiarioDerecho implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 4565022993227663524L;
    private Long idDerechoBeneficiario;
    private Long tipoInstitucion;
    private String folioInstitucion;
    private String nombre;
    private String direccion;
    private String rfc;
    private Double porcentajeRetencion;
    private Long posicion;
    private BigDecimal monto;
    private BigDecimal montoRetenido;
    private BigDecimal montoPagado;
    private String cuenta;
    private Long idDerechoCapital;
    private String uoi;
    private String error;
    private Long idBeneficiario;
    private String adpNumber;
    private String country;
    private String tipoFormato;
    private String tipoBeneficiario;
    private boolean eliminar;

    public Long getTipoInstitucion() {
        return this.tipoInstitucion;
    }

    public String getFolioInstitucion() {
        return this.folioInstitucion;
    }

    public String getNombre() {
        return this.nombre;
    }

    public String getDireccion() {
        return this.direccion;
    }

    public String getRfc() {
        return this.rfc;
    }

    public Double getPorcentajeRetencion() {
        return this.porcentajeRetencion;
    }

    public String getPorcentajeRetencionAsString() {
        return com.indeval.portalinternacional.middleware.servicios.constantes.Constantes.DECIMAL_FORMAT_PORCENTAJE_VIEW
                .format(this.porcentajeRetencion.floatValue());
    }

    public Long getPosicion() {
        return this.posicion;
    }

    public BigDecimal getMonto() {
        return this.monto;
    }

    public BigDecimal getMontoRetenido() {
        return this.montoRetenido;
    }

    public BigDecimal getMontoPagado() {
        return this.montoPagado;
    }

    public void setTipoInstitucion(final Long tipoInstitucion) {
        this.tipoInstitucion = tipoInstitucion;
    }

    public void setFolioInstitucion(final String folioInstitucion) {
        this.folioInstitucion = folioInstitucion;
    }

    public void setNombre(final String nombre) {
        this.nombre = nombre;
    }

    public void setDireccion(final String direccion) {
        this.direccion = direccion;
    }

    public void setRfc(final String rfc) {
        this.rfc = rfc;
    }

    public void setPorcentajeRetencion(final Double porcentajeRetencion) {
        this.porcentajeRetencion = porcentajeRetencion;
    }

    public void setPosicion(final Long posicion) {
        this.posicion = posicion;
    }

    public void setMonto(final BigDecimal monto) {
        this.monto = monto;
    }

    public void setMontoRetenido(final BigDecimal montoRetenido) {
        this.montoRetenido = montoRetenido;
    }

    public void setMontoPagado(final BigDecimal montoPagado) {
        this.montoPagado = montoPagado;
    }

    public Long getIdDerechoBeneficiario() {
        return this.idDerechoBeneficiario;
    }

    public void setIdDerechoBeneficiario(final Long idDerechoBeneficiario) {
        this.idDerechoBeneficiario = idDerechoBeneficiario;
    }

    public String getCuenta() {
        return this.cuenta;
    }

    public void setCuenta(final String cuenta) {
        this.cuenta = cuenta;
    }

    public void transform(final DerechoBeneficiario derechoBeneficiario, final Float proporcion,
            final boolean esDistribucion) {
        StringBuffer address = null;
        Domicilio domicilio = null;
        BigDecimal proporcionTmp = new BigDecimal(proporcion);
        BigDecimal posicionTmp = null;
        BigDecimal porcentajeRet = null;
        
        
        this.setIdDerechoBeneficiario(derechoBeneficiario.getIdDerechoBeneficiario());
        this.setIdDerechoCapital(derechoBeneficiario.getIdDerechoCapital());
        
        if (!esDistribucion) {
            this.setPorcentajeRetencion(derechoBeneficiario.getBeneficiario()
                    .getPorcentajeRetencion());
        } else {
            this.setPorcentajeRetencion(com.indeval.portalinternacional.middleware.servicios.constantes.Constantes.CERO_DOUBLE);
        }
        this.setPosicion(derechoBeneficiario.getAsignacion());
        
        if(derechoBeneficiario.getBeneficiario()!=null) {
        
        	this.setNombre(derechoBeneficiario.getBeneficiario().getNombreGeneral());        	
	        this.setUoi(derechoBeneficiario.getBeneficiario().getUoiNumber());
	        this.setAdpNumber(derechoBeneficiario.getBeneficiario().getAdp());
	        this.setTipoFormato(derechoBeneficiario.getBeneficiario().getTipoFormato());
	        this.setTipoBeneficiario(derechoBeneficiario.getBeneficiario().getTipoBeneficiario().getDescTipoBeneficiario());
	        
	        address = new StringBuffer();
	        
	        if (derechoBeneficiario.getBeneficiario().getTipoFormato()
	                .equalsIgnoreCase(BeneficiariosConstantes.FORMATO_W8_BEN)
	                || derechoBeneficiario.getBeneficiario().getTipoFormato()
	                        .equalsIgnoreCase(BeneficiariosConstantes.FORMATO_W8_BEN2014)
	                || derechoBeneficiario.getBeneficiario().getTipoFormato()
	                        .equalsIgnoreCase(BeneficiariosConstantes.FORMATO_W8_BEN2017)
	                || derechoBeneficiario.getBeneficiario().getTipoFormato()
	                        .equalsIgnoreCase(BeneficiariosConstantes.FORMATO_W8_BEN_E)
	                || derechoBeneficiario.getBeneficiario().getTipoFormato()
	                        .equalsIgnoreCase(BeneficiariosConstantes.FORMATO_W8_BEN_E_2016)) {
	            if (derechoBeneficiario.getBeneficiario().getFormatoW8BEN() != null) {
	                this.setRfc(derechoBeneficiario.getBeneficiario().getFormatoW8BEN()
	                        .getForeignTaxIdNumb());
	            }
	            if (derechoBeneficiario.getBeneficiario().getFormatoW8BENE() != null) {
	                this.setRfc(derechoBeneficiario.getBeneficiario().getFormatoW8BENE().getUsTIN());
	            }
	            if (derechoBeneficiario.getBeneficiario().getDomicilioW8Normal() != null) {
	                domicilio = derechoBeneficiario.getBeneficiario().getDomicilioW8Normal();
	                this.setCountry(domicilio.getCountry());
	            }
	        } else if (derechoBeneficiario.getBeneficiario().getTipoFormato()
	                .equalsIgnoreCase(BeneficiariosConstantes.FORMATO_W9)) {
	            if (derechoBeneficiario.getBeneficiario().getDomicilioW9() != null) {
	                domicilio = derechoBeneficiario.getBeneficiario().getDomicilioW9();
	                this.setCountry(Constantes.COUNTRY_W9);
	            }
	            if (derechoBeneficiario.getBeneficiario().getFormatoW9() != null) {
	                this.setRfc(derechoBeneficiario.getBeneficiario().getFormatoW9().getSsn());
	            }
	        } else if (derechoBeneficiario.getBeneficiario().getTipoFormato()
	                .equalsIgnoreCase(BeneficiariosConstantes.FORMATO_W8_IMY)
	                || derechoBeneficiario.getBeneficiario().getTipoFormato()
	                        .equalsIgnoreCase(BeneficiariosConstantes.FORMATO_W8_IMY2015)
	                || derechoBeneficiario.getBeneficiario().getTipoFormato()
	                        .equalsIgnoreCase(BeneficiariosConstantes.FORMATO_W8_IMY2017)) {
	            if (derechoBeneficiario.getBeneficiario().getFormatoW8IMY() != null) {
	                this.setRfc(derechoBeneficiario.getBeneficiario().getFormatoW8IMY()
	                        .getTaxpayerIdNumb());
	            }
	            if (derechoBeneficiario.getBeneficiario().getFormatoW8IMY2015() != null) {
	                this.setRfc(derechoBeneficiario.getBeneficiario().getFormatoW8IMY2015().getUsTIN());
	            }
	            // if (derechoBeneficiario.getBeneficiario().getFormatoW8IMY2017() != null) {
	            // this.setRfc(derechoBeneficiario.getBeneficiario().getFormatoW8IMY2017().getUsTIN());
	            // }
	            if (derechoBeneficiario.getBeneficiario().getDomicilioW8Normal() != null) {
	                domicilio = derechoBeneficiario.getBeneficiario().getDomicilioW8Normal();
	                this.setCountry(domicilio.getCountry());
	            }
	        } else if (derechoBeneficiario.getBeneficiario().getTipoFormato()
	                .equalsIgnoreCase(BeneficiariosConstantes.FORMATO_MILA)) {
	            if (derechoBeneficiario.getBeneficiario().getFormatoMILA() != null) {
	                if (!StringUtils.isBlank(derechoBeneficiario.getBeneficiario().getFormatoMILA()
	                        .getRfc())) {
	                    this.setRfc(derechoBeneficiario.getBeneficiario().getFormatoMILA().getRfc());
	                }
	                // Es un rfc de personas morales el tipo de documento
	                else if (derechoBeneficiario.getBeneficiario().getFormatoMILA()
	                        .getTipoDocumentoIndentidad().getIdTipoDocumento() == 70) {
	                    this.setRfc(derechoBeneficiario.getBeneficiario().getFormatoMILA()
	                            .getNumeroDocumento());
	                }
	            }
	            if (derechoBeneficiario.getBeneficiario().getDomicilioMILA() != null) {
	                domicilio = derechoBeneficiario.getBeneficiario().getDomicilioMILA();
	                this.setCountry(domicilio.getCountry());
	            }
	        }
	
	        if (domicilio != null) {
	            address.append(domicilio.getCalleNumeros());
	            if (StringUtils.isNotBlank(domicilio.getPostalCode())) {
	                address.append(",").append(domicilio.getPostalCode());
	            }
	            if (StringUtils.isNotBlank(domicilio.getCityTown())) {
	                address.append(",").append(domicilio.getCityTown());
	            }
	            if (StringUtils.isNotBlank(domicilio.getStateProvince())) {
	                address.append(",").append(domicilio.getStateProvince());
	            }
	            this.setDireccion(address.toString());
	        }
	        
        }

        if(derechoBeneficiario.getCuentaNombrada()!=null) {
        
	        this.setCuenta(derechoBeneficiario.getCuentaNombrada().getCuenta());
	        this.setFolioInstitucion(derechoBeneficiario.getCuentaNombrada().getInstitucion().getFolioInstitucion());	        
	        this.setTipoInstitucion(derechoBeneficiario.getCuentaNombrada().getInstitucion().getTipoInstitucion().getIdTipoInstitucion());
        }

        // Obtiene la posicion
        posicionTmp = new BigDecimal(this.getPosicion());
        // Monto = posicionNoAsignada * proporcion
        this.setMonto(posicionTmp.multiply(proporcionTmp).setScale(2, BigDecimal.ROUND_HALF_DOWN));
        // Si el porcentaje de retención es mayor a 0
        if (this.getPorcentajeRetencion() != null && this.getPorcentajeRetencion() > 0) {
            // Calcula el porcentaje de retención
            porcentajeRet =
                    new BigDecimal(this.getPorcentajeRetencion()).divide(
                            Constantes.CIEN_BIGDECIMAL, 4, BigDecimal.ROUND_HALF_DOWN);
            // Calcula el monto retenido
            this.setMontoRetenido(this.getMonto().multiply(porcentajeRet)
                    .setScale(2, BigDecimal.ROUND_HALF_DOWN));
            // Calcula el monto pagado
            this.setMontoPagado(this.getMonto().subtract(this.getMontoRetenido())
                    .setScale(2, BigDecimal.ROUND_HALF_DOWN));
        } else {
            this.setMontoRetenido(BigDecimal.ZERO);
            this.setMontoPagado(this.getMonto());
        }
    }

    public void transformEntityVista(final VConsultaBeneficiarioDerechosCuenta derechoBeneficiario, final Float proporcion, 
                                     final boolean esDistribucion) {
        BigDecimal posicionTmp = null;
        BigDecimal porcentajeRet = null;
        BigDecimal proporcionTmp = new BigDecimal(proporcion);

        this.setCuenta(derechoBeneficiario.getCuenta());
        this.setFolioInstitucion(derechoBeneficiario.getFolioInstitucion());
        this.setIdDerechoBeneficiario(derechoBeneficiario.getIdDerechoBeneficiario());
        this.setIdDerechoCapital(derechoBeneficiario.getIdDerechoCapital());
        this.setNombre(derechoBeneficiario.getNombreCompleto());
        if (!esDistribucion) {
            this.setPorcentajeRetencion(derechoBeneficiario.getPorcentajeRetencion());
        }
        else {
            this.setPorcentajeRetencion(com.indeval.portalinternacional.middleware.servicios.constantes.Constantes.CERO_DOUBLE);
        }
        this.setPosicion(derechoBeneficiario.getPosicion());
        this.setUoi(derechoBeneficiario.getUoi());
        this.setAdpNumber(derechoBeneficiario.getAdpNumber());
        this.setTipoFormato(derechoBeneficiario.getTipoFormato());
        this.setTipoBeneficiario(derechoBeneficiario.getDescTipoBeneficiario());

        if(derechoBeneficiario.getTipoFormato()!=null) {
	        // Estableciendo RFC, Direccion y Pais
	        if (derechoBeneficiario.getTipoFormato().equalsIgnoreCase(BeneficiariosConstantes.FORMATO_W8_BEN) || 
	            derechoBeneficiario.getTipoFormato().equalsIgnoreCase(BeneficiariosConstantes.FORMATO_W8_BEN2014) || 
	            derechoBeneficiario.getTipoFormato().equalsIgnoreCase(BeneficiariosConstantes.FORMATO_W8_BEN2017) ||
	            derechoBeneficiario.getTipoFormato().equalsIgnoreCase(BeneficiariosConstantes.FORMATO_W8_BEN_E) || 
	            derechoBeneficiario.getTipoFormato().equalsIgnoreCase(BeneficiariosConstantes.FORMATO_W8_BEN_E_2016)) {
	            if (derechoBeneficiario.getIdCamposFormatoW8BEN() != null && derechoBeneficiario.getIdCamposFormatoW8BEN() > 0) {
	                this.setRfc(derechoBeneficiario.getRfcFW8BEN());
	            }
	            if (derechoBeneficiario.getIdCamposFormatoW8BENE() != null  && derechoBeneficiario.getIdCamposFormatoW8BENE() > 0) {
	                this.setRfc(derechoBeneficiario.getRfcFW8BENE());
	            }
	            if (derechoBeneficiario.getIdDomicilioW8Normal() != null && derechoBeneficiario.getIdDomicilioW8Normal() > 0) {
	                this.setDireccion(derechoBeneficiario.getDireccionDomW8NORMAL());
	                this.setCountry(derechoBeneficiario.getContryDomW8NORMAL());
	            }
	        }
	        else if (derechoBeneficiario.getTipoFormato().equalsIgnoreCase(BeneficiariosConstantes.FORMATO_W9)) {
	            if (derechoBeneficiario.getIdDomicilioW9() != null && derechoBeneficiario.getIdDomicilioW9() > 0) {
	                this.setDireccion(derechoBeneficiario.getDireccionDomW9());
	                this.setCountry(derechoBeneficiario.getContryDomW9());
	            }
	            if (derechoBeneficiario.getIdCamposFormatoW9() != null && derechoBeneficiario.getIdCamposFormatoW9() > 0) {
	                this.setRfc(derechoBeneficiario.getRfcFW9());
	            }
	        }
	        else if (derechoBeneficiario.getTipoFormato().equalsIgnoreCase(BeneficiariosConstantes.FORMATO_W8_IMY) ||
	                 derechoBeneficiario.getTipoFormato().equalsIgnoreCase(BeneficiariosConstantes.FORMATO_W8_IMY2015) ||
	                 derechoBeneficiario.getTipoFormato().equalsIgnoreCase(BeneficiariosConstantes.FORMATO_W8_IMY2017)) {
	            if (derechoBeneficiario.getIdCamposFormatoW8IMY() != null && derechoBeneficiario.getIdCamposFormatoW8IMY() > 0) {
	                this.setRfc(derechoBeneficiario.getRfcFW8IMY());
	            }
	            if (derechoBeneficiario.getIdCamposFormatoW8IMY2015() != null && derechoBeneficiario.getIdCamposFormatoW8IMY2015() > 0) {
	                this.setRfc(derechoBeneficiario.getRfcFW8IMY2015());
	            }
	            if (derechoBeneficiario.getIdDomicilioW8Normal() != null && derechoBeneficiario.getIdDomicilioW8Normal() > 0) {
	                this.setDireccion(derechoBeneficiario.getDireccionDomW8NORMAL());
	                this.setCountry(derechoBeneficiario.getContryDomW8NORMAL());
	            }
	        }
	        else if (derechoBeneficiario.getTipoFormato().equalsIgnoreCase(BeneficiariosConstantes.FORMATO_MILA)) {
	            if (derechoBeneficiario.getIdCamposFormatoMILA() != null && derechoBeneficiario.getIdCamposFormatoMILA() > 0) {
	                if (!StringUtils.isBlank(derechoBeneficiario.getRfcFMILA())) {
	                    this.setRfc(derechoBeneficiario.getRfcFMILA());
	                }
	                // Es un rfc de personas morales el tipo de documento
	                else if (!StringUtils.isBlank(derechoBeneficiario.getRfcFMILANumeroDocumento())) {
	                    this.setRfc(derechoBeneficiario.getRfcFMILANumeroDocumento());
	                }
	            }
	            if (derechoBeneficiario.getIdDomicilioMILA() != null && derechoBeneficiario.getIdDomicilioMILA() > 0) {
	                this.setDireccion(derechoBeneficiario.getDireccionDomMila());
	                this.setCountry(derechoBeneficiario.getContryDomMILA());
	            }
	        }
        }

        this.setTipoInstitucion(derechoBeneficiario.getIdTipoInstitucion());

        // Obtiene la posicion
        posicionTmp = new BigDecimal(this.getPosicion());
        // Monto = posicionNoAsignada * proporcion
        this.setMonto(posicionTmp.multiply(proporcionTmp).setScale(2, BigDecimal.ROUND_HALF_DOWN));
        // Si el porcentaje de retención es mayor a 0
        if (this.getPorcentajeRetencion() != null && this.getPorcentajeRetencion() > 0) {
            // Calcula el porcentaje de retencion
            porcentajeRet = new BigDecimal(this.getPorcentajeRetencion()).divide(Constantes.CIEN_BIGDECIMAL, 4, BigDecimal.ROUND_HALF_DOWN);
            // Calcula el monto retenido
            this.setMontoRetenido(this.getMonto().multiply(porcentajeRet).setScale(2, BigDecimal.ROUND_HALF_DOWN));
            // Calcula el monto pagado
            this.setMontoPagado(this.getMonto().subtract(this.getMontoRetenido()).setScale(2, BigDecimal.ROUND_HALF_DOWN));
        }
        else {
            this.setMontoRetenido(BigDecimal.ZERO);
            this.setMontoPagado(this.getMonto());
        }
    }

    public Long getIdDerechoCapital() {
        return this.idDerechoCapital;
    }

    public void setIdDerechoCapital(final Long idDerechoCapital) {
        this.idDerechoCapital = idDerechoCapital;
    }

    public String getUoi() {
        return this.uoi;
    }

    public void setUoi(final String uoi) {
        this.uoi = uoi;
    }

    public String getError() {
        return this.error;
    }

    public void setError(final String error) {
        this.error = error;
    }

    public Long getIdBeneficiario() {
        return this.idBeneficiario;
    }

    public void setIdBeneficiario(final Long idBeneficiario) {
        this.idBeneficiario = idBeneficiario;
    }

    public String getAdpNumber() {
        return this.adpNumber;
    }

    public void setAdpNumber(final String adpNumber) {
        this.adpNumber = adpNumber;
    }

    public String getCountry() {
        return this.country;
    }

    public void setCountry(final String country) {
        this.country = country;
    }

    public String getTipoFormato() {
        return this.tipoFormato;
    }

    public void setTipoFormato(final String tipoFormato) {
        this.tipoFormato = tipoFormato;
    }

    public String getTipoBeneficiario() {
        return this.tipoBeneficiario;
    }

    public void setTipoBeneficiario(final String tipoBeneficiario) {
        this.tipoBeneficiario = tipoBeneficiario;
    }

    public boolean isEliminar() {
        return this.eliminar;
    }

    public void setEliminar(final boolean eliminar) {
        this.eliminar = eliminar;
    }
}
