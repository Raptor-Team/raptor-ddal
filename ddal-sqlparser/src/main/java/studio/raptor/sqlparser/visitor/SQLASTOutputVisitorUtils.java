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
package studio.raptor.sqlparser.visitor;

import studio.raptor.sqlparser.ast.expr.SQLCharExpr;
import studio.raptor.sqlparser.ast.expr.SQLIntegerExpr;
import studio.raptor.sqlparser.ast.expr.SQLNumberExpr;

public class SQLASTOutputVisitorUtils {

  public static boolean visit(PrintableVisitor visitor, SQLIntegerExpr x) {
    visitor.print(x.getNumber().toString());
    return false;
  }

  public static boolean visit(PrintableVisitor visitor, SQLNumberExpr x) {
    visitor.print(x.getNumber().toString());
    return false;
  }

  public static boolean visit(PrintableVisitor visitor, SQLCharExpr x) {
    visitor.print('\'');

    String text = x.getText();
    text = text.replaceAll("'", "''");
    text = text.replaceAll("\\\\", "\\\\");

    visitor.print(text);

    visitor.print('\'');
    return false;
  }
}
