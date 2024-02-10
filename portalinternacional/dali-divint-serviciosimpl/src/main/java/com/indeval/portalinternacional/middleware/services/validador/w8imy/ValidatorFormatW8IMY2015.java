/**
 * Bursatec - Portal Internacional
 * Copyrigth (c) 2015 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.services.validador.w8imy;

import org.apache.commons.lang.StringUtils;

import com.indeval.portaldali.middleware.formatosw.FormaW8IMY2015;
import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.FormatoW8Service;
import com.indeval.portalinternacional.middleware.services.validador.ValidatorBeneficiario;
import com.indeval.portalinternacional.middleware.servicios.constantes.BeneficiariosConstantes;
import com.indeval.portalinternacional.middleware.servicios.constantes.Constantes;
import com.indeval.portalinternacional.middleware.servicios.modelo.Beneficiario;
import com.indeval.portalinternacional.middleware.servicios.modelo.Domicilio;
import com.indeval.portalinternacional.middleware.servicios.modelo.filetransfer.beneficiario.tipoformato.FileTransferFormaW8IMY2015;

/**
 * @author Pablo Balderas
 *
 */
public abstract class ValidatorFormatW8IMY2015 extends ValidatorBeneficiario<FileTransferFormaW8IMY2015> {
	
	/** Servicio para el parseo de los formatos a xml y viceversa */
	private FormatoW8Service formatoW8Service;

