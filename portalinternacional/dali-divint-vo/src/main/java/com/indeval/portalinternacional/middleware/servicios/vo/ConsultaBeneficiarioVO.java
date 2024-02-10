package com.indeval.portalinternacional.middleware.servicios.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;


import com.indeval.portaldali.persistence.modelo.Institucion;
import com.indeval.portalinternacional.middleware.servicios.modelo.Beneficiario;
import com.indeval.portalinternacional.middleware.servicios.modelo.FormatoW8BEN;
import com.indeval.portalinternacional.middleware.servicios.modelo.FormatoW8IMY;
import com.indeval.portalinternacional.middleware.servicios.modelo.FormatoW9;

public class ConsultaBeneficiarioVO implements Serializable {

	private static final long serialVersionUID = -7321590323628617871L;

	private Long idBeneficiario;
	private List<String> institucion;
	private String formato;
	private String nombres;
	private String descTipoBeneficiario;
	private String descStatusBeneficiario;
	private Long custodio;
	private String razonSocial;
	private boolean activar;
	private boolean personaFisica;
	private Date fechaRegistro;
	private Date fechaFormato;

	public ConsultaBeneficiarioVO() {
		super();
	}
	
	public ConsultaBeneficiarioVO(Long custodio, String descTipoBeneficiario,
			String formato, Long idBeneficiario, List<String> institucion,
			String nombres, String descStatusBeneficiario, String razonSocial) {
		super();
		this.custodio = custodio;
		this.descTipoBeneficiario = descTipoBeneficiario;
		this.formato = formato;
		this.idBeneficiario = idBeneficiario;
		this.institucion = institucion;
		this.nombres = nombres;
		this.descStatusBeneficiario = descStatusBeneficiario;
		this.razonSocial = razonSocial;
		activar = false;
	}
	
	public ConsultaBeneficiarioVO(Long idBeneficiario, String nombres, String razonSocial) {
		super();
		this.idBeneficiario = idBeneficiario;
		this.nombres = nombres;
		this.razonSocial = razonSocial;
		activar = false;
	}
	
	public static ConsultaBeneficiarioVO construyeVO(Beneficiario beneficiario) {
		ConsultaBeneficiarioVO vo = new ConsultaBeneficiarioVO();
		vo.setCustodio(beneficiario.getIdCuentaNombrada());
		vo.setFormato(beneficiario.getTipoFormato());
		vo.setIdBeneficiario(beneficiario.getIdBeneficiario());
		vo.setFechaRegistro(beneficiario.getFechaRegistro());
		vo.setFechaFormato(beneficiario.getFechaFormato());
		
		
		if(beneficiario.getInstitucion() != null && 
				!beneficiario.getInstitucion().isEmpty()) {
			List<String> inst = new ArrayList<String>();
			for(Institucion institucion : beneficiario.getInstitucion()) {
				inst.add(institucion.getClaveInstitucion() + " - " + institucion.getNombreCorto());
			}
			vo.setInstitucion(inst);
		}
		vo.setPersonaFisica(beneficiario.getPersonaFisica());
		if( beneficiario.getPersonaFisica() ) {
			vo.setNombres(beneficiario.getNombres() + " " + beneficiario.getApellidoPaterno() + " " + beneficiario.getApellidoMaterno());
			vo.setRazonSocial(null);
		} else {
			vo.setNombres(null);
			vo.setRazonSocial(beneficiario.getRazonSocial());
		}
		if( beneficiario.getTipoFormato().equals("W8BEN") ) {
			FormatoW8BEN formato = beneficiario.getFormatoW8BEN();
			procesaW8BEN(formato, vo);
		} else if( beneficiario.getTipoFormato().equals("W8IMY") ) {
			FormatoW8IMY formato = beneficiario.getFormatoW8IMY();
			procesaW8IMY(formato, vo);
		} else if( beneficiario.getTipoFormato().equals("W9") ) {
			FormatoW9 formato = beneficiario.getFormatoW9();
			procesaW9(formato, vo);
		}
		
		vo.setDescStatusBeneficiario(beneficiario.getStatusBenef().getDescStatusBenef());
		vo.setActivar(false);
		return vo;
	}
	
	private static void procesaW8BEN(FormatoW8BEN formato,ConsultaBeneficiarioVO vo) {
		vo.setDescTipoBeneficiario(formato.getField3().getDescripcion());
	}
	
