/**
 * Bursatec - Portal Internacional Copyrigth (c) 2015 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.services.validador.w8imy;

import org.apache.commons.lang.StringUtils;

import com.indeval.portaldali.middleware.formatosw.FormaW8IMY2017;
import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.FormatoW8Service;
import com.indeval.portalinternacional.middleware.services.validador.ValidatorBeneficiario;
import com.indeval.portalinternacional.middleware.servicios.constantes.BeneficiariosConstantes;
import com.indeval.portalinternacional.middleware.servicios.constantes.Constantes;
import com.indeval.portalinternacional.middleware.servicios.modelo.Beneficiario;
import com.indeval.portalinternacional.middleware.servicios.modelo.Domicilio;
import com.indeval.portalinternacional.middleware.servicios.modelo.filetransfer.beneficiario.tipoformato.FileTransferFormaW8IMY2017;

/**
 * @author Pablo Balderas
 * 
 */
public abstract class ValidatorFormatW8IMY2017 extends
        ValidatorBeneficiario<FileTransferFormaW8IMY2017> {

    /** Servicio para el parseo de los formatos a xml y viceversa */
    private FormatoW8Service formatoW8Service;

    /*
     * (non-Javadoc)
     * 
     * @see com.indeval.portalinternacional.middleware.services.validador.ValidatorBeneficiario#
     * validaFormatoW(com.indeval.portalinternacional.middleware.servicios.modelo.Beneficiario)
     */
    @Override
    public void validaFormatoW(final Beneficiario beneficiario) throws BusinessException {
        FormaW8IMY2017 forma =
                this.formatoW8Service.obtenerCamposFormatoW8IMY2017(beneficiario
                        .getFormatoW8IMY2015().getCamposFormato());

        boolean esFideicomisoSimple =
                beneficiario.getTipoBeneficiario().getIdTipoBeneficiario()
                        .compareTo(Constantes.FIDEICOMISO_SIMPLE) == 0;
        boolean esIntermediarioCalificado =
                beneficiario.getTipoBeneficiario().getIdTipoBeneficiario()
                        .compareTo(Constantes.INTERMEDIARIO_CALIFICADO_CRPR) == 0
                        || beneficiario.getTipoBeneficiario().getIdTipoBeneficiario()
                                .compareTo(Constantes.INTERMEDIARIO_CALIFICADO_SRPR) == 0;

        // Validaciones Parte I

        // Parte I-4 Obligatorio
        boolean parteIVChecked =
                forma.isPartIcmp4a() || forma.isPartIcmp4b() || forma.isPartIcmp4c()
                        || forma.isPartIcmp4d() || forma.isPartIcmp4e() || forma.isPartIcmp4f()
                        || forma.isPartIcmp4g() || forma.isPartIcmp4h() || forma.isPartIcmp4i();
        if (!parteIVChecked) {
            throw new BusinessException(this.getErrorResolver().getMessage("J0131",
                    (Object) " Parte I-4 "));
        }

        boolean parteI4Invalida = this.verificaMultiBooleanCheckedPartI4(forma);
        if (parteI4Invalida) {
            throw new BusinessException(this.getErrorResolver().getMessage("J0133",
                    (Object) " Parte I-4 "));
        }

        // FIDEICOMISO SIMPLE --> Nonwithholding foreign simple trust.
        if (esFideicomisoSimple) {
            if (!forma.isPartIcmp4h()) {
                throw new BusinessException(this.getErrorResolver().getMessage("J0131",
                        (Object) " Parte I-4. Nonwithholding foreign simple trust "));
            }
        }

        // INTERMEDIARIO CALIFICADO CON Y SIN RPR --> Qualified intermediary.
        if (esIntermediarioCalificado) {
            if (!forma.isPartIcmp4a()) {
                throw new BusinessException(this.getErrorResolver().getMessage("J0131",
                        (Object) " Parte I-4. Qualified intermediary "));
            }
        }

        // Parte I-5 Obligatorio
        boolean parteVChecked =
                forma.isPartIcmp5a() || forma.isPartIcmp5b() || forma.isPartIcmp5c()
                        || forma.isPartIcmp5d() || forma.isPartIcmp5e() || forma.isPartIcmp5f()
                        || forma.isPartIcmp5g() || forma.isPartIcmp5h() || forma.isPartIcmp5i()
                        || forma.isPartIcmp5j() || forma.isPartIcmp5k() || forma.isPartIcmp5l()
                        || forma.isPartIcmp5m() || forma.isPartIcmp5n() || forma.isPartIcmp5o()
                        || forma.isPartIcmp5p() || forma.isPartIcmp5q() || forma.isPartIcmp5r()
                        || forma.isPartIcmp5s() || forma.isPartIcmp5t() || forma.isPartIcmp5u()
                        || forma.isPartIcmp5v() || forma.isPartIcmp5w() || forma.isPartIcmp5x()
                        || forma.isPartIcmp5y() || forma.isPartIcmp5z();
        if (!parteVChecked) {
            throw new BusinessException(this.getErrorResolver().getMessage("J0131",
                    (Object) " Parte I-5 "));
        }
        // Parte I-5 Solamente una opcion
        boolean parteI5Invalida = this.verificaMultiBooleanCheckedPartI5(forma);
        if (parteI5Invalida) {
            throw new BusinessException(this.getErrorResolver().getMessage("J0133",
                    (Object) " Parte I-5 "));
        }

        // Domicilio beneficiario
        boolean domicilioTitularIncompleto =
                StringUtils.isEmpty(beneficiario.getDomicilioW8Normal().getStreet())
                        || StringUtils.isEmpty(beneficiario.getDomicilioW8Normal().getCityTown())
                        || StringUtils.isEmpty(beneficiario.getDomicilioW8Normal().getCountry());
        if (domicilioTitularIncompleto) {
            throw new BusinessException(this.getErrorResolver().getMessage("J0131",
                    (Object) " Domicilio beneficiario "));
        }

        // Domicilio correspondencia. Si el check esta habilitado, valida que el domicilio haya sido
        // capturado
        if (!forma.isReadOnlyCmp7()) {
            boolean domicilioCorreoIncompleto =
                    StringUtils.isEmpty(beneficiario.getDomicilioW8Correo().getStreet())
                            || StringUtils.isEmpty(beneficiario.getDomicilioW8Correo()
                                    .getCityTown())
                            || StringUtils
                                    .isEmpty(beneficiario.getDomicilioW8Correo().getCountry());
            if (domicilioCorreoIncompleto) {
                throw new BusinessException(this.getErrorResolver().getMessage("J0131",
                        (Object) " Domicilio correo "));
            }
        }

        // Parte I-8
        if (esFideicomisoSimple) {
            boolean existeParteI8 =
                    StringUtils.isNotEmpty(forma.getPartIcmp8()) || forma.isPartIcmp8a()
                            || forma.isPartIcmp8b() || forma.isPartIcmp8c() || forma.isPartIcmp8d()
                            || forma.isPartIcmp8e();
            if (existeParteI8) {
                throw new BusinessException(this.getErrorResolver().getMessage("J0135",
                        (Object) " Parte I-8 "));
            }
        }

        if (esIntermediarioCalificado) {
            boolean parteI8Incompleta = StringUtils.isEmpty(forma.getPartIcmp8());
            if (parteI8Incompleta) {
                throw new BusinessException(this.getErrorResolver().getMessage("J0131",
                        (Object) " Parte I-8 "));
            }

            boolean parteI8Invalida = this.verificaMultiBooleanCheckedPartI8(forma);
            if (parteI8Invalida) {
                throw new BusinessException(this.getErrorResolver().getMessage("J0133",
                        (Object) " Parte I-8 "));
            }

            if (!forma.isPartIcmp8a()) {
                throw new BusinessException(this.getErrorResolver().getMessage("J0131",
                        (Object) " Parte I-8 QI-EIN "));
            }
        }
        // Parte I-10
        if (beneficiario.getIdCuentaNombrada().compareTo(Constantes.CN_CLEARSTREAM) == 0) {
            if (StringUtils.isEmpty(forma.getPartIcmp10())) {
                throw new BusinessException(this.getErrorResolver().getMessage("J0131",
                        (Object) " Parte I-10. Referencia "));
            }
            if (!BeneficiariosConstantes.REF_CLEARSTREAM.equals(forma.getPartIcmp10())) {
                throw new BusinessException(this.getErrorResolver().getMessage("J0107",
                        (Object) " Parte I-10. Referencia "));
            }
        }

        if (beneficiario.getIdCuentaNombrada().compareTo(Constantes.CN_EUROCLEAR_BANK) == 0) {
            if (StringUtils.isEmpty(forma.getPartIcmp10())) {
                throw new BusinessException(this.getErrorResolver().getMessage("J0131",
                        (Object) " Parte I-10. Referencia "));
            }
            if (!BeneficiariosConstantes.REF_EUROCLEAR_BANK.equals(forma.getPartIcmp10())) {
                throw new BusinessException(this.getErrorResolver().getMessage("J0107",
                        (Object) " Parte I-10. Referencia "));
            }
        }

        // SIGNER
        boolean parteXXIXIncompleta = StringUtils.isBlank(forma.getPrintName());
        if (parteXXIXIncompleta) {
            throw new BusinessException(this.getErrorResolver().getMessage("J0131",
                    (Object) " Parte XXIX "));
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.indeval.portalinternacional.middleware.services.validador.ValidatorBeneficiario#
     * validaDomicilioBeneficiario
     * (com.indeval.portalinternacional.middleware.servicios.modelo.Domicilio, java.lang.String)
     */
    @Override
    public void validaDomicilioBeneficiario(final Domicilio domicilio, final String tipoDomicilio)
            throws BusinessException {

    }

    /**
     * @param forma
     * @return
     */
    private boolean verificaMultiBooleanCheckedPartI4(final FormaW8IMY2017 forma) {
        int contador = 0;

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

        return contador > 1 ? true : false;
    }

    /**
     * @param forma
     * @return
     */
    private boolean verificaMultiBooleanCheckedPartI5(final FormaW8IMY2017 forma) {
        int contador = 0;

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

        return contador > 1 ? true : false;
    }

    /**
     * @param forma
     * @return
     */
    private boolean verificaMultiBooleanCheckedPartI8(final FormaW8IMY2017 forma) {
        int contador = 0;

        if (forma.isPartIcmp8a()) {
            contador++;
        }
        if (forma.isPartIcmp8b()) {
            contador++;
        }
        if (forma.isPartIcmp8c()) {
            contador++;
        }
        if (forma.isPartIcmp8d()) {
            contador++;
        }
        if (forma.isPartIcmp8e()) {
            contador++;
        }

        return contador > 1 ? true : false;
    }


    /**
     * Método para establecer el atributo formatoW8Service
     * 
     * @param formatoW8Service El valor del atributo formatoW8Service a establecer.
     */
    public void setFormatoW8Service(final FormatoW8Service formatoW8Service) {
        this.formatoW8Service = formatoW8Service;
    }
}