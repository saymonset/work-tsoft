package com.indeval.portalinternacional.middleware.servicios.dto;

import java.io.Serializable;
import java.util.Date;

import com.indeval.portalinternacional.middleware.servicios.modelo.SettlementDisciplineRegime;

public class SettlementDisciplineRegimeVO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long idConfigCsdr;
	private Long idCuentaBoveda;
	private Long cuenta;
	private String detalleCustodio;
	private Integer holdAndRelease;
	private Integer partialSettlement;
	private Integer bilateralCancellation;
	private Boolean isEditar;
	private Boolean isEliminar;
	private String tipoConfiguracion;
	
	/** Configuracion para Institucion **/
	private Long idInstitucion;
	private String folioInstitucion;
	private String nombreCortoInstitucion;
	
	/**
	 * 
	 */
	public SettlementDisciplineRegimeVO() {
		super();
	}

	/**
	 * @param idCuentaBoveda
	 * @param detalleCustodio
	 */
	public SettlementDisciplineRegimeVO(Long idCuentaBoveda, String detalleCustodio) {
		super();
		this.idCuentaBoveda = idCuentaBoveda;
		this.detalleCustodio = detalleCustodio;
	}

	/**
	 * @param idCuentaBoveda
	 * @param cuenta
	 * @param detalleCustodio
	 */
	public SettlementDisciplineRegimeVO(Long idCuentaBoveda, Long cuenta, String detalleCustodio) {
		super();
		this.idCuentaBoveda = idCuentaBoveda;
		this.cuenta = cuenta;
		this.detalleCustodio = detalleCustodio;
	}

	/**
	 * @param idInstitucion
	 * @param folioInstitucion
	 * @param nombreCortoInstitucion
	 */
	public SettlementDisciplineRegimeVO(Long idInstitucion, String folioInstitucion, String nombreCortoInstitucion) {
		super();
		this.idInstitucion = idInstitucion;
		this.folioInstitucion = folioInstitucion;
		this.nombreCortoInstitucion = nombreCortoInstitucion;
	}

	/**
	 * @param idConfigCsdr
	 * @param idCuentaBoveda
	 * @param holdAndRelease
	 * @param partialSettlement
	 * @param bilateralCancellation
	 */
	public SettlementDisciplineRegimeVO(Long idConfigCsdr, Long idCuentaBoveda, Long cuenta, String detalleCustodio,
			Integer holdAndRelease, Integer partialSettlement, Integer bilateralCancellation) {
		super();
		this.idConfigCsdr = idConfigCsdr;
		this.idCuentaBoveda = idCuentaBoveda;
		this.cuenta = cuenta;
		this.detalleCustodio = detalleCustodio;
		this.holdAndRelease = holdAndRelease;
		this.partialSettlement = partialSettlement;
		this.bilateralCancellation = bilateralCancellation;
		this.isEditar = Boolean.FALSE;
		this.isEliminar = Boolean.FALSE;
	}
	
	/**
	 * @param idConfigCsdr
	 * @param idCuentaBoveda
	 * @param holdAndRelease
	 * @param partialSettlement
	 * @param bilateralCancellation
	 */
	public SettlementDisciplineRegimeVO(Long idConfigCsdr, Long idInstitucion, String folioInstitucion, String nombreCortoInstitucion,
			Integer holdAndRelease, Integer partialSettlement, Integer bilateralCancellation) {
		super();
		this.idConfigCsdr = idConfigCsdr;
		this.idInstitucion = idInstitucion;
		this.folioInstitucion = folioInstitucion;
		this.nombreCortoInstitucion = nombreCortoInstitucion;
		this.holdAndRelease = holdAndRelease;
		this.partialSettlement = partialSettlement;
		this.bilateralCancellation = bilateralCancellation;
		this.isEditar = Boolean.FALSE;
	}
	
	
	
	/**
	 * @return the idConfigCsdr
	 */
	public Long getIdConfigCsdr() {
		return idConfigCsdr;
	}
	/**
	 * @param idConfigCsdr the idConfigCsdr to set
	 */
	public void setIdConfigCsdr(Long idConfigCsdr) {
		this.idConfigCsdr = idConfigCsdr;
	}
	/**
	 * @return the idCuentaBoveda
	 */
	public Long getIdCuentaBoveda() {
		return idCuentaBoveda;
	}

	/**
	 * @param idCuentaBoveda the idCuentaBoveda to set
	 */
	public void setIdCuentaBoveda(Long idCuentaBoveda) {
		this.idCuentaBoveda = idCuentaBoveda;
	}
	/**
	 * @return the holdAndRelease
	 */
	public Integer getHoldAndRelease() {
		return holdAndRelease;
	}
	/**
	 * @param holdAndRelease the holdAndRelease to set
	 */
	public void setHoldAndRelease(Integer holdAndRelease) {
		this.holdAndRelease = holdAndRelease;
	}
	/**
	 * @return the partialSettlement
	 */
	public Integer getPartialSettlement() {
		return partialSettlement;
	}
	/**
	 * @param partialSettlement the partialSettlement to set
	 */
	public void setPartialSettlement(Integer partialSettlement) {
		this.partialSettlement = partialSettlement;
	}

	/**
	 * @return the bilateralCancellation
	 */
	public Integer getBilateralCancellation() {
		return bilateralCancellation;
	}

	/**
	 * @param bilateralCancellation the bilateralCancellation to set
	 */
	public void setBilateralCancellation(Integer bilateralCancellation) {
		this.bilateralCancellation = bilateralCancellation;
	}

	/**
	 * @return the isEditar
	 */
	public Boolean getIsEditar() {
		return isEditar;
	}

	/**
	 * @param isEditar the isEditar to set
	 */
	public void setIsEditar(Boolean isEditar) {
		this.isEditar = isEditar;
	}

	/**
	 * @return the detalleCustodio
	 */
	public String getDetalleCustodio() {
		return detalleCustodio;
	}

	/**
	 * @param detalleCustodio the detalleCustodio to set
	 */
	public void setDetalleCustodio(String detalleCustodio) {
		this.detalleCustodio = detalleCustodio;
	}

	/**
	 * @return the isEliminar
	 */
	public Boolean getIsEliminar() {
		return isEliminar;
	}

	/**
	 * @param isEliminar the isEliminar to set
	 */
	public void setIsEliminar(Boolean isEliminar) {
		this.isEliminar = isEliminar;
	}

	/**
	 * @return the cuenta
	 */
	public Long getCuenta() {
		return cuenta;
	}

	/**
	 * @param cuenta the cuenta to set
	 */
	public void setCuenta(Long cuenta) {
		this.cuenta = cuenta;
	}
	
	/**
	 * @return the idInstitucion
	 */
	public Long getIdInstitucion() {
		return idInstitucion;
	}

	/**
	 * @param idInstitucion the idInstitucion to set
	 */
	public void setIdInstitucion(Long idInstitucion) {
		this.idInstitucion = idInstitucion;
	}

	/**
	 * @return the folioInstitucion
	 */
	public String getFolioInstitucion() {
		return folioInstitucion;
	}

	/**
	 * @param folioInstitucion the folioInstitucion to set
	 */
	public void setFolioInstitucion(String folioInstitucion) {
		this.folioInstitucion = folioInstitucion;
	}
	
	/**
	 * @return the nombreCortoInstitucion
	 */
	public String getNombreCortoInstitucion() {
		return nombreCortoInstitucion;
	}

	/**
	 * @param nombreCortoInstitucion the nombreCortoInstitucion to set
	 */
	public void setNombreCortoInstitucion(String nombreCortoInstitucion) {
		this.nombreCortoInstitucion = nombreCortoInstitucion;
	}

	/**
	 * @return the tipoConfiguracion
	 */
	public String getTipoConfiguracion() {
		return tipoConfiguracion;
	}

	/**
	 * @param tipoConfiguracion the tipoConfiguracion to set
	 */
	public void setTipoConfiguracion(String tipoConfiguracion) {
		this.tipoConfiguracion = tipoConfiguracion;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SettlementDisciplineRegimeVO [idConfigCsdr=" + idConfigCsdr + ", idCuentaBoveda=" + idCuentaBoveda
				+ ", cuenta=" + cuenta + ", detalleCustodio=" + detalleCustodio + ", holdAndRelease=" + holdAndRelease
				+ ", partialSettlement=" + partialSettlement + ", bilateralCancellation=" + bilateralCancellation
				+ ", isEditar=" + isEditar + ", isEliminar=" + isEliminar + ", idInstitucion=" + idInstitucion + "]";
	}

	public String getDescripcionCamposLlave() {
		StringBuilder descripcion = new StringBuilder("[");
//		descripcion.append("Cuenta Nombrada = ").append(idCuentaBoveda).append(", ");
		if(this.folioInstitucion != null){
			descripcion.append("Folio = ").append(folioInstitucion).append(", ");
			descripcion.append("Institución = ").append(nombreCortoInstitucion).append(", ");			
		} else {
			descripcion.append("Cuenta = ").append(cuenta).append(", ");
			descripcion.append("Detalle Custodio = ").append(detalleCustodio).append(", ");
		}
		descripcion.append("Hold&Release = ").append((holdAndRelease == 1) ? "SI" : "NO").append(", ");
		descripcion.append("Partial Settlement = ").append((partialSettlement == 1) ? "SI" : "NO").append(", ");
		descripcion.append("Bilateral Cancellation = ").append((bilateralCancellation == 1) ? "SI" : "NO").append("]");
		return descripcion.toString();
	}
	
	public String getDescripcionCamposModificar(SettlementDisciplineRegimeVO settDisciplineRegimeVOAnterior) {
		StringBuilder descripcion = new StringBuilder("[");
//		descripcion.append("Cuenta Nombrada = ").append(idCuentaBoveda).append(", ");
//		descripcion.append("Cuenta = ").append(cuenta).append(", ");
//		descripcion.append("Detalle Custodio = ").append(detalleCustodio);
		
		if(settDisciplineRegimeVOAnterior.getHoldAndRelease() != null && this.holdAndRelease != null
				&& !settDisciplineRegimeVOAnterior.getHoldAndRelease().equals(this.holdAndRelease)){
			descripcion.append("Hold&Release = ").append((this.holdAndRelease == 1) ? "SI" : "NO").append(" ");
		}
		
		if(settDisciplineRegimeVOAnterior.getPartialSettlement() != null && this.partialSettlement != null
				&& !settDisciplineRegimeVOAnterior.getPartialSettlement().equals(this.partialSettlement)){
			descripcion.append("Partial Settlement = ").append((partialSettlement == 1) ? "SI" : "NO").append(" ");
		}
		
		if(settDisciplineRegimeVOAnterior.getBilateralCancellation() != null && this.bilateralCancellation != null
				&& !settDisciplineRegimeVOAnterior.getBilateralCancellation().equals(this.bilateralCancellation)){
			descripcion.append("Bilateral Cancellation = ").append((bilateralCancellation == 1) ? "SI" : "NO");
		}
		
		descripcion.append("]");
		
		return descripcion.toString();
	}
	
	public String getDescripcionEliminar() {
		StringBuilder descripcion = new StringBuilder("[");
//		descripcion.append("Cuenta Nombrada = ").append(idCuentaBoveda).append(", ");
		if(this.folioInstitucion != null){
			descripcion.append("Folio = ").append(folioInstitucion).append(", ");
			descripcion.append("Institución = ").append(nombreCortoInstitucion).append("]");		
		} else {
			descripcion.append("Cuenta = ").append(cuenta).append(", ");
			descripcion.append("Detalle Custodio = ").append(detalleCustodio).append("]");
		}
		return descripcion.toString();
	}
	
	public static SettlementDisciplineRegime transformSettlementDisciplineRegimeVoToModel(SettlementDisciplineRegimeVO settlementDisciplineRegime, Boolean esActualizacion){
		SettlementDisciplineRegime settleDiscRegModel = null;
		if(settlementDisciplineRegime != null){
			settleDiscRegModel = new SettlementDisciplineRegime();
			if(settlementDisciplineRegime.getIdInstitucion() != null){
				settleDiscRegModel.setIdInstitucion(settlementDisciplineRegime.getIdInstitucion());
			} else {
				settleDiscRegModel.setIdCuentaBoveda(settlementDisciplineRegime.getIdCuentaBoveda());
				settleDiscRegModel.setCuenta(settlementDisciplineRegime.getCuenta());
				settleDiscRegModel.setDetalleCustodio(settlementDisciplineRegime.getDetalleCustodio());
			}
			settleDiscRegModel.setHoldAndRelease((settlementDisciplineRegime.getHoldAndRelease() == 1L) ? Boolean.TRUE : Boolean.FALSE);
			settleDiscRegModel.setPartialSettlement((settlementDisciplineRegime.getPartialSettlement() == 1L) ? Boolean.TRUE : Boolean.FALSE);
			settleDiscRegModel.setBilateralCancellation((settlementDisciplineRegime.getBilateralCancellation() == 1L) ? Boolean.TRUE : Boolean.FALSE);
			if(!esActualizacion){
				settleDiscRegModel.setFechaAlta(new Date());
			} else {
				settleDiscRegModel.setIdConfigCsdr(settlementDisciplineRegime.getIdConfigCsdr());
			}
		}
		return settleDiscRegModel;
	}
	
	public static SettlementDisciplineRegime createSettlementDisciplineRegimeVoToModelWithChanges(
			SettlementDisciplineRegimeVO settlementDisciplineRegimeNuevo, SettlementDisciplineRegimeVO settlementDisciplineRegimeAnt){
		
		SettlementDisciplineRegimeVO settleDiscRegVo = new SettlementDisciplineRegimeVO();
		settleDiscRegVo.setIdConfigCsdr(settlementDisciplineRegimeAnt.getIdConfigCsdr());
		if(settlementDisciplineRegimeAnt.getIdInstitucion() != null){
			settleDiscRegVo.setIdInstitucion(settlementDisciplineRegimeAnt.getIdInstitucion());
			settleDiscRegVo.setFolioInstitucion(settlementDisciplineRegimeNuevo.getFolioInstitucion());
			settleDiscRegVo.setNombreCortoInstitucion(settlementDisciplineRegimeNuevo.getNombreCortoInstitucion());
		} else {
			settleDiscRegVo.setIdCuentaBoveda(settlementDisciplineRegimeAnt.getIdCuentaBoveda());
			settleDiscRegVo.setCuenta(settlementDisciplineRegimeAnt.getCuenta());
			settleDiscRegVo.setDetalleCustodio(settlementDisciplineRegimeAnt.getDetalleCustodio());
		}
		
		if(settlementDisciplineRegimeAnt.getHoldAndRelease() != settlementDisciplineRegimeNuevo.getHoldAndRelease()){
			settleDiscRegVo.setHoldAndRelease(settlementDisciplineRegimeNuevo.getHoldAndRelease());			
		}
		
		if(settlementDisciplineRegimeAnt.getPartialSettlement() != settlementDisciplineRegimeNuevo.getPartialSettlement()){
			settleDiscRegVo.setPartialSettlement(settlementDisciplineRegimeNuevo.getPartialSettlement());			
		}
		
		if(settlementDisciplineRegimeAnt.getBilateralCancellation() != settlementDisciplineRegimeNuevo.getBilateralCancellation()){
			settleDiscRegVo.setBilateralCancellation(settlementDisciplineRegimeNuevo.getBilateralCancellation());			
		}
		
		return transformSettlementDisciplineRegimeVoToModel(settleDiscRegVo, Boolean.TRUE);
	}
	
	public static SettlementDisciplineRegime createSettlementDisciplineRegimeVoToDelete(SettlementDisciplineRegimeVO settlementDisciplineRegimeVo){
		
		SettlementDisciplineRegime settleDiscReg = new SettlementDisciplineRegime();
		settleDiscReg.setIdConfigCsdr(settlementDisciplineRegimeVo.getIdConfigCsdr());
		if(settlementDisciplineRegimeVo.getIdInstitucion() != null){
			settleDiscReg.setIdInstitucion(settlementDisciplineRegimeVo.getIdInstitucion());
			settleDiscReg.setFolioInstitucion(settlementDisciplineRegimeVo.getFolioInstitucion());
			settleDiscReg.setNombreCortoinstitucion(settlementDisciplineRegimeVo.getNombreCortoInstitucion());
		} else {
			settleDiscReg.setIdCuentaBoveda(settlementDisciplineRegimeVo.getIdCuentaBoveda());
			settleDiscReg.setCuenta(settlementDisciplineRegimeVo.getCuenta());
			settleDiscReg.setDetalleCustodio(settlementDisciplineRegimeVo.getDetalleCustodio());
		}
		settleDiscReg.setHoldAndRelease((settlementDisciplineRegimeVo.getHoldAndRelease() == 1L) ? Boolean.TRUE : Boolean.FALSE);
		settleDiscReg.setPartialSettlement((settlementDisciplineRegimeVo.getPartialSettlement() == 1L) ? Boolean.TRUE : Boolean.FALSE);
		settleDiscReg.setBilateralCancellation((settlementDisciplineRegimeVo.getBilateralCancellation() == 1L) ? Boolean.TRUE : Boolean.FALSE);
		
		return settleDiscReg;
		
	}
	
	public void actualizaConfiguracion(SettlementDisciplineRegime settlementDisciplineRegime){

		if(settlementDisciplineRegime.getHoldAndRelease() != null){
			this.holdAndRelease = (settlementDisciplineRegime.getHoldAndRelease()) ? 1 : 0;
		}
		
		if(settlementDisciplineRegime.getPartialSettlement() != null){
			this.partialSettlement = (settlementDisciplineRegime.getPartialSettlement()) ? 1 : 0;
		}
		
		if(settlementDisciplineRegime.getBilateralCancellation() != null){
			this.bilateralCancellation = (settlementDisciplineRegime.getBilateralCancellation()) ? 1 : 0;
		}
		
	}

}
