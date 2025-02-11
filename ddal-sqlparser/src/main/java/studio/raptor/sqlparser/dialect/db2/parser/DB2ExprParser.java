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
package studio.raptor.sqlparser.dialect.db2.parser;

import studio.raptor.sqlparser.ast.SQLExpr;
import studio.raptor.sqlparser.ast.SQLName;
import studio.raptor.sqlparser.ast.expr.SQLIdentifierExpr;
import studio.raptor.sqlparser.ast.expr.SQLSequenceExpr;
import studio.raptor.sqlparser.parser.Lexer;
import studio.raptor.sqlparser.parser.SQLExprParser;
import studio.raptor.sqlparser.parser.Token;

public class DB2ExprParser extends SQLExprParser {

  public final static String[] AGGREGATE_FUNCTIONS = {"AVG", "COUNT", "MAX", "MIN", "STDDEV", "SUM",
      "ROW_NUMBER",
      "ROWNUMBER"};

  public DB2ExprParser(String sql) {
    this(new DB2Lexer(sql));
    this.lexer.nextToken();
  }

  public DB2ExprParser(Lexer lexer) {
    super(lexer);
    this.aggregateFunctions = AGGREGATE_FUNCTIONS;
  }

  public SQLExpr primaryRest(SQLExpr expr) {
    if (identifierEquals("VALUE")) {
      if (expr instanceof SQLIdentifierExpr) {
        SQLIdentifierExpr identExpr = (SQLIdentifierExpr) expr;
        String ident = identExpr.getName();
        if (ident.equalsIgnoreCase("NEXT")) {
          lexer.nextToken();
          accept(Token.FOR);
          SQLName seqName = this.name();
          SQLSequenceExpr seqExpr = new SQLSequenceExpr(seqName, SQLSequenceExpr.Function.NextVal);
          return seqExpr;
        } else if (ident.equalsIgnoreCase("PREVIOUS")) {
          lexer.nextToken();
          accept(Token.FOR);
          SQLName seqName = this.name();
          SQLSequenceExpr seqExpr = new SQLSequenceExpr(seqName, SQLSequenceExpr.Function.PrevVal);
          return seqExpr;
        }
      }
    } else if (identifierEquals("DATE")) {
      if (expr instanceof SQLIdentifierExpr) {
        SQLIdentifierExpr identExpr = (SQLIdentifierExpr) expr;
        String ident = identExpr.getName();
        if (ident.equalsIgnoreCase("CURRENT")) {
          lexer.nextToken();

          expr = new SQLIdentifierExpr("CURRENT DATE");
        }
      }
    } else if (identifierEquals("TIMESTAMP")) {
      if (expr instanceof SQLIdentifierExpr) {
        SQLIdentifierExpr identExpr = (SQLIdentifierExpr) expr;
        String ident = identExpr.getName();
        if (ident.equalsIgnoreCase("CURRENT")) {
          lexer.nextToken();

          expr = new SQLIdentifierExpr("CURRENT DATE");
        }
      }
    }

    return super.primaryRest(expr);
  }

  protected SQLExpr dotRest(SQLExpr expr) {
    if (identifierEquals("NEXTVAL")) {
      if (expr instanceof SQLIdentifierExpr) {
        SQLIdentifierExpr identExpr = (SQLIdentifierExpr) expr;
        SQLSequenceExpr seqExpr = new SQLSequenceExpr(identExpr, SQLSequenceExpr.Function.NextVal);
        lexer.nextToken();
        return seqExpr;
      }
    } else if (identifierEquals("PREVVAL")) {
      if (expr instanceof SQLIdentifierExpr) {
        SQLIdentifierExpr identExpr = (SQLIdentifierExpr) expr;
        SQLSequenceExpr seqExpr = new SQLSequenceExpr(identExpr, SQLSequenceExpr.Function.PrevVal);
        lexer.nextToken();
        return seqExpr;
      }
    } else if (identifierEquals("CURRVAL")) {
      if (expr instanceof SQLIdentifierExpr) {
        SQLIdentifierExpr identExpr = (SQLIdentifierExpr) expr;
        SQLSequenceExpr seqExpr = new SQLSequenceExpr(identExpr, SQLSequenceExpr.Function.CurrVal);
        lexer.nextToken();
        return seqExpr;
      }
    }

    return super.dotRest(expr);
  }

}
