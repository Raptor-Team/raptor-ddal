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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import studio.raptor.sqlparser.ast.expr.SQLBinaryOpExpr;
import studio.raptor.sqlparser.ast.expr.SQLCaseExpr;
import studio.raptor.sqlparser.ast.expr.SQLCharExpr;
import studio.raptor.sqlparser.ast.expr.SQLIdentifierExpr;
import studio.raptor.sqlparser.ast.expr.SQLInListExpr;
import studio.raptor.sqlparser.ast.expr.SQLIntegerExpr;
import studio.raptor.sqlparser.ast.expr.SQLMethodInvokeExpr;
import studio.raptor.sqlparser.ast.expr.SQLNullExpr;
import studio.raptor.sqlparser.ast.expr.SQLNumberExpr;
import studio.raptor.sqlparser.ast.expr.SQLQueryExpr;
import studio.raptor.sqlparser.ast.expr.SQLUnaryExpr;
import studio.raptor.sqlparser.ast.expr.SQLVariantRefExpr;
import studio.raptor.sqlparser.visitor.SQLEvalVisitor;
import studio.raptor.sqlparser.visitor.SQLEvalVisitorUtils;
import studio.raptor.sqlparser.visitor.functions.Function;

public class DB2EvalVisitor extends DB2ASTVisitorAdapter implements SQLEvalVisitor {

  private Map<String, Function> functions = new HashMap<String, Function>();
  private List<Object> parameters = new ArrayList<Object>();

  private int variantIndex = -1;

  private boolean markVariantIndex = true;

  public DB2EvalVisitor() {
    this(new ArrayList<Object>(1));
  }

  public DB2EvalVisitor(List<Object> parameters) {
    this.parameters = parameters;
  }

  public List<Object> getParameters() {
    return parameters;
  }

  public void setParameters(List<Object> parameters) {
    this.parameters = parameters;
  }

  public boolean visit(SQLCharExpr x) {
    return SQLEvalVisitorUtils.visit(this, x);
  }

  public int incrementAndGetVariantIndex() {
    return ++variantIndex;
  }

  public int getVariantIndex() {
    return variantIndex;
  }

  public boolean visit(SQLVariantRefExpr x) {
    return SQLEvalVisitorUtils.visit(this, x);
  }

  public boolean visit(SQLBinaryOpExpr x) {
    return SQLEvalVisitorUtils.visit(this, x);
  }

  public boolean visit(SQLUnaryExpr x) {
    return SQLEvalVisitorUtils.visit(this, x);
  }

  public boolean visit(SQLIntegerExpr x) {
    return SQLEvalVisitorUtils.visit(this, x);
  }

  public boolean visit(SQLNumberExpr x) {
    return SQLEvalVisitorUtils.visit(this, x);
  }

  @Override
  public boolean visit(SQLCaseExpr x) {
    return SQLEvalVisitorUtils.visit(this, x);
  }

  @Override
  public boolean visit(SQLInListExpr x) {
    return SQLEvalVisitorUtils.visit(this, x);
  }

  @Override
  public boolean visit(SQLNullExpr x) {
    return SQLEvalVisitorUtils.visit(this, x);
  }

  @Override
  public boolean visit(SQLMethodInvokeExpr x) {
    return SQLEvalVisitorUtils.visit(this, x);
  }

  @Override
  public boolean visit(SQLQueryExpr x) {
    return SQLEvalVisitorUtils.visit(this, x);
  }

  public boolean isMarkVariantIndex() {
    return markVariantIndex;
  }

  public void setMarkVariantIndex(boolean markVariantIndex) {
    this.markVariantIndex = markVariantIndex;
  }

  @Override
  public Function getFunction(String funcName) {
    return functions.get(funcName);
  }

  @Override
  public void registerFunction(String funcName, Function function) {
    functions.put(funcName, function);
  }

  @Override
  public void unregisterFunction(String funcName) {
    functions.remove(funcName);
  }

  public boolean visit(SQLIdentifierExpr x) {
    return SQLEvalVisitorUtils.visit(this, x);
  }
}
