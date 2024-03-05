/**
 * 2H Software - Bursatec
 * Portal DAli
 */
package com.indeval.portaldali.presentation.util;


import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.support.AbstractPlatformTransactionManager;
import org.springframework.transaction.support.DefaultTransactionStatus;

/**
* Implementación de un transaction manager que no hace uso de transacciones para BD que no soportan transacciones.
*
*@author José Antonio Huizar Moreno
* @version 1.0
*
*/
public class NoTxTransactionManager extends AbstractPlatformTransactionManager {

	private static final long serialVersionUID = 1L;

	/* (non-Javadoc)
	 * @see org.springframework.transaction.support.AbstractPlatformTransactionManager#doBegin(java.lang.Object, org.springframework.transaction.TransactionDefinition)
	 */
	protected void doBegin(Object arg0, TransactionDefinition arg1) throws TransactionException {

	}

	/* (non-Javadoc)
	 * @see org.springframework.transaction.support.AbstractPlatformTransactionManager#doCommit(org.springframework.transaction.support.DefaultTransactionStatus)
	*/
	protected void doCommit(DefaultTransactionStatus arg0) throws TransactionException {

	}

	/* (non-Javadoc)
	 * @see org.springframework.transaction.support.AbstractPlatformTransactionManager#doGetTransaction()
	 */
	protected Object doGetTransaction() throws TransactionException {
		
		return null;
	}

	/* (non-Javadoc)
	 * @see org.springframework.transaction.support.AbstractPlatformTransactionManager#doRollback(org.springframework.transaction.support.DefaultTransactionStatus)
	 */
	protected void doRollback(DefaultTransactionStatus arg0) throws TransactionException {

	}

	
}
