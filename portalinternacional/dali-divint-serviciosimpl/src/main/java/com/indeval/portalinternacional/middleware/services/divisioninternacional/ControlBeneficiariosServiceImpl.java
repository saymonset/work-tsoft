package com.indeval.portalinternacional.middleware.services.divisioninternacional;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bursatec.util.message.MessageResolver;
import com.indeval.portaldali.middleware.formatosw.FormaW8IMY2015;
import com.indeval.portaldali.middleware.formatosw.FormaW8IMY2017;
import com.indeval.portaldali.middleware.services.util.DateUtilService;
import com.indeval.portaldali.middleware.services.util.ValidatorService;
import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portaldali.persistence.modelo.Institucion;
import com.indeval.portalinternacional.middleware.services.validador.ValidatorBeneficiarioService;
import com.indeval.portalinternacional.middleware.services.validador.ValidatorDivIntService;
import com.indeval.portalinternacional.middleware.servicios.constantes.BeneficiariosConstantes;
import com.indeval.portalinternacional.middleware.servicios.constantes.Constantes;
import com.indeval.portalinternacional.middleware.servicios.modelo.Beneficiario;
import com.indeval.portalinternacional.middleware.servicios.modelo.BeneficiarioInstitucion;
import com.indeval.portalinternacional.middleware.servicios.modelo.CatBic;
import com.indeval.portalinternacional.middleware.servicios.modelo.CustodioTipoBenef;
import com.indeval.portalinternacional.middleware.servicios.modelo.Domicilio;
import com.indeval.portalinternacional.middleware.servicios.modelo.ExemptPayeeW9;
import com.indeval.portalinternacional.middleware.servicios.modelo.ExemptionFatcaW9;
import com.indeval.portalinternacional.middleware.servicios.modelo.Field3W8BEN;
import com.indeval.portalinternacional.middleware.servicios.modelo.Field3W8IMY;
import com.indeval.portalinternacional.middleware.servicios.modelo.Field3W9;
import com.indeval.portalinternacional.middleware.servicios.modelo.FormatoFiscal;
import com.indeval.portalinternacional.middleware.servicios.modelo.FormatoMilaArchivos;
import com.indeval.portalinternacional.middleware.servicios.modelo.FormatoW8IMY;
import com.indeval.portalinternacional.middleware.servicios.modelo.HistoricoBeneficiario;
import com.indeval.portalinternacional.middleware.servicios.modelo.MILACodigoDepartamento;
import com.indeval.portalinternacional.middleware.servicios.modelo.MILASectorEconomico;
import com.indeval.portalinternacional.middleware.servicios.modelo.MILATipoDocumento;
import com.indeval.portalinternacional.middleware.servicios.modelo.MILATipoEmpresa;
import com.indeval.portalinternacional.middleware.servicios.modelo.PaisInt;
import com.indeval.portalinternacional.middleware.servicios.modelo.StatusBeneficiario;
import com.indeval.portalinternacional.middleware.servicios.modelo.TipoBeneficiario;
import com.indeval.portalinternacional.middleware.servicios.modelo.adp.ClaveAdp;
import com.indeval.portalinternacional.middleware.servicios.vo.BeneficiariosPaginacionVO;
import com.indeval.portalinternacional.middleware.servicios.vo.CatBicVO;
import com.indeval.portalinternacional.middleware.servicios.vo.ConsultaBeneficiariosParam;
import com.indeval.portalinternacional.middleware.servicios.vo.ConsultaHistoricoBeneficiariosParam;
import com.indeval.portalinternacional.middleware.servicios.vo.ListStatusBeneficiario;
import com.indeval.portalinternacional.middleware.servicios.vo.NombreBeneficiario;
import com.indeval.portalinternacional.middleware.servicios.vo.TipoFormato;
import com.indeval.portalinternacional.persistence.dao.AdpCustodioPorcentajeDao;
import com.indeval.portalinternacional.persistence.dao.BeneficiarioDao;
import com.indeval.portalinternacional.persistence.dao.CamposFormatosDao;
import com.indeval.portalinternacional.persistence.dao.CatBicDao;
import com.indeval.portalinternacional.persistence.dao.ClaveAdpDao;
import com.indeval.portalinternacional.persistence.dao.CustodioTipoBenefDao;
import com.indeval.portalinternacional.persistence.dao.HistoricoBeneficiarioDao;
import com.indeval.portalinternacional.persistence.dao.StatusBeneficiarioDao;

@SuppressWarnings({"unchecked"})
public class ControlBeneficiariosServiceImpl implements ControlBeneficiariosService, Constantes {

    /** Para el registro de log de esta clase */
	private final Logger log = LoggerFactory.getLogger(this.getClass());

    private MessageResolver errorResolver;
    private BeneficiarioDao beneficiarioDao;
    private DateUtilService dateUtilService;
    private ValidatorDivIntService validatorDivIntService;
    private ValidatorService validatorService;
    private CatBicDao catBicDao;
    private StatusBeneficiarioDao statusBeneficiarioDao;
    private HistoricoBeneficiarioDao historicoBeneficiarioDao;
    private CustodioTipoBenefDao custodioTipoBenefDao;
    private CamposFormatosDao camposFormatosDao;
    private Map<String, String> countryISO;
    private Map<Integer, PaisInt> paises;
    /** Servicio Formatos Fiscales */
    private FormatoFiscalService serviceFormatosFiscales;

    private MailService mailService;
    private ValidatorBeneficiarioService validatorFormatW9Service;
    private ValidatorBeneficiarioService validatorFormatW8BENService;
    private ValidatorBeneficiarioService validatorFormatW8IMYService;
    private ValidatorBeneficiarioService validatorFormatW8BEN2014Service;
    private ValidatorBeneficiarioService validatorFormatMILAService;
    private ValidatorBeneficiarioService validatorFormatW8BENEService;
    private ValidatorBeneficiarioService validatorFormatW8BENE2016Service;
    private ValidatorBeneficiarioService validatorFormatW8IMY2015Service;
    private ValidatorBeneficiarioService validatorFormatW8IMY2017Service;

    /** Servicio conciliaciones, usado para la consulta de paises **/
    private ConciliacionEfectivoIntService conciliacionEfectivoIntService;

    /** Servicio para parsear el formato a xml y viceversa */
    private FormatoW8Service formatoW8Service;
    
    private List<String> querysFormatos = new ArrayList<String>();

    /**
     * Listado de formatos a depurar en el Job de depuracion de formatos fiscales.
     */
    private String listaFormatosDepurar;
    
    /**
     * Dao para las consultas de Clave ADP
     */
    private ClaveAdpDao claveAdpDao;
    
    /** Dao para las consultas de la relación custodio adp */
	private AdpCustodioPorcentajeDao adpCustodioPorcentajeDao;