	private static void procesaW8IMY(FormatoW8IMY formato,ConsultaBeneficiarioVO vo) {
		vo.setDescTipoBeneficiario(formato.getField3().getDescripcion());
	}

	private static void procesaW9(FormatoW9 formato,ConsultaBeneficiarioVO vo) {
		vo.setDescTipoBeneficiario(formato.getTypeTaxPayer().getDescripcion());
	}

	/**
	 * @return the idBeneficiario
	 */
	public Long getIdBeneficiario() {
		return idBeneficiario;
	}

	/**
	 * @param idBeneficiario
	 *            the idBeneficiario to set
	 */
	public void setIdBeneficiario(Long idBeneficiario) {
		this.idBeneficiario = idBeneficiario;
	}

	/**
	 * @return the institucion
	 */
	public List<String> getInstitucion() {
		return institucion;
	}

	/**
	 * @param institucion
	 *            the institucion to set
	 */
	public void setInstitucion(List<String> institucion) {
		this.institucion = institucion;
	}

	/**
	 * @return the formato
	 */
	public String getFormato() {
		return formato;
	}

	/**
	 * @param formato
	 *            the formato to set
	 */
	public void setFormato(String formato) {
		this.formato = formato;
	}

	/**
	 * @return the nombreBeneficiario
	 */
	public String getNombres() {
		return nombres;
	}

	/**
	 * @param nombreBeneficiario
	 *            the nombreBeneficiario to set
	 */
	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	/**
	 * @return the descTipoBeneficiario
	 */
	public String getDescTipoBeneficiario() {
		return descTipoBeneficiario;
	}

	/**
	 * @param descTipoBeneficiario
	 *            the descTipoBeneficiario to set
	 */
	public void setDescTipoBeneficiario(String descTipoBeneficiario) {
		this.descTipoBeneficiario = descTipoBeneficiario;
	}

	/**
	 * @return the descStatusBeneficiario
	 */
	public String getDescStatusBeneficiario() {
		return descStatusBeneficiario;
	}

	/**
	 * @param descStatusBeneficiario
	 *            the descStatusBeneficiario to set
	 */
	public void setDescStatusBeneficiario(String descStatusBeneficiario) {
		this.descStatusBeneficiario = descStatusBeneficiario;
	}

	/**
	 * @return the custodio
	 */
	public Long getCustodio() {
		return custodio;
	}

	/**
	 * @param custodio
	 *            the custodio to set
	 */
	public void setCustodio(Long custodio) {
		this.custodio = custodio;
	}

	/**
	 * @return the razonSocial
	 */
	public String getRazonSocial() {
		return razonSocial;
	}

	/**
	 * @param razonSocial the razonSocial to set
	 */
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	/**
	 * @param activar the activar to set
	 */
	public void setActivar(boolean activar) {
		this.activar = activar;
	}

	/**
	 * @return the activar
	 */
	public boolean isActivar() {
		return activar;
	}

	/**
	 * @param personaFisica the personaFisica to set
	 */
	public void setPersonaFisica(boolean personaFisica) {
		this.personaFisica = personaFisica;
	}

	/**
	 * @return the personaFisica
	 */
	public boolean isPersonaFisica() {
		return personaFisica;
	}

	/**
	 * @param fechaRegistro the fechaRegistro to set
	 */
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	/**
	 * @return the fechaRegistro
	 */
	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	/**
	 * @param fechaFormato the fechaFormato to set
	 */
	public void setFechaFormato(Date fechaFormato) {
		this.fechaFormato = fechaFormato;
	}

	/**
	 * @return the fechaFormato
	 */
	public Date getFechaFormato() {
		return fechaFormato;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(13, 23).append(idBeneficiario).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		boolean isEqual = false;
		if (obj instanceof Beneficiario) {
			Beneficiario beneficiario = (Beneficiario) obj;
			isEqual = new EqualsBuilder().append(idBeneficiario,
					beneficiario.getIdBeneficiario()).isEquals();
		}
		return isEqual;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append("Id Beneficiario", idBeneficiario)
				.append("Nombre", nombres)
				.append("Tipo Formato", formato)
				.append("Tipo Beneficiario", descTipoBeneficiario)
				.append("Status Beneficiario", descStatusBeneficiario)
				.append("Institucion",institucion)
				.append("Custodio",custodio).toString();
	}

}
