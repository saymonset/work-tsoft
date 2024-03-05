/*
 * Copyright (c) 2006 Bursatec. All Rights Reserved
 */
package com.indeval.portaldali.presentation.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import javax.faces.model.SelectItem;

/**
 * <p>
 * Realiza transformacion de objetos de dominio a objetos SelectItem para la
 * implementacion de myfaces Fecha de creaci&oacute;n: 25/Ago/2006 Copyright (c) 
 * Indeval
 * </p>
 * 
 * @author José Alejandro Aguilar Puch.
 */

public class VO2SelectItem {

    
    /**
     * Transforma un javaBean en un javax.faces.model.SelectItem por medio del
     * mecanismo de reflexion de java
     *            <i><B>Nota:</B> Se debe aplicar el converter "com.indeval.Serializable" al componente que se asige el SelectItem</i>
     *            
     * 
     * @param bean:
     *            el javaBean que sera transformado en SelectItem
     * @param labelAttribute:
     *            es el nombre del atributo del javaBean object que sera
     *            asignado como label al SelectItem<br>
     *            <i><B>Nota:</B> Es indispensable que se tenga definido un
     *            getter para esta propiedad</I>
     * @return El SelectItem que represta a el javaBean original
     */

    public static SelectItem encode(Object bean, String labelAttribute) throws NoSuchMethodException,
            InvocationTargetException, IllegalAccessException {
        SelectItem si = new SelectItem();
        si.setDescription(getValue(labelAttribute, bean).toString());
        si.setLabel(getValue(labelAttribute, bean).toString());
        si.setValue(bean);
        return si;
    }

    /**
     * Transforma una lista de javaBeans en un array de
     * javax.faces.model.SelectItem por medio del mecanismo de reflexion de java
     *  <i><B>Nota:</B> Se debe aplicar el converter "com.indeval.Serializable" al componente que se asige el SelectItem</i>
     * 
     * @param lista:
     *            Es la lista de javaBeans que seran transformados en SelectItem
     * @param labelAttribute:
     *            es el nombre del atributo del javaBean object que sera
     *            asignado como label al SelectItem<br>
     *            <i><B>Nota:</B> Es indispensable que se tenga definido un
     *            getter para esta propiedad</I>
     *      
     * @return El arreglo de SelectItems correspondiente a la lista de javaBeans
     */
    public static SelectItem[] encode(List list, String labelAttribute) throws NoSuchMethodException,
            InvocationTargetException, IllegalAccessException {
        SelectItem[] wrappedLista = encode(list.toArray(), labelAttribute);
        return wrappedLista;
    }

    /**
     * Transforma un array de javaBeans y en un array de
     * javax.faces.model.SelectItem por medio del mecanismo de reflexion de java
     * <i><B>Nota:</B> Se debe aplicar el converter "com.indeval.Serializable" al componente que se asige el SelectItem</i>
     * 
     * @param array:
     *            Es el array de javaBeans que seran transformados en SelectItem
     * @param labelAttribute:
     *            es el nombre del atributo del javaBean object que sera
     *            asignado como label al SelectItem<br>
     *            <i><B>Nota:</B> Es indispensable que se tenga definido un
     *            getter para esta propiedad</I>
     * @return El arreglo de SelectItems correspondiente al arreglo de javaBeans
     */

    public static SelectItem[] encode(Object[] array, String labelAttribute) throws NoSuchMethodException,
            InvocationTargetException, IllegalAccessException {

        SelectItem[] wrappedLista = new SelectItem[array.length];
        for (int i = 0; i < array.length; i++) {
            wrappedLista[i] = encode(array[i], labelAttribute);
        }
        return wrappedLista;
    }

    /**
     * Construye el nombre del getter correspondiente al atributo que se
     * proporciona como parametro
     * 
     * @param attributeName:
     *            Es el nombre del atributo
     * @return El nombre del getter corespondiene al atributo
     */

    
    
