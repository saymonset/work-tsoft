package com.indeval.portalinternacional.middleware.servicios.modelo.multicarga;

import java.io.Serializable;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("registroAdp")
public class RegistroAdp implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@XStreamAlias("uoi") 
	@XStreamAsAttribute
	private String uoi;
	
	@XStreamAlias("adp") 
	@XStreamAsAttribute
	private String adp;

	public String getUoi() {
		return uoi;
	}

	public void setUoi(String uoi) {
		this.uoi = uoi;
	}

	public String getAdp() {
		return adp;
	}

	public void setAdp(String adp) {
		this.adp = adp;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof RegistroAdp ){
			RegistroAdp registroAdp = (RegistroAdp)obj;
			if(registroAdp.getUoi().equalsIgnoreCase(this.uoi)){
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
