// Cambio Multidivisas
package com.indeval.portalinternacional.middleware.servicios.modelo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "C_BANCO_CORRESPONSAL")
@SequenceGenerator(name = "foliador", sequenceName = "SEQ_BANCO_CORRESPONSAL", allocationSize = 1)
@NamedQueries({
		@NamedQuery(name = "CuentaCorresponsal.findByIdInstitucion", query = "SELECT cc FROM CuentaCorresponsal cc WHERE cc.idInstitucion = :idInstitucion"),
		@NamedQuery(name = "CuentaCorresponsal.findByIdDivisa", query = "SELECT cc FROM CuentaCorresponsal cc WHERE cc.idDivisa = :idDivisa"),
		@NamedQuery(name = "CuentaCorresponsal.findByIdDivisaAndIdInstitucion", query = "SELECT cc FROM CuentaCorresponsal cc WHERE cc.idDivisa = :idDivisa AND cc.idInstitucion = :idInstitucion"),
		@NamedQuery(name = "CuentaCorresponsal.findIdDivisasByIdInstitucion", query = "SELECT cc.idDivisa FROM CuentaCorresponsal cc WHERE cc.idInstitucion = :idInstitucion") 
})
public class CuentaCorresponsal implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "foliador")
	@Column(name = "ID_BANCO_CORRESPONSAL")
	private Long idCuentaCorresponsal;

	@Column(name = "ID_INSTITUCION")
	private Long idInstitucion;

	@Column(name = "ID_DIVISA")
	private Long idDivisa;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_BANCO_CORRESPONSAL_103", referencedColumnName = "ID_BANCO_CORRESPONSAL_103")
	private CuentaCorresponsal103 cuentaCorresponsal103;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_BANCO_CORRESPONSAL_202", referencedColumnName = "ID_BANCO_CORRESPONSAL_202")
	private CuentaCorresponsal202 cuentaCorresponsal202;
	
	@Column(name = "FECHA_ALTA")
	private Date fechaAlta;

	@Column(name = "FECHA_ULT_MODIFICACION")
	private Date fechaUltModificacion;

	public Long getIdCuentaCorresponsal() {
		return idCuentaCorresponsal;
	}

	public void setIdCuentaCorresponsal(Long idCuentaCorresponsal) {
		this.idCuentaCorresponsal = idCuentaCorresponsal;
	}

	public Long getIdInstitucion() {
		return idInstitucion;
	}

	public void setIdInstitucion(Long idInstitucion) {
		this.idInstitucion = idInstitucion;
	}

	public Long getIdDivisa() {
		return idDivisa;
	}

	public void setIdDivisa(Long idDivisa) {
		this.idDivisa = idDivisa;
	}

	public CuentaCorresponsal103 getCuentaCorresponsal103() {
		return cuentaCorresponsal103;
	}

	public void setCuentaCorresponsal103(CuentaCorresponsal103 cuentaCorresponsal103) {
		this.cuentaCorresponsal103 = cuentaCorresponsal103;
	}

	public CuentaCorresponsal202 getCuentaCorresponsal202() {
		return cuentaCorresponsal202;
	}

	public void setCuentaCorresponsal202(CuentaCorresponsal202 cuentaCorresponsal202) {
		this.cuentaCorresponsal202 = cuentaCorresponsal202;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public Date getFechaUltModificacion() {
		return fechaUltModificacion;
	}

	public void setFechaUltModificacion(Date fechaUltModificacion) {
		this.fechaUltModificacion = fechaUltModificacion;
	}

}
