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

import java.util.List;
import studio.raptor.sqlparser.ast.SQLExpr;
import studio.raptor.sqlparser.ast.expr.SQLMethodInvokeExpr;
import studio.raptor.sqlparser.visitor.SQLEvalVisitor;

public class Substring implements Function {

  public final static Substring instance = new Substring();

  public Object eval(SQLEvalVisitor visitor, SQLMethodInvokeExpr x) {
    List<SQLExpr> params = x.getParameters();
    int paramSize = params.size();
    if (paramSize != 2 && paramSize != 3) {
      return SQLEvalVisitor.EVAL_ERROR;
    }

    SQLExpr param0 = params.get(0);
    SQLExpr param1 = params.get(1);
    param0.accept(visitor);
    param1.accept(visitor);

    Object param0Value = param0.getAttributes().get(EVAL_VALUE);
    Object param1Value = param1.getAttributes().get(EVAL_VALUE);
    if (param0Value == null || param1Value == null) {
      return SQLEvalVisitor.EVAL_ERROR;
    }

    String str = param0Value.toString();
    int index = ((Number) param1Value).intValue();

    if (paramSize == 2) {

      if (index <= 0) {
        int lastIndex = str.length() + index;
        return str.substring(lastIndex);
      }

      return str.substring(index - 1);
    }

    SQLExpr param2 = params.get(2);
    param2.accept(visitor);
    Object param2Value = param2.getAttributes().get(EVAL_VALUE);
    if (param2Value == null) {
      return SQLEvalVisitor.EVAL_ERROR;
    }

    int len = ((Number) param2Value).intValue();

    String result;
    if (index <= 0) {
      int lastIndex = str.length() + index;
      result = str.substring(lastIndex);
    } else {
      result = str.substring(index - 1);
    }

    if (len > result.length()) {
      return result;
    }
    return result.substring(0, len);
  }
}
