package com.indeval.portaldali.persistence.util;

import java.io.Serializable;
import java.util.List;

import org.hibernate.FetchMode;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.ResultTransformer;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.indeval.portaldali.persistence.vo.PageVO;

/**
 * @author <a href="mailto:ivan.kazakov@2hsoftware.com.mx">Ivan Kazakov</a>
 *
 */
@SuppressWarnings({"unchecked"})
public class PageableDetachedCriteriaDali extends DetachedCriteria implements Serializable {
	public static final long serialVersionUID = (long)1;
	private DetachedCriteria selectCriteria;
	private DetachedCriteria countCriteria;

	private PageableDetachedCriteriaDali(Class criteriaClass) {
		super(criteriaClass.getName());
		this.selectCriteria = DetachedCriteria.forClass(criteriaClass);
		this.countCriteria = DetachedCriteria.forClass(criteriaClass).setProjection(Projections.rowCount());
	}
	
	public static final PageableDetachedCriteriaDali createPageableDetachedCriteria(Class criteriaClass) {
		return (new PageableDetachedCriteriaDali(criteriaClass));
	}
	
	public PageableDetachedCriteriaDali add(Criterion arg0) {
		this.selectCriteria.add(arg0);
		this.countCriteria.add(arg0);
		return (this);
	}
	
	public PageableDetachedCriteriaDali addOrder(Order arg0) {
		this.selectCriteria.addOrder(arg0);
//		this.countCriteria.addOrder(arg0);
		return (this);
	}
	
	public PageableDetachedCriteriaDali createAlias(String arg0, String arg1) {
		this.selectCriteria.createAlias(arg0, arg1);
		this.countCriteria.createAlias(arg0, arg1);
		return (this);
	}
	
	public PageableDetachedCriteriaDali createCriteria(String arg0) {
		this.selectCriteria.createCriteria(arg0);
		this.countCriteria.createCriteria(arg0);
		return (this);
	}
	
	public PageableDetachedCriteriaDali createCriteria(String arg0, String arg1) {
		this.selectCriteria.createCriteria(arg0,arg1);
		this.countCriteria.createCriteria(arg0, arg1);
		return (this);
	}
	
	public String getAlias() {
		return (selectCriteria.getAlias());
	}
	
	public PageableDetachedCriteriaDali setFetchMode(String arg0, FetchMode arg1) throws HibernateException {
		this.selectCriteria.setFetchMode(arg0, arg1);
		this.countCriteria.setFetchMode(arg0, arg1);
		return (this);
	}
	
	public PageableDetachedCriteriaDali setProjection(Projection arg0) {
		this.selectCriteria.setProjection(arg0);
		return (this);
	}
	
	public PageableDetachedCriteriaDali setResultTransformer(ResultTransformer arg0) {
		this.selectCriteria.setResultTransformer(arg0);
		return (this);
	}
	
	public PageVO pageResult(PageVO pageVO, HibernateTemplate hibernateTemplate) {
		
		List<Integer> countResult = hibernateTemplate.findByCriteria(countCriteria);
		if ((countResult != null) && (!countResult.isEmpty())) {
			pageVO.setTotalRegistros(countResult.get(0));
			if(countResult.get(0).intValue() > 0){
			    pageVO.setRegistros(hibernateTemplate.findByCriteria(
			            selectCriteria,pageVO.getOffset().intValue(),pageVO.getRegistrosXPag().intValue()));    
			}
			else{
			    pageVO.setRegistros(null);    
			}
			
		}
		return (pageVO);
	}


}
