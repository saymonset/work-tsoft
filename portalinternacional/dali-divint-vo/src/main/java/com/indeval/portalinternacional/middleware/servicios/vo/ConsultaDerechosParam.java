package com.indeval.portalinternacional.middleware.servicios.vo;

import java.io.Serializable;
import java.util.Date;

import com.indeval.portaldali.middleware.servicios.modelo.vo.EmisionVO;
import com.indeval.portaldali.persistence.modelo.Institucion;

public class ConsultaDerechosParam implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2523373873514747248L;
	
	private EmisionVO emision;

	private Date fechaCorte;
	
	private Date fechaPago;
	
	private Long idDivisa;
	
	private Integer idEstado;
	
	private Institucion institucion;
	
	private String claveInstitucion;

	private String divisa;

	private String bovedaCustodio;

	private String descEstatusDerecho;
    
    private Integer totalRegistrosParaReportes;

	public EmisionVO getEmision() {
		return emision;
	}

	public void setEmision(EmisionVO emision) {
		this.emision = emision;
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

	public Long getIdDivisa() {
		return idDivisa;
	}

	public void setIdDivisa(Long idDivisa) {
		this.idDivisa = idDivisa;
	}

	public Integer getIdEstado() {
		return idEstado;
	}

	public void setIdEstado(Integer idEstado) {
		this.idEstado = idEstado;
	}

	public Institucion getInstitucion() {
		return institucion;
	}

	public void setInstitucion(Institucion institucion) {
		this.institucion = institucion;
	}

	public String getClaveInstitucion() {
		return claveInstitucion;
	}

	public void setClaveInstitucion(String claveInstitucion) {
		this.claveInstitucion = claveInstitucion;
	}

	public String getDivisa() {
        return divisa;
    }

    public void setDivisa(String divisa) {
        this.divisa = divisa;
    }

    public String getBovedaCustodio() {
        return bovedaCustodio;
    }

    public void setBovedaCustodio(String bovedaCustodio) {
        this.bovedaCustodio = bovedaCustodio;
    }

    public String getDescEstatusDerecho() {
        return descEstatusDerecho;
    }

    public void setDescEstatusDerecho(String descEstatusDerecho) {
        this.descEstatusDerecho = descEstatusDerecho;
    }

    public Integer getTotalRegistrosParaReportes() {
        return totalRegistrosParaReportes;
    }

    public void setTotalRegistrosParaReportes(Integer totalRegistrosParaReportes) {
        this.totalRegistrosParaReportes = totalRegistrosParaReportes;
    }

    @Override
	public String toString() {
		return "ConsultaDerechosParam [emision=" + emision + ", fechaCorte="
				+ fechaCorte + ", fechaPago=" + fechaPago + ", idDivisa="
				+ idDivisa + ", idEstado=" + idEstado + ", institucion="
				+ institucion + ", claveInstitucion=" + claveInstitucion + ", "
				+ "divisa=" + this.divisa + ", "
		        + "bovedaCustodio=" + this.bovedaCustodio + ", "
				+ "descEstatusDerecho=" + this.descEstatusDerecho + ", " 
				+ "totalRegistrosParaReportes=" + this.totalRegistrosParaReportes + "]";
	}
}
