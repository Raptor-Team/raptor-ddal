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

package studio.raptor.ddal.jdbc;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import studio.raptor.ddal.core.executor.resultset.ColumnDefinition;
import studio.raptor.ddal.jdbc.unsupported.AbstractUnsupportedOperationResultSetMetaData;

/**
 * @author Sam
 * @since 3.0.0
 */
public class RaptorJdbcResultSetMetaData extends AbstractUnsupportedOperationResultSetMetaData {

  private List<ColumnDefinition> columnList;

  public RaptorJdbcResultSetMetaData(List<ColumnDefinition> columnDefinitionList) {
    this.columnList = null != columnDefinitionList ? columnDefinitionList : new ArrayList<ColumnDefinition>();
  }

  @Override
  public int getColumnCount() throws SQLException {
    return columnList.size();
  }

  @Override
  public boolean isAutoIncrement(int column) throws SQLException {
    return false;
  }

  @Override
  public boolean isCaseSensitive(int column) throws SQLException {
    return false;
  }

  @Override
  public boolean isSearchable(int column) throws SQLException {
    return false;
  }

  @Override
  public boolean isCurrency(int column) throws SQLException {
    return false;
  }

  @Override
  public int isNullable(int column) throws SQLException {
    return 0;
  }

  @Override
  public boolean isSigned(int column) throws SQLException {
    return false;
  }

  @Override
  public int getColumnDisplaySize(int column) throws SQLException {
    return this.columnList.get(column - 1).getColumnLength();
  }

  @Override
  public String getColumnLabel(int column) throws SQLException {
    return this.columnList.get(column - 1).getName();
  }

  @Override
  public String getColumnName(int column) throws SQLException {
    return this.columnList.get(column - 1).getName();
  }

  @Override
  public String getSchemaName(int column) throws SQLException {
    return this.columnList.get(column - 1).getSchema();
  }

  @Override
  public String getTableName(int column) throws SQLException {
    return this.columnList.get(column - 1).getTable();
  }

  @Override
  public int getColumnType(int column) throws SQLException {
    return this.columnList.get(column - 1).getType();
  }

  @Override
  public String getColumnTypeName(int column) throws SQLException {
    return JDBCType.valueOf(getColumnType(column)).getName();
  }
}