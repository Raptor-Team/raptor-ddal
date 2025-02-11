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
package studio.raptor.sqlparser.ast.expr;

import java.io.Serializable;
import studio.raptor.sqlparser.ast.SQLExpr;
import studio.raptor.sqlparser.ast.SQLExprImpl;
import studio.raptor.sqlparser.visitor.SQLASTVisitor;

public class SQLNotExpr extends SQLExprImpl implements Serializable {

  private static final long serialVersionUID = 1L;
  public SQLExpr expr;

  public SQLNotExpr() {

  }

  public SQLNotExpr(SQLExpr expr) {

    this.expr = expr;
  }

  public SQLExpr getExpr() {
    return this.expr;
  }

  public void setExpr(SQLExpr expr) {
    this.expr = expr;
  }

  @Override
  public void output(StringBuffer buf) {
    buf.append(" NOT ");
    this.expr.output(buf);
  }

  protected void accept0(SQLASTVisitor visitor) {
    if (visitor.visit(this)) {
      acceptChild(visitor, this.expr);
    }

    visitor.endVisit(this);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((expr == null) ? 0 : expr.hashCode());
    return result;
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
    SQLNotExpr other = (SQLNotExpr) obj;
    if (expr == null) {
      if (other.expr != null) {
        return false;
      }
    } else if (!expr.equals(other.expr)) {
      return false;
    }
    return true;
  }
}
