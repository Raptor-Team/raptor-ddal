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
package studio.raptor.sqlparser.dialect.odps.visitor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import studio.raptor.sqlparser.ast.SQLDataType;
import studio.raptor.sqlparser.ast.SQLHint;
import studio.raptor.sqlparser.ast.SQLOrderBy;
import studio.raptor.sqlparser.ast.SQLSetQuantifier;
import studio.raptor.sqlparser.ast.SQLStatement;
import studio.raptor.sqlparser.ast.expr.SQLCaseExpr;
import studio.raptor.sqlparser.ast.expr.SQLCharExpr;
import studio.raptor.sqlparser.ast.expr.SQLMethodInvokeExpr;
import studio.raptor.sqlparser.ast.statement.SQLAlterTableRenameColumn;
import studio.raptor.sqlparser.ast.statement.SQLAssignItem;
import studio.raptor.sqlparser.ast.statement.SQLColumnDefinition;
import studio.raptor.sqlparser.ast.statement.SQLCreateTableStatement;
import studio.raptor.sqlparser.ast.statement.SQLJoinTableSource;
import studio.raptor.sqlparser.ast.statement.SQLJoinTableSource.JoinType;
import studio.raptor.sqlparser.ast.statement.SQLSelect;
import studio.raptor.sqlparser.ast.statement.SQLSelectItem;
import studio.raptor.sqlparser.ast.statement.SQLSubqueryTableSource;
import studio.raptor.sqlparser.ast.statement.SQLTableElement;
import studio.raptor.sqlparser.ast.statement.SQLTableSource;
import studio.raptor.sqlparser.dialect.odps.ast.OdpsAddStatisticStatement;
import studio.raptor.sqlparser.dialect.odps.ast.OdpsAnalyzeTableStatement;
import studio.raptor.sqlparser.dialect.odps.ast.OdpsCreateTableStatement;
import studio.raptor.sqlparser.dialect.odps.ast.OdpsGrantStmt;
import studio.raptor.sqlparser.dialect.odps.ast.OdpsInsert;
import studio.raptor.sqlparser.dialect.odps.ast.OdpsInsertStatement;
import studio.raptor.sqlparser.dialect.odps.ast.OdpsLateralViewTableSource;
import studio.raptor.sqlparser.dialect.odps.ast.OdpsListStmt;
import studio.raptor.sqlparser.dialect.odps.ast.OdpsReadStatement;
import studio.raptor.sqlparser.dialect.odps.ast.OdpsRemoveStatisticStatement;
import studio.raptor.sqlparser.dialect.odps.ast.OdpsSelectQueryBlock;
import studio.raptor.sqlparser.dialect.odps.ast.OdpsSetLabelStatement;
import studio.raptor.sqlparser.dialect.odps.ast.OdpsShowGrantsStmt;
import studio.raptor.sqlparser.dialect.odps.ast.OdpsShowPartitionsStmt;
import studio.raptor.sqlparser.dialect.odps.ast.OdpsShowStatisticStmt;
import studio.raptor.sqlparser.dialect.odps.ast.OdpsStatisticClause;
import studio.raptor.sqlparser.dialect.odps.ast.OdpsUDTFSQLSelectItem;
import studio.raptor.sqlparser.dialect.odps.ast.OdpsValuesTableSource;
import studio.raptor.sqlparser.util.JdbcConstants;
import studio.raptor.sqlparser.visitor.SQLASTOutputVisitor;

public class OdpsOutputVisitor extends SQLASTOutputVisitor implements OdpsASTVisitor {

  private Set<String> builtInFunctions = new HashSet<String>();

  {
    builtInFunctions.add("IF");
    builtInFunctions.add("COALESCE");
    builtInFunctions.add("TO_DATE");
    builtInFunctions.add("SUBSTR");
    builtInFunctions.add("INSTR");
    builtInFunctions.add("LENGTH");
    builtInFunctions.add("SPLIT");
    builtInFunctions.add("TOLOWER");
    builtInFunctions.add("TOUPPER");
    builtInFunctions.add("EXPLODE");
    builtInFunctions.add("LEAST");
    builtInFunctions.add("GREATEST");

    groupItemSingleLine = true;
  }

  public OdpsOutputVisitor(Appendable appender) {
    super(appender, JdbcConstants.ODPS);
  }

