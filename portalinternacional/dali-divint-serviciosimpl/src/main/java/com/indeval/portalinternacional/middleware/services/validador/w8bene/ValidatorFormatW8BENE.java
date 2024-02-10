/**
 * Bursatec - Portal Internacional
 * Copyrigth (c) 2015 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.services.validador.w8bene;

import org.apache.commons.lang.StringUtils;

import com.indeval.portaldali.middleware.formatosw.FormaW8BENE;
import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.FormatoW8Service;
import com.indeval.portalinternacional.middleware.services.validador.ValidatorBeneficiario;
import com.indeval.portalinternacional.middleware.servicios.constantes.BeneficiariosConstantes;
import com.indeval.portalinternacional.middleware.servicios.constantes.Constantes;
import com.indeval.portalinternacional.middleware.servicios.modelo.Beneficiario;
import com.indeval.portalinternacional.middleware.servicios.modelo.Domicilio;
import com.indeval.portalinternacional.middleware.servicios.modelo.filetransfer.beneficiario.tipoformato.FileTransferFormaW8BENE;

/**
 * Clase que implenta las validaciones de negocio para los formatos W8BEN-E.
 *
 * @author Abraham Morales
 * @author Pablo Balderas
 */
public abstract class ValidatorFormatW8BENE extends ValidatorBeneficiario<FileTransferFormaW8BENE> {

	/** Servicio para el parseo de los formatos a xml y viceversa */
	private FormatoW8Service formatoW8Service;

