/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.servicios.vo;

import java.io.Serializable;


public class UsuarioVO implements Serializable{
	
    
	private static final long serialVersionUID = 1L;
	
	private String login;
	private String user;
    private String id;
    private String razon;
    private String folio;
    private String nombreCorto;
    private Boolean isUserIndeval = Boolean.FALSE;
    private Boolean isUserBanxico = Boolean.FALSE;
    private String idOriginal;
    private String folioOriginal;
    private boolean conCertificado;
    private String numeroSerieCertificado;
    
    
    
	public String getUser() {
		return user;
	}
    public String getId() {
        return id;
    }
    
    public String getFolio() {
        return folio;
    }
    public String getRazon() {
        return razon;
    }
    public String getNombreCorto() {
        return nombreCorto;
    }
    public String getLogin() {
        return login;
    }
    public Boolean getIsUserIndeval() {
        return isUserIndeval;
    }
    public Boolean getIsUserBanxico() {
        return isUserBanxico;
    }
    public String getFolioOriginal() {
        return folioOriginal;
    }
    public String getIdOriginal() {
        return idOriginal;
    }
    public boolean isConCertificado() {
        return conCertificado;
    }
    public String getNumeroSerieCertificado() {
        return numeroSerieCertificado;
    }
    
    
    
    public void setUser(String user) {}
    public void setId(String id) {}
    public void setRazon(String razon) {}
    public void setFolio(String folio) {}
    public void setNombreCorto(String nombreCorto) {}
    public void setLogin(String login) {}
    public void setIsUserIndeval(Boolean isUserIndeval) {}
    public void setIsUserBanxico(Boolean isUserBanxico) {}
    public void setFolioOriginal(String folioOriginal) {}
    public void setIdOriginal(String idOriginal) {}
    public void setPasswd(String passwd) {}
    public void setConCertificado(boolean conCertificado) {}
    public void setNumeroSerieCertificado(String numeroSerieCertificado) {}    
    
    
    
    public void _setUser(String user) {
        this.user = user;
    }
    public void _setId(String id) {
        this.id = id;
    }
    public void _setRazon(String razon) {
        this.razon = razon;
    }
    public void _setFolio(String folio) {
        this.folio = folio;
    }
    public void _setNombreCorto(String nombreCorto) {
        this.nombreCorto = nombreCorto;
    }
	public void _setIsUserIndeval(Boolean isUserIndeval) {
		this.isUserIndeval = isUserIndeval;
	}
	public void _setLogin(String login) {
		this.login = login;
	}
    public void _setIsUserBanxico(Boolean isUserBanxico) {
        this.isUserBanxico = isUserBanxico;
    }
    public void _setFolioOriginal(String folioOriginal) {
        this.folioOriginal = folioOriginal;
    }
    public void _setIdOriginal(String idOriginal) {
        this.idOriginal = idOriginal;
    }
    public void _setConCertificado(boolean conCertificado) {
        this.conCertificado = conCertificado;
    }
    public void _setNumeroSerieCertificado(String numeroSerieCertificado) {
        this.numeroSerieCertificado = numeroSerieCertificado;
    }

}