    /**
     * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.ControlBeneficiariosService#insertaBeneficiario(com.indeval.portalinternacional.middleware.servicios.modelo.Beneficiario)
     */
    public Long insertaBeneficiario(final Beneficiario beneficiario, final Long idInstitucion)
            throws BusinessException {

        this.log.info("Entrando a insertaBeneficiario()");

        /* Se colocan los valores de los campos que se establecen al registrar */
        beneficiario.setFechaRegistro(this.dateUtilService.getCurrentDate());
        StatusBeneficiario statusBeneficiario = new StatusBeneficiario();
        statusBeneficiario.setIdStatusBenef(new Long(STATUS_BENEFICIARIO_REGISTRADO));
        beneficiario.setStatusBenef(statusBeneficiario);
        beneficiario.setEliminado(Boolean.FALSE);

        if (beneficiario.getPersonaFisica()) {
            beneficiario.setApellidoMaterno(this.limpiaEspaciosDobles(beneficiario
                    .getApellidoMaterno()));
            beneficiario.setApellidoPaterno(this.limpiaEspaciosDobles(beneficiario
                    .getApellidoPaterno()));
            beneficiario.setNombres(this.limpiaEspaciosDobles(beneficiario.getNombres()));
        } else {
            beneficiario.setRazonSocial(this.limpiaEspaciosDobles(beneficiario.getRazonSocial()));
        }

        this.validatorFormatW9Service.validaCapturaBeneficiario(beneficiario, idInstitucion);

        TipoFormato tipoFormato = TipoFormato.obtenerInstancia(beneficiario.getTipoFormato());
        switch (tipoFormato) {
            case W8BEN2014: {
                if (this.obtieneFechaValida().compareTo(beneficiario.getFechaFormato()) > 0) {
                    throw new BusinessException(this.errorResolver.getMessage("J0075"));
                }
                this.validatorFormatW8BEN2014Service.validaFormatoW(beneficiario);

                String rfc = beneficiario.getFormatoW8BEN().getForeignTaxIdNumb();

                if (StringUtils.isNotBlank(rfc)) {
                    beneficiario.getFormatoW8BEN().setForeignTaxIdNumb(rfc.trim().toUpperCase());
                }



                if (StringUtils.isNotBlank(beneficiario.getFormatoW8BEN().getSigner())) {
                    beneficiario.getFormatoW8BEN().setSigner(
                            beneficiario.getFormatoW8BEN().getSigner().toUpperCase());
                }

                if (this.beneficiarioDao.save(beneficiario.getFormatoW8BEN()) == null) {
                    throw new BusinessException(this.errorResolver.getMessage("J0020",
                            (Object) "Formato lW8BEN"));
                }

                String paisLine9ap2 = beneficiario.getFormatoW8BEN().getField9OptionACountry();
                if (StringUtils.isNotBlank(paisLine9ap2)) {
                    beneficiario.getFormatoW8BEN().setField9OptionACountry(
                            paisLine9ap2.toUpperCase());
                }

                this.validatorFormatW8BEN2014Service.validaDomicilioBeneficiario(
                        beneficiario.getDomicilioW8Normal(), "Domicilio de Residencia ");
                this.convierteDomicilioMayus(beneficiario.getDomicilioW8Normal());
                if (this.beneficiarioDao.save(beneficiario.getDomicilioW8Normal()) == null) {
                    throw new BusinessException(this.errorResolver.getMessage("J0020",
                            (Object) "Domicilio de Residencia"));
                }

                if (!beneficiario.getFormatoW8BEN().isDisabledDireccionPostal()) {
                    this.validatorFormatW8BEN2014Service.validaDomicilioBeneficiario(
                            beneficiario.getDomicilioW8Correo(), "Domicilio de Correo ");
                    this.convierteDomicilioMayus(beneficiario.getDomicilioW8Correo());
                    if (this.beneficiarioDao.save(beneficiario.getDomicilioW8Correo()) == null) {
                        throw new BusinessException(this.errorResolver.getMessage("J0020",
                                (Object) "Domicilio de Correo"));
                    }
                }

                break;
            }
            // W8BEN2017 es igual que el 2014
            case W8BEN2017: {
                if (this.obtieneFechaValida().compareTo(beneficiario.getFechaFormato()) > 0) {
                    throw new BusinessException(this.errorResolver.getMessage("J0075"));
                }
                this.validatorFormatW8BEN2014Service.validaFormatoW(beneficiario);

                String rfc = beneficiario.getFormatoW8BEN().getForeignTaxIdNumb();

                if (StringUtils.isNotBlank(rfc)) {
                    beneficiario.getFormatoW8BEN().setForeignTaxIdNumb(rfc.trim().toUpperCase());
                }



                if (StringUtils.isNotBlank(beneficiario.getFormatoW8BEN().getSigner())) {
                    beneficiario.getFormatoW8BEN().setSigner(
                            beneficiario.getFormatoW8BEN().getSigner().toUpperCase());
                }

                if (this.beneficiarioDao.save(beneficiario.getFormatoW8BEN()) == null) {
                    throw new BusinessException(this.errorResolver.getMessage("J0020",
                            (Object) "Formato lW8BEN"));
                }

                String paisLine9ap2 = beneficiario.getFormatoW8BEN().getField9OptionACountry();
                if (StringUtils.isNotBlank(paisLine9ap2)) {
                    beneficiario.getFormatoW8BEN().setField9OptionACountry(
                            paisLine9ap2.toUpperCase());
                }

                this.validatorFormatW8BEN2014Service.validaDomicilioBeneficiario(
                        beneficiario.getDomicilioW8Normal(), "Domicilio de Residencia ");
                this.convierteDomicilioMayus(beneficiario.getDomicilioW8Normal());
                if (this.beneficiarioDao.save(beneficiario.getDomicilioW8Normal()) == null) {
                    throw new BusinessException(this.errorResolver.getMessage("J0020",
                            (Object) "Domicilio de Residencia"));
                }

                if (!beneficiario.getFormatoW8BEN().isDisabledDireccionPostal()) {
                    this.validatorFormatW8BEN2014Service.validaDomicilioBeneficiario(
                            beneficiario.getDomicilioW8Correo(), "Domicilio de Correo ");
                    this.convierteDomicilioMayus(beneficiario.getDomicilioW8Correo());
                    if (this.beneficiarioDao.save(beneficiario.getDomicilioW8Correo()) == null) {
                        throw new BusinessException(this.errorResolver.getMessage("J0020",
                                (Object) "Domicilio de Correo"));
                    }
                }

                break;
            }

            case W8BEN: {
                if (this.obtieneFechaValida().compareTo(beneficiario.getFechaFormato()) > 0) {
                    throw new BusinessException(this.errorResolver.getMessage("J0075"));
                }

                this.validatorFormatW8BENService.validaFormatoW(beneficiario);

                String rfc = beneficiario.getFormatoW8BEN().getForeignTaxIdNumb();
                if (StringUtils.isNotBlank(rfc)) {
                    beneficiario.getFormatoW8BEN().setForeignTaxIdNumb(rfc.trim().toUpperCase());
                }

                if (this.beneficiarioDao.save(beneficiario.getFormatoW8BEN()) == null) {
                    throw new BusinessException(this.errorResolver.getMessage("J0020",
                            (Object) "Formato W8BEN"));
                }

                String paisLine9ap2 = beneficiario.getFormatoW8BEN().getField9OptionACountry();
                if (StringUtils.isNotBlank(paisLine9ap2)) {
                    beneficiario.getFormatoW8BEN().setField9OptionACountry(
                            paisLine9ap2.toUpperCase());
                }

                this.validatorFormatW8BENService.validaDomicilioBeneficiario(
                        beneficiario.getDomicilioW8Normal(), "Domicilio de Residencia ");
                this.convierteDomicilioMayus(beneficiario.getDomicilioW8Normal());
                if (this.beneficiarioDao.save(beneficiario.getDomicilioW8Normal()) == null) {
                    throw new BusinessException(this.errorResolver.getMessage("J0020",
                            (Object) "Domicilio de Residencia"));
                }

                if (!beneficiario.getFormatoW8BEN().isDisabledDireccionPostal()) {
                    this.validatorFormatW8BENService.validaDomicilioBeneficiario(
                            beneficiario.getDomicilioW8Correo(), "Domicilio de Correo ");
                    this.convierteDomicilioMayus(beneficiario.getDomicilioW8Correo());
                    if (this.beneficiarioDao.save(beneficiario.getDomicilioW8Correo()) == null) {
                        throw new BusinessException(this.errorResolver.getMessage("J0020",
                                (Object) "Domicilio de Correo"));
                    }
                }

                break;
            }

            case W8IMY: {
                this.validatorFormatW8IMYService.validaFormatoW(beneficiario);
                String rfc = beneficiario.getFormatoW8IMY().getForeignTaxIdNumb();
                if (StringUtils.isNotBlank(rfc)) {
                    beneficiario.getFormatoW8IMY().setForeignTaxIdNumb(rfc.trim().toUpperCase());
                }
                if (this.beneficiarioDao.save(beneficiario.getFormatoW8IMY()) == null) {
                    throw new BusinessException(this.errorResolver.getMessage("J0020",
                            (Object) "Formato W8IMY"));
                }

                this.validatorFormatW8IMYService.validaDomicilioBeneficiario(
                        beneficiario.getDomicilioW8Normal(), "Domicilio de Residencia ");
                this.convierteDomicilioMayus(beneficiario.getDomicilioW8Normal());
                if (this.beneficiarioDao.save(beneficiario.getDomicilioW8Normal()) == null) {
                    throw new BusinessException(this.errorResolver.getMessage("J0020",
                            (Object) "Domicilio de Residencia"));
                }

                if (!beneficiario.getFormatoW8IMY().isDisabledDireccionPostal()) {
                    this.validatorFormatW8IMYService.validaDomicilioBeneficiario(
                            beneficiario.getDomicilioW8Correo(), "Domicilio de Correo ");
                    this.convierteDomicilioMayus(beneficiario.getDomicilioW8Correo());
                    if (this.beneficiarioDao.save(beneficiario.getDomicilioW8Correo()) == null) {
                        throw new BusinessException(this.errorResolver.getMessage("J0020",
                                (Object) "Domicilio de Correo"));
                    }
                }

                break;
            }

            case W9: {
                beneficiario.getFormatoW9().setTaxClassification(
                        StringUtils.upperCase(beneficiario.getFormatoW9().getTaxClassification()));

                this.validatorFormatW9Service.validaFormatoW(beneficiario);

                if (!beneficiario.getFormatoW9().getTypeTaxPayer().getIdCampo().equals(4l)) {
                    beneficiario.getFormatoW9().setTaxClassification(null);
                }

                String bussinessName = beneficiario.getFormatoW9().getBusinessName();
                if (StringUtils.isNotBlank(bussinessName)) {
                    beneficiario.getFormatoW9().setBusinessName(bussinessName.trim().toUpperCase());
                }

                if (this.beneficiarioDao.save(beneficiario.getFormatoW9()) == null) {
                    throw new BusinessException(this.errorResolver.getMessage("J0020",
                            (Object) "Formato W9"));
                }

                this.validatorFormatW9Service.validaDomicilioBeneficiario(
                        beneficiario.getDomicilioW9(), " Domicilio W9 ");
                this.convierteDomicilioMayus(beneficiario.getDomicilioW9());
                beneficiario.setPorcentajeRetencion(null);
                if (this.beneficiarioDao.save(beneficiario.getDomicilioW9()) == null) {
                    throw new BusinessException(this.errorResolver.getMessage("J0020",
                            (Object) " Domicilio W9 "));
                }

                break;
            }
            case MILA: {
                beneficiario.getFormatoMILA().setNumeroDocumento(
                        StringUtils.upperCase(beneficiario.getFormatoMILA().getNumeroDocumento()));

                if (beneficiario.getPersonaFisica()) {
                    beneficiario.getFormatoMILA().setCaracterEntidad(null);
                    beneficiario.getFormatoMILA().setSectorEconomico(null);
                    if (beneficiario.getFormatoMILA().getPaisNacionalidad().getIdPais().intValue() != PaisInt.ID_PAIS_MEXICO) {
                        beneficiario.getFormatoMILA().setRfc(null);
                    } else if (!StringUtils.isBlank(beneficiario.getFormatoMILA().getRfc())) {
                        beneficiario.getFormatoMILA().setRfc(
                                StringUtils.upperCase(beneficiario.getFormatoMILA().getRfc()));
                    }
                } else {
                    beneficiario.getFormatoMILA().setRfc(null);
                }
                if (StringUtils.isEmpty(beneficiario.getFormatoMILA().getIdentificadorMILA())) {
                    beneficiario.getFormatoMILA().setIdentificadorMILA(
                            this.obtieneCodigoMilaBeneficiario(beneficiario.getIdCuentaNombrada()));
                }

                if (!StringUtils.isBlank(beneficiario.getFormatoMILA().getNombreDocumento())) {
                    beneficiario.getFormatoMILA().setNombreDocumento(
                            StringUtils.upperCase(beneficiario.getFormatoMILA()
                                    .getNombreDocumento()));
                }

                this.validatorFormatMILAService.validaFormatoW(beneficiario);

                FormatoMilaArchivos archivos = beneficiario.getFormatoMILA().getArchivos();
                beneficiario.getFormatoMILA().setArchivos(null);

                Long idFormato = (Long) this.beneficiarioDao.save(beneficiario.getFormatoMILA());
                if (idFormato == null) {
                    throw new BusinessException(this.errorResolver.getMessage("J0020",
                            (Object) "Formato MILA"));
                }
                archivos.setIdArchivosFormatoMILA(idFormato);

                beneficiario.getFormatoMILA().setArchivos(archivos);

                this.beneficiarioDao.save(beneficiario.getFormatoMILA());

                this.validatorFormatMILAService.validaDomicilioBeneficiario(
                        beneficiario.getDomicilioMILA(), " Domicilio MILA ");
                this.convierteDomicilioMayus(beneficiario.getDomicilioMILA());
                beneficiario.setPorcentajeRetencion(null);
                if (this.beneficiarioDao.save(beneficiario.getDomicilioMILA()) == null) {
                    throw new BusinessException(this.errorResolver.getMessage("J0020",
                            (Object) " Domicilio MILA "));
                }

                break;
            }
            case W8BENE:
                // Valida el formato
                this.validatorFormatW8BENEService.validaFormatoW(beneficiario);
                // Guarda el formato W8BEN-E
                if (this.beneficiarioDao.save(beneficiario.getFormatoW8BENE()) == null) {
                    throw new BusinessException(this.errorResolver.getMessage("J0020",
                            (Object) "Formato W8BENE"));
                }
                // Guarda los domicilios
                if (this.beneficiarioDao.save(beneficiario.getDomicilioW8Normal()) == null) {
                    throw new BusinessException(this.errorResolver.getMessage("J0020",
                            (Object) "Domicilio de Residencia"));
                }
                if (beneficiario.getDomicilioW8Correo() != null) {
                    if (this.beneficiarioDao.save(beneficiario.getDomicilioW8Correo()) == null) {
                        throw new BusinessException(this.errorResolver.getMessage("J0020",
                                (Object) "Domicilio de Correo"));
                    }
                }
                break;
            case W8BENE2016:
                // Valida el formato
                this.validatorFormatW8BENE2016Service.validaFormatoW(beneficiario);
                // Guarda el formato W8BEN-E
                if (this.beneficiarioDao.save(beneficiario.getFormatoW8BENE()) == null) {
                    throw new BusinessException(this.errorResolver.getMessage("J0020",
                            (Object) "Formato W8BENE"));
                }
                // Guarda los domicilios
                if (this.beneficiarioDao.save(beneficiario.getDomicilioW8Normal()) == null) {
                    throw new BusinessException(this.errorResolver.getMessage("J0020",
                            (Object) "Domicilio de Residencia"));
                }
                if (beneficiario.getDomicilioW8Correo() != null) {
                    if (this.beneficiarioDao.save(beneficiario.getDomicilioW8Correo()) == null) {
                        throw new BusinessException(this.errorResolver.getMessage("J0020",
                                (Object) "Domicilio de Correo"));
                    }
                }
                break;
            case W8IMY2015:
                // Valida el formato
                this.validatorFormatW8IMY2015Service.validaFormatoW(beneficiario);
                // Guarda el formato W8IMY 2015
                if (this.beneficiarioDao.save(beneficiario.getFormatoW8IMY2015()) == null) {
                    throw new BusinessException(this.errorResolver.getMessage("J0020",
                            (Object) "Formato W8IMY 2015"));
                }
                // Guarda los domicilios
                if (this.beneficiarioDao.save(beneficiario.getDomicilioW8Normal()) == null) {
                    throw new BusinessException(this.errorResolver.getMessage("J0020",
                            (Object) "Domicilio de Residencia"));
                }
                if (beneficiario.getDomicilioW8Correo() != null) {
                    if (this.beneficiarioDao.save(beneficiario.getDomicilioW8Correo()) == null) {
                        throw new BusinessException(this.errorResolver.getMessage("J0020",
                                (Object) "Domicilio de Correo"));
                    }
                }
                break;
            case W8IMY2017:
                // Valida el formato
                this.validatorFormatW8IMY2017Service.validaFormatoW(beneficiario);
                // Guarda el formato W8IMY 2015
                if (this.beneficiarioDao.save(beneficiario.getFormatoW8IMY2015()) == null) {
                    throw new BusinessException(this.errorResolver.getMessage("J0020",
                            (Object) "Formato W8IMY 2017"));
                }
                // Guarda los domicilios
                if (this.beneficiarioDao.save(beneficiario.getDomicilioW8Normal()) == null) {
                    throw new BusinessException(this.errorResolver.getMessage("J0020",
                            (Object) "Domicilio de Residencia"));
                }
                if (beneficiario.getDomicilioW8Correo() != null) {
                    if (this.beneficiarioDao.save(beneficiario.getDomicilioW8Correo()) == null) {
                        throw new BusinessException(this.errorResolver.getMessage("J0020",
                                (Object) "Domicilio de Correo"));
                    }
                }
                break;
        }
        // Conversion a Mayusculas
        if (beneficiario.getPersonaFisica()) {
            beneficiario.setNombres(beneficiario.getNombres().toUpperCase());
            beneficiario.setApellidoPaterno(beneficiario.getApellidoPaterno().toUpperCase());
            if (beneficiario.getApellidoMaterno() != null) {
                beneficiario.setApellidoMaterno(beneficiario.getApellidoMaterno().toUpperCase());
            }
        } else {
            beneficiario.setRazonSocial(beneficiario.getRazonSocial().toUpperCase());
        }
        String paisIncorporacion = beneficiario.getPaisIncorporacion();
        if (StringUtils.isNotBlank(paisIncorporacion)) {
            beneficiario.setPaisIncorporacion(paisIncorporacion.trim().toUpperCase());
        }

        if (BeneficiariosConstantes.FORMATO_W9.equals(beneficiario.getTipoFormato())) {
            beneficiario.setPaisIncorporacion("USA");
        }

        beneficiario
                .setUoiNumber(this.obtieneUOIUnico(this.obtieneCodigoBeneficiario(beneficiario)));

        //Coloca el origen del beneficiario como Dali
        beneficiario.setOrigen(BeneficiariosConstantes.ORIGEN_BENEFICIARIO_DALI);
        
        /* Se guarda el objeto */
        Long benefId = (Long) this.beneficiarioDao.save(beneficiario);
        this.beneficiarioDao.flush();
        
        if (benefId == null) {
            throw new BusinessException(this.errorResolver.getMessage("J0020",
                    (Object) "Beneficiario"));
        } else {
            if (idInstitucion != null && idInstitucion > 0) {
                BeneficiarioInstitucion beneficiarioInstitucion = new BeneficiarioInstitucion();
                beneficiarioInstitucion.setInstitucion(idInstitucion);
                beneficiarioInstitucion.setBeneficiario(benefId);
                try{
                    Long idBenefInstitucion = (Long) this.beneficiarioDao.save(beneficiarioInstitucion);
                    if (idBenefInstitucion == null) {
                        throw new BusinessException(this.errorResolver.getMessage("J0020",
                                (Object) "Institucion del Beneficiario"));
                    }
                } catch(Exception ex){
                    throw new BusinessException(this.errorResolver.getMessage("J0020",
                    		ex.getMessage() + " - " + ex.getCause(), ex.getMessage() + " - " + ex.getCause()));
                }
            }
            try{
                this.creaHistoricoBeneficiario(benefId, STATUS_BENEFICIARIO_REGISTRADO,
                        STATUS_BENEFICIARIO_REGISTRADO);
                this.beneficiarioDao.flush();
            } catch(Exception ex){
                throw new BusinessException(this.errorResolver.getMessage("J0020",
                		ex.getMessage() + " - " + ex.getCause(), ex.getMessage() + " - " + ex.getCause()));            	
            }
        }

        return benefId;
    }

    public void eliminaBeneficiario(final Long idBeneficiario) throws BusinessException {
        this.log.debug("Entrando a eliminaBeneficiario(" + idBeneficiario + ")");

        this.validatorService.validaDTONoNulo(idBeneficiario, " Beneficiario ");
        Beneficiario beneficiario = this.beneficiarioDao.findBeneficiarioById(idBeneficiario);

        if (beneficiario == null) {
            throw new BusinessException(this.errorResolver.getMessage("J0073"));
        } else {
            Long idStatusAcutal = beneficiario.getStatusBenef().getIdStatusBenef();
            beneficiario.setFechaEliminado(this.dateUtilService.getCurrentDate());

            /*
             * StatusBeneficiario status = new StatusBeneficiario();
             * status.setIdStatusBenef(STATUS_BENEFICIARIO_ELIMINADO);
             * beneficiario.setStatusBenef(status);
             */
            beneficiario.setEliminado(Boolean.TRUE);
            this.beneficiarioDao.update(beneficiario);
            this.creaHistoricoBeneficiario(beneficiario.getIdBeneficiario(), idStatusAcutal,
                    STATUS_BENEFICIARIO_ELIMINADO);
        }
    }

    public void eliminaInstitucionBeneficiario(final Long idBeneficiario, final Long idInstitucion) {
        this.log.debug("Entrando a eliminaInstitucionBeneficiario(" + idBeneficiario + "-"
                + idInstitucion + ")");
        this.validatorService.validaDTONoNulo(idBeneficiario, " Beneficiario ");
        this.validatorService.validaDTONoNulo(idInstitucion, " Institucion ");
        BeneficiarioInstitucion beneInst =
                this.beneficiarioDao.getBeneficiarioInstitucion(idBeneficiario, idInstitucion);
        if (beneInst == null) {
            throw new BusinessException(this.errorResolver.getMessage("J0078"));
        } else {
            this.beneficiarioDao.delete(beneInst);
            //Aqui se cambia el campo extraer de la tabla: T_FORMATOS_FISCALES_ING a false
            Beneficiario beneficiario = this.beneficiarioDao.findBeneficiarioById(idBeneficiario);
            if(beneficiario != null){
                FormatoFiscal formatoBean = serviceFormatosFiscales.prepareTinCobroByBeneficiarioIdInstitucion(beneficiario, idInstitucion);
                if(formatoBean != null){
                    serviceFormatosFiscales.updateTinFormato(formatoBean);
                }
            }
        }
    }
    
