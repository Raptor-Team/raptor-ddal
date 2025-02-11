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
package studio.raptor.sqlparser.dialect.sqlserver.ast.stmt;

import studio.raptor.sqlparser.ast.SQLExpr;
import studio.raptor.sqlparser.ast.SQLStatement;
import studio.raptor.sqlparser.dialect.sqlserver.ast.SQLServerObjectImpl;
import studio.raptor.sqlparser.dialect.sqlserver.ast.SQLServerStatement;
import studio.raptor.sqlparser.dialect.sqlserver.visitor.SQLServerASTVisitor;

public class SQLServerWaitForStatement extends SQLServerObjectImpl implements SQLServerStatement {

  private SQLExpr delay;

  private SQLExpr time;

  private SQLStatement statement;

  private SQLExpr timeout;

  private String dbType;

  @Override
  public void accept0(SQLServerASTVisitor visitor) {
    if (visitor.visit(this)) {
      acceptChild(visitor, delay);
      acceptChild(visitor, time);
      acceptChild(visitor, statement);
      acceptChild(visitor, timeout);
    }
    visitor.endVisit(this);
  }

  public SQLExpr getDelay() {
    return delay;
  }

  public void setDelay(SQLExpr delay) {
    this.delay = delay;
  }

  public SQLExpr getTime() {
    return time;
  }

  public void setTime(SQLExpr time) {
    this.time = time;
  }

  public SQLStatement getStatement() {
    return statement;
  }

  public void setStatement(SQLStatement statement) {
    this.statement = statement;
  }

  public SQLExpr getTimeout() {
    return timeout;
  }

  public void setTimeout(SQLExpr timeout) {
    this.timeout = timeout;
  }

  public String getDbType() {
    return dbType;
  }

  public void setDbType(String dbType) {
    this.dbType = dbType;
  }
}
