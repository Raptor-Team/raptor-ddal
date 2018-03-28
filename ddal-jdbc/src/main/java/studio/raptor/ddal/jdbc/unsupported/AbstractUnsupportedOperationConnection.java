/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package studio.raptor.ddal.jdbc.unsupported;


import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

import studio.raptor.ddal.jdbc.adapter.WrapperAdapter;

/**
 * Declare a database connection object that does not support operations.
 *
 * @author jackcao
 * @since 3.0
 */
public abstract class AbstractUnsupportedOperationConnection extends WrapperAdapter implements Connection {

  @Override
  public final void setTransactionIsolation(final int level) throws SQLException {
    throw new SQLFeatureNotSupportedException("setTransactionIsolation");
  }

  public DatabaseMetaData getMetaData() throws SQLException {
    throw new SQLFeatureNotSupportedException("getMetaData");
  }

  @Override
  public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
    throw new SQLFeatureNotSupportedException("prepareStatement(String sql, int autoGeneratedKeys)");
  }

  @Override
  public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
    throw new SQLFeatureNotSupportedException("prepareStatement(String sql, int[] columnIndexes)");
  }

  @Override
  public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
    throw new SQLFeatureNotSupportedException("prepareStatement(String sql, String[] columnNames)");
  }

  @Override
  public final CallableStatement prepareCall(final String sql) throws SQLException {
    throw new SQLFeatureNotSupportedException("prepareCall");
  }

  @Override
  public final CallableStatement prepareCall(final String sql, final int resultSetType, final int resultSetConcurrency) throws SQLException {
    throw new SQLFeatureNotSupportedException("prepareCall");
  }

  @Override
  public final CallableStatement prepareCall(final String sql, final int resultSetType, final int resultSetConcurrency, final int resultSetHoldability) throws SQLException {
    throw new SQLFeatureNotSupportedException("prepareCall");
  }

  @Override
  public final String nativeSQL(final String sql) throws SQLException {
    throw new SQLFeatureNotSupportedException("nativeSQL");
  }

  @Override
  public final Savepoint setSavepoint() throws SQLException {
    throw new SQLFeatureNotSupportedException("setSavepoint");
  }

  @Override
  public final Savepoint setSavepoint(final String name) throws SQLException {
    throw new SQLFeatureNotSupportedException("setSavepoint name");
  }

  @Override
  public final void releaseSavepoint(final Savepoint savepoint) throws SQLException {
    throw new SQLFeatureNotSupportedException("releaseSavepoint");
  }

  @Override
  public final void rollback(final Savepoint savepoint) throws SQLException {
    throw new SQLFeatureNotSupportedException("rollback savepoint");
  }

  @Override
  public final void abort(final Executor executor) throws SQLException {
    throw new SQLFeatureNotSupportedException("abort");
  }

  @Override
  public final String getCatalog() throws SQLException {
    throw new SQLFeatureNotSupportedException("getCatalog");
  }

  @Override
  public final void setCatalog(final String catalog) throws SQLException {
    throw new SQLFeatureNotSupportedException("setCatalog");
  }

  @Override
  public final String getSchema() throws SQLException {
    throw new SQLFeatureNotSupportedException("getSchema");
  }

  @Override
  public final void setSchema(final String schema) throws SQLException {
    throw new SQLFeatureNotSupportedException("setSchema");
  }

  @Override
  public final Map<String, Class<?>> getTypeMap() throws SQLException {
    throw new SQLFeatureNotSupportedException("getTypeMap");
  }

  @Override
  public final void setTypeMap(final Map<String, Class<?>> map) throws SQLException {
    throw new SQLFeatureNotSupportedException("setTypeMap");
  }

  @Override
  public final int getNetworkTimeout() throws SQLException {
    throw new SQLFeatureNotSupportedException("getNetworkTimeout");
  }

  @Override
  public final void setNetworkTimeout(final Executor executor, final int milliseconds) throws SQLException {
    throw new SQLFeatureNotSupportedException("setNetworkTimeout");
  }

  @Override
  public final Clob createClob() throws SQLException {
    throw new SQLFeatureNotSupportedException("createClob");
  }

  @Override
  public final Blob createBlob() throws SQLException {
    throw new SQLFeatureNotSupportedException("createBlob");
  }

  @Override
  public final NClob createNClob() throws SQLException {
    throw new SQLFeatureNotSupportedException("createNClob");
  }

  @Override
  public final SQLXML createSQLXML() throws SQLException {
    throw new SQLFeatureNotSupportedException("createSQLXML");
  }

  @Override
  public final Array createArrayOf(final String typeName, final Object[] elements) throws SQLException {
    throw new SQLFeatureNotSupportedException("createArrayOf");
  }

  @Override
  public final Struct createStruct(final String typeName, final Object[] attributes) throws SQLException {
    throw new SQLFeatureNotSupportedException("createStruct");
  }

  @Override
  public final boolean isValid(final int timeout) throws SQLException {
    throw new SQLFeatureNotSupportedException("isValid");
  }

  @Override
  public final Properties getClientInfo() throws SQLException {
    throw new SQLFeatureNotSupportedException("getClientInfo");
  }

  @Override
  public final void setClientInfo(final Properties properties) throws SQLClientInfoException {
    throw new UnsupportedOperationException("setClientInfo properties");
  }

  @Override
  public final String getClientInfo(final String name) throws SQLException {
    throw new SQLFeatureNotSupportedException("getClientInfo name");
  }

  @Override
  public final void setClientInfo(final String name, final String value) throws SQLClientInfoException {
    throw new UnsupportedOperationException("setClientInfo name value");
  }

  @Override
  public Statement createStatement(final int resultSetType, final int resultSetConcurrency) throws SQLException {
    throw new UnsupportedOperationException("createStatement(final int resultSetType, final int resultSetConcurrency)");
  }

  @Override
  public Statement createStatement(final int resultSetType, final int resultSetConcurrency, final int resultSetHoldability) throws SQLException {
    throw new UnsupportedOperationException("createStatement(final int resultSetType, final int resultSetConcurrency, final int resultSetHoldability)");
  }

  @Override
  public PreparedStatement prepareStatement(final String sql, final int resultSetType, final int resultSetConcurrency, final int resultSetHoldability) throws SQLException {
    throw new UnsupportedOperationException("prepareStatement(final String sql, final int resultSetType, final int resultSetConcurrency, final int resultSetHoldability)");
  }
}
