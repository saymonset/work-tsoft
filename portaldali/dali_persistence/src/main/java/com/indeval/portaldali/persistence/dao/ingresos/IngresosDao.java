/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.dao.ingresos;

import com.bursatec.persistence.dao.BaseDao;
import com.indeval.portaldali.persistence.model.ConsultaIngresos;
import com.indeval.portaldali.persistence.model.Institucion;
import com.indeval.portaldali.persistence.model.Sistema;

/**
 * DAO para el soprote de ingresos
 * 
 * @author Rafael Ibarra
 */
public interface IngresosDao extends BaseDao {

    public Sistema getSistemaPorNombre(String nombre);
    
    public Institucion getInstitucionPorIdFolio(String idFolio);
    
    public ConsultaIngresos getConsultaIngresosPorNombre(String nombre);
    
    public boolean debeDejarBitacoraInstitucion( String idFolio );
    
}