	/*
	 * (non-Javadoc)
	 * @see com.indeval.portalinternacional.middleware.services.validador.ValidatorBeneficiario#validaFormatoW(com.indeval.portalinternacional.middleware.servicios.modelo.Beneficiario)
	 */
	@Override
	public void validaFormatoW(Beneficiario beneficiario) throws BusinessException {
		FormaW8IMY2015 forma = formatoW8Service.obtenerCamposFormatoW8IMY2015(beneficiario.getFormatoW8IMY2015().getCamposFormato());
		
		boolean esFideicomisoSimple = beneficiario.getTipoBeneficiario().getIdTipoBeneficiario().compareTo(Constantes.FIDEICOMISO_SIMPLE)==0;
		boolean esIntermediarioCalificado = beneficiario.getTipoBeneficiario().getIdTipoBeneficiario().compareTo(Constantes.INTERMEDIARIO_CALIFICADO_CRPR)==0
				|| beneficiario.getTipoBeneficiario().getIdTipoBeneficiario().compareTo(Constantes.INTERMEDIARIO_CALIFICADO_SRPR)==0;
		
		// Validaciones Parte I
		
		// Parte I-4 Obligatorio
		boolean parteIVChecked = forma.isPartIcmp4a() || 
				forma.isPartIcmp4b() || forma.isPartIcmp4c() || 
				forma.isPartIcmp4d() || forma.isPartIcmp4e() ||
				forma.isPartIcmp4f() || forma.isPartIcmp4g() ||
				forma.isPartIcmp4h() || forma.isPartIcmp4i();
		if(!parteIVChecked){
			throw new BusinessException(getErrorResolver().getMessage("J0131", (Object) " Parte I-4 "));
		}

		boolean parteI4Invalida = this.verificaMultiBooleanCheckedPartI4(forma);
		if(parteI4Invalida){
			throw new BusinessException(getErrorResolver().getMessage("J0133", (Object) " Parte I-4 "));
		}
		
		// FIDEICOMISO SIMPLE --> Nonwithholding foreign simple trust.
		if(esFideicomisoSimple){
			if (!forma.isPartIcmp4h()){
				throw new BusinessException(getErrorResolver().getMessage("J0131", (Object) " Parte I-4. Nonwithholding foreign simple trust "));
			}
		}

		// INTERMEDIARIO CALIFICADO CON Y SIN RPR --> Qualified intermediary.
		if(esIntermediarioCalificado){
			if(!forma.isPartIcmp4a()){
				throw new BusinessException(getErrorResolver().getMessage("J0131", (Object) " Parte I-4. Qualified intermediary "));
			}
		}

/*		if(esIntermediarioCalificado){
			if(!forma.isPartIIIcmp14a()){
				throw new BusinessException(getErrorResolver().getMessage("J0131", (Object) " Parte III-14a "));
			}
		}
*/		
		// Parte III
/*		if (forma.isPartIcmp4a()){
			boolean parte3Incompleta = !forma.isPartIIIcmp14a()
					&& !forma.isPartIIIcmp14b()
					&& !forma.isPartIIIcmp14c()
					&& !forma.isPartIIIcmp14d()
					&& !forma.isPartIIIcmp14e()
					&& !forma.isPartIIIcmp14f();
			if(parte3Incompleta) {
				throw new BusinessException(getErrorResolver().getMessage("J0131", (Object) " Parte III "));
			}
			//Si capturo el campo 14c1, valida que el campo 14c haya sido capturado.
			if(StringUtils.isNotBlank(forma.getPartIIIcmp14c1()) && !forma.isPartIIIcmp14c()) {
				throw new BusinessException(getErrorResolver().getMessage("J0131", (Object) " Parte III-14c "));
			}
			//Si capturo el campo 14d1, valida que el campo 14d haya sido capturado.
			if(StringUtils.isNotBlank(forma.getPartIIIcmp14d1()) && !forma.isPartIIIcmp14d()) {
				throw new BusinessException(getErrorResolver().getMessage("J0131", (Object) " Parte III-14d "));
			}
			//Si capturo el campo 14e1, valida que el campo 14e haya sido capturado.
			if(StringUtils.isNotBlank(forma.getPartIIIcmp14e1()) && !forma.isPartIIIcmp14e()) {
				throw new BusinessException(getErrorResolver().getMessage("J0131", (Object) " Parte III-14e "));
			}
			//Si capturo los campos 14e2 y/o 14e3, debe validar que el campo 14e haya sido capturado
			if((forma.isPartIIIcmp14ei() || forma.isPartIIIcmp14eii()) && !forma.isPartIIIcmp14e()) {
				throw new BusinessException(getErrorResolver().getMessage("J0131", (Object) " Parte III-14e "));
			}
		}
		// Parte IV
		if (forma.isPartIcmp4b()){
			boolean parte4Incompleta = !forma.isPartIVcmp15a()
					&& !forma.isPartIVcmp15b()
					&& !forma.isPartIVcmp15c()
					&& !forma.isPartIVcmp15d();
			if(parte4Incompleta) {
				throw new BusinessException(getErrorResolver().getMessage("J0131", (Object) " Parte IV "));
			}
		}
		
		// Parte V
		if (forma.isPartIcmp4c() || forma.isPartIcmp5f()){
			boolean parte5Incompleta = !forma.isPartVcmp16a()
					&& !forma.isPartVcmp16b()
					&& !forma.isPartVcmp16c();
			if(parte5Incompleta) {
				throw new BusinessException(getErrorResolver().getMessage("J0131", (Object) " Parte V "));
			}
		}
		
		// Parte VI
		if (forma.isPartIcmp4d()){
			boolean parte6Incompleta = !forma.isPartVIcmp17a()
					&& !forma.isPartVIcmp17b()
					&& !forma.isPartVIcmp17c();
			if(parte6Incompleta) {
				throw new BusinessException(getErrorResolver().getMessage("J0131", (Object) " Parte VI "));
			}
		}
		
		// Parte VII
		if (forma.isPartIcmp4e() || forma.isPartIcmp4f()){
			boolean parte7Incompleta = !forma.isPartVIIcmp18();
			if(parte7Incompleta) {
				throw new BusinessException(getErrorResolver().getMessage("J0131", (Object) " Parte VII "));
			}
		}
		
		// Parte VIII
		boolean verificaParte8 = esFideicomisoSimple
				|| forma.isPartIcmp4g() || forma.isPartIcmp4h() || forma.isPartIcmp4i();
		if (verificaParte8){
			boolean parte8Incompleta = !forma.isPartVIIIcmp19();
			if(parte8Incompleta) {
				throw new BusinessException(getErrorResolver().getMessage("J0131", (Object) " Parte VIII "));
			}
		}
*/
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
				|| forma.isPartIcmp5y();
		if(!parteVChecked){
			throw new BusinessException(getErrorResolver().getMessage("J0131", (Object) " Parte I-5 "));
		}

		// Parte I-5 Solamente una opción
		boolean parteI5Invalida = this.verificaMultiBooleanCheckedPartI5(forma);
		if(parteI5Invalida){
			throw new BusinessException(getErrorResolver().getMessage("J0133", (Object) " Parte I-5 "));
		}

/*		// Parte IX
		if (forma.isPartIcmp5a()){
			boolean parte9Incompleta = !forma.isPartIXcmp20();
			if(parte9Incompleta){
				throw new BusinessException(getErrorResolver().getMessage("J0131", (Object) " Parte IX "));
			}
		}
		
		// Parte X
		if (forma.isPartIcmp5g()){
			boolean parte10Incompleta = StringUtils.isEmpty(forma.getPartXcmp21a())
					|| (!forma.isPartXcmp21b() && !forma.isPartXcmp21c());
			if(parte10Incompleta){
				throw new BusinessException(getErrorResolver().getMessage("J0131", (Object) " Parte X "));
			}
		}
		
		// Parte XI
		if (forma.isPartIcmp5l()){
			boolean parte11Incompleta = !forma.isPartXIcmp22a()
					&& !forma.isPartXIcmp22b()
					&& !forma.isPartXIcmp22c();
			if(parte11Incompleta){
				throw new BusinessException(getErrorResolver().getMessage("J0131", (Object) " Parte XI "));
			}			
		}
		
		// Parte XII
		if (forma.isPartIcmp5h()){
			boolean parte12Incompleta = !forma.isPartXIIcmp23();
			if(parte12Incompleta){
				throw new BusinessException(getErrorResolver().getMessage("J0131", (Object) " Parte XII "));
			}			
		}

		// Parte XIII
		if (forma.isPartIcmp5i()){
			boolean parte13Incompleta = !forma.isPartXIIIcmp24();
			if(parte13Incompleta){
				throw new BusinessException(getErrorResolver().getMessage("J0131", (Object) " Parte XIII "));
			}
		}
		
		// Parte XIV
		if (forma.isPartIcmp5j()){
			boolean parte14Incompleta = StringUtils.isEmpty(forma.getPartXIVcmp25a())
				|| !forma.isPartXIVcmp25b();
			if(parte14Incompleta){
				throw new BusinessException(getErrorResolver().getMessage("J0131", (Object) " Parte XIV "));
			}
		}
		
		// Parte XV
		if (forma.isPartIcmp5k()){
			boolean parte15Incompleta = !forma.isPartXVcmp26();
			if(parte15Incompleta){
				throw new BusinessException(getErrorResolver().getMessage("J0131", (Object) " Parte XV "));
			}
		}
		
		// Parte XVI
		if (forma.isPartIcmp5m()){
			boolean parte16Incompleta = !forma.isPartXVIcmp27a()
					&& !forma.isPartXVIcmp27b()
					&& !forma.isPartXVIcmp27c();
			if(parte16Incompleta){
				throw new BusinessException(getErrorResolver().getMessage("J0131", (Object) " Parte XVI "));
			}
		}
		
		// Parte XVII
		if (forma.isPartIcmp5n()){
			boolean parte17Incompleta = !forma.isPartXVIIcmp28();
			if(parte17Incompleta){
				throw new BusinessException(getErrorResolver().getMessage("J0131", (Object) " Parte XVII "));
			}
		}
		
		// Parte XVIII
		if (forma.isPartIcmp5o()){
			boolean parte18Incompleta = !forma.isPartXVIIIcmp29a();
			if(parte18Incompleta){
				throw new BusinessException(getErrorResolver().getMessage("J0131", (Object) " Parte XVIII "));
			}
			
			parte18Incompleta = StringUtils.isEmpty(forma.getPartXVIIIcmp29b())
					&& StringUtils.isEmpty(forma.getPartXVIIIcmp29c())
					&& StringUtils.isEmpty(forma.getPartXVIIIcmp29d());
			if(parte18Incompleta){
				throw new BusinessException(getErrorResolver().getMessage("J0131", (Object) " Parte XVIII "));
			}
		}
		
		// Parte XIX
		if (forma.isPartIcmp5p()){
			boolean parte19Incompleta = !forma.isPartXIXcmp30a()
				&& !forma.isPartXIXcmp30b()
				&& !forma.isPartXIXcmp30c()
				&& !forma.isPartXIXcmp30d()
				&& !forma.isPartXIXcmp30e()
				&& !forma.isPartXIXcmp30f();
			if(parte19Incompleta){
				throw new BusinessException(getErrorResolver().getMessage("J0131", (Object) " Parte XIX "));
			}	
		}
		
		// Parte XX
		if (forma.isPartIcmp5q()){
			boolean parte20Incompleta = !forma.isPartXXcmp31();
			if(parte20Incompleta){
				throw new BusinessException(getErrorResolver().getMessage("J0131", (Object) " Parte XX "));
			}
		}
		
		// Parte XXI
		if (forma.isPartIcmp5r()){
			boolean parte21Incompleta = !forma.isPartXXIcmp32a()
					|| StringUtils.isEmpty(forma.getPartXXIcmp32b());
			if(parte21Incompleta){
				throw new BusinessException(getErrorResolver().getMessage("J0131", (Object) " Parte XXI "));
			}
		}
		
		// Parte XXII
		if (forma.isPartIcmp5s()){
			boolean parte22Incompleta = !forma.isPartXXIIcmp33a()
					|| StringUtils.isEmpty(forma.getPartXXIIcmp33b());
			if(parte22Incompleta){
				throw new BusinessException(getErrorResolver().getMessage("J0131", (Object) " Parte XXII "));
			}
		}
		
		// Parte XXIII
		if (forma.isPartIcmp5t()){
			boolean parte23Incompleta = !forma.isPartXXIIIcmp34a()
					&& !forma.isPartXXIIIcmp34b();
			if(parte23Incompleta){
				throw new BusinessException(getErrorResolver().getMessage("J0131", (Object) " Parte XXIII "));
			}
			
			if (forma.isPartXXIIIcmp34a()){
				if(StringUtils.isEmpty(forma.getPartXXIIIcmp34a1())){
					throw new BusinessException(getErrorResolver().getMessage("J0131", (Object) " Parte XXIII-34a "));
				}
			}

			if (forma.isPartXXIIIcmp34b()){
				if(StringUtils.isEmpty(forma.getPartXXIIIcmp34b1()) && StringUtils.isEmpty(forma.getPartXXIIIcmp34b2())){
					throw new BusinessException(getErrorResolver().getMessage("J0131", (Object) " Parte XXIII-34b "));
				}
			}
		}
		
		// Parte XXIV
		if (forma.isPartIcmp5u()){
			boolean parte24Incompleta = !forma.isPartXXIVcmp35();
			if(parte24Incompleta){
				throw new BusinessException(getErrorResolver().getMessage("J0131", (Object) " Parte XXIV "));
			}
		}

		// Parte XXV
		if (forma.isPartIcmp5v()){
			boolean parte25Incompleta = !forma.isPartXXVcmp36();
			if(parte25Incompleta){
				throw new BusinessException(getErrorResolver().getMessage("J0131", (Object) " Parte XXV "));
			}
		}

		// Parte XXVI
		if (forma.isPartIcmp5w()){
			boolean parte26Incompleta = !forma.isPartXXVIcmp37();
			if(parte26Incompleta){
				throw new BusinessException(getErrorResolver().getMessage("J0131", (Object) " Parte XXVI "));
			}
		}
		
		// Parte XXVII
		if (forma.isPartIcmp5y()){
			boolean parte27Incompleta = StringUtils.isEmpty(forma.getPartXXVIIcmp38())
					|| !forma.isPartXXVIIcmp39();
			if(parte27Incompleta){
				throw new BusinessException(getErrorResolver().getMessage("J0131", (Object) " Parte XXVII "));
			}
		}
*/
		
		// Domicilio beneficiario
		boolean domicilioTitularIncompleto =
			StringUtils.isEmpty(beneficiario.getDomicilioW8Normal().getStreet()) || 
			StringUtils.isEmpty(beneficiario.getDomicilioW8Normal().getCityTown()) || 
			StringUtils.isEmpty(beneficiario.getDomicilioW8Normal().getCountry());
		if(domicilioTitularIncompleto){
			throw new BusinessException(getErrorResolver().getMessage("J0131", (Object) " Domicilio beneficiario "));			
		}

		// Domicilio correspondencia. Si el check esta habilitado, valida que el domicilio haya sido capturado 
		if(!forma.isReadOnlyCmp7()) {
			boolean domicilioCorreoIncompleto =
				StringUtils.isEmpty(beneficiario.getDomicilioW8Correo().getStreet()) || 
				StringUtils.isEmpty(beneficiario.getDomicilioW8Correo().getCityTown()) ||
				StringUtils.isEmpty(beneficiario.getDomicilioW8Correo().getCountry());
			if(domicilioCorreoIncompleto){
				throw new BusinessException(getErrorResolver().getMessage("J0131", (Object) " Domicilio correo "));
			}
		}

		// Parte I-8
		if (esFideicomisoSimple){
			boolean existeParteI8 = StringUtils.isNotEmpty(forma.getPartIcmp8())
					|| forma.isPartIcmp8a() || forma.isPartIcmp8b()
					|| forma.isPartIcmp8c() || forma.isPartIcmp8d()
					|| forma.isPartIcmp8e();
			if (existeParteI8){
				throw new BusinessException(getErrorResolver().getMessage("J0135", (Object) " Parte I-8 "));
			}
		}

		if(esIntermediarioCalificado){
			boolean parteI8Incompleta = StringUtils.isEmpty(forma.getPartIcmp8());
			if(parteI8Incompleta){
				throw new BusinessException(getErrorResolver().getMessage("J0131", (Object) " Parte I-8 "));
			}

			boolean parteI8Invalida = this.verificaMultiBooleanCheckedPartI8(forma);
			if(parteI8Invalida){
				throw new BusinessException(getErrorResolver().getMessage("J0133", (Object) " Parte I-8 "));
			}

			if(!forma.isPartIcmp8a()){
				throw new BusinessException(getErrorResolver().getMessage("J0131", (Object) " Parte I-8 QI-EIN "));
			}
		}

		/* Valida si campo 5 es:
		 * 	Participating FFI
		 * 	Reporting Model 1 FFI
		 * 	Reporting Model 2 FFI
		 * Entonces se debe llenar campo: GIIN (Campo9)
		 */
		if(forma.isPartIcmp5b() || forma.isPartIcmp5c() ||  forma.isPartIcmp5d()){
			if(StringUtils.isEmpty(forma.getPartIcmp9())){
				throw new BusinessException(getErrorResolver().getMessage("J0131", (Object) " Parte I-9. GIIN "));				
			}
		}
		
		// Parte I-10
		if(beneficiario.getIdCuentaNombrada().compareTo(Constantes.CN_CLEARSTREAM)==0){
			if(StringUtils.isEmpty(forma.getPartIcmp10())){
				throw new BusinessException(getErrorResolver().getMessage("J0131", (Object) " Parte I-10. Referencia "));
			}
			if (!BeneficiariosConstantes.REF_CLEARSTREAM.equals(forma.getPartIcmp10())){
				throw new BusinessException(getErrorResolver().getMessage("J0107", (Object) " Parte I-10. Referencia "));
			}
		}
		
		if(beneficiario.getIdCuentaNombrada().compareTo(Constantes.CN_EUROCLEAR_BANK)==0){
			if(StringUtils.isEmpty(forma.getPartIcmp10())){
				throw new BusinessException(getErrorResolver().getMessage("J0131", (Object) " Parte I-10. Referencia "));
			}
			if (!BeneficiariosConstantes.REF_EUROCLEAR_BANK.equals(forma.getPartIcmp10())){
				throw new BusinessException(getErrorResolver().getMessage("J0107", (Object) " Parte I-10. Referencia "));
			}	
		}
		
        // SIGNER
		// Valida Parte XXIX
		if (StringUtils.isEmpty(forma.getPartXXIX())){
			throw new BusinessException(getErrorResolver().getMessage("J0131", (Object) " Parte XXIX - Certification "));
		}
                
	}

