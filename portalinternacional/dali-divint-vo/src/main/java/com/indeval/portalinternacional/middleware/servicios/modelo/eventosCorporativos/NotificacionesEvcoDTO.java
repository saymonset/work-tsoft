/**
 * 
 */
package com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

/**
 * @author kode-
 *
 */
public class NotificacionesEvcoDTO {

	
	private String idNotificacion;
	private boolean borradoLogico;
	private int posicionLista;
	private String tipoNotificacion;
	private String destinatario;
	private String textAdicional;
	private Date desde;
	private Date hasta;
	private String hora;
	private String cronExpression;
	private String strDesde;
	private String strHasta;
	
	private String numNotificacion;
	private String minCapturado;
	private String hrCapturada;
	private String diaCapturado;
	private String mesCapturado;
	private String patronCron;
	//campos comunes
	private String strDestinatario;
	private String strTextoNotificacion;
	private String strFechaIncio;
	private String strFechaFin;
	//Campo que me permite mapear la peridiocidad de la notificacion
	private String strPeridiocidad;
	//campo que me permite mapear el numero de opcion que se eligio en el componente acordeon
	private String numOpcAcc;
	//campo que me permite mapear el tipo de captura que estoy haciendo
	private String periodo;
	//campo que me permite mapear la captura de los dias seleccionados en la seccion de configuracion por semana
	private String[] arrayDias;
	private String idListaDIstribucion;

	private final DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	/**
	 * @return the tipoNotificacion
	 */
	public String getTipoNotificacion() {
		return tipoNotificacion;
	}
	/**
	 * @param tipoNotificacion the tipoNotificacion to set
	 */
	public void setTipoNotificacion(String tipoNotificacion) {
		this.tipoNotificacion = tipoNotificacion;
	}
	/**
	 * @return the destinatario
	 */
	public String getDestinatario() {
		return destinatario;
	}
	/**
	 * @param destinatario the destinatario to set
	 */
	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}
	/**
	 * @return the textAdicional
	 */
	public String getTextAdicional() {
		return textAdicional;
	}
	/**
	 * @param textAdicional the textAdicional to set
	 */
	public void setTextAdicional(String textAdicional) {
		this.textAdicional = textAdicional;
	}
	/**
	 * @return the desde
	 */
	public Date getDesde() {
		return desde;
	}
	/**
	 * @param desde the desde to set
	 */
	public void setDesde(Date desde) {
		this.desde = desde;
	}
	/**
	 * @return the hasta
	 */
	public Date getHasta() {
		return hasta;
	}
	/**
	 * @param hasta the hasta to set
	 */
	public void setHasta(Date hasta) {
		this.hasta = hasta;
	}
	/**
	 * @return the hora
	 */
	public String getHora() {
		return hora;
	}
	/**
	 * @param hora the hora to set
	 */
	public void setHora(String hora) {
		this.hora = hora;
	}
	
	public String getCronExpression() {
		return cronExpression;
	}
	
	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}
	
	public String getNumNotificacion() {
		return numNotificacion;
	}
	
	public void setNumNotificacion(String numNotificacion) {
		this.numNotificacion = numNotificacion;
	}
	
	public String getStrDesde() {
		return strDesde;
	}
	
	public void setStrDesde(String strDesde) {
		this.strDesde = strDesde;
	}
	
	public String getStrHasta() {
		return strHasta;
	}
	
	public void setStrHasta(String strHasta) {
		this.strHasta = strHasta;
	}
	
	public String getMinCapturado() {
		return minCapturado;
	}
	
	public void setMinCapturado(String minCapturado) {
		this.minCapturado = minCapturado;
	}
	
	public String getHrCapturada() {
		return hrCapturada;
	}
	
	public void setHrCapturada(String hrCapturada) {
		this.hrCapturada = hrCapturada;
	}
	
	public String getDiaCapturado() {
		return diaCapturado;
	}
	
	public void setDiaCapturado(String diaCapturado) {
		this.diaCapturado = diaCapturado;
	}
	
	public String getMesCapturado() {
		return mesCapturado;
	}
	
	public void setMesCapturado(String mesCapturado) {
		this.mesCapturado = mesCapturado;
	}
	
	public String getPatronCron() {
		return patronCron;
	}
	
	public void setPatronCron(String patronCron) {
		this.patronCron = patronCron;
	}
	
	public String getStrDestinatario() {
		return strDestinatario;
	}
	
	public void setStrDestinatario(String strDestinatario) {
		this.strDestinatario = strDestinatario;
	}
	
	public String getStrTextoNotificacion() {
		return strTextoNotificacion;
	}
	
	public void setStrTextoNotificacion(String strTextoNotificacion) {
		this.strTextoNotificacion = strTextoNotificacion;
	}
	
	public String getStrFechaIncio() {
		return strFechaIncio;
	}
	
	public void setStrFechaIncio(String strFechaIncio) {
		this.strFechaIncio = strFechaIncio;
	}
	
	public String getStrFechaFin() {
		return strFechaFin;
	}
	
	public void setStrFechaFin(String strFechaFin) {		
		this.strFechaFin = strFechaFin;
	}
	
	public String getStrPeridiocidad() {
		return strPeridiocidad;
	}
	
	public void setStrPeridiocidad(String strPeridiocidad) {
		this.strPeridiocidad = strPeridiocidad;
	}
	
	public String getNumOpcAcc() {
		return numOpcAcc;
	}
	
	public void setNumOpcAcc(String numOpcAcc) {
		this.numOpcAcc = numOpcAcc;
	}
	
	public String getPeriodo() {
		return periodo;
	}
	
	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}
	
	public String[] getArrayDias() {
		return arrayDias;
	}
	
	public void setArrayDias(String[] arrayDias) {
		this.arrayDias = arrayDias;
	}
	
	public String getIdListaDIstribucion() {
		return idListaDIstribucion;
	}
	
	public void setIdListaDIstribucion(String idListaDIstribucion) {
		this.idListaDIstribucion = idListaDIstribucion;
	}
	
	public Date getFechaInicio() throws ParseException{
		if(this.strFechaIncio != null){
			return (df.parse(this.strFechaIncio));
		}else{
			return null;
		}
	}
	
	public Date getFechaFin() throws ParseException{
		if(this.strFechaIncio != null){
			return (df.parse(this.strFechaFin));
		}else{
			return null;
		}		
	}
	public String getIdNotificacion() {
		return idNotificacion;
	}
	public void setIdNotificacion(String idNotificacion) {
		this.idNotificacion = idNotificacion;
	}
	public int getPosicionLista() {
		return posicionLista;
	}
	public void setPosicionLista(int posicionLista) {
		this.posicionLista = posicionLista;
	}
	public boolean isBorradoLogico() {
		return borradoLogico;
	}
	public void setBorradoLogico(boolean borradoLogico) {
		this.borradoLogico = borradoLogico;
	}
	
}
