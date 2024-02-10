package com.indeval.portalinternacional.middleware.servicios.fo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class FastworkMessageFO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String isin;
	
	private String biccode;
	
	private String[] tipoMensajesSelect;
	
	private String[] monedasSelect;
	
	private BigDecimal monto;
	
	private Date fechaInicial;
	
	private Date fechaFinal;
	
	private String camposSwift;
	
	private String buscar;

	public String getIsin() {
		return isin;
	}

	public void setIsin(String isin) {
		if(isin != null) {
			this.isin = isin.toUpperCase();
		}
	}

	public String getBiccode() {
		return biccode;
	}

	public void setBiccode(String biccode) {
		if(biccode != null) {
			this.biccode = biccode.toUpperCase();
		}
	}

	public String[] getTipoMensajesSelect() {
		return tipoMensajesSelect;
	}

	public void setTipoMensajesSelect(String[] tipoMensajesSelect) {
		this.tipoMensajesSelect = tipoMensajesSelect;
	}

	public String[] getMonedasSelect() {
		return monedasSelect;
	}

	public void setMonedasSelect(String[] monedasSelect) {
		this.monedasSelect = monedasSelect;
	}

	public BigDecimal getMonto() {
		return monto;
	}

	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}

	public Date getFechaInicial() {
		return fechaInicial;
	}

	public void setFechaInicial(Date fechaInicial) {
		this.fechaInicial = fechaInicial;
	}

	public Date getFechaFinal() {
		return fechaFinal;
	}

	public void setFechaFinal(Date fechaFinal) {
		this.fechaFinal = fechaFinal;
	}

	public String getCamposSwift() {
		return camposSwift;
	}

	public void setCamposSwift(String camposSwift) {
		if(camposSwift != null) {
			this.camposSwift = camposSwift.replaceAll("\\s+","").toUpperCase();
		}
	}

	public String getBuscar() {
		return buscar;
	}

	public void setBuscar(String buscar) {
		this.buscar = buscar;
	}
	
	public String getMonedaFiltroDesc() {
		if (monedasSelect != null && monedasSelect.length > 0) {
		    StringBuilder nameBuilder = new StringBuilder();
		
		    for (String n : monedasSelect) {
		    	if(!n.equals("")) {
		    		nameBuilder.append(n).append(",");
		    	}
		    }

		    if(!(nameBuilder.lastIndexOf(",") < 0)) {
		    	nameBuilder.deleteCharAt(nameBuilder.lastIndexOf(","));
		    }
		
		    return nameBuilder.toString();
		} else {
		    return "";
		}

	}
	
	public String getTipoMensajesFiltroDesc() {
		if (tipoMensajesSelect != null && tipoMensajesSelect.length > 0) {
		    StringBuilder nameBuilder = new StringBuilder();
		
		    for (String n : tipoMensajesSelect) {
		    	if(!n.equals("")) {
		    		nameBuilder.append(n).append(",");
		    	}
		    }
		    
		    if(!(nameBuilder.lastIndexOf(",") < 0)) {
		    	nameBuilder.deleteCharAt(nameBuilder.lastIndexOf(","));
		    }
		
		    return nameBuilder.toString();
		} else {
		    return "";
		}

	}
	
	public String getTipoMensajesForQuery() {
		if (tipoMensajesSelect != null && tipoMensajesSelect.length > 0) {
		    StringBuilder nameBuilder = new StringBuilder();
		
		    for (String n : tipoMensajesSelect) {
		    	if(!n.equals("")) {
		    		nameBuilder.append("'").append(n).append("',");
		    	}
		    }
		    
		    if(!(nameBuilder.lastIndexOf(",") < 0)) {
		    	nameBuilder.deleteCharAt(nameBuilder.lastIndexOf(","));
		    }
		
		    return nameBuilder.toString();
		} else {
		    return "";
		}

	}
	
	public String getMonedaForQuery() {
		if (monedasSelect != null && monedasSelect.length > 0) {
		    StringBuilder nameBuilder = new StringBuilder();
		
		    for (String n : monedasSelect) {
		    	if(!n.equals("")) {
		    		nameBuilder.append("'").append(n).append("',");
		    	}
		    }

		    if(!(nameBuilder.lastIndexOf(",") < 0)) {
		    	nameBuilder.deleteCharAt(nameBuilder.lastIndexOf(","));
		    }
		
		    return nameBuilder.toString();
		} else {
		    return "";
		}

	}
	

}