    /**
     * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.ControlBeneficiariosService#getBeneficiarioInstitucion(java.lang.Long)
     */
	public BeneficiarioInstitucion getBeneficiarioInstitucion(Long idBeneficiario, Long idInstitucion) throws BusinessException {
		// TODO Auto-generated method stub
		BeneficiarioInstitucion beneficiarioInst = serviceFormatosFiscales.getBeneficiarioInstitucion(idBeneficiario, idInstitucion);
		return beneficiarioInst;
	}
	
	public Beneficiario consultaBeneficiarioByUoi(String uoiBeneficiario) throws BusinessException {
		return this.beneficiarioDao.findBeneficiarioByUoiNumber(uoiBeneficiario);
	}
	
    /**
     * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.ControlBeneficiariosService#activarBeneficiario(java.lang.Long)
     */
    public void preAutorizaBeneficiario(final Long idBeneficiario) throws BusinessException {
        this.log.info("Entrando a preAutorizaBeneficiario()");

        this.validatorService.validaDTONoNulo(idBeneficiario, " Beneficiario ");
        
        Beneficiario beneficiario = this.beneficiarioDao.findBeneficiarioById(idBeneficiario);
        this.log.info("beneficiarioID ::: " + beneficiario.getIdBeneficiario());
        // Actualiza beneficiario a Pre Autorizado
        this.updateBeneficiarioStatus(beneficiario, STATUS_BENEFICIARIO_PRE_AUTORIZADO);
    }

    /**
     * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.ControlBeneficiariosService#activarBeneficiario(java.lang.Long)
     */
    public void activarBeneficiario(final Long idBeneficiario) throws BusinessException {
        this.log.info("Entrando a activarBeneficiario()");

        this.validatorService.validaDTONoNulo(idBeneficiario, " Beneficiario ");
        
        Beneficiario beneficiario = this.beneficiarioDao.findBeneficiarioById(idBeneficiario);
        
        this.log.info("beneficiarioID ::: " + beneficiario.getIdBeneficiario());
        // Actualiza beneficiario
        this.updateBeneficiario(beneficiario);
        // Almacena en tabla: T_FORMATOS_FISCALES_ING
        // validatorFormatoFiscalTinService
        this.log.info("beneficiarioID Update");
        List<FormatoFiscal> listFormatoFiscal = serviceFormatosFiscales.prepareFormatoFiscalTin(beneficiario);
        this.log.info("beneficiarioID Update :: prepareFormatoFiscalTin");
        /*
         * Solo formatos fiscales: W8BEN, W9, W8BENE, W8IMY
         */
        listFormatoFiscal = serviceFormatosFiscales.validateFormatosFiscales(listFormatoFiscal);
        this.log.info("beneficiarioID Update :: listFormatoFiscal :: " + listFormatoFiscal);
        if(listFormatoFiscal == null || listFormatoFiscal.size() == 0){
        	// Si hay error, envia correo electronico
            this.log.info("sendMail.. ");
//        	this.mailService.sendMail("message", "destino", "subject", "from", "UTF-8");
        } else{
        	serviceFormatosFiscales.saveFormatoFiscalTin(listFormatoFiscal, beneficiario);
        }
    }
    
    /**
     * Metodo para actualizar beneficiario
     * @param beneficiario
     */
    public void updateBeneficiario(Beneficiario beneficiario){
        this.log.info("Entrando a updateBeneficiario()");
        
        if (beneficiario == null) {
            throw new BusinessException(this.errorResolver.getMessage("J0073"));
        }
        this.log.info("beneficiario.getStatusBenef().getIdStatusBenef() :: " + beneficiario.getStatusBenef().getIdStatusBenef());
        if (!beneficiario.getStatusBenef().getIdStatusBenef()
                .equals(STATUS_BENEFICIARIO_AUTORIZADO)) {
            Long idStatusAcutal = beneficiario.getStatusBenef().getIdStatusBenef();
            StatusBeneficiario status = new StatusBeneficiario();
            status.setIdStatusBenef(STATUS_BENEFICIARIO_AUTORIZADO);
            beneficiario.setStatusBenef(status);
            beneficiario.setFechaAutorizacion(this.dateUtilService.getCurrentDate());
            this.beneficiarioDao.update(beneficiario);
            this.beneficiarioDao.flush();
            this.creaHistoricoBeneficiario(beneficiario.getIdBeneficiario(), idStatusAcutal,
                    STATUS_BENEFICIARIO_AUTORIZADO);
            this.log.info("creaHistoricoBeneficiario :: finish");
        } else {
            throw new BusinessException(this.errorResolver.getMessage("J0072"));
        }
    }
    
    /**
     * Metodo para actualizar beneficiario
     * @param beneficiario
     */
    public void updateBeneficiarioStatus(Beneficiario beneficiario, long statusActualizado){
        this.log.info("Entrando a updateBeneficiario()");
        
        if (beneficiario == null) {
            throw new BusinessException(this.errorResolver.getMessage("J0073"));
        }
        this.log.info("beneficiario.getStatusBenef().getIdStatusBenef() :: " + beneficiario.getStatusBenef().getIdStatusBenef());
        if (!beneficiario.getStatusBenef().getIdStatusBenef()
                .equals(statusActualizado)) {
        	
            Long idStatusActual = beneficiario.getStatusBenef().getIdStatusBenef();
            StatusBeneficiario status = new StatusBeneficiario();
            status.setIdStatusBenef(statusActualizado);
            beneficiario.setStatusBenef(status);
            beneficiario.setFechaAutorizacion(this.dateUtilService.getCurrentDate());
            this.beneficiarioDao.update(beneficiario);
            this.beneficiarioDao.flush();
            
            this.creaHistoricoBeneficiario(beneficiario.getIdBeneficiario(), idStatusActual, statusActualizado);
            this.log.info("creaHistoricoBeneficiario :: finish");
        } else {
            throw new BusinessException(this.errorResolver.getMessage("J0147", new Object[] { beneficiario.getIdBeneficiario(), beneficiario.getStatusBenef().getDescStatusBenef()}));
        }
    }
    
	/**
	 * Metodo para actualizar estatus de un beneficiario
	 * @param idBeneficiario
	 * @param statusActualizado
	 */
    public void actualizaStatusBeneficiario(Beneficiario beneficiario, long statusActualizado, String descStatusActualizado){
        this.log.info("Entrando a actualizaStatusBeneficiario()");

        if (beneficiario == null) {
            throw new BusinessException(this.errorResolver.getMessage("J0073"));
        }
        
        this.log.info("beneficiario.getStatusBenef().getIdStatusBenef() :: " + beneficiario.getStatusBenef().getIdStatusBenef());

        Long idStatusActual = beneficiario.getStatusBenef().getIdStatusBenef();
        
		if(statusActualizado == STATUS_BENEFICIARIO_ELIMINADO){
			if(beneficiario.getEliminado()){
				throw new BusinessException(this.errorResolver.getMessage("J0148"));
			} else{
				this.eliminaBeneficiario(beneficiario.getIdBeneficiario());
			}
		} else if (!beneficiario.getStatusBenef().getIdStatusBenef().equals(statusActualizado) && statusActualizado != 0) {
            StatusBeneficiario status = new StatusBeneficiario();
            status.setIdStatusBenef(statusActualizado);
            beneficiario.setStatusBenef(status);
            beneficiario.setFechaAutorizacion(this.dateUtilService.getCurrentDate());
            this.beneficiarioDao.update(beneficiario);
            this.beneficiarioDao.flush();
            
            this.creaHistoricoBeneficiario(beneficiario.getIdBeneficiario(), idStatusActual, statusActualizado);
            this.log.info("creaHistoricoBeneficiario :: finish");
        } else {
            throw new BusinessException(this.errorResolver.getMessage("J0147", new Object[] { beneficiario.getIdBeneficiario(), beneficiario.getStatusBenef().getDescStatusBenef()}));
        }
    }
    
    public void cambiaStatusBeneficiario(Beneficiario beneficiario, long statusActualizado, String descStatusActualizado) {
        ListStatusBeneficiario statusBeneficiario = ListStatusBeneficiario.obtenerInstancia(beneficiario.getStatusBenef().getDescStatusBenef());
        switch (statusBeneficiario) {
            case REGISTRADO:
            case REGISTRADO_CORTO:
            	if(descStatusActualizado.equals(ListStatusBeneficiario.ELIMINADO.getStatusBeneficiario())
                		|| descStatusActualizado.equals(ListStatusBeneficiario.ELIMINADO_CORTO.getStatusBeneficiario())
                		|| descStatusActualizado.equals(ListStatusBeneficiario.PRE_AUTORIZADO.getStatusBeneficiario())
                		|| descStatusActualizado.equals(ListStatusBeneficiario.PRE_AUTORIZADO_CORTO.getStatusBeneficiario())){
            		this.actualizaStatusBeneficiario(beneficiario, statusActualizado, descStatusActualizado);
            	} else {
            		throw new BusinessException(this.errorResolver.getMessage("J0149", new Object[] { ListStatusBeneficiario.REGISTRADO.getStatusBeneficiario(), descStatusActualizado}));
            	}
            break;
            
            case PRE_AUTORIZADO:
            case PRE_AUTORIZADO_CORTO:
            	if(descStatusActualizado.equals(ListStatusBeneficiario.AUTORIZADO.getStatusBeneficiario())
                		|| descStatusActualizado.equals(ListStatusBeneficiario.AUTORIZADO_CORTO.getStatusBeneficiario())){
            			this.activarBeneficiario(beneficiario.getIdBeneficiario());
            	} else if(descStatusActualizado.equals(ListStatusBeneficiario.ELIMINADO.getStatusBeneficiario())
                		|| descStatusActualizado.equals(ListStatusBeneficiario.ELIMINADO_CORTO.getStatusBeneficiario())){
            			this.actualizaStatusBeneficiario(beneficiario, statusActualizado, descStatusActualizado);
                } else if(descStatusActualizado.equals(ListStatusBeneficiario.CANCELADO.getStatusBeneficiario())
                		|| descStatusActualizado.equals(ListStatusBeneficiario.CANCELADO_CORTO.getStatusBeneficiario())){
            			this.cancelaBeneficiario(beneficiario.getIdBeneficiario());
            	} else {
            		throw new BusinessException(this.errorResolver.getMessage("J0149", new Object[] { ListStatusBeneficiario.PRE_AUTORIZADO.getStatusBeneficiario(), descStatusActualizado}));
                }
            break;
            
            case AUTORIZADO:
            case AUTORIZADO_CORTO:
            	if(descStatusActualizado.equals(ListStatusBeneficiario.CANCELADO.getStatusBeneficiario())
                		|| descStatusActualizado.equals(ListStatusBeneficiario.CANCELADO_CORTO.getStatusBeneficiario())){
            			this.cancelaBeneficiario(beneficiario.getIdBeneficiario());
            	} else if(descStatusActualizado.equals(ListStatusBeneficiario.PRE_AUTORIZADO.getStatusBeneficiario())
                		|| descStatusActualizado.equals(ListStatusBeneficiario.PRE_AUTORIZADO_CORTO.getStatusBeneficiario())){
            		this.actualizaStatusBeneficiario(beneficiario, statusActualizado, descStatusActualizado);
            	} else {
            		throw new BusinessException(this.errorResolver.getMessage("J0149", new Object[] { ListStatusBeneficiario.AUTORIZADO.getStatusBeneficiario(), descStatusActualizado}));
                	}
            break;
            
            case ACTUALIZADO:
            case ACTUALIZADO_CORTO:
            	if(descStatusActualizado.equals(ListStatusBeneficiario.CANCELADO.getStatusBeneficiario())
                		|| descStatusActualizado.equals(ListStatusBeneficiario.CANCELADO_CORTO.getStatusBeneficiario())){
            			this.cancelaBeneficiario(beneficiario.getIdBeneficiario());
            	} else if(descStatusActualizado.equals(ListStatusBeneficiario.ELIMINADO.getStatusBeneficiario())
                		|| descStatusActualizado.equals(ListStatusBeneficiario.ELIMINADO_CORTO.getStatusBeneficiario())
                		|| descStatusActualizado.equals(ListStatusBeneficiario.PRE_AUTORIZADO.getStatusBeneficiario())
                		|| descStatusActualizado.equals(ListStatusBeneficiario.PRE_AUTORIZADO_CORTO.getStatusBeneficiario())){
            			this.actualizaStatusBeneficiario(beneficiario, statusActualizado, descStatusActualizado);
                } else {
            		throw new BusinessException(this.errorResolver.getMessage("J0149", new Object[] { ListStatusBeneficiario.ACTUALIZADO.getStatusBeneficiario(), descStatusActualizado}));
                }
            break;            
            case CANCELADO:
            case CANCELADO_CORTO:
            	if(descStatusActualizado.equals(ListStatusBeneficiario.ELIMINADO.getStatusBeneficiario())
                		|| descStatusActualizado.equals(ListStatusBeneficiario.ELIMINADO_CORTO.getStatusBeneficiario())
                		|| descStatusActualizado.equals(ListStatusBeneficiario.PRE_AUTORIZADO.getStatusBeneficiario())
                		|| descStatusActualizado.equals(ListStatusBeneficiario.PRE_AUTORIZADO_CORTO.getStatusBeneficiario())){
            		this.actualizaStatusBeneficiario(beneficiario, statusActualizado, descStatusActualizado);
                } else{
            		throw new BusinessException(this.errorResolver.getMessage("J0149", new Object[] { ListStatusBeneficiario.CANCELADO.getStatusBeneficiario(), descStatusActualizado}));
                }
            break;
            
            case VENCIDO:
            case VENCIDO_CORTO:
            	if(descStatusActualizado.equals(ListStatusBeneficiario.PRE_AUTORIZADO.getStatusBeneficiario())
                		|| descStatusActualizado.equals(ListStatusBeneficiario.PRE_AUTORIZADO_CORTO.getStatusBeneficiario())){
        			this.actualizaStatusBeneficiario(beneficiario, statusActualizado, descStatusActualizado);
                } else {
            		throw new BusinessException(this.errorResolver.getMessage("J0149", new Object[] { ListStatusBeneficiario.VENCIDO.getStatusBeneficiario(), descStatusActualizado}));
                }
            break;

            default:
            	throw new BusinessException(this.errorResolver.getMessage("J0149", new Object[] { statusBeneficiario, descStatusActualizado}));
        }
    }

    /**
     * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.ControlBeneficiariosService#cancelaBeneficiario(java.lang.Long)
     */
    public void cancelaBeneficiario(final Long idBeneficiario) throws BusinessException {
        this.log.info("Entrando a cancelaBeneficiario()");
        this.validatorService.validaDTONoNulo(idBeneficiario, " Beneficiario ");

        Beneficiario beneficiario = this.beneficiarioDao.findBeneficiarioById(idBeneficiario);

        if (beneficiario == null) {
            throw new BusinessException(this.errorResolver.getMessage("J0073"));
        }

        if (!beneficiario.getStatusBenef().getIdStatusBenef().equals(STATUS_BENEFICIARIO_CANCELADO)) {
            Long idStatusAcutal = beneficiario.getStatusBenef().getIdStatusBenef();
            StatusBeneficiario status = new StatusBeneficiario();
            status.setIdStatusBenef(STATUS_BENEFICIARIO_CANCELADO);
            beneficiario.setStatusBenef(status);
            this.beneficiarioDao.update(beneficiario);
            this.creaHistoricoBeneficiario(beneficiario.getIdBeneficiario(), idStatusAcutal,
                    STATUS_BENEFICIARIO_CANCELADO);
            
            List<FormatoFiscal> listFormatoFiscal = serviceFormatosFiscales.prepareFormatoFiscalTin(beneficiario);
            /*
             * Solo formatos fiscales: W8BEN, W9, W8BENE, W8IMY
             */
            listFormatoFiscal = serviceFormatosFiscales.validateFormatosFiscales(listFormatoFiscal);
            if(listFormatoFiscal != null){
            	for (FormatoFiscal formatoFiscal : listFormatoFiscal) {
            		Boolean isNuevoTin = serviceFormatosFiscales.validateExistsFormatoFiscalTin(formatoFiscal);
            		if(!isNuevoTin){
            			serviceFormatosFiscales.actualizaFormatoFiscal(formatoFiscal);
            		} 
//            		else{
//            			serviceFormatosFiscales.guardaTinFormatoFiscal(formatoFiscal);
//            		}
				}
            }
            
            
        } else {
            throw new BusinessException(this.errorResolver.getMessage("J0074"));
        }
    }

    /**
     * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.ControlBeneficiariosService#agregaBeneficiarioInstitucion(java.lang.Long,
     *      java.lang.Long)
     */
    public void agregaBeneficiarioInstitucion(final Long idBeneficiario, final Long idInstitucion)
            throws BusinessException {
        this.log.info("Entrando a insertaBeneficiario()");

        this.validatorService.validaDTONoNulo(idBeneficiario, " Beneficiario ");
        this.validatorService.validaDTONoNulo(idInstitucion, " Institucion ");

        this.validatorDivIntService.validaInstitucion(idInstitucion);

        Beneficiario beneficiario = this.beneficiarioDao.findBeneficiarioById(idBeneficiario);

        if (beneficiario != null
                && beneficiario.getStatusBenef() != null
                && (beneficiario.getStatusBenef().getIdStatusBenef()
                        .compareTo(STATUS_BENEFICIARIO_AUTORIZADO) == 0 || beneficiario
                        .getStatusBenef().getIdStatusBenef().compareTo(STATUS_BENEFICIARIO_VENCIDO) == 0)) {
            Set<Institucion> instituciones = beneficiario.getInstitucion();
            for (Institucion i : instituciones) {
                if (i != null && i.getIdInstitucion().compareTo(idInstitucion) == 0) {
                    throw new BusinessException(this.errorResolver.getMessage("J0070",
                            (Object) null));
                }
            }
            BeneficiarioInstitucion beneficiarioInstitucion = new BeneficiarioInstitucion();
            beneficiarioInstitucion.setBeneficiario(idBeneficiario);
            beneficiarioInstitucion.setInstitucion(idInstitucion);
//            beneficiarioInstitucion.setGeneraCobro(generaCobroTin);
            this.beneficiarioDao.save(beneficiarioInstitucion);
        } else {
            throw new BusinessException(this.errorResolver.getMessage("J0069", (Object) null));
        }

    }

    /**
     * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.ControlBeneficiariosService#obtieneCodigoBeneficiario(com.indeval.portalinternacional.middleware.servicios.modelo.Beneficiario)
     */
    public String obtieneCodigoBeneficiario(final Beneficiario beneficiario)
            throws BusinessException {
        StringBuilder codigoUOI = new StringBuilder();

        String pais = beneficiario.getPaisIncorporacion();

        if (StringUtils.isNotBlank(pais)) {
            Integer idPais = null;
            // Solo los de mila tienen el pais como un catalogo, por lo que tenemos el codigo ISO ya
            if (BeneficiariosConstantes.FORMATO_MILA.equals(beneficiario.getTipoFormato())) {
                idPais = beneficiario.getFormatoMILA().getPaisNacionalidad().getIdPais();
            }
            pais = pais.trim().toUpperCase();
            if (idPais != null && this.paises.containsKey(idPais)) {
                codigoUOI.append(this.paises.get(idPais).getClave());
            } else if (this.countryISO.containsKey(pais)) {
                codigoUOI.append(this.countryISO.get(pais));
            } else {
                codigoUOI.append(pais.length() >= 2 ? pais.substring(0, 2) : pais.substring(0, 1)
                        + "_");
            }
        } else {
            throw new BusinessException("Pais de Incorporacion Invalido");
        }

        // Postura de la entidad
        if (BeneficiariosConstantes.FORMATO_W8_BEN.equals(beneficiario.getTipoFormato())
                || BeneficiariosConstantes.FORMATO_W8_BEN2014.equals(beneficiario.getTipoFormato())
                || BeneficiariosConstantes.FORMATO_W8_BEN2017.equals(beneficiario.getTipoFormato())
                || BeneficiariosConstantes.FORMATO_W8_BEN_E.equals(beneficiario.getTipoFormato())
                || BeneficiariosConstantes.FORMATO_W8_BEN_E_2016.equals(beneficiario
                        .getTipoFormato())
                || BeneficiariosConstantes.FORMATO_W9.equals(beneficiario.getTipoFormato())
                || BeneficiariosConstantes.FORMATO_MILA.equals(beneficiario.getTipoFormato())) {
            codigoUOI.append(BEN_POSTURA_BENEFICIARIO);
        } else if (BeneficiariosConstantes.FORMATO_W8_IMY.equals(beneficiario.getTipoFormato())
                || BeneficiariosConstantes.FORMATO_W8_IMY2015.equals(beneficiario.getTipoFormato())
                || BeneficiariosConstantes.FORMATO_W8_IMY2017.equals(beneficiario.getTipoFormato())) {
            codigoUOI.append(BEN_POSTURA_INTERMEDIARIO);
        }

        // Tipo de la entidad
        if (beneficiario.getPersonaFisica()
                || BeneficiariosConstantes.FORMATO_W9.equals(beneficiario.getTipoFormato())
                && beneficiario.getFormatoW9().getTypeTaxPayer().getIdCampo() == 1l) {
            codigoUOI.append(BEN_TIPO_ENTIDAD_PFISICA);
        } else {
            codigoUOI.append(BEN_TIPO_ENTIDAD_PMORAL);
            if (BeneficiariosConstantes.FORMATO_W8_BEN.equals(beneficiario.getTipoFormato())
                    || BeneficiariosConstantes.FORMATO_W8_BEN2014.equals(beneficiario
                            .getTipoFormato())
                    || BeneficiariosConstantes.FORMATO_W8_BEN2017.equals(beneficiario
                            .getTipoFormato())
                    || BeneficiariosConstantes.FORMATO_W8_BEN_E.equals(beneficiario
                            .getTipoFormato())
                    || BeneficiariosConstantes.FORMATO_W8_BEN_E_2016.equals(beneficiario
                            .getTipoFormato())
                    || BeneficiariosConstantes.FORMATO_W9.equals(beneficiario.getTipoFormato())
                    || BeneficiariosConstantes.FORMATO_MILA.equals(beneficiario.getTipoFormato())) {
                codigoUOI.append(BEN_TIPO_ENTIDAD_PMORAL_BFINAL);
            } else {
                // Caso W8IMY
                if (BeneficiariosConstantes.FORMATO_W8_IMY.equals(beneficiario.getTipoFormato())) {
                    FormatoW8IMY formato = beneficiario.getFormatoW8IMY();
                    if (formato.getField3().getIdCampo() == 1l) {
                        codigoUOI.append(BEN_TIPO_ENTIDAD_PMORAL_INTER_CALIF);
                    } else {
                        codigoUOI.append(BEN_TIPO_ENTIDAD_PMORAL_INTER_SIMPLE);
                    }
                }
                // Caso W8IMY2015
                else if (BeneficiariosConstantes.FORMATO_W8_IMY2015.equals(beneficiario
                        .getTipoFormato())) {
                    FormaW8IMY2015 w8imy2015 =
                            this.formatoW8Service.obtenerCamposFormatoW8IMY2015(beneficiario
                                    .getFormatoW8IMY2015().getCamposFormato());
                    if (w8imy2015.isPartIII()) {
                        codigoUOI.append(BEN_TIPO_ENTIDAD_PMORAL_INTER_CALIF);
                    } else {
                        codigoUOI.append(BEN_TIPO_ENTIDAD_PMORAL_INTER_SIMPLE);
                    }
                }
                // Caso W8IMY2017
                else if (BeneficiariosConstantes.FORMATO_W8_IMY2017.equals(beneficiario
                        .getTipoFormato())) {
                    FormaW8IMY2017 w8imy2017 =
                            this.formatoW8Service.obtenerCamposFormatoW8IMY2017(beneficiario
                                    .getFormatoW8IMY2015().getCamposFormato());
                    if (w8imy2017.isPartIcmp4a()) {
                        codigoUOI.append(BEN_TIPO_ENTIDAD_PMORAL_INTER_CALIF);
                    } else {
                        codigoUOI.append(BEN_TIPO_ENTIDAD_PMORAL_INTER_SIMPLE);
                    }
                }
            }
        }

        // Nombre beneficiario
        if (beneficiario.getPersonaFisica()
                || BeneficiariosConstantes.FORMATO_W9.equals(beneficiario.getTipoFormato())
                && beneficiario.getFormatoW9().getTypeTaxPayer().getIdCampo() == 1l) {
            if (BeneficiariosConstantes.FORMATO_W9.equals(beneficiario.getTipoFormato())) {
                String ssn = beneficiario.getFormatoW9().getSsn();
                if (StringUtils.isNotBlank(ssn)) {
                    codigoUOI.append(ssn.replaceAll("-", "").substring(0, 4));
                } else {
                    throw new BusinessException("SSN Invalido");
                }
            } else if (BeneficiariosConstantes.FORMATO_W8_BEN.equals(beneficiario.getTipoFormato())
                    || BeneficiariosConstantes.FORMATO_W8_BEN2014.equals(beneficiario
                            .getTipoFormato())
                    || BeneficiariosConstantes.FORMATO_W8_BEN2017.equals(beneficiario
                            .getTipoFormato())) {
                // TODO Validar Campos
                String rfc = beneficiario.getFormatoW8BEN().getForeignTaxIdNumb();
                if (StringUtils.isNotBlank(rfc)) {
                    codigoUOI.append(rfc.length() >= 4 ? rfc.substring(0, 4) : rfc);
                } else {
                    String nombre = beneficiario.getNombres();
                    nombre = nombre.trim().toUpperCase();
                    String apellidoPaterno = beneficiario.getApellidoPaterno();
                    apellidoPaterno = apellidoPaterno.trim().toUpperCase();
                    String apellidoMaterno = beneficiario.getApellidoMaterno();
                    if (StringUtils.isBlank(beneficiario.getApellidoMaterno())) {
                        codigoUOI.append(apellidoPaterno.length() >= 3 ? apellidoPaterno.substring(
                                0, 3) : apellidoPaterno);
                    } else {
                        apellidoMaterno = apellidoMaterno.trim().toUpperCase();
                        codigoUOI.append(apellidoPaterno.length() >= 2 ? apellidoPaterno.substring(
                                0, 2) : apellidoPaterno);
                        codigoUOI.append(beneficiario.getApellidoMaterno().trim().toUpperCase()
                                .substring(0, 1));
                    }
                    codigoUOI.append(nombre.substring(0, 1));
                }
            } else if (BeneficiariosConstantes.FORMATO_MILA.equals(beneficiario.getTipoFormato())) {
                String ssn = beneficiario.getFormatoMILA().getNumeroDocumento();
                // Verificamos que el documento de identidad es un SSN
                if (beneficiario.getFormatoMILA().getTipoDocumentoIndentidad().getIdTipoDocumento() == 71
                        && StringUtils.isNotBlank(ssn)) {
                    codigoUOI.append(ssn.replaceAll("-", "").substring(0, 4));
                } else {
                    String rfc = beneficiario.getFormatoMILA().getRfc();
                    if (StringUtils.isNotBlank(rfc)) {
                        codigoUOI.append(rfc.length() >= 4 ? rfc.substring(0, 4) : rfc);
                    } else {
                        String nombre = beneficiario.getNombres();
                        nombre = nombre.trim().toUpperCase();
                        String apellidoPaterno = beneficiario.getApellidoPaterno();
                        apellidoPaterno = apellidoPaterno.trim().toUpperCase();
                        String apellidoMaterno = beneficiario.getApellidoMaterno();
                        if (StringUtils.isBlank(beneficiario.getApellidoMaterno())) {
                            codigoUOI.append(apellidoPaterno.length() >= 3 ? apellidoPaterno
                                    .substring(0, 3) : apellidoPaterno);
                        } else {
                            apellidoMaterno = apellidoMaterno.trim().toUpperCase();
                            codigoUOI.append(apellidoPaterno.length() >= 2 ? apellidoPaterno
                                    .substring(0, 2) : apellidoPaterno);
                            codigoUOI.append(beneficiario.getApellidoMaterno().trim().toUpperCase()
                                    .substring(0, 1));
                        }
                        codigoUOI.append(nombre.substring(0, 1));
                    }
                }
            } else {
                throw new BusinessException("Caso no contemplado para UOI");
            }
        } else {
            if (BeneficiariosConstantes.FORMATO_W9.equals(beneficiario.getTipoFormato())) {
                String ein = beneficiario.getFormatoW9().getEmployerIdNumb();
                if (StringUtils.isNotBlank(ein)) {
                    codigoUOI.append(ein.replaceAll("-", "").substring(0, 3));
                } else {
                    throw new BusinessException("EIN Invalido");
                }
            } else if (BeneficiariosConstantes.FORMATO_MILA.equals(beneficiario.getTipoFormato())
                    && beneficiario.getFormatoMILA().getTipoDocumentoIndentidad()
                            .getIdTipoDocumento() == 73) {
                String ein = beneficiario.getFormatoMILA().getNumeroDocumento();
                if (StringUtils.isNotBlank(ein)) {
                    codigoUOI.append(ein.replaceAll("-", "").substring(0, 3));
                } else {
                    throw new BusinessException("EIN Invalido");
                }
            } else {
                String rfc = null;
                if (BeneficiariosConstantes.FORMATO_W8_IMY.equals(beneficiario.getTipoFormato())) {
                    rfc = beneficiario.getFormatoW8IMY().getForeignTaxIdNumb();
                } else if (BeneficiariosConstantes.FORMATO_W8_BEN.equals(beneficiario
                        .getTipoFormato())
                        || BeneficiariosConstantes.FORMATO_W8_BEN2014.equals(beneficiario
                                .getTipoFormato())
                        || BeneficiariosConstantes.FORMATO_W8_BEN2017.equals(beneficiario
                                .getTipoFormato())) {
                    rfc = beneficiario.getFormatoW8BEN().getForeignTaxIdNumb();
                } else if (BeneficiariosConstantes.FORMATO_MILA.equals(beneficiario
                        .getTipoFormato())) {
                    // Solamente si es persona moral y tiene el tipo de documento RFC
                    if (beneficiario.getFormatoMILA().getTipoDocumentoIndentidad()
                            .getIdTipoDocumento() == 70) {
                        rfc = beneficiario.getFormatoMILA().getNumeroDocumento();
                    }
                }
                if (StringUtils.isNotBlank(rfc)) {
                    codigoUOI.append(rfc.length() >= 3 ? rfc.substring(0, 3) : rfc);
                } else {
                    String razonSocial = beneficiario.getRazonSocial().trim().toUpperCase();
                    codigoUOI.append(razonSocial.length() >= 3 ? razonSocial.substring(0, 3)
                            : razonSocial);
                }
            }
        }
        /*
         * if (BeneficiariosConstantes.FORMATO_W8_BEN.equals(beneficiario.getTipoFormato()) ||
         * BeneficiariosConstantes.FORMATO_W8_BEN2014.equals(beneficiario.getTipoFormato()) ||
         * BeneficiariosConstantes.FORMATO_W8_BEN_E.equals(beneficiario.getTipoFormato())) {
         * Calendar cal = Calendar.getInstance(); cal.setTime(beneficiario.getFechaFormato());
         * Integer anio = cal.get(Calendar.YEAR) + 3; String cadena = String.valueOf(anio);
         * codigoUOI.append(cadena.substring(cadena.length() - 2, cadena.length())); } else {
         * codigoUOI.append("00"); }
         */
        // Retorna el código UOI
        return codigoUOI.toString();
    }


    private String obtieneUOIUnico(final String uoiNumber) throws BusinessException {
        String uoiSinEspacios = null;
        if (uoiNumber != null) {
            uoiSinEspacios = uoiNumber.trim().replace(" ", "-");
        } else {
            throw new BusinessException("El UOI es nulo. No hay datos para generar su consecutivo");
        }
        String uoi = this.getConsecutivoUOI(uoiSinEspacios);

        // revisar si todavia se encuentra repetido
        Long repetidos = this.beneficiarioDao.findBeneficiarioByUOI(uoi);
        if (repetidos != null && repetidos > 0) {
            uoi = "RE" + uoiSinEspacios.substring(2);
            uoi = this.getConsecutivoUOI(uoi);
        }

        return uoi;
    }

    private String getConsecutivoUOI(String uoiSinEspacios) {
        Long maxConsecutivo = this.beneficiarioDao.findUOIMaxConsecutivo(uoiSinEspacios);

        if (maxConsecutivo != null && maxConsecutivo > 0 && maxConsecutivo < 10000l) {
            uoiSinEspacios =
                    uoiSinEspacios + StringUtils.leftPad(maxConsecutivo.toString(), 4, "0");
        } else {
            uoiSinEspacios = uoiSinEspacios + "0000";
        }
        return uoiSinEspacios;
    }

    /**
     * Genera el objeto BO del domicilio
     * 
     * @param domicilio
     */
    private void convierteDomicilioMayus(final Domicilio domicilio) {
        domicilio.setStreet(domicilio.getStreet().toUpperCase());
        domicilio.setOuterNumber(domicilio.getOuterNumber().toUpperCase());
        domicilio.setPostalCode(domicilio.getPostalCode().toUpperCase());
        domicilio.setCityTown(domicilio.getCityTown().toUpperCase());
        domicilio.setStateProvince(domicilio.getStateProvince().toUpperCase());
        String pais = domicilio.getCountry();
        if (StringUtils.isNotBlank(pais)) {
            domicilio.setCountry(pais.toUpperCase());
        }
        String numeroInterior = domicilio.getInteriorNumber();
        if (StringUtils.isNotBlank(numeroInterior)) {
            domicilio.setInteriorNumber(numeroInterior.toUpperCase());
        }
    }

    /**
     * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.ControlBeneficiariosService#consultaBeneficiarios(com.indeval.portalinternacional.middleware.servicios.modelo.Beneficiario,
     *      com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO)
     */
    public PaginaVO consultaBeneficiarios(final ConsultaBeneficiariosParam param, PaginaVO paginaVO, Boolean isPopup)
            throws BusinessException {

        this.log.info("Entrando a consultaBeneficiarios()");

        /*
         * if (StringUtils.isNotBlank(param.getNombreRazonSocial())) { PaginaVO paginaTemp = new
         * PaginaVO(); paginaTemp.setOffset(0); paginaTemp.setRegistrosXPag(PaginaVO.TODOS);
         * paginaTemp = beneficiarioDao.findBeneficiarios(param, paginaTemp); List<Beneficiario>
         * listadoReal = new ArrayList<Beneficiario>(); for (Beneficiario ben : (List<Beneficiario>)
         * paginaTemp.getRegistros()) { if
         * (StringUtils.contains(StringUtils.deleteWhitespace(ben.getNombreGeneral().toUpperCase()),
         * StringUtils.deleteWhitespace(param.getNombreRazonSocial().toUpperCase()))) {
         * listadoReal.add(ben); } } log.debug("Registros del servicio: [" + listadoReal.size() +
         * "]"); paginaVO.extraerSublist(listadoReal); } else {
         */
        paginaVO = this.beneficiarioDao.findBeneficiarios(param, paginaVO,isPopup);
        this.log.debug("Registros del servicio: [" + paginaVO.getRegistros().size() + "]");
        /*
         * }
         * 
         * log.debug("Registros del servicio: [" + paginaVO.getRegistros().size() + "]");
         * log.debug("Tamaño del servicio: [" + paginaVO.getTotalRegistros() + "]");
         */

        return paginaVO;

    }

    public List<BeneficiariosPaginacionVO> consultaPaginasBeneficiarios(
            final ConsultaBeneficiariosParam param, Boolean isPopup) throws BusinessException {
        List<BeneficiariosPaginacionVO> mostrar = new ArrayList<BeneficiariosPaginacionVO>();

        this.log.info("Entrando a consultaBeneficiarios()");

        PaginaVO pagina = new PaginaVO();
        param.setTraeDireccion(false);// siempre sin direccion
        param.setOrdernar(false);// no solicita ordenacion
        pagina.setRegistrosXPag(null);// siempre trae todos los registros

        pagina = this.beneficiarioDao.findBeneficiarios(param, pagina, isPopup);

        if (pagina != null && pagina.getRegistros() != null && !pagina.getRegistros().isEmpty()) {
            List<Beneficiario> benefs = pagina.getRegistros();
            pagina = null;
            String letra = null;
            BeneficiariosPaginacionVO beneficiario = null;
            // obtiene las letras con el num de ocurrencias
            Map<String, Integer> letras = new HashMap<String, Integer>();
            List<String> letters = new ArrayList<String>();
            for (Beneficiario benef : benefs) {
                letra =
                        StringUtils.isNotBlank(benef.getNombres()) ? benef.getNombres().trim()
                                .substring(0, 1) : benef.getRazonSocial().trim().substring(0, 1);
                if (letras.get(letra) == null) {
                    letras.put(letra, 1);
                    letters.add(letra);
                } else {
                    letras.put(letra, letras.get(letra) + 1);
                }
            }// fin for
             // ordenar la ñ
            Collator esCollator = Collator.getInstance(new Locale("es"));
            Collections.sort(letters, esCollator);
            Map<String, Integer> letras2 = new LinkedHashMap<String, Integer>();
            for (String string : letters) {
                letras2.put(string, letras.get(string));
            }
            // limpia los objetos pesados de memoria
            benefs = null;
            int numPagina = 0;
            // llena los objetos en base a las letras
            for (Map.Entry<String, Integer> letter : letras2.entrySet()) {
                if (letter.getValue() > 0 && !letter.getKey().equals("_")) { // obtiene el numero de
                                                                             // paginas
                    numPagina = letter.getValue() / 500;
                    if (letter.getValue() % 500 > 0) {
                        numPagina = numPagina + 1;
                    }
                    // obtiene la etiqueta a mostrar en base al no. de paginas
                    if (numPagina == 1) {
                        beneficiario = new BeneficiariosPaginacionVO();
                        beneficiario.setEtiquetaMostrar(letter.getKey());
                        beneficiario.setPaginas(INTEGER_ZERO);
                        beneficiario.setLetra(letter.getKey());
                        beneficiario.setCantidad(letter.getValue());
                        mostrar.add(beneficiario);
                    } else {
                        for (int i = 0; i < numPagina; i++) {
                            beneficiario = new BeneficiariosPaginacionVO();
                            beneficiario.setPaginas(i);
                            beneficiario.setEtiquetaMostrar(letter.getKey() + (i + 1));
                            beneficiario.setLetra(letter.getKey());
                            beneficiario.setCantidad(letter.getValue());
                            mostrar.add(beneficiario);
                        }
                    }
                }
            }// fin for

            this.log.debug("*********consultaPaginasBeneficiariosFin:" + mostrar.size());
        }
        return mostrar;
    }

    /**
     * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.ControlBeneficiariosService#consultaBeneficiarioById(java.lang.Long)
     */
    public Beneficiario consultaBeneficiarioById(final Long idBeneficiario)
            throws BusinessException {
        this.log.info("Entrando a consultaBeneficiarioById()");
        if (idBeneficiario != null) {
            this.log.info("Saliendo de consultaBeneficiarioById()");
            return this.beneficiarioDao.findBeneficiarioById(idBeneficiario);
        }
        this.log.info("Saliendo de consultaBeneficiarioById() con null");
        return null;
    }

    /**
     * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.ControlBeneficiariosService#consultaBeneficiarioByIdEliminados(java.lang.Long)
     */
    public Beneficiario consultaBeneficiarioByIdEliminados(final Long idBeneficiario)
            throws BusinessException {
        this.log.info("Entrando a consultaBeneficiarioByIdEliminados()");
        if (idBeneficiario != null) {
            this.log.info("Saliendo de consultaBeneficiarioByIdEliminados()");
            return this.beneficiarioDao.findBeneficiarioByIdEliminados(idBeneficiario);
        }
        this.log.info("Saliendo de consultaBeneficiarioById() con null");
        return null;
    }