  public boolean visit(OdpsCreateTableStatement x) {
    if (x.isIfNotExiists()) {
      print0(ucase ? "CREATE TABLE IF NOT EXISTS " : "create table if not exists ");
    } else {
      print0(ucase ? "CREATE TABLE " : "create table ");
    }

    x.getName().accept(this);

    if (x.getLike() != null) {
      print0(ucase ? " LIKE " : " like ");
      x.getLike().accept(this);
    }

    int size = x.getTableElementList().size();
    if (size > 0) {
      print0(" (");

      if (this.isPrettyFormat() && x.hasBodyBeforeComment()) {
        print(' ');
        printComment(x.getBodyBeforeCommentsDirect(), "");
      }

      incrementIndent();
      println();
      for (int i = 0; i < size; ++i) {
        SQLTableElement element = x.getTableElementList().get(i);
        element.accept(this);

        if (i != size - 1) {
          print(',');
        }
        if (this.isPrettyFormat() && element.hasAfterComment()) {
          print(' ');
          printComment(element.getAfterCommentsDirect(), "\n");
        }

        if (i != size - 1) {
          println();
        }
      }
      decrementIndent();
      println();
      print(')');
    }

    if (x.getComment() != null) {
      println();
      print0(ucase ? "COMMENT " : "comment ");
      x.getComment().accept(this);
    }

    int partitionSize = x.getPartitionColumns().size();
    if (partitionSize > 0) {
      println();
      print0(ucase ? "PARTITIONED BY (" : "partitioned by (");
      incrementIndent();
      println();
      for (int i = 0; i < partitionSize; ++i) {
        SQLColumnDefinition column = x.getPartitionColumns().get(i);
        column.accept(this);

        if (i != partitionSize - 1) {
          print(',');
        }
        if (this.isPrettyFormat() && column.hasAfterComment()) {
          print(' ');
          printComment(column.getAfterCommentsDirect(), "\n");
        }

        if (i != partitionSize - 1) {
          println();
        }
      }
      decrementIndent();
      println();
      print(')');
    }

    if (x.getLifecycle() != null) {
      println();
      print0(ucase ? "LIFECYCLE " : "lifecycle ");
      x.getLifecycle().accept(this);
    }

    if (x.getSelect() != null) {
      println();
      print0(ucase ? "AS" : "as");
      println();
      x.getSelect().accept(this);
    }

    return false;
  }

  @Override
  public void endVisit(OdpsCreateTableStatement x) {
    super.endVisit((SQLCreateTableStatement) x);
  }

  public SQLStatement parseInsert() {
    OdpsInsertStatement stmt = new OdpsInsertStatement();

    return stmt;
  }

  @Override
  public void endVisit(OdpsInsertStatement x) {

  }

  @Override
  public boolean visit(OdpsInsertStatement x) {
    SQLTableSource from = x.getFrom();
    if (x.getFrom() != null) {
      if (from instanceof SQLSubqueryTableSource) {
        SQLSelect select = ((SQLSubqueryTableSource) from).getSelect();
        print0(ucase ? "FROM (" : "from (");
        incrementIndent();
        println();
        select.accept(this);
        decrementIndent();
        println();
        print0(") ");
        print0(x.getFrom().getAlias());
      } else {
        print0(ucase ? "FROM " : "from ");
        from.accept(this);
      }
      println();
    }

    for (int i = 0; i < x.getItems().size(); ++i) {
      OdpsInsert insert = x.getItems().get(i);
      if (i != 0) {
        println();
      }
      insert.accept(this);
    }
    return false;
  }

  @Override
  public void endVisit(OdpsInsert x) {

  }

  @Override
  public boolean visit(OdpsInsert x) {
    if (x.hasBeforeComment()) {
      printComment(x.getBeforeCommentsDirect(), "\n");
      println();
    }
    if (x.isOverwrite()) {
      print0(ucase ? "INSERT OVERWRITE TABLE " : "insert overwrite table ");
    } else {
      print0(ucase ? "INSERT INTO TABLE " : "insert into table ");
    }
    x.getTableSource().accept(this);

    int partitions = x.getPartitions().size();
    if (partitions > 0) {
      print0(ucase ? " PARTITION (" : " partition (");
      for (int i = 0; i < partitions; ++i) {
        if (i != 0) {
          print0(", ");
        }

        SQLAssignItem assign = x.getPartitions().get(i);
        assign.getTarget().accept(this);

        if (assign.getValue() != null) {
          print('=');
          assign.getValue().accept(this);
        }
      }
      print(')');
    }
    println();
    x.getQuery().accept(this);

    return false;
  }

