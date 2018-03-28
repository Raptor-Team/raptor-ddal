/*
 * Copyright 1999-2017 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package studio.raptor.sqlparser.ast.statement;

import studio.raptor.sqlparser.ast.SQLExpr;
import studio.raptor.sqlparser.ast.SQLStatementImpl;
import studio.raptor.sqlparser.visitor.SQLASTVisitor;

public class SQLDropIndexStatement extends SQLStatementImpl implements SQLDDLStatement {

  private SQLExpr indexName;
  private SQLExprTableSource tableName;

  public SQLDropIndexStatement() {

  }

  public SQLDropIndexStatement(String dbType) {
    super(dbType);
  }

  public SQLExpr getIndexName() {
    return indexName;
  }

  public void setIndexName(SQLExpr indexName) {
    this.indexName = indexName;
  }

  public SQLExprTableSource getTableName() {
    return tableName;
  }

  public void setTableName(SQLExprTableSource tableName) {
    this.tableName = tableName;
  }

  public void setTableName(SQLExpr tableName) {
    this.setTableName(new SQLExprTableSource(tableName));
  }

  @Override
  protected void accept0(SQLASTVisitor visitor) {
    if (visitor.visit(this)) {
      acceptChild(visitor, indexName);
      acceptChild(visitor, tableName);
    }
    visitor.endVisit(this);
  }
}
