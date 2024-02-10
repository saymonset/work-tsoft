/**
 * Bursatec - Portal Internacional
 * Copyrigth (c) 2015 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.servicios.modelo.filetransfer.beneficiario.tipoformato;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Objeto para el proceso de file transfer de formatos W8BEN-E.
 * 
 * @author Pablo Balderas
 *
 */
public class FileTransferFormaW8BENE2016 extends FileTransferForma {

	/** Name of organitation */
	private String campo1;
	
	private String campo2;
	
	private String campo3;
	
	private String campo4a;
	
	private String campo4b;
	
	private String campo5;
	
	private String campo6a;
	
	private String campo6b;
	
	private String campo6c;
	
	private String campo7a;
	
	private String campo7b;
	
	private String campo7c;
	
	private String campo8;
	
	private String campo9a;
	
	private String campo9b;
	
	private String campo10;
	
	private String campo11a;
	private String campo11b;
	private String campo11c;
	private String campo11d;
	private String campo11e;
	
	private String campo12a;
	
	private String campo12b;
	
	private String campo12c;
	
	private String campo13;
	
	private String campo14a;
	
	private String campo14b;
	
	private String campo14c;
	
	private String campo15a;
	
	private String campo15b;
	
	private String campo15c;
	
	private String campo15d;
	
	private String campo15e;
	
	private String printName;
	
	private String anexo1;
	
	private String anexo2;
	
	private String anexo3;
	
	private String anexo4;
	
	private String anexo5;
	
	private String anexo6;
	
