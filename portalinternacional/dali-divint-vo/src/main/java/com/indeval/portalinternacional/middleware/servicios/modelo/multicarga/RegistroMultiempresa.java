package com.indeval.portalinternacional.middleware.servicios.modelo.multicarga;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("registroMultiempresa")
public class RegistroMultiempresa {
	
	@XStreamAlias("uoi") 
	@XStreamAsAttribute
	private String uoi;
	
	@XStreamAlias("institucionOrigen") 
	@XStreamAsAttribute
	private String institucionOrigen;
	
	@XStreamAlias("institucionDestino") 
	@XStreamAsAttribute
	private String institucionDestino;

	public String getUoi() {
		return uoi;
	}

	public void setUoi(String uoi) {
		this.uoi = uoi;
	}

	public String getInstitucionOrigen() {
		return institucionOrigen;
	}

	public void setInstitucionOrigen(String institucionOrigen) {
		this.institucionOrigen = institucionOrigen;
	}

	public String getInstitucionDestino() {
		return institucionDestino;
	}

	public void setInstitucionDestino(String institucionDestino) {
		this.institucionDestino = institucionDestino;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof RegistroMultiempresa ){
			RegistroMultiempresa registroMultiempresa = (RegistroMultiempresa)obj;
			if(registroMultiempresa.getUoi().equalsIgnoreCase(this.uoi)){
				return true;
			}
			else{
				return false;
			}
		}else{
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.uoi.hashCode();
	}

}