	/*
	 * (non-Javadoc)
	 * @see com.indeval.portalinternacional.middleware.services.validador.ValidatorBeneficiario#validaFormatoW(com.indeval.portalinternacional.middleware.servicios.modelo.Beneficiario)
	 */
	@Override
	public void validaFormatoW(final Beneficiario beneficiario) throws BusinessException {

		FormaW8BENE forma = this.formatoW8Service.obtenerCamposFormato(beneficiario.getFormatoW8BENE().getCamposFormato());
		boolean esPersonaMoralNoEua =   beneficiario.getTipoBeneficiario().getIdTipoBeneficiario().compareTo(Constantes.PERSONA_MORAL_NO_EUA)==0;
		boolean esAforeSiefore =        beneficiario.getTipoBeneficiario().getIdTipoBeneficiario().compareTo(Constantes.SIEFORE_AFORE)==0;
		boolean esFideicomisoComplejo = beneficiario.getTipoBeneficiario().getIdTipoBeneficiario().compareTo(Constantes.FIDEICOMISO_COMPLEJO)==0;
		boolean esOtros = !esPersonaMoralNoEua && !esAforeSiefore && !esFideicomisoComplejo;

		// Validaciones Parte I

		// Parte I-4 Obligatorio
		boolean parteIVChecked = forma.isPartIcmp4a() ||
				forma.isPartIcmp4b() || forma.isPartIcmp4c() ||
				forma.isPartIcmp4d() || forma.isPartIcmp4e() ||
				forma.isPartIcmp4f() || forma.isPartIcmp4g() ||
				forma.isPartIcmp4h() || forma.isPartIcmp4i() ||
				forma.isPartIcmp4j() || forma.isPartIcmp4k();
		if(!parteIVChecked){
			throw new BusinessException(this.getErrorResolver().getMessage("J0131", (Object) " Parte I-4 "));
		}

		boolean parteI4Invalida = this.verificaMultiBooleanCheckedPartI4(forma);
		if(parteI4Invalida){
			throw new BusinessException(this.getErrorResolver().getMessage("J0133", (Object) " Parte I-4 "));
		}

		// Persona Moral Extranjera EUA --> Solo Corporation
		if(esPersonaMoralNoEua){
			if (!forma.isPartIcmp4a()){
				throw new BusinessException(this.getErrorResolver().getMessage("J0131", (Object) " Parte I-4. Corporation "));
			}
		}

		// Afore-Siefore --> Solo Tax-exempt organization
		if(esAforeSiefore){
			if (!forma.isPartIcmp4j()){
				throw new BusinessException(this.getErrorResolver().getMessage("J0131", (Object) " Parte I-4. Tax-exempt organization "));
			}
		}

		// Fideicomiso complejo --> Solo Complex trust
		if(esFideicomisoComplejo){
			if (!forma.isPartIcmp4f()){
				throw new BusinessException(this.getErrorResolver().getMessage("J0131", (Object) " Parte I-4. Complex trust "));
			}
		}

		// Parte I-5 Obligatorio
		boolean parteVChecked = forma.isPartIcmp5a() || forma.isPartIcmp5b()
				|| forma.isPartIcmp5c() || forma.isPartIcmp5d()
				|| forma.isPartIcmp5e() || forma.isPartIcmp5f()
				|| forma.isPartIcmp5g() || forma.isPartIcmp5h()
				|| forma.isPartIcmp5i() || forma.isPartIcmp5j()
				|| forma.isPartIcmp5k() || forma.isPartIcmp5l()
				|| forma.isPartIcmp5m() || forma.isPartIcmp5n()
				|| forma.isPartIcmp5o() || forma.isPartIcmp5p()
				|| forma.isPartIcmp5q() || forma.isPartIcmp5r()
				|| forma.isPartIcmp5s() || forma.isPartIcmp5t()
				|| forma.isPartIcmp5u() || forma.isPartIcmp5v()
				|| forma.isPartIcmp5w() || forma.isPartIcmp5x()
				|| forma.isPartIcmp5y() || forma.isPartIcmp5z()
				|| forma.isPartIcmp5aa() || forma.isPartIcmp5ab()
				|| forma.isPartIcmp5ac() || forma.isPartIcmp5ad()
				|| forma.isPartIcmp5ae();
		if(!parteVChecked){
			throw new BusinessException(this.getErrorResolver().getMessage("J0131", (Object) " Parte I-5 "));
		}

		// Parte I-5 Solamente una opción
		boolean parteI5Invalida = this.verificaMultiBooleanCheckedPartI5(forma);
		if(parteI5Invalida){
			throw new BusinessException(this.getErrorResolver().getMessage("J0133", (Object) " Parte I-5 "));
		}

		// Parte IV
		if (forma.isPartIcmp5f()){
			boolean parteIVIncompleta = StringUtils.isEmpty(forma.getPartIVcmp16());
			if(parteIVIncompleta) {
				throw new BusinessException(this.getErrorResolver().getMessage("J0131", (Object) " Parte IV "));
			}

			parteIVIncompleta = !forma.isPartIVcmp17a() && !forma.isPartIVcmp17b();
			if(parteIVIncompleta) {
				throw new BusinessException(this.getErrorResolver().getMessage("J0131", (Object) " Parte IV-17a, 17b "));
			}
		}

		// Parte V
		if (forma.isPartIcmp5g()){
			boolean parteVIncompleta = !forma.isPartVcmp18();
			if(parteVIncompleta) {
				throw new BusinessException(this.getErrorResolver().getMessage("J0131", (Object) " Parte V "));
			}
		}

		// Parte VI
		if (forma.isPartIcmp5h()){
			boolean parteVIIncompleta = !forma.isPartVIcmp19();
			if(parteVIIncompleta) {
				throw new BusinessException(this.getErrorResolver().getMessage("J0131", (Object) " Parte VI "));
			}
		}

		// Parte VII
		if (forma.isPartIcmp5i()){
			boolean parteVIIIncompleta = StringUtils.isEmpty(forma.getPartVIIcmp20())
					|| !forma.isPartVIIcmp21();
			if(parteVIIIncompleta) {
				throw new BusinessException(this.getErrorResolver().getMessage("J0131", (Object) " Parte VII "));
			}
		}

		// Parte VIII
		if (forma.isPartIcmp5j()){
			boolean parteVIIIIncompleta = !forma.isPartVIIIcmp22();
			if(parteVIIIIncompleta) {
				throw new BusinessException(this.getErrorResolver().getMessage("J0131", (Object) " Parte VIII "));
			}
		}

		// Parte IX
		if (forma.isPartIcmp5k()){
			boolean parteIXIncompleta = !forma.isPartIXcmp23();
			if(parteIXIncompleta) {
				throw new BusinessException(this.getErrorResolver().getMessage("J0131", (Object) " Parte IX "));
			}
		}

		// Parte X
		if (forma.isPartIcmp5l()){
			boolean parteXIncompleta = !forma.isPartXcmp24a() && (!forma.isPartXcmp24b()
					&& !forma.isPartXcmp24c() && !forma.isPartXcmp24d());
			if(parteXIncompleta) {
				throw new BusinessException(this.getErrorResolver().getMessage("J0131", (Object) " Parte X "));
			}
		}

		// Parte XI
		if (forma.isPartIcmp5m()){
			boolean parteXIIncompleta = !forma.isPartXIcmp25a() && !forma.isPartXIcmp25b()
					&& !forma.isPartXIcmp25c();

			if(parteXIIncompleta) {
				throw new BusinessException(this.getErrorResolver().getMessage("J0131", (Object) " Parte XI "));
			}
		}

		// Parte XII
		if (forma.isPartIcmp5n()){
			boolean parteXIIIncompleta = !forma.isPartXIIcmp26a()
					|| StringUtils.isEmpty(forma.getPartXIIcmp26b())
					|| StringUtils.isEmpty(forma.getPartXIIcmp26c())
					|| StringUtils.isEmpty(forma.getPartXIIcmp26d());
			if(parteXIIIncompleta) {
				throw new BusinessException(this.getErrorResolver().getMessage("J0131", (Object) " Parte XII "));
			}
		}

		// Parte XIII
		if (forma.isPartIcmp5o()){
			boolean parteXIIIIncompleta = !forma.isPartXIIIcmp27();
			if(parteXIIIIncompleta) {
				throw new BusinessException(this.getErrorResolver().getMessage("J0131", (Object) " Parte XIII "));
			}
		}

		// Parte XIV
		if (forma.isPartIcmp5p()){
			boolean parteXIVIncompleta = !forma.isPartXIVcmp28a() && !forma.isPartXIVcmp28b();
			if(parteXIVIncompleta) {
				throw new BusinessException(this.getErrorResolver().getMessage("J0131", (Object) " Parte XIV "));
			}
		}

		// Parte XV
		if (forma.isPartIcmp5q()){
			boolean parteXVIncompleta = !(forma.isPartXVcmp29a()
					|| forma.isPartXVcmp29b()
					|| forma.isPartXVcmp29c()
					|| forma.isPartXVcmp29d()
					|| forma.isPartXVcmp29e()
					|| forma.isPartXVcmp29f());
			if(parteXVIncompleta) {
				throw new BusinessException(this.getErrorResolver().getMessage("J0131", (Object) " Parte XV "));
			}
		}

		// Parte XVI
		if (forma.isPartIcmp5r()){
			boolean parteXVIIncompleta = !forma.isPartXVIcmp30();
			if(parteXVIIncompleta) {
				throw new BusinessException(this.getErrorResolver().getMessage("J0131", (Object) " Parte XVI "));
			}
		}

		// Parte XVII
		if (forma.isPartIcmp5s()){
			boolean parteXVIIIncompleta = !forma.isPartXVIIcmp31();
			if(parteXVIIIncompleta) {
				throw new BusinessException(this.getErrorResolver().getMessage("J0131", (Object) " Parte XVII "));
			}
		}

		// Parte XVIII
		if (forma.isPartIcmp5t()){
			boolean parteXVIIIIncompleta = !forma.isPartXVIIIcmp32();
			if(parteXVIIIIncompleta) {
				throw new BusinessException(this.getErrorResolver().getMessage("J0131", (Object) " Parte XVIII "));
			}
		}

		// Parte XIX
		if (forma.isPartIcmp5u()){
			boolean parteXIXIncompleta = !forma.isPartXIXcmp33a()
					|| StringUtils.isEmpty(forma.getPartXIXcmp33b());
			if(parteXIXIncompleta) {
				throw new BusinessException(this.getErrorResolver().getMessage("J0131", (Object) " Parte XIX "));
			}
		}

		// Parte XX
		if (forma.isPartIcmp5v()){
			boolean parteXXIncompleta = !forma.isPartXXcmp34a()
					|| StringUtils.isEmpty(forma.getPartXXcmp34b());
			if(parteXXIncompleta) {
				throw new BusinessException(this.getErrorResolver().getMessage("J0131", (Object) " Parte XX "));
			}
		}

		// Parte XXI
		if (forma.isPartIcmp5w()){
			boolean parteXXIIncompleta = !forma.isPartXXIcmp35a()
					|| StringUtils.isEmpty(forma.getPartXXIcmp35b());
			if(parteXXIIncompleta) {
				throw new BusinessException(this.getErrorResolver().getMessage("J0131", (Object) " Parte XXI "));
			}
		}

		// Parte XXII
		if (forma.isPartIcmp5x()){
			boolean parteXXIIIncompleta = !forma.isPartXXIIcmp36();
			if(parteXXIIIncompleta) {
				throw new BusinessException(this.getErrorResolver().getMessage("J0131", (Object) " Parte XXII "));
			}
		}

		// Parte XXIII
		if (forma.isPartIcmp5y()){
			boolean parteXXIIIIncompleta = !forma.isPartXXIIIcmp37a() &&
					!forma.isPartXXIIIcmp37c();
			if(parteXXIIIIncompleta) {
				throw new BusinessException(this.getErrorResolver().getMessage("J0131", (Object) " Parte XXIII "));
			}

			if(forma.isPartXXIIIcmp37a()){
				if(StringUtils.isEmpty(forma.getPartXXIIIcmp37b())){
					throw new BusinessException(this.getErrorResolver().getMessage("J0131", (Object) " Parte XXIII-37a "));
				}
			}

			if(forma.isPartXXIIIcmp37c()){
				if(StringUtils.isEmpty(forma.getPartXXIIIcmp37d()) || StringUtils.isEmpty(forma.getPartXXIIIcmp37e())){
					throw new BusinessException(this.getErrorResolver().getMessage("J0131", (Object) " Parte XXIII-37b "));
				}
			}
		}

		// Parte XXIV
		if (forma.isPartIcmp5z()){
			boolean parteXXIVIncompleta = !forma.isPartXXIVcmp38();
			if(parteXXIVIncompleta) {
				throw new BusinessException(this.getErrorResolver().getMessage("J0131", (Object) " Parte XXIV "));
			}
		}

		// Parte XXV
		if (forma.isPartIcmp5aa()){
			boolean parteXXVIncompleta = !forma.isPartXXVcmp39();
			if(parteXXVIncompleta) {
				throw new BusinessException(this.getErrorResolver().getMessage("J0131", (Object) " Parte XXV "));
			}
		}

		// Parte XXVI
		if (forma.isPartIcmp5ab()){
			boolean parteXXVIIncompleta = !forma.isPartXXVIcmp40a() && !forma.isPartXXVIcmp40b()
					&& !forma.isPartXXVIcmp40c();

			if(parteXXVIIncompleta){
				throw new BusinessException(this.getErrorResolver().getMessage("J0131", (Object) " Parte XXVI "));
			}
		}

		// Parte XXVII
		if (forma.isPartIcmp5ac()){
			boolean parteXXVIIIncompleta = !forma.isPartXXVIIcmp41();
			if(parteXXVIIIncompleta) {
				throw new BusinessException(this.getErrorResolver().getMessage("J0131", (Object) " Parte XXVII "));
			}
		}

		// Parte XXVIII
		if (forma.isPartIcmp5ae()){
			boolean parteXXVIIIIncompleta = StringUtils.isEmpty(forma.getPartXXVIIIcmp42())
					|| !forma.isPartXXVIIIcmp43();
			if(parteXXVIIIIncompleta) {
				throw new BusinessException(this.getErrorResolver().getMessage("J0131", (Object) " Parte XXVIII "));
			}
		}
		
		if(forma.isPartIcmp5b()) {
			if(forma.isPartIcmp9a1()){
				boolean giinInvalido = StringUtils.isEmpty(forma.getPartIcmp9a2());
				if(giinInvalido){
					throw new BusinessException(this.getErrorResolver().getMessage("J0131", (Object) " GIIN "));
				}
			}else {
				throw new BusinessException(this.getErrorResolver().getMessage("J0131", (Object) " GIIN "));
			}
			
		}
		
		if(forma.isPartIcmp5c()) {
			if(forma.isPartIcmp9a1()){
				boolean giinInvalido = StringUtils.isEmpty(forma.getPartIcmp9a2());
				if(giinInvalido){
					throw new BusinessException(this.getErrorResolver().getMessage("J0131", (Object) " GIIN "));
				}
			}else {
				throw new BusinessException(this.getErrorResolver().getMessage("J0131", (Object) " GIIN "));
			}
			
		}
		
		if(forma.isPartIcmp5d()) {
			if(forma.isPartIcmp9a1()){
				boolean giinInvalido = StringUtils.isEmpty(forma.getPartIcmp9a2());
				if(giinInvalido){
					throw new BusinessException(this.getErrorResolver().getMessage("J0131", (Object) " GIIN "));
				}
			}else {
				throw new BusinessException(this.getErrorResolver().getMessage("J0131", (Object) " GIIN "));
			}
			
		}

		// Domicilio beneficiario
		boolean domicilioTitularIncompleto =
			StringUtils.isEmpty(beneficiario.getDomicilioW8Normal().getStreet()) ||
			StringUtils.isEmpty(beneficiario.getDomicilioW8Normal().getCityTown()) ||
			StringUtils.isEmpty(beneficiario.getDomicilioW8Normal().getCountry());
		if(domicilioTitularIncompleto){
			throw new BusinessException(this.getErrorResolver().getMessage("J0131", (Object) " Domicilio beneficiario "));
		}

		// Domicilio correspondencia. Si el check esta habilitado, valida que el domicilio haya sido capturado
		if(!forma.isInhabilitarPartIcmp7()) {
			boolean domicilioCorreoIncompleto =
				StringUtils.isBlank(beneficiario.getDomicilioW8Correo().getStreet()) ||
				StringUtils.isBlank(beneficiario.getDomicilioW8Correo().getCityTown()) ||
				StringUtils.isBlank(beneficiario.getDomicilioW8Correo().getCountry());
			if(domicilioCorreoIncompleto){
				throw new BusinessException(this.getErrorResolver().getMessage("J0131", (Object) " Domicilio correo "));
			}
		}


		// Parte I-10
		if(beneficiario.getIdCuentaNombrada().compareTo(Constantes.CN_CLEARSTREAM)==0){
			if(StringUtils.isEmpty(forma.getPartIcmp10())){
				throw new BusinessException(this.getErrorResolver().getMessage("J0131", (Object) " Parte I-10. Referencia "));
			} else if (!BeneficiariosConstantes.REF_CLEARSTREAM.equals(forma.getPartIcmp10())){
				throw new BusinessException(this.getErrorResolver().getMessage("J0107", (Object) " Parte I-10. Referencia "));
			}
		}

		if(beneficiario.getIdCuentaNombrada().compareTo(Constantes.CN_EUROCLEAR_BANK)==0){
			if(StringUtils.isEmpty(forma.getPartIcmp10())){
				throw new BusinessException(this.getErrorResolver().getMessage("J0131", (Object) " Parte I-10. Referencia "));
			} else if (!BeneficiariosConstantes.REF_EUROCLEAR_BANK.equals(forma.getPartIcmp10())){
				throw new BusinessException(this.getErrorResolver().getMessage("J0107", (Object) " Parte I-10. Referencia "));
			}

		}

		// Validaciones Parte III

		if(!esOtros){
			if(!forma.isPartIIIcmp14a()){
				throw new BusinessException(this.getErrorResolver().getMessage("J0131", (Object) " Parte III-14a "));
			}

			boolean esPaisDistinto = !forma.getPartIcmp2().equals(forma.getPartIIIcmp14a1());
			if (esPaisDistinto) {
				throw new BusinessException(this.getErrorResolver().getMessage("J0107", (Object) " Parte III-14a. Pais "));
			}

			if(!forma.isPartIIIcmp14b()){
				throw new BusinessException(this.getErrorResolver().getMessage("J0131", (Object) " Parte III-14b "));
			}
		}


		if(esAforeSiefore){
			boolean parteIII15Incompleta = StringUtils.isEmpty(forma.getPartIIIcmp15a())
				|| StringUtils.isEmpty(forma.getPartIIIcmp15b())
				|| StringUtils.isEmpty(forma.getPartIIIcmp15c())
				|| StringUtils.isEmpty(forma.getPartIIIcmp15d1())
				|| StringUtils.isEmpty(forma.getPartIIIcmp15d2());
			if(parteIII15Incompleta){
				throw new BusinessException(this.getErrorResolver().getMessage("J0131", (Object) " Parte III-15 "));
			}
		}

		// Validacion Parte III-15
		boolean esDividends = StringUtils.equalsIgnoreCase(forma.getPartIIIcmp15a(), BeneficiariosConstantes.VALOR_DIVIDENDS);
		if(esDividends){
			boolean isTextoDividends = StringUtils.equalsIgnoreCase(forma.getPartIIIcmp15c(), BeneficiariosConstantes.DIVIDENDS);
			if(!isTextoDividends){
				throw new BusinessException(this.getErrorResolver().getMessage("J0107", (Object) " Parte III-15c. "));
			}
		}
		boolean esInterest = StringUtils.equalsIgnoreCase(forma.getPartIIIcmp15a(), BeneficiariosConstantes.VALOR_INTEREST);
		if(esInterest){
			boolean isTextoInterest = StringUtils.equalsIgnoreCase(forma.getPartIIIcmp15c(), BeneficiariosConstantes.INTEREST);
			if(!isTextoInterest){
				throw new BusinessException(this.getErrorResolver().getMessage("J0107", (Object) " Parte III-15c. "));
			}
		}
		//Valida el porcentaje
		this.validarPorcentaje(forma);

		// Validaciones Parte XXIX
		forma.setPartXXIX(true); //siempre va seleccionado
		boolean parteXXIXIncompleta = StringUtils.isEmpty(forma.getPrintName());
		if(parteXXIXIncompleta) {
			throw new BusinessException(this.getErrorResolver().getMessage("J0131", (Object) " Parte XXIX "));
		}

	}

