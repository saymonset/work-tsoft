package com.indeval.portalinternacional.middleware.servicios.vo;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.indeval.portaldali.persistence.util.DateUtils;
import com.indeval.portalinternacional.middleware.servicios.constantes.Constantes;
import com.indeval.portalinternacional.middleware.servicios.dto.BovedaDto;
import com.indeval.portalinternacional.middleware.servicios.dto.DivisaDTO;

public class MovimientoEfectivoInternacionalVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long idMovimiento;
	
	private String idMovimientoStr;
	
	private String participante;
	
	private Long idCuenta;
	
	private Integer idInstitucion;

	private String cuenta;
	
	private Long idCuentaCorresponsal;

	private String nombreInstitucion;

	private String tipoMovimiento;

	private DivisaDTO divisa = new DivisaDTO();

	private BovedaDto boveda = new BovedaDto();

	private Double saldoDisponible;

	private Double importeTraspasar;

	private Double saldoEfectivo;

	private Date fechaUltimaModificacion;

	private Date fechaAlta;
	
	private Date fechaLiquidacion;
	
	private Long estadoMovimiento;
	
	private Long estadoLiqIndeval;

	private String descEstadoMovimiento;

	private String descEstadoLiqIndeval;
	
	private String referenciaNumerica;
	
	private String referenciaRelacionada;
	
	private String notasComentarios;
	
	private String tipoMensaje;
	
	private String remittanceInfo;
	
	private String intermediaryOption;
	
	private String intermediaryValue;
	
	private String intermediaryNameAddress;
	
	private String accountOption;
	
	private String accountValue;
	
	private String accountNameAddress;
	
	private String accountLocation;
	
	private String beneficiaryOption;
	
	private String beneficiaryValue;
	
	private String beneficiaryNameAddress;
	
	private String beneficiaryNumberName;
	
	private Long folioControl;
	
	private String intermediaryBic;
	
	private String accountBic;
	
	private String beneficiaryBic;
	
	private String descripcionError;
	
	private boolean seleccionadoAutorizar;
    
    private boolean seleccionadoLiberar;
    
    private boolean seleccionadoReenviar;
    
    private boolean seleccionadoCancelar;
    
	private Long idCatbic;

	public Long getIdMovimiento() {
		return idMovimiento;
	}

	public void setIdMovimiento(Long idMovimiento) {
		this.idMovimiento = idMovimiento;
	}

	public String getIdMovimientoStr() {
		return idMovimientoStr;
	}

	public void setIdMovimientoStr(String idMovimientoStr) {
		this.idMovimientoStr = idMovimientoStr;
	}

	public void setParticipante(String participante) {
		this.participante = participante;
	}
	
	public String getParticipante() {
		return participante;
	}

	public String getCuenta() {
		return cuenta;
	}

	public void setCuenta(String cuenta) {
		this.cuenta = cuenta;
	}

	public String getNombreInstitucion() {
		return nombreInstitucion;
	}

	public void setNombreInstitucion(String nombreInstitucion) {
		this.nombreInstitucion = nombreInstitucion;
	}

	public String getTipoMovimiento() {
		return tipoMovimiento;
	}

	public void setTipoMovimiento(String tipoMovimiento) {
		this.tipoMovimiento = tipoMovimiento;
	}

	public DivisaDTO getDivisa() {
		return divisa;
	}

	public void setDivisa(DivisaDTO divisa) {
		this.divisa = divisa;
	}

	public Double getSaldoDisponible() {
		return saldoDisponible;
	}

	public void setSaldoDisponible(Double saldoDisponible) {
		this.saldoDisponible = saldoDisponible;
	}

	public Double getImporteTraspasar() {
		return importeTraspasar;
	}

	public void setImporteTraspasar(Double importeTraspasar) {
		this.importeTraspasar = importeTraspasar;
	}

	public Double getSaldoEfectivo() {
		return saldoEfectivo;
	}

	public void setSaldoEfectivo(Double saldoEfectivo) {
		this.saldoEfectivo = saldoEfectivo;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaUltimaModificacion(Date fechaUltimaModificacion) {
		this.fechaUltimaModificacion = fechaUltimaModificacion;
	}

	public Date getFechaUltimaModificacion() {
		return fechaUltimaModificacion;
	}

	public String getDescEstadoMovimiento() {
		return descEstadoMovimiento;
	}

	public void setDescEstadoMovimiento(String descEstadoMovimiento) {
		this.descEstadoMovimiento = descEstadoMovimiento;
	}

	public String getDescEstadoLiqIndeval() {
		return descEstadoLiqIndeval;
	}

	public void setDescEstadoLiqIndeval(String descEstadoLiqIndeval) {
		this.descEstadoLiqIndeval = descEstadoLiqIndeval;
	}

	public void setBoveda(BovedaDto boveda) {
		this.boveda = boveda;
	}

	public BovedaDto getBoveda() {
		return boveda;
	}
	
	public void setClaveDivisa(String claveDivisa) {
		this.divisa = this.divisa == null ? new DivisaDTO() : this.divisa;
		this.divisa.setClaveAlfabetica(claveDivisa);
	}
	
	public void setNombreCortoBoveda(String nombreCortoBoveda) {
		this.boveda = this.boveda == null ? new BovedaDto() : this.boveda;
		this.boveda.setNombreCorto(nombreCortoBoveda);
	}
	
	public Long getEstadoMovimiento() {
		return estadoMovimiento;
	}

	public void setEstadoMovimiento(Long estadoMovimiento) {
		this.estadoMovimiento = estadoMovimiento;
	}

	public Long getEstadoLiqIndeval() {
		return estadoLiqIndeval;
	}

	public void setEstadoLiqIndeval(Long estadoLiqIndeval) {
		this.estadoLiqIndeval = estadoLiqIndeval;
	}
	
	public void setIdCuenta(Long idCuenta) {
		this.idCuenta = idCuenta;
	}
	
	public Long getIdCuenta() {
		return idCuenta;
	}
	
	public void setIdCuentaCorresponsal(Long idCuentaCorresponsal) {
		this.idCuentaCorresponsal = idCuentaCorresponsal;
	}
	
	public Long getIdCuentaCorresponsal() {
		return idCuentaCorresponsal;
	}

	public Date getFechaLiquidacion() {
		return fechaLiquidacion;
	}

	public void setFechaLiquidacion(Date fechaLiquidacion) {
		this.fechaLiquidacion = fechaLiquidacion;
	}
	
	public void setNotasComentarios(String notasComentarios) {
		this.notasComentarios = notasComentarios;
	}
	
	public String getNotasComentarios() {
		return notasComentarios;
	}
	
	public String getRemittanceInfo() {
		return remittanceInfo;
	}

	public void setRemittanceInfo(String remittanceInfo) {
		this.remittanceInfo = remittanceInfo;
	}

	public void setReferenciaNumerica(String referenciaNumerica) {
		this.referenciaNumerica = referenciaNumerica;
	}
	
	public String getReferenciaNumerica() {
		return referenciaNumerica;
	}
	
	public void setReferenciaRelacionada(String referenciaRelacionada) {
		this.referenciaRelacionada = referenciaRelacionada;
	}
	
	public String getReferenciaRelacionada() {
		return referenciaRelacionada;
	}

	public String getIntermediaryValue() {
		return intermediaryValue;
	}

	public void setIntermediaryValue(String intermediaryValue) {
		this.intermediaryValue = intermediaryValue;
	}

	public String getIntermediaryNameAddress() {
		return intermediaryNameAddress;
	}

	public void setIntermediaryNameAddress(String intermediaryNameAddress) {
		this.intermediaryNameAddress = intermediaryNameAddress;
	}

	public String getAccountValue() {
		return accountValue;
	}

	public void setAccountValue(String accountValue) {
		this.accountValue = accountValue;
	}

	public String getAccountNameAddress() {
		return accountNameAddress;
	}

	public void setAccountNameAddress(String accountNameAddress) {
		this.accountNameAddress = accountNameAddress;
	}

	public String getAccountLocation() {
		return accountLocation;
	}

	public void setAccountLocation(String accountLocation) {
		this.accountLocation = accountLocation;
	}

	public String getBeneficiaryValue() {
		return beneficiaryValue;
	}

	public void setBeneficiaryValue(String beneficiaryValue) {
		this.beneficiaryValue = beneficiaryValue;
	}

	public String getBeneficiaryNameAddress() {
		return beneficiaryNameAddress;
	}

	public void setBeneficiaryNameAddress(String beneficiaryNameAddress) {
		this.beneficiaryNameAddress = beneficiaryNameAddress;
	}

	public String getBeneficiaryNumberName() {
		return beneficiaryNumberName;
	}

	public void setBeneficiaryNumberName(String beneficiaryNumberName) {
		this.beneficiaryNumberName = beneficiaryNumberName;
	}

	public boolean isSeleccionadoAutorizar() {
		return seleccionadoAutorizar;
	}

	public void setSeleccionadoAutorizar(boolean seleccionadoAutorizar) {
		this.seleccionadoAutorizar = seleccionadoAutorizar;
	}

	public boolean isSeleccionadoLiberar() {
		return seleccionadoLiberar;
	}

	public void setSeleccionadoLiberar(boolean seleccionadoLiberar) {
		this.seleccionadoLiberar = seleccionadoLiberar;
	}

	/**
	 * @return the seleccionadoReenviar
	 */
	public boolean isSeleccionadoReenviar() {
		return seleccionadoReenviar;
	}

	/**
	 * @param seleccionadoReenviar the seleccionadoReenviar to set
	 */
	public void setSeleccionadoReenviar(boolean seleccionadoReenviar) {
		this.seleccionadoReenviar = seleccionadoReenviar;
	}

	public boolean isSeleccionadoCancelar() {
		return seleccionadoCancelar;
	}

	public void setSeleccionadoCancelar(boolean seleccionadoCancelar) {
		this.seleccionadoCancelar = seleccionadoCancelar;
	}
	
	public void setIdInstitucion(Integer idInstitucion) {
		this.idInstitucion = idInstitucion;
	}
	
	public Integer getIdInstitucion() {
		return idInstitucion;
	}

	public Long getFolioControl() {
		return folioControl;
	}

	public void setFolioControl(Long folioControl) {
		this.folioControl = folioControl;
	}
	
	public void setAccountBic(String accountBic) {
		this.accountBic = accountBic;
	}
	
	public String getAccountBic() {
		return accountBic;
	}
	
	public void setBeneficiaryBic(String beneficiaryBic) {
		this.beneficiaryBic = beneficiaryBic;
	}
	
	public String getBeneficiaryBic() {
		return beneficiaryBic;
	}
	
	public String getDescripcionError() {
		return descripcionError;
	}

	public void setDescripcionError(String descripcionError) {
		this.descripcionError = descripcionError;
	}

	public void setIntermediaryBic(String intermediaryBic) {
		this.intermediaryBic = intermediaryBic;
	}
	
	public String getIntermediaryBic() {
		return intermediaryBic;
	}
	
	public void setIdCatbic(Long idCatbic) {
		this.idCatbic = idCatbic;
	}
	
	public Long getIdCatbic() {
		return idCatbic;
	}

	public String getTipoMensaje() {
		return tipoMensaje;
	}

	public void setTipoMensaje(String tipoMensaje) {
		this.tipoMensaje = tipoMensaje;
	}

	public String getIntermediaryOption() {
		return intermediaryOption;
	}

	public void setIntermediaryOption(String intermediaryOption) {
		this.intermediaryOption = intermediaryOption;
	}

	public String getAccountOption() {
		return accountOption;
	}

	public void setAccountOption(String accountOption) {
		this.accountOption = accountOption;
	}

	public String getBeneficiaryOption() {
		return beneficiaryOption;
	}

	public void setBeneficiaryOption(String beneficiaryOption) {
		this.beneficiaryOption = beneficiaryOption;
	}

	public boolean isDeposito() {
		return !StringUtils.isEmpty(this.idMovimientoStr) && Constantes.PREFIJO_ID_DEPOSITO.equals(this.idMovimientoStr.substring(0, 2));
	}
	
	public void cancela() {
		this.estadoMovimiento = Constantes.ESTADO_MOVIMIENTO_CANCELADO;
		this.estadoLiqIndeval = Constantes.ESTADO_LIQ_INDEVAL_CANCELADO;
		
		this.descEstadoMovimiento = Constantes.ESTATUS_CANCELADO;
		this.descEstadoLiqIndeval = Constantes.ESTATUS_CANCELADO;
	}

	@Override
	public String toString() {
		return "MovimientoEfectivoInternacionalVO [idMovimiento=" + idMovimiento + ", idMovimientoStr="
				+ idMovimientoStr + ", participante=" + participante + ", idCuenta=" + idCuenta + ", idInstitucion="
				+ idInstitucion + ", cuenta=" + cuenta + ", idCuentaCorresponsal=" + idCuentaCorresponsal
				+ ", nombreInstitucion=" + nombreInstitucion + ", tipoMovimiento=" + tipoMovimiento + ", divisa="
				+ divisa + ", boveda=" + boveda + ", saldoDisponible=" + saldoDisponible + ", importeTraspasar="
				+ importeTraspasar + ", saldoEfectivo=" + saldoEfectivo + ", fechaUltimaModificacion="
				+ fechaUltimaModificacion + ", fechaAlta=" + fechaAlta + ", fechaLiquidacion=" + fechaLiquidacion
				+ ", estadoMovimiento=" + estadoMovimiento + ", estadoLiqIndeval=" + estadoLiqIndeval
				+ ", descEstadoMovimiento=" + descEstadoMovimiento + ", descEstadoLiqIndeval=" + descEstadoLiqIndeval
				+ ", referenciaNumerica=" + referenciaNumerica + ", referenciaRelacionada=" + referenciaRelacionada
				+ ", notasComentarios=" + notasComentarios + ", tipoMensaje=" + tipoMensaje + ", intermediaryOption="
				+ intermediaryOption + ", intermediaryValue=" + intermediaryValue + ", intermediaryNameAddress="
				+ intermediaryNameAddress + ", accountOption=" + accountOption + ", accountValue=" + accountValue
				+ ", accountNameAddress=" + accountNameAddress + ", accountLocation=" + accountLocation
				+ ", beneficiaryOption=" + beneficiaryOption + ", beneficiaryValue=" + beneficiaryValue
				+ ", beneficiaryNameAddress=" + beneficiaryNameAddress + ", beneficiaryNumberName="
				+ beneficiaryNumberName + ", folioControl=" + folioControl + ", intermediaryBic=" + intermediaryBic
				+ ", accountBic=" + accountBic + ", beneficiaryBic=" + beneficiaryBic + ", descripcionError="
				+ descripcionError + ", seleccionadoAutorizar=" + seleccionadoAutorizar + ", seleccionadoLiberar="
				+ seleccionadoLiberar + ", seleccionadoReenviar=" + seleccionadoReenviar + ", seleccionadoCancelar="
				+ seleccionadoCancelar + ", idCatbic=" + idCatbic + "]";
	}

	public String toDetalleString() {
		DecimalFormat df = new DecimalFormat("###,##0.00");
		StringBuilder sb = new StringBuilder("[");
		sb.append("Participante=").append(this.participante).append(", ");
		sb.append("Fecha=").append(DateUtils.format(this.fechaAlta, "dd/MM/yyyy HH:mm:ss")).append(", ");
		sb.append("Tipo Movimiento=").append(this.tipoMovimiento).append(", ");
		sb.append("Boveda=").append(this.boveda.getNombreCorto()).append(", ");
		sb.append("Divisa=").append(this.divisa.getClaveAlfabetica()).append(", ");
		sb.append("Estatus Movimiento=").append(this.descEstadoMovimiento).append(", ");
		sb.append("Estatus Liquidación=").append(this.descEstadoLiqIndeval).append(", ");
		sb.append("Saldo Disponible=").append(df.format(this.saldoDisponible)).append(", ");
		sb.append("Importe=").append(df.format(this.importeTraspasar)).append(", ");
		sb.append("Saldo Actual=").append(df.format(this.saldoEfectivo)).append(", ");
		sb.append("Folio Control=").append(this.folioControl);
		if(this.idMovimientoStr != null && Constantes.PREFIJO_ID_RETIRO.equals(this.idMovimientoStr.substring(0, 2))) {
			sb.append(", ").append("Referencia Numérica=").append(this.referenciaNumerica).append(", ");
			sb.append("Referencia Relacionada=").append(this.referenciaRelacionada).append(", ");
			sb.append("Notas/Comentarios=").append(this.notasComentarios).append("] ");
		} else {
			sb.append("] ");
		}
		return sb.toString();
	}
}
