// Cambio Multidivisas
package com.indeval.portalinternacional.middleware.servicios.modelo;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("multidivisa")
public class Multidivisa {
	
	public enum TipoMovimiento{
		
		DEPOSITO("D"), RETIRO("R");
		
		private String clave;

		private TipoMovimiento(String clave) {
			this.clave = clave;
		}

		public String getClave() {
			return clave;
		}
	}

	public enum EstadoMovimiento{
		
		REGISTRADO("RG"), RETENIDO("RT"), AUTORIZADO("AU"), LIBERADO("LB"), APLICADO("AP"), CANCELADO("CA"), REENVIADO("RE");
		
		private String clave;

		private EstadoMovimiento(String clave) {
			this.clave = clave;
		}

		public String getClave() {
			return clave;
		}
	}
	
	@XStreamAlias("tipoMovimiento")	
	private String tipoMovimiento;
	
	@XStreamAlias("estado")	
	private String estado;
	
	@XStreamAlias("folioControl")	
	private Long folioConstrol;
	
	@XStreamAlias("id")	
	private Long id;

	/**
	 * @return the tipoMovimiento
	 */
	public String getTipoMovimiento() {
		return tipoMovimiento;
	}

	/**
	 * @param tipoMovimiento the tipoMovimiento to set
	 */
	public void setTipoMovimiento(TipoMovimiento tipoMovimiento) {
		this.tipoMovimiento = tipoMovimiento.clave;
	}

	/**
	 * @return the estado
	 */
	public String getEstado() {
		return estado;
	}

	/**
	 * @param estado the estado to set
	 */
	public void setEstado(EstadoMovimiento estado) {
		this.estado = estado.clave;
	}

	/**
	 * @return the folioConstrol
	 */
	public Long getFolioConstrol() {
		return folioConstrol;
	}

	/**
	 * @param folioConstrol the folioConstrol to set
	 */
	public void setFolioConstrol(Long folioConstrol) {
		this.folioConstrol = folioConstrol;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
}
