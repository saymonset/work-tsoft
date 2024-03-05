package com.indeval.portaldali.persistence.dao.util;

import java.util.Date;
/**
 * 
 * @author JAPUCH
 *
 */
public interface FechaDao {
	public Date getCurrentDate();
	public Date getFullCurrentDate();
	public Date getLimiteInferior();
	public Date getLimiteSuperior();
	public Date getLimiteInferiorHabiles();
	public Date getLimiteSuperiorHabiles();

}
