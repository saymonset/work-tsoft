package com.indeval.portalinternacional.presentation.controller.consultaEmisiones;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.apache.commons.lang.StringUtils;

import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portaldali.middleware.servicios.modelo.dto.EstatusEmisionesDTO;
import com.indeval.portaldali.middleware.servicios.modelo.vo.EmisionVO;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portaldali.persistence.modelo.CuentaNombrada;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.DivisionInternacionalService;
import com.indeval.portalinternacional.middleware.servicios.constantes.Constantes;
import com.indeval.portalinternacional.middleware.servicios.modelo.CatBic;
import com.indeval.portalinternacional.middleware.servicios.modelo.SicEmision;
import com.indeval.portalinternacional.presentation.controller.common.ControllerBase;

/**
 * 
 * @author Cesar Hernandez
 *
 */
public class ConsultaEmisionesBean extends ControllerBase {
	
	private EmisionVO emision = null;
	
	private CatBic catBic = null;
	
	private EstatusEmisionesDTO estatusEmisionesDTO = null;
	
	/** Lista de Custodios */
	private List<SelectItem> listaCustodios;
	private List<SelectItem> listaEstatus;
	
	private DivisionInternacionalService divisionInternacionalService;
	
	private boolean consultaEjecutada;
	
	private int totalPaginas = 1;
	
	private long idEstatus;
	private long idCatbic;
	
	private PaginaVO resultados = null;

    private boolean banderaBitacoraConsulta = false;
	
	private int totalRegistros = 0;

	private boolean esUsuarioCambioBoveda = false;

    private List<SelectItem> listaBovedas;

    private long idCuentaBoveda = 0;
	
	/**
	 * Inicializa el bean
	 * @return
	 */
	public String getInit()
	{
		if(paginaVO == null)
			paginaVO = new PaginaVO();
		
		if(emision == null)
			emision = new EmisionVO();
		
		if(emision.getTv() == null)
			emision.setTv(StringUtils.EMPTY);
		
		if(emision.getEmisora() == null)
			emision.setEmisora(StringUtils.EMPTY);
		
		if(emision.getSerie() == null)
			emision.setSerie(StringUtils.EMPTY);
		
		if(emision.getIsin() == null)
			emision.setIsin(StringUtils.EMPTY);
		
		if(catBic == null)
			catBic = new CatBic();
		
		if(catBic.getCuentaNombrada() == null)
			catBic.setCuentaNombrada(new CuentaNombrada());
		
		if(estatusEmisionesDTO == null)
			estatusEmisionesDTO = new EstatusEmisionesDTO();
		
		inicializaCustodios();
		
		inicializaEstatus();

		esUsuarioCambioBoveda = false;
        if (this.usuarioContieneRolCambioBoveda()) {
            this.inicializaBovedas();
            this.esUsuarioCambioBoveda = true;
            //Solo carga el estatus de emision liberada para cambio de boveda 
            SelectItem estatusLiberada = new SelectItem(3L, "Liberada");
    		listaEstatus = new ArrayList<SelectItem>();
    		listaEstatus.add(estatusLiberada);
        }
		
		paginaVO.setRegistrosXPag(50);
		paginaVO.setOffset(0);
		banderaBitacoraConsulta = false;

		return null;
	}

    /**
     * Verifica si en los roles del usuario se encuentra el de Cambio de Boveda.
     * @return true si contiene el rol, false en caso contrario.
     */
    private boolean usuarioContieneRolCambioBoveda() {
        final String ROL_CAMBIO_BOVEDA = "INT_CAMBIO_BOVEDA";
        return this.isUserInRoll(ROL_CAMBIO_BOVEDA);
    }

	/**
	 * Buscar las emisiones
	 * @param evt
	 */
	public void buscarEmisiones(ActionEvent evt)
	{
        paginaVO.setRegistrosXPag(50);
		paginaVO.setOffset(0);
        getPaginaVO().setRegistros(null);
		ejecutarConsulta();

	}
	
	/**
	 * Ejecuta la consulta y calcula el numero de pagina para la paginacion.
	 * TODO Este metodo es un overide de la clase padre
	 */
	public String ejecutarConsulta() {
        if (this.esUsuarioCambioBoveda) {
        	//Si no elije estatus emision, solo consulta liberadas
        	if(idEstatus == -1) {
        		idEstatus = 3;
        		estatusEmisionesDTO = divisionInternacionalService.obtenerEstatusEmisionByPk(BigInteger.valueOf(idEstatus));
        	}
            paginaVO = 
            	divisionInternacionalService.findSicEmisionesByEmisionAndCustodioPosicionCero(
            		estatusEmisionesDTO, catBic, emision, paginaVO);
        }
        else {
            paginaVO = this.divisionInternacionalService.findSicEmisionesByEmisionAndCustodio(
                       estatusEmisionesDTO, catBic, emision, paginaVO, banderaBitacoraConsulta);
        }
        
        banderaBitacoraConsulta = false;
		totalPaginas = paginaVO.getTotalRegistros() / paginaVO.getRegistrosXPag();
		
		if(paginaVO.getTotalRegistros() % paginaVO.getRegistrosXPag() > 0)
			totalPaginas++;
		
		totalPaginas = (totalPaginas <= 0) ? 1 : totalPaginas;
		
		setConsultaEjecutada(true);
		
		System.out.println("************** CUSTODIO:: " + catBic.getDetalleCustodio());
				
		return null;
	}
	
	/**
	 * Limpia todos los campos
	 * @param evt
	 */
	public void limpiar(ActionEvent evt)
	{
         banderaBitacoraConsulta = false;
		emision.setTv(StringUtils.EMPTY);
		emision.setEmisora(StringUtils.EMPTY);
		emision.setSerie(StringUtils.EMPTY);
		emision.setIsin(StringUtils.EMPTY);
		
		estatusEmisionesDTO.setId(-1);
		estatusEmisionesDTO.setDescripcion(StringUtils.EMPTY);
		
		catBic.getCuentaNombrada().setIdCuentaNombrada((long)-1);
		catBic.setDetalleCustodio(StringUtils.EMPTY);
		
		if(resultados != null)
			resultados.getRegistros().clear();
		if(paginaVO.getRegistros() != null)
			paginaVO.getRegistros().clear();
		paginaVO.setRegistrosXPag(50);
		paginaVO.setOffset(0);
		
		idEstatus = -1;
		idCatbic = -1;
		
		setConsultaEjecutada(false);
	}

    /**
     * Inicializa lista de bovedas
     */
    private void inicializaBovedas() {
        List<Object[]> lista = this.divisionInternacionalService.obtieneAllCatBic();
        listaBovedas = new ArrayList<SelectItem>();

        /** IMPORTANTE: Las siguientes 22 lineas de codigo, es previsto por si Zazil quiere eliminar del listado las bovedas de MILA */
        /*final long BOV_DECEVAL = 99082;
        final long BOV_CAVALI = 99084;
        final long BOV_DCVCHILE = 17472;
        List<Object[]> listaTemp = new ArrayList<Object[]>();
        boolean encontrado = false;
        for (Object[] object : lista) {
            if (BOV_DECEVAL == ((Long)object[0]).longValue()) {
                encontrado = true;
            }
            else if (BOV_CAVALI == ((Long)object[0]).longValue()) {
                encontrado = true;
            }
            else if (BOV_DCVCHILE == ((Long)object[0]).longValue()) {
                encontrado = true;
            }
            if (!encontrado) {
                listaTemp.add(object);
            }
            encontrado = false;
        }
        lista = listaTemp;
        listaTemp = null;*/

        for(Object[] bovedaActual : lista) {
            listaBovedas.add(new SelectItem((Long)bovedaActual[0], (String)bovedaActual[1]));
        }
    }

	/**
	 * Inicializa lista de custodios
	 */
	private void inicializaCustodios() {
		List<Object[]> lista = divisionInternacionalService.obtieneAllCatBic();
		listaCustodios = new ArrayList<SelectItem>();
		
		for(Object[] custodioActual : lista) {
			listaCustodios.add(new SelectItem((Long)custodioActual[0], (String)custodioActual[1]));
		}
	}
	
	/**
	 * Inicializa lista de estatus de emisiones
	 */
	private void inicializaEstatus() {
		List<EstatusEmisionesDTO> lista = divisionInternacionalService.obtieneEmisionesEstatus();
		listaEstatus = new ArrayList<SelectItem>();
		
		for(EstatusEmisionesDTO dto : lista) {
			listaEstatus.add(new SelectItem(dto.getId(), dto.getDescripcion()));
		}
	}

    /**
     * Asigna la boveda a las emisiones que tiene posicion cero.
     * @param evt
     */
    public void asignaBoveda(ActionEvent evt) {

        SicEmision[] emisiones = (SicEmision[]) paginaVO.getRegistros().toArray(new SicEmision[] {});
        List<SicEmision> listaEmisiones = new ArrayList<SicEmision>();

        try {
            for (SicEmision emision : emisiones) {
                if (emision.isAsigno()) {
                    listaEmisiones.add(emision);
                }
            }

            if (this.idCuentaBoveda <= 0) {
                throw new BusinessException("Es necesario seleccionar una B\u00F3veda del combo!");
            }

            if (listaEmisiones == null || listaEmisiones.size() <= 0) {
                throw new BusinessException("No seleccion\u00F3 ninguna Emisi\u00F3n para Asignar la B\u00F3veda!");
            }

            /*System.out.println("####### IdCuentaBoveda=[" + this.idCuentaBoveda + "]");
            for (SicEmision s : listaEmisiones) {
                System.out.println("####### IdSicEmision=[" + s.getIdSicEmisiones() + "] / Emision=[" + 
                        s.getEmision().getInstrumento().getClaveTipoValor() + "|" + s.getEmision().getEmisora().getClavePizarra() + 
                        "|" + s.getEmision().getSerie() + "]");

            }*/

            if (this.usuarioContieneRolCambioBoveda()) {
              this.divisionInternacionalService.realizarCambioDeBoveda(listaEmisiones, this.idCuentaBoveda);
              super.agregarInfoMensaje("B\u00F3veda Asignada a las Emisiones seleccionadas!");
            }
            else {
                throw new BusinessException("Privilegios insuficientes!");
            }
        } catch(BusinessException be) {
            agregarMensaje(be);
            return;
        } catch(Exception e) {
            agregarMensaje(e);
            return;
        }

        this.buscarEmisiones(evt);
    }

	/**
	 * Genera los reportes de Consulta de Emisiones
	 * @param evt
	 */
	public void generarReportes(ActionEvent evt)
	{	

		reiniciarEstadoPeticion();
		resultados = new PaginaVO();
		resultados.setOffset(0);
		resultados.setRegistrosXPag(PaginaVO.TODOS);
		
		resultados = divisionInternacionalService.findSicEmisionesByEmisionAndCustodio(estatusEmisionesDTO, catBic, emision, resultados, false);
		totalRegistros = resultados.getRegistros().size();
	}
	
	/**
	 * @return the emision
	 */
	public EmisionVO getEmision() {
		return emision;
	}

	/**
	 * @param emision the emision to set
	 */
	public void setEmision(EmisionVO emision) {
		this.emision = emision;
	}

	/**
	 * @return the consultaEjecutada
	 */
	public boolean isConsultaEjecutada() {
		return consultaEjecutada;
	}

	/**
	 * @param consultaEjecutada the consultaEjecutada to set
	 */
	public void setConsultaEjecutada(boolean consultaEjecutada) {
		this.consultaEjecutada = consultaEjecutada;
	}

	/**
	 * @return the catBic
	 */
	public CatBic getCatBic() {
		return catBic;
	}

	/**
	 * @param catBic the catBic to set
	 */
	public void setCatBic(CatBic catBic) {
		this.catBic = catBic;
	}

	/**
	 * @return the listaCustodios
	 */
	public List getListaCustodios() {
		return listaCustodios;
	}

	/**
	 * @param listaCustodios the listaCustodios to set
	 */
	public void setListaCustodios(List listaCustodios) {
		this.listaCustodios = listaCustodios;
	}

	/**
	 * @return the divisionInternacionalService
	 */
	public DivisionInternacionalService getDivisionInternacionalService() {
		return divisionInternacionalService;
	}

	/**
	 * @param divisionInternacionalService the divisionInternacionalService to set
	 */
	public void setDivisionInternacionalService(
			DivisionInternacionalService divisionInternacionalService) {
		this.divisionInternacionalService = divisionInternacionalService;
	}

	/**
	 * @return the listaEstatus
	 */
	public List getListaEstatus() {
		return listaEstatus;
	}

	/**
	 * @param listaEstatus the listaEstatus to set
	 */
	public void setListaEstatus(List listaEstatus) {
		this.listaEstatus = listaEstatus;
	}

	/**
	 * @return the totalPaginas
	 */
	public int getTotalPaginas() {
		return totalPaginas;
	}

	/**
	 * @param totalPaginas the totalPaginas to set
	 */
	public void setTotalPaginas(int totalPaginas) {
		this.totalPaginas = totalPaginas;
	}

	/**
	 * @return the estatusEmisionesDTO
	 */
	public EstatusEmisionesDTO getEstatusEmisionesDTO() {
		return estatusEmisionesDTO;
	}

	/**
	 * @param estatusEmisionesDTO the estatusEmisionesDTO to set
	 */
	public void setEstatusEmisionesDTO(EstatusEmisionesDTO estatusEmisionesDTO) {
		this.estatusEmisionesDTO = estatusEmisionesDTO;
	}

	/**
	 * @return the idEstatus
	 */
	public long getIdEstatus() {
		return idEstatus;
	}

	/**
	 * @param idEstatus the idEstatus to set
	 */
	public void setIdEstatus(long idEstatus) {
		this.idEstatus = idEstatus;
		
		estatusEmisionesDTO = divisionInternacionalService.obtenerEstatusEmisionByPk(BigInteger.valueOf(idEstatus));
		
		if(estatusEmisionesDTO == null){
			estatusEmisionesDTO = new EstatusEmisionesDTO();
			estatusEmisionesDTO.setId(-1);
			estatusEmisionesDTO.setDescripcion("TODAS");
		}
	}

	/**
	 * @return the idCatbic
	 */
	public long getIdCatbic() {
		return idCatbic;
	}

	/**
	 * @param idCatbic the idCatbic to set
	 */
	public void setIdCatbic(long idCatbic) {
		this.idCatbic = idCatbic;
		
		catBic.getCuentaNombrada().setIdCuentaNombrada((long)-1);
		catBic.setDetalleCustodio(StringUtils.EMPTY);
		
		for(int i = 0; i < listaCustodios.size(); i++){
			if(((SelectItem)listaCustodios.get(i)).getValue().toString().equals(String.valueOf(idCatbic))){
				catBic.getCuentaNombrada().setIdCuentaNombrada(idCatbic);
				catBic.setDetalleCustodio(((SelectItem)listaCustodios.get(i)).getLabel().toString());
				break;
			}
		}//for
	}
	
	/**Metodo que regresa el valor del Isin ingresado, para mostrarlo en la tabla resumen
	 * return String Isin
	 * */
	public String getIsinResumen(){
		String isin = Constantes.OPCION_TODAS_CRITERIO;
		if(!StringUtils.isBlank(emision.getIsin())){
			return emision.getIsin().toUpperCase();
		}
		return isin;
	}
	/**
	 * @return the resultados
	 */
	public PaginaVO getResultados() {
		return resultados;
	}

	/**
	 * @param resultados the resultados to set
	 */
	public void setResultados(PaginaVO resultados) {
		this.resultados = resultados;
	}

	/**
	 * @return the totalRegistros
	 */
	public int getTotalRegistros() {
		return totalRegistros;
	}

	/**
	 * @param totalRegistros the totalRegistros to set
	 */
	public void setTotalRegistros(int totalRegistros) {
		this.totalRegistros = totalRegistros;
	}

    public boolean isBanderaBitacoraConsulta() {
        return banderaBitacoraConsulta;
    }

    public void setBanderaBitacoraConsulta(boolean banderaBitacoraConsulta) {
        this.banderaBitacoraConsulta = banderaBitacoraConsulta;
    }

    public boolean isEsUsuarioCambioBoveda() {
        return esUsuarioCambioBoveda;
    }

    public void setEsUsuarioCambioBoveda(boolean esUsuarioCambioBoveda) {
        this.esUsuarioCambioBoveda = esUsuarioCambioBoveda;
    }

    public List getListaBovedas() {
        return listaBovedas;
    }

    public void setListaBovedas(List listaBovedas) {
        this.listaBovedas = listaBovedas;
    }

    public long getIdCuentaBoveda() {
        return idCuentaBoveda;
    }

    public void setIdCuentaBoveda(long idCuentaBoveda) {
        this.idCuentaBoveda = idCuentaBoveda;
    }

}
