/**
 * Bursatec - Portal Internacional
 * Copyrigth (c) 2015 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.servicios.modelo.filetransfer.beneficiario.tipoformato;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Objeto para el proceso de file transfer de formatos W8IMY 2015.
 * 
 * @author Pablo Balderas
 *
 */
public class FileTransferFormaW8IMY2015 extends FileTransferForma {

	private String campo1;
	
	private String campo2;
	
	private String campo3;
	
	private String campo4;

	private String campo5;
	
	/** Domicilio */
	private String campo6a;
	private String campo6b;
	private String campo6c;
	
	/** Domicilio de correspondencia */
	private String campo7a;
	private String campo7b;
	private String campo7c;
	
	private String campo8;
	private String campo8a;
	private String campo8b;
	private String campo8c;
	private String campo8d;
	private String campo8e;
	
	private String campo9;
	
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
	
	/** Anexos para capturar los datos de las partes asociadas al campo 4 */
	private String campo4Anexo1;
	private String campo4Anexo2;
	private String campo4Anexo3;
	private String campo4Anexo4;
	private String campo4Anexo5;
	private String campo4Anexo6;
	private String campo4Anexo7;
	private String campo4Anexo8;
	private String campo4Anexo9;
	private String campo4Anexo10;
	private String campo4Anexo11;
	
	/** Anexos para capturar los datos de las partes asociadas al campo 4 */
	private String campo5Anexo1;
	private String campo5Anexo2;
	private String campo5Anexo3;
	private String campo5Anexo4;
	private String campo5Anexo5;
	private String campo5Anexo6;
	