    /**
     * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.ControlBeneficiariosService#actualizaBeneficiario(com.indeval.portalinternacional.middleware.servicios.modelo.Beneficiario)
     */
    public void actualizaBeneficiario(final Beneficiario beneficiario) throws BusinessException {

        // Valida que el beneficiario no este en estato AUTORIZADO
        StatusBeneficiario statusBeneficiario = beneficiario.getStatusBenef();
        if (statusBeneficiario.getIdStatusBenef().longValue() == STATUS_BENEFICIARIO_AUTORIZADO) {
            throw new BusinessException(this.errorResolver.getMessage("J0052"));
        }
        Long idStatusAcutal = statusBeneficiario.getIdStatusBenef();
        // Cambia el estado del beneficiario a ACTUALIZADO
        if (statusBeneficiario.getIdStatusBenef().longValue() == STATUS_BENEFICIARIO_VENCIDO
                || statusBeneficiario.getIdStatusBenef().longValue() == STATUS_BENEFICIARIO_CANCELADO) {
            statusBeneficiario.setIdStatusBenef(new Long(STATUS_BENEFICIARIO_ACTUALIZADO));
            beneficiario.setStatusBenef(statusBeneficiario);
        }
        if (beneficiario.getPersonaFisica()) {
            beneficiario.setApellidoMaterno(this.limpiaEspaciosDobles(beneficiario
                    .getApellidoMaterno()));
            beneficiario.setApellidoPaterno(this.limpiaEspaciosDobles(beneficiario
                    .getApellidoPaterno()));
            beneficiario.setNombres(this.limpiaEspaciosDobles(beneficiario.getNombres()));
        } else {
            beneficiario.setRazonSocial(this.limpiaEspaciosDobles(beneficiario.getRazonSocial()));
        }

        this.validatorFormatW9Service.validaCapturaBeneficiario(beneficiario, null);

        TipoFormato tipoFormato = TipoFormato.obtenerInstancia(beneficiario.getTipoFormato());

        switch (tipoFormato) {
            case W8BEN2014: {
                if (this.obtieneFechaValida().compareTo(beneficiario.getFechaFormato()) > 0) {
                    throw new BusinessException(this.errorResolver.getMessage("J0075"));
                }

                this.validatorFormatW8BEN2014Service.validaFormatoW(beneficiario);
                String rfc = beneficiario.getFormatoW8BEN().getForeignTaxIdNumb();
                if (StringUtils.isNotBlank(rfc)) {
                    beneficiario.getFormatoW8BEN().setForeignTaxIdNumb(rfc.trim().toUpperCase());
                }

                if (StringUtils.isNotBlank(beneficiario.getFormatoW8BEN().getSigner())) {
                    beneficiario.getFormatoW8BEN().setSigner(
                            beneficiario.getFormatoW8BEN().getSigner().toUpperCase());
                }

                this.beneficiarioDao.update(beneficiario.getFormatoW8BEN());

                String paisLine9ap2 = beneficiario.getFormatoW8BEN().getField9OptionACountry();
                if (StringUtils.isNotBlank(paisLine9ap2)) {
                    beneficiario.getFormatoW8BEN().setField9OptionACountry(
                            paisLine9ap2.toUpperCase());
                }

                // validatorDivIntService.validaDomicilioW8(beneficiario.getDomicilioW8Normal(),
                // "Domicilio de Residencia ");
                this.validatorFormatW8BEN2014Service.validaDomicilioBeneficiario(
                        beneficiario.getDomicilioW8Normal(), "Domicilio de Residencia ");
                this.convierteDomicilioMayus(beneficiario.getDomicilioW8Normal());
                this.beneficiarioDao.update(beneficiario.getDomicilioW8Normal());

                if (!beneficiario.getFormatoW8BEN().isDisabledDireccionPostal()) {
                    // validatorDivIntService.validaDomicilioW8(beneficiario.getDomicilioW8Correo(),
                    // "Domicilio de Correo ");
                    this.validatorFormatW8BEN2014Service.validaDomicilioBeneficiario(
                            beneficiario.getDomicilioW8Correo(), "Domicilio de Correo ");
                    this.convierteDomicilioMayus(beneficiario.getDomicilioW8Correo());
                    if (beneficiario.getDomicilioW8Correo().getIdDomicilio() == null) {
                        this.beneficiarioDao.save(beneficiario.getDomicilioW8Correo());
                    } else {
                        this.beneficiarioDao.update(beneficiario.getDomicilioW8Correo());
                    }
                } else {
                    beneficiario.setDomicilioW8Correo(null);
                }

                break;
            }
            // W8BEN2017 actualiza beneficiario
            case W8BEN2017: {
                if (this.obtieneFechaValida().compareTo(beneficiario.getFechaFormato()) > 0) {
                    throw new BusinessException(this.errorResolver.getMessage("J0075"));
                }

                this.validatorFormatW8BEN2014Service.validaFormatoW(beneficiario);
                String rfc = beneficiario.getFormatoW8BEN().getForeignTaxIdNumb();
                if (StringUtils.isNotBlank(rfc)) {
                    beneficiario.getFormatoW8BEN().setForeignTaxIdNumb(rfc.trim().toUpperCase());
                }

                if (StringUtils.isNotBlank(beneficiario.getFormatoW8BEN().getSigner())) {
                    beneficiario.getFormatoW8BEN().setSigner(
                            beneficiario.getFormatoW8BEN().getSigner().toUpperCase());
                }

                this.beneficiarioDao.update(beneficiario.getFormatoW8BEN());

                String paisLine9ap2 = beneficiario.getFormatoW8BEN().getField9OptionACountry();
                if (StringUtils.isNotBlank(paisLine9ap2)) {
                    beneficiario.getFormatoW8BEN().setField9OptionACountry(
                            paisLine9ap2.toUpperCase());
                }

                // validatorDivIntService.validaDomicilioW8(beneficiario.getDomicilioW8Normal(),
                // "Domicilio de Residencia ");
                this.validatorFormatW8BEN2014Service.validaDomicilioBeneficiario(
                        beneficiario.getDomicilioW8Normal(), "Domicilio de Residencia ");
                this.convierteDomicilioMayus(beneficiario.getDomicilioW8Normal());
                this.beneficiarioDao.update(beneficiario.getDomicilioW8Normal());

                if (!beneficiario.getFormatoW8BEN().isDisabledDireccionPostal()) {
                    // validatorDivIntService.validaDomicilioW8(beneficiario.getDomicilioW8Correo(),
                    // "Domicilio de Correo ");
                    this.validatorFormatW8BEN2014Service.validaDomicilioBeneficiario(
                            beneficiario.getDomicilioW8Correo(), "Domicilio de Correo ");
                    this.convierteDomicilioMayus(beneficiario.getDomicilioW8Correo());
                    if (beneficiario.getDomicilioW8Correo().getIdDomicilio() == null) {
                        this.beneficiarioDao.save(beneficiario.getDomicilioW8Correo());
                    } else {
                        this.beneficiarioDao.update(beneficiario.getDomicilioW8Correo());
                    }
                } else {
                    beneficiario.setDomicilioW8Correo(null);
                }

                break;
            }
            case W8BEN: {
                if (this.obtieneFechaValida().compareTo(beneficiario.getFechaFormato()) > 0) {
                    throw new BusinessException(this.errorResolver.getMessage("J0075"));
                }

                // validatorDivIntService.validaFormatoW8BEN(beneficiario.getFormatoW8BEN(),beneficiario.getIdCuentaNombrada());
                this.validatorFormatW8BENService.validaFormatoW(beneficiario);
                String rfc = beneficiario.getFormatoW8BEN().getForeignTaxIdNumb();
                if (StringUtils.isNotBlank(rfc)) {
                    beneficiario.getFormatoW8BEN().setForeignTaxIdNumb(rfc.trim().toUpperCase());
                }
                this.beneficiarioDao.update(beneficiario.getFormatoW8BEN());

                String paisLine9ap2 = beneficiario.getFormatoW8BEN().getField9OptionACountry();
                if (StringUtils.isNotBlank(paisLine9ap2)) {
                    beneficiario.getFormatoW8BEN().setField9OptionACountry(
                            paisLine9ap2.toUpperCase());
                }

                // validatorDivIntService.validaDomicilioW8(beneficiario.getDomicilioW8Normal(),
                // "Domicilio de Residencia ");
                this.validatorFormatW8BENService.validaDomicilioBeneficiario(
                        beneficiario.getDomicilioW8Normal(), "Domicilio de Residencia ");
                this.convierteDomicilioMayus(beneficiario.getDomicilioW8Normal());
                this.beneficiarioDao.update(beneficiario.getDomicilioW8Normal());

                if (!beneficiario.getFormatoW8BEN().isDisabledDireccionPostal()) {
                    // validatorDivIntService.validaDomicilioW8(beneficiario.getDomicilioW8Correo(),
                    // "Domicilio de Correo ");
                    this.validatorFormatW8BENService.validaDomicilioBeneficiario(
                            beneficiario.getDomicilioW8Correo(), "Domicilio de Correo ");
                    this.convierteDomicilioMayus(beneficiario.getDomicilioW8Correo());
                    if (beneficiario.getDomicilioW8Correo().getIdDomicilio() == null) {
                        this.beneficiarioDao.save(beneficiario.getDomicilioW8Correo());
                    } else {
                        this.beneficiarioDao.update(beneficiario.getDomicilioW8Correo());
                    }
                } else {
                    beneficiario.setDomicilioW8Correo(null);
                }

                break;
            }

            case W8IMY: {
                // validatorDivIntService.validaFormatoW8IMY(beneficiario.getFormatoW8IMY(),
                // beneficiario);
                this.validatorFormatW8IMYService.validaFormatoW(beneficiario);
                String rfc = beneficiario.getFormatoW8IMY().getForeignTaxIdNumb();
                if (StringUtils.isNotBlank(rfc)) {
                    beneficiario.getFormatoW8IMY().setForeignTaxIdNumb(rfc.trim().toUpperCase());
                }
                this.beneficiarioDao.update(beneficiario.getFormatoW8IMY());

                // validatorDivIntService.validaDomicilioW8(beneficiario.getDomicilioW8Normal(),
                // "Domicilio de Residencia ");
                this.validatorFormatW8IMYService.validaDomicilioBeneficiario(
                        beneficiario.getDomicilioW8Normal(), "Domicilio de Residencia ");
                this.convierteDomicilioMayus(beneficiario.getDomicilioW8Normal());
                this.beneficiarioDao.update(beneficiario.getDomicilioW8Normal());

                if (!beneficiario.getFormatoW8IMY().isDisabledDireccionPostal()) {
                    // validatorDivIntService.validaDomicilioW8(beneficiario.getDomicilioW8Correo(),
                    // "Domicilio de Correo ");
                    this.validatorFormatW8IMYService.validaDomicilioBeneficiario(
                            beneficiario.getDomicilioW8Correo(), "Domicilio de Correo ");
                    this.convierteDomicilioMayus(beneficiario.getDomicilioW8Correo());
                    if (beneficiario.getDomicilioW8Correo().getIdDomicilio() == null) {
                        this.beneficiarioDao.save(beneficiario.getDomicilioW8Correo());
                    } else {
                        this.beneficiarioDao.update(beneficiario.getDomicilioW8Correo());
                    }
                } else {
                    beneficiario.setDomicilioW8Correo(null);
                }

                break;
            }

            case W9: {
                beneficiario.getFormatoW9().setTaxClassification(
                        StringUtils.upperCase(beneficiario.getFormatoW9().getTaxClassification()));
                this.validatorFormatW9Service.validaFormatoW(beneficiario);

                if (!beneficiario.getFormatoW9().getTypeTaxPayer().getIdCampo().equals(4l)) {
                    beneficiario.getFormatoW9().setTaxClassification(null);
                }
                // modificacion para limpiar el campo otherDescription cuando se selecciona otra
                // opcion

                if (!beneficiario.getFormatoW9().getTypeTaxPayer().getIdCampo().equals(5l)) {
                    beneficiario.getFormatoW9().setOtherDescription(null);
                }
                String bussinessName = beneficiario.getFormatoW9().getBusinessName();
                if (StringUtils.isNotBlank(bussinessName)) {
                    beneficiario.getFormatoW9().setBusinessName(bussinessName.trim().toUpperCase());
                }

                this.beneficiarioDao.update(beneficiario.getFormatoW9());

                this.validatorFormatW9Service.validaDomicilioBeneficiario(
                        beneficiario.getDomicilioW9(), " Domicilio W9 ");
                this.convierteDomicilioMayus(beneficiario.getDomicilioW9());
                this.beneficiarioDao.update(beneficiario.getDomicilioW9());

                break;
            }
            case MILA: {
                beneficiario.getFormatoMILA().setNumeroDocumento(
                        StringUtils.upperCase(beneficiario.getFormatoMILA().getNumeroDocumento()));

                if (beneficiario.getPersonaFisica()) {
                    beneficiario.getFormatoMILA().setCaracterEntidad(null);
                    beneficiario.getFormatoMILA().setSectorEconomico(null);
                    if (beneficiario.getFormatoMILA().getPaisNacionalidad().getIdPais().intValue() != PaisInt.ID_PAIS_MEXICO) {
                        beneficiario.getFormatoMILA().setRfc(null);
                    } else if (!StringUtils.isBlank(beneficiario.getFormatoMILA().getRfc())) {
                        beneficiario.getFormatoMILA().setRfc(
                                StringUtils.upperCase(beneficiario.getFormatoMILA().getRfc()));
                    }
                } else {
                    beneficiario.getFormatoMILA().setRfc(null);
                }
                if (StringUtils.isEmpty(beneficiario.getFormatoMILA().getIdentificadorMILA())) {
                    beneficiario.getFormatoMILA().setIdentificadorMILA(
                            this.obtieneCodigoMilaBeneficiario(beneficiario.getIdCuentaNombrada()));
                }

                if (beneficiario.getFormatoMILA().getArchivos().getIdArchivosFormatoMILA() == null) {
                    beneficiario
                            .getFormatoMILA()
                            .getArchivos()
                            .setIdArchivosFormatoMILA(
                                    beneficiario.getFormatoMILA().getIdCamposFormatoMILA());
                }

                if (!StringUtils.isBlank(beneficiario.getFormatoMILA().getNombreDocumento())) {
                    beneficiario.getFormatoMILA().setNombreDocumento(
                            StringUtils.upperCase(beneficiario.getFormatoMILA()
                                    .getNombreDocumento()));
                }

                this.validatorFormatMILAService.validaFormatoW(beneficiario);

                this.beneficiarioDao.update(beneficiario.getFormatoMILA());

                this.validatorFormatMILAService.validaDomicilioBeneficiario(
                        beneficiario.getDomicilioMILA(), " Domicilio MILA ");
                this.convierteDomicilioMayus(beneficiario.getDomicilioMILA());
                this.beneficiarioDao.update(beneficiario.getDomicilioMILA());

                break;
            }
            case W8BENE:
                // Valida el formato
                this.validatorFormatW8BENEService.validaFormatoW(beneficiario);
                // Actualiza el formato
                this.beneficiarioDao.update(beneficiario.getFormatoW8BENE());
                // Actualiza los domicilios
                this.beneficiarioDao.update(beneficiario.getDomicilioW8Normal());
                if (beneficiario.getDomicilioW8Correo() != null) {
                    if (beneficiario.getDomicilioW8Correo().getIdDomicilio() == null) {
                        this.beneficiarioDao.save(beneficiario.getDomicilioW8Correo());
                    } else {
                        this.beneficiarioDao.update(beneficiario.getDomicilioW8Correo());
                    }
                }
                break;
            case W8BENE2016:
                // Valida el formato 2016
                this.validatorFormatW8BENE2016Service.validaFormatoW(beneficiario);
                // Actualiza el formato
                this.beneficiarioDao.update(beneficiario.getFormatoW8BENE());
                // Actualiza los domicilios
                this.beneficiarioDao.update(beneficiario.getDomicilioW8Normal());
                if (beneficiario.getDomicilioW8Correo() != null) {
                    if (beneficiario.getDomicilioW8Correo().getIdDomicilio() == null) {
                        this.beneficiarioDao.save(beneficiario.getDomicilioW8Correo());
                    } else {
                        this.beneficiarioDao.update(beneficiario.getDomicilioW8Correo());
                    }
                }
                break;
            case W8IMY2015:
                // Valida el formato
                this.validatorFormatW8IMY2015Service.validaFormatoW(beneficiario);
                // Actualiza el formato
                this.beneficiarioDao.update(beneficiario.getFormatoW8IMY2015());
                // Actualiza los domicilios
                this.beneficiarioDao.update(beneficiario.getDomicilioW8Normal());
                if (beneficiario.getDomicilioW8Correo() != null) {
                    if (beneficiario.getDomicilioW8Correo().getIdDomicilio() == null) {
                        this.beneficiarioDao.save(beneficiario.getDomicilioW8Correo());
                    } else {
                        this.beneficiarioDao.update(beneficiario.getDomicilioW8Correo());
                    }
                }
                break;

            case W8IMY2017:
                // Valida el formato
                this.validatorFormatW8IMY2017Service.validaFormatoW(beneficiario);
                // Actualiza el formato
                this.beneficiarioDao.update(beneficiario.getFormatoW8IMY2015());
                // Actualiza los domicilios
                this.beneficiarioDao.update(beneficiario.getDomicilioW8Normal());
                if (beneficiario.getDomicilioW8Correo() != null) {
                    if (beneficiario.getDomicilioW8Correo().getIdDomicilio() == null) {
                        this.beneficiarioDao.save(beneficiario.getDomicilioW8Correo());
                    } else {
                        this.beneficiarioDao.update(beneficiario.getDomicilioW8Correo());
                    }
                }
                break;
        }

        // Conversion a Mayusculas
        if (beneficiario.getPersonaFisica()) {
            beneficiario.setNombres(beneficiario.getNombres().toUpperCase());
            beneficiario.setApellidoPaterno(beneficiario.getApellidoPaterno().toUpperCase());
            if (beneficiario.getApellidoMaterno() != null) {
                beneficiario.setApellidoMaterno(beneficiario.getApellidoMaterno().toUpperCase());
            }
        } else {
            beneficiario.setRazonSocial(beneficiario.getRazonSocial().trim().toUpperCase());
        }
        String paisIncorporacion = beneficiario.getPaisIncorporacion();
        if (StringUtils.isNotBlank(paisIncorporacion)) {
            beneficiario.setPaisIncorporacion(paisIncorporacion.trim().toUpperCase());
        }

        if (BeneficiariosConstantes.FORMATO_W9.equals(beneficiario.getTipoFormato())) {
            beneficiario.setPaisIncorporacion("USA");
        }

        beneficiario.setFechaCambio(this.dateUtilService.getCurrentDate());

        this.beneficiarioDao.update(beneficiario);
        this.creaHistoricoBeneficiario(beneficiario.getIdBeneficiario(), idStatusAcutal,
                STATUS_BENEFICIARIO_ACTUALIZADO);

        if (BeneficiariosConstantes.FORMATO_W8_BEN.equals(beneficiario.getTipoFormato()) 
        		|| BeneficiariosConstantes.FORMATO_W8_BEN2014.equals(beneficiario.getTipoFormato()) 
        		|| BeneficiariosConstantes.FORMATO_W8_BEN2017.equals(beneficiario.getTipoFormato())
        		|| BeneficiariosConstantes.FORMATO_W9.equals(beneficiario.getTipoFormato())
				|| BeneficiariosConstantes.FORMATO_W8_BEN_E.equals(beneficiario.getTipoFormato()) 
        		|| BeneficiariosConstantes.FORMATO_W8_BEN_E_2016.equals(beneficiario.getTipoFormato())
        		|| BeneficiariosConstantes.FORMATO_W8_IMY.equals(beneficiario.getTipoFormato())
        		|| BeneficiariosConstantes.FORMATO_W8_IMY2015.equals(beneficiario.getTipoFormato()) 
        		|| BeneficiariosConstantes.FORMATO_W8_IMY2017.equals(beneficiario.getTipoFormato())
        		|| BeneficiariosConstantes.FORMATO_W8_IMY2016.equals(beneficiario.getTipoFormato())){
        	
            List<FormatoFiscal> listFormatoFiscal = serviceFormatosFiscales.prepareFormatoFiscalTin(beneficiario);
            if(listFormatoFiscal != null){
            	for (FormatoFiscal formatoFiscal : listFormatoFiscal) {
            		Boolean isNuevoTin = serviceFormatosFiscales.validateExistsFormatoFiscalTin(formatoFiscal);
            		if(!isNuevoTin){
            			serviceFormatosFiscales.actualizaFormatoFiscal(formatoFiscal);
            		}
    			}
            }

        }
    }

