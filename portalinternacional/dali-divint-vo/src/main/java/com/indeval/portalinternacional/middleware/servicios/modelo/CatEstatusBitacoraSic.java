package com.indeval.portalinternacional.middleware.servicios.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the C_ESTATUS_BITACORA_SIC database table.
 * 
 */
@Entity
@Table(name="C_ESTATUS_BITACORA_SIC")
@NamedQuery(name="CatEstatusBitacoraSic.findAll", query="SELECT c FROM CatEstatusBitacoraSic c")
public class CatEstatusBitacoraSic implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="foliadorCBSIC", sequenceName="ID_ESTATUS_BITACORA_SEC")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="foliadorCBSIC")
	@Column(name="ID_ESTATUS_BITACORA")
	private long idEstatusBitacora;

	@Column(name="DESC_ESTATUS_BITACORA")
	private String descEstatusBitacora;

	//bi-directional many-to-one association to TBitacoraOperacionesSic
	@OneToMany(mappedBy="CEstatusBitacoraSic")
	private List<BitacoraOperacionesSic> TBitacoraOperacionesSics;

	public CatEstatusBitacoraSic() {
	}

	public long getIdEstatusBitacora() {
		return this.idEstatusBitacora;
	}

	public void setIdEstatusBitacora(long idEstatusBitacora) {
		this.idEstatusBitacora = idEstatusBitacora;
	}

	public String getDescEstatusBitacora() {
		return this.descEstatusBitacora;
	}

	public void setDescEstatusBitacora(String descEstatusBitacora) {
		this.descEstatusBitacora = descEstatusBitacora;
	}

	public List<BitacoraOperacionesSic> getTBitacoraOperacionesSics() {
		return this.TBitacoraOperacionesSics;
	}

	public void setTBitacoraOperacionesSics(List<BitacoraOperacionesSic> TBitacoraOperacionesSics) {
		this.TBitacoraOperacionesSics = TBitacoraOperacionesSics;
	}

	public BitacoraOperacionesSic addTBitacoraOperacionesSic(BitacoraOperacionesSic TBitacoraOperacionesSic) {
		getTBitacoraOperacionesSics().add(TBitacoraOperacionesSic);
		TBitacoraOperacionesSic.setCEstatusBitacoraSic(this);

		return TBitacoraOperacionesSic;
	}

	public BitacoraOperacionesSic removeTBitacoraOperacionesSic(BitacoraOperacionesSic TBitacoraOperacionesSic) {
		getTBitacoraOperacionesSics().remove(TBitacoraOperacionesSic);
		TBitacoraOperacionesSic.setCEstatusBitacoraSic(null);

		return TBitacoraOperacionesSic;
	}

}