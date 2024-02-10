package com.indeval.portalinternacional.middleware.services.divisioninternacional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portalinternacional.middleware.servicios.dto.CustodiosDepositantesDto;
import com.indeval.portalinternacional.middleware.servicios.vo.CustodioVO;
import com.indeval.portalinternacional.middleware.servicios.vo.DepositanteVO;
import com.indeval.portalinternacional.middleware.servicios.vo.EmisionDataBaseVO;
import com.indeval.portalinternacional.middleware.servicios.vo.EmisionVO;
import com.indeval.portalinternacional.middleware.servicios.vo.MensajeSecuenciaVO;
import com.indeval.portalinternacional.persistence.dao.EmisionesCustodiosDepositantesDao;

/**
 * Implementaci&oacute;n de la interfaz de servicio EmisionesCustodiosDepositantesService
 * @author arivera
 * @version 1.0
 */
public class EmisionesCustodiosDepositantesServiceImpl implements EmisionesCustodiosDepositantesService {
	
	/** variable para el log */
	private static final Logger log = LoggerFactory.getLogger(EmisionesCustodiosDepositantesServiceImpl.class);
	
	static final Integer NUMERO_MENSAJES_SECUENCIA = 25;
	
	/** Dao para el manejo de emisiones Custodios Depositantes */
	private EmisionesCustodiosDepositantesDao emisionesCustodiosDepositantesDao;
	
	/**
	* {@inheritDoc}
	*/
	public PaginaVO consultarEmisionesCustodiosDepositantes(final CustodiosDepositantesDto params, PaginaVO paginaVO) throws BusinessException {
		if (params == null) {
			throw new BusinessException("Parametros vacios");
		}
		if (paginaVO == null) {
			throw new BusinessException("Estado de la Paginacion vacio");
		}
		
		PaginaVO pagina = emisionesCustodiosDepositantesDao.consultarEmisionesCustodiosDepositantes(params, paginaVO);
		return pagina;
	}
	
	/**
	* {@inheritDoc}
	*/
	public List<MensajeSecuenciaVO> consultarEmisionesCustodiosDepositantesXml(final CustodiosDepositantesDto params, final PaginaVO paginaVO) throws Exception {
		PaginaVO pagina = emisionesCustodiosDepositantesDao.consultarEmisionesCustodiosDepositantes(params, paginaVO);		
		List<EmisionDataBaseVO> lista = (List<EmisionDataBaseVO>) pagina.getRegistros();
		Boolean agregarCustodio = Boolean.FALSE;
		HashMap<String, EmisionVO> hash = new HashMap<String, EmisionVO>();
		for (EmisionDataBaseVO emisionDataBaseVO : lista) {
            EmisionVO emisionVO = null;
            if (hash.containsKey(StringUtils.upperCase(emisionDataBaseVO.getTv())
                    + StringUtils.upperCase(emisionDataBaseVO.getEmisora())
                    + StringUtils.upperCase(emisionDataBaseVO.getSerie())
                    + StringUtils.upperCase(StringUtils.defaultString(emisionDataBaseVO.getIsin(), "ISIN_NULO")))) {
                emisionVO = hash.get(StringUtils.upperCase(emisionDataBaseVO.getTv())
                        + StringUtils.upperCase(emisionDataBaseVO.getEmisora())
                        + StringUtils.upperCase(emisionDataBaseVO.getSerie()
                        + StringUtils.upperCase(StringUtils.defaultString(emisionDataBaseVO.getIsin(), "ISIN_NULO"))));
                // Buscar el Custodio
                CustodioVO custodioVO = emisionVO.findCustodioVO(emisionDataBaseVO.getBicProd());
                if (custodioVO == null) {
                    custodioVO = new CustodioVO(emisionDataBaseVO.getBicProd(), emisionDataBaseVO.getDetalleCustodio(),
                            emisionDataBaseVO.getClaveTipoInstitucion(), emisionDataBaseVO.getFolioInstitucion(),
                            emisionDataBaseVO.getCuenta());
                    agregarCustodio = Boolean.TRUE;
                }
                // Buscar el depositante
                DepositanteVO depositanteVO = custodioVO.findDepositanteVO(emisionDataBaseVO.getBicDepLiq());
                if (depositanteVO == null) {
                    depositanteVO = new DepositanteVO(emisionDataBaseVO.getBicDepLiq(), emisionDataBaseVO.getDepLiq());
                }
                custodioVO.getDepositantes().add(depositanteVO);
                if (agregarCustodio) {
                    emisionVO.getCustodiosDepositantes().add(custodioVO);
                }
                agregarCustodio = Boolean.FALSE;
            } else {
                emisionVO = new EmisionVO(emisionDataBaseVO.getIsin(),
                        emisionDataBaseVO.getTv(),
                        emisionDataBaseVO.getEmisora(),
                        emisionDataBaseVO.getSerie());
                // Buscar el Custodio
                CustodioVO custodioVO = emisionVO.findCustodioVO(emisionDataBaseVO.getBicProd());
                if (custodioVO == null) {
                    custodioVO = new CustodioVO(emisionDataBaseVO.getBicProd(), emisionDataBaseVO.getDetalleCustodio(),
                            emisionDataBaseVO.getClaveTipoInstitucion(), emisionDataBaseVO.getFolioInstitucion(),
                            emisionDataBaseVO.getCuenta());
                }
                // Buscar el depositante
                DepositanteVO depositanteVO = custodioVO.findDepositanteVO(emisionDataBaseVO.getBicDepLiq());
                if (depositanteVO == null) {
                    depositanteVO = new DepositanteVO(emisionDataBaseVO.getBicDepLiq(), emisionDataBaseVO.getDepLiq());
                }
                custodioVO.getDepositantes().add(depositanteVO);
                emisionVO.getCustodiosDepositantes().add(custodioVO);

                hash.put(emisionVO.getKey(), emisionVO);
            }
        }
		
		
		String sequence = "" + (new Date(System.currentTimeMillis())).getTime();
		List<MensajeSecuenciaVO> ret = new ArrayList<MensajeSecuenciaVO>();
		int i = 1;

		ret.add(new MensajeSecuenciaVO(sequence, i++, new ArrayList(hash.values()).size(), new ArrayList(hash.values())));
		
		return ret;
	}
	
    /**
     * Divide una lista en elementos de tipo
     * <code>MensajeSecuenciaVO</code>
     *
     * @param lista La lista a seccionar
     * @param numeroMensajesSecuencia El numero de elementos por lista
     * @throws Exception
     * @return A <code>List</code> object.
     */
    public static List<MensajeSecuenciaVO> getMensaje(List lista, Integer numeroMensajesSecuencia) throws Exception {
        String sequence = "" + (new Date(System.currentTimeMillis())).getTime();
        log.debug("--> sequence" + sequence);
        List listaDeSublistas = split(lista, numeroMensajesSecuencia.intValue(), false, null);
        List<MensajeSecuenciaVO> ret = new ArrayList();
        int i = 1;
        for (Iterator iter = listaDeSublistas.iterator(); iter.hasNext();) {
            ret.add(new MensajeSecuenciaVO(sequence, i++, listaDeSublistas.size(), (List) iter.next()));
        }
        return ret;
    }
	
    /**
     * Divide una lista en elementos de tipo
     * <code>MensajeSecuenciaVO</code>
     *
     * @param lista La lista a seccionar
     * @param numeroMensajesSecuencia El numero de elementos por lista
     * @throws Exception
     * @return A <code>List</code> object.
     */
    public static List<MensajeSecuenciaVO> dividirMensaje(List lista, Integer numeroMensajesSecuencia) throws Exception {
        String sequence = "" + (new Date(System.currentTimeMillis())).getTime();
        log.debug("--> sequence" + sequence);
        List listaDeSublistas = split(lista, numeroMensajesSecuencia.intValue(), false, null);
        List<MensajeSecuenciaVO> ret = new ArrayList();
        int i = 1;
        for (Iterator iter = listaDeSublistas.iterator(); iter.hasNext();) {
            ret.add(new MensajeSecuenciaVO(sequence, i++, listaDeSublistas.size(), (List) iter.next()));
        }
        return ret;
    }
    
    /**
     * Splits list into multiple lists of equal size. If list size cannot be
     * divided by column count, fill parameter determines should it be padded
     * with pad value or not.
     *
     * @param arg List to be splitted.
     * @param colCount Number of elements in each split.
     * @param pad Last split should be null padded?
     * @param padValue Value that will be used for padding.
     * @return List of list parts.
     */
    public static List split(List arg, int colCount, boolean pad,
            Object padValue) {

        int size = arg.size();
        List rows = new ArrayList(size / colCount + 1);
        int start = 0;
        int end = colCount;
        while (start < size) {
            List row;
            // check is this last and uncomplete row
            if (end > size) {
                // using sublist directly can cause synchronization problems
                row = new ArrayList(arg.subList(start, size));
                if (pad) {
                    for (int i = size; i < end; ++i) {
                        row.add(padValue);
                    }
                }
            } else {
                row = new ArrayList(arg.subList(start, end));
            }
            rows.add(row);
            start = end;
            end += colCount;
        }
        return rows;
    }
    
    /**
     * Metodo que obtiene la fecha actual 
     * @return Date con la fecha atcual
     */
    public static Date getCurrentDate() {
        return new Date(System.currentTimeMillis());
    }


	public void setEmisionesCustodiosDepositantesDao(EmisionesCustodiosDepositantesDao emisionesCustodiosDepositantesDao) {
		this.emisionesCustodiosDepositantesDao = emisionesCustodiosDepositantesDao;
	}	

}