  public boolean visit(SQLCaseExpr x) {
    incrementIndent();
    print0(ucase ? "CASE " : "case ");
    if (x.getValueExpr() != null) {
      x.getValueExpr().accept(this);
      println();
    }

    for (int i = 0, size = x.getItems().size(); i < size; ++i) {
      println();
      x.getItems().get(i).accept(this);
    }

    if (x.getElseExpr() != null) {
      println();
      print0(ucase ? "ELSE " : "else ");
      x.getElseExpr().accept(this);
    }

    decrementIndent();
    println();
    print0(ucase ? "END" : "end");

    return false;
  }

  protected void printSelectList(List<SQLSelectItem> selectList) {
    incrementIndent();
    for (int i = 0, size = selectList.size(); i < size; ++i) {
      SQLSelectItem selectItem = selectList.get(i);

      if (i != 0) {
        SQLSelectItem preSelectItem = selectList.get(i - 1);
        if (preSelectItem.hasAfterComment()) {
          print(' ');
          printComment(preSelectItem.getAfterCommentsDirect(), "\n");
        }

        println();
        print0(", ");
      }

      selectItem.accept(this);

      if (i == selectList.size() - 1 && selectItem.hasAfterComment()) {
        print(' ');
        printComment(selectItem.getAfterCommentsDirect(), "\n");
      }
    }
    decrementIndent();
  }

  @Override
  public boolean visit(SQLSubqueryTableSource x) {
    print('(');
    incrementIndent();
    println();
    x.getSelect().accept(this);
    decrementIndent();
    println();
    print(')');

    if (x.getAlias() != null) {
      print(' ');
      print0(x.getAlias());
    }

    return false;
  }

  @Override
  public boolean visit(SQLJoinTableSource x) {
    x.getLeft().accept(this);

    if (x.getJoinType() == JoinType.COMMA) {
      print(',');
    } else {
      println();
      printJoinType(x.getJoinType());
    }
    print(' ');
    x.getRight().accept(this);

    if (x.getCondition() != null) {
      println();
      print0(ucase ? "ON " : "on ");
      incrementIndent();
      x.getCondition().accept(this);
      decrementIndent();
    }

    if (x.getUsing().size() > 0) {
      print0(ucase ? " USING (" : " using (");
      printAndAccept(x.getUsing(), ", ");
      print(')');
    }

    if (x.getAlias() != null) {
      print0(ucase ? " AS " : " as ");
      print0(x.getAlias());
    }

    return false;
  }

  @Override
  public void endVisit(OdpsUDTFSQLSelectItem x) {

  }

  @Override
  public boolean visit(OdpsUDTFSQLSelectItem x) {
    x.getExpr().accept(this);

    println();
    print0(ucase ? "AS (" : "as (");

    int aliasSize = x.getAliasList().size();
    if (aliasSize > 5) {
      incrementIndent();
      println();
    }

    for (int i = 0; i < aliasSize; ++i) {
      if (i != 0) {
        if (aliasSize > 5) {
          println(",");
        } else {
          print0(", ");
        }
      }
      print0(x.getAliasList().get(i));
    }

    if (aliasSize > 5) {
      decrementIndent();
      println();
    }
    print(')');

    return false;
  }

  @Override
  public void endVisit(OdpsShowPartitionsStmt x) {

  }

  @Override
  public boolean visit(OdpsShowPartitionsStmt x) {
    print0(ucase ? "SHOW PARTITIONS " : "show partitions ");
    x.getTableSource().accept(this);
    return false;
  }

  @Override
  public void endVisit(OdpsShowStatisticStmt x) {

  }

  @Override
  public boolean visit(OdpsShowStatisticStmt x) {
    print0(ucase ? "SHOW STATISTIC " : "show statistic ");
    x.getTableSource().accept(this);
    return false;
  }

  @Override
  public void endVisit(OdpsSetLabelStatement x) {

  }

  @Override
  public boolean visit(OdpsSetLabelStatement x) {
    print0(ucase ? "SET LABEL " : "set label ");
    print0(x.getLabel());
    print0(ucase ? " TO " : " to ");

    if (x.getUser() != null) {
      print0(ucase ? "USER " : "user ");
      x.getUser().accept(this);
    } else if (x.getTable() != null) {
      print0(ucase ? "TABLE " : "table ");
      x.getTable().accept(this);
      if (x.getColumns().size() > 0) {
        print('(');
        printAndAccept(x.getColumns(), ", ");
        print(')');
      }
    }

    return false;
  }

  @Override
  public void endVisit(OdpsSelectQueryBlock x) {

  }

  @Override
  public boolean visit(OdpsSelectQueryBlock x) {
    if (isPrettyFormat() && x.hasBeforeComment()) {
      printComment(x.getBeforeCommentsDirect(), "\n");
      println();
    }

    print0(ucase ? "SELECT " : "select ");

    List<SQLHint> hints = x.getHintsDirect();
    if (hints != null) {
      printAndAccept(hints, " ");
      print(' ');
    }

    if (SQLSetQuantifier.ALL == x.getDistionOption()) {
      print0(ucase ? "ALL " : "all ");
    } else if (SQLSetQuantifier.DISTINCT == x.getDistionOption()) {
      print0(ucase ? "DISTINCT " : "distinct ");
    } else if (SQLSetQuantifier.UNIQUE == x.getDistionOption()) {
      print0(ucase ? "UNIQUE " : "unique ");
    }

    printSelectList(x.getSelectList());

    if (x.getFrom() != null) {
      println();
      print0(ucase ? "FROM " : "from ");
      x.getFrom().accept(this);
    }

    if (x.getWhere() != null) {
      println();
      print0(ucase ? "WHERE " : "where ");
      if (x.getWhere().hasBeforeComment() && isPrettyFormat()) {
        printlnComments(x.getWhere().getBeforeCommentsDirect());
      }

      x.getWhere().setParent(x);
      x.getWhere().accept(this);
      if (x.getWhere().hasAfterComment() && isPrettyFormat()) {
        print(' ');
        printComment(x.getWhere().getAfterCommentsDirect(), "\n");
      }
    }

    if (x.getGroupBy() != null) {
      println();
      x.getGroupBy().accept(this);
    }

    if (x.getOrderBy() != null) {
      println();
      x.getOrderBy().accept(this);
    }

    if (x.getDistributeBy().size() > 0) {
      println();
      print0(ucase ? "DISTRIBUTE BY " : "distribute by ");
      printAndAccept(x.getDistributeBy(), ", ");

      if (!x.getSortBy().isEmpty()) {
        print0(ucase ? " SORT BY " : " sort by ");
        printAndAccept(x.getSortBy(), ", ");
      }
    }

    if (x.getLimit() != null) {
      println();
      x.getLimit().accept(this);
    }

    return false;
  }

  public boolean visit(SQLOrderBy x) {
    int itemSize = x.getItems().size();
    if (itemSize > 0) {
      print0(ucase ? "ORDER BY " : "order by ");
      incrementIndent();
      for (int i = 0; i < itemSize; ++i) {
        if (i != 0) {
          println(", ");
        }
        x.getItems().get(i).accept(this);
      }
      decrementIndent();
    }

    return false;
  }

  @Override
  public void endVisit(OdpsAnalyzeTableStatement x) {

  }

  @Override
  public boolean visit(OdpsAnalyzeTableStatement x) {
    print0(ucase ? "ANALYZE TABLE " : "analyze table ");
    x.getTable().accept(this);

    if (x.getPartition().size() > 0) {
      print0(ucase ? " PARTITION (" : " partition (");
      printAndAccept(x.getPartition(), ", ");
      print(')');
    }

    print0(ucase ? " COMPUTE STATISTICS" : " compute statistics");

    return false;
  }

  @Override
  public void endVisit(OdpsAddStatisticStatement x) {

  }

  @Override
  public boolean visit(OdpsAddStatisticStatement x) {
    print0(ucase ? "ADD STATISTIC " : "add statistic ");
    x.getTable().accept(this);
    print(' ');
    x.getStatisticClause().accept(this);

    return false;
  }

