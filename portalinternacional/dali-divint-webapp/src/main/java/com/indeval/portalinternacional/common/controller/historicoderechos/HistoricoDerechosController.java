package com.indeval.portalinternacional.common.controller.historicoderechos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portaldali.persistence.dao.common.DivisaDao;
import com.indeval.portaldali.persistence.modelo.Divisa;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.DivisionInternacionalService;
import com.indeval.portalinternacional.middleware.servicios.modelo.Control;
import com.indeval.portalinternacional.middleware.servicios.modelo.Custodio;
import com.indeval.portalinternacional.middleware.servicios.modelo.EstadoDerechoInt;
import com.indeval.portalinternacional.middleware.servicios.modelo.TipoPagoInt;
import com.indeval.portalinternacional.middleware.servicios.vo.ConsultaHistoricoParamsTO;
import com.indeval.portalinternacional.presentation.controller.common.ControllerBase;

public class HistoricoDerechosController extends ControllerBase {

	private static final Logger log = LoggerFactory.getLogger(HistoricoDerechosController.class);
	
	 /** Listas */
    private List<SelectItem> listaCustodios;
    private List<SelectItem> listaEstatus;
    private List<SelectItem> listaDivisa;
    private List<SelectItem> listaTipoPagoCAEV;
    private List<SelectItem> listaTipoPagoCAMV;
    private List<SelectItem> listaEstadoMensajeria;
    private Map<String, String> derechosAutomatizadosDeuda;

    private DivisaDao divisaDao;
    
    private DivisionInternacionalService divisionInternacionalService;

    private boolean consultaEjecutada;

    private int totalPaginas = 1;

    private PaginaVO resultados = null;

    private boolean banderaBitacoraConsulta = false;

    private int totalRegistros = 0;

    private ConsultaHistoricoParamsTO calendario;

    private String referencia;
    private String tipoMensaje;
    private String tipoPagoCAEV;
    private String tipoPagoCAMV;
    private String tipoValor;
    private String emisora;
    private String serie;
    private String isin;
    private Date fechaPagoInicio;
    private Date fechaPagoFin;
    private Date fechaCorteInicio;
    private Date fechaCorteFin;
    private Date fechaXdateInicio;
    private Date fechaXdateFin;
    private Date interimDateInicio;
    private Date interimDateFin;
    private String divisa;
    private String refCustodio;
    private String custodio;
    private String estado;
    private String estadoMensajeria;
    
    /** Pagina para los reportes */
    private PaginaVO paginaReportes;

    public HistoricoDerechosController() {

    }

    public String getInit() {
        if (this.paginaVO == null) {
            this.paginaVO = new PaginaVO();
        }

        this.custodio = "-1";
        this.estado = "-1";
        this.tipoPagoCAEV = "-1";
        this.tipoPagoCAMV = "-1";
        this.divisa = "-1";
        this.tipoValor = "";
        this.emisora = "";
        this.serie = "";
        this.isin = "";
        this.refCustodio = "";
        this.fechaPagoInicio = null;
        this.fechaPagoFin = null;
        this.fechaCorteInicio = null;
        this.fechaCorteFin = null;
        this.fechaXdateInicio = null;
        this.fechaXdateFin = null;
        this.estadoMensajeria = "";

        this.inicializaCustodios();
        this.inicializaEstatus();
        this.inicializaDivisa();
        this.inicializaTipoPagoCAEV();
        this.inicializaTipoPagoCAMV();

        this.paginaVO.setRegistrosXPag(50);
        this.paginaVO.setOffset(0);
        this.banderaBitacoraConsulta = false;

        return null;
    }

    /**
     * Buscar las emisiones
     * 
     * @param evt
     */
    public void buscarDerechos(final ActionEvent evt) {
        this.paginaVO.setRegistrosXPag(50);
        this.paginaVO.setOffset(0);
        this.getPaginaVO().setRegistros(null);
        this.ejecutarConsulta();
    }

    /**
     * Genera los reportes de Consulta de Emisiones
     * 
     * @param evt
     */
    public void generarReportes(final ActionEvent evt) {

        this.reiniciarEstadoPeticion();
        this.paginaReportes = new PaginaVO();
        this.paginaReportes.setOffset(0);
        this.paginaReportes.setRegistrosXPag(PaginaVO.TODOS);
        // paginaReportes = divisionInternacionalService.consultaCalendarioDerechos(calendario,
        // paginaReportes);
        this.totalRegistros = this.paginaReportes.getTotalRegistros();

    }

    /**
     * Ejecuta la consulta y calcula el numero de pagina para la paginacion. Este metodo es un
     * overide de la clase padre
     */
    @Override
    public String ejecutarConsulta() {

        this.setParams();

        this.totalPaginas = this.paginaVO.getTotalRegistros() / this.paginaVO.getRegistrosXPag();

        if (this.paginaVO.getTotalRegistros() % this.paginaVO.getRegistrosXPag() > 0) {
            this.totalPaginas++;
        }
        this.totalPaginas = this.totalPaginas <= 0 ? 1 : this.totalPaginas;

        this.setConsultaEjecutada(true);
        return null;
    }

    private void setParams() {
        this.calendario = new ConsultaHistoricoParamsTO();
        // calendario.setRefCustodio(refCustodio);

        this.calendario.setTipoValor(this.tipoValor);
        this.calendario.setEmisora(this.emisora);
        this.calendario.setSerie(this.serie);
        this.calendario.setIsin(this.isin);

        // Faltan fechas

        if (this.custodio != null && !this.custodio.equals("") && this.custodio.matches("-*[0-9]+")) {
            this.calendario.setCustodio(Integer.valueOf(this.custodio));
        }
        if (this.divisa != null && !this.divisa.equals("") && !this.divisa.matches("-*[0-9]+")) {
            this.calendario.setDivisa(this.divisa);
        }
        if (this.estado != null && !this.estado.equals("") && this.estado.matches("-*[0-9]+")) {
            this.calendario.setEstado(Integer.valueOf(this.estado));
        }
        if (this.tipoPagoCAEV != null && !this.tipoPagoCAEV.equals("")
                && this.tipoPagoCAEV.matches("-*[0-9]+")) {
            this.calendario.setTipoPagoCAEV(Integer.valueOf(this.tipoPagoCAEV));
        }
        if (this.tipoPagoCAMV != null && !this.tipoPagoCAMV.equals("")
                && this.tipoPagoCAMV.matches("-*[0-9]+")) {
            this.calendario.setTipoPagoCAMV(Integer.valueOf(this.tipoPagoCAMV));
        }


        // this.calendario.setEstadoMensajeria(this.estadoMensajeria);
    }

    /**
     * Limpia todos los campos
     * 
     * @param evt
     */
    public void limpiar(final ActionEvent evt)  {
        this.banderaBitacoraConsulta = false;
        // calendario.init();
        this.custodio = "-1";
        this.estado = "-1";
        this.tipoPagoCAEV = "-1";
        this.tipoPagoCAMV = "-1";
        this.divisa = "-1";
        this.tipoValor = "";
        this.emisora = "";
        this.serie = "";
        this.isin = "";
        this.refCustodio = "";
        this.fechaPagoInicio = null;
        this.fechaPagoFin = null;
        this.fechaCorteInicio = null;
        this.fechaPagoFin = null;
        this.fechaXdateInicio = null;
        this.fechaXdateFin = null;
        this.interimDateInicio = null;
        this.interimDateFin = null;
        this.estadoMensajeria = "";

        if (this.resultados != null) {
            this.resultados.getRegistros().clear();
        }
        if (this.paginaVO.getRegistros() != null) {
            this.paginaVO.getRegistros().clear();
        }
        this.paginaVO.setRegistrosXPag(50);
        this.paginaVO.setOffset(0);

        this.setConsultaEjecutada(false);
    }

    /**
     * Obtiene la consulta de Estados
     */
    @SuppressWarnings("unchecked")
    public List<SelectItem> getEstados() {
        if (this.listaEstatus != null && this.listaEstatus.size() > 0) {
            return this.listaEstatus;
        }
        List<EstadoDerechoInt> estados =
                this.divisionInternacionalService.obtieneEstadosDerechoInt();
        List<SelectItem> listaEstados = new ArrayList<SelectItem>();
        if (estados != null) {
            for (EstadoDerechoInt estado : estados) {

                listaEstados
                        .add(new SelectItem(estado.getId().toString(), estado.getDescripcion()));
            }
        } else {
            listaEstados.add(new SelectItem("-2", "VACIO"));
        }
        return listaEstados;
    }

    /**
     * Obtiene la consulta de Estados Mensajeria
     */
    @SuppressWarnings("unchecked")
    public List<SelectItem> getEstadosMensajeria() {
        if (this.listaEstadoMensajeria == null) {
            List<SelectItem> listaEstados = new ArrayList<SelectItem>();
            listaEstados.add(new SelectItem("-1", "Todos"));
            this.listaEstadoMensajeria = listaEstados;
        }
        return this.listaEstadoMensajeria;

    }

    public void updateEstadoMensajeria(final ValueChangeEvent event) {
        this.listaEstadoMensajeria.clear();
        String edo = (String) event.getNewValue();
        List<Control> estados = this.divisionInternacionalService.obtieneEstadosMensajeria(edo);
        List<SelectItem> listaEstados = new ArrayList<SelectItem>();
        listaEstados.add(new SelectItem("-1", "Todos"));
        if (estados != null) {
            for (Control estado : estados) {
                listaEstados.add(new SelectItem(estado.getIdControl().toString(), estado
                        .getDescripcion()));
            }
        }
        this.listaEstadoMensajeria = listaEstados;
    }

    /**
	 * 
	 */
    @SuppressWarnings("unchecked")
    public List<SelectItem> getTiposPagoCAEV() {
        if (this.listaTipoPagoCAEV != null && this.listaTipoPagoCAEV.size() > 0) {
            return this.listaTipoPagoCAEV;
        }
        return this.getTiposPagoMensaje(true);
    }

    @SuppressWarnings("unchecked")
    public List<SelectItem> getTiposPagoCAMV() {
        if (this.listaTipoPagoCAMV != null && this.listaTipoPagoCAMV.size() > 0) {
            return this.listaTipoPagoCAMV;
        }
        return this.getTiposPagoMensaje(false);
    }

    /**
     * getTiposPago
     * 
     * @return
     */
    public List<SelectItem> getTiposPago() {
        return this.getTiposPagoMensaje(null);
    }

    /**
     * obtiene los tipos de mensaje
     */
    private List<SelectItem> getTiposPagoMensaje(final Boolean isCAEV) {
        List<TipoPagoInt> tipos = this.divisionInternacionalService.obtieneTiposPagoInt(isCAEV);
        List<SelectItem> listaPagos = new ArrayList<SelectItem>();
        if (tipos != null) {
            for (TipoPagoInt tp : tipos) {

                listaPagos.add(new SelectItem(tp.getId().toString(), tp.getClavePago()));


            }
        } else {
            listaPagos.add(new SelectItem("-2", "VACIO"));
        }
        return listaPagos;
    }

    private void inicializaTipoPagoCAMV() {
        this.listaTipoPagoCAMV = this.getTiposPagoCAMV();

    }

    private void inicializaTipoPagoCAEV() {
        this.listaTipoPagoCAEV = this.getTiposPagoCAEV();
    }

    private void inicializaDivisa() {
        this.listaDivisa = this.getDivisas();
    }

    /**
     * Obtiene una lista de <code>SelectItem</code> para popular el combo box de eleccion de divisa
     * 
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<SelectItem> getDivisas() {
        if (this.listaDivisa != null && this.listaDivisa.size() > 0) {
            return this.listaDivisa;
        }
        Divisa[] listaDivisas = this.divisaDao.findDivisas();
        List<SelectItem> listaSelectDivisas = new ArrayList<SelectItem>();
        for (int i = 0; i < listaDivisas.length; i++) {
            listaSelectDivisas.add(new SelectItem(listaDivisas[i].getClaveAlfabetica(),
                    listaDivisas[i].getDescripcion()));
        }
        return listaSelectDivisas;
    }

    /**
	 * 
	 */
    public List<SelectItem> getCustodios() {
        if (this.listaCustodios != null && this.listaCustodios.size() > 0) {
            return this.listaCustodios;
        }
        List<Custodio> custodios = this.divisionInternacionalService.obtieneCatalogoCustodios();
        List<SelectItem> listaCustodios = new ArrayList<SelectItem>();
        if (custodios != null) {
            for (Custodio custodio : custodios) {

                listaCustodios.add(new SelectItem(custodio.getId().toString(), custodio
                        .getDescripcion()));

            }
        } else {
            listaCustodios.add(new SelectItem("-2", "VACIO"));
        }
        return listaCustodios;
    }

    /**
     * Inicializa lista de custodios
     */
    private void inicializaCustodios() {
        this.listaCustodios = this.getCustodios();
    }

    /**
     * Inicializa lista de estatus de emisiones
     */
    private void inicializaEstatus() {
        this.listaEstatus = this.getEstados();
    }


    /**
     * @return the consultaEjecutada
     */
    public boolean isConsultaEjecutada() {
        return this.consultaEjecutada;
    }

    /**
     * @param consultaEjecutada the consultaEjecutada to set
     */
    public void setConsultaEjecutada(final boolean consultaEjecutada) {
        this.consultaEjecutada = consultaEjecutada;
    }

    /**
     * @return the listaCustodios
     */
    public List getListaCustodios() {
        return this.listaCustodios;
    }

    /**
     * @param listaCustodios the listaCustodios to set
     */
    public void setListaCustodios(final List listaCustodios) {
        this.listaCustodios = listaCustodios;
    }

    /**
     * @return the divisionInternacionalService
     */
    public DivisionInternacionalService getDivisionInternacionalService() {
        return this.divisionInternacionalService;
    }

    /**
     * @param divisionInternacionalService the divisionInternacionalService to set
     */
    public void setDivisionInternacionalService(
            final DivisionInternacionalService divisionInternacionalService) {
        this.divisionInternacionalService = divisionInternacionalService;
    }

    /**
     * @return the listaEstatus
     */
    public List getListaEstatus() {
        return this.listaEstatus;
    }

    /**
     * @param listaEstatus the listaEstatus to set
     */
    public void setListaEstatus(final List listaEstatus) {
        this.listaEstatus = listaEstatus;
    }

    /**
     * @return the totalPaginas
     */
    @Override
    public int getTotalPaginas() {
        return this.totalPaginas;
    }

    /**
     * @param totalPaginas the totalPaginas to set
     */
    @Override
    public void setTotalPaginas(final int totalPaginas) {
        this.totalPaginas = totalPaginas;
    }

    /**
     * @return the resultados
     */
    public PaginaVO getResultados() {
        return this.resultados;
    }

    /**
     * @param resultados the resultados to set
     */
    public void setResultados(final PaginaVO resultados) {
        this.resultados = resultados;
    }

    /**
     * @return the totalRegistros
     */
    public int getTotalRegistros() {
        return this.totalRegistros;
    }

    /**
     * @param totalRegistros the totalRegistros to set
     */
    public void setTotalRegistros(final int totalRegistros) {
        this.totalRegistros = totalRegistros;
    }

    public boolean isBanderaBitacoraConsulta() {
        return this.banderaBitacoraConsulta;
    }

    public void setBanderaBitacoraConsulta(final boolean banderaBitacoraConsulta) {
        this.banderaBitacoraConsulta = banderaBitacoraConsulta;
    }

    /**
     * @return the 
     */
    public ConsultaHistoricoParamsTO getCalendario() {
        return this.calendario;
    }

    /**
     * @param calendario the calendario to set
     */
    public void setCalendario(final ConsultaHistoricoParamsTO calendario) {
        this.calendario = calendario;
    }

    /**
     * @return the paginaReportes
     */
    public PaginaVO getPaginaReportes() {
        return this.paginaReportes;
    }

    /**
     * @param paginaReportes the paginaReportes to set
     */
    public void setPaginaReportes(final PaginaVO paginaReportes) {
        this.paginaReportes = paginaReportes;
    }

    public String getSelectedDivisa() {
        String resultado = this.getSelected(this.getDivisa(), this.listaDivisa);
        if (resultado != null) {
            return resultado;
        }
        return "TODAS";
    }

    public String getSelectedTipoPagoCAMV() {
        String resultado = this.getSelected(this.getTipoPagoCAMV(), this.listaTipoPagoCAMV);
        if (resultado != null) {
            return resultado;
        }
        return "TODOS";
    }

    public String getSelectedTipoPagoCAEV() {
        String resultado = this.getSelected(this.getTipoPagoCAEV(), this.listaTipoPagoCAEV);
        if (resultado != null) {
            return resultado;
        }
        return "TODOS";
    }

    public String getSelectedEstado() {
        String resultado = this.getSelected(this.getEstado(), this.listaEstatus);
        if (resultado != null) {
            return resultado;
        }
        return "TODOS";
    }

    public String getSelectedCustodio() {
        String resultado = this.getSelected(this.getCustodio(), this.listaCustodios);
        if (resultado != null) {
            return resultado;
        }
        return "TODOS";
    }

    private String getSelected(final String key, final List<SelectItem> lista) {
        String resultado = null;
        for (SelectItem item : lista) {
            if (key != null && item.getValue().equals(key)) {
                resultado = item.getLabel();
            }
        }
        return resultado;
    }

    private String getRequestURL() {
        Object request = FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (request instanceof HttpServletRequest) {
            return ((HttpServletRequest) request).getRequestURL().toString();
        } else {
            return null;
        }
    }

    /**
     * @return the estadoMensajeria
     */
    public String getEstadoMensajeria() {
        return this.estadoMensajeria;
    }

    /**
     * @param estadoMensajeria the estadoMensajeria to set
     */
    public void setEstadoMensajeria(final String estadoMensajeria) {
        this.estadoMensajeria = estadoMensajeria;
    }

    /**
     * @return the listaEstadoMensajeria
     */
    public List<SelectItem> getListaEstadoMensajeria() {
        return this.listaEstadoMensajeria;
    }

    /**
     * @param listaEstadoMensajeria the listaEstadoMensajeria to set
     */
    public void setListaEstadoMensajeria(final List<SelectItem> listaEstadoMensajeria) {
        this.listaEstadoMensajeria = listaEstadoMensajeria;
    }

    /**
     * @return the derechosAutomatizadosDeuda
     */
    public Map<String, String> getDerechosAutomatizadosDeuda() {
        if (this.derechosAutomatizadosDeuda == null || this.derechosAutomatizadosDeuda.size() > 0) {
            this.derechosAutomatizadosDeuda =
                    this.divisionInternacionalService.getDerechosAutomatizadosDeudaMap();
        }
        return this.derechosAutomatizadosDeuda;
    }


    /**
     * @return the referencia
     */
    public String getReferencia() {
        return this.referencia;
    }

    /**
     * @param referencia the referencia to set
     */
    public void setReferencia(final String referencia) {
        this.referencia = referencia;
    }

    /**
     * @return the tipoMensaje
     */
    public String getTipoMensaje() {
        return this.tipoMensaje;
    }

    /**
     * @param tipoMensaje the tipoMensaje to set
     */
    public void setTipoMensaje(final String tipoMensaje) {
        this.tipoMensaje = tipoMensaje;
    }

    /**
     * @return the tipoPagoCAEV
     */
    public String getTipoPagoCAEV() {
        return this.tipoPagoCAEV;
    }

    /**
     * @param tipoPagoCAEV the tipoPagoCAEV to set
     */
    public void setTipoPagoCAEV(final String tipoPagoCAEV) {
        this.tipoPagoCAEV = tipoPagoCAEV;
    }

    /**
     * @return the tipoPagoCAMV
     */
    public String getTipoPagoCAMV() {
        return this.tipoPagoCAMV;
    }

    /**
     * @param tipoPagoCAMV the tipoPagoCAMV to set
     */
    public void setTipoPagoCAMV(final String tipoPagoCAMV) {
        this.tipoPagoCAMV = tipoPagoCAMV;
    }

    /**
     * @return the tipoValor
     */
    public String getTipoValor() {
        return this.tipoValor;
    }

    /**
     * @param tipoValor the tipoValor to set
     */
    public void setTipoValor(final String tipoValor) {
        this.tipoValor = tipoValor;
    }

    /**
     * @return the emisora
     */
    public String getEmisora() {
        return this.emisora;
    }

    /**
     * @param emisora the emisora to set
     */
    public void setEmisora(final String emisora) {
        this.emisora = emisora;
    }

    /**
     * @return the serie
     */
    public String getSerie() {
        return this.serie;
    }

    /**
     * @param serie the serie to set
     */
    public void setSerie(final String serie) {
        this.serie = serie;
    }

    /**
     * @return the isin
     */
    public String getIsin() {
        return this.isin;
    }

    /**
     * @param isin the isin to set
     */
    public void setIsin(final String isin) {
        this.isin = isin;
    }

    /**
     * @return the divisa
     */
    public String getDivisa() {
        return this.divisa;
    }

    /**
     * @param divisa the divisa to set
     */
    public void setDivisa(final String divisa) {
        this.divisa = divisa;
    }

    /**
     * @return the custodio
     */
    public String getCustodio() {
        return this.custodio;
    }

    /**
     * @param custodio the custodio to set
     */
    public void setCustodio(final String custodio) {
        this.custodio = custodio;
    }

    /**
     * @return the estado
     */
    public String getEstado() {
        return this.estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(final String estado) {
        this.estado = estado;
    }

    /**
     * @return the refCustodio
     */
    public String getRefCustodio() {
        return this.refCustodio;
    }

    /**
     * @param refCustodio the refCustodio to set
     */
    public void setRefCustodio(final String refCustodio) {
        this.refCustodio = refCustodio;
    }

    /**
     * @return the fechaPagoInicio
     */
    public Date getFechaPagoInicio() {
        return this.fechaPagoInicio;
    }

    /**
     * @param fechaPagoInicio the fechaPagoInicio to set
     */
    public void setFechaPagoInicio(final Date fechaPagoInicio) {
        this.fechaPagoInicio = fechaPagoInicio;
    }

    /**
     * @return the fechaPagoFin
     */
    public Date getFechaPagoFin() {
        return this.fechaPagoFin;
    }

    /**
     * @param fechaPagoFin the fechaPagoFin to set
     */
    public void setFechaPagoFin(final Date fechaPagoFin) {
        this.fechaPagoFin = fechaPagoFin;
    }

    /**
     * @return the fechaCorteInicio
     */
    public Date getFechaCorteInicio() {
        return this.fechaCorteInicio;
    }

    /**
     * @param fechaCorteInicio the fechaCorteInicio to set
     */
    public void setFechaCorteInicio(final Date fechaCorteInicio) {
        this.fechaCorteInicio = fechaCorteInicio;
    }

    /**
     * @return the fechaCorteFin
     */
    public Date getFechaCorteFin() {
        return this.fechaCorteFin;
    }

    /**
     * @param fechaCorteFin the fechaCorteFin to set
     */
    public void setFechaCorteFin(final Date fechaCorteFin) {
        this.fechaCorteFin = fechaCorteFin;
    }

    /**
     * @return the fechaXdateInicio
     */
    public Date getFechaXdateInicio() {
        return this.fechaXdateInicio;
    }

    /**
     * @param fechaXdateInicio the fechaXdateInicio to set
     */
    public void setFechaXdateInicio(final Date fechaXdateInicio) {
        this.fechaXdateInicio = fechaXdateInicio;
    }

    /**
     * @return the fechaXdateFin
     */
    public Date getFechaXdateFin() {
        return this.fechaXdateFin;
    }

    /**
     * @param fechaXdateFin the fechaXdateFin to set
     */
    public void setFechaXdateFin(final Date fechaXdateFin) {
        this.fechaXdateFin = fechaXdateFin;
    }

    /**
     * @return the interimDateInicio
     */
    public Date getInterimDateInicio() {
        return this.interimDateInicio;
    }

    /**
     * @param interimDateInicio the interimDateInicio to set
     */
    public void setInterimDateInicio(final Date interimDateInicio) {
        this.interimDateInicio = interimDateInicio;
    }

    /**
     * @return the interimDateFin
     */
    public Date getInterimDateFin() {
        return this.interimDateFin;
    }

    /**
     * @param interimDateFin the interimDateFin to set
     */
    public void setInterimDateFin(final Date interimDateFin) {
        this.interimDateFin = interimDateFin;
    }
}