	/**  */
	private String formaW8BENE;

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj == null) {return false;}
		if(obj == this) {return true;}
		if(!(obj instanceof FileTransferFormaW8BENE2016)) {return false;}
		FileTransferFormaW8BENE2016 other = (FileTransferFormaW8BENE2016)obj;
		boolean isEquals = false;
		final EqualsBuilder eqb = new EqualsBuilder();
		eqb.append(campo1,other.campo1);
		eqb.append(getClaveCustodio(),other.getClaveCustodio());
		eqb.append(getTipoBeneficiario(),other.getTipoBeneficiario());
		isEquals = eqb.isEquals();
		return isEquals;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.indeval.portalinternacional.middleware.servicios.modelo.filetransfer.beneficiario.tipoformato.FileTransferForma#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder(17,31).
		   append(campo1!=null?campo1:0).
		   append(getClaveCustodio() != null ? getClaveCustodio() : 0).
		   append(getTipoBeneficiario() != null ? getTipoBeneficiario() : 0).toHashCode();		
	}
	
	/**
	 * Método para obtener el atributo campo1
	 * @return El atributo campo1
	 */
	public String getCampo1() {
		return campo1;
	}

	/**
	 * Método para establecer el atributo campo1
	 * @param campo1 El valor del atributo campo1 a establecer.
	 */
	public void setCampo1(String campo1) {
		this.campo1 = campo1;
	}

	/**
	 * Método para obtener el atributo campo2
	 * @return El atributo campo2
	 */
	public String getCampo2() {
		return campo2;
	}

	/**
	 * Método para establecer el atributo campo2
	 * @param campo2 El valor del atributo campo2 a establecer.
	 */
	public void setCampo2(String campo2) {
		this.campo2 = campo2;
	}

	/**
	 * Método para obtener el atributo campo3
	 * @return El atributo campo3
	 */
	public String getCampo3() {
		return campo3;
	}

	/**
	 * Método para establecer el atributo campo3
	 * @param campo3 El valor del atributo campo3 a establecer.
	 */
	public void setCampo3(String campo3) {
		this.campo3 = campo3;
	}

	/**
	 * Método para obtener el atributo campo4a
	 * @return El atributo campo4a
	 */
	public String getCampo4a() {
		return campo4a;
	}

	/**
	 * Método para establecer el atributo campo4a
	 * @param campo4a El valor del atributo campo4a a establecer.
	 */
	public void setCampo4a(String campo4a) {
		this.campo4a = campo4a;
	}

	/**
	 * Método para obtener el atributo campo4b
	 * @return El atributo campo4b
	 */
	public String getCampo4b() {
		return campo4b;
	}

	/**
	 * Método para establecer el atributo campo4b
	 * @param campo4b El valor del atributo campo4b a establecer.
	 */
	public void setCampo4b(String campo4b) {
		this.campo4b = campo4b;
	}

	/**
	 * Método para obtener el atributo campo5
	 * @return El atributo campo5
	 */
	public String getCampo5() {
		return campo5;
	}

	/**
	 * Método para establecer el atributo campo5
	 * @param campo5 El valor del atributo campo5 a establecer.
	 */
	public void setCampo5(String campo5) {
		this.campo5 = campo5;
	}

	/**
	 * Método para obtener el atributo campo6a
	 * @return El atributo campo6a
	 */
	public String getCampo6a() {
		return campo6a;
	}

	/**
	 * Método para establecer el atributo campo6a
	 * @param campo6a El valor del atributo campo6a a establecer.
	 */
	public void setCampo6a(String campo6a) {
		this.campo6a = campo6a;
	}

	/**
	 * Método para obtener el atributo campo6b
	 * @return El atributo campo6b
	 */
	public String getCampo6b() {
		return campo6b;
	}

	/**
	 * Método para establecer el atributo campo6b
	 * @param campo6b El valor del atributo campo6b a establecer.
	 */
	public void setCampo6b(String campo6b) {
		this.campo6b = campo6b;
	}

	/**
	 * Método para obtener el atributo campo6c
	 * @return El atributo campo6c
	 */
	public String getCampo6c() {
		return campo6c;
	}

	/**
	 * Método para establecer el atributo campo6c
	 * @param campo6c El valor del atributo campo6c a establecer.
	 */
	public void setCampo6c(String campo6c) {
		this.campo6c = campo6c;
	}

	/**
	 * Método para obtener el atributo campo7a
	 * @return El atributo campo7a
	 */
	public String getCampo7a() {
		return campo7a;
	}

	/**
	 * Método para establecer el atributo campo7a
	 * @param campo7a El valor del atributo campo7a a establecer.
	 */
	public void setCampo7a(String campo7a) {
		this.campo7a = campo7a;
	}

	/**
	 * Método para obtener el atributo campo7b
	 * @return El atributo campo7b
	 */
	public String getCampo7b() {
		return campo7b;
	}

	/**
	 * Método para establecer el atributo campo7b
	 * @param campo7b El valor del atributo campo7b a establecer.
	 */
	public void setCampo7b(String campo7b) {
		this.campo7b = campo7b;
	}

	/**
	 * Método para obtener el atributo campo7c
	 * @return El atributo campo7c
	 */
	public String getCampo7c() {
		return campo7c;
	}

	/**
	 * Método para establecer el atributo campo7c
	 * @param campo7c El valor del atributo campo7c a establecer.
	 */
	public void setCampo7c(String campo7c) {
		this.campo7c = campo7c;
	}

	/**
	 * Método para obtener el atributo campo8
	 * @return El atributo campo8
	 */
	public String getCampo8() {
		return campo8;
	}

	/**
	 * Método para establecer el atributo campo8
	 * @param campo8 El valor del atributo campo8 a establecer.
	 */
	public void setCampo8(String campo8) {
		this.campo8 = campo8;
	}

	/**
	 * Método para obtener el atributo campo9a
	 * @return El atributo campo9a
	 */
	public String getCampo9a() {
		return campo9a;
	}

	/**
	 * Método para establecer el atributo campo9a
	 * @param campo9a El valor del atributo campo9a a establecer.
	 */
	public void setCampo9a(String campo9a) {
		this.campo9a = campo9a;
	}

	/**
	 * Método para obtener el atributo campo9b
	 * @return El atributo campo9b
	 */
	public String getCampo9b() {
		return campo9b;
	}

	/**
	 * Método para establecer el atributo campo9b
	 * @param campo9b El valor del atributo campo9b a establecer.
	 */
	public void setCampo9b(String campo9b) {
		this.campo9b = campo9b;
	}

	/**
	 * Método para obtener el atributo campo10
	 * @return El atributo campo10
	 */
	public String getCampo10() {
		return campo10;
	}

	/**
	 * Método para establecer el atributo campo10
	 * @param campo10 El valor del atributo campo10 a establecer.
	 */
	public void setCampo10(String campo10) {
		this.campo10 = campo10;
	}

	/**
	 * Método para obtener el atributo campo12a
	 * @return El atributo campo12a
	 */
	public String getCampo12a() {
		return campo12a;
	}

	/**
	 * Método para establecer el atributo campo12a
	 * @param campo12a El valor del atributo campo12a a establecer.
	 */
	public void setCampo12a(String campo12a) {
		this.campo12a = campo12a;
	}

	/**
	 * Método para obtener el atributo campo12b
	 * @return El atributo campo12b
	 */
	public String getCampo12b() {
		return campo12b;
	}

	/**
	 * Método para establecer el atributo campo12b
	 * @param campo12b El valor del atributo campo12b a establecer.
	 */
	public void setCampo12b(String campo12b) {
		this.campo12b = campo12b;
	}

	/**
	 * Método para obtener el atributo campo12c
	 * @return El atributo campo12c
	 */
	public String getCampo12c() {
		return campo12c;
	}

	/**
	 * Método para establecer el atributo campo12c
	 * @param campo12c El valor del atributo campo12c a establecer.
	 */
	public void setCampo12c(String campo12c) {
		this.campo12c = campo12c;
	}

	/**
	 * Método para obtener el atributo campo13
	 * @return El atributo campo13
	 */
	public String getCampo13() {
		return campo13;
	}

	/**
	 * Método para establecer el atributo campo13
	 * @param campo13 El valor del atributo campo13 a establecer.
	 */
	public void setCampo13(String campo13) {
		this.campo13 = campo13;
	}

	/**
	 * Método para obtener el atributo campo14a
	 * @return El atributo campo14a
	 */
	public String getCampo14a() {
		return campo14a;
	}

	/**
	 * Método para establecer el atributo campo14a
	 * @param campo14a El valor del atributo campo14a a establecer.
	 */
	public void setCampo14a(String campo14a) {
		this.campo14a = campo14a;
	}

	/**
	 * Método para obtener el atributo campo14b
	 * @return El atributo campo14b
	 */
	public String getCampo14b() {
		return campo14b;
	}

	/**
	 * Método para establecer el atributo campo14b
	 * @param campo14b El valor del atributo campo14b a establecer.
	 */
	public void setCampo14b(String campo14b) {
		this.campo14b = campo14b;
	}

	/**
	 * Método para obtener el atributo campo14c
	 * @return El atributo campo14c
	 */
	public String getCampo14c() {
		return campo14c;
	}

	/**
	 * Método para establecer el atributo campo14c
	 * @param campo14c El valor del atributo campo14c a establecer.
	 */
	public void setCampo14c(String campo14c) {
		this.campo14c = campo14c;
	}

	/**
	 * Método para obtener el atributo campo15a
	 * @return El atributo campo15a
	 */
	public String getCampo15a() {
		return campo15a;
	}

	/**
	 * Método para establecer el atributo campo15a
	 * @param campo15a El valor del atributo campo15a a establecer.
	 */
	public void setCampo15a(String campo15a) {
		this.campo15a = campo15a;
	}

	/**
	 * Método para obtener el atributo campo15b
	 * @return El atributo campo15b
	 */
	public String getCampo15b() {
		return campo15b;
	}

	/**
	 * Método para establecer el atributo campo15b
	 * @param campo15b El valor del atributo campo15b a establecer.
	 */
	public void setCampo15b(String campo15b) {
		this.campo15b = campo15b;
	}

	/**
	 * Método para obtener el atributo campo15c
	 * @return El atributo campo15c
	 */
	public String getCampo15c() {
		return campo15c;
	}

	/**
	 * Método para establecer el atributo campo15c
	 * @param campo15c El valor del atributo campo15c a establecer.
	 */
	public void setCampo15c(String campo15c) {
		this.campo15c = campo15c;
	}

	/**
	 * Método para obtener el atributo campo15d
	 * @return El atributo campo15d
	 */
	public String getCampo15d() {
		return campo15d;
	}

	/**
	 * Método para establecer el atributo campo15d
	 * @param campo15d El valor del atributo campo15d a establecer.
	 */
	public void setCampo15d(String campo15d) {
		this.campo15d = campo15d;
	}

	/**
	 * Método para obtener el atributo campo15e
	 * @return El atributo campo15e
	 */
	public String getCampo15e() {
		return campo15e;
	}

	/**
	 * Método para establecer el atributo campo15e
	 * @param campo15e El valor del atributo campo15e a establecer.
	 */
	public void setCampo15e(String campo15e) {
		this.campo15e = campo15e;
	}

	/**
	 * Método para obtener el atributo anexo1
	 * @return El atributo anexo1
	 */
	public String getAnexo1() {
		return anexo1;
	}

	/**
	 * Método para establecer el atributo anexo1
	 * @param anexo1 El valor del atributo anexo1 a establecer.
	 */
	public void setAnexo1(String anexo1) {
		this.anexo1 = anexo1;
	}

	/**
	 * Método para obtener el atributo anexo2
	 * @return El atributo anexo2
	 */
	public String getAnexo2() {
		return anexo2;
	}

	/**
	 * Método para establecer el atributo anexo2
	 * @param anexo2 El valor del atributo anexo2 a establecer.
	 */
	public void setAnexo2(String anexo2) {
		this.anexo2 = anexo2;
	}

	/**
	 * Método para obtener el atributo anexo3
	 * @return El atributo anexo3
	 */
	public String getAnexo3() {
		return anexo3;
	}

	/**
	 * Método para establecer el atributo anexo3
	 * @param anexo3 El valor del atributo anexo3 a establecer.
	 */
	public void setAnexo3(String anexo3) {
		this.anexo3 = anexo3;
	}

	/**
	 * Método para obtener el atributo anexo4
	 * @return El atributo anexo4
	 */
	public String getAnexo4() {
		return anexo4;
	}

	/**
	 * Método para establecer el atributo anexo4
	 * @param anexo4 El valor del atributo anexo4 a establecer.
	 */
	public void setAnexo4(String anexo4) {
		this.anexo4 = anexo4;
	}

	/**
	 * Método para obtener el atributo anexo5
	 * @return El atributo anexo5
	 */
	public String getAnexo5() {
		return anexo5;
	}

	/**
	 * Método para establecer el atributo anexo5
	 * @param anexo5 El valor del atributo anexo5 a establecer.
	 */
	public void setAnexo5(String anexo5) {
		this.anexo5 = anexo5;
	}

	/**
	 * Método para obtener el atributo anexo6
	 * @return El atributo anexo6
	 */
	public String getAnexo6() {
		return anexo6;
	}

	/**
	 * Método para establecer el atributo anexo6
	 * @param anexo6 El valor del atributo anexo6 a establecer.
	 */
	public void setAnexo6(String anexo6) {
		this.anexo6 = anexo6;
	}

	/**
	 * Método para obtener el atributo printName
	 * @return El atributo printName
	 */
	public String getPrintName() {
		return printName;
	}

	/**
	 * Método para establecer el atributo printName
	 * @param printName El valor del atributo printName a establecer.
	 */
	public void setPrintName(String printName) {
		this.printName = printName;
	}

	/**
	 * Método para obtener el atributo formaW8BENE
	 * @return El atributo formaW8BENE
	 */
	public String getFormaW8BENE() {
		return formaW8BENE;
	}

	/**
	 * Método para establecer el atributo formaW8BENE
	 * @param formaW8BENE El valor del atributo formaW8BENE a establecer.
	 */
	public void setFormaW8BENE(String formaW8BENE) {
		this.formaW8BENE = formaW8BENE;
	}

	/**
	 * Método para obtener el atributo campo11a
	 * @return El atributo campo11a
	 */
	public String getCampo11a() {
		return campo11a;
	}

	/**
	 * Método para establecer el atributo campo11a
	 * @param campo11a El valor del atributo campo11a a establecer.
	 */
	public void setCampo11a(String campo11a) {
		this.campo11a = campo11a;
	}

	/**
	 * Método para obtener el atributo campo11b
	 * @return El atributo campo11b
	 */
	public String getCampo11b() {
		return campo11b;
	}

	/**
	 * Método para establecer el atributo campo11b
	 * @param campo11b El valor del atributo campo11b a establecer.
	 */
	public void setCampo11b(String campo11b) {
		this.campo11b = campo11b;
	}

	/**
	 * Método para obtener el atributo campo11c
	 * @return El atributo campo11c
	 */
	public String getCampo11c() {
		return campo11c;
	}

	/**
	 * Método para establecer el atributo campo11c
	 * @param campo11c El valor del atributo campo11c a establecer.
	 */
	public void setCampo11c(String campo11c) {
		this.campo11c = campo11c;
	}

	/**
	 * Método para obtener el atributo campo11d
	 * @return El atributo campo11d
	 */
	public String getCampo11d() {
		return campo11d;
	}

	/**
	 * Método para establecer el atributo campo11d
	 * @param campo11d El valor del atributo campo11d a establecer.
	 */
	public void setCampo11d(String campo11d) {
		this.campo11d = campo11d;
	}

	/**
	 * Método para obtener el atributo campo11e
	 * @return El atributo campo11e
	 */
	public String getCampo11e() {
		return campo11e;
	}

	/**
	 * Método para establecer el atributo campo11e
	 * @param campo11e El valor del atributo campo11e a establecer.
	 */
	public void setCampo11e(String campo11e) {
		this.campo11e = campo11e;
	}
}
