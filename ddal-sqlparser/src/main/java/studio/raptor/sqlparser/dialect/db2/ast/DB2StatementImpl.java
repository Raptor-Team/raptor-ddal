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
package studio.raptor.sqlparser.dialect.db2.ast;

import studio.raptor.sqlparser.ast.SQLStatementImpl;
import studio.raptor.sqlparser.dialect.db2.visitor.DB2ASTVisitor;
import studio.raptor.sqlparser.util.JdbcConstants;
import studio.raptor.sqlparser.visitor.SQLASTVisitor;

public abstract class DB2StatementImpl extends SQLStatementImpl implements DB2Object {

  public DB2StatementImpl() {
    super(JdbcConstants.DB2);
  }

  @Override
  protected void accept0(SQLASTVisitor visitor) {
    if (visitor instanceof DB2ASTVisitor) {
      accept0((DB2ASTVisitor) visitor);
      return;
    }

    super.accept0(visitor);
  }

  @Override
  public abstract void accept0(DB2ASTVisitor visitor);
}