    /**
     * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.ControlBeneficiariosService#obtieneCatBic()
     */
    public List<Object[]> obtieneCatBic() throws BusinessException {
        this.log.info("Entrando a obtieneCatBic()");
        List<Object[]> listCatBic = this.catBicDao.findCatBicByName();
        if (listCatBic == null) {
            listCatBic = new ArrayList<Object[]>();
        }
        return listCatBic;
    }

    public List<Object[]> obtieneAllCatBic() throws BusinessException {
        this.log.info("Entrando a obtieneCatBic()");
        List<Object[]> listCatBic = this.catBicDao.findAllCatBicByName();
        if (listCatBic == null) {
            listCatBic = new ArrayList<Object[]>();
        }
        return listCatBic;
    }

    /**
     * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.ControlBeneficiariosService#obtieneStatusBeneficiario()
     */
    public StatusBeneficiario[] obtieneStatusBeneficiario() throws BusinessException {

        this.log.info("Entrando a obtieneStatusBeneficiario()");
        List<StatusBeneficiario> listStatusBeneficiario =
                this.statusBeneficiarioDao.findStatusBeneficiario();
        if (listStatusBeneficiario == null) {
            listStatusBeneficiario = new ArrayList<StatusBeneficiario>();
        }
        return listStatusBeneficiario
                .toArray(new StatusBeneficiario[listStatusBeneficiario.size()]);
    }

    /**
     * Inserta un registro en HistoricoBeneficiario
     * 
     * @param beneficiario
     * @throws BusinessException
     */
    private void creaHistoricoBeneficiario(final Long idBeneficario, final Long idStatusAnterior,
            final Long idStatusNuevo) throws BusinessException {
        this.log.info("Entrando a creaHistoricoBeneficiario()");
        HistoricoBeneficiario historicoBenefVencido = new HistoricoBeneficiario();
        historicoBenefVencido.setIdBeneficiario(idBeneficario);
        historicoBenefVencido.setFechaAlta(this.dateUtilService.getCurrentDate());

        StatusBeneficiario statusAnterior = new StatusBeneficiario();
        statusAnterior.setIdStatusBenef(idStatusAnterior);
        historicoBenefVencido.setStatusAnterior(statusAnterior);

        StatusBeneficiario statusNuevo = new StatusBeneficiario();
        statusNuevo.setIdStatusBenef(idStatusNuevo);
        historicoBenefVencido.setStatusNuevo(statusNuevo);

        this.historicoBeneficiarioDao.save(historicoBenefVencido);
        this.log.info("this.historicoBeneficiarioDao.save(historicoBenefVencido) Save");
    }

    /**
     * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.ControlBeneficiariosService#consultaBeneficiariosHistorico(com.indeval.portalinternacional.middleware.servicios.vo.ConsultaHistoricoBeneficiariosParam,
     *      com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO)
     */
    @SuppressWarnings("rawtypes")
    public PaginaVO consultaBeneficiariosHistorico(final ConsultaHistoricoBeneficiariosParam param,
            final PaginaVO paginaVO) throws BusinessException {
        if (paginaVO == null) {
            throw new BusinessException(this.errorResolver.getMessage("J0053", (Object) "PaginaVO"));
        }
        if (param == null) {
            throw new BusinessException(this.errorResolver.getMessage("J0053",
                    (Object) "ConsultaHistoricoBeneficiariosParam"));
        }
        if (StringUtils.isNotBlank(param.getNombreRazonSocial())) {
            List<Long> listaBeneficiarios = this.buscaIdBeneficiariosPorNombreHistorico(param);
            if (listaBeneficiarios != null && listaBeneficiarios.size() > 0) {
                param.setListaBeneficiarios(listaBeneficiarios);
            } else {
                paginaVO.setTotalRegistros(0);
                paginaVO.setRegistros(new ArrayList());
                return paginaVO;
            }
        }
        return this.historicoBeneficiarioDao.findHistoricoBeneficiario(param, paginaVO);
    }

    /**
     * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.ControlBeneficiariosService#obtieneTipoBeneficiario(java.lang.Long)
     */
    @SuppressWarnings("rawtypes")
    public List obtieneTiposBeneficiario(final Long idCuentaNombrada) {

        List<Object[]> listCustodioTipoBenef =
                this.custodioTipoBenefDao.findByIdCatBic(idCuentaNombrada);

        if (listCustodioTipoBenef == null || listCustodioTipoBenef.size() == 0) {
            return new ArrayList<Object[]>();
        }

        return listCustodioTipoBenef;
    }


    /**
     * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.ControlBeneficiariosService#obtieneTiposBeneficiario(java.lang.Long)
     */
    @SuppressWarnings("rawtypes")
    public List obtieneTiposBeneficiario() {

        List<Object[]> listCustodioTipoBenef = this.custodioTipoBenefDao.findByIdCatBic();

        if (listCustodioTipoBenef == null || listCustodioTipoBenef.size() == 0) {
            return new ArrayList<Object[]>();
        }

        return listCustodioTipoBenef;
    }

    /**
     * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.ControlBeneficiariosService#obtieneTiposBeneficiario(java.lang.String)
     */
    public TipoBeneficiario[] obtieneTiposBeneficiario(final String catBic) {
        TipoBeneficiario[] tiposBeneficiario;
        List<CustodioTipoBenef> listCustodioTipoBenef =
                this.custodioTipoBenefDao.findByNameCatBic(catBic);
        if (listCustodioTipoBenef == null) {
            listCustodioTipoBenef = new ArrayList<CustodioTipoBenef>();
        }
        tiposBeneficiario = new TipoBeneficiario[listCustodioTipoBenef.size()];
        for (int i = 0; i < listCustodioTipoBenef.size(); i++) {
            tiposBeneficiario[i] = listCustodioTipoBenef.get(i).getTipoBeneficiario();
        }
        return tiposBeneficiario;
    }

    /**
     * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.ControlBeneficiariosService#obtieneFormato(java.lang.Long,
     *      java.lang.Long)
     */
    public String obtieneFormato(final Long idCuentaNombrada, final Long idTipoBeneficiario) {
        if (idCuentaNombrada != null && idCuentaNombrada > 0 && idTipoBeneficiario != null
                && idTipoBeneficiario > 0) {
            return this.custodioTipoBenefDao.findFormato(idCuentaNombrada, idTipoBeneficiario);
        }
        return null;
    }

    public void asignaAdpBeneficiario(final Long idBeneficiario, final String adp) {
        this.log.info("Entrando a asignaAdpBeneficiario()");
        if (idBeneficiario == null) {
            throw new BusinessException(this.errorResolver.getMessage("J0053",
                    (Object) "Id del beneficiario"));
        }
        if (StringUtils.isBlank(adp)) {
            throw new BusinessException(this.errorResolver.getMessage("J0053", (Object) "ADP"));
        }
        /* se modifica la longitud permitida, 10 valor anterior */
        if (adp.length() > 20) {
            throw new BusinessException(this.errorResolver.getMessage("J0077"));
        }
        Beneficiario beneficiario = this.beneficiarioDao.findBeneficiarioById(idBeneficiario);
        if (beneficiario != null) {
        	ClaveAdp claveAdp = claveAdpDao.findClaveAdpByClaveAdp(adp.toUpperCase());
        	if(claveAdp!=null && claveAdp.getIdClaveAdp()!=null && beneficiario.getIdCuentaNombrada()!=null) {
        		Integer porcentaje = adpCustodioPorcentajeDao.getPorcentaje(claveAdp.getIdClaveAdp(), beneficiario.getIdCuentaNombrada());
        		if(porcentaje!=null) {
        			beneficiario.setPorcentajeRetencion((double)porcentaje);
        		}
        	}
            beneficiario.setAdp(adp.toUpperCase());
            this.beneficiarioDao.update(beneficiario);
        } else {
            throw new BusinessException(this.errorResolver.getMessage("J0073"));
        }
    }

    public Beneficiario consultaBeneficiarioUoiInst(final String uoi,
            final Long idEstausBeneficiario, final Long idTipoInstitucion,
            final String folioInstitucion) throws BusinessException {

        return this.beneficiarioDao.getBeneficiarioByUoiInstitucion(uoi, idEstausBeneficiario,
                idTipoInstitucion, folioInstitucion);
    }

    public String obtieneCodigoMilaBeneficiario(final long idCuentaNombrada)
            throws BusinessException {

        StringBuilder codigoMILA = new StringBuilder();

        codigoMILA.append(idCuentaNombrada);

        Long totalActuales =
                this.beneficiarioDao.findMaxBeneficiarioMilaByCustodio(idCuentaNombrada);
        totalActuales++;

        // El codigo de mila es de 10 posiciones
        int posicionesRestantes = 10 - codigoMILA.length();

        codigoMILA.append(String.format("%0" + posicionesRestantes + "d", totalActuales));

        return codigoMILA.toString();
    }

    /**
     * @see ControlBeneficiariosService#getListaCustodioTipoBenef()
     */
    public List<CustodioTipoBenef> getListaCustodioTipoBenef() {
        return this.custodioTipoBenefDao.getCustodiosTipoBeneficiario();
    }

    public List<Field3W8BEN> getField3W8BEN() {
        return this.camposFormatosDao.getField3W8BEN();
    }

    public List<Field3W8IMY> getField3W8IMY() {
        return this.camposFormatosDao.getField3W8IMY();
    }

    public List<Field3W9> getField3W9() {
        return this.camposFormatosDao.getField3W9();
    }

    public void setBeneficiarioDao(final BeneficiarioDao beneficiarioDao) {
        this.beneficiarioDao = beneficiarioDao;
    }


    public void setErrorResolver(final MessageResolver errorResolver) {
        this.errorResolver = errorResolver;
    }


    public void setValidatorDivIntService(final ValidatorDivIntService validatorDivIntService) {
        this.validatorDivIntService = validatorDivIntService;
    }

    public void setValidatorService(final ValidatorService validatorService) {
        this.validatorService = validatorService;
    }


    public void setCatBicDao(final CatBicDao catBicDao) {
        this.catBicDao = catBicDao;
    }

    public void setCustodioTipoBenefDao(final CustodioTipoBenefDao custodioTipoBenefDao) {
        this.custodioTipoBenefDao = custodioTipoBenefDao;
    }

    public void setDateUtilService(final DateUtilService dateUtilService) {
        this.dateUtilService = dateUtilService;
    }

    public void setStatusBeneficiarioDao(final StatusBeneficiarioDao statusBeneficiarioDao) {
        this.statusBeneficiarioDao = statusBeneficiarioDao;
    }

    /**
     * @param camposFormatosDao the camposFormatosDao to set
     */
    public void setCamposFormatosDao(final CamposFormatosDao camposFormatosDao) {
        this.camposFormatosDao = camposFormatosDao;
    }

    /**
     * @param countryISO the countryISO to set
     */
    public void setCountryISO(final Map<String, String> countryISO) {
        this.countryISO = countryISO;
    }

    /**
     * @return the countryISO
     */
    public Map<String, String> getCountryISO() {
        return this.countryISO;
    }
    
    public void setClaveAdpDao(ClaveAdpDao claveAdpDao) {
		this.claveAdpDao = claveAdpDao;
	}

	public void setAdpCustodioPorcentajeDao(AdpCustodioPorcentajeDao adpCustodioPorcentajeDao) {
		this.adpCustodioPorcentajeDao = adpCustodioPorcentajeDao;
	}

    /**
     * @param historicoBeneficiarioDao the historicoBeneficiarioDao to set
     */
    public void setHistoricoBeneficiarioDao(final HistoricoBeneficiarioDao historicoBeneficiarioDao) {
        this.historicoBeneficiarioDao = historicoBeneficiarioDao;
    }

