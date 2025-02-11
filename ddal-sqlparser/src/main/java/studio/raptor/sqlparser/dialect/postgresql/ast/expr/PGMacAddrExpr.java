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
package studio.raptor.sqlparser.dialect.postgresql.ast.expr;

import studio.raptor.sqlparser.ast.SQLExpr;
import studio.raptor.sqlparser.dialect.postgresql.visitor.PGASTVisitor;


public class PGMacAddrExpr extends PGExprImpl {

  private SQLExpr value;

  public SQLExpr getValue() {
    return value;
  }

  public void setValue(SQLExpr value) {
    this.value = value;
  }

  @Override
  public void accept0(PGASTVisitor visitor) {
    if (visitor.visit(this)) {
      acceptChild(visitor, value);
    }
    visitor.endVisit(this);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((value == null) ? 0 : value.hashCode());
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
    PGMacAddrExpr other = (PGMacAddrExpr) obj;
    if (value == null) {
      if (other.value != null) {
        return false;
      }
    } else if (!value.equals(other.value)) {
      return false;
    }
    return true;
  }

}

