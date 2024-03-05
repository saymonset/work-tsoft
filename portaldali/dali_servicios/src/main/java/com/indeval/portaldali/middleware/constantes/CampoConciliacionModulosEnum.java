package com.indeval.portaldali.middleware.constantes;

import static com.indeval.portaldali.middleware.constantes.EstiloDatosExcel.FECHA_HORA;
import static com.indeval.portaldali.middleware.constantes.EstiloDatosExcel.MONEDA;
import static com.indeval.portaldali.middleware.constantes.EstiloDatosExcel.NUMERICO;
import static com.indeval.portaldali.middleware.constantes.EstiloDatosExcel.TEXTO;
import static com.indeval.portaldali.middleware.constantes.FlujoConciliacionModulosEnum.AE_MATCH;
import static com.indeval.portaldali.middleware.constantes.FlujoConciliacionModulosEnum.AE_MAV;
import static com.indeval.portaldali.middleware.constantes.FlujoConciliacionModulosEnum.AE_MOS;
import static com.indeval.portaldali.middleware.constantes.FlujoConciliacionModulosEnum.MATCH_MOV;
import static com.indeval.portaldali.middleware.constantes.FlujoConciliacionModulosEnum.MAV_SLV;
import static com.indeval.portaldali.middleware.constantes.FlujoConciliacionModulosEnum.MOS_SLV;
import static com.indeval.portaldali.middleware.constantes.FlujoConciliacionModulosEnum.MOV_SLV;
import static com.indeval.portaldali.middleware.constantes.FlujoConciliacionModulosEnum.SLV_MAV;
import static com.indeval.portaldali.middleware.constantes.FlujoConciliacionModulosEnum.SLV_MOS;
import static com.indeval.portaldali.middleware.constantes.FlujoConciliacionModulosEnum.SLV_MOV;
import static com.indeval.portaldali.middleware.constantes.FlujoConciliacionModulosEnum.MOV_AS;
import static com.indeval.portaldali.middleware.constantes.FlujoConciliacionModulosEnum.MOS_AS;
import static com.indeval.portaldali.middleware.constantes.FlujoConciliacionModulosEnum.MAV_AS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.type.BigDecimalType;
import org.hibernate.type.DateType;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.hibernate.type.Type;

public enum CampoConciliacionModulosEnum {

