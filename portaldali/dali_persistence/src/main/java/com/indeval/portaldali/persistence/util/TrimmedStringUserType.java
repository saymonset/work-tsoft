/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.util;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.usertype.UserType;

/**
 * @author <a href="mailto:ivan.kazakov@2hsoftware">Ivan Kazakov</a>
 *
 */
public class TrimmedStringUserType implements UserType {

	public int[] sqlTypes() {
		return (new int[] {Hibernate.CHARACTER.sqlType()});
	}
	
	@SuppressWarnings ("unchecked")
	public Class returnedClass() {
		return (java.lang.String.class);
	}
	
	public boolean isMutable() {
		return (false);
	}
	
	public Object deepCopy(Object object) {
		return (object);
	}
	
	public Serializable disassemble(Object object) {
		return ((Serializable)object);
	}
	
	public Object assemble(Serializable serializable, Object object) {
		return (serializable);
	}
	
	public Object replace(Object original, Object target, Object owner) {
		return (original);
	}
	
	public boolean equals(Object x, Object y) {
		if (x == y) {
			return (true);
		}
		if ((x == null) || (y == null)) {
			return (false);
		}
		return (x.equals(y));
	}
	
	public int hashCode(Object object) {
		return (object.hashCode());
	}
	
	public Object nullSafeGet(ResultSet resultSet, String[] names, Object owner) throws SQLException {
		String string = resultSet.getString(names[0]);
		if (resultSet.wasNull()) {
			return (null);
		}
		return (string.trim());
	}
	
	public void nullSafeSet(PreparedStatement preparedStatement, Object value, int index) throws HibernateException, SQLException {
		if (value == null) {
			preparedStatement.setNull(index, Hibernate.CHARACTER.sqlType());
		}
		else {
			preparedStatement.setString(index, value.toString().trim());
		}
	}
}
