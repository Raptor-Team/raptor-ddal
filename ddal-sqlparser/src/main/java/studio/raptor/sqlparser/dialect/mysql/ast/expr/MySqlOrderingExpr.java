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
package studio.raptor.sqlparser.dialect.mysql.ast.expr;

import studio.raptor.sqlparser.ast.SQLExpr;
import studio.raptor.sqlparser.ast.SQLExprImpl;
import studio.raptor.sqlparser.ast.SQLOrderingSpecification;
import studio.raptor.sqlparser.dialect.mysql.visitor.MySqlASTVisitor;
import studio.raptor.sqlparser.visitor.SQLASTVisitor;

public class MySqlOrderingExpr extends SQLExprImpl implements MySqlExpr {

  protected SQLExpr expr;
  protected SQLOrderingSpecification type;

  public MySqlOrderingExpr() {

  }

  public MySqlOrderingExpr(SQLExpr expr, SQLOrderingSpecification type) {
    super();
    this.expr = expr;
    this.type = type;
  }

  @Override
  protected void accept0(SQLASTVisitor visitor) {
    MySqlASTVisitor mysqlVisitor = (MySqlASTVisitor) visitor;
    if (mysqlVisitor.visit(this)) {
      acceptChild(visitor, this.expr);
    }

    mysqlVisitor.endVisit(this);
  }

  public SQLExpr getExpr() {
    return expr;
  }

  public void setExpr(SQLExpr expr) {
    expr.setParent(this);
    this.expr = expr;
  }

  public SQLOrderingSpecification getType() {
    return type;
  }

  public void setType(SQLOrderingSpecification type) {
    this.type = type;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    MySqlOrderingExpr other = (MySqlOrderingExpr) obj;
    if (expr != other.expr) {
      return false;
    }
    if (type == null) {
      if (other.type != null) {
        return false;
      }
    } else if (!type.equals(other.type)) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((expr == null) ? 0 : expr.hashCode());
    result = prime * result + ((type == null) ? 0 : type.hashCode());
    return result;
  }

}