    public Date obtieneFechaValida() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(this.dateUtilService.getCurrentDate());
        calendar.add(Calendar.YEAR, -3);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.MONTH, Calendar.JANUARY);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    private List<Long> buscaIdBeneficiariosPorNombreHistorico(
            final ConsultaHistoricoBeneficiariosParam param) {
        List<Long> retorno = new ArrayList<Long>();
        ConsultaBeneficiariosParam params = null;
        if (StringUtils.isNotBlank(param.getNombreRazonSocial())) {
            List<NombreBeneficiario> listaNombres =
                    this.beneficiarioDao.getNombresBeneficiarios(params);
            for (NombreBeneficiario nombreBeneficiario : listaNombres) {
                if (nombreBeneficiario.comparaNombre(param.getNombreRazonSocial())) {
                    retorno.add(nombreBeneficiario.getIdBeneficiario());
                }
                if (retorno.size() > 250) {
                    break;
                }
            }
        }
        this.log.debug("Tamaño de la lista de BEneficiarios con msimo nombre: [" + retorno.size()
                + "]");
        return retorno;
    }

    private String limpiaEspaciosDobles(final String cadenaOriginal) {
        String retorno = StringUtils.trimToNull(cadenaOriginal);

        if (StringUtils.isNotBlank(retorno)) {
            while (retorno.contains("  ")) {
                retorno = retorno.replaceAll("  ", " ");
            }
        }

        return retorno;
    }

    /**
     * Arma un listado de cadenas basandose en la lista de formatos fiscales inyectados en el servicio desde la base 
     * de datos.
     * @return El listado de formatos fiscales armado.
     */
    private List<String> armarListadoDeFormatos() throws BusinessException {
        log.info("\n\n####### Entrando a armarListadoDeFormatos()...");
        List<String> formatos = null;
        if (this.listaFormatosDepurar != null && StringUtils.isNotEmpty(this.listaFormatosDepurar)) {
            log.debug("\n\n####### listaFormatosDepurar=[" + this.listaFormatosDepurar + "]");
            String[] listaSeparada = this.listaFormatosDepurar.split(",");
            formatos = Arrays.asList(listaSeparada);
        }
        else {
            throw new BusinessException("No se proporcionaron Formatos Fiscales desde base de datos!");
        }

        return formatos;
    }

    /*
     * (non-Javadoc)
     * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.ControlBeneficiariosService#depurarFormatosFiscalesBeneficiarios()
     */
    public void depurarFormatosFiscalesBeneficiarios() throws BusinessException {
        log.info("\n\n####### Entrando a depurarFormatosFiscalesBeneficiarios()...");
        List<Beneficiario> lstBeneficiarios = null;
        HistoricoBeneficiario historicoBenef = null;
        Integer registrosDepurados = null;
        StatusBeneficiario estadoAnterior = null;
        StatusBeneficiario estadoActual = null;

        // Listado de Formatos Fiscales a depurar
        List<String> tiposFormato = this.armarListadoDeFormatos();

        // Fecha actual
        Date fechaActual = this.dateUtilService.getCurrentDate();
        Calendar fechaLimiteDepuracion = Calendar.getInstance();
        fechaLimiteDepuracion.setTime(fechaActual);
        fechaLimiteDepuracion.add(Calendar.YEAR, Constantes.VIGENCIA_ANIOS_FORMATO_W8BEN * -1);
        log.info("\n\n####### Inicializando depuracion de beneficiarios ...");

        log.debug("\n\n####### Consultando beneficiarios para guardar en historicos...");
        lstBeneficiarios = this.beneficiarioDao.getBeneficiariosDepuracion(
                               Integer.valueOf(fechaLimiteDepuracion.get(Calendar.YEAR)), tiposFormato);
        log.debug("\n\n####### Se almacenaran en historicos : " + (lstBeneficiarios == null ? "0" : lstBeneficiarios.size()) + 
                  " beneficiarios....");
        if (lstBeneficiarios != null && !lstBeneficiarios.isEmpty()) {
            estadoAnterior = new StatusBeneficiario();
            estadoAnterior.setIdStatusBenef(Constantes.STATUS_BENEFICIARIO_AUTORIZADO);
            estadoActual = new StatusBeneficiario();
            estadoActual.setIdStatusBenef(Constantes.STATUS_BENEFICIARIO_VENCIDO);
            for (Beneficiario benef : lstBeneficiarios) {
                historicoBenef = new HistoricoBeneficiario();
                historicoBenef.setBeneficiario(benef);
                historicoBenef.setIdBeneficiario(benef.getIdBeneficiario());
                historicoBenef.setStatusAnterior(estadoAnterior);
                historicoBenef.setStatusNuevo(estadoActual);
                historicoBenef.setFechaAlta(fechaActual);
                this.historicoBeneficiarioDao.save(historicoBenef);
            }
            log.debug("\n\n####### Beneficiarios guardados en historicos!");
            registrosDepurados = this.beneficiarioDao.depurarFormatosFiscalesBeneficiarios(
                                     Integer.valueOf(fechaLimiteDepuracion.get(Calendar.YEAR)), tiposFormato);
            log.info("\n\n####### Depuracion terminada, se depuraron " + registrosDepurados + " registros.");
        }
    }

    /**
     * @see ControlBeneficiariosService#modificaPorcentajeRetencion(Long,Double)
     */
    public void modificaPorcentajeRetencion(final Long idBeneficiario,
            final Double nuevoPorcentajeRetencion) throws BusinessException {
        this.log.info("Entrando a modificaPorcentajeRetencion()");
        if (idBeneficiario == null) {
            throw new BusinessException(this.errorResolver.getMessage("J0053",
                    (Object) "Id del beneficiario"));
        }
        if (nuevoPorcentajeRetencion == null) {
            throw new BusinessException(this.errorResolver.getMessage("J0053",
                    (Object) "Porcentaje Beneficiario"));
        }
        if (nuevoPorcentajeRetencion > VALOR_MAXIMO_PORCENTAJE_RETENCION) {
            throw new BusinessException(this.errorResolver.getMessage("J0100"));
        }
        Beneficiario beneficiario = this.beneficiarioDao.findBeneficiarioById(idBeneficiario);
        if (beneficiario != null) {
            if (beneficiario.getStatusBenef().getIdStatusBenef() == STATUS_BENEFICIARIO_AUTORIZADO) {
                beneficiario.setPorcentajeRetencion(nuevoPorcentajeRetencion);
                this.beneficiarioDao.update(beneficiario);
            } else {
                throw new BusinessException(this.errorResolver.getMessage("J0101"));
            }
        } else {
            throw new BusinessException(this.errorResolver.getMessage("J0073"));
        }

    }

    /**
     * @see ControlBeneficiariosService#asignaValorAdpNuloBeneficiario(String)
     */
    public void asignaValorAdpNuloBeneficiario(final String uoi) {
        this.log.info("Entrando a asignaValorAdpNuloBeneficiario()");
        Beneficiario beneAux = this.beneficiarioDao.consultaBeneficiarioByUoiForAdp(uoi);
        if (beneAux != null && beneAux.getAdp() != null) {
            beneAux.setAdp(null);
            this.beneficiarioDao.update(beneAux);
        }

    }

    /**
     * @see ControlBeneficiariosService#eliminaInstitucionBeneficiarioMulticarga(Long, Long)
     */
    public void eliminaInstitucionBeneficiarioMulticarga(final Long idBeneficiario,
            final Long idInstitucion) {
        this.log.info("Entrando a eliminaInstitucionBeneficiarioMulticarga()");
        BeneficiarioInstitucion beneInst =
                this.beneficiarioDao.getBeneficiarioInstitucion(idBeneficiario, idInstitucion);
        if (beneInst != null) {
            this.beneficiarioDao.delete(beneInst);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.
     * ControlBeneficiariosService#consultaCatExemptPayeeW9()
     */
    public List<ExemptPayeeW9> consultaCatExemptPayeeW9() {
        return this.beneficiarioDao.consultaCatExemptPayeeW9();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.
     * ControlBeneficiariosService#consultaCatExemptionFatcaW9()
     */
    public List<ExemptionFatcaW9> consultaCatExemptionFatcaW9() {
        return this.beneficiarioDao.consultaCatExemptionFatcaW9();
    }

    /**
     * @param validatorFormatW9Service the validatorFormatW9Service to set
     */
    public void setValidatorFormatW9Service(
            final ValidatorBeneficiarioService validatorFormatW9Service) {
        this.validatorFormatW9Service = validatorFormatW9Service;
    }

    /**
     * @param validatorFormatW8BENService the validatorFormatW8BENService to set
     */
    public void setValidatorFormatW8BENService(
            final ValidatorBeneficiarioService validatorFormatW8BENService) {
        this.validatorFormatW8BENService = validatorFormatW8BENService;
    }

    /**
     * @param validatorFormatW8IMYService the validatorFormatW8IMYService to set
     */
    public void setValidatorFormatW8IMYService(
            final ValidatorBeneficiarioService validatorFormatW8IMYService) {
        this.validatorFormatW8IMYService = validatorFormatW8IMYService;
    }

    /**
     * @param validatorFormatW8BEN2014Service the validatorFormatW8BEN2014Service to set
     */
    public void setValidatorFormatW8BEN2014Service(
            final ValidatorBeneficiarioService validatorFormatW8BEN2014Service) {
        this.validatorFormatW8BEN2014Service = validatorFormatW8BEN2014Service;
    }

    /**
     * @param validatorFormatMILAService the validatorFormatMILAService to set
     */
    public void setValidatorFormatMILAService(
            final ValidatorBeneficiarioService validatorFormatMILAService) {
        this.validatorFormatMILAService = validatorFormatMILAService;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.
     * ControlBeneficiariosService#consultaCatMilaSectorEconomico()
     */
    public List<MILASectorEconomico> consultaCatMilaSectorEconomico() {
        return this.beneficiarioDao.consultaCatMilaSectorEconomico();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.
     * ControlBeneficiariosService#consultaCatMilaTipoDocumento()
     */
    public List<MILATipoDocumento> consultaCatMilaTipoDocumento() {
        return this.beneficiarioDao.consultaCatMilaTipoDocumento();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.
     * ControlBeneficiariosService#consultaCatMilaTipoEmpresa()
     */
    public List<MILATipoEmpresa> consultaCatMilaTipoEmpresa() {
        return this.beneficiarioDao.consultaCatMilaTipoEmpresa();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.
     * ControlBeneficiariosService#consultaCatMilaEstados()
     */
    public List<MILACodigoDepartamento> consultaCatMilaEstados() {
        return this.beneficiarioDao.consultaCatMilaEstados();
    }

    /**
     * @param conciliacionEfectivoIntService the conciliacionEfectivoIntService to set
     */
    public void setConciliacionEfectivoIntService(
            final ConciliacionEfectivoIntService conciliacionEfectivoIntService) {
        this.conciliacionEfectivoIntService = conciliacionEfectivoIntService;
        if (this.conciliacionEfectivoIntService != null) {
            List<PaisInt> paisesLista = this.conciliacionEfectivoIntService.consultaPaises();
            this.paises = new HashMap<Integer, PaisInt>(paisesLista.size());
            for (PaisInt pais : paisesLista) {
                this.paises.put(pais.getIdPais(), pais);
            }
        } else {
            this.paises = new HashMap<Integer, PaisInt>(0);
        }
    }

    /**
     * Método para establecer el atributo validatorFormatW8BENEService
     * 
     * @param validatorFormatW8BENEService El valor del atributo validatorFormatW8BENEService a
     *        establecer.
     */
    public void setValidatorFormatW8BENEService(
            final ValidatorBeneficiarioService validatorFormatW8BENEService) {
        this.validatorFormatW8BENEService = validatorFormatW8BENEService;
    }

    /**
     * Método para obtener el atributo formatoW8Service
     * 
     * @return El atributo formatoW8Service
     */
    public FormatoW8Service getFormatoW8Service() {
        return this.formatoW8Service;
    }

    /**
     * Método para establecer el atributo formatoW8Service
     * 
     * @param formatoW8Service El valor del atributo formatoW8Service a establecer.
     */
    public void setFormatoW8Service(final FormatoW8Service formatoW8Service) {
        this.formatoW8Service = formatoW8Service;
    }

    /**
     * Método para establecer el atributo validatorFormatW8IMY2015Service
     * 
     * @param validatorFormatW8IMY2015Service El valor del atributo validatorFormatW8IMY2015Service
     *        a establecer.
     */
    public void setValidatorFormatW8IMY2015Service(
            final ValidatorBeneficiarioService validatorFormatW8IMY2015Service) {
        this.validatorFormatW8IMY2015Service = validatorFormatW8IMY2015Service;
    }

    /** @param validatorFormatW8BENE2016Service to be set in this.validatorFormatW8BENE2016Service */
    public void setValidatorFormatW8BENE2016Service(
            final ValidatorBeneficiarioService validatorFormatW8BENE2016Service) {
        this.validatorFormatW8BENE2016Service = validatorFormatW8BENE2016Service;
    }

    /**
     * Método para establecer el atributo validatorFormatW8IMY2017Service
     * 
     * @param validatorFormatW8IMY2017Service El valor del atributo validatorFormatW8IMY2017Service
     *        a establecer.
     */
    public void setValidatorFormatW8IMY2017Service(
            final ValidatorBeneficiarioService validatorFormatW8IMY2017Service) {
        this.validatorFormatW8IMY2017Service = validatorFormatW8IMY2017Service;
    }

	public void setMailService(MailService mailService) {
		this.mailService = mailService;
	}

	/**
	 * @param serviceFormatosFiscales the serviceFormatosFiscales to set
	 */
	public void setServiceFormatosFiscales(FormatoFiscalService serviceFormatosFiscales) {
		this.serviceFormatosFiscales = serviceFormatosFiscales;
	}

	
	/**
	 * Método para consultar los custodios activos
	 * 
	 * @return listCatBic
	 */
	public List<Object[]> obtieneCatBicActivo() throws BusinessException {

		 this.log.info("Entrando a obtieneCatBicActivo()");
	        List<Object[]> listCatBic = this.catBicDao.findCatBicByNameAndActive();
	        if (listCatBic == null) {
	            listCatBic = new ArrayList<Object[]>();
	        }
	        return listCatBic;
	}

	public boolean esCatBicActivo(Long idCuentaNombrada) {

		this.log.info("Entrando a esCatBicActivo");
		
		CatBic catBic = this.catBicDao.getCatBicEntityByIdCuentaNombrada(idCuentaNombrada);
		
		boolean isActive = false;
		
		if(catBic !=null) {
			if(catBic.getActivo()==1) {
				isActive = true;
			}
		}
		
		return isActive;
	}

	public List<CatBicVO> obtieneCatBicEntities() {
		
		this.log.info("Entrando a obtieneCatBicEntities");
		List<CatBicVO> lisCatBicVOs = new ArrayList<CatBicVO>();
		
		List<Object[]> listCatbic = new ArrayList<Object[]>(catBicDao.findCatBicByNameEntity());

		for(Object[] cb : listCatbic) {				
			lisCatBicVOs.add(mapCatBicToCatBicVO(cb));
		}
		return lisCatBicVOs;
	}
	
	
	private CatBicVO mapCatBicToCatBicVO(Object[] catBic) {
		
		CatBicVO catvo = new CatBicVO();

		catvo.setCuentaNombrada((Long)catBic[0]);
		catvo.setDetalleCustodio((String) catBic[1]);
		catvo.setBicProd((String) catBic[2]);
		catvo.setEstatusRegistro((String) catBic[3]);
		catvo.setActivo((Integer) catBic[4]);
		catvo.setEditar(false);
		catvo.setAbreviacionCustodio((String) catBic[5]);
		
		return catvo;
	}

	public void modificarCustodios(CatBicVO custodiosBeneficiarios) {
		if(custodiosBeneficiarios.getAbreviacionCustodio() != null){
			custodiosBeneficiarios.setAbreviacionCustodio(custodiosBeneficiarios.getAbreviacionCustodio().toUpperCase());
		}
		this.catBicDao.modificarCustodios(custodiosBeneficiarios);
	}
	
	
	/**
	 * Valida si la abreviacion de un custodio existe.
	 */
	public CatBic findAbreviacionByCustodio(String abreviacionCustodio) {
		
		return this.catBicDao.findAbreviacionByCustodio(abreviacionCustodio);

	}

    public void setListaFormatosDepurar(String listaFormatosDepurar) {
        this.listaFormatosDepurar = listaFormatosDepurar;
    }

}
