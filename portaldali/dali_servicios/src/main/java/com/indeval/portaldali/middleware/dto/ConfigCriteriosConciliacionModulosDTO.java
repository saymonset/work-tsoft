package com.indeval.portaldali.middleware.dto;

import java.io.Serializable;

public class ConfigCriteriosConciliacionModulosDTO implements Serializable {

	private static final long serialVersionUID = 7521745956841639875L;

	private final String campoFiltroFolioTras;
	private final String campoFiltroCuentaTras;
	private final String campoFiltroFolioRece;
	private final String campoFiltroCuentaRece;
	private final String campoFiltroTv;
	private final String campoFiltroEmisora;
	private final String campoFiltroSerie;
	private final String campoFiltroTipoInstr;
	private final String campoFiltroTipoOper;

	public ConfigCriteriosConciliacionModulosDTO(String campoFiltroFolioTras, String campoFiltroCuentaTras,
			String campoFiltroFolioRece, String campoFiltroCuentaRece, String campoFiltroTv, String campoFiltroEmisora,
			String campoFiltroSerie, String campoFiltroTipoInstr, String campoFiltroTipoOper) {
		super();
		this.campoFiltroFolioTras = campoFiltroFolioTras;
		this.campoFiltroCuentaTras = campoFiltroCuentaTras;
		this.campoFiltroFolioRece = campoFiltroFolioRece;
		this.campoFiltroCuentaRece = campoFiltroCuentaRece;
		this.campoFiltroTv = campoFiltroTv;
		this.campoFiltroEmisora = campoFiltroEmisora;
		this.campoFiltroSerie = campoFiltroSerie;
		this.campoFiltroTipoInstr = campoFiltroTipoInstr;
		this.campoFiltroTipoOper = campoFiltroTipoOper;
	}

	public String getCampoFiltroFolioTras() {
		return campoFiltroFolioTras;
	}

	public String getCampoFiltroCuentaTras() {
		return campoFiltroCuentaTras;
	}

	public String getCampoFiltroFolioRece() {
		return campoFiltroFolioRece;
	}

	public String getCampoFiltroCuentaRece() {
		return campoFiltroCuentaRece;
	}

	public String getCampoFiltroTv() {
		return campoFiltroTv;
	}

	public String getCampoFiltroEmisora() {
		return campoFiltroEmisora;
	}

	public String getCampoFiltroSerie() {
		return campoFiltroSerie;
	}

	public String getCampoFiltroTipoInstr() {
		return campoFiltroTipoInstr;
	}

	public String getCampoFiltroTipoOper() {
		return campoFiltroTipoOper;
	}

}
