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

import studio.raptor.sqlparser.SQLUtils;
import studio.raptor.sqlparser.ast.expr.SQLCastExpr;
import studio.raptor.sqlparser.dialect.postgresql.visitor.PGASTVisitor;
import studio.raptor.sqlparser.visitor.SQLASTVisitor;

public class PGTypeCastExpr extends SQLCastExpr implements PGExpr {

  @Override
  public void accept0(PGASTVisitor visitor) {
    if (visitor.visit(this)) {
      acceptChild(visitor, this.expr);
      acceptChild(visitor, this.dataType);
    }
    visitor.endVisit(this);
  }

  @Override
  protected void accept0(SQLASTVisitor visitor) {
    accept0((PGASTVisitor) visitor);
  }

  public String toString() {
    return SQLUtils.toPGString(this);
  }
}
