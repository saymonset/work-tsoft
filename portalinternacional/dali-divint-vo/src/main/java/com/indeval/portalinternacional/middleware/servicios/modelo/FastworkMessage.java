package com.indeval.portalinternacional.middleware.servicios.modelo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "MES_")
public class FastworkMessage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "MES_DBKEY")
	private String dbkey;

	@Column(name = "MES_TEXT1")
	private byte[] message;
	
	@Column(name = "MES_AFLD1")
	private String bicCode;
	
	@Column(name = "MES_MDTIME")
	private Date dateTime;
	
	@Column(name = "MES_MTYPE")
	private String tipoMensaje;

	@Column(name = "MES_ISIN")
	private String isin;

	@Column(name = "MES_AMOUNT_19ASETT")
	private String amount19ASETT;

	@Column(name = "MES_AMOUNT_19AESTT")
	private String amount19AESTT;

	@Column(name = "MES_AMOUNT_19BCHAR")
	private String amount19BCHAR;

	@Column(name = "MES_AMOUNT_19BPRIN")
	private String amount19BPRIN;

	@Column(name = "MES_AMOUNT_19BCINL")
	private String amount19BCINL;

	@Column(name = "MES_AMOUNT_19BGRSS")
	private String amount19BGRSS;

	@Column(name = "MES_AMOUNT_19BNETT")
	private String amount19BNETT;

	@Column(name = "MES_AMOUNT_19BTAXR")
	private String amount19BTAXR;

	@Column(name = "MES_AMOUNT_19BENTL")
	private String amount19BENTL;

	@Column(name = "MES_AMOUNT_19BNRAT")
	private String amount19BNRAT;

	@Column(name = "MES_AMOUNT_19BPSTA")
	private String amount19BPSTA;

	@Column(name = "MES_AMOUNT_19BFTCA")
	private String amount19BFTCA;

	@Column(name = "MES_AMOUNT_92FCHAR")
	private String amount92FCHAR;

	@Column(name = "MES_AMOUNT_92FINTP")
	private String amount92FINTP;

	@Column(name = "MES_AMOUNT_92FGRSS")
	private String amount92FGRSS;

	@Column(name = "MES_AMOUNT_92FNETT")
	private String amount92FNETT;

	@Column(name = "MES_AMOUNT_32A")
	private String amount32A;

	@Column(name = "MES_AMOUNT_32B")
	private String amount32B;

	@Column(name = "MES_AMOUNT_60F")
	private String amount60F;

	@Column(name = "MES_AMOUNT_62F")
	private String amount62F;

	@Column(name = "MES_AMOUNT_64")
	private String amount64;
	
	@Column(name = "MES_CURRENCY_19ASETT")
	private String currency19ASETT;

	@Column(name = "MES_CURRENCY_19AESTT")
	private String currency19AESTT;

	@Column(name = "MES_CURRENCY_19BCHAR")
	private String currency19BCHAR;

	@Column(name = "MES_CURRENCY_19BPRIN")
	private String currency19BPRIN;

	@Column(name = "MES_CURRENCY_19BCINL")
	private String currency19BCINL;

	@Column(name = "MES_CURRENCY_19BGRSS")
	private String currency19BGRSS;

	@Column(name = "MES_CURRENCY_19BNETT")
	private String currency19BNETT;

	@Column(name = "MES_CURRENCY_19BTAXR")
	private String currency19BTAXR;

	@Column(name = "MES_CURRENCY_19BENTL")
	private String currency19BENTL;

	@Column(name = "MES_CURRENCY_19BNRAT")
	private String currency19BNRAT;

	@Column(name = "MES_CURRENCY_19BPSTA")
	private String currency19BPSTA;

	@Column(name = "MES_CURRENCY_19BFTCA")
	private String currency19BFTCA;

	@Column(name = "MES_CURRENCY_92FCHAR")
	private String currency92FCHAR;

	@Column(name = "MES_CURRENCY_92FINTP")
	private String currency92FINTP;

	@Column(name = "MES_CURRENCY_92FGRSS")
	private String currency92FGRSS;

	@Column(name = "MES_CURRENCY_92FNETT")
	private String currency92FNETT;

	@Column(name = "MES_CURRENCY_32A")
	private String currency32A;

	@Column(name = "MES_CURRENCY_32B")
	private String currency32B;

	@Column(name = "MES_CURRENCY_60F")
	private String currency60F;

	@Column(name = "MES_CURRENCY_62F")
	private String currency62F;

	@Column(name = "MES_CURRENCY_64")
	private String currency64;

	public String getDbkey() {
		return dbkey;
	}

	public void setDbkey(String dbkey) {
		this.dbkey = dbkey;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public byte[] getMessage() {
		return message;
	}

	public void setMessage(byte[] message) {
		this.message = message;
	}

	public String getBicCode() {
		return bicCode;
	}

	public void setBicCode(String bicCode) {
		this.bicCode = bicCode;
	}

	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	public String getTipoMensaje() {
		return tipoMensaje;
	}

	public void setTipoMensaje(String tipoMensaje) {
		this.tipoMensaje = tipoMensaje;
	}

	public String getIsin() {
		return isin;
	}

	public void setIsin(String isin) {
		this.isin = isin;
	}

	public String getAmount19ASETT() {
		return amount19ASETT;
	}

	public void setAmount19ASETT(String amount19asett) {
		amount19ASETT = amount19asett;
	}

	public String getAmount19AESTT() {
		return amount19AESTT;
	}

	public void setAmount19AESTT(String amount19aestt) {
		amount19AESTT = amount19aestt;
	}

	public String getAmount19BCHAR() {
		return amount19BCHAR;
	}

	public void setAmount19BCHAR(String amount19bchar) {
		amount19BCHAR = amount19bchar;
	}

	public String getAmount19BPRIN() {
		return amount19BPRIN;
	}

	public void setAmount19BPRIN(String amount19bprin) {
		amount19BPRIN = amount19bprin;
	}

	public String getAmount19BCINL() {
		return amount19BCINL;
	}

	public void setAmount19BCINL(String amount19bcinl) {
		amount19BCINL = amount19bcinl;
	}

	public String getAmount19BGRSS() {
		return amount19BGRSS;
	}

	public void setAmount19BGRSS(String amount19bgrss) {
		amount19BGRSS = amount19bgrss;
	}

	public String getAmount19BNETT() {
		return amount19BNETT;
	}

	public void setAmount19BNETT(String amount19bnett) {
		amount19BNETT = amount19bnett;
	}

	public String getAmount19BTAXR() {
		return amount19BTAXR;
	}

	public void setAmount19BTAXR(String amount19btaxr) {
		amount19BTAXR = amount19btaxr;
	}

	public String getAmount19BENTL() {
		return amount19BENTL;
	}

	public void setAmount19BENTL(String amount19bentl) {
		amount19BENTL = amount19bentl;
	}

	public String getAmount19BNRAT() {
		return amount19BNRAT;
	}

	public void setAmount19BNRAT(String amount19bnrat) {
		amount19BNRAT = amount19bnrat;
	}

	public String getAmount19BPSTA() {
		return amount19BPSTA;
	}

	public void setAmount19BPSTA(String amount19bpsta) {
		amount19BPSTA = amount19bpsta;
	}

	public String getAmount19BFTCA() {
		return amount19BFTCA;
	}

	public void setAmount19BFTCA(String amount19bftca) {
		amount19BFTCA = amount19bftca;
	}

	public String getAmount92FCHAR() {
		return amount92FCHAR;
	}

	public void setAmount92FCHAR(String amount92fchar) {
		amount92FCHAR = amount92fchar;
	}

	public String getAmount92FINTP() {
		return amount92FINTP;
	}

	public void setAmount92FINTP(String amount92fintp) {
		amount92FINTP = amount92fintp;
	}

	public String getAmount92FGRSS() {
		return amount92FGRSS;
	}

	public void setAmount92FGRSS(String amount92fgrss) {
		amount92FGRSS = amount92fgrss;
	}

	public String getAmount92FNETT() {
		return amount92FNETT;
	}

	public void setAmount92FNETT(String amount92fnett) {
		amount92FNETT = amount92fnett;
	}

	public String getAmount32A() {
		return amount32A;
	}

	public void setAmount32A(String amount32a) {
		amount32A = amount32a;
	}

	public String getAmount32B() {
		return amount32B;
	}

	public void setAmount32B(String amount32b) {
		amount32B = amount32b;
	}

	public String getAmount60F() {
		return amount60F;
	}

	public void setAmount60F(String amount60f) {
		amount60F = amount60f;
	}

	public String getAmount62F() {
		return amount62F;
	}

	public void setAmount62F(String amount62f) {
		amount62F = amount62f;
	}

	public String getAmount64() {
		return amount64;
	}

	public void setAmount64(String amount64) {
		this.amount64 = amount64;
	}

	public String getCurrency19ASETT() {
		return currency19ASETT;
	}

	public void setCurrency19ASETT(String currency19asett) {
		currency19ASETT = currency19asett;
	}

	public String getCurrency19AESTT() {
		return currency19AESTT;
	}

	public void setCurrency19AESTT(String currency19aestt) {
		currency19AESTT = currency19aestt;
	}

	public String getCurrency19BCHAR() {
		return currency19BCHAR;
	}

	public void setCurrency19BCHAR(String currency19bchar) {
		currency19BCHAR = currency19bchar;
	}

	public String getCurrency19BPRIN() {
		return currency19BPRIN;
	}

	public void setCurrency19BPRIN(String currency19bprin) {
		currency19BPRIN = currency19bprin;
	}

	public String getCurrency19BCINL() {
		return currency19BCINL;
	}

	public void setCurrency19BCINL(String currency19bcinl) {
		currency19BCINL = currency19bcinl;
	}

	public String getCurrency19BGRSS() {
		return currency19BGRSS;
	}

	public void setCurrency19BGRSS(String currency19bgrss) {
		currency19BGRSS = currency19bgrss;
	}

	public String getCurrency19BNETT() {
		return currency19BNETT;
	}

	public void setCurrency19BNETT(String currency19bnett) {
		currency19BNETT = currency19bnett;
	}

	public String getCurrency19BTAXR() {
		return currency19BTAXR;
	}

	public void setCurrency19BTAXR(String currency19btaxr) {
		currency19BTAXR = currency19btaxr;
	}

	public String getCurrency19BENTL() {
		return currency19BENTL;
	}

	public void setCurrency19BENTL(String currency19bentl) {
		currency19BENTL = currency19bentl;
	}

	public String getCurrency19BNRAT() {
		return currency19BNRAT;
	}

	public void setCurrency19BNRAT(String currency19bnrat) {
		currency19BNRAT = currency19bnrat;
	}

	public String getCurrency19BPSTA() {
		return currency19BPSTA;
	}

	public void setCurrency19BPSTA(String currency19bpsta) {
		currency19BPSTA = currency19bpsta;
	}

	public String getCurrency19BFTCA() {
		return currency19BFTCA;
	}

	public void setCurrency19BFTCA(String currency19bftca) {
		currency19BFTCA = currency19bftca;
	}

	public String getCurrency92FCHAR() {
		return currency92FCHAR;
	}

	public void setCurrency92FCHAR(String currency92fchar) {
		currency92FCHAR = currency92fchar;
	}

	public String getCurrency92FINTP() {
		return currency92FINTP;
	}

	public void setCurrency92FINTP(String currency92fintp) {
		currency92FINTP = currency92fintp;
	}

	public String getCurrency92FGRSS() {
		return currency92FGRSS;
	}

	public void setCurrency92FGRSS(String currency92fgrss) {
		currency92FGRSS = currency92fgrss;
	}

	public String getCurrency92FNETT() {
		return currency92FNETT;
	}

	public void setCurrency92FNETT(String currency92fnett) {
		currency92FNETT = currency92fnett;
	}

	public String getCurrency32A() {
		return currency32A;
	}

	public void setCurrency32A(String currency32a) {
		currency32A = currency32a;
	}

	public String getCurrency32B() {
		return currency32B;
	}

	public void setCurrency32B(String currency32b) {
		currency32B = currency32b;
	}

	public String getCurrency60F() {
		return currency60F;
	}

	public void setCurrency60F(String currency60f) {
		currency60F = currency60f;
	}

	public String getCurrency62F() {
		return currency62F;
	}

	public void setCurrency62F(String currency62f) {
		currency62F = currency62f;
	}

	public String getCurrency64() {
		return currency64;
	}

	public void setCurrency64(String currency64) {
		this.currency64 = currency64;
	}
	
	
	
}