	/** Part XXIX Certification **/
	private String certification;
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj == null) {return false;}
		if(obj == this) {return true;}
		if(!(obj instanceof FileTransferFormaW8IMY2015)) {return false;}
		FileTransferFormaW8IMY2015 other = (FileTransferFormaW8IMY2015)obj;
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
	 * Método para obtener el atributo campo4
	 * @return El atributo campo4
	 */
	public String getCampo4() {
		return campo4;
	}

	/**
	 * Método para establecer el atributo campo4
	 * @param campo4 El valor del atributo campo4 a establecer.
	 */
	public void setCampo4(String campo4) {
		this.campo4 = campo4;
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
	 * Método para obtener el atributo campo8a
	 * @return El atributo campo8a
	 */
	public String getCampo8a() {
		return campo8a;
	}

	/**
	 * Método para establecer el atributo campo8a
	 * @param campo8a El valor del atributo campo8a a establecer.
	 */
	public void setCampo8a(String campo8a) {
		this.campo8a = campo8a;
	}

	/**
	 * Método para obtener el atributo campo8b
	 * @return El atributo campo8b
	 */
	public String getCampo8b() {
		return campo8b;
	}

	/**
	 * Método para establecer el atributo campo8b
	 * @param campo8b El valor del atributo campo8b a establecer.
	 */
	public void setCampo8b(String campo8b) {
		this.campo8b = campo8b;
	}

	/**
	 * Método para obtener el atributo campo8c
	 * @return El atributo campo8c
	 */
	public String getCampo8c() {
		return campo8c;
	}

	/**
	 * Método para establecer el atributo campo8c
	 * @param campo8c El valor del atributo campo8c a establecer.
	 */
	public void setCampo8c(String campo8c) {
		this.campo8c = campo8c;
	}

	/**
	 * Método para obtener el atributo campo8d
	 * @return El atributo campo8d
	 */
	public String getCampo8d() {
		return campo8d;
	}

	/**
	 * Método para establecer el atributo campo8d
	 * @param campo8d El valor del atributo campo8d a establecer.
	 */
	public void setCampo8d(String campo8d) {
		this.campo8d = campo8d;
	}

	/**
	 * Método para obtener el atributo campo8e
	 * @return El atributo campo8e
	 */
	public String getCampo8e() {
		return campo8e;
	}

	/**
	 * Método para establecer el atributo campo8e
	 * @param campo8e El valor del atributo campo8e a establecer.
	 */
	public void setCampo8e(String campo8e) {
		this.campo8e = campo8e;
	}

	/**
	 * Método para obtener el atributo campo9
	 * @return El atributo campo9
	 */
	public String getCampo9() {
		return campo9;
	}

	/**
	 * Método para establecer el atributo campo9
	 * @param campo9 El valor del atributo campo9 a establecer.
	 */
	public void setCampo9(String campo9) {
		this.campo9 = campo9;
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

	/**
	 * Método para obtener el atributo campo4Anexo1
	 * @return El atributo campo4Anexo1
	 */
	public String getCampo4Anexo1() {
		return campo4Anexo1;
	}

	/**
	 * Método para establecer el atributo campo4Anexo1
	 * @param campo4Anexo1 El valor del atributo campo4Anexo1 a establecer.
	 */
	public void setCampo4Anexo1(String campo4Anexo1) {
		this.campo4Anexo1 = campo4Anexo1;
	}

	/**
	 * Método para obtener el atributo campo4Anexo2
	 * @return El atributo campo4Anexo2
	 */
	public String getCampo4Anexo2() {
		return campo4Anexo2;
	}

	/**
	 * Método para establecer el atributo campo4Anexo2
	 * @param campo4Anexo2 El valor del atributo campo4Anexo2 a establecer.
	 */
	public void setCampo4Anexo2(String campo4Anexo2) {
		this.campo4Anexo2 = campo4Anexo2;
	}

	/**
	 * Método para obtener el atributo campo4Anexo3
	 * @return El atributo campo4Anexo3
	 */
	public String getCampo4Anexo3() {
		return campo4Anexo3;
	}

	/**
	 * Método para establecer el atributo campo4Anexo3
	 * @param campo4Anexo3 El valor del atributo campo4Anexo3 a establecer.
	 */
	public void setCampo4Anexo3(String campo4Anexo3) {
		this.campo4Anexo3 = campo4Anexo3;
	}

	/**
	 * Método para obtener el atributo campo4Anexo4
	 * @return El atributo campo4Anexo4
	 */
	public String getCampo4Anexo4() {
		return campo4Anexo4;
	}

	/**
	 * Método para establecer el atributo campo4Anexo4
	 * @param campo4Anexo4 El valor del atributo campo4Anexo4 a establecer.
	 */
	public void setCampo4Anexo4(String campo4Anexo4) {
		this.campo4Anexo4 = campo4Anexo4;
	}

	/**
	 * Método para obtener el atributo campo4Anexo5
	 * @return El atributo campo4Anexo5
	 */
	public String getCampo4Anexo5() {
		return campo4Anexo5;
	}

	/**
	 * Método para establecer el atributo campo4Anexo5
	 * @param campo4Anexo5 El valor del atributo campo4Anexo5 a establecer.
	 */
	public void setCampo4Anexo5(String campo4Anexo5) {
		this.campo4Anexo5 = campo4Anexo5;
	}

	/**
	 * Método para obtener el atributo campo4Anexo6
	 * @return El atributo campo4Anexo6
	 */
	public String getCampo4Anexo6() {
		return campo4Anexo6;
	}

	/**
	 * Método para establecer el atributo campo4Anexo6
	 * @param campo4Anexo6 El valor del atributo campo4Anexo6 a establecer.
	 */
	public void setCampo4Anexo6(String campo4Anexo6) {
		this.campo4Anexo6 = campo4Anexo6;
	}

	/**
	 * Método para obtener el atributo campo4Anexo7
	 * @return El atributo campo4Anexo7
	 */
	public String getCampo4Anexo7() {
		return campo4Anexo7;
	}

	/**
	 * Método para establecer el atributo campo4Anexo7
	 * @param campo4Anexo7 El valor del atributo campo4Anexo7 a establecer.
	 */
	public void setCampo4Anexo7(String campo4Anexo7) {
		this.campo4Anexo7 = campo4Anexo7;
	}

	/**
	 * Método para obtener el atributo campo4Anexo8
	 * @return El atributo campo4Anexo8
	 */
	public String getCampo4Anexo8() {
		return campo4Anexo8;
	}

	/**
	 * Método para establecer el atributo campo4Anexo8
	 * @param campo4Anexo8 El valor del atributo campo4Anexo8 a establecer.
	 */
	public void setCampo4Anexo8(String campo4Anexo8) {
		this.campo4Anexo8 = campo4Anexo8;
	}

	/**
	 * Método para obtener el atributo campo4Anexo9
	 * @return El atributo campo4Anexo9
	 */
	public String getCampo4Anexo9() {
		return campo4Anexo9;
	}

	/**
	 * Método para establecer el atributo campo4Anexo9
	 * @param campo4Anexo9 El valor del atributo campo4Anexo9 a establecer.
	 */
	public void setCampo4Anexo9(String campo4Anexo9) {
		this.campo4Anexo9 = campo4Anexo9;
	}

	/**
	 * Método para obtener el atributo campo4Anexo10
	 * @return El atributo campo4Anexo10
	 */
	public String getCampo4Anexo10() {
		return campo4Anexo10;
	}

	/**
	 * Método para establecer el atributo campo4Anexo10
	 * @param campo4Anexo10 El valor del atributo campo4Anexo10 a establecer.
	 */
	public void setCampo4Anexo10(String campo4Anexo10) {
		this.campo4Anexo10 = campo4Anexo10;
	}

	/**
	 * Método para obtener el atributo campo4Anexo11
	 * @return El atributo campo4Anexo11
	 */
	public String getCampo4Anexo11() {
		return campo4Anexo11;
	}

	/**
	 * Método para establecer el atributo campo4Anexo11
	 * @param campo4Anexo11 El valor del atributo campo4Anexo11 a establecer.
	 */
	public void setCampo4Anexo11(String campo4Anexo11) {
		this.campo4Anexo11 = campo4Anexo11;
	}

	/**
	 * Método para obtener el atributo campo5Anexo1
	 * @return El atributo campo5Anexo1
	 */
	public String getCampo5Anexo1() {
		return campo5Anexo1;
	}

	/**
	 * Método para establecer el atributo campo5Anexo1
	 * @param campo5Anexo1 El valor del atributo campo5Anexo1 a establecer.
	 */
	public void setCampo5Anexo1(String campo5Anexo1) {
		this.campo5Anexo1 = campo5Anexo1;
	}

	/**
	 * Método para obtener el atributo campo5Anexo2
	 * @return El atributo campo5Anexo2
	 */
	public String getCampo5Anexo2() {
		return campo5Anexo2;
	}

	/**
	 * Método para establecer el atributo campo5Anexo2
	 * @param campo5Anexo2 El valor del atributo campo5Anexo2 a establecer.
	 */
	public void setCampo5Anexo2(String campo5Anexo2) {
		this.campo5Anexo2 = campo5Anexo2;
	}

	/**
	 * Método para obtener el atributo campo5Anexo3
	 * @return El atributo campo5Anexo3
	 */
	public String getCampo5Anexo3() {
		return campo5Anexo3;
	}

	/**
	 * Método para establecer el atributo campo5Anexo3
	 * @param campo5Anexo3 El valor del atributo campo5Anexo3 a establecer.
	 */
	public void setCampo5Anexo3(String campo5Anexo3) {
		this.campo5Anexo3 = campo5Anexo3;
	}

	/**
	 * Método para obtener el atributo campo5Anexo4
	 * @return El atributo campo5Anexo4
	 */
	public String getCampo5Anexo4() {
		return campo5Anexo4;
	}

	/**
	 * Método para establecer el atributo campo5Anexo4
	 * @param campo5Anexo4 El valor del atributo campo5Anexo4 a establecer.
	 */
	public void setCampo5Anexo4(String campo5Anexo4) {
		this.campo5Anexo4 = campo5Anexo4;
	}

	/**
	 * Método para obtener el atributo campo5Anexo5
	 * @return El atributo campo5Anexo5
	 */
	public String getCampo5Anexo5() {
		return campo5Anexo5;
	}

	/**
	 * Método para establecer el atributo campo5Anexo5
	 * @param campo5Anexo5 El valor del atributo campo5Anexo5 a establecer.
	 */
	public void setCampo5Anexo5(String campo5Anexo5) {
		this.campo5Anexo5 = campo5Anexo5;
	}

	/**
	 * Método para obtener el atributo campo5Anexo6
	 * @return El atributo campo5Anexo6
	 */
	public String getCampo5Anexo6() {
		return campo5Anexo6;
	}

	/**
	 * Método para establecer el atributo campo5Anexo6
	 * @param campo5Anexo6 El valor del atributo campo5Anexo6 a establecer.
	 */
	public void setCampo5Anexo6(String campo5Anexo6) {
		this.campo5Anexo6 = campo5Anexo6;
	}

	/**
	 * @return the certification
	 */
	public String getCertification() {
		return certification;
	}

	/**
	 * @param certification the certification to set
	 */
	public void setCertification(String certification) {
		this.certification = certification;
	}

}