	/*
	 * (non-Javadoc)
	 * @see com.indeval.portalinternacional.middleware.services.validador.ValidatorBeneficiario#validaDomicilioBeneficiario(com.indeval.portalinternacional.middleware.servicios.modelo.Domicilio, java.lang.String)
	 */
	@Override
	public void validaDomicilioBeneficiario(final Domicilio domicilio, final String tipoDomicilio) throws BusinessException {

	}

	/**
	 * Valida que el campo 15b de la Parte III tenga el formato xxx.xx y no sea menor a 0.00 ni mayor a 100.00
	 * @param forma Objeto donde esta el valor.
	 */
	private void validarPorcentaje(final FormaW8BENE forma) {
		if(StringUtils.isNotBlank(forma.getPartIIIcmp15b())) {
			if(!forma.getPartIIIcmp15b().matches(Constantes.PATRON_PORCENTAJE_RETENCION)){
				throw new BusinessException(this.getErrorResolver().getMessage("J0139", (Object) " Parte III-15 Porcentaje. "));
			}
			Double nuevoPorcentaje = Double.valueOf(forma.getPartIIIcmp15b().trim());
			if(nuevoPorcentaje > Constantes.VALOR_MAXIMO_PORCENTAJE_RETENCION || nuevoPorcentaje < Constantes.VALOR_MINIMO_PORCENTAJE_RETENCION){
				throw new BusinessException(this.getErrorResolver().getMessage("J0140", (Object) " Parte III-15 Porcentaje. "));
			}
		}
	}


