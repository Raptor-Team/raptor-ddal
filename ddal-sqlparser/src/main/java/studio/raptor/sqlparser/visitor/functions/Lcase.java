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
package studio.raptor.sqlparser.visitor.functions;

import static studio.raptor.sqlparser.visitor.SQLEvalVisitor.EVAL_VALUE;

import studio.raptor.sqlparser.ast.SQLExpr;
import studio.raptor.sqlparser.ast.expr.SQLMethodInvokeExpr;
import studio.raptor.sqlparser.visitor.SQLEvalVisitor;


public class Lcase implements Function {

  public final static Lcase instance = new Lcase();

  public Object eval(SQLEvalVisitor visitor, SQLMethodInvokeExpr x) {
    if (x.getParameters().size() != 1) {
      return SQLEvalVisitor.EVAL_ERROR;
    }

    SQLExpr param0 = x.getParameters().get(0);
    param0.accept(visitor);

    Object param0Value = param0.getAttributes().get(EVAL_VALUE);
    if (param0Value == null) {
      return SQLEvalVisitor.EVAL_ERROR;
    }

    String strValue = param0Value.toString();

    String result = strValue.toLowerCase();

    return result;
  }
}
