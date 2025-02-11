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
import studio.raptor.sqlparser.dialect.oracle.visitor.OracleASTVisitor;

public class OracleGotoStatement extends OracleStatementImpl {

  private SQLName label;

  public OracleGotoStatement() {
  }

  public OracleGotoStatement(SQLName label) {
    this.label = label;
  }

  @Override
  public void accept0(OracleASTVisitor visitor) {
    if (visitor.visit(this)) {
      acceptChild(visitor, label);
    }
    visitor.endVisit(this);
  }

  public SQLName getLabel() {
    return label;
  }

  public void setLabel(SQLName label) {
    this.label = label;
  }

}
