package com.indeval.portalinternacional.middleware.servicios.vo;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.indeval.portalinternacional.middleware.servicios.modelo.FastworkMessage;

public class FastworkMessageVO {
	
	private String bicCode;
	
	private String isin;
	
	private String tipoMensaje;
	
	private BigDecimal precio;
	
	private String iso;
	
	private Date fechaRegistro;
	
	private String dbkey;
	
	private List<String> camposSwift;	
	
	public FastworkMessageVO(FastworkMessage message) {
		
		this.bicCode = message.getBicCode();
		
		this.tipoMensaje = message.getTipoMensaje();
		
		this.fechaRegistro = message.getDateTime();
		
		this.iso = new String(message.getMessage(), StandardCharsets.UTF_8);
		
		this.dbkey = message.getDbkey(); 
		
		this.isin = message.getIsin();
		
		this.iso = new String(message.getMessage(), StandardCharsets.UTF_8);
		
	}
	
	public FastworkMessageVO(FastworkMessage message, String camposSwift) {
		
		this.bicCode = message.getBicCode();
		
		this.tipoMensaje = message.getTipoMensaje();
		
		this.fechaRegistro = message.getDateTime();
		
		this.iso = new String(message.getMessage(), StandardCharsets.UTF_8);
		
		this.dbkey = message.getDbkey(); 
		
		this.isin = message.getIsin();
		
		String ISO = this.iso;
		
		if(camposSwift != null && !camposSwift.isEmpty()) {
			
			this.camposSwift = new ArrayList<>();
			
			String[] campos = camposSwift.split(",");
			
			for(String campo : campos) {
				
				campo = campo.trim().replaceAll("\r", "").replaceAll("\n", "");
				
				if(!campo.isEmpty()) {
					
					int indxCampo = ISO.indexOf(campo.toUpperCase());
					
					if(indxCampo > -1) {
						
						int indxNL = ISO.indexOf("\n", indxCampo);
						
						String valorCampo = ISO.substring(indxCampo + campo.length(), indxNL);
						
						if(valorCampo.indexOf("/") > 0) {
						
							valorCampo = valorCampo.substring(valorCampo.indexOf("/"), valorCampo.indexOf("\r"));
							
						}
							
						valorCampo = valorCampo.replace("/","").replace(":","").trim();
						
						this.camposSwift.add(valorCampo);
						
					}else {
						
						this.camposSwift.add("&nbsp;");
						
					}
				}
			}
			
		}
		
	}
	
	public static List<FastworkMessageVO> getlistaResultados(List<FastworkMessage> messages) {
		List<FastworkMessageVO> listaResultados = new ArrayList<>();
		
		for(FastworkMessage message:messages) {
			
			FastworkMessageVO mensaje = new FastworkMessageVO(message);
			
			listaResultados.add(mensaje);
		}
		
		return listaResultados;
	}
	
	public static List<FastworkMessageVO> getlistaResultados(List<FastworkMessage> messages, String camposSwift) {
		List<FastworkMessageVO> listaResultados = new ArrayList<>();
		
		for(FastworkMessage message:messages) {
			
			FastworkMessageVO mensaje = new FastworkMessageVO(message, camposSwift);
			
			listaResultados.add(mensaje);
		}
		
		return listaResultados;
	}

	public String getBicCode() {
		return bicCode;
	}

	public String getIsin() {
		return isin;
	}

	public String getTipoMensaje() {
		return tipoMensaje;
	}

	public BigDecimal getPrecio() {
		return precio;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public String getDbkey() {
		return dbkey;
	}

	public List<String> getCamposSwift() {
		return camposSwift;
	}

	public String getIso() {
		return iso;
	}

	public String getIsoReporte() {
		return iso;
	}

}
