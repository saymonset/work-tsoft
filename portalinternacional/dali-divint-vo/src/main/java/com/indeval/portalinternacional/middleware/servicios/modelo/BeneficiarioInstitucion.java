/*
 * Copyrigth (c) 2008 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.servicios.modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.indeval.portaldali.persistence.modelo.Institucion;
/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 * 
 */
@Entity
@Table(name = "T_BENEFICIARIOS_INSTITUCION")
@SequenceGenerator(name = "foliador", sequenceName = "ID_BENEFICIARIOS_INS_SEQ", allocationSize = 1, initialValue = 1)
public class BeneficiarioInstitucion implements Serializable {

	private static final long serialVersionUID = -1034096473757947745L;
	
	private Long idBeneficiarioInstitucion;

	private Long beneficiario;

	private Long institucion;
	
//	private Boolean generaCobro;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "foliador")
	@Column(name = "ID_BENEFICIARIO_INSTITUCION", unique = true, nullable = false)
	public Long getIdBeneficiarioInstitucion() {
		return idBeneficiarioInstitucion;
	}

	@Column(name = "ID_BENEFICIARIO", unique = false, nullable = false)
	public Long getBeneficiario() {
		return beneficiario;
	}
	
	@Column(name = "ID_INSTITUCION", unique = false, nullable = false)
	public Long getInstitucion() {
		return institucion;
	}
	
//	@Column(name = "GENERA_COBRO", unique = false, nullable = true)
//	public Boolean getGeneraCobro() {
//		return generaCobro;
//	}

	public void setIdBeneficiarioInstitucion(Long idBeneficiarioInstitucion) {
		this.idBeneficiarioInstitucion = idBeneficiarioInstitucion;
	}
	
	public void setBeneficiario(Long beneficiario) {
		this.beneficiario = beneficiario;
	}

	public void setInstitucion(Long institucion) {
		this.institucion = institucion;
	}
	
}
