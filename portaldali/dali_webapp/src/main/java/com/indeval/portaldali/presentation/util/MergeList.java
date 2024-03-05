/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 * MergeList.java
 * May 6, 2008
 */
package com.indeval.portaldali.presentation.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Implementación de una lista la cual permite agregar ms listas y
 * posteriormente obtener los elementos uno a uno de dichas listas.
 * 
 * @author José Antonio Huizar Moreno
 * @version 1.0
 * 
 */
public class MergeList implements List<Object> {

	/** Contiene los elementos mezclados de las listas */
	private List<Object> lista = new ArrayList<Object>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#add(int, java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	public void add(int index, Object object) {
		if (object instanceof List) {
			lista.addAll((List) object);
		} else {
			lista.add(index, object);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#add(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	public boolean add(Object object) {
		boolean result = false;
		if (object instanceof List) {
			result = lista.addAll((List) object);
		} else {
			result = lista.add(object);
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#addAll(java.util.Collection)
	 */
	public boolean addAll(Collection<? extends Object> collection) {

		return lista.addAll(collection);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#addAll(int, java.util.Collection)
	 */
	public boolean addAll(int index, Collection<? extends Object> collection) {

		return lista.addAll(index, collection);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#clear()
	 */
	public void clear() {

		lista.clear();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#contains(java.lang.Object)
	 */
	public boolean contains(Object object) {

		return lista.contains(object);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#containsAll(java.util.Collection)
	 */
	public boolean containsAll(Collection<?> collection) {

		return lista.containsAll(collection);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#get(int)
	 */
	public Object get(int index) {

		return lista.get(index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#indexOf(java.lang.Object)
	 */
	public int indexOf(Object object) {

		return lista.indexOf(object);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#isEmpty()
	 */
	public boolean isEmpty() {

		return lista.isEmpty();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#iterator()
	 */
	public Iterator<Object> iterator() {

		return lista.iterator();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#lastIndexOf(java.lang.Object)
	 */
	public int lastIndexOf(Object object) {

		return lista.lastIndexOf(object);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#listIterator()
	 */
	public ListIterator<Object> listIterator() {

		return lista.listIterator();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#listIterator(int)
	 */
	public ListIterator<Object> listIterator(int arg0) {

		return lista.listIterator();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#remove(int)
	 */
	public Object remove(int index) {

		return lista.remove(index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#remove(java.lang.Object)
	 */
	public boolean remove(Object object) {

		return lista.remove(object);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#removeAll(java.util.Collection)
	 */
	public boolean removeAll(Collection<?> collection) {

		return lista.removeAll(collection);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#retainAll(java.util.Collection)
	 */
	public boolean retainAll(Collection<?> collection) {

		return lista.retainAll(collection);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#set(int, java.lang.Object)
	 */
	public Object set(int index, Object object) {

		return lista.set(index, object);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#size()
	 */
	public int size() {

		return lista.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#subList(int, int)
	 */
	public List<Object> subList(int fromIndex, int toIndex) {

		return lista.subList(fromIndex, toIndex);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#toArray()
	 */
	public Object[] toArray() {

		return lista.toArray();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#toArray(T[])
	 */
	public <T> T[] toArray(T[] array) {

		return lista.toArray(array);
	}

}