  @Override
  public void endVisit(OdpsRemoveStatisticStatement x) {

  }

  @Override
  public boolean visit(OdpsRemoveStatisticStatement x) {
    print0(ucase ? "REMOVE STATISTIC " : "remove statistic ");
    x.getTable().accept(this);
    print(' ');
    x.getStatisticClause().accept(this);

    return false;
  }

  @Override
  public void endVisit(OdpsStatisticClause.TableCount x) {

  }

  @Override
  public boolean visit(OdpsStatisticClause.TableCount x) {
    print0(ucase ? "TABLE_COUNT" : "table_count");
    return false;
  }

  @Override
  public void endVisit(OdpsStatisticClause.ExpressionCondition x) {

  }

  @Override
  public boolean visit(OdpsStatisticClause.ExpressionCondition x) {
    print0(ucase ? "EXPRESSION_CONDITION " : "expression_condition ");
    x.getExpr().accept(this);
    return false;
  }

  @Override
  public void endVisit(OdpsStatisticClause.NullValue x) {

  }

  @Override
  public boolean visit(OdpsStatisticClause.NullValue x) {
    print0(ucase ? "NULL_VALUE " : "null_value ");
    x.getColumn().accept(this);
    return false;
  }

  @Override
  public void endVisit(OdpsStatisticClause.ColumnSum x) {

  }

  @Override
  public boolean visit(OdpsStatisticClause.ColumnSum x) {
    print0(ucase ? "COLUMN_SUM " : "column_sum ");
    x.getColumn().accept(this);
    return false;
  }

  @Override
  public void endVisit(OdpsStatisticClause.ColumnMax x) {

  }

  @Override
  public boolean visit(OdpsStatisticClause.ColumnMax x) {
    print0(ucase ? "COLUMN_MAX " : "column_max ");
    x.getColumn().accept(this);
    return false;
  }

  @Override
  public void endVisit(OdpsStatisticClause.ColumnMin x) {

  }

  @Override
  public boolean visit(OdpsStatisticClause.ColumnMin x) {
    print0(ucase ? "COLUMN_MIN " : "column_min ");
    x.getColumn().accept(this);
    return false;
  }

  @Override
  public void endVisit(OdpsReadStatement x) {

  }

  @Override
  public boolean visit(OdpsReadStatement x) {
    print0(ucase ? "READ " : "read ");
    x.getTable().accept(this);

    if (x.getColumns().size() > 0) {
      print0(" (");
      printAndAccept(x.getColumns(), ", ");
      print(')');
    }

    if (x.getPartition().size() > 0) {
      print0(ucase ? " PARTITION (" : " partition (");
      printAndAccept(x.getPartition(), ", ");
      print(')');
    }

    if (x.getRowCount() != null) {
      print(' ');
      x.getRowCount().accept(this);
    }

    return false;
  }

  public boolean visit(SQLMethodInvokeExpr x) {
    if (x.getOwner() != null) {
      x.getOwner().accept(this);
      print(':');
    }
    printFunctionName(x.getMethodName());
    print('(');
    printAndAccept(x.getParameters(), ", ");
    print(')');
    return false;
  }

  protected void printJoinType(JoinType joinType) {
    if (joinType.equals(JoinType.LEFT_OUTER_JOIN)) {
      print0(ucase ? "LEFT OUTER JOIN" : "left outer join");
    } else if (joinType.equals(JoinType.RIGHT_OUTER_JOIN)) {
      print0(ucase ? "RIGHT OUTER JOIN" : "right outer join");
    } else if (joinType.equals(JoinType.FULL_OUTER_JOIN)) {
      print0(ucase ? "FULL OUTER JOIN" : "full outer join");
    } else {
      print0(ucase ? joinType.name : joinType.name_lcase);
    }
  }

  public boolean visit(SQLDataType x) {
    String dataTypeName = x.getName();
    print0(ucase ? dataTypeName.toUpperCase() : dataTypeName.toLowerCase());
    if (x.getArguments().size() > 0) {
      print('(');
      printAndAccept(x.getArguments(), ", ");
      print(')');
    }

    return false;
  }

