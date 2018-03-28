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
package studio.raptor.sqlparser.dialect.db2.visitor;

import studio.raptor.sqlparser.dialect.db2.ast.stmt.DB2SelectQueryBlock;
import studio.raptor.sqlparser.dialect.db2.ast.stmt.DB2ValuesStatement;
import studio.raptor.sqlparser.visitor.SQLASTVisitor;

public interface DB2ASTVisitor extends SQLASTVisitor {

  boolean visit(DB2SelectQueryBlock x);

  void endVisit(DB2SelectQueryBlock x);

  boolean visit(DB2ValuesStatement x);

  void endVisit(DB2ValuesStatement x);
}
