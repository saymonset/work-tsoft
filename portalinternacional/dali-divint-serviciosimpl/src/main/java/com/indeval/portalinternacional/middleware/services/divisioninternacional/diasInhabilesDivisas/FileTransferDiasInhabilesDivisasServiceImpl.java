/*
 * Multidivisas: Dás inhábiles por Divisa : Procesamiento de archivo de carga
 */
package com.indeval.portalinternacional.middleware.services.divisioninternacional.diasInhabilesDivisas;


import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.ConsultaDivisaService;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.DiasInhabilesDivisasService;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.FileTransferService;
import com.indeval.portalinternacional.middleware.servicios.constantes.Constantes;
import com.indeval.portalinternacional.middleware.servicios.dto.DivisaDTO;
import com.indeval.portalinternacional.middleware.servicios.dto.diasIhabilesDivisas.DiasInhabilesDivisasDTO;
import com.indeval.portalinternacional.middleware.servicios.dto.diasIhabilesDivisas.ErrorDiaInhabilDivisa;
import com.indeval.portalinternacional.middleware.servicios.dto.diasIhabilesDivisas.FechaDiaInhabilDTO;
import com.indeval.portalinternacional.middleware.servicios.modelo.DiasInhabilesDivisas;
import com.indeval.portalinternacional.middleware.servicios.modelo.HistoricoDiasInhabilesDivisas;
import com.indeval.portalinternacional.middleware.servicios.vo.FileTransferVO;
import com.indeval.portalinternacional.middleware.servicios.vo.ResumenVO;
import com.indeval.portalinternacional.middleware.servicios.vo.TotalesProcesoVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.*;

import static com.indeval.portalinternacional.middleware.servicios.constantes.EstatusDB.REGISTRADO;
import static com.indeval.portalinternacional.middleware.servicios.dto.diasIhabilesDivisas.ErrorDiaInhabilDivisaTipo.ERROR_DIVISA;
import static com.indeval.portalinternacional.middleware.servicios.dto.diasIhabilesDivisas.ErrorDiaInhabilDivisaTipo.ERROR_REPETIDO;

/**
 * Multidivisas: Carga de archivo de Días Inhábiles por Divisa
 */
@SuppressWarnings({"unchecked"})
public class FileTransferDiasInhabilesDivisasServiceImpl implements FileTransferService, Constantes {
    private static final Logger log = LoggerFactory.getLogger(FileTransferDiasInhabilesDivisasServiceImpl.class);

    private static final SimpleDateFormat FORMATO_FECHA_LLAVE = new SimpleDateFormat("ddMMyyyy");

    private ConsultaDivisaService consultaDivisaService;
    private static HashMap<String, DivisaDTO> divisasValidas;

    private DiasInhabilesDivisasService diasInhabilesDivisasService;


    private void init() {
        log.debug("init :: " + this.getClass().getName());
        if (divisasValidas == null || divisasValidas.isEmpty()) {
            divisasValidas = consultaDivisaService.consultarDivisas();
        }
    }

    /**
     * Valida la información para carga
     *
     * @param cadena
     * @return FechaDiaInhabil
     */
    private FechaDiaInhabilDTO camposDivInt(String cadena) {
        log.debug("camposDivInt :: " + cadena);
        init();
        FechaDiaInhabilDTO fechaDiaInhabil = ValidadorFecha.obtenerFechaValida(cadena);
        if (fechaDiaInhabil.isValida()) {
            fechaDiaInhabil.setDivisaDTO(divisasValidas.get(fechaDiaInhabil.getDivisa().toUpperCase()));
            if (fechaDiaInhabil.getDivisaDTO() == null) {
                fechaDiaInhabil.setError(new ErrorDiaInhabilDivisa(ERROR_DIVISA, fechaDiaInhabil.getCampos()[0]));
                fechaDiaInhabil.setValida(false);
            }
        }
        return fechaDiaInhabil;
    }


    /**
     * @see FileTransferService#almacenaInformacion(FileTransferVO)
     */
    public Integer almacenaInformacion(FileTransferVO fileTransferVO) throws BusinessException {
        log.debug("almacenaInformacion .. ");
        log.debug("Iniciando con la validación y transformación de la información del archivo :: " + fileTransferVO.getNombreArchivo());

        String[] lineasObtenidas = fileTransferVO.getInformacionArchivo();
        List<FechaDiaInhabilDTO> objetosTranformados = new ArrayList<>();
        Set<String> objetosEncontrados = new HashSet<>();

        for (String linea : lineasObtenidas) {
            FechaDiaInhabilDTO fechaDiaInhabilEncontrada = camposDivInt(linea);
            if (fechaDiaInhabilEncontrada.isValida()) {
                String llave = fechaDiaInhabilEncontrada.getDivisaDTO().getId() + "_" +
                        FORMATO_FECHA_LLAVE.format(fechaDiaInhabilEncontrada.getFecha());
                if (objetosEncontrados.contains(llave)) {
                    fechaDiaInhabilEncontrada.setError(new ErrorDiaInhabilDivisa(
                            ERROR_REPETIDO, fileTransferVO.getNombreArchivo() + ": " + llave));
                    fechaDiaInhabilEncontrada.setValida(false);
                } else {
                    objetosEncontrados.add(llave);
                }
            }
            objetosTranformados.add(fechaDiaInhabilEncontrada);
        }
        fileTransferVO.getPaginaVO().setRegistros(objetosTranformados);
        fileTransferVO.getPaginaVO().setTotalRegistros(fileTransferVO.getPaginaVO().getRegistros().size());
        log.debug("Total de registros encontrados para el " +
                "archivo [" + fileTransferVO.getNombreArchivo() + "] " +
                ":: " + fileTransferVO.getPaginaVO().getTotalRegistros());
        return fileTransferVO.getPaginaVO().getTotalRegistros();
    }

    /**
     * @see FileTransferService#cancelaProceso(FileTransferVO)
     */
    public void cancelaProceso(FileTransferVO fileTransferVO) throws BusinessException {
        log.debug("No implementado");
    }

    /**
     * @see FileTransferService#grabaInformacion(FileTransferVO)
     */
    public void grabaInformacion(FileTransferVO fileTransferVO) throws BusinessException {
        log.debug("grabaInformacion");
        Long ultimoID = diasInhabilesDivisasService.getUltimoID();
        HistoricoDiasInhabilesDivisas historicoDiasInhabilesDivisas = new HistoricoDiasInhabilesDivisas();
        historicoDiasInhabilesDivisas.setIdHistorico(ultimoID + 1);
        historicoDiasInhabilesDivisas.setCreador(fileTransferVO.getNombreUsuario());
        Date ahora = new Date();
        historicoDiasInhabilesDivisas.setFechaCreacion(ahora);
        historicoDiasInhabilesDivisas.setFechaUltModificacion(ahora);
        historicoDiasInhabilesDivisas.setEstatus(REGISTRADO.getCodigo());
        historicoDiasInhabilesDivisas.setNombreArchivo(fileTransferVO.getNombreArchivo());
        historicoDiasInhabilesDivisas.setRegistrosTotal(fileTransferVO.getPaginaVO().getTotalRegistros());
        historicoDiasInhabilesDivisas.setRegistrosCorrectos(fileTransferVO.getPaginaVO().getRegistros().size());
        historicoDiasInhabilesDivisas.setRegistrosError(
                historicoDiasInhabilesDivisas.getRegistrosTotal() -
                        historicoDiasInhabilesDivisas.getRegistrosCorrectos());

        diasInhabilesDivisasService.salvarHistorico(historicoDiasInhabilesDivisas);

        List<FechaDiaInhabilDTO> fechaDiaInhabilList = fileTransferVO.getPaginaVO().getRegistros();
        List<DiasInhabilesDivisas> diasInhabilesDivisas = new ArrayList<>();
        for (FechaDiaInhabilDTO fechaDiaInhabil : fechaDiaInhabilList) {
            DiasInhabilesDivisas diaInhabilDivisa = new DiasInhabilesDivisas();
            diaInhabilDivisa.setDiaInhabil(fechaDiaInhabil.getFecha());
            diaInhabilDivisa.setIdDivisa(fechaDiaInhabil.getDivisaDTO().getId());
            diaInhabilDivisa.setCreador(fileTransferVO.getNombreUsuario());
            diaInhabilDivisa.setFechaCreacion(ahora);
            diaInhabilDivisa.setFechaUltModificacion(ahora);
            diaInhabilDivisa.setEstatus(REGISTRADO.getCodigo());
            diaInhabilDivisa.setIdHistoricoDiasInhabilesDivisas(
                    historicoDiasInhabilesDivisas.getIdHistorico());
            diasInhabilesDivisas.add(diaInhabilDivisa);
        }
        diasInhabilesDivisasService.salvarDiasInhabiles(diasInhabilesDivisas);
        fileTransferVO.setObjetoReferenciaFinal(historicoDiasInhabilesDivisas.getIdHistorico());
    }

    /**
     * @see FileTransferService#muestraInformacion(FileTransferVO)
     */
    public TotalesProcesoVO muestraInformacion(FileTransferVO fileTransferVO) throws BusinessException {
        log.debug("FileTransferServiceImpl :: muestraInformacion");
        System.out.println("Aquí se carga la información de resumen");
        return null;
    }

    /**
     * @see FileTransferService#obtieneResumen(FileTransferVO)
     */
    public ResumenVO obtieneResumen(FileTransferVO fileTransferVO) throws BusinessException {
        log.debug("FileTransferServiceImpl :: obtieneResumen");
        ResumenVO resumenVO = new ResumenVO();
        resumenVO.setFechaCarga(new Date());
        resumenVO.setAgenteFirmado(fileTransferVO.getAgenteFirmado());
        resumenVO.setNombreUsuario(fileTransferVO.getNombreUsuario());
        resumenVO.setTotalRegistros(fileTransferVO.getPaginaVO().getTotalRegistros());

        List<FechaDiaInhabilDTO> transformacion = fileTransferVO.getPaginaVO().getRegistros();
        List<FechaDiaInhabilDTO> objetosCargaFinal = new ArrayList<>();


        int errorFormato = 0;
        int errorFecha = 0;
        int errorDivisa = 0;
        int errorRepetido = 0;

        for (FechaDiaInhabilDTO fechaDiaInhabil : transformacion) {
            if (fechaDiaInhabil.isValida()) {
                objetosCargaFinal.add(fechaDiaInhabil);
            } else {
                log.debug(fechaDiaInhabil.getError().getError());
                switch (fechaDiaInhabil.getError().getTipoError().getTipo()) {
                    case 1:
                        errorFormato++;
                        break;
                    case 2:
                    case 3:
                    case 4:
                        errorFecha++;
                        break;
                    case 5:
                        errorDivisa++;
                        break;
                    case 6:
                        errorRepetido++;
                        break;
                }
            }
        }

        List<String> tiposErrores = new ArrayList<>();
        if (errorFormato > 0) tiposErrores.add("  Formato : " + Integer.toString(errorFormato));
        if (errorFecha > 0) tiposErrores.add("    Fecha : " + Integer.toString(errorFecha));
        if (errorDivisa > 0) tiposErrores.add("   Divisa : " + Integer.toString(errorDivisa));
        if (errorRepetido > 0) tiposErrores.add("Repetidos : " + Integer.toString(errorRepetido));
        log.debug(tiposErrores.toString());

        resumenVO.setTotalCargados(objetosCargaFinal.size());
        resumenVO.setTotalError(resumenVO.getTotalRegistros() - resumenVO.getTotalCargados());
        resumenVO.setTiposErrores(tiposErrores);

        fileTransferVO.getPaginaVO().setRegistros(objetosCargaFinal);
        fileTransferVO.getPaginaVO().setTotalRegistros(resumenVO.getTotalRegistros());

        return resumenVO;
    }


    /* Mutators */


    /**
     * @return the consultaDivisaService
     */
    public ConsultaDivisaService getConsultaDivisaService() {
        return consultaDivisaService;
    }

    /**
     * @param consultaDivisaService the consultaDivisaService to set
     */
    public void setConsultaDivisaService(ConsultaDivisaService consultaDivisaService) {
        this.consultaDivisaService = consultaDivisaService;
    }

    public DiasInhabilesDivisasService getDiasInhabilesDivisasService() {
        return diasInhabilesDivisasService;
    }

    public void setDiasInhabilesDivisasService(DiasInhabilesDivisasService diasInhabilesDivisasService) {
        this.diasInhabilesDivisasService = diasInhabilesDivisasService;
    }
}
