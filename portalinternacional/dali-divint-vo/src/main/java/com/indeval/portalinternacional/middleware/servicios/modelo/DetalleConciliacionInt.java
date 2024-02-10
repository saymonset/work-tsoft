package com.indeval.portalinternacional.middleware.servicios.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;



import com.indeval.portaldali.persistence.modelo.Boveda;

/**
 * 
 * @author Arzate-Jacinto A.
 *
 */
@Entity
@Table(name = "T_DETALLE_CONCILIACION_INT")
@SequenceGenerator(name = "foliador",sequenceName = "SEQ_T_DET_CONCILIACION_INT",initialValue = 1, allocationSize = 1)
public class DetalleConciliacionInt implements Serializable {


	private static final long serialVersionUID = 3406010865258821922L;
	private Long id;
	private Long conciliacion;
	private Date fechaMensaje;
	private Date fechaConciliacion;
	private String cuentas;
	private String tv;
	private String emisora;
	private String serie;
	private String isin;
	private List<DetalleIsinConciliacionInt> listaPosicionEmision;
	private BigDecimal posicionMensaje;
	private Long posicionDali;
	private BigDecimal diferencia;
	private String nombre;
	private Boveda boveda;
	private Custodio custodio;
	private Map<String, DetalleIsinConciliacionInt> mapaDetalle;
	private String existencia;
	private Double valorNominal;
	private static final String EXISTENCIA_MENSAJE = "M";
	private static final String EXISTENCIA_DALI = "D";
	private static final String EXISTENCIA_AMBOS = "A";
	
