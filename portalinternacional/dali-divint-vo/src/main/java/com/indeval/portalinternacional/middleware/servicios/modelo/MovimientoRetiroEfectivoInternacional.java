// Cambio Multidivisas
package com.indeval.portalinternacional.middleware.servicios.modelo;

import com.indeval.portaldali.persistence.modelo.CuentaNombrada;
import com.indeval.portalinternacional.middleware.servicios.vo.MovimientoEfectivoInternacionalVO;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author Enrique Guzman
 *
 */
@Entity
@Table(name = "T_MOVIMIENTO_RETIRO_EFECTIVO_INT")
@SequenceGenerator(name = "foliador", sequenceName = "SEQ_MOV_RETIRO_EFECT_INT", allocationSize = 1)
public class MovimientoRetiroEfectivoInternacional implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "foliador")
	@Column(name = "ID_MOVIMIENTO_RETIRO_EFECTIVO_INT")
	private Long idMovimientoEfectivoInt;

	@Column(name = "ID_CUENTA")
	private Long idCuenta;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_CUENTA", insertable = false, updatable = false)
	private CuentaNombrada cuenta;

	@Column(name = "ID_DIVISA")
	private Long idDivisa;

	@Column(name = "ID_BOVEDA")
	private Long idBoveda;

	@Column(name = "SALDO_DISPONIBLE")
	private Double saldoDisponible;

	@Column(name = "IMPORTE_TRASPASAR")
	private Double importeTraspasar;

	@Column(name = "SALDO_EFECTIVO")
	private Double saldoEfectivo;

	@Column(name = "FECHA_ALTA")
	private Date fechaAlta;

	@Column(name = "FECHA_ULT_MODIFICACION")
	private Date fechaUltimaModificacion;
	
	@Column(name = "FECHA_LIQUIDACION")
	private Date fechaLiquidacion;

	@Column(name = "ESTADO_MOV_EFECTIVO_INT")
	private Long estadoMovEfectivoInt;

	@Column(name = "ESTADO_LIQ_INDEVAL")
	private Long estadoLiqIndeval;
	
	@Column(name = "REFERENCIA_NUMERICA")
	private String referenciaNumerica;
	
	@Column(name = "REFERENCIA_RELACIONADA")
	private String referenciaRelacionada;
	
	@Column(name = "NOTAS_COMENTARIOS")
	private String notasComentarios;
	
	@Column(name = "REMITTANCE_INFO")
	private String remittanceInfo;
	
	@Column(name = "INTERMEDIARY_OPTION")
	private String intermediaryOption;
	
	@Column(name = "INTERMEDIARY_PARTY_ID")
	private String intermediaryPartyId;
	
	@Column(name = "INTERMEDIARY_NAME_ADDRESS")
	private String intermediaryNameAddress;
	
	@Column(name = "INTERMEDIARY_BIC")
	private String intermediaryBic;
	
	@Column(name = "ACCOUNT_OPTION")
	private String accountOption;
	
	@Column(name = "ACCOUNT_PARTY_ID")
	private String accountPartyId;
	
	@Column(name = "ACCOUNT_NAME_ADDRESS")
	private String accountNameAddress;
	
	@Column(name = "ACCOUNT_LOCATION")
	private String accountLocation;
	
	@Column(name = "ACCOUNT_BIC")
	private String accountBic;
	
	@Column(name = "BENEFICIARY_OPTION")
	private String beneficiaryOption;
	
	@Column(name = "BENEFICIARY_PARTY_ID")
	private String beneficiaryPartyId;
	
	@Column(name = "BENEFICIARY_ACCOUNT")
	private String beneficiaryAccount;
	
	@Column(name = "BENEFICIARY_NAME_ADDRESS")
	private String beneficiaryNameAddress;
	
	@Column(name = "BENEFICIARY_NUMBER_NAME")
	private String beneficiaryNumberName;

	@Column(name = "TIPO_MENSAJE")
	private String tipoMensajee;
	
	@Column(name = "BENEFICIARY_BIC")
	private String beneficiaryBic;
	
	@Column(name = "FOLIO_CONTROL")
	private Long folioControl;

	@Column(name = "ID_CATBIC")
	private Long idCatbic;
	
	public Long getIdMovimientoEfectivoInt() {
		return idMovimientoEfectivoInt;
	}

	public void setIdMovimientoEfectivoInt(Long idMovimientoEfectivoInt) {
		this.idMovimientoEfectivoInt = idMovimientoEfectivoInt;
	}

	public Long getIdCuenta() {
		return idCuenta;
	}

	public void setIdCuenta(Long idCuenta) {
		this.idCuenta = idCuenta;
	}

	public CuentaNombrada getCuenta() {
		return cuenta;
	}

	public void setCuenta(CuentaNombrada cuenta) {
		this.cuenta = cuenta;
	}

	public Long getIdDivisa() {
		return idDivisa;
	}

	public void setIdDivisa(Long idDivisa) {
		this.idDivisa = idDivisa;
	}

	public Long getIdBoveda() {
		return idBoveda;
	}

	public void setIdBoveda(Long idBoveda) {
		this.idBoveda = idBoveda;
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

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public Date getFechaUltimaModificacion() {
		return fechaUltimaModificacion;
	}

	public void setFechaUltimaModificacion(Date fechaUltimaModificacion) {
		this.fechaUltimaModificacion = fechaUltimaModificacion;
	}

	public Date getFechaLiquidacion() {
		return fechaLiquidacion;
	}

	public void setFechaLiquidacion(Date fechaLiquidacion) {
		this.fechaLiquidacion = fechaLiquidacion;
	}

	public Long getEstadoMovEfectivoInt() {
		return estadoMovEfectivoInt;
	}

	public void setEstadoMovEfectivoInt(Long estadoMovEfectivoInt) {
		this.estadoMovEfectivoInt = estadoMovEfectivoInt;
	}

	public Long getEstadoLiqIndeval() {
		return estadoLiqIndeval;
	}

	public void setEstadoLiqIndeval(Long estadoLiqIndeval) {
		this.estadoLiqIndeval = estadoLiqIndeval;
	}

	public String getReferenciaNumerica() {
		return referenciaNumerica;
	}

	public void setReferenciaNumerica(String referenciaNumerica) {
		this.referenciaNumerica = referenciaNumerica;
	}

	public String getReferenciaRelacionada() {
		return referenciaRelacionada;
	}

	public void setReferenciaRelacionada(String referenciaRelacionada) {
		this.referenciaRelacionada = referenciaRelacionada;
	}

	public String getNotasComentarios() {
		return notasComentarios;
	}

	public void setNotasComentarios(String notasComentarios) {
		this.notasComentarios = notasComentarios;
	}

	public String getRemittanceInfo() {
		return remittanceInfo;
	}

	public void setRemittanceInfo(String remittanceInfo) {
		this.remittanceInfo = remittanceInfo;
	}

	public String getIntermediaryOption() {
		return intermediaryOption;
	}

	public void setIntermediaryOption(String intermediaryOption) {
		this.intermediaryOption = intermediaryOption;
	}

	public String getIntermediaryPartyId() {
		return intermediaryPartyId;
	}

	public void setIntermediaryPartyId(String intermediaryPartyId) {
		this.intermediaryPartyId = intermediaryPartyId;
	}

	public String getIntermediaryNameAddress() {
		return intermediaryNameAddress;
	}

	public void setIntermediaryNameAddress(String intermediaryNameAddress) {
		this.intermediaryNameAddress = intermediaryNameAddress;
	}

	public String getIntermediaryBic() {
		return intermediaryBic;
	}

	public void setIntermediaryBic(String intermediaryBic) {
		this.intermediaryBic = intermediaryBic;
	}

	public String getAccountOption() {
		return accountOption;
	}

	public void setAccountOption(String accountOption) {
		this.accountOption = accountOption;
	}

	public String getAccountPartyId() {
		return accountPartyId;
	}

	public void setAccountPartyId(String accountPartyId) {
		this.accountPartyId = accountPartyId;
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

	public String getAccountBic() {
		return accountBic;
	}

	public void setAccountBic(String accountBic) {
		this.accountBic = accountBic;
	}

	public String getBeneficiaryOption() {
		return beneficiaryOption;
	}

	public void setBeneficiaryOption(String beneficiaryOption) {
		this.beneficiaryOption = beneficiaryOption;
	}

	public String getBeneficiaryPartyId() {
		return beneficiaryPartyId;
	}

	public void setBeneficiaryPartyId(String beneficiaryPartyId) {
		this.beneficiaryPartyId = beneficiaryPartyId;
	}

	public String getBeneficiaryAccount() {
		return beneficiaryAccount;
	}

	public void setBeneficiaryAccount(String beneficiaryAccount) {
		this.beneficiaryAccount = beneficiaryAccount;
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

	public String getBeneficiaryBic() {
		return beneficiaryBic;
	}

	public void setBeneficiaryBic(String beneficiaryBic) {
		this.beneficiaryBic = beneficiaryBic;
	}

	public Long getFolioControl() {
		return folioControl;
	}

	public void setFolioControl(Long folioControl) {
		this.folioControl = folioControl;
	}

	public Long getIdCatbic() {
		return idCatbic;
	}

	public void setIdCatbic(Long idCatbic) {
		this.idCatbic = idCatbic;
	}

	public String getTipoMensajee() {
		return tipoMensajee;
	}

	public void setTipoMensajee(String tipoMensajee) {
		this.tipoMensajee = tipoMensajee;
	}

	public void actualizaMovimientoEfectivo(MovimientoEfectivoInternacionalVO vo) {
		this.estadoMovEfectivoInt = vo.getEstadoMovimiento();
		this.estadoLiqIndeval = vo.getEstadoLiqIndeval();		
	}

}
