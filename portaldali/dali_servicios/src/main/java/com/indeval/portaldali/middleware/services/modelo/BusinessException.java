/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.modelo;

import java.io.PrintStream;
import java.io.PrintWriter;

import org.springframework.core.NestedRuntimeException;

/**
 * Handy class for wrapping checked Exceptions with a root cause.
 * <p>
 * This time-honored technique is no longer necessary in Java 1.4, which finally
 * provides built-in support for exception nesting. Thus exceptions in
 * applications written to use Java 1.4 need not extend this class. To ease
 * migration, this class mirrors Java 1.4's nested exceptions as closely as
 * possible.
 * <p>
 * Abstract to force the programmer to extend the class. <code>getMessage</code>
 * will include nested exception information; <code>printStackTrace</code> etc
 * will delegate to the wrapped exception, if any.
 * <p>
 * The similarity between this class and the NestedRuntimeException class is
 * unavoidable, as Java forces these two classes to have different superclasses
 * (ah, the inflexibility of concrete inheritance!).
 * <p>
 * As discussed in <a
 * href="http://www.amazon.com/exec/obidos/tg/detail/-/0764543857/">Expert
 * One-On-One J2EE Design and Development</a>, runtime exceptions are often a
 * better alternative to checked exceptions. However, all exceptions should
 * preserve their stack trace, if caused by a lower-level exception.
 * 
 * @author Rod Johnson
 * @author Juergen Hoeller
 * @see #getMessage
 * @see #printStackTrace
 * @see NestedRuntimeException
 */
public class BusinessException extends RuntimeException {

	/** Constante de serializacion */
	private static final long serialVersionUID = 1L;

	/** Root cause of this nested exception */
	private Throwable cause;

	/**
	 * C&oacute;digo de error
	 */
	private String errorCode;

	/**
	 * @param msg
	 *            Mensaje de la excepci&oacute;n.
	 * @param errorCode
	 *            C&oacute;digo de error.
	 */
	public BusinessException(String msg, String errorCode) {
		super(errorCode + " - " + msg);
		this.setErrorCode(errorCode);
	}

	/**
	 * Construct a <code>NestedCheckedException</code> with the specified
	 * detail message.
	 * 
	 * @param msg
	 *            the detail message
	 */
	public BusinessException(String msg) {
		super(msg);
	}

	/**
	 * Construct a <code>NestedCheckedException</code> with the specified
	 * detail message and nested exception.
	 * 
	 * @param msg
	 *            the detail message
	 * @param ex
	 *            the nested exception
	 */
	public BusinessException(String msg, Throwable ex) {
		super(msg);
		this.cause = ex;
	}

	/**
	 * Return the nested cause, or <code>null</code> if none.
	 * 
	 * @return Throwable
	 */
	public Throwable getCause() {
		// Even if you cannot set the cause of this exception other than through
		// the constructor, we check for the cause being "this" here, as the
		// cause
		// could still be set to "this" via reflection: for example, by a
		// remoting
		// deserializer like Hessian's.
		return ((this.cause == this) ? null : this.cause);
	}

	/**
	 * Return the detail message, including the message from the nested
	 * exception if there is one.
	 * 
	 * @return String
	 */
	public String getMessage() {
		if (getCause() == null) {
			return super.getMessage();
		} else {
			return super.getMessage() + "; nested exception is " + getCause().getClass().getName() + ": " + getCause().getMessage();
		}
	}

	/**
	 * Print the composite message and the embedded stack trace to the specified
	 * stream.
	 * 
	 * @param ps
	 *            the print stream
	 */
	public void printStackTrace(PrintStream ps) {
		if (getCause() == null) {
			super.printStackTrace(ps);
		} else {
			ps.println(this);
			getCause().printStackTrace(ps);
		}
	}

	/**
	 * Print the composite message and the embedded stack trace to the specified
	 * print writer.
	 * 
	 * @param pw
	 *            the print writer
	 */
	public void printStackTrace(PrintWriter pw) {
		if (getCause() == null) {
			super.printStackTrace(pw);
		} else {
			pw.println(this);
			getCause().printStackTrace(pw);
		}
	}

	/**
	 * Check whether this exception contains an exception of the given class:
	 * either it is of the given class itself or it contains a nested cause of
	 * the given class.
	 * <p>
	 * Currently just traverses NestedCheckedException causes. Will use the JDK
	 * 1.4 exception cause mechanism once Spring requires JDK 1.4.
	 * 
	 * @param exClass
	 *            the exception class to look for
	 * @return boolean
	 */
	public boolean contains(Class exClass) {
		if (exClass == null) {
			return false;
		}

		Throwable ex = this;

		while (ex != null) {
			if (exClass.isInstance(ex)) {
				return true;
			}

			if (ex instanceof BusinessException) {
				// Cast is necessary on JDK 1.3, where Throwable does not
				// provide a "getCause" method itself.
				ex = ((BusinessException) ex).getCause();
			} else {
				ex = null;
			}
		}

		return false;
	}

	/**
	 * @return El C&oacute;digo de Error
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * @param errorCode
	 *            El C&oacute;digo de Error
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

}