	ID_BITACORA_ADAPTADOR_ENTRADA("", "idBitacoraAdaptadorEntrada", false, true, new LongType(), null, 
			new HashSet<FlujoConciliacionModulosEnum>(Arrays.asList(new FlujoConciliacionModulosEnum[]{AE_MOS, AE_MAV, AE_MATCH}))),
	FOLIO_INSTRUCCION_LIQUIDACION("", "folioInstruccionLiquidacion", false, true, new LongType(), null, 
			new HashSet<FlujoConciliacionModulosEnum>(Arrays.asList(new FlujoConciliacionModulosEnum[]{SLV_MOS,SLV_MOV}))),
	ID_INSTRUCCION_EFECTIVO("", "idInstruccionEfectivo", false, true, new LongType(), null, 
			new HashSet<FlujoConciliacionModulosEnum>(Arrays.asList(new FlujoConciliacionModulosEnum[]{MOS_SLV, MOS_AS}))),
	ID_MODULO_ORIGEN("", "idModuloOrigen", false, true, new LongType(), null, 
			new HashSet<FlujoConciliacionModulosEnum>(Arrays.asList(new FlujoConciliacionModulosEnum[]{MOV_SLV, MOV_AS}))),
	PARTICIPANTE_SIN_NOTIFICAR("Participante sin Notificar", "participanteSinNotif", true, true, new StringType(), TEXTO,
			new HashSet<FlujoConciliacionModulosEnum>(Arrays.asList(new FlujoConciliacionModulosEnum[]{MOV_AS, MOS_AS}))),
	FOLIO_TENEDOR("Folio Tenedor", "folioContraparte", true, true, new StringType(), TEXTO, 
			new HashSet<FlujoConciliacionModulosEnum>(Arrays.asList(new FlujoConciliacionModulosEnum[]{MAV_AS}))),
	NOMBRE_TENEDOR("Tenedor", "nombreContraparte", true, false, new StringType(), TEXTO,
			new HashSet<FlujoConciliacionModulosEnum>(Arrays.asList(new FlujoConciliacionModulosEnum[]{MAV_AS}))),
	FOLIO_INSTITUCION("Folio Instituci\u00f3n", "folioParticipante", true, true, new StringType(), TEXTO,
			new HashSet<FlujoConciliacionModulosEnum>(Arrays.asList(new FlujoConciliacionModulosEnum[]{AE_MAV, MAV_SLV, SLV_MAV, MAV_AS}))),
	NOMBRE_INSTITUCION("Instituci\u00f3n", "nombreParticipante", true, false, new StringType(), TEXTO,
			new HashSet<FlujoConciliacionModulosEnum>(Arrays.asList(new FlujoConciliacionModulosEnum[]{AE_MAV, MAV_SLV, SLV_MAV, MAV_AS}))),
	CUENTA_INSTITUCION("Cuenta Instituci\u00f3n", "cuentaParticipante", true, false, new StringType(), TEXTO, 
			new HashSet<FlujoConciliacionModulosEnum>(Arrays.asList(new FlujoConciliacionModulosEnum[]{AE_MAV, MAV_SLV, SLV_MAV, MAV_AS}))),
	FOLIO_TRASPASANTE("Folio Traspasante", "folioParticipante", true, false, new StringType(), TEXTO,
			new HashSet<FlujoConciliacionModulosEnum>(Arrays.asList(new FlujoConciliacionModulosEnum[]{AE_MATCH, AE_MOS, MATCH_MOV, MOV_SLV, MOS_SLV, SLV_MOV, SLV_MOS, MOV_AS, MOS_AS}))),
	NOMBRE_TRASPASANTE("Traspasante", "nombreParticipante", true, false, new StringType(), TEXTO,
			new HashSet<FlujoConciliacionModulosEnum>(Arrays.asList(new FlujoConciliacionModulosEnum[]{AE_MATCH, AE_MOS, MOV_SLV, MOS_SLV, SLV_MOV, SLV_MOS, MOV_AS, MOS_AS}))),
	CUENTA_TRASPASANTE("Cuenta Traspasante", "cuentaParticipante", true, false, new StringType(), TEXTO, 
			new HashSet<FlujoConciliacionModulosEnum>(Arrays.asList(new FlujoConciliacionModulosEnum[]{AE_MATCH, MATCH_MOV, MOV_SLV, SLV_MOV, MOV_AS}))),
	FOLIO_RECEPTORA("Folio Receptor", "folioContraparte", true, false, new StringType(), TEXTO, 
			new HashSet<FlujoConciliacionModulosEnum>(Arrays.asList(new FlujoConciliacionModulosEnum[]{AE_MATCH, AE_MOS, MATCH_MOV, MOV_SLV, MOS_SLV, SLV_MOV, SLV_MOS, MOV_AS, MOS_AS}))),
	NOMBRE_RECEPTORA("Receptor", "nombreContraparte", true, false, new StringType(), TEXTO,
			new HashSet<FlujoConciliacionModulosEnum>(Arrays.asList(new FlujoConciliacionModulosEnum[]{AE_MATCH, AE_MOS, MOV_SLV, MOS_SLV, SLV_MOV, SLV_MOS, MOV_AS, MOS_AS}))),
	CUENTA_RECEPTORA("Cuenta Receptor", "cuentaContraparte", true, false, new StringType(), TEXTO, 
			new HashSet<FlujoConciliacionModulosEnum>(Arrays.asList(new FlujoConciliacionModulosEnum[]{AE_MATCH, MATCH_MOV, MOV_SLV, SLV_MOV, MOV_AS}))),
	FOLIO_CONTROL("Folio Control", "folioControl", true, false, new StringType(), TEXTO, 
			new HashSet<FlujoConciliacionModulosEnum>(Arrays.asList(new FlujoConciliacionModulosEnum[]{AE_MATCH, AE_MOS, MATCH_MOV, MOV_SLV, SLV_MOV, SLV_MOS, MOV_AS, MOS_AS}))),
	FOLIO_USUARIO("Folio Usuario", "folioUsuario", true, true, new StringType(), TEXTO,
			new HashSet<FlujoConciliacionModulosEnum>(Arrays.asList(new FlujoConciliacionModulosEnum[]{MATCH_MOV, MOV_SLV, SLV_MOV, MOV_AS, MOS_AS}))),
	FOLIO_INSTRUCCION("Folio Instrucci\u00f3n", "folioInstruccion", true, false, new StringType(), TEXTO, 
			new HashSet<FlujoConciliacionModulosEnum>(Arrays.asList(new FlujoConciliacionModulosEnum[]{MOS_SLV}))),
	FOLIO_PRELIQUIDADOR("Folio Preliquidador", "folioPreliquidador", true, true, new StringType(), TEXTO,
			new HashSet<FlujoConciliacionModulosEnum>(Arrays.asList(new FlujoConciliacionModulosEnum[]{MAV_SLV, SLV_MAV}))),
	REF_OPERACION("Referencia Operaci\u00f3n", "referenciaOperacion", true, true, new StringType(), TEXTO, 
			new HashSet<FlujoConciliacionModulosEnum>(Arrays.asList(new FlujoConciliacionModulosEnum[]{AE_MAV, MOS_SLV, MAV_AS}))),
	TIPO_INSTRUCCION("TI", "tipoInstruccion", true, false, new StringType(), TEXTO, 
			new HashSet<FlujoConciliacionModulosEnum>(Arrays.asList(new FlujoConciliacionModulosEnum[]{MOV_SLV, MOS_SLV, SLV_MOV, SLV_MOS, MOV_AS, MOS_AS}))),
	TIPO_OPERACION("TO", "tipoOperacion", true, true, new StringType(), TEXTO, 
			new HashSet<FlujoConciliacionModulosEnum>(Arrays.asList(new FlujoConciliacionModulosEnum[]{AE_MATCH, AE_MOS, AE_MAV, MATCH_MOV, MAV_AS}))),
	ESTADO_OPERACION("Estatus","estadoOperacion", true, true, new StringType(), TEXTO, 
			new HashSet<FlujoConciliacionModulosEnum>(Arrays.asList(new FlujoConciliacionModulosEnum[]{MATCH_MOV, MOV_SLV, MOS_SLV, MAV_SLV, SLV_MAV, SLV_MOV, SLV_MOS, MOV_AS, MOS_AS}))),
	ESTADO_OPERACION_LIQ("Estatus Liquidaci\u00f3n","estadoOperacionLiq", true, false, new StringType(), TEXTO, 
			new HashSet<FlujoConciliacionModulosEnum>(Arrays.asList(new FlujoConciliacionModulosEnum[]{SLV_MOV, SLV_MOS, SLV_MAV}))),
	TIPO_DERECHO("Tipo Derecho", "tipoDerecho", true, false, new StringType(), TEXTO, 
			new HashSet<FlujoConciliacionModulosEnum>(Arrays.asList(new FlujoConciliacionModulosEnum[]{MAV_SLV, SLV_MAV}))),
	TIPO_MENSAJE("Mensaje", "tipoMensaje", true, false, new StringType(), TEXTO, 
			new HashSet<FlujoConciliacionModulosEnum>(Arrays.asList(new FlujoConciliacionModulosEnum[]{AE_MATCH, AE_MOS, AE_MAV, MATCH_MOV, MOS_SLV, SLV_MOS, MOS_AS}))),
	TIPO_MOVIMIENTO("Tipo Movimiento", "tipoMovimiento", true, false, new StringType(), TEXTO, 
			new HashSet<FlujoConciliacionModulosEnum>(Arrays.asList(new FlujoConciliacionModulosEnum[]{MAV_SLV, SLV_MAV}))),
	TV("TV", "tv", true, false, new StringType(), TEXTO, 
			new HashSet<FlujoConciliacionModulosEnum>(Arrays.asList(new FlujoConciliacionModulosEnum[]{AE_MATCH, AE_MAV, MATCH_MOV, MOV_SLV, MAV_SLV, SLV_MAV, SLV_MOV, MOV_AS, MAV_AS}))),
	EMISORA("Emisora", "emisora", true, false, new StringType(), TEXTO, 
			new HashSet<FlujoConciliacionModulosEnum>(Arrays.asList(new FlujoConciliacionModulosEnum[]{AE_MATCH, AE_MAV, MATCH_MOV, MOV_SLV, MAV_SLV, SLV_MAV, SLV_MOV, MOV_AS, MAV_AS}))),
	SERIE("Serie", "serie", true, false, new StringType(), TEXTO, 
			new HashSet<FlujoConciliacionModulosEnum>(Arrays.asList(new FlujoConciliacionModulosEnum[]{AE_MATCH, AE_MAV, MATCH_MOV, MOV_SLV, MAV_SLV, SLV_MAV, SLV_MOV, MOV_AS, MAV_AS}))),
	CUPON("Cup\u00f3n", "cupon", true, false, new StringType(), TEXTO, 
			new HashSet<FlujoConciliacionModulosEnum>(Arrays.asList(new FlujoConciliacionModulosEnum[]{AE_MATCH, AE_MAV, MATCH_MOV, MOV_SLV, MAV_SLV, SLV_MAV, SLV_MOV, MOV_AS, MAV_AS}))),	
	FECHA_REGISTRO("Fecha Registro", "fechaRegistro", true, false, new DateType(), FECHA_HORA, 
			new HashSet<FlujoConciliacionModulosEnum>(Arrays.asList(new FlujoConciliacionModulosEnum[]{AE_MATCH, AE_MOS, AE_MAV, MATCH_MOV, MOS_SLV, MOV_SLV, MOV_AS, MOS_AS, MAV_AS}))),
	FECHA_LIQUIDACION("Fecha Liquidaci\u00f3n", "fechaLiquidacion", true, false, new DateType(), FECHA_HORA, 
			new HashSet<FlujoConciliacionModulosEnum>(Arrays.asList(new FlujoConciliacionModulosEnum[]{AE_MAV, MOS_SLV, SLV_MOV, SLV_MOS, SLV_MAV, MOS_AS}))),
	PRECIO_TITULO("Precio T\u00edtulo", "precioTitulo", true, false, new BigDecimalType(), MONEDA,
			new HashSet<FlujoConciliacionModulosEnum>(Arrays.asList(new FlujoConciliacionModulosEnum[]{MATCH_MOV, MOV_SLV, SLV_MOV, MOV_AS}))),
	MONTO("Monto Operado", "importe", true, false, new BigDecimalType(), MONEDA,
			new HashSet<FlujoConciliacionModulosEnum>(Arrays.asList(new FlujoConciliacionModulosEnum[]{AE_MATCH, MATCH_MOV, MOV_SLV, MAV_SLV, SLV_MAV, SLV_MOV, MOV_AS}))),
	IMPORTE("Importe", "importe", true, false, new BigDecimalType(), MONEDA,
			new HashSet<FlujoConciliacionModulosEnum>(Arrays.asList(new FlujoConciliacionModulosEnum[]{AE_MOS, MOS_SLV, SLV_MOS, MOS_AS}))),
	DIVISA("Divisa", "divisa", true, false, new StringType(), TEXTO, 
			new HashSet<FlujoConciliacionModulosEnum>(Arrays.asList(new FlujoConciliacionModulosEnum[]{AE_MATCH, AE_MOS, MATCH_MOV, MOV_SLV, MOS_SLV, SLV_MOV, SLV_MOS, MOV_AS, MOS_AS}))),
	CANTIDAD_TITULOS("Cantidad Operada", "cantidadTitulos", true, false, new LongType(), NUMERICO,
			new HashSet<FlujoConciliacionModulosEnum>(Arrays.asList(new FlujoConciliacionModulosEnum[]{AE_MATCH, AE_MAV, MATCH_MOV, MOV_SLV, MAV_SLV, SLV_MAV, SLV_MOV, MOV_AS, MAV_AS}))),
	FOLIO_BANCO_TRABAJO("Folio Banco Trabajo", "folioBancoTrabajo", true, false, new StringType(), TEXTO, 
			new HashSet<FlujoConciliacionModulosEnum>(Arrays.asList(new FlujoConciliacionModulosEnum[]{MOV_SLV}))),
	NOMBRE_BANCO_TRABAJO("Banco Trabajo", "nombreBancoTrabajo", true, false, new StringType(), TEXTO,
			new HashSet<FlujoConciliacionModulosEnum>(Arrays.asList(new FlujoConciliacionModulosEnum[]{MOV_SLV}))),
	CUENTA_BANCO_TRABAJO("Cuenta Banco Trabajo", "cuentaBancoTrabajo", true, false, new StringType(), TEXTO, 
			new HashSet<FlujoConciliacionModulosEnum>(Arrays.asList(new FlujoConciliacionModulosEnum[]{MOV_SLV})));
	
	private String descripcion;
	private String propiedadDto;
	private boolean incluirExcel;
	private boolean incluirXml;
	private Type tipoHibernate;
	private EstiloDatosExcel estiloExcel;
	private Set<FlujoConciliacionModulosEnum> flujos;
	
	private CampoConciliacionModulosEnum(String descripcion, String propiedadDto, boolean incluirExcel, boolean incluirXml, Type tipoHibernate,
			EstiloDatosExcel estiloExcel, Set<FlujoConciliacionModulosEnum> flujos) {
		this.descripcion = descripcion;
		this.propiedadDto = propiedadDto;
		this.incluirExcel = incluirExcel;
		this.incluirXml = incluirXml;
		this.tipoHibernate = tipoHibernate;
		this.estiloExcel = estiloExcel;
		this.flujos = flujos;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public String getPropiedadDto() {
		return propiedadDto;
	}

	public boolean isIncluirExcel() {
		return incluirExcel;
	}

	public boolean isIncluirXml() {
		return incluirXml;
	}

	public Type getTipoHibernate() {
		return tipoHibernate;
	}

	public EstiloDatosExcel getEstiloExcel() {
		return estiloExcel;
	}

	public Set<FlujoConciliacionModulosEnum> getFlujos() {
		return flujos;
	}
	
	public static List<CampoConciliacionModulosEnum> getCamposPorFlujo(FlujoConciliacionModulosEnum flujo) {
		List<CampoConciliacionModulosEnum> campos = new ArrayList<>();
		
		for(CampoConciliacionModulosEnum campo : values()) {
			if(campo.getFlujos().contains(flujo)) {
				campos.add(campo);
			}
		}
		
		return campos;
	}
	
	public static List<CampoConciliacionModulosEnum> getCamposPorFlujoExcel(FlujoConciliacionModulosEnum flujo) {
		List<CampoConciliacionModulosEnum> campos = new ArrayList<>();
		
		for(CampoConciliacionModulosEnum campo : values()) {
			if(campo.getFlujos().contains(flujo) && campo.isIncluirExcel()) {
				campos.add(campo);
			}
		}
		
		return campos;
	}
	
	public static List<CampoConciliacionModulosEnum> getCamposXMLPorFlujo(FlujoConciliacionModulosEnum flujo) {
		List<CampoConciliacionModulosEnum> campos = new ArrayList<>();
		
		for(CampoConciliacionModulosEnum campo : values()) {
			if(campo.getFlujos().contains(flujo) && campo.isIncluirXml()) {
				campos.add(campo);
			}
		}
		
		return campos;
	}
	
}