	/*
	 * (non-Javadoc)
	 * @see com.indeval.portalinternacional.middleware.services.validador.ValidatorBeneficiario#validaDomicilioBeneficiario(com.indeval.portalinternacional.middleware.servicios.modelo.Domicilio, java.lang.String)
	 */
	@Override
	public void validaDomicilioBeneficiario(Domicilio domicilio, String tipoDomicilio) throws BusinessException {

	}
	
    /**
     * @param forma
     * @return
     */
    private boolean verificaMultiBooleanCheckedPartI4(FormaW8IMY2015 forma) {
    	int contador=0;
    	
    	if (forma.isPartIcmp4a()) contador++;
    	if (forma.isPartIcmp4b()) contador++;
    	if (forma.isPartIcmp4c()) contador++;
    	if (forma.isPartIcmp4d()) contador++;
    	if (forma.isPartIcmp4e()) contador++;
    	if (forma.isPartIcmp4f()) contador++;
    	if (forma.isPartIcmp4g()) contador++;
    	if (forma.isPartIcmp4h()) contador++;
    	if (forma.isPartIcmp4i()) contador++;

    	return (contador > 1 ? true : false );
    }

    /**
     * @param forma
     * @return
     */
    private boolean verificaMultiBooleanCheckedPartI5(FormaW8IMY2015 forma) {
    	int contador=0;
    	
    	if (forma.isPartIcmp5a()) contador++;
    	if (forma.isPartIcmp5b()) contador++;
    	if (forma.isPartIcmp5c()) contador++;
    	if (forma.isPartIcmp5d()) contador++;
    	if (forma.isPartIcmp5e()) contador++;
    	if (forma.isPartIcmp5f()) contador++;
    	if (forma.isPartIcmp5g()) contador++;
    	if (forma.isPartIcmp5h()) contador++;
    	if (forma.isPartIcmp5i()) contador++;
    	if (forma.isPartIcmp5j()) contador++;
    	if (forma.isPartIcmp5k()) contador++;
    	if (forma.isPartIcmp5l()) contador++;
    	if (forma.isPartIcmp5m()) contador++;
    	if (forma.isPartIcmp5n()) contador++;
    	if (forma.isPartIcmp5o()) contador++;
    	if (forma.isPartIcmp5p()) contador++;
    	if (forma.isPartIcmp5q()) contador++;
    	if (forma.isPartIcmp5r()) contador++;
    	if (forma.isPartIcmp5s()) contador++;
    	if (forma.isPartIcmp5t()) contador++;
    	if (forma.isPartIcmp5u()) contador++;
    	if (forma.isPartIcmp5v()) contador++;
    	if (forma.isPartIcmp5w()) contador++;
    	if (forma.isPartIcmp5x()) contador++;
    	if (forma.isPartIcmp5y()) contador++;
    	
    	return (contador > 1 ? true : false );
    }

    /**
     * @param forma
     * @return
     */
    private boolean verificaMultiBooleanCheckedPartI8(FormaW8IMY2015 forma) {
    	int contador=0;
    	
    	if (forma.isPartIcmp8a()) contador++;
    	if (forma.isPartIcmp8b()) contador++;
    	if (forma.isPartIcmp8c()) contador++;
    	if (forma.isPartIcmp8d()) contador++;
    	if (forma.isPartIcmp8e()) contador++;
    	
    	return (contador > 1 ? true : false );
    }
	
	
	/**
	 * Método para establecer el atributo formatoW8Service
	 * @param formatoW8Service El valor del atributo formatoW8Service a establecer.
	 */
	public void setFormatoW8Service(FormatoW8Service formatoW8Service) {
		this.formatoW8Service = formatoW8Service;
	}
}