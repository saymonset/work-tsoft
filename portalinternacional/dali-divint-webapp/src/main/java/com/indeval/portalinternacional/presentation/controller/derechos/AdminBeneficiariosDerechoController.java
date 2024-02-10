package com.indeval.portalinternacional.presentation.controller.derechos;


import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.services.util.DateUtilService;
import com.indeval.portaldali.middleware.servicios.modelo.vo.AgenteVO;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.AdminCatalogosBenefService;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.ControlDerechosService;
import com.indeval.portalinternacional.middleware.servicios.constantes.Constantes;
import com.indeval.portalinternacional.middleware.servicios.modelo.FactorSimulacionMav;
import com.indeval.portalinternacional.middleware.servicios.modelo.HorarioBeneficiario;
import com.indeval.portalinternacional.middleware.servicios.modelo.VConsultaBeneficiarioDerechosCuenta;
import com.indeval.portalinternacional.middleware.servicios.vo.ConsultaDerechosParam;
import com.indeval.portalinternacional.middleware.servicios.vo.PosicionCuentaVO;
import com.indeval.portalinternacional.middleware.servicios.vo.VConsultaDerechosCapitalVO;
import com.indeval.portalinternacional.presentation.controller.common.ControllerBase;

public class AdminBeneficiariosDerechoController  extends ControllerBase{

	private static final Logger LOG = LoggerFactory.getLogger(AdminBeneficiariosDerechoController.class);
    private ConsultaDerechosParam param;
    private boolean banderaInicio = true;
    private ControlDerechosService derechosService;
    private DateUtilService dateUtilService;
    private VConsultaDerechosCapitalVO derechoSeleccionado;
    private AdminCatalogosBenefService adminCatalogosBenefService;
    private List<AdminBeneficiariosDerecho> reporte;
    private Map<Long, List<BeneficiarioDerecho>> beneficiariosPorcuenta = new HashMap<Long, List<BeneficiarioDerecho>>();
    private Map<Long, List<BeneficiarioDerecho>> beneficiariosPorcuentaReporte = new HashMap<Long, List<BeneficiarioDerecho>>();
    private Map<Long, Object[]> totalXCuenta;
    private Map<Long, Object[]> totalXCuentaReporte;
    private static final int INDICE_POSICION_NO_ASIGNADA = 0;
    private static final int INDICE_POSICION_ASIGNADA = 1;
    private static final int INDICE_MONTO_TOTAL_NO_ASIGNADA = 2;
    private static final int INDICE_MONTO_NO_ASIGNADA = 3;
    private static final int INDICE_MONTO_RETENCION_NO_ASIGNADA = 4;
    private static final int INDICE_MONTO_TOTAL_TITULOS = 5;
    private static final int INDICE_TOTAL_MONTO_BRUTO = 0;
    private static final int INDICE_TOTAL_IMPUESTO_RET = 1;
    private static final int INDICE_TOTAL_MONTO_NETO = 2;
    private long totalPosicionAsignada = 0;
    private long totalPosicionNoAsignada = 0;
    private BigDecimal totalPosiciones = null;
    private long totalPosicionAsignadaRepo = 0;
    private long totalPosicionNoAsignadaRepo = 0;
    private String claveInstitucion;
    private PaginaVO paginaReportes;
    private boolean horarioValido;
    private boolean requiereBeneficiarios;
    private Float porcentajeRetencion;
    private List<Object> lstDummyReporte;
    private BigDecimal[] totalesMontos;
    private BigDecimal[] totalesReportes;
    private final DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    private Map<String,String> parchePorcentajeRetencionXFecha;


    public void obtenerOperaciones(final ActionEvent event) {
        this.ejecutarConsulta();
    }

    public void ejecutarConsultaListener(final ActionEvent event) {
        this.ejecutarConsulta();
    }

    /**
     * Realiza la consulta de cuentas del derecho y sus beneficiarios
     */
    @Override
    public String ejecutarConsulta() {
    	Long tiempoConsulta=System.currentTimeMillis();
        FactorSimulacionMav factorSimulacionMav = null;
        HorarioBeneficiario horarioBeneficiario = null;
        HorarioBeneficiario horarioInst = null;
        AgenteVO agenteVO = null;
        List<PosicionCuenta> lstCuentas = null;
        PosicionCuenta tmp = null;
        Object[] totalesCuenta = null;
        this.totalXCuenta = new HashMap<Long, Object[]>();

        if (this.paginaVO != null && this.paginaVO.getRegistros() != null) {
            this.paginaVO.getRegistros().clear();
        }

        this.totalPosicionAsignada = 0;
        this.totalPosicionNoAsignada = 0;

        LOG.info("\n\n####### Recuperando derecho del request....\n\n");
        if (this.derechoSeleccionado == null) {
            this.derechoSeleccionado = (VConsultaDerechosCapitalVO)
                    FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get(Constantes.KEY_DERECHO_SELECCIONADO);
            this.param = (ConsultaDerechosParam)FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get("parametrosConsulta");
        }

        agenteVO = this.getAgenteFirmado();
        LOG.info("\n\n####### Verificando si la emision requiere beneficiarios....\n\n");
        this.requiereBeneficiarios = this.derechosService.requireBeneficiariosDerechoOptimizado(this.derechoSeleccionado.getEmision(), 
                                                                                                this.derechoSeleccionado.getIdEmision());
        LOG.info("\n\n####### Requiere beneficiarios?[" + this.requiereBeneficiarios + "]\n\n");

        if (this.requiereBeneficiarios) {
            LOG.info("\n\n####### Recuperando el porcentaje de retencion de default para la emision con id [" + 
                     this.derechoSeleccionado.getIdEmision() + "]\n\n");
            this.porcentajeRetencion = 
                this.derechosService.getPorcentajeRetDefaultOptimizado(this.derechoSeleccionado.getEmision(), 
                                                                       this.derechoSeleccionado.getIdEmision());

            if (this.porcentajeRetencion == null) {
                this.porcentajeRetencion = Float.valueOf(0);
            }
            else {
                //Parche para porcentaje de retencion por default por intervalo de fechas
                this.parchaPorcentajeRetencionDefault(this.derechoSeleccionado);
            }
            LOG.info("\n\n####### El porcentaje de retencion es de [" + this.porcentajeRetencion + "]\n\n");

            if (this.isUserInRoll(Constantes.ROL_INT_BEN_INDEVAL) || this.isUserInRoll(Constantes.ROL_INT_BEN_INDEVAL_ADMIN)) {
                LOG.info("\n\n####### El horario es valido ya que es indeval\n\n");
                this.horarioValido = true;
            }
            else {
                LOG.info("\n\n####### Recuperando el horario para la emision....\n\n");
                horarioBeneficiario = 
                    this.derechosService.getHorarioBenefDerechoOptimizado(this.derechoSeleccionado.getEmision(), 
                                                                          this.derechoSeleccionado.getIdEmision());
                if (horarioBeneficiario != null && 
                    this.isHorarioValido(horarioBeneficiario, this.derechoSeleccionado.getFechaCorte(), this.derechoSeleccionado.getFechaPago())) {
                    LOG.info("\n\n####### El horario del custodio es valido...\n\n");
                    this.horarioValido = true;
                }
                else {
                    LOG.info("\n\n####### El horario del custodio es invalido, se checara el horario de la institucion si es que existe...\n\n");
                    horarioInst = 
                        this.derechosService.getHorarioInstitucionOptimizado(this.derechoSeleccionado.getEmision(), 
                                                                             this.derechoSeleccionado.getIdEmision(), 
                                                                             Integer.valueOf(agenteVO.getId()), 
                                                                             agenteVO.getFolio());
                    if (horarioInst != null && 
                        this.isHorarioValido(horarioInst,this.derechoSeleccionado.getFechaCorte(),this.derechoSeleccionado.getFechaPago())) {
                        LOG.debug("el horario de la institucion es valido");
                        this.horarioValido = true;
                    }
                    else {
                        LOG.debug("El horario de la institucion es invalido");
                        this.horarioValido = false;
                    }
                }
            }
        }
        else {
            factorSimulacionMav = this.derechosService.getFactorSimulacion(this.derechoSeleccionado.getIdDerechoCapital());
            if (factorSimulacionMav != null && factorSimulacionMav.getPorcentajeRetencion() != null) {
                this.porcentajeRetencion = factorSimulacionMav.getPorcentajeRetencion();
            }
            else {
                this.porcentajeRetencion = Float.valueOf(0);
            }
            this.horarioValido = true;
        }

        LOG.info("\n\n####### Consultando cuentas para el derecho con id [" + this.derechoSeleccionado.getIdDerechoCapital() + "]\n\n]");
        if (!this.isUserInRoll(Constantes.ROL_INT_BEN_INDEVAL)) {
            LOG.info("\n\n####### Se recuperaran las cuentas relacionadas del usuario logeado...\n\n");
            this.setPaginaVO(this.derechosService.getCuentasDerechoOptimizado(this.derechoSeleccionado.getIdDerechoCapital(),
                                                                    Integer.valueOf(agenteVO.getId()),
                                                                    agenteVO.getFolio(),
                                                                    this.paginaVO));
        }
        else if (StringUtils.isNotBlank(this.claveInstitucion) && 
                this.claveInstitucion.trim().length() == Constantes.SIZE_ID_FOLIO && 
                StringUtils.isNumeric(this.claveInstitucion.substring(0, Constantes.SIZE_ID)) && 
                StringUtils.isNumeric(this.claveInstitucion.substring(Constantes.SIZE_ID, Constantes.SIZE_ID_FOLIO))) {
            LOG.info("\n\n####### Se recuperaran las cuentas relacionadas al usuario de la caja de texto con id y folio...\n\n");
            this.setPaginaVO(this.derechosService.getCuentasDerechoOptimizado(this.derechoSeleccionado.getIdDerechoCapital(),
                                                                    Integer.valueOf(this.claveInstitucion.substring(0, Constantes.SIZE_ID)),
                                                                    this.claveInstitucion.substring(Constantes.SIZE_ID,Constantes.SIZE_ID_FOLIO),
                                                                    this.paginaVO));
        }
        else {
            LOG.info("\n\n####### Se consultaran todas las cuentas relacionadas al derecho...\n\n");
            this.setPaginaVO(this.derechosService.getCuentasDerechoOptimizado(this.derechoSeleccionado.getIdDerechoCapital(), 
                                                                              this.paginaVO));
        }


        if (this.paginaVO != null && this.paginaVO.getRegistros() != null && !this.paginaVO.getRegistros().isEmpty()) {
            LOG.info("\n\n####### Transformando cuentas.....\n\n");
            lstCuentas = new ArrayList<PosicionCuenta>();
            this.beneficiariosPorcuenta = new HashMap<Long, List<BeneficiarioDerecho>>();
            for (PosicionCuentaVO posicionVO : (List<PosicionCuentaVO>) this.paginaVO.getRegistros()) {
                tmp = new PosicionCuenta();
                tmp.transformVO(posicionVO);
                totalesCuenta = new Object[5];
                this.beneficiariosPorcuenta.put(tmp.getIdCuentaNombrada(), null);
                totalesCuenta[INDICE_POSICION_NO_ASIGNADA] = tmp.getPosicionNoAsignada();
                totalesCuenta[INDICE_POSICION_ASIGNADA] = Long.valueOf(0);
                this.totalXCuenta.put(tmp.getIdCuentaNombrada(), totalesCuenta);
                lstCuentas.add(tmp);
            }

            this.paginaVO.getRegistros().clear();
            this.paginaVO.setRegistros(lstCuentas);
            LOG.info("\n\n####### se buscaran los beneficiarios para las cuentas....\n\n");
            this.fillCuentasBeneficiarios(this.beneficiariosPorcuenta.keySet().toArray(new Long[]{}),true);
            this.calculaTotales(false);
            //Se calculan montos totales
            this.totalesMontos = new BigDecimal[3];
            for (Object[] total:this.totalXCuenta.values()) {
                this.calculaMontosTotales(total);
            }
            this.calculaTotalesPosAsignada();
            this.beneficiariosPorcuenta.clear();
            this.totalPosiciones = new BigDecimal(this.totalPosicionAsignada).add(new BigDecimal(this.totalPosicionNoAsignada));
        }
        else {
            LOG.info("No se encontraron cuentas......");
            this.paginaVO.setRegistros(new ArrayList<PosicionCuenta>());
        }

        LOG.info("Fin de consulta tiempo:"+(System.currentTimeMillis()- tiempoConsulta)+" milisegundos");
        return null;
    }


    /**
     * PARCHA EL Porcentaje de retencion por default para que se pueda poner por un cierto rango de fechas
     * Utiliza un mapa que setea porcentajes de retencion por default dependiendo de la fecha de la tabla T_HORARIOS_BENEFICIARIO
    KEY: debe ser el nombre que tenga el custodio en C_CATBIC
    VALUE:[fecha a partir de la cual hacia atras validara fecha_corte]|[porcentaje de retencion]
    si son varias fechas, va delimitado por un ";" y entre fecha y porcentaje por un "|"
    Ejemplo:  key="JP MORGAN" value="05/05/2016|32;02/05/2016|25;30/04/2016|28;"
     *****NOTA IMPORTANTE: PONER FECHAS EN ESTRICTO ORDEN DESCENDENTE es decir, de mayor a menor****
     *el mapa se encuentra en applicationContext.xml
     * @param derecho
     */
    private void parchaPorcentajeRetencionDefault(final VConsultaDerechosCapitalVO derecho) {

        if (this.parchePorcentajeRetencionXFecha != null && derecho != null && derecho.getFechaPago() != null){
            String porcentajeFecha = this.parchePorcentajeRetencionXFecha.get(derecho.getBovedaCustodio());
            if(porcentajeFecha != null){
                String[] fechasPorc = porcentajeFecha.split(";");
                for (String fc : fechasPorc){
                    String[] fp = fc.split("\\|");
                    if(fp != null && fp.length == 2){
                        try {
                            Date menorAFecha= this.df.parse(fp[0]);
                            if(!StringUtils.isNumeric(fp[1])){
                                throw new ParseException("Valor del parche de porcentaje de retencion por fecha No es un numero",0);
                            }
                            if(derecho.getFechaPago().compareTo(menorAFecha) <= 0){
                                this.porcentajeRetencion= Float.valueOf(fp[1]);
                            }
                        } catch (ParseException e) {
                            LOG.error("NO SE PUDO PARSEAR UNA FECHA PARA CAMBIO DE PORCENTAJE RETENCION CAPITALES idDerechoCapital:"+derecho.getIdDerechoCapital(),e);

                        }
                    }
                }
            }
        }

    }

    /**
     * 
     * @param lstCuentas, la lista de cuenta.
     * @param beneficiariosPorcuenta La lista de beneficiarios para la cuenta
     * @return lista de beneficiarios y el desgloce de la cuenta.
     */
    private List<AdminBeneficiariosDerecho> llenarReporte(final List<PosicionCuenta> lstCuentas, final Map<Long, List<BeneficiarioDerecho>> beneficiariosPorcuenta2) {

        List<AdminBeneficiariosDerecho> resultados = new ArrayList<AdminBeneficiariosDerecho>();
        this.totalesReportes = new BigDecimal[6];
        AdminBeneficiariosDerecho reporte = null;
        Long posicionTmp = null;

        this.totalesReportes[INDICE_POSICION_NO_ASIGNADA] =  BigDecimal.ZERO;
        this.totalesReportes[INDICE_POSICION_ASIGNADA] =  BigDecimal.ZERO;
        this.totalesReportes[INDICE_MONTO_TOTAL_NO_ASIGNADA] =  BigDecimal.ZERO;
        this.totalesReportes[INDICE_MONTO_RETENCION_NO_ASIGNADA] =  BigDecimal.ZERO;
        this.totalesReportes[INDICE_MONTO_NO_ASIGNADA] =  BigDecimal.ZERO;
        this.totalesReportes[INDICE_MONTO_TOTAL_TITULOS] =  BigDecimal.ZERO;

        for (PosicionCuenta posicionCuenta : lstCuentas) {

            List<BeneficiarioDerecho> listBenef = beneficiariosPorcuenta2.get(posicionCuenta.getIdCuentaNombrada());

            if(listBenef != null && !listBenef.isEmpty() ){

                for (BeneficiarioDerecho beneficiario : listBenef) {

                    reporte = new AdminBeneficiariosDerecho();

                    reporte.setClaveInstitucion(posicionCuenta.getClaveInstitucion());
                    reporte.setNombreInstitucion(posicionCuenta.getNombreInstitucion());
                    reporte.setCuenta(posicionCuenta.getCuenta());
                    reporte.setAdpNumber(beneficiario.getAdpNumber());
                    reporte.setUoi(beneficiario.getUoi());
                    reporte.setNombre(beneficiario.getNombre());
                    reporte.setDireccion(beneficiario.getDireccion());
                    reporte.setCountry(beneficiario.getCountry());
                    reporte.setRfc(beneficiario.getRfc());
                    reporte.setPorcentajeRetencion(beneficiario.getPorcentajeRetencion());
                    reporte.setPosicion( beneficiario.getPosicion());
                    reporte.setMonto(beneficiario.getMonto());
                    reporte.setMontoRetenido(beneficiario.getMontoRetenido());
                    reporte.setMontoPagado(beneficiario.getMontoPagado());
                    reporte.setTipoFormato(beneficiario.getTipoFormato());
                    reporte.setTipoBeneficiario(beneficiario.getTipoBeneficiario());

                    this.totalesReportes[INDICE_MONTO_NO_ASIGNADA] = this.totalesReportes[INDICE_MONTO_NO_ASIGNADA].add(reporte.getMonto()!= null ? reporte.getMonto() :BigDecimal.ZERO);
                    this.totalesReportes[INDICE_MONTO_RETENCION_NO_ASIGNADA] = this.totalesReportes[INDICE_MONTO_RETENCION_NO_ASIGNADA].add(reporte.getMontoRetenido()!= null ? reporte.getMontoRetenido() :BigDecimal.ZERO);
                    this.totalesReportes[INDICE_MONTO_TOTAL_NO_ASIGNADA] = this.totalesReportes[INDICE_MONTO_TOTAL_NO_ASIGNADA].add(reporte.getMontoPagado()!= null ? reporte.getMontoPagado() :BigDecimal.ZERO);
                    this.totalesReportes[INDICE_POSICION_ASIGNADA] = this.totalesReportes[INDICE_POSICION_ASIGNADA].add(reporte.getPosicionAsignada() != null ? new BigDecimal(reporte.getPosicionAsignada()):BigDecimal.ZERO);
                    this.totalesReportes[INDICE_POSICION_NO_ASIGNADA] =this.totalesReportes[INDICE_POSICION_NO_ASIGNADA].add( reporte.getPosicionNoAsignada()!= null ? new BigDecimal(reporte.getPosicionNoAsignada()):BigDecimal.ZERO );

                    resultados.add(reporte);
                }
                posicionTmp = (Long)this.totalXCuentaReporte.get(posicionCuenta.getIdCuentaNombrada()) [INDICE_POSICION_NO_ASIGNADA];
                //Insertar el Registro de la posicion no asignada, se eliminan los que tienen posicion no asignada igual a cero
                if(posicionTmp != null){
                    reporte = new AdminBeneficiariosDerecho();

                    reporte.setClaveInstitucion(posicionCuenta.getClaveInstitucion());
                    reporte.setNombreInstitucion(posicionCuenta.getNombreInstitucion());
                    reporte.setCuenta(posicionCuenta.getCuenta());
                    reporte.setMonto( (BigDecimal) this.totalXCuentaReporte.get(posicionCuenta.getIdCuentaNombrada()) [INDICE_MONTO_NO_ASIGNADA] );
                    reporte.setMontoRetenido( (BigDecimal) this.totalXCuentaReporte.get(posicionCuenta.getIdCuentaNombrada()) [INDICE_MONTO_RETENCION_NO_ASIGNADA] );
                    reporte.setMontoPagado(  (BigDecimal) this.totalXCuentaReporte.get(posicionCuenta.getIdCuentaNombrada()) [INDICE_MONTO_TOTAL_NO_ASIGNADA] );
                    reporte.setPorcentajeRetencion(this.porcentajeRetencion.doubleValue());

                    reporte.setPosicion( (Long) this.totalXCuentaReporte.get(posicionCuenta.getIdCuentaNombrada()) [INDICE_POSICION_NO_ASIGNADA]);

                    reporte.setPosicionAsignada( (Long) this.totalXCuentaReporte.get(posicionCuenta.getIdCuentaNombrada()) [INDICE_POSICION_ASIGNADA] );
                    reporte.setPosicionNoAsignada((Long) this.totalXCuentaReporte.get(posicionCuenta.getIdCuentaNombrada()) [INDICE_POSICION_NO_ASIGNADA]);

                    this.totalesReportes[INDICE_MONTO_NO_ASIGNADA] = this.totalesReportes[INDICE_MONTO_NO_ASIGNADA].add(reporte.getMonto()!= null ? reporte.getMonto() :BigDecimal.ZERO);
                    this.totalesReportes[INDICE_MONTO_RETENCION_NO_ASIGNADA] = this.totalesReportes[INDICE_MONTO_RETENCION_NO_ASIGNADA].add(reporte.getMontoRetenido()!= null ? reporte.getMontoRetenido() :BigDecimal.ZERO);
                    this.totalesReportes[INDICE_MONTO_TOTAL_NO_ASIGNADA] = this.totalesReportes[INDICE_MONTO_TOTAL_NO_ASIGNADA].add(reporte.getMontoPagado()!= null ? reporte.getMontoPagado() :BigDecimal.ZERO);
                    this.totalesReportes[INDICE_POSICION_ASIGNADA] = this.totalesReportes[INDICE_POSICION_ASIGNADA].add(reporte.getPosicionAsignada() != null ? new BigDecimal(reporte.getPosicionAsignada()):BigDecimal.ZERO);
                    this.totalesReportes[INDICE_POSICION_NO_ASIGNADA] =this.totalesReportes[INDICE_POSICION_NO_ASIGNADA].add( reporte.getPosicionNoAsignada()!= null ? new BigDecimal(reporte.getPosicionNoAsignada()):BigDecimal.ZERO );

                    if(posicionTmp.longValue() > 0){
                        resultados.add(reporte);
                    }
                }
            } else{
                reporte = new AdminBeneficiariosDerecho();
                reporte.setClaveInstitucion(posicionCuenta.getClaveInstitucion());
                reporte.setNombreInstitucion(posicionCuenta.getNombreInstitucion());
                reporte.setCuenta(posicionCuenta.getCuenta());
                reporte.setMonto( (BigDecimal) this.totalXCuentaReporte.get(posicionCuenta.getIdCuentaNombrada()) [INDICE_MONTO_NO_ASIGNADA] );
                reporte.setMontoRetenido( (BigDecimal) this.totalXCuentaReporte.get(posicionCuenta.getIdCuentaNombrada()) [INDICE_MONTO_RETENCION_NO_ASIGNADA] );
                reporte.setMontoPagado(  (BigDecimal) this.totalXCuentaReporte.get(posicionCuenta.getIdCuentaNombrada()) [INDICE_MONTO_TOTAL_NO_ASIGNADA] );
                reporte.setPorcentajeRetencion(this.porcentajeRetencion.doubleValue());
                reporte.setPosicion( posicionCuenta.getPosicionNoAsignada() );
                reporte.setPosicionAsignada(posicionCuenta.getPosicionAsignada());
                reporte.setPosicionNoAsignada(posicionCuenta.getPosicionNoAsignada());

                this.totalesReportes[INDICE_MONTO_NO_ASIGNADA] = this.totalesReportes[INDICE_MONTO_NO_ASIGNADA].add(reporte.getMonto()!= null ? reporte.getMonto() :BigDecimal.ZERO);
                this.totalesReportes[INDICE_MONTO_RETENCION_NO_ASIGNADA] = this.totalesReportes[INDICE_MONTO_RETENCION_NO_ASIGNADA].add(reporte.getMontoRetenido()!= null ? reporte.getMontoRetenido() :BigDecimal.ZERO);
                this.totalesReportes[INDICE_MONTO_TOTAL_NO_ASIGNADA] = this.totalesReportes[INDICE_MONTO_TOTAL_NO_ASIGNADA].add(reporte.getMontoPagado()!= null ? reporte.getMontoPagado() :BigDecimal.ZERO);
                this.totalesReportes[INDICE_POSICION_ASIGNADA] = this.totalesReportes[INDICE_POSICION_ASIGNADA].add(reporte.getPosicionAsignada() != null ? new BigDecimal(reporte.getPosicionAsignada()):BigDecimal.ZERO);
                this.totalesReportes[INDICE_POSICION_NO_ASIGNADA] =this.totalesReportes[INDICE_POSICION_NO_ASIGNADA].add( reporte.getPosicionNoAsignada()!= null ? new BigDecimal(reporte.getPosicionNoAsignada()):BigDecimal.ZERO );

                resultados.add(reporte);
            }

            this.totalesReportes[INDICE_MONTO_TOTAL_TITULOS] = this.totalesReportes[INDICE_POSICION_ASIGNADA].add(this.totalesReportes[INDICE_POSICION_NO_ASIGNADA]);

        }

        return resultados;
    }

    public String getInit() {

        if(this.banderaInicio) {
            this.paginaVO.setRegistrosXPag(20);
            this.banderaInicio = false;
        }

        if(!(isUserInRoll("INT_BEN_INDEVAL")||isUserInRoll("INT_BEN_INDEVAL_ADMIN")))
        		this.claveInstitucion=this.getAgenteFirmado().getId()+this.getAgenteFirmado().getFolio();
        
        this.ejecutarConsulta();

        return null;
    }


    public String toTablaRetencion(){
        FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put(Constantes.KEY_DERECHO_SELECCIONADO, this.derechoSeleccionado);
        FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("parametrosConsulta", this.param);
        return Constantes.TO_CONSULTA_TABLA_RETENCION;
    }

    /**
     * Realiza la eliminacion de beneficiarios seleccionados
     * @param event
     */
    public void eliminaBeneficiario(final ActionEvent event){
        List<PosicionCuenta> lstPosicionesCuenta = null;
        List<BeneficiarioDerecho> listBenef = null;
        if (this.paginaVO != null && !this.paginaVO.getRegistros().isEmpty()) {
            lstPosicionesCuenta = this.paginaVO.getRegistros();
            for(PosicionCuenta posicionCuenta:lstPosicionesCuenta){
                listBenef = this.beneficiariosPorcuenta.get(posicionCuenta.getIdCuentaNombrada());
                if(listBenef != null && !listBenef.isEmpty()){
                    for(BeneficiarioDerecho beneficiarioDerecho : listBenef){
                        if(beneficiarioDerecho.isEliminar()){
                            this.derechosService.deleteBeneficiarioDerecho(beneficiarioDerecho.getIdDerechoBeneficiario());
                        }
                    }
                }
            }
        }
        this.ejecutarConsulta();
    }

    private void fillCuentasBeneficiarios(Long[] idsCuentas, boolean isSimplificado) {
        boolean esDistribucion = false;
        AgenteVO agenteVO = null;
        List<VConsultaBeneficiarioDerechosCuenta> lstBeneficiarios = null;
        List<BeneficiarioDerecho> beneficiariosCuenta = null;
        BeneficiarioDerecho tmp = null;
        Object[] tmpTotales = null;


        if (!this.isUserInRoll(Constantes.ROL_INT_BEN_INDEVAL)) {
            agenteVO = this.getAgenteFirmado();
            LOG.info("\n\n####### Consultando beneficiarios por id folio...\n\n");
            if(isSimplificado) 
           	 	lstBeneficiarios = this.derechosService.getBenefDerechoXCuentaOptimizadoSimplificado(this.derechoSeleccionado.getIdDerechoCapital(), 
                        idsCuentas, Integer.valueOf(agenteVO.getId()), 
                        agenteVO.getFolio());
            else
            	lstBeneficiarios = this.derechosService.getBenefDerechoXCuentaOptimizado(this.derechoSeleccionado.getIdDerechoCapital(), 
                                                                                     idsCuentas, Integer.valueOf(agenteVO.getId()), 
                                                                                     agenteVO.getFolio());
        }
        else {
            LOG.info("\n\n####### Consultando todos los beneficiarios...\n\n");
            if(isSimplificado)
            	lstBeneficiarios = this.derechosService.getBenefDerechoXCuentaOptimizadoSimplificado(this.derechoSeleccionado.getIdDerechoCapital(),idsCuentas,null,null);
            else            
            	lstBeneficiarios = this.derechosService.getBenefDerechoXCuentaOptimizado(this.derechoSeleccionado.getIdDerechoCapital(),idsCuentas);
        }

        LOG.info("\n\n####### Llenando cuentas con los beneficiarios.....\n\n");

        if(lstBeneficiarios!=null) {
	        for (VConsultaBeneficiarioDerechosCuenta derechoBeneficiario : lstBeneficiarios) {
	            tmp = new BeneficiarioDerecho();
	
	            if (this.derechoSeleccionado.getIdTipoDerecho().intValue() == Constantes.ID_DERECHO_DISTRIBUCION && 
	                this.adminCatalogosBenefService.tieneEmisionPorcentajeCeroOptimizado(this.derechoSeleccionado.getIdEmision())) {
	                LOG.info("\n\n####### es distribucion....\n\n");
	                esDistribucion = true;
	            }
	
	            tmp.transformEntityVista(derechoBeneficiario, this.derechoSeleccionado.getProporcion(), esDistribucion);
	
	            if (this.beneficiariosPorcuenta.get(derechoBeneficiario.getIdCuentaNombrada()) == null) {
	                beneficiariosCuenta = new ArrayList<BeneficiarioDerecho>();
	            }
	            else {
	                beneficiariosCuenta = this.beneficiariosPorcuenta.get(derechoBeneficiario.getIdCuentaNombrada());
	            }
	
	            if(isSimplificado) {
		            //Se calculan los totales con el beneficiario actual de la iteracion
		            tmpTotales = this.totalXCuenta.get(derechoBeneficiario.getIdCuentaNombrada());
		            tmpTotales[INDICE_POSICION_NO_ASIGNADA] = ((Long)tmpTotales[INDICE_POSICION_NO_ASIGNADA]).longValue() - tmp.getPosicion().longValue();
		            tmpTotales[INDICE_POSICION_ASIGNADA] =  ((Long)tmpTotales[INDICE_POSICION_ASIGNADA]).longValue() + tmp.getPosicion().longValue();
	            }
	            beneficiariosCuenta.add(tmp);
	            this.beneficiariosPorcuenta.put(derechoBeneficiario.getIdCuentaNombrada(), beneficiariosCuenta);
	        }
        }

        if(isSimplificado) {
	        for (Object[] total:this.totalXCuenta.values()) {
	            this.calculaTotalesDefault(total);
	        }
        }
    }


    public String getDetalleBeneficiariosCuenta() {
    	Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		
		String idCuentaNombrada = params.get("idCuentaNombrada");
		Long [] cuentas=new Long[]{Long.valueOf(idCuentaNombrada)};
		
		fillCuentasBeneficiarios(cuentas,false);
		return null;
    }
    public String emptyDetalleBeneficiariosCuenta() {
    	
    	Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		
		String idCuentaNombrada = params.get("idCuentaNombrada");
		
		this.beneficiariosPorcuenta.remove(Long.parseLong(idCuentaNombrada));
		
		return null;
    	
    }
    private void calculaTotales(final boolean isReporte){

        //Este calculo es para totales de los reportes
        if(isReporte){
            if(this.totalXCuentaReporte != null && !this.totalXCuentaReporte.isEmpty()){
                for(Object[] tmp:this.totalXCuenta.values()){
                    if(tmp[0] != null){
                        this.totalPosicionAsignadaRepo += ((Long)tmp[1]).longValue();
                    }
                    if(tmp[1] != null){
                        this.totalPosicionNoAsignadaRepo += ((Long)tmp[0]).longValue();
                    }
                }
            }
        }else{//Este calculo es para los totales de la pagina
            if(this.totalXCuenta != null && !this.totalXCuenta.isEmpty()){
                for(Object[] tmp:this.totalXCuenta.values()){
                    if(tmp[0] != null){
                        this.totalPosicionAsignada += ((Long)tmp[1]).longValue();
                    }
                    if(tmp[1] != null){
                        this.totalPosicionNoAsignada += ((Long)tmp[0]).longValue();
                    }
                }
            }
        }
    }

    /**
     * Pone los valores de los totales a default
     * @param total
     */
    private void calculaTotalesDefault(final Object[] total){
        BigDecimal monto = null;
        BigDecimal montoRetencion = null;
        BigDecimal montoTotal = null;
        BigDecimal proporcion = null;
        BigDecimal porcRetTemp = null;
        BigDecimal montoFee = null;

        proporcion = new BigDecimal(this.derechoSeleccionado.getProporcion());
        BigDecimal posicionNoAsignada = new BigDecimal((Long)total[INDICE_POSICION_NO_ASIGNADA]);
        //Monto = posicionNoAsignada * proporcion
        monto = posicionNoAsignada.multiply(proporcion).setScale(2, BigDecimal.ROUND_HALF_DOWN);
        //porcentajeDeRetencion = porcentajeRetencionCapturado / 100
        porcRetTemp = new BigDecimal(this.porcentajeRetencion).divide(Constantes.CIEN_BIGDECIMAL, 4, BigDecimal.ROUND_HALF_DOWN);
        if(this.derechoSeleccionado.getFee() != null){
            BigDecimal fee = new BigDecimal(this.derechoSeleccionado.getFee());
            montoFee = posicionNoAsignada.multiply(fee).setScale(2, BigDecimal.ROUND_HALF_DOWN);
            montoRetencion = monto.multiply(porcRetTemp).setScale(2, BigDecimal.ROUND_HALF_DOWN).add(montoFee).setScale(2, BigDecimal.ROUND_HALF_DOWN);
        }
        else {
            //Se agrega la escala y el modo de redondeo.
            montoRetencion = monto.multiply(porcRetTemp).setScale(2, BigDecimal.ROUND_HALF_DOWN);
        }
        montoTotal = monto.subtract(montoRetencion).setScale(2, BigDecimal.ROUND_HALF_DOWN);

        total[INDICE_MONTO_NO_ASIGNADA] = monto;
        total[INDICE_MONTO_RETENCION_NO_ASIGNADA] = montoRetencion;
        total[INDICE_MONTO_TOTAL_NO_ASIGNADA] = montoTotal;
    }

    private void calculaMontosTotales(final Object[] total){
        if(this.totalesMontos[INDICE_TOTAL_MONTO_BRUTO] == null){
            this.totalesMontos[INDICE_TOTAL_MONTO_BRUTO] = (BigDecimal)total[INDICE_MONTO_NO_ASIGNADA];
            this.totalesMontos[INDICE_TOTAL_IMPUESTO_RET] = (BigDecimal)total[INDICE_MONTO_RETENCION_NO_ASIGNADA];
            this.totalesMontos[INDICE_TOTAL_MONTO_NETO] = (BigDecimal)total[INDICE_MONTO_TOTAL_NO_ASIGNADA];
        }else{
            this.totalesMontos[INDICE_TOTAL_MONTO_BRUTO] = this.totalesMontos[INDICE_TOTAL_MONTO_BRUTO].add((BigDecimal)total[INDICE_MONTO_NO_ASIGNADA]);
            this.totalesMontos[INDICE_TOTAL_IMPUESTO_RET] = this.totalesMontos[INDICE_TOTAL_IMPUESTO_RET].add((BigDecimal)total[INDICE_MONTO_RETENCION_NO_ASIGNADA]);
            this.totalesMontos[INDICE_TOTAL_MONTO_NETO] = this.totalesMontos[INDICE_TOTAL_MONTO_NETO].add((BigDecimal)total[INDICE_MONTO_TOTAL_NO_ASIGNADA]);
        }
    }
    //beneficiariosPorcuenta
    private void calculaTotalesPosAsignada(){

        if(this.beneficiariosPorcuenta != null && !this.beneficiariosPorcuenta.isEmpty()){
            for(List<BeneficiarioDerecho> benefXCuenta: this.beneficiariosPorcuenta.values()){
                if(benefXCuenta != null && !benefXCuenta.isEmpty()){
                    for(BeneficiarioDerecho bn:benefXCuenta){
                        if(this.totalesMontos[INDICE_TOTAL_MONTO_BRUTO] == null){
                            this.totalesMontos[INDICE_TOTAL_MONTO_BRUTO] = bn.getMonto();
                            this.totalesMontos[INDICE_TOTAL_IMPUESTO_RET] = bn.getMontoRetenido();
                            this.totalesMontos[INDICE_TOTAL_MONTO_NETO] = bn.getMontoPagado();
                        }else{
                            this.totalesMontos[INDICE_TOTAL_MONTO_BRUTO] = this.totalesMontos[INDICE_TOTAL_MONTO_BRUTO].add(bn.getMonto());
                            this.totalesMontos[INDICE_TOTAL_IMPUESTO_RET] = this.totalesMontos[INDICE_TOTAL_IMPUESTO_RET].add(bn.getMontoRetenido());
                            this.totalesMontos[INDICE_TOTAL_MONTO_NETO] = this.totalesMontos[INDICE_TOTAL_MONTO_NETO].add(bn.getMontoPagado());
                        }
                    }
                }
            }
        }
    }

    /**
     * Realiza la consulta de todos los beneficiarios del derecho agrupados por cuentas
     * @param event
     */
    public void generarReporteXLS(final ActionEvent event) {

        this.ejecutarConsultaReporte();
    }

    /**
     * Realiza la consulta de todos los beneficiarios del derecho agrupados por cuentas
     * @param event
     */
    public void generarReportes(final ActionEvent event) {

        this.ejecutarConsultaReporte();
    }

    private void ejecutarConsultaReporte() {
        this.lstDummyReporte = new ArrayList<Object>();
        this.lstDummyReporte.add(new Object());
        List<PosicionCuenta> lstCuentas = null;
        PosicionCuenta posicicionCuenta = null;
        boolean esDistribucion = false;
        List<VConsultaBeneficiarioDerechosCuenta> lstBeneficiarios = null;
        List<BeneficiarioDerecho> beneficiariosCuenta = null;
        BeneficiarioDerecho tmp = null;
        Long[] idsCuentas = null;
        AgenteVO agenteVO = null;
        PosicionCuentaVO posicion = null;
        List<BeneficiarioDerecho> emptyList = null;
        Object[] totalesCuenta = null;
        Object[] tmpTotales = null;

        this.paginaReportes = new PaginaVO();
        this.paginaReportes.setRegistrosXPag(PaginaVO.TODOS);
        this.paginaReportes.setOffset(0);

        if (!this.isUserInRoll(Constantes.ROL_INT_BEN_INDEVAL)) {
            LOG.info("\n\n####### Se recuperaran las cuentas relacionadas del usuario logeado...\n\n");
            agenteVO = this.getAgenteFirmado();
            this.setPaginaReportes(
                    this.derechosService.getCuentasDerechoOptimizado(this.derechoSeleccionado.getIdDerechoCapital(),
                                                                     Integer.valueOf(agenteVO.getId()),
                                                                     agenteVO.getFolio(),
                                                                     this.paginaReportes));
        }
        else if (StringUtils.isNotBlank(this.claveInstitucion) && this.claveInstitucion.trim().length() == Constantes.SIZE_ID_FOLIO && 
                 StringUtils.isNumeric(this.claveInstitucion.substring(0, Constantes.SIZE_ID)) && 
                 StringUtils.isNumeric(this.claveInstitucion.substring(Constantes.SIZE_ID, Constantes.SIZE_ID_FOLIO))) {
            LOG.info("\n\n####### Se recuperaran las cuentas relacionadas al usuario de la caja de texto con id y folio...\n\n");
            this.setPaginaReportes(
                    this.derechosService.getCuentasDerechoOptimizado(this.derechoSeleccionado.getIdDerechoCapital(),
                                         Integer.valueOf(this.claveInstitucion.substring(0, Constantes.SIZE_ID)),
                                         this.claveInstitucion.substring(Constantes.SIZE_ID,Constantes.SIZE_ID_FOLIO),
                                         this.paginaReportes));
        }
        else {
            LOG.info("\n\n####### Se consultaran todas las cuentas relacionadas al derecho...\n\n");
            this.setPaginaReportes(this.derechosService.getCuentasDerechoOptimizado(this.derechoSeleccionado.getIdDerechoCapital(), 
                                                                                    this.paginaReportes));
        }

        lstCuentas = new ArrayList<PosicionCuenta>();
        this.totalXCuentaReporte = new HashMap<Long, Object[]>();
        if (this.paginaReportes.getRegistros() != null && !this.paginaReportes.getRegistros().isEmpty()) {
            this.beneficiariosPorcuentaReporte = new HashMap<Long, List<BeneficiarioDerecho>>();
            for (int i = 0;i < this.paginaReportes.getRegistros().size();i++) {
                posicion = (PosicionCuentaVO) this.paginaReportes.getRegistros().get(i);
                posicicionCuenta = new PosicionCuenta();
                posicicionCuenta.transformVO(posicion);
                lstCuentas.add(posicicionCuenta);
                emptyList = new ArrayList<BeneficiarioDerecho>();
                this.beneficiariosPorcuentaReporte.put(posicion.getIdCuentaNombrada(), emptyList);

                totalesCuenta = new Object[5];
                totalesCuenta[INDICE_POSICION_NO_ASIGNADA] = posicicionCuenta.getPosicionNoAsignada();
                totalesCuenta[INDICE_POSICION_ASIGNADA] = Long.valueOf(0);
                this.totalXCuentaReporte.put(posicicionCuenta.getIdCuentaNombrada(), totalesCuenta);
            }

            idsCuentas =  this.beneficiariosPorcuentaReporte.keySet().toArray(new Long[]{});

            if (!this.isUserInRoll(Constantes.ROL_INT_BEN_INDEVAL)) {
                agenteVO = this.getAgenteFirmado();
                LOG.info("\n\n####### Consultando beneficiarios por id folio....\n\n");
                lstBeneficiarios = this.derechosService.getBenefDerechoXCuentaOptimizado(this.derechoSeleccionado.getIdDerechoCapital(), 
                                                                                         idsCuentas, Integer.valueOf(agenteVO.getId()), 
                                                                                         agenteVO.getFolio());
            }
            else {
                LOG.info("\n\n####### Consultando todos los beneficiarios...\n\n");
                lstBeneficiarios = this.derechosService.getBenefDerechoXCuentaOptimizado(this.derechoSeleccionado.getIdDerechoCapital(), 
                                                                                         idsCuentas);
            }
            for (VConsultaBeneficiarioDerechosCuenta derechoBeneficiario : lstBeneficiarios) {
                tmp = new BeneficiarioDerecho();

                if (this.derechoSeleccionado.getIdTipoDerecho().intValue() == Constantes.ID_DERECHO_DISTRIBUCION && 
                    this.adminCatalogosBenefService.tieneEmisionPorcentajeCeroOptimizado(this.derechoSeleccionado.getIdEmision())) {
                    esDistribucion = true;
                }
                tmp.transformEntityVista(derechoBeneficiario, this.derechoSeleccionado.getProporcion(),esDistribucion);
                beneficiariosCuenta = this.beneficiariosPorcuentaReporte.get(derechoBeneficiario.getIdCuentaNombrada());
                beneficiariosCuenta.add(tmp);

                tmpTotales = this.totalXCuentaReporte.get(derechoBeneficiario.getIdCuentaNombrada());
                tmpTotales[INDICE_POSICION_NO_ASIGNADA] = ((Long)tmpTotales[INDICE_POSICION_NO_ASIGNADA]).longValue() - tmp.getPosicion().longValue();
                tmpTotales[INDICE_POSICION_ASIGNADA] =  ((Long)tmpTotales[INDICE_POSICION_ASIGNADA]).longValue() + tmp.getPosicion().longValue();

                this.beneficiariosPorcuentaReporte.put(derechoBeneficiario.getIdCuentaNombrada(), beneficiariosCuenta);
            }
        }
        this.calculaTotales(true);
        for (Object[] total:this.totalXCuentaReporte.values()) {
            this.calculaTotalesDefault(total);
        }
        this.paginaReportes.getRegistros().clear();
        this.paginaReportes.setRegistros(lstCuentas);

        this.reporte = this.llenarReporte(lstCuentas, this.beneficiariosPorcuentaReporte);
    }



    /**
     * Verifica si el horario es valido para agregar beneficiarios y habilitar la liga en la pagina
     * @param beneficiario
     * @param fechaCorte
     * @param fechaPago
     * @return
     */
    private boolean isHorarioValido(final HorarioBeneficiario beneficiario,final Date fechaCorte,final Date fechaPago){
        Calendar fechaActual = Calendar.getInstance();
        fechaActual.setTime(this.dateUtilService.getCurrentDate());
        Calendar fechaLimite = Calendar.getInstance();
        if(beneficiario.getEsDespuesFechaCorte()){
            LOG.debug("Validando horario con fecha de corte");
            fechaLimite.setTime(this.dateUtilService.agregarDiasHabiles(fechaCorte, beneficiario.getDias().intValue()));
        }else{
            LOG.debug("Validando horario con fecha de pago");
            fechaLimite.setTime(this.dateUtilService.agregarDiasHabiles(fechaPago, beneficiario.getDias().intValue()*-1));
        }
        fechaLimite.set(Calendar.HOUR_OF_DAY,beneficiario.getHora().intValue());
        fechaLimite.set(Calendar.MINUTE, beneficiario.getMinuto().intValue());

        LOG.debug("fecha actual ["+fechaActual.getTime()+"]");
        LOG.debug("fecha limite ["+fechaLimite.getTime()+"]");

        return fechaActual.before(fechaLimite);
    }

    public String getClaveFolio(){
        return this.getAgenteFirmado().getId()+this.getAgenteFirmado().getFolio();
    }

    public String regresar(){
        FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("parametrosConsulta", this.param);
        return "consultaDerechos";
    }

    public boolean isBanderaInicio() {
        return this.banderaInicio;
    }

    public void setBanderaInicio(final boolean banderaInicio) {
        this.banderaInicio = banderaInicio;
    }

    public void setDerechosService(final ControlDerechosService derechosService) {
        this.derechosService = derechosService;
    }

    public VConsultaDerechosCapitalVO getDerechoSeleccionado() {
        return this.derechoSeleccionado;
    }

    public void setDerechoSeleccionado(final VConsultaDerechosCapitalVO derechoSeleccionado) {
        this.derechoSeleccionado = derechoSeleccionado;
    }

    public Map<Long, List<BeneficiarioDerecho>> getBeneficiariosPorcuenta() {
        return this.beneficiariosPorcuenta;
    }

    public void setBeneficiariosPorcuenta(
            final Map<Long, List<BeneficiarioDerecho>> beneficiariosPorcuenta) {
        this.beneficiariosPorcuenta = beneficiariosPorcuenta;
    }

    public Map<Long, Object[]> getTotalXCuenta() {
        return this.totalXCuenta;
    }

    public void setTotalXCuenta(final Map<Long, Object[]> totalXCuenta) {
        this.totalXCuenta = totalXCuenta;
    }

    public Long getTotalPosicionAsignada() {
        return this.totalPosicionAsignada;
    }

    public void setTotalPosicionAsignada(final Long totalPosicionAsignada) {
        this.totalPosicionAsignada = totalPosicionAsignada;
    }

    public long getTotalPosicionNoAsignada() {
        return this.totalPosicionNoAsignada;
    }

    public void setTotalPosicionNoAsignada(final long totalPosicionNoAsignada) {
        this.totalPosicionNoAsignada = totalPosicionNoAsignada;
    }

    public String getClaveInstitucion() {
        return this.claveInstitucion;
    }

    public void setClaveInstitucion(final String claveInstitucion) {
        this.claveInstitucion = claveInstitucion;
    }

    public PaginaVO getPaginaReportes() {
        return this.paginaReportes;
    }

    public void setPaginaReportes(final PaginaVO paginaReportes) {
        this.paginaReportes = paginaReportes;
    }

    public boolean isHorarioValido() {
        return this.horarioValido;
    }

    public void setHorarioValido(final boolean horarioValido) {
        this.horarioValido = horarioValido;
    }

    public boolean isRequiereBeneficiarios() {
        return this.requiereBeneficiarios;
    }

    public Float getPorcentajeRetencion() {
        return this.porcentajeRetencion;
    }

    public String getPorcentajeRetencionAsString() {
        return Constantes.DECIMAL_FORMAT_PORCENTAJE_VIEW.format(this.porcentajeRetencion.floatValue())+" %";
    }

    public void setDateUtilService(final DateUtilService dateUtilService) {
        this.dateUtilService = dateUtilService;
    }

    public Map<Long, List<BeneficiarioDerecho>> getBeneficiariosPorcuentaReporte() {
        return this.beneficiariosPorcuentaReporte;
    }

    public Map<Long, Object[]> getTotalXCuentaReporte() {
        return this.totalXCuentaReporte;
    }

    public long getTotalPosicionAsignadaRepo() {
        return this.totalPosicionAsignadaRepo;
    }

    public long getTotalPosicionNoAsignadaRepo() {
        return this.totalPosicionNoAsignadaRepo;
    }

    public List<Object> getLstDummyReporte() {
        return this.lstDummyReporte;
    }

    public void setAdminCatalogosBenefService(
            final AdminCatalogosBenefService adminCatalogosBenefService) {
        this.adminCatalogosBenefService = adminCatalogosBenefService;
    }

    /**
     * @return the reporte
     */
    public List<AdminBeneficiariosDerecho> getReporte() {
        return this.reporte;
    }

    /**
     * @param reporte the reporte to set
     */
    public void setReporte(final List<AdminBeneficiariosDerecho> reporte) {
        this.reporte = reporte;
    }




    /**
     * @return the totalesReportes
     */
    public BigDecimal[] getTotalesReportes() {
        return this.totalesReportes;
    }

    /**
     * @param totalesReportes the totalesReportes to set
     */
    public void setTotalesReportes(final BigDecimal[] totalesReportes) {
        this.totalesReportes = totalesReportes;
    }

    public BigDecimal[] getTotalesMontos() {
        return this.totalesMontos;
    }

    public BigDecimal getTotalPosiciones() {
        return this.totalPosiciones;
    }

    /** @return this.parchePorcentajeRetencionXFecha */
    public Map<String,String> getParchePorcentajeRetencionXFecha() {
        return this.parchePorcentajeRetencionXFecha;
    }

    /** @param parchePorcentajeRetencionXFecha to be set in this.parchePorcentajeRetencionXFecha */
    public void setParchePorcentajeRetencionXFecha(final Map<String,String> parchePorcentajeRetencionXFecha) {
        this.parchePorcentajeRetencionXFecha = parchePorcentajeRetencionXFecha;
    }



}
