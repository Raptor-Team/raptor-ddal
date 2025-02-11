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
import studio.raptor.sqlparser.ast.SQLExprImpl;
import studio.raptor.sqlparser.ast.statement.SQLSelect;
import studio.raptor.sqlparser.visitor.SQLASTVisitor;

public class SQLExistsExpr extends SQLExprImpl implements Serializable {

  private static final long serialVersionUID = 1L;
  public boolean not = false;
  public SQLSelect subQuery;

  public SQLExistsExpr() {

  }

  public SQLExistsExpr(SQLSelect subQuery) {
    this.setSubQuery(subQuery);
  }

  public SQLExistsExpr(SQLSelect subQuery, boolean not) {
    this.setSubQuery(subQuery);
    this.not = not;
  }

  public boolean isNot() {
    return this.not;
  }

  public void setNot(boolean not) {
    this.not = not;
  }

  public SQLSelect getSubQuery() {
    return this.subQuery;
  }

  public void setSubQuery(SQLSelect subQuery) {
    if (subQuery != null) {
      subQuery.setParent(this);
    }
    this.subQuery = subQuery;
  }

  protected void accept0(SQLASTVisitor visitor) {
    if (visitor.visit(this)) {
      acceptChild(visitor, this.subQuery);
    }

    visitor.endVisit(this);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (not ? 1231 : 1237);
    result = prime * result + ((subQuery == null) ? 0 : subQuery.hashCode());
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
    SQLExistsExpr other = (SQLExistsExpr) obj;
    if (not != other.not) {
      return false;
    }
    if (subQuery == null) {
      if (other.subQuery != null) {
        return false;
      }
    } else if (!subQuery.equals(other.subQuery)) {
      return false;
    }
    return true;
  }
}