  protected void printFunctionName(String name) {
    String upperName = name.toUpperCase();
    if (builtInFunctions.contains(upperName)) {
      print0(ucase ? upperName : name);
    } else {
      print0(name);
    }
  }

  @Override
  public void endVisit(OdpsShowGrantsStmt x) {

  }

  @Override
  public boolean visit(OdpsShowGrantsStmt x) {
    print0(ucase ? "SHOW GRANTS" : "show grants");
    if (x.getUser() != null) {
      print0(ucase ? " FOR " : " for ");
      x.getUser().accept(this);
    }

    if (x.getObjectType() != null) {
      print0(ucase ? " ON TYPE " : " on type ");
      x.getObjectType().accept(this);
    }
    return false;
  }

  @Override
  public void endVisit(OdpsListStmt x) {

  }

  @Override
  public boolean visit(OdpsListStmt x) {
    print0(ucase ? "LIST " : "list ");
    if (x.getObject() != null) {
      x.getObject().accept(this);
    }
    return false;
  }

  @Override
  public void endVisit(OdpsGrantStmt x) {

  }

  @Override
  public boolean visit(OdpsGrantStmt x) {
    print0(ucase ? "GRANT " : "grant ");
    if (x.isSuper()) {
      print0(ucase ? "SUPER " : "super ");
    }

    if (x.isLabel()) {
      print0(ucase ? "LABEL " : "label ");
      x.getLabel().accept(this);
      ;
    } else {
      printAndAccept(x.getPrivileges(), ", ");
    }

    if (x.getOn() != null) {
      print0(ucase ? " ON " : " on ");
      if (x.getObjectType() != null) {
        print0(ucase ? x.getObjectType().name() : x.getObjectType().name().toLowerCase());
        print(' ');
      }
      x.getOn().accept(this);

      if (x.getColumns().size() > 0) {
        print('(');
        printAndAccept(x.getColumns(), ", ");
        print(')');
      }
    }

    if (x.getTo() != null) {
      print0(ucase ? " TO " : " to ");
      if (x.getSubjectType() != null) {
        print0(x.getSubjectType().name());
        print(' ');
      }
      x.getTo().accept(this);
    }

    if (x.getExpire() != null) {
      print0(ucase ? " WITH EXP " : " with exp ");
      x.getExpire().accept(this);
    }

    return false;
  }

  @Override
  public void endVisit(OdpsLateralViewTableSource x) {

  }

  @Override
  public boolean visit(OdpsLateralViewTableSource x) {
    x.getTableSource().accept(this);
    incrementIndent();
    println();
    print0(ucase ? "LATERAL VIEW " : "lateral view ");
    x.getMethod().accept(this);
    print(' ');
    print0(x.getAlias());
    print0(ucase ? " AS " : " as ");
    printAndAccept(x.getColumns(), ", ");
    decrementIndent();
    return false;
  }

  public boolean visit(SQLCharExpr x) {
    String text = x.getText();
    if (text == null) {
      print0(ucase ? "NULL" : "null");
    } else {
      StringBuilder buf = new StringBuilder(text.length() + 2);
      buf.append('\'');
      for (int i = 0; i < text.length(); ++i) {
        char ch = text.charAt(i);
        switch (ch) {
          case '\\':
            buf.append("\\\\");
            break;
          case '\'':
            buf.append("\\'");
            break;
          case '\0':
            buf.append("\\0");
            break;
          case '\n':
            buf.append("\\n");
            break;
          default:
            buf.append(ch);
            break;
        }
      }
      buf.append('\'');

      print0(buf.toString());
    }

    return false;
  }

  @Override
  public void endVisit(OdpsValuesTableSource x) {

  }

  @Override
  public boolean visit(OdpsValuesTableSource x) {
    print0(ucase ? "VALUES " : "values ");
    printAndAccept(x.getValues(), ", ");

    print(' ');
    print0(x.getAlias());
    print0(" (");
    printAndAccept(x.getColumns(), ", ");
    print(')');

    return false;
  }

  @Override
  public boolean visit(SQLAlterTableRenameColumn x) {
    print0(ucase ? "CHANGE COLUMN " : "change column ");
    x.getColumn().accept(this);
    print0(ucase ? " RENAME TO " : " rename to ");
    x.getTo().accept(this);
    return false;
  }
}
