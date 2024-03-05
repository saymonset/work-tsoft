package com.indeval.portaldali.persistence.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@Entity
@Table(name="R_INST_CON_INST_REFERENCIADA")
@NamedQueries(
	{
		@NamedQuery(
				name = "DepositanteValidoBanxico.getDepositantesValidosParaBanxico",
				query = " FROM DepositanteValidoBanxico depositanteValidoBanxico " +
						" WHERE depositanteValidoBanxico.idInstitucion = :idInstitucion " +
						" AND depositanteValidoBanxico.institucionValida = '1' "
		)
	}
)
public class DepositanteValidoBanxico implements Serializable {

    /**
	 * Identificador unico de la clase.
	 */
	private static final long serialVersionUID = 6377695472951907279L;

	@Id
	@Column(name ="ID_INSTITUCION", unique = true)
	private Long idInstitucion;

    @Column(name ="DEPOSITANTE_VALIDO", nullable = false)
	private int institucionValida;

	public Long getIdInstitucion() {
		return idInstitucion;
	}

	public void setIdInstitucion(Long idInstitucion) {
		this.idInstitucion = idInstitucion;
	}

	public int getInstitucionValida() {
		return institucionValida;
	}

	public void setInstitucionValida(int institucionValida) {
		this.institucionValida = institucionValida;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(13, 23)
			.append(idInstitucion)
			.append(institucionValida)
			.toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof DepositanteValidoBanxico) {
			DepositanteValidoBanxico depositanteValidoBanxico = (DepositanteValidoBanxico) obj;
			return new EqualsBuilder()
				.append(idInstitucion, depositanteValidoBanxico.getIdInstitucion())
				.append(institucionValida, depositanteValidoBanxico.getInstitucionValida())
				.isEquals();
		}

		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
			.append("Id de la Institucion", idInstitucion)
			.append("La institucion es valida ?", institucionValida)
			.toString();
	}
}