/*
 * This file contains software that has been made available under The BSD
 * license. Use and distribution hereof are subject to the restrictions set
 * forth therein.
 * 
 * Copyright (c) 2003 TADA AB - Taby Sweden
 * All Rights Reserved
 */
package org.postgresql.pljava.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.postgresql.pljava.internal.Tuple;
import org.postgresql.pljava.internal.TupleDesc;
import org.postgresql.pljava.internal.TupleTableSlot;

/**
 * A single row, read-only ResultSet, specially made for functions and
 * procedures that takes complex types as arguments.
 *
 * @author Thomas Hallgren
 */
public class SingleRowReader extends SingleRowResultSet
{
	private final TupleDesc m_tupleDesc;
	private final Tuple m_tuple;

	public SingleRowReader(TupleTableSlot tableSlot)
	throws SQLException
	{
		m_tupleDesc = tableSlot.getTupleDesc();
		m_tuple = tableSlot.getTuple();
	}

	public int findColumn(String columnName)
	throws SQLException
	{
		return m_tupleDesc.getColumnIndex(columnName);
	}

	protected Object getObjectValue(int columnIndex)
	throws SQLException
	{
		return m_tuple.getObject(this.getTupleDesc(), columnIndex);
	}

	/**
	 * Returns {@link ResultSet#CONCUR_READ_ONLY}.
	 */
	public int getConcurrency()
	throws SQLException
	{
		return ResultSet.CONCUR_READ_ONLY;
	}

	/**
	 * This feature is not supported on a <code>ReadOnlyResultSet</code>.
	 * @throws SQLException indicating that this feature is not supported.
	 */
	public void cancelRowUpdates()
	throws SQLException
	{
		throw readOnlyException();
	}

	/**
	 * This feature is not supported on a <code>ReadOnlyResultSet</code>.
	 * @throws SQLException indicating that this feature is not supported.
	 */
	public void deleteRow()
	throws SQLException
	{
		throw readOnlyException();
	}

	/**
	 * This feature is not supported on a <code>ReadOnlyResultSet</code>.
	 * @throws SQLException indicating that this feature is not supported.
	 */
	public void insertRow()
	throws SQLException
	{
		throw readOnlyException();
	}

	/**
	 * This feature is not supported on a <code>ReadOnlyResultSet</code>.
	 * @throws SQLException indicating that this feature is not supported.
	 */
	public void moveToInsertRow()
	throws SQLException
	{
		throw readOnlyException();
	}

	/**
	 * This feature is not supported on a <code>ReadOnlyResultSet</code>.
	 * @throws SQLException indicating that this feature is not supported.
	 */
	public void updateRow()
	throws SQLException
	{
		throw readOnlyException();
	}

	/**
	 * Always returns false.
	 */
	public boolean rowUpdated()
	throws SQLException
	{
		return false;
	}

	/**
	 * This feature is not supported on a <code>ReadOnlyResultSet</code>.
	 * @throws SQLException indicating that this feature is not supported.
	 */
	public void updateObject(int columnIndex, Object x) throws SQLException
	{
		throw readOnlyException();
	}

	/**
	 * This feature is not supported on a <code>ReadOnlyResultSet</code>.
	 * @throws SQLException indicating that this feature is not supported.
	 */
	public void updateObject(int columnIndex, Object x, int scale)
	throws SQLException
	{
		throw readOnlyException();
	}

	protected final TupleDesc getTupleDesc()
	{
		return m_tupleDesc;
	}

	private static SQLException readOnlyException()
	{
		return new UnsupportedFeatureException("ResultSet is read-only");
	}
}
