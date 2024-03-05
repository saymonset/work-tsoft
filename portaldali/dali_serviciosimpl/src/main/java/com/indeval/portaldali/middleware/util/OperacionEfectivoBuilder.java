package com.indeval.portaldali.middleware.util;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.hibernate.lob.ClobImpl;

import com.indeval.portaldali.middleware.dto.BovedaDTO;
import com.indeval.portaldali.middleware.dto.DivisaDTO;
import com.indeval.portaldali.middleware.dto.EstadoInstruccionCatDTO;
import com.indeval.portaldali.middleware.dto.InstitucionDTO;
import com.indeval.portaldali.middleware.dto.RetiroEfectivoDTO;
import com.indeval.portaldali.middleware.dto.TipoInstruccionDTO;

public class OperacionEfectivoBuilder {
	private Long idOperacionEfectivo; 
	
	private Integer idTipoInstruccion;
	private String tipoOperacion; // TREF, SPEI, SIAC, RCCS
	
	private String origen;
	private Integer idEstadoInstruccion;
	private String referenciaOperacion;
	private String referenciaMensaje;
	
	
	private Long idInstitucionEmisor;
	private Long idInstitucionReceptor;
	private String cuentaEmisor;
	private String cuentaReceptor;
	
	private String referenciaNumerica;
	private String conceptoPago;
	
	private Long idDivisa;
	private Long idBoveda;
	private BigDecimal importe;
	private String datosAdicionales;
	
	private Date fechaRegistro;
	private String usuarioRegistro;
	private String serieRegistro;
	private String firmaRegistro;
	
	private OperacionEfectivoBuilder(String tipoOperacion) {
		this.tipoOperacion = tipoOperacion;
	}
	
	public static OperacionEfectivoBuilder newInstance(String tipoOperacion) {
		if(StringUtils.isBlank(tipoOperacion))throw new IllegalArgumentException("tipoOperacion is required.");
		return new  OperacionEfectivoBuilder(tipoOperacion);
	}
	
	public OperacionEfectivoBuilder withIdOperacionEfectivo(long idOperacionEfectivo) {
		this.idOperacionEfectivo = idOperacionEfectivo;
		return this;
	}
	
	public OperacionEfectivoBuilder withIdTipoInstruccion(int idTipoInstruccion) {
		this.idTipoInstruccion = idTipoInstruccion;
		return this;
	}
	
	public OperacionEfectivoBuilder withTipoOperacion(String tipoOperacion) {
		this.tipoOperacion = tipoOperacion;
		return this;
	}
	
	public OperacionEfectivoBuilder withIdEstado(int idEstadoInstruccion) {
		this.idEstadoInstruccion = idEstadoInstruccion;
		return this;
	}
	
	public OperacionEfectivoBuilder withReferenciaOperacion(String referenciaOperacion) {
		this.referenciaOperacion = referenciaOperacion;
		return this;
	}
	
	public OperacionEfectivoBuilder withReferenciaMensaje(String referenciaMensaje) {
		this.referenciaMensaje = referenciaMensaje;
		return this;
	}
	
	public OperacionEfectivoBuilder withIdBoveda(long idBoveda) {
		this.idBoveda = idBoveda;
		return this;
	}
	
	public OperacionEfectivoBuilder withIdInstitucionEmisor(long idInstitucionEmisor) {
		this.idInstitucionEmisor = idInstitucionEmisor;
		return this;
	}
	
	public OperacionEfectivoBuilder withIdInstitucionReceptor(long idInstitucionReceptor) {
		this.idInstitucionReceptor = idInstitucionReceptor;
		return this;
	}
	
	public OperacionEfectivoBuilder withCuentaEmisor(String cuentaEmisor) {
		this.cuentaEmisor = cuentaEmisor;
		return this;
	}
	
	public OperacionEfectivoBuilder withCuentaReceptor(String cuentaReceptor) {
		this.cuentaReceptor = cuentaReceptor;
		return this;
	}
	
	public OperacionEfectivoBuilder withReferenciaNumerica(String referenciaNumerica) {
		this.referenciaNumerica = referenciaNumerica;
		return this;
	}
	
	public OperacionEfectivoBuilder withConceptoPago(String conceptoPago) {
		this.conceptoPago = conceptoPago;
		return this;
	}
	
	public OperacionEfectivoBuilder withIdDivisa(long idDivisa) {
		this.idDivisa = idDivisa;
		return this;
	}
	
	public OperacionEfectivoBuilder withImporte(BigDecimal importe) {
		this.importe = importe;
		return this;
	}
	
	public OperacionEfectivoBuilder withFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
		return this;
	}
	
	public OperacionEfectivoBuilder withUsuarioRegistro(String usuarioRegistro) {
		this.usuarioRegistro = usuarioRegistro;
		return this;
	}
	
	public OperacionEfectivoBuilder withSerieRegistro(String serieRegistro) {
		this.serieRegistro = serieRegistro;
		return this;
	}
	
	public OperacionEfectivoBuilder withFirmaRegistro(String firmaRegistro) {
		this.firmaRegistro = firmaRegistro;
		return this;
	}
	public OperacionEfectivoBuilder withOrigen(String origen) {
		this.origen = origen;
		return this;
	}
	public OperacionEfectivoBuilder withDatosAdicionales(String datosAdicionales) {
		this.datosAdicionales = datosAdicionales;
		return this;
	}
	public RetiroEfectivoDTO build() {
		RetiroEfectivoDTO efectivo = new RetiroEfectivoDTO();
		
		// ID
		efectivo.setIdRetiroEfectivoNal(this.idOperacionEfectivo);
		// ID INSTITUCION EMISOR
		if(this.idInstitucionEmisor != null)
			efectivo.setInstitucion(new InstitucionDTO( this.idInstitucionEmisor));
		// ID INSTITUCION RECEPTOR
		if(this.idInstitucionReceptor != null)
			efectivo.setIdInstReceptor(new InstitucionDTO(this.idInstitucionReceptor));
		// ID TIPO INSTRUCCION
		if(this.idTipoInstruccion != null)
			efectivo.setTipoInstruccion(new TipoInstruccionDTO(this.idTipoInstruccion));
		// ID ESTADO INSTRUCCION
		if(this.idEstadoInstruccion != null)
			efectivo.setEstado(new EstadoInstruccionCatDTO(this.idEstadoInstruccion));
		// ID DIVISA
		if(this.idDivisa != null)
			efectivo.setDivisa(new DivisaDTO(this.idDivisa));
		// ID CUENTA
		if(this.idBoveda != null)
			efectivo.setBoveda(new BovedaDTO(this.idBoveda));
		// CUENTA EMISOR
		efectivo.setCuentaEmisor(this.cuentaEmisor);
		// CUENTA RECEPTOR
		efectivo.setCuentaBeneficiario(this.cuentaReceptor);
		// IMPORTE TRASPASO
		efectivo.setImporteTraspaso(this.importe);
		// TIPO OPERACION 
		efectivo.setTipoOperacion(this.tipoOperacion);
		// ORIGEN
		efectivo.setOrigen(this.origen);
		// REFERENCIA OPERACION
		efectivo.setReferenciaOperacion(this.referenciaOperacion);
		// REFERENCIA MENSAJE
		efectivo.setReferenciaMensaje(this.referenciaMensaje);
		// REFERENCIA NUMERICA
		efectivo.setReferencia(this.referenciaNumerica);
		// CONCEPTO PAGO
		efectivo.setConceptoPago(this.conceptoPago);
		// DATOS ADICIONALES
		if(this.datosAdicionales != null)
			efectivo.setDatosAdicionales(new ClobImpl(this.datosAdicionales));
		// FECHA REGISTRO
		efectivo.setFechaCreacion(this.fechaRegistro);
		// USUARIO REGISTRO 
		efectivo.setUsuarioCreacion(this.usuarioRegistro);
		// SERIE REGISTRO
		efectivo.setSerieCreacion(this.serieRegistro);
		// FIRMA REGISTRO
		if(this.firmaRegistro != null)
			efectivo.setCreacionFirmada(new ClobImpl(this.firmaRegistro));
		
		return efectivo;
	}
}
