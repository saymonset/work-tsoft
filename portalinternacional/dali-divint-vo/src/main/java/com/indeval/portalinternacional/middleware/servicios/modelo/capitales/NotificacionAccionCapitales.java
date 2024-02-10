package com.indeval.portalinternacional.middleware.servicios.modelo.capitales;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("NotificacionAccionCapitales")
public class NotificacionAccionCapitales {
    /**
     * enum que establece el los tipos de cambio de estados
     * 
     * @author lmunoz
     * 
     */
    public enum TipoAccion {
        DERECHO_CAPITAL_PAGADO("PDC"), RANGO_FECHAS_PAGADO("RFP");
        private final String tipoAccion;

        /**
         * costructor
         * 
         * @param tipoAccion
         */
        private TipoAccion(final String tipoAccion) {
            this.tipoAccion = tipoAccion;
        }

        /**
         * tipo de accion
         * 
         * @return tipo accion
         */
        public String getTipoAccion() {
            return this.tipoAccion;
        }
    }

    @XStreamAlias("accion")
    @XStreamAsAttribute
    private String accion;

    @XStreamAlias("idDerechoCapital")
    private Long idDerechoCapital;


    @XStreamAlias("fechaInicio")
    private Date fechaInicio;


    @XStreamAlias("fechaFin")
    private Date fechaFin;

    /**
     * @return the accion
     */
    public String getAccion() {
        return this.accion;
    }

    /**
     * @param accion the accion to set
     */
    public void setAccion(final TipoAccion accion) {
        this.accion = accion.getTipoAccion();
    }


    public TipoAccion getTipoAccion() {
        if (StringUtils.isEmpty(this.accion)) {
            return null;
        }
        if (this.accion.equals(TipoAccion.DERECHO_CAPITAL_PAGADO.getTipoAccion())) {
            return TipoAccion.DERECHO_CAPITAL_PAGADO;
        } else if (this.accion.equals(TipoAccion.RANGO_FECHAS_PAGADO.getTipoAccion())) {
            return TipoAccion.RANGO_FECHAS_PAGADO;
        } else {
            return null;
        }
    }

    /**
     * @return the idDerechoCapital
     */
    public Long getIdDerechoCapital() {
        return this.idDerechoCapital;
    }

    /**
     * @param idDerechoCapital the idDerechoCapital to set
     */
    public void setIdDerechoCapital(final Long idDerechoCapital) {
        this.idDerechoCapital = idDerechoCapital;
    }

    /**
     * @return the fechaInicio
     */
    public Date getFechaInicio() {
        return this.fechaInicio;
    }

    /**
     * @param fechaInicio the fechaInicio to set
     */
    public void setFechaInicio(final Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     * @return the fechaFin
     */
    public Date getFechaFin() {
        return this.fechaFin;
    }

    /**
     * @param fechaFin the fechaFin to set
     */
    public void setFechaFin(final Date fechaFin) {
        this.fechaFin = fechaFin;
    }


}
