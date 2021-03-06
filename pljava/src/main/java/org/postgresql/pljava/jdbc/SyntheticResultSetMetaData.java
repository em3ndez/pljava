/*
 * Copyright (c) 2005-2018 Tada AB and other contributors, as listed below.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the The BSD 3-Clause License
 * which accompanies this distribution, and is available at
 * http://opensource.org/licenses/BSD-3-Clause
 *
 * Contributors:
 *   Filip Hrbek
 *   PostgreSQL Global Development Group
 *   Chapman Flack
 */

package org.postgresql.pljava.jdbc;

import java.sql.SQLException;

import org.postgresql.pljava.internal.Oid;

/**
 * Implementation of ResultSetMetaData for SyntheticResultSet
 *
 * @author Filip Hrbek
 */

public class SyntheticResultSetMetaData extends AbstractResultSetMetaData
{

	private final ResultSetField[] m_fields;

	/**
	 * Constructor.
	 * @param fields Array of ResultSetField
	 */
	public SyntheticResultSetMetaData(ResultSetField[] fields)
	{
		super();
		m_fields = fields;
	}

    /**
     * Returns the number of columns in this <code>ResultSet</code> object.
     *
     * @return the number of columns
     * @exception SQLException if a database access error occurs
     */
    public final int getColumnCount() throws SQLException
	{
		return m_fields.length;
	}

    /**
     * Indicates whether the designated column is automatically numbered, thus read-only.
     *
     * @param column the first column is 1, the second is 2, ...
     * @return <code>true</code> if so; <code>false</code> otherwise
     * @exception SQLException if a database access error occurs
     */
    public final boolean isAutoIncrement(int column) throws SQLException
	{
		checkColumnIndex(column);
		//SyntheticResultSet has no autoincrement columns
		return false;
	}

    /**
     * Gets the designated column's suggested title for use in printouts and
     * displays.
     *
     * @param column the first column is 1, the second is 2, ...
     * @return the suggested column title
     * @exception SQLException if a database access error occurs
     */
    public final String getColumnLabel(int column) throws SQLException
	{
		checkColumnIndex(column);
		return m_fields[column-1].getColumnLabel();
	}

    //--------------------------JDBC 2.0-----------------------------------

    /**
     * <p>Returns the fully-qualified name of the Java class whose instances 
     * are manufactured if the method <code>ResultSet.getObject</code>
     * is called to retrieve a value 
     * from the column.  <code>ResultSet.getObject</code> may return a subclass of the
     * class returned by this method.
     *
     * @param column the first column is 1, the second is 2, ...
     * @return the fully-qualified name of the class in the Java programming
     *         language that would be used by the method 
     * <code>ResultSet.getObject</code> to retrieve the value in the specified
     * column. This is the class name used for custom mapping.
     * @exception SQLException if a database access error occurs
     * @since 1.2
     */
    public final String getColumnClassName(int column) throws SQLException
	{
		checkColumnIndex(column);
		return m_fields[column-1].getJavaClass().getName();
	}

    /**
     * Checks if the column index is valid.
     *
     * @param column the first column is 1, the second is 2, ...
     * @exception SQLException if the column is out of index bounds
     */
    protected final void checkColumnIndex(int column) throws SQLException
	{
		if (column < 1 || column > m_fields.length)
		{
			throw new SQLException("Invalid column index: " + column);
		}
	}

	/**
	 * Gets column OID
	 * @param column Column index
	 * @return column OID
	 * @throws SQLException if an error occurs
	 */
	protected final Oid getOid(int column) throws SQLException
	{
		return m_fields[column-1].getOID();
	}

	/**
	 * Gets column length
	 * @param column Column index
	 * @return column length
	 * @throws SQLException if an error occurs
	 */
	protected final int getFieldLength(int column) throws SQLException
	{
		return m_fields[column-1].getLength();
	}
}
