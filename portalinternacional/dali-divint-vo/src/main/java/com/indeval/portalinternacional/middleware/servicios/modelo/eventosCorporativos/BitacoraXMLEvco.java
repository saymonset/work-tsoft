/**
 * 
 */
package com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos;


import java.util.Date;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;


/**
 * @author kode-
 *
 */
@XStreamAlias("BitacoraXMLEvco")
 public class BitacoraXMLEvco {


		private Long idEventoCorporativo;
		private String fechaCreacion;
		private String fechaRegistro;
		private String tipoValor;
		private String emisora;
		private String serie;
		private String isin;
		private TipoDerechoEvCo tipoDerechoMO;
		private TipoDerechoEvCo tipoDerechoML;
		private TipoMercado tipoMercado;
		private TipoEvento tipoEvento;
		private CuerpoEventoCorporativo cuerpoEvento;
		private Estado estado;
		private String fechaIndeval;
		private String fechaCliente;
		private String fechaPago;
		private Integer prioridad;
		private CustodioDto custodio;
		private Date fechaActualizacion;
		private Boolean actualizado;

		private List<NotasDTO> notasVec;
		private List<OpcionesDTO> opcionesVec;			
		private List<ArchivosAdjuntosEvcoDTO> archivosVec;	


		private Long hashCodeGral;
	
		private Long hashCodeOpciones;

		private Long hashCodeNotas;

		private Long hashCodeCuerpo;
		
//		@XStreamAlias("hashCodeNotas") 
//		@XStreamAsAttribute		
		private Long hashCodeArchivo;
		/**
		 * @return the idEventoCorporativo
		 */
		public Long getIdEventoCorporativo() {
			return idEventoCorporativo;
		}
		/**
		 * @param idEventoCorporativo the idEventoCorporativo to set
		 */
		public void setIdEventoCorporativo(Long idEventoCorporativo) {
			this.idEventoCorporativo = idEventoCorporativo;
		}
		
		/**
		 * @return the fechaCreacion
		 */
		public String getFechaCreacion() {
			return fechaCreacion;
		}
		/**
		 * @param fechaCreacion the fechaCreacion to set
		 */
		public void setFechaCreacion(String fechaCreacion) {
			this.fechaCreacion = fechaCreacion;
		}
		/**
		 * @return the fechaRegistro
		 */
		public String getFechaRegistro() {
			return fechaRegistro;
		}
		/**
		 * @param fechaRegistro the fechaRegistro to set
		 */
		public void setFechaRegistro(String fechaRegistro) {
			this.fechaRegistro = fechaRegistro;
		}
		/**
		 * @return the tipoValor
		 */
		public String getTipoValor() {
			return tipoValor;
		}
		/**
		 * @param tipoValor the tipoValor to set
		 */
		public void setTipoValor(String tipoValor) {
			this.tipoValor = tipoValor;
		}
		/**
		 * @return the emisora
		 */
		public String getEmisora() {
			return emisora;
		}
		/**
		 * @param emisora the emisora to set
		 */
		public void setEmisora(String emisora) {
			this.emisora = emisora;
		}
		/**
		 * @return the serie
		 */
		public String getSerie() {
			return serie;
		}
		/**
		 * @param serie the serie to set
		 */
		public void setSerie(String serie) {
			this.serie = serie;
		}
		/**
		 * @return the isin
		 */
		public String getIsin() {
			return isin;
		}
		/**
		 * @param isin the isin to set
		 */
		public void setIsin(String isin) {
			this.isin = isin;
		}
		/**
		 * @return the tipoDerechoMO
		 */
		public TipoDerechoEvCo getTipoDerechoMO() {
			return tipoDerechoMO;
		}
		/**
		 * @param tipoDerechoMO the tipoDerechoMO to set
		 */
		public void setTipoDerechoMO(TipoDerechoEvCo tipoDerechoMO) {
			this.tipoDerechoMO = tipoDerechoMO;
		}
		/**
		 * @return the tipoDerechoML
		 */
		public TipoDerechoEvCo getTipoDerechoML() {
			return tipoDerechoML;
		}
		/**
		 * @param tipoDerechoML the tipoDerechoML to set
		 */
		public void setTipoDerechoML(TipoDerechoEvCo tipoDerechoML) {
			this.tipoDerechoML = tipoDerechoML;
		}
		/**
		 * @return the tipoMercado
		 */
		public TipoMercado getTipoMercado() {
			return tipoMercado;
		}
		/**
		 * @param tipoMercado the tipoMercado to set
		 */
		public void setTipoMercado(TipoMercado tipoMercado) {
			this.tipoMercado = tipoMercado;
		}
		/**
		 * @return the tipoEvento
		 */
		public TipoEvento getTipoEvento() {
			return tipoEvento;
		}
		/**
		 * @param tipoEvento the tipoEvento to set
		 */
		public void setTipoEvento(TipoEvento tipoEvento) {
			this.tipoEvento = tipoEvento;
		}
		/**
		 * @return the estado
		 */
		public Estado getEstado() {
			return estado;
		}
		/**
		 * @param estado the estado to set
		 */
		public void setEstado(Estado estado) {
			this.estado = estado;
		}

		/**
		 * @return the fechaIndeval
		 */
		public String getFechaIndeval() {
			return fechaIndeval;
		}
		/**
		 * @param fechaIndeval the fechaIndeval to set
		 */
		public void setFechaIndeval(String fechaIndeval) {
			this.fechaIndeval = fechaIndeval;
		}
		/**
		 * @return the fechaCliente
		 */
		public String getFechaCliente() {
			return fechaCliente;
		}
		/**
		 * @param fechaCliente the fechaCliente to set
		 */
		public void setFechaCliente(String fechaCliente) {
			this.fechaCliente = fechaCliente;
		}
		/**
		 * @return the fechaPago
		 */
		public String getFechaPago() {
			return fechaPago;
		}
		/**
		 * @param fechaPago the fechaPago to set
		 */
		public void setFechaPago(String fechaPago) {
			this.fechaPago = fechaPago;
		}
		/**
		 * @return the prioridad
		 */
		public Integer getPrioridad() {
			return prioridad;
		}
		/**
		 * @param prioridad the prioridad to set
		 */
		public void setPrioridad(Integer prioridad) {
			this.prioridad = prioridad;
		}

		/**
		 * @return the custodio
		 */
		public CustodioDto getCustodio() {
			return custodio;
		}
		/**
		 * @param custodio the custodio to set
		 */
		public void setCustodio(CustodioDto custodio) {
			this.custodio = custodio;
		}
		/**
		 * @return the fechaActualizacion
		 */
		public Date getFechaActualizacion() {
			return fechaActualizacion;
		}
		/**
		 * @param fechaActualizacion the fechaActualizacion to set
		 */
		public void setFechaActualizacion(Date fechaActualizacion) {
			this.fechaActualizacion = fechaActualizacion;
		}
		/**
		 * @return the actualizado
		 */
		public Boolean getActualizado() {
			return actualizado;
		}
		/**
		 * @param actualizado the actualizado to set
		 */
		public void setActualizado(Boolean actualizado) {
			this.actualizado = actualizado;
		}


		/**
		 * @return the cuerpoEvento
		 */
		public CuerpoEventoCorporativo getCuerpoEvento() {
			return cuerpoEvento;
		}
		/**
		 * @param cuerpoEvento the cuerpoEvento to set
		 */
		public void setCuerpoEvento(CuerpoEventoCorporativo cuerpoEvento) {
			this.cuerpoEvento = cuerpoEvento;
		}
		/**
		 * @return the notasVec
		 */
		public List<NotasDTO> getNotasVec() {
			return notasVec;
		}
		/**
		 * @param notasVec the notasVec to set
		 */
		public void setNotasVec(List<NotasDTO> notasVec) {
			this.notasVec = notasVec;
		}
		/**
		 * @return the opcionesVec
		 */
		public List<OpcionesDTO> getOpcionesVec() {
			return opcionesVec;
		}
		/**
		 * @param opcionesVec the opcionesVec to set
		 */
		public void setOpcionesVec(List<OpcionesDTO> opcionesVec) {
			this.opcionesVec = opcionesVec;
		}
		/**
		 * @return the hashCode
		 */
		public Long getHashCodeGral() {
			
			String cadenaObjeto=toString();		
			this.hashCodeGral = (long) cadenaObjeto.hashCode();
			return hashCodeGral;
		}
		/**
		 * @param hashCodeGral the hashCodeGral to set
		 */
		public void setHashCodeGral(Long hashCodeGral) {
			this.hashCodeGral = hashCodeGral;
		}
		
		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "BitacoraBeanTest [idEventoCorporativo=" + idEventoCorporativo
					+ ", fechaCreacion=" + fechaCreacion + ", fechaRegistro="
					+ fechaRegistro + ", tipoValor=" + tipoValor + ", emisora="
					+ emisora + ", serie=" + serie + ", isin=" + isin
					+ ", tipoDerechoMO=" + tipoDerechoMO.toString() + ", tipoDerechoML="
					+ tipoDerechoML.toString() + ", tipoMercado=" + tipoMercado
					+ ", tipoEvento=" + tipoEvento + ", cuerpoEvento="
					+ cuerpoEvento + ", estado=" + estado + ", fechaIndeval="
					+ fechaIndeval + ", fechaCliente=" + fechaCliente
					+ ", fechaPago=" + fechaPago + ", prioridad=" + prioridad
					+ ", custodio=" + custodio + ", fechaActualizacion="
					+ fechaActualizacion + ", actualizado=" + actualizado
					+ ", notasVec="
					+ getHashCodeOpciones() + ", opcionesVec="
					+ getHashCodeNotas() + ", hashCodeGral=" + hashCodeGral + "]";
		}
		/**
		 * @return the hashCodeOpciones
		 */
		public Long getHashCodeOpciones() {
			
			String hashCodeTmp="";
			if(opcionesVec != null && opcionesVec.size() > 0 && opcionesVec.get(0) != null){
			for (OpcionesDTO opcionesEvco : opcionesVec)
			{
				hashCodeTmp = hashCodeTmp + opcionesEvco.toString();
			}
			
			Long hash=(long) hashCodeTmp.hashCode(); 
			
			this.hashCodeOpciones=hash;
			return hashCodeOpciones;
			}else{
				return 0l;
			}
		}
		/**
		 * @param hashCodeOpciones the hashCodeOpciones to set
		 */
		public void setHashCodeOpciones(Long hashCodeOpciones) {
			this.hashCodeOpciones = hashCodeOpciones;
		}
		/**
		 * @return the hashCodeNotas
		 */
		public Long getHashCodeNotas()
		{
			String hashCodeTmp="";
			if(notasVec != null && notasVec.size()>0){
			for (NotasDTO notasEvco : notasVec)
			{
				hashCodeTmp = hashCodeTmp + notasEvco.toString();
			}

			Long hash=(long) hashCodeTmp.hashCode(); 
	 		
			this.hashCodeNotas=hash;
			
			return hashCodeNotas;
			}else{
				return 0l;
			}
		}
		/**
		 * @param hashCodeNotas the hashCodeNotas to set
		 */
		public void setHashCodeNotas(Long hashCodeNotas) {
			this.hashCodeNotas = hashCodeNotas;
		}
		
		/**
		 * @return the hashCodeCuerpo
		 */
		public Long getHashCodeCuerpo() {
			
			String hashCodeCuerpoTmp= cuerpoEvento.toString();
			Long hashCodeCuerpoTMP = (long)hashCodeCuerpoTmp.hashCode();
			this.hashCodeCuerpo=hashCodeCuerpoTMP;
			
			return hashCodeCuerpo;
		}
		/**
		 * @param hashCodeCuerpo the hashCodeCuerpo to set
		 */
		public void setHashCodeCuerpo(Long hashCodeCuerpo) {
			this.hashCodeCuerpo = hashCodeCuerpo;
		}


		/**
		 * @return the archivosVec
		 */
		public List<ArchivosAdjuntosEvcoDTO> getArchivosVec() {
			return archivosVec;
		}
		/**
		 * @param archivosVec the archivosVec to set
		 */
		public void setArchivosVec(List<ArchivosAdjuntosEvcoDTO> archivosVec) {
			this.archivosVec = archivosVec;
		}
		/**
		 * @return the hashCodeArchivo
		 */
		public Long getHashCodeArchivo() {
			if(archivosVec != null && archivosVec.size() > 0){
				String hashCodeTmp="";
				
				
				for (ArchivosAdjuntosEvcoDTO archivoEvco : archivosVec)
				{
					hashCodeTmp = hashCodeTmp + archivoEvco.toString();
				}
	
				Long hash=(long) hashCodeTmp.hashCode(); 
		 		
				this.hashCodeArchivo=hash;
				return hashCodeArchivo;
			}else{
				return 0l;
			}
		}
		/**
		 * @param hashCodeArchivo the hashCodeArchivo to set
		 */
		public void setHashCodeArchivo(Long hashCodeArchivo) {
			this.hashCodeArchivo = hashCodeArchivo;
		}

	
}
