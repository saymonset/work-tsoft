package com.indeval.portalinternacional.middleware.servicios.vo;

import com.indeval.portaldali.persistence.modelo.CuentaNombrada;
import com.indeval.portalinternacional.middleware.servicios.modelo.CatBic;

import java.io.Serializable;
import java.util.Date;

public class CatBicVO implements Serializable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -6144848110747524422L;

	/**
	 * Long
	 */
	private Long idCatbic;

	/**
	 * CuentaNombrada
	 */
	private CuentaNombradaVO cuentaNombrada;

	/**
	 * String
	 */
	private String bicProd;

	/**
	 * String
	 */
	private String bicPrueba;

	/**
	 * String
	 */
	private String cuentaIndeval;

	/**
	 * Date
	 */
	private Date fechaHora;

	/**
	 * String
	 */
	private String pais;

	/**
	 * String
	 */
	private String status;

	/**
	 * String
	 */
	private String usuario;

	/**
	 * String 
	 */
	private String moneda;

	/**
	 * String
	 */
	private String detalleCustodio;

	/**
	 * String 
	 */
	private String mercado;
	
	/**
	 * String 
	 */
	private String estatusRegistro;

	/**
	 * Integer
	 */
	private Integer activo;
	
	/**
	 * String
	 */
	private String abreviacionCustodio;
	
    /** Indica si el valor se va a editar */
    private boolean editar;


	public CatBicVO(CatBic catBic) {
		this.idCatbic = catBic.getIdCatbic();

		/**
		 * CuentaNombrada
		 */
		this.cuentaNombrada = new CuentaNombradaVO(catBic.getCuentaNombrada());

		/**
		 * String
		 */
		this.bicProd  = catBic.getBicProd();

		/**
		 * String
		 */
		this.bicPrueba = catBic.getBicPrueba();

		/**
		 * String
		 */
		this.cuentaIndeval = catBic.getCuentaIndeval();

		/**
		 * Date
		 */
		this.fechaHora = catBic.getFechaHora();

		/**
		 * String
		 */
		this.pais = catBic.getPais();

		/**
		 * String
		 */
		this.status = catBic.getStatus();

		/**
		 * String
		 */
		this.usuario = catBic.getUsuario();

		/**
		 * String
		 */
		this.moneda = catBic.getMoneda();

		/**
		 * String
		 */
		this.detalleCustodio = catBic.getDetalleCustodio();

		/**
		 * String
		 */
		this.mercado = catBic.getMercado();

		/**
		 * String
		 */
		this.estatusRegistro = catBic.getEstatusRegistro();

		/**
		 * Integer
		 */
		this.activo = catBic.getActivo();

		/**
		 * String
		 */
		this.abreviacionCustodio = catBic.getAbreviacionCustodio();


	}

	public CatBicVO() {
	}

	public Long getIdCatbic() {
		return idCatbic;
	}


	public void setIdCatbic(Long idCatbic) {
		this.idCatbic = idCatbic;
	}




	public String getBicProd() {
		return bicProd;
	}


	public void setBicProd(String bicProd) {
		this.bicProd = bicProd;
	}


	public String getBicPrueba() {
		return bicPrueba;
	}


	public void setBicPrueba(String bicPrueba) {
		this.bicPrueba = bicPrueba;
	}


	public String getCuentaIndeval() {
		return cuentaIndeval;
	}


	public void setCuentaIndeval(String cuentaIndeval) {
		this.cuentaIndeval = cuentaIndeval;
	}


	public Date getFechaHora() {
		return fechaHora;
	}


	public void setFechaHora(Date fechaHora) {
		this.fechaHora = fechaHora;
	}


	public String getPais() {
		return pais;
	}


	public void setPais(String pais) {
		this.pais = pais;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getUsuario() {
		return usuario;
	}


	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}


	public String getMoneda() {
		return moneda;
	}


	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}


	public String getDetalleCustodio() {
		return detalleCustodio;
	}


	public void setDetalleCustodio(String detalleCustodio) {
		this.detalleCustodio = detalleCustodio;
	}


	public String getMercado() {
		return mercado;
	}


	public void setMercado(String mercado) {
		this.mercado = mercado;
	}


	public String getEstatusRegistro() {
		return estatusRegistro;
	}


	public void setEstatusRegistro(String estatusRegistro) {
		this.estatusRegistro = estatusRegistro;
	}


	public Integer getActivo() {
		return activo;
	}


	public void setActivo(Integer activo) {
		this.activo = activo;
	}
	
	/**
	 * @return the abreviacionCustodio
	 */
	public String getAbreviacionCustodio() {
		return abreviacionCustodio;
	}


	/**
	 * @param abreviacionCustodio the abreviacionCustodio to set
	 */
	public void setAbreviacionCustodio(String abreviacionCustodio) {
		this.abreviacionCustodio = abreviacionCustodio;
	}


	public boolean isEditar() {
		return editar;
	}


	public void setEditar(boolean editar) {
		this.editar = editar;
	}


	@Override
	public String toString() {
		return "CatBicVO [idCatbic=" + idCatbic + ", cuentaNombrada=" + cuentaNombrada + ", bicProd=" + bicProd
				+ ", bicPrueba=" + bicPrueba + ", cuentaIndeval=" + cuentaIndeval + ", fechaHora=" + fechaHora
				+ ", pais=" + pais + ", status=" + status + ", usuario=" + usuario + ", moneda=" + moneda
				+ ", detalleCustodio=" + detalleCustodio + ", mercado=" + mercado + ", estatusRegistro="
				+ estatusRegistro + ", activo=" + activo + "]";
	}

	public void setCuentaNombrada(CuentaNombradaVO cuentaNombrada) {
		this.cuentaNombrada = cuentaNombrada;
	}

	public CuentaNombradaVO getCuentaNombrada() {
		return cuentaNombrada;
	}
}
