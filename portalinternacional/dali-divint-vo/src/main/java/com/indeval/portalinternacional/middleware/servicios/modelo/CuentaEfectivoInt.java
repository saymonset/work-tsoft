package com.indeval.portalinternacional.middleware.servicios.modelo;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.indeval.portaldali.persistence.modelo.Boveda;
import com.indeval.portaldali.persistence.modelo.Divisa;

/**
 *
 * @author Fernando Pineda
 *
 */
@Entity
@Table(name = "C_CUENTA_EFECTIVO_INT")
@SequenceGenerator(name = "foliador", sequenceName = "SEQ_C_CUENTA_EFECTIVO_INT", allocationSize = 1)
public class CuentaEfectivoInt implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "foliador")
	@Column(name = "ID_CUENTA_EFECTIVO")
	private Long idCuenta;
	
	@Column(name = "BIC_CODE")
	private String bicCode;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_DIVISA")
	private Divisa divisa;
	
	@Column(name = "CUENTA")
	private String cuenta;
	
	@Column(name = "CUENTA_950")
	private String cuenta950;
	
	@Column(name = "ACTIVO")
	private Boolean activo;
	
	@Column(name = "ENVIO_TESORERIA")
	private Boolean envioTesoreria;
	
	@Column(name = "SALDO_INICIAL")
	private BigDecimal saldo;
	
	@Column(name = "NOMBRE")
	private String nombre;
	
	@Column(name = "HORARIO_INICIO")
	private String horarioInicio;
	
	@Column(name = "HORARIO_FIN")
	private String horarioFin;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_PAIS")
	private PaisInt pais;
	
	
//	@ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(referencedColumnName="ID_TIPO_CUENTA_EFECTIVO", name = "TIPO_CUENTA")
//	private TipoCuentaEfectivoInt tipoCuenta;
	@Column(name = "TIPO_CUENTA")
	private Integer tipoCuenta;
	
	@Column(name = "CUENTA_NEGOCIO")
	private String cuentaNegocio;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_BOVEDA")
	private Boveda boveda;
	
	/**
	 * @return the idCuenta
	 */
	public Long getIdCuenta() {
		return idCuenta;
	}
	/**
	 * @param idCuenta the idCuenta to set
	 */
	public void setIdCuenta(Long idCuenta) {
		this.idCuenta = idCuenta;
	}
	/**
	 * @return the bicCode
	 */
	public String getBicCode() {
		return bicCode;
	}
	/**
	 * @param bicCode the bicCode to set
	 */
	public void setBicCode(String bicCode) {
		this.bicCode = bicCode;
	}
	/**
	 * @return the divisa
	 */
	public Divisa getDivisa() {
		return divisa;
	}
	/**
	 * @param divisa 
	 * @param idDivisa the idDivisa to set
	 */
	public void setDivisa(Divisa divisa) {
		this.divisa = divisa;
	}
	/**
	 * @return the cuenta
	 */
	public String getCuenta() {
		return cuenta;
	}
	/**
	 * @param cuenta the cuenta to set
	 */
	public void setCuenta(String cuenta) {
		this.cuenta = cuenta;
	}
	/**
	 * @return the activo
	 */
	public Boolean getActivo() {
		return activo;
	}
	/**
	 * @param activo the activo to set
	 */
	public void setActivo(Boolean activo) {
		this.activo = activo;
	}
	/**
	 * @return the envioTesoreria
	 */
	public Boolean getEnvioTesoreria() {
		return envioTesoreria;
	}
	/**
	 * @param envioTesoreria the envioTesoreria to set
	 */
	public void setEnvioTesoreria(Boolean envioTesoreria) {
		this.envioTesoreria = envioTesoreria;
	}
	/**
	 * @return the saldo
	 */
	public BigDecimal getSaldo() {
		return saldo;
	}
	/**
	 * @param saldo the saldo to set
	 */
	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}
	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}
	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	/**
	 * @return the horarioInicio
	 */
	public String getHorarioInicio() {
		return horarioInicio;
	}
	/**
	 * @param horarioInicio the horarioInicio to set
	 */
	public void setHorarioInicio(String horarioInicio) {
		this.horarioInicio = horarioInicio;
	}
	/**
	 * @return the horarioFin
	 */
	public String getHorarioFin() {
		return horarioFin;
	}
	/**
	 * @param horarioFin the horarioFin to set
	 */
	public void setHorarioFin(String horarioFin) {
		this.horarioFin = horarioFin;
	}
	/**
	 * @return the pais
	 */
	public PaisInt getPais() {
		return pais;
	}
	/**
	 * @param pais the pais to set
	 */
	public void setPais(PaisInt pais) {
		this.pais = pais;
	}
	/**
	 * @return the tipoCuenta
	 */
//	public TipoCuentaEfectivoInt getTipoCuenta() {
//		return tipoCuenta;
//	}
	/**
	 * @param tipoCuenta the tipoCuenta to set
	 */
//	public void setTipoCuenta(TipoCuentaEfectivoInt tipoCuenta) {
//		this.tipoCuenta = tipoCuenta;
//	}
	/**
	 * @return the cuentaNegocio
	 */
	public String getCuentaNegocio() {
		return cuentaNegocio;
	}
	/**
	 * @param cuentaNegocio the cuentaNegocio to set
	 */
	public void setCuentaNegocio(String cuentaNegocio) {
		this.cuentaNegocio = cuentaNegocio;
	}
	/**
	 * @return the boveda
	 */
	public Boveda getBoveda() {
		return boveda;
	}
	/**
	 * @param boveda the boveda to set
	 */
	public void setBoveda(Boveda boveda) {
		this.boveda = boveda;
	}
	/**
	 * @return the cuenta950
	 */
	public String getCuenta950() {
		return cuenta950;
	}
	/**
	 * @param cuenta950 the cuenta950 to set
	 */
	public void setCuenta950(String cuenta950) {
		this.cuenta950 = cuenta950;
	}
	/**
	 * @return the tipoCuenta
	 */
	public Integer getTipoCuenta() {
		return tipoCuenta;
	}
	/**
	 * @param tipoCuenta the tipoCuenta to set
	 */
	public void setTipoCuenta(Integer tipoCuenta) {
		this.tipoCuenta = tipoCuenta;
	}
}