	/**
	 * Transforma un javaBean en un javax.faces.model.SelectItem por medio del
	 * mecanismo de reflexion de java
	 * 
	 * @param bean:
	 *            el javaBean que sera transformado en SelectItem
	 * @param labelAttribute:
	 *            es el nombre del atributo del javaBean object que sera
	 *            asignado como label al SelectItem<br>
	 *            <i><B>Nota:</B> Es indispensable que se tenga definido un
	 *            getter para esta propiedad</I>
	 * @param valueAttribute:
	 *            es el nombre del atributo del javaBean object que sera
	 *            asignado como value al SelectItem<br>
	 *            Nota: Es indispensable que se tenga definido un getter para
	 *            esta propiedad
	 * @return El SelectItem que represta a el javaBean original
	 */

	public static SelectItem encode(Object bean, String labelAttribute,
			String valueAttribute) throws NoSuchMethodException,
			InvocationTargetException, IllegalAccessException {
		SelectItem si = new SelectItem();
		si.setDescription(getValue(labelAttribute, bean).toString());
		si.setLabel(getValue(labelAttribute, bean).toString());
		si.setValue(getValue(valueAttribute, bean));
		return si;
	}

	/**
	 * Transforma una lista de javaBeans en un array de
	 * javax.faces.model.SelectItem por medio del mecanismo de reflexion de java
	 * 
	 * @param lista:
	 *            Es la lista de javaBeans que seran transformados en SelectItem
	 * @param labelAttribute:
	 *            es el nombre del atributo del javaBean object que sera
	 *            asignado como label al SelectItem<br>
	 *            <i><B>Nota:</B> Es indispensable que se tenga definido un
	 *            getter para esta propiedad</I>
	 * @param valueAttribute:
	 *            es el nombre del atributo del javaBean object que sera
	 *            asignado como value al SelectItem<br>
	 *            Nota: Es indispensable que se tenga definido un getter para
	 *            esta propiedad
	 * 
	 * @return El arreglo de SelectItems correspondiente a la lista de javaBeans
	 */
	public static SelectItem[] encode(List list, String labelAttribute,
			String valueAttribute) throws NoSuchMethodException,
			InvocationTargetException, IllegalAccessException {
		SelectItem[] wrappedLista = encode(list.toArray(), labelAttribute,
				valueAttribute);
		return wrappedLista;
	}

	/**
	 * Transforma un array de javaBeans y en un array de
	 * javax.faces.model.SelectItem por medio del mecanismo de reflexion de java
	 * 
	 * @param array:
	 *            Es el array de javaBeans que seran transformados en SelectItem
	 * @param labelAttribute:
	 *            es el nombre del atributo del javaBean object que sera
	 *            asignado como label al SelectItem<br>
	 *            <i><B>Nota:</B> Es indispensable que se tenga definido un
	 *            getter para esta propiedad</I>
	 * @param valueAttribute:
	 *            es el nombre del atributo del javaBean object que sera
	 *            asignado como value al SelectItem<br>
	 *            Nota: Es indispensable que se tenga definido un getter para
	 *            esta propiedad
	 * 
	 * @return El arreglo de SelectItems correspondiente al arreglo de javaBeans
	 */

	public static SelectItem[] encode(Object[] array, String labelAttribute,
			String valueAttribute) throws NoSuchMethodException,
			InvocationTargetException, IllegalAccessException {

		SelectItem[] wrappedLista = new SelectItem[array.length];
		for (int i = 0; i < array.length; i++) {
			wrappedLista[i] = encode(array[i], labelAttribute, valueAttribute);
		}
		return wrappedLista;
	}

	/**
	 * Construye el nombre del getter correspondiente al atributo que se
	 * proporciona como parametro
	 * 
	 * @param attributeName:
	 *            Es el nombre del atributo
	 * @return El nombre del getter corespondiene al atributo
	 */

	private static String getGetterName(String attributeName) {
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

	private static Object getValue(String attributeName, Object javaBean)
			throws NoSuchMethodException, InvocationTargetException,
			IllegalAccessException {
		Method method = javaBean.getClass().getMethod(
				getGetterName(attributeName), null);
		return method.invoke(javaBean, null);
	}

}
