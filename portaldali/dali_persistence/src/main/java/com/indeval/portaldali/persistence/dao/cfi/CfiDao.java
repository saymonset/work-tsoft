/**
 * CMMV - Portal Dali
 * Copyrigth (c) 2017 CMMV. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.dao.cfi;

import com.indeval.portaldali.modelo.to.cfi.DetalleCfiTO;

/**
 * Interfaz de datos que define los metodos de consulta para el CFI.
 * 
 * @author Pablo Balderas
 */
public interface CfiDao {

	/**
	 * Consulta el detalle del CFI
	 * @param categoria categoria del cfi
	 * @param grupo grupo del cfi
	 * @param atributoUno atributo uno del cfi
	 * @param atributoDos atributo dos del cfi
	 * @param atributoTres atributo tres del cfi
	 * @param atributoCuatro atributo cuatro del cfi
	 * @return TO con el detalle del CFI
	 */
	DetalleCfiTO findDetalleCfi(String categoria, String grupo, String atributoUno, String atributoDos, String atributoTres, String atributoCuatro);
	
}
