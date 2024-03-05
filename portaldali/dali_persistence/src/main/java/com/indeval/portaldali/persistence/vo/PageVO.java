/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.vo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class PageVO {

    private Integer offset;
    
    private Integer registrosXPag;
    
    private List registros;
    
    //Este atributo se preve necesario para hacer 
    //uso de este objeto en el metodo count() que 
    //debe implementar el dao
    private Integer totalRegistros;
    
    //Este atributo se utiliza para enviar los totales
    private Map valores = new HashMap();
    
    /**
     * Constructor por defecto
     */
    public PageVO(){
        this.offset = new Integer("0");
        this.registrosXPag = new Integer("0");
        this.totalRegistros = new Integer("0");
    }
    
    /**
     * Constructor por parametros
     * @param registrosXPag
     */
    public PageVO(int registrosXPag){
        this.offset = new Integer("0");
        this.registrosXPag = new Integer(registrosXPag);
        this.totalRegistros = new Integer("0");
    }
    
    /**
     * @return Map
     */
    public Map getValores() {
        return valores;
    }

    /**
     * @param valores
     */
    public void setValores(Map valores) {
        this.valores = valores;
    }

    /**
     * @return Integer
     */
    public Integer getOffset() {
        return offset;
    }

    /**
     * @return Integer
     */
    public Integer getRegistrosXPag() {
        return registrosXPag;
    }

    /**
     * @return Integer
     */
    public Integer getTotalRegistros() {
        return totalRegistros;
    }

    /**
     * @return List
     */
    public List getRegistros() {
        return registros;
    }
    
    /**
     * @param offset
     */
    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    /**
     * @param registrosXPag
     */
    public void setRegistrosXPag(Integer registrosXPag) {
        this.registrosXPag = registrosXPag;
    }

    /**
     * @param totalRegistros
     */
    public void setTotalRegistros(Integer totalRegistros) {
        this.totalRegistros = totalRegistros;
    }

    /**
     * @param registros
     */
    public void setRegistros(List registros) {
        this.registros = registros;
    }
    
}
