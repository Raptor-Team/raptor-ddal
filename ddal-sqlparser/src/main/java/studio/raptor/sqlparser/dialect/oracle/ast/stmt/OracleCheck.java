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
package studio.raptor.sqlparser.dialect.oracle.ast.stmt;

import studio.raptor.sqlparser.ast.SQLName;
import studio.raptor.sqlparser.ast.statement.SQLCheck;
import studio.raptor.sqlparser.dialect.oracle.ast.OracleSQLObject;
import studio.raptor.sqlparser.dialect.oracle.visitor.OracleASTVisitor;
import studio.raptor.sqlparser.visitor.SQLASTVisitor;

public class OracleCheck extends SQLCheck implements OracleConstraint, OracleSQLObject {

  private OracleUsingIndexClause using;
  private SQLName exceptionsInto;
  private Initially initially;
  private Boolean deferrable;

  @Override
  protected void accept0(SQLASTVisitor visitor) {
    if (visitor instanceof OracleASTVisitor) {
      accept0((OracleASTVisitor) visitor);
      return;
    }

    super.accept(visitor);
  }

  public void accept0(OracleASTVisitor visitor) {
    if (visitor.visit(this)) {
      acceptChild(visitor, this.getName());
      acceptChild(visitor, this.getExpr());

      acceptChild(visitor, using);
      acceptChild(visitor, exceptionsInto);
    }
    visitor.endVisit(this);
  }

  public Boolean getDeferrable() {
    return deferrable;
  }

  public void setDeferrable(Boolean deferrable) {
    this.deferrable = deferrable;
  }

  public Initially getInitially() {
    return initially;
  }

  public void setInitially(Initially initially) {
    this.initially = initially;
  }

  public SQLName getExceptionsInto() {
    return exceptionsInto;
  }

  public void setExceptionsInto(SQLName exceptionsInto) {
    this.exceptionsInto = exceptionsInto;
  }

  public OracleUsingIndexClause getUsing() {
    return using;
  }

  public void setUsing(OracleUsingIndexClause using) {
    if (using != null) {
      using.setParent(this);
    }
    this.using = using;
  }

}
