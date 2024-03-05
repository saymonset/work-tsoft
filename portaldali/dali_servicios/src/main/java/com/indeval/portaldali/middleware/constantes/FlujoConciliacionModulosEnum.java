package com.indeval.portaldali.middleware.constantes;

import com.indeval.portaldali.middleware.dto.ConfigCriteriosConciliacionModulosDTO;

public enum FlujoConciliacionModulosEnum {
	
	AE_MATCH("AE", "MATCH", 0, "obtenRegistrosConciliacionAeMatch", 
			new ConfigCriteriosConciliacionModulosDTO("LPAD(T.ID_TIPO_INSTITUCION, 2, '0') || T.FOLIO_INSTITUCION", "CT.CUENTA", 
					"LPAD(R.ID_TIPO_INSTITUCION, 2, '0') || R.FOLIO_INSTITUCION", "CR.CUENTA", "BA.TV", "BA.EMISORA", "BA.SERIE", null, null)),
	AE_MOS("AE", "MOS", 1, "obtenRegistrosConciliacionAeMos", 
			new ConfigCriteriosConciliacionModulosDTO("LPAD(T.ID_TIPO_INSTITUCION, 2, '0') || T.FOLIO_INSTITUCION", null, 
					"LPAD(R.ID_TIPO_INSTITUCION, 2, '0') || R.FOLIO_INSTITUCION", null, null, null, null, null, null)),
	AE_MAV("AE", "MAV", 2, "obtenRegistrosConciliacionAeMav", 
			new ConfigCriteriosConciliacionModulosDTO("NVL(LPAD(T.ID_TIPO_INSTITUCION, 2, '0') || T.FOLIO_INSTITUCION, LPAD(R.ID_TIPO_INSTITUCION, 2, '0') || R.FOLIO_INSTITUCION)", 
					"NVL(CT.CUENTA, CR.CUENTA)", null, null, "BA.TV", "BA.EMISORA", "BA.SERIE", null, null)),
	MATCH_MOV("MATCH", "MOV", 3, "obtenRegistrosConciliacionMatchMov", 
			new ConfigCriteriosConciliacionModulosDTO("\"folioParticipante\"", "\"cuentaParticipante\"", "\"folioContraparte\"", "\"cuentaContraparte\"", "\"tv\"", 
					"\"emisora\"", "\"serie\"", null, null)),
	MOV_SLV("MOV", "SLV", 4, "obtenRegistrosConciliacionMovSlv", 
			new ConfigCriteriosConciliacionModulosDTO("LPAD(TRA.ID_TIPO_INSTITUCION, 2, '0') || TRA.FOLIO_INSTITUCION", "C_TRA.CUENTA", 
					"LPAD(REC.ID_TIPO_INSTITUCION, 2, '0') || REC.FOLIO_INSTITUCION", "C_REC.CUENTA", "INO.CLAVE_TIPO_VALOR", 
					"EMA.CLAVE_PIZARRA", "EMN.SERIE", "MOV.ID_TIPO_INSTRUCCION", null)),
	MOS_SLV("MOS", "SLV", 5, "obtenRegistrosConciliacionMosSlv", 
			new ConfigCriteriosConciliacionModulosDTO("LPAD(TRA.ID_TIPO_INSTITUCION, 2, '0') || TRA.FOLIO_INSTITUCION", null, 
					"LPAD(REC.ID_TIPO_INSTITUCION, 2, '0') || REC.FOLIO_INSTITUCION", null, null, null, null, "MOS.ID_TIPO_INSTRUCCION", null)),
	MAV_SLV("MAV", "SLV", 6, "obtenRegistrosConciliacionMavSlv", 
			new ConfigCriteriosConciliacionModulosDTO("\"folioParticipante\"", "\"cuentaParticipante\"", null, null, "\"tv\"", "\"emisora\"", "\"serie\"", null, null)),
	SLV_MOV("SLV", "MOV", 7, "obtenRegistrosConciliacionSlvMov", 
			new ConfigCriteriosConciliacionModulosDTO("LPAD(TRA.ID_TIPO_INSTITUCION, 2, '0') || TRA.FOLIO_INSTITUCION", "C_TRA.CUENTA", 
					"LPAD(REC.ID_TIPO_INSTITUCION, 2, '0') || REC.FOLIO_INSTITUCION", "C_REC.CUENTA", "INO.CLAVE_TIPO_VALOR", 
					"EMA.CLAVE_PIZARRA", "EMN.SERIE", "IL.ID_TIPO_INSTRUCCION", null)),
	SLV_MOS("SLV", "MOS", 8, "obtenRegistrosConciliacionSlvMos", 
			new ConfigCriteriosConciliacionModulosDTO("LPAD(TRA.ID_TIPO_INSTITUCION, 2, '0') || TRA.FOLIO_INSTITUCION", null, 
					"LPAD(REC.ID_TIPO_INSTITUCION, 2, '0') || REC.FOLIO_INSTITUCION", null, null, null, null, "IL.ID_TIPO_INSTRUCCION", null)),
	SLV_MAV("SLV", "MAV", 9, "obtenRegistrosConciliacionSlvMav", 
			new ConfigCriteriosConciliacionModulosDTO("\"folioParticipante\"", "\"cuentaParticipante\"", null, null, "\"tv\"", "\"emisora\"", "\"serie\"", null, null)),
	MOV_AS("MOV", "AS", 10, "obtenRegistrosConciliacionMovAs", 
			new ConfigCriteriosConciliacionModulosDTO("\"folioParticipante\"", "\"cuentaParticipante\"", "\"folioContraparte\"", "\"cuentaContraparte\"", "\"tv\"", 
					"\"emisora\"", "\"serie\"", "ID_TIPO_INSTRUCCION", null)),
	MOS_AS("MOS", "AS", 11, "obtenRegistrosConciliacionMosAs", 
			new ConfigCriteriosConciliacionModulosDTO("\"folioParticipante\"", null, "\"folioContraparte\"", null, null, null, null, "ID_TIPO_INSTRUCCION", null)),
	MAV_AS("MAV", "AS", 12, "obtenRegistrosConciliacionMavAs", 
			new ConfigCriteriosConciliacionModulosDTO("\"folioParticipante\"", "\"cuentaParticipante\"", null, null, "\"tv\"", "\"emisora\"", "\"serie\"", null, null)),
	SPEI_MOS("SPEI", "MOS", 13, "obtenRegistrosConciliacionReteSiac", 
			new ConfigCriteriosConciliacionModulosDTO("\"folioParticipante\"", null, "\"folioContraparte\"", null, null, null, null, null, null));
	
	private String origen;
	private String destino;
	private int id;
	private String metodoDAO;
	private final ConfigCriteriosConciliacionModulosDTO campos;
	
	private FlujoConciliacionModulosEnum(String origen, String destino, int id, String metodoDAO, ConfigCriteriosConciliacionModulosDTO campos) {
		this.origen = origen;
		this.destino = destino;
		this.id = id;
		this.metodoDAO = metodoDAO;
		this.campos = campos;
	}

	public String getOrigen() {
		return origen;
	}

	public String getDestino() {
		return destino;
	}

	public ConfigCriteriosConciliacionModulosDTO getCampos() {
		return campos;
	}

	public int getId() {
		return id;
	}

	public String getMetodoDAO() {
		return metodoDAO;
	}

	public static FlujoConciliacionModulosEnum obtenPorId(int id) {
		for(FlujoConciliacionModulosEnum config : values()) {
			if(config.id == id) {
				return config;
			}
		}
		
		return null;
	}

}
