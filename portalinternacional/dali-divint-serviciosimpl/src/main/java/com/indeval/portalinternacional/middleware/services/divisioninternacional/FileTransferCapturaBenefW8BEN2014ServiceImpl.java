package com.indeval.portalinternacional.middleware.services.divisioninternacional;

import static com.indeval.portalinternacional.middleware.services.util.ExcelUtils.getColumnAsString;
import static com.indeval.portalinternacional.middleware.services.util.ExcelUtils.transformaCadena;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portalinternacional.middleware.servicios.constantes.Constantes;
import com.indeval.portalinternacional.middleware.servicios.modelo.Beneficiario;
import com.indeval.portalinternacional.middleware.servicios.modelo.Domicilio;
import com.indeval.portalinternacional.middleware.servicios.modelo.Field3W8BEN;
import com.indeval.portalinternacional.middleware.servicios.modelo.FormatoW8BEN;
import com.indeval.portalinternacional.middleware.servicios.modelo.TipoBeneficiario;
import com.indeval.portalinternacional.middleware.servicios.modelo.filetransfer.beneficiario.tipoformato.FileTransferFormaW8BEN2014;
import com.indeval.portalinternacional.middleware.servicios.vo.TipoFormato;

public class FileTransferCapturaBenefW8BEN2014ServiceImpl extends
        FileTransferCapturaBenefServiceImpl<FileTransferFormaW8BEN2014> {



    /** Log de clase. */
	private static final Logger logger = LoggerFactory.getLogger(FileTransferCapturaBenefW8BEN2014ServiceImpl.class);



    public int guardaBeneficiarios(final String claveUsuario, final Long tipoFormato)
            throws BusinessException {
        logger.info("Entrando a FileTransferCapturaBenefServiceImpl.guardaBeneficiarios()");
        Boolean cuentaConRegistrosCorrectos = Boolean.FALSE;
        List<FileTransferFormaW8BEN2014> listaRegistrosCorrectos = null;
        int numeroBeneficiariosGuardados = 0;
        listaRegistrosCorrectos = this.consultaRegistrosCorrectos(claveUsuario);
        Beneficiario beneficiario = null;
        if (tipoFormato == Constantes.FORMATO_W8BEN2014) {
            if (listaRegistrosCorrectos != null && !listaRegistrosCorrectos.isEmpty()) {
                cuentaConRegistrosCorrectos = Boolean.TRUE;
                for (FileTransferFormaW8BEN2014 fileTransferFormaW8BEN2014 : listaRegistrosCorrectos) {
                    Long idInstitucion = fileTransferFormaW8BEN2014.getIdInstitucion();
                    beneficiario = this.convierteBeneficiario(fileTransferFormaW8BEN2014);
                    this.getControlBeneficiariosService().insertaBeneficiario(beneficiario,
                            idInstitucion);
                    numeroBeneficiariosGuardados++;
                }
            }
            this.eliminaCargaExistente(claveUsuario, cuentaConRegistrosCorrectos);
        }
        return numeroBeneficiariosGuardados;
    }



    @Override
    public FileTransferFormaW8BEN2014 convertRowToFileTransferForma(final HSSFRow row) {

        FileTransferFormaW8BEN2014 fileTransferFormaW8BEN2014 = new FileTransferFormaW8BEN2014();

        String tipoFormato = getColumnAsString(row.getCell(0));

        fileTransferFormaW8BEN2014.setTipoFormato(tipoFormato);

        String tipoBeneficiario = getColumnAsString(row.getCell(1));

        fileTransferFormaW8BEN2014.setTipoBeneficiario(transformaCadena(tipoBeneficiario));

        String custodio = getColumnAsString(row.getCell(2));

        fileTransferFormaW8BEN2014.setClaveCustodio(transformaCadena(custodio));

        String institucion = getColumnAsString(row.getCell(3));

        fileTransferFormaW8BEN2014.setClaveInstitucion(transformaCadena(institucion));

        String fechaFormato = getColumnAsString(row.getCell(4));

        fileTransferFormaW8BEN2014.setFechaFormato(transformaCadena(fechaFormato));

        String estado = getColumnAsString(row.getCell(5));

        fileTransferFormaW8BEN2014.setEstado(transformaCadena(estado));

        String nombre = getColumnAsString(row.getCell(6));

        fileTransferFormaW8BEN2014.setNombre(transformaCadena(nombre));

        String apellidoPaterno = getColumnAsString(row.getCell(7));

        fileTransferFormaW8BEN2014.setApellidoPaterno(transformaCadena(apellidoPaterno));

        String apellidoMaterno = getColumnAsString(row.getCell(8));

        fileTransferFormaW8BEN2014.setApellidoMaterno(transformaCadena(apellidoMaterno));

        String paisResidencia = getColumnAsString(row.getCell(9));

        fileTransferFormaW8BEN2014.setPaisResidencia(paisResidencia);

        String residenceAddressStreet = getColumnAsString(row.getCell(10));

        fileTransferFormaW8BEN2014.setResidenceAddressStreet(residenceAddressStreet);

        String residenceAddressOutNumber = getColumnAsString(row.getCell(11));

        fileTransferFormaW8BEN2014.setResidenceAddressOutNumber(residenceAddressOutNumber);

        String residenceAddressIntNumbre = getColumnAsString(row.getCell(12));

        fileTransferFormaW8BEN2014.setResidenceAddressIntNumber(residenceAddressIntNumbre);

        String residenceAddressPostalCode = getColumnAsString(row.getCell(13));

        fileTransferFormaW8BEN2014.setResidenceAddressPostalCode(residenceAddressPostalCode);

        String residenceAddressCity = getColumnAsString(row.getCell(14));

        fileTransferFormaW8BEN2014.setResidenceAddressCity(residenceAddressCity);

        String residenceAddressState = getColumnAsString(row.getCell(15));

        fileTransferFormaW8BEN2014.setResidenceAddressState(residenceAddressState);

        String residenceAddressCountry = getColumnAsString(row.getCell(16));

        fileTransferFormaW8BEN2014.setResidenceAddressCountry(residenceAddressCountry);


        String mailAddressStreet = getColumnAsString(row.getCell(17));

        fileTransferFormaW8BEN2014.setMailAddressStreet(mailAddressStreet);

        String setMailAddressOutNumber = getColumnAsString(row.getCell(18));

        fileTransferFormaW8BEN2014.setMailAddressOutNumber(setMailAddressOutNumber);

        String mailAddressIntNumber = getColumnAsString(row.getCell(19));

        fileTransferFormaW8BEN2014.setMailAddressIntNumber(mailAddressIntNumber);

        String setMailAddressPostalCode = getColumnAsString(row.getCell(20));

        fileTransferFormaW8BEN2014.setMailAddressPostalCode(setMailAddressPostalCode);

        String setMailAddressCity = getColumnAsString(row.getCell(21));

        fileTransferFormaW8BEN2014.setMailAddressCity(setMailAddressCity);

        String setMailAddressState = getColumnAsString(row.getCell(22));

        fileTransferFormaW8BEN2014.setMailAddressState(setMailAddressState);

        String setMailAddressCountry = getColumnAsString(row.getCell(23));

        fileTransferFormaW8BEN2014.setMailAddressCountry(setMailAddressCountry);

        String foreingTaxIdNumber = getColumnAsString(row.getCell(24));

        fileTransferFormaW8BEN2014.setForeingTaxIdNumber(foreingTaxIdNumber);

        String referenceNumber = getColumnAsString(row.getCell(25));

        fileTransferFormaW8BEN2014.setReferenceNumber(referenceNumber);

        String fechaNacimiento = getColumnAsString(row.getCell(26));

        fileTransferFormaW8BEN2014.setFechaNacimiento(fechaNacimiento);

        String paisResidenteBeneficiaro = getColumnAsString(row.getCell(27));

        fileTransferFormaW8BEN2014.setPaisResidenteBeneficiaro(paisResidenteBeneficiaro);

        String idArticuloReclamoTarifa = getColumnAsString(row.getCell(28));

        fileTransferFormaW8BEN2014.setIdArticuloReclamoTarifa(idArticuloReclamoTarifa);

        String porcentajeTasaRetencion = getColumnAsString(row.getCell(29));

        fileTransferFormaW8BEN2014.setPorcentajeTasaRetencion(porcentajeTasaRetencion);

        String tipoIngreso = getColumnAsString(row.getCell(30));

        fileTransferFormaW8BEN2014.setTipoIngreso(tipoIngreso);

        String razonArticuloReclamo = getColumnAsString(row.getCell(31));

        fileTransferFormaW8BEN2014.setRazonArticuloReclamo(razonArticuloReclamo);

        // String capacidadActuacion = getColumnAsString(row.getCell(30));
        //
        // fileTransferFormaW8BEN2014.setCapacidadActuacion(capacidadActuacion);
        //
        // String nombreFirmante = getColumnAsString(row.getCell(31));
        //
        // fileTransferFormaW8BEN2014.setNombreFirmante(nombreFirmante);

        return fileTransferFormaW8BEN2014;

    }



    public Beneficiario convierteBeneficiario(
            final FileTransferFormaW8BEN2014 fileTransferFormaW8BEN2014) {
        logger.info("Convirtiendo el Beneficiario");



        // datos comunes
        Beneficiario beneficiario = new Beneficiario();
        beneficiario.setNombres(fileTransferFormaW8BEN2014.getNombre());
        beneficiario.setApellidoPaterno(fileTransferFormaW8BEN2014.getApellidoPaterno());
        beneficiario.setApellidoMaterno(fileTransferFormaW8BEN2014.getApellidoMaterno());

        beneficiario.setFechaFormato(fileTransferFormaW8BEN2014.getDateFechaFormato());
        beneficiario.setIdCuentaNombrada(fileTransferFormaW8BEN2014.getIdCuentaNombrada());
        beneficiario.setActivo(Boolean.valueOf("ACTIVO".equals(fileTransferFormaW8BEN2014
                .getEstado())));
        beneficiario.setPersonaFisica(Boolean.TRUE);
        // String tipoFormato = TipoFormato.W8BEN2014.getTipoFormato();
        // Nuevo Foramto w82017
        String tipoFormato = TipoFormato.W8BEN2017.getTipoFormato();
        beneficiario.setTipoFormato(tipoFormato);

        beneficiario.setPaisIncorporacion(fileTransferFormaW8BEN2014.getPaisResidencia());
        // tipo Beneficiario
        TipoBeneficiario tipoBeneficiario = new TipoBeneficiario();

        tipoBeneficiario.setIdTipoBeneficiario(1L);

        beneficiario.setTipoBeneficiario(tipoBeneficiario);
        // domicilio
        Domicilio domicilioResidencial = new Domicilio();
        domicilioResidencial.setStreet(fileTransferFormaW8BEN2014.getResidenceAddressStreet());
        domicilioResidencial.setOuterNumber(fileTransferFormaW8BEN2014
                .getResidenceAddressOutNumber());
        domicilioResidencial.setInteriorNumber(fileTransferFormaW8BEN2014
                .getResidenceAddressIntNumber());
        domicilioResidencial.setPostalCode(fileTransferFormaW8BEN2014
                .getResidenceAddressPostalCode());
        domicilioResidencial.setCityTown(fileTransferFormaW8BEN2014.getResidenceAddressCity());
        domicilioResidencial
                .setStateProvince(fileTransferFormaW8BEN2014.getResidenceAddressState());
        domicilioResidencial.setCountry(fileTransferFormaW8BEN2014.getResidenceAddressCountry());



        beneficiario.setDomicilioW8Normal(domicilioResidencial);



        FormatoW8BEN formatoW8BEN = new FormatoW8BEN();

        Field3W8BEN field3W8BEN = new Field3W8BEN();
        field3W8BEN.setIdCampo(1L);
        formatoW8BEN.setField3(field3W8BEN);

        formatoW8BEN.setField9OptionA(!"NA".equalsIgnoreCase(fileTransferFormaW8BEN2014
                .getPaisResidenteBeneficiaro()));
        formatoW8BEN.setField9OptionACountry(fileTransferFormaW8BEN2014
                .getPaisResidenteBeneficiaro());
        formatoW8BEN.setField9OptionB(Boolean.FALSE);
        formatoW8BEN.setField9OptionC(Boolean.FALSE);
        formatoW8BEN.setField9OptionD(Boolean.FALSE);
        formatoW8BEN.setField9OptionE(Boolean.FALSE);

        if ("NA".equalsIgnoreCase(fileTransferFormaW8BEN2014.getIdArticuloReclamoTarifa())
                && "NA".equalsIgnoreCase(fileTransferFormaW8BEN2014.getPorcentajeTasaRetencion())
                && "NA".equalsIgnoreCase(fileTransferFormaW8BEN2014.getTipoIngreso())
                && "NA".equalsIgnoreCase(fileTransferFormaW8BEN2014.getRazonArticuloReclamo())) {
            formatoW8BEN.setDisabledSection10(Boolean.TRUE);
        } else {
            formatoW8BEN.setField10Article(fileTransferFormaW8BEN2014.getIdArticuloReclamoTarifa());
            formatoW8BEN.setField10Rate(fileTransferFormaW8BEN2014.getPorcentajeTasaRetencion());
            formatoW8BEN.setField10Type(fileTransferFormaW8BEN2014.getTipoIngreso());
            formatoW8BEN.setField10Reasons(fileTransferFormaW8BEN2014.getRazonArticuloReclamo());
        }
        formatoW8BEN.setField11(Boolean.FALSE);
        formatoW8BEN.setCapacityActing(fileTransferFormaW8BEN2014.getCapacidadActuacion());

        formatoW8BEN.setDisabledreferenceNumber("NA".equalsIgnoreCase(fileTransferFormaW8BEN2014
                .getReferenceNumber()));
        formatoW8BEN.setDisabledUsIdNumber(Boolean.TRUE);
        formatoW8BEN.setForeignTaxIdNumb(fileTransferFormaW8BEN2014.getForeingTaxIdNumber());



        if (!"NA".equalsIgnoreCase(fileTransferFormaW8BEN2014.getMailAddressStreet())) {
            Domicilio domicilioMail = new Domicilio();
            domicilioMail.setStreet(fileTransferFormaW8BEN2014.getMailAddressStreet());
            domicilioMail.setOuterNumber(fileTransferFormaW8BEN2014.getMailAddressOutNumber());
            domicilioMail.setInteriorNumber(fileTransferFormaW8BEN2014.getMailAddressIntNumber());
            domicilioMail.setPostalCode(fileTransferFormaW8BEN2014.getMailAddressPostalCode());
            domicilioMail.setCityTown(fileTransferFormaW8BEN2014.getMailAddressCity());
            domicilioMail.setStateProvince(fileTransferFormaW8BEN2014.getMailAddressState());
            domicilioMail.setCountry(fileTransferFormaW8BEN2014.getMailAddressCountry());

            beneficiario.setDomicilioW8Correo(domicilioMail);
            formatoW8BEN.setDisabledDireccionPostal(Boolean.FALSE);
        } else {
            formatoW8BEN.setDisabledDireccionPostal(Boolean.TRUE);
        }


        formatoW8BEN.setReferenceNumbers(!"NA".equalsIgnoreCase(fileTransferFormaW8BEN2014
                .getReferenceNumber()) ? fileTransferFormaW8BEN2014.getReferenceNumber() : null);

        formatoW8BEN.setFechaNacimiento(fileTransferFormaW8BEN2014.getDateFechaNacimiento());

        formatoW8BEN.setSigner(beneficiario.getNombreGeneral());


        beneficiario.setFormatoW8BEN(formatoW8BEN);



        return beneficiario;
    }



}