    /**
     * @param forma
     * @return
     */
    private boolean verificaMultiBooleanCheckedPartI4(final FormaW8BENE forma) {
    	int contador=0;

    	if (forma.isPartIcmp4a()) {
            contador++;
        }
    	if (forma.isPartIcmp4b()) {
            contador++;
        }
    	if (forma.isPartIcmp4c()) {
            contador++;
        }
    	if (forma.isPartIcmp4d()) {
            contador++;
        }
    	if (forma.isPartIcmp4e()) {
            contador++;
        }
    	if (forma.isPartIcmp4f()) {
            contador++;
        }
    	if (forma.isPartIcmp4g()) {
            contador++;
        }
    	if (forma.isPartIcmp4h()) {
            contador++;
        }
    	if (forma.isPartIcmp4i()) {
            contador++;
        }
    	if (forma.isPartIcmp4j()) {
            contador++;
        }
    	if (forma.isPartIcmp4k()) {
            contador++;
        }

    	return contador > 1 ? true : false;
    }

    /**
     * @param forma
     * @return
     */
    private boolean verificaMultiBooleanCheckedPartI5(final FormaW8BENE forma) {
    	int contador=0;

    	if (forma.isPartIcmp5a()) {
            contador++;
        }
    	if (forma.isPartIcmp5b()) {
            contador++;
        }
    	if (forma.isPartIcmp5c()) {
            contador++;
        }
    	if (forma.isPartIcmp5d()) {
            contador++;
        }
    	if (forma.isPartIcmp5e()) {
            contador++;
        }
    	if (forma.isPartIcmp5f()) {
            contador++;
        }
    	if (forma.isPartIcmp5g()) {
            contador++;
        }
    	if (forma.isPartIcmp5h()) {
            contador++;
        }
    	if (forma.isPartIcmp5i()) {
            contador++;
        }
    	if (forma.isPartIcmp5j()) {
            contador++;
        }
    	if (forma.isPartIcmp5k()) {
            contador++;
        }
    	if (forma.isPartIcmp5l()) {
            contador++;
        }
    	if (forma.isPartIcmp5m()) {
            contador++;
        }
    	if (forma.isPartIcmp5n()) {
            contador++;
        }
    	if (forma.isPartIcmp5o()) {
            contador++;
        }
    	if (forma.isPartIcmp5p()) {
            contador++;
        }
    	if (forma.isPartIcmp5q()) {
            contador++;
        }
    	if (forma.isPartIcmp5r()) {
            contador++;
        }
    	if (forma.isPartIcmp5s()) {
            contador++;
        }
    	if (forma.isPartIcmp5t()) {
            contador++;
        }
    	if (forma.isPartIcmp5u()) {
            contador++;
        }
    	if (forma.isPartIcmp5v()) {
            contador++;
        }
    	if (forma.isPartIcmp5w()) {
            contador++;
        }
    	if (forma.isPartIcmp5x()) {
            contador++;
        }
    	if (forma.isPartIcmp5y()) {
            contador++;
        }
    	if (forma.isPartIcmp5z()) {
            contador++;
        }
    	if (forma.isPartIcmp5aa()) {
            contador++;
        }
    	if (forma.isPartIcmp5ab()) {
            contador++;
        }
    	if (forma.isPartIcmp5ac()) {
            contador++;
        }
    	if (forma.isPartIcmp5ad()) {
            contador++;
        }
    	if (forma.isPartIcmp5ae()) {
            contador++;
        }

    	return contador > 1 ? true : false;
    }

	/**
	 * Método para establecer el atributo formatoW8Service
	 * @param formatoW8Service El valor del atributo formatoW8Service a establecer.
	 */
	public void setFormatoW8Service(final FormatoW8Service formatoW8Service) {
		this.formatoW8Service = formatoW8Service;
	}

}