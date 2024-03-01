/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.servicios.vo;

import com.indeval.portaldali.middleware.servicios.modelo.AbstractBaseDTO;
import com.indeval.portaldali.middleware.servicios.modelo.vo.AgenteVO;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import org.springframework.validation.Errors;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
@SuppressWarnings({"unchecked"})
public class FileTransferVO extends AbstractBaseDTO {

    /**
     * Constante de Serializacion
     */
    private static final long serialVersionUID = 1L;

    /**
     * objeto con los datos de la intitucion firmada
     */
    private AgenteVO agenteFirmado;

    /**
     * Nombre del usuario u operador de la institucion
     */
    private String nombreUsuario;

    private String tipoProceso;

    private Map consecProtocolo;

    private boolean soloErrores;

    /**
     * Objeto de paginacion para el resumen
     */
    private PaginaVO paginaVO;

    /**
     * Es para manejar la interrupcion por tiempo
     */
    private Integer offset;

    /**
     * Multidivisas: Nombre del archivo para registrar
     */
    private String nombreArchivo;
    private String[] informacionArchivo;

    private Object objetoReferenciaFinal;

    /**
     * Genner - Para FileTransfer de multidivisas
     *
     * @return
     */
    private List<RegistroExcelMultiDivVO> rowsArchivo;

    private String clvUsuario;

    @Override
    public String toString() {
        return super.toString();
    }

    /**
     * Recorre la informacion del archivo y retorna un String con ella
     *
     * @return String
     */
    public String toStringInformacionArchivo() {
        StringBuffer sb = new StringBuffer("[");
        if (this.getInformacionArchivo() != null) {
            for (int i = 0; i < this.getInformacionArchivo().length; i++) {
                sb.append("[" + this.getInformacionArchivo()[i] + "]");
                if (i < this.getInformacionArchivo().length) {
                    sb.append(" : ");
                }
            }
        }
        sb.append("]");
        return sb.toString();
    }

    /* Setters y Getters */

    /**
     * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
     */
    public void validate(Object arg0, Errors arg1) {

    }

    /**
     * @return the agenteFirmado
     */
    public AgenteVO getAgenteFirmado() {
        return agenteFirmado;
    }

    /**
     * @param agenteFirmado the agenteFirmado to set
     */
    public void setAgenteFirmado(AgenteVO agenteFirmado) {
        this.agenteFirmado = agenteFirmado;
    }

    /**
     * @return the tipoProceso
     */
    public String getTipoProceso() {
        return tipoProceso;
    }

    /**
     * @param tipoProceso the tipoProceso to set
     */
    public void setTipoProceso(String tipoProceso) {
        this.tipoProceso = tipoProceso;
    }

    /**
     * @return the consecProtocolo
     */
    public Map getConsecProtocolo() {
        return consecProtocolo;
    }

    /**
     * @param consecProtocolo the consecProtocolo to set
     */
    public void setConsecProtocolo(Map consecProtocolo) {
        this.consecProtocolo = consecProtocolo;
    }

    /**
     * @return the soloErrores
     */
    public boolean isSoloErrores() {
        return soloErrores;
    }

    /**
     * @param soloErrores the soloErrores to set
     */
    public void setSoloErrores(boolean soloErrores) {
        this.soloErrores = soloErrores;
    }

    /**
     * @return the paginaVO
     */
    public PaginaVO getPaginaVO() {
        return paginaVO;
    }

    /**
     * @param paginaVO the paginaVO to set
     */
    public void setPaginaVO(PaginaVO paginaVO) {
        this.paginaVO = paginaVO;
    }

    /**
     * @return the informacionArchivo
     */
    public String[] getInformacionArchivo() {
        return informacionArchivo;
    }

    /**
     * @param informacionArchivo the informacionArchivo to set
     */
    public void setInformacionArchivo(String[] informacionArchivo) {
        this.informacionArchivo = informacionArchivo;
    }

    /**
     * @return the offset
     */
    public Integer getOffset() {
        return offset;
    }

    /**
     * @param offset the offset to set
     */
    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    /**
     * @return the nombreUsuario
     */
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    /**
     * @param nombreUsuario the nombreUsuario to set
     */
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public List<RegistroExcelMultiDivVO> getRowsArchivo() {
        return rowsArchivo;
    }

    public void setRowsArchivo(List<RegistroExcelMultiDivVO> rowsArchivo) {
        this.rowsArchivo = rowsArchivo;
    }

    public String getClvUsuario() {
        return clvUsuario;
    }

    public void setClvUsuario(String clvUsuario) {
        this.clvUsuario = clvUsuario;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public Object getObjetoReferenciaFinal() {
        return objetoReferenciaFinal;
    }

    public void setObjetoReferenciaFinal(Object objetoReferenciaFinal) {
        this.objetoReferenciaFinal = objetoReferenciaFinal;
    }
}
