package com.indeval.portaldali.presentation.util;

import java.util.List;

import com.indeval.portaldali.middleware.services.modelo.EmisoraVO;

public class EstadoCuentaSocInvHelper {

	private EmisoraVO emisora;
	
	@SuppressWarnings("unchecked")
	private List saldos;

	/**
	 * @return the emisora
	 */
	public EmisoraVO getEmisora() {
		return emisora;
	}

	/**
	 * @param emisora the emisora to set
	 */
	public void setEmisora(EmisoraVO emisora) {
		this.emisora = emisora;
	}

	/**
	 * @return the saldos
	 */
	@SuppressWarnings("unchecked")
	public List getSaldos() {
		return saldos;
	}

	/**
	 * @param saldos the saldos to set
	 */
	@SuppressWarnings("unchecked")
	public void setSaldos(List saldos) {
		this.saldos = saldos;
	}

	/**
	 * @return the tamanioSaldos
	 */
	public int getTamanioSaldos() {
		if( saldos != null ) {
			return saldos.size();
		}
		return 0;
	}
}
