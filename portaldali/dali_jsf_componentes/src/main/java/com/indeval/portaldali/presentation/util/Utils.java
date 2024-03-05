// Decompiled by DJ v3.9.9.91 Copyright 2005 Atanas Neshkov  Date: 22/08/2006 16:15:47
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) fieldsfirst nonlb space 
// Source File Name:   Utils.java

package com.indeval.portaldali.presentation.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.StringTokenizer;

public class Utils {

	public static final String BR = System.getProperty("line.separator");

	public static final String ALIGN_LEFT = "LEFT";
	public static final String ALIGN_RIGHT = "RIGHT";
	public static final String ALIGN_CENTER = "CENTER";
	
	public static final String BRWin = "\r\n";

	public static final String HTML_BR = "<BR>";

	public static final String spc = " ";

	public static String dquote(String s) {
		StringBuffer sb = new StringBuffer();
		sb.append("\"");
		sb.append(s);
		sb.append("\"");
		return sb.toString();
	}

	public static String squote(String s) {
		StringBuffer sb = new StringBuffer();
		sb.append("'");
		sb.append(s);
		sb.append("'");
		return sb.toString();
	}

	public static String resolveScientificNotation(String valor) {

		int posicion = valor.indexOf("E");
		if (posicion < 0) {
			return valor;
		}
		String base = valor.substring(0, posicion);
		String signoNumero = base.substring(0, 1);
		if (signoNumero.equals("-")) {
			base = base.substring(1);
		} else {
			signoNumero = "";
		}
		String signo = valor.substring(posicion + 1, posicion + 2);
		String magnitud = valor.substring(posicion + 2);

		int posDecimals = base.indexOf(".");

		String parteEntera = "";
		String decimal = "";

		if (posDecimals < 0) {
			parteEntera = base;
		} else {
			parteEntera = base.substring(0, posDecimals);
			decimal = base.substring(posDecimals + 1);
		}

		int numMagnitud = Integer.parseInt(magnitud);

		if (signo.equals("+")) {
			while (decimal.length() <= numMagnitud) {
				decimal += "0";
			}
			parteEntera = parteEntera + decimal.substring(0, numMagnitud);
			decimal = decimal.substring(numMagnitud);
		}

		if (signo.equals("-")) {
			while (parteEntera.length() <= numMagnitud) {
				parteEntera = "0" + parteEntera;
			}
			decimal = parteEntera.substring(parteEntera.length() - numMagnitud,
					parteEntera.length())
					+ decimal;
			parteEntera = parteEntera.substring(0, parteEntera.length()
					- numMagnitud);
		}

		return signoNumero + parteEntera + "." + decimal;
	}

	public static String getStringValue(Object value, String pattern, Integer size, String align) {
		if (align == null || align.trim().length() == 0) {
			align = ALIGN_LEFT;
		}
		String stValue = getFormatedValue(value, pattern);

		if (stValue.length() > size.intValue()) {
			stValue = stValue.substring(0, size.intValue());
		} else {
			boolean left = false;
			while (stValue.length() < size.intValue()) {
				if (align.equals(ALIGN_LEFT)) {
					stValue += " ";
				} else if (align.equals(ALIGN_RIGHT)) {
					stValue = " " + stValue;
				} else {
					if (left) {
						stValue = stValue + " ";
					} else {
						stValue = " " + stValue;
					}
					left = !left;
				}
			}
		}
		return stValue;
	}

	public static String getFormatedValue(Object value, String pattern) {
		Format formater = null;
		if (value == null) {
			return "";
		} else if (pattern == null || pattern.trim().length() == 0) {
			return value.toString();
		} else if (value instanceof java.lang.Number) {
			formater = new DecimalFormat(pattern);
			return formater.format(value);
		} else if (value instanceof java.util.Date) {
			formater = new SimpleDateFormat(pattern);
			return formater.format(value);
		}
		return value.toString();
	}

	/**
	 * Construye el nombre del getter correspondiente al atributo que se
	 * proporciona como parametro
	 * 
	 * @param attributeName:
	 *            Es el nombre del atributo
	 * @return El nombre del getter corespondiene al atributo
	 */

	public static String getGetterName(String attributeName) {
		StringBuffer getterName = new StringBuffer();
		getterName.append("get");
		if (attributeName.length() > 0) {
			getterName.append(Character.toUpperCase(attributeName.charAt(0)));
		}
		if (attributeName.length() > 1) {
			getterName.append(attributeName.substring(1));
		}
		return getterName.toString();
	}

	/**
	 * Recupera el valor del atributo representado por el parametro "arrtibute"
	 * perteneciente al javaBean "javaBean"
	 * 
	 * @param attributeName:
	 *            Es el nombre del atributo
	 * @param javaBean:
	 *            Es el javaBean del cual se extraera el valor
	 * @return El valor del atributo como un Object<br>
	 *         <i><b>NOTA:</b> El atributo no puede ser un tipo de dato
	 *         primitivo</i>
	 */

	public static Object getValue(String attributeName, Object javaBean)
			throws NoSuchMethodException, InvocationTargetException,
			IllegalAccessException {
		StringTokenizer st = new StringTokenizer(attributeName, ".");
		Object valor = javaBean;
		while (st.hasMoreTokens()) {
			Method method = valor.getClass().getMethod(
					getGetterName(st.nextToken()), null);
			valor = method.invoke(valor, null);
		}
		return valor;
	}

	
	
	
}