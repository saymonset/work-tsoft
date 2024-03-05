package com.indeval.portaldali.vo;

import java.util.Map;
import java.util.Set;

public class CaptOpsConfig {

    private Map tiposOperaciones;
    private Set tiposOperacionesFirmadas;
    private CaptOpsFieldConfigVO def;
    
    public CaptOpsConfig(){
        def =   new CaptOpsFieldConfigVO();
        def.setReadonly(Boolean.FALSE);
        def.setRendered(Boolean.TRUE);
    }
    
    public CaptOpsFieldConfigVO getConfig( String tipoOp, String md_fv, String fieldId ){
        String mapMD_FV = (String)tiposOperaciones.get(tipoOp + ":"+ md_fv + ":" + fieldId);
        if( mapMD_FV == null ){
            return def;
        }
        String[] mapa = mapMD_FV.split(",");
        CaptOpsFieldConfigVO config = new CaptOpsFieldConfigVO();
        config.setReadonly(new Boolean(mapa[0]));
        config.setRendered(new Boolean(mapa[1]));
        return config;
    }
    
    
    private CaptOpsFieldConfigVO getDefaultCaptOpsFieldConfigVO(){ 
        return def;
    }

    public Map getTiposOperaciones() {
        return tiposOperaciones;
    }

    public void setTiposOperaciones(Map tiposOperaciones) {
        this.tiposOperaciones = tiposOperaciones;
    }

    public Set getTiposOperacionesFirmadas() {
        return tiposOperacionesFirmadas;
    }

    public void setTiposOperacionesFirmadas(Set tiposOperacionesFirmadas) {
        this.tiposOperacionesFirmadas = tiposOperacionesFirmadas;
    }
}