	public DetalleConciliacionInt() {
		super();
	}
	
	
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="foliador")
	@Column(name="ID_DETALLE_CONCILIACION")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name="ID_CONCILIACION")
	public Long getConciliacion() {
		return conciliacion;
	}
	public void setConciliacion(Long conciliacion) {
		this.conciliacion = conciliacion;
	}
	
	@Column(name="FECHA_MENSAJE")
	public Date getFechaMensaje() {
		return fechaMensaje;
	}
	public void setFechaMensaje(Date fechaMensaje) {
		this.fechaMensaje = fechaMensaje;
	}
	
	@Column(name="FECHA_CONCILIACION")
	public Date getFechaConciliacion() {
		return fechaConciliacion;
	}
	public void setFechaConciliacion(Date fechaConciliacion) {
		this.fechaConciliacion = fechaConciliacion;
	}
	
	@Column(name="CUENTAS")
	public String getCuentas() {
		return cuentas;
	}
	public void setCuentas(String cuentas) {
		this.cuentas = cuentas;
	}
	@Column(name="TV")
	public String getTv() {
		return tv;
	}
	public void setTv(String tv) {
		this.tv = tv;
	}
	
	@Column(name="EMISORA")
	public String getEmisora() {
		return emisora;
	}
	public void setEmisora(String emisora) {
		this.emisora = emisora;
	}
	
	@Column(name="SERIE")
	public String getSerie() {
		return serie;
	}
	public void setSerie(String serie) {
		this.serie = serie;
	}
	
	@Column(name="ISIN")
	public String getIsin() {
		return isin;
	}
	public void setIsin(String isin) {
		this.isin = isin;
	}


	@OneToMany(targetEntity=DetalleIsinConciliacionInt.class,cascade=CascadeType.ALL,fetch = FetchType.EAGER)
	@JoinColumn(name="ID_DETALLE_CONCILIACION")
	public List<DetalleIsinConciliacionInt> getListaPosicionEmision() {
		return listaPosicionEmision;
	}
	public void setListaPosicionEmision(
			List<DetalleIsinConciliacionInt> listaPosicionEmision) {
		this.listaPosicionEmision = listaPosicionEmision;
	}
	
	@Column(name="POSICION_MENSAJE")
	public BigDecimal getPosicionMensaje() {
		return posicionMensaje;
	}
	public void setPosicionMensaje(BigDecimal posicionMensaje) {
		this.posicionMensaje = posicionMensaje;
	}
	
	@Column(name="POSICION_DALI")
	public Long getPosicionDali() {
		return posicionDali;
	}
	public void setPosicionDali(Long posicionDali) {
		this.posicionDali = posicionDali;
	}
	
	@Column(name="DIFERENCIA")
	public BigDecimal getDiferencia() {
		return diferencia;
	}
	public void setDiferencia(BigDecimal diferencia) {
		this.diferencia = diferencia;
	}
	
	@Column(name="NOMBRE")
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_BOVEDA", unique = false, nullable = true)
	public Boveda getBoveda() {
		return boveda;
	}

	public void setBoveda(Boveda boveda) {
		this.boveda = boveda;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_CUSTODIO", unique = false, nullable = true)
	public Custodio getCustodio() {
		return custodio;
	}
	public void setCustodio(Custodio custodio) {
		this.custodio = custodio;
	}
	
	@Transient
	public BigDecimal getPosAGGR(){		
		return getDetallePos("AGGR")!= null ? new BigDecimal(getDetallePos("AGGR").toString()) : null;
	}
	
	@Transient
	public BigDecimal getPosAVAI(){
		return getDetallePos("AVAI")!= null ? new BigDecimal(getDetallePos("AVAI").toString()) : null;
	}
	
	@Transient
	public BigDecimal getPosNAVL(){
		return getDetallePos("NAVL") != null ? new BigDecimal(getDetallePos("NAVL").toString()) : null;
	}
	
	@Transient
	public BigDecimal getPosBLOK(){
		return getDetallePos("BLOK") != null ? new BigDecimal(getDetallePos("BLOK").toString()) : null;
	}
	
	@Transient
	public BigDecimal getPosBLCA(){
		return getDetallePos("BLCA") != null ? new BigDecimal(getDetallePos("BLCA").toString()) : null;
	}
	
	@Transient
	public BigDecimal getPosPEND(){
		return getDetallePos("PEND") != null ? new BigDecimal(getDetallePos("PEND").toString()) : null;
	}
	
	@Transient
	public BigDecimal getPosPENR(){
		return getDetallePos("PENR") != null ? new BigDecimal(getDetallePos("PENR").toString()) : null;
	}
	
	@Transient
	public Double getDetallePosByTipo(String tipo){
		return getDetallePos(tipo);
	}
	
	@Transient
	private Double getDetallePos(String tipo){
		Double val = null;
		if(StringUtils.isEmpty(tipo)){
			return null;
		}
		
		if(mapaDetalle == null){
			llenaMapaDetalle();
		}
		
		if(mapaDetalle != null && mapaDetalle.size() > 0){			
			if(mapaDetalle.containsKey(tipo)){
				val= mapaDetalle.get(tipo).getValor();
			}
		}
		return val;
	}
	
	@Transient
	private void llenaMapaDetalle(){
		mapaDetalle = new HashMap<String, DetalleIsinConciliacionInt>();
		if(listaPosicionEmision != null && listaPosicionEmision.size() > 0){
			for(DetalleIsinConciliacionInt det : listaPosicionEmision){
				mapaDetalle.put(det.getPosicionEmision(), det);
			}
		}
	}
	
	@Transient
	public Map getMapaDetalle(){
		if(mapaDetalle == null){
			llenaMapaDetalle();
		}
		return mapaDetalle;
	}


	/**
	 * @return the existencia
	 */
	@Column(name="EXISTENCIA")
	public String getExistencia() {
		return existencia;
	}
	@Transient
	public String getExistenciaCompleto(){
		if(!StringUtils.isEmpty(existencia)){
			if(EXISTENCIA_MENSAJE.equalsIgnoreCase(existencia)){
				return "MENSAJE";
			}else if(EXISTENCIA_DALI.equalsIgnoreCase(existencia)){
				return "DALI";
			}else if(EXISTENCIA_AMBOS.equalsIgnoreCase(existencia)){
				return "AMBOS";
			}
		}
		return null;
	}
	/**
	 * @param existencia the existencia to set
	 */
	public void setExistencia(String existencia) {
		this.existencia = existencia;
	}




	/**
	 * @return the valorNominal
	 */
	@Column(name="VALOR_NOMINAL")
	public Double getValorNominal() {
		return valorNominal;
	}




	/**
	 * @param valorNominal the valorNominal to set
	 */
	public void setValorNominal(Double valorNominal) {
		this.valorNominal = valorNominal;
	}
	
}