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
package studio.raptor.sqlparser.dialect.mysql.ast.statement;

import java.util.ArrayList;
import java.util.List;
import studio.raptor.sqlparser.ast.SQLCommentHint;
import studio.raptor.sqlparser.ast.SQLLimit;
import studio.raptor.sqlparser.ast.SQLOrderBy;
import studio.raptor.sqlparser.ast.statement.SQLDeleteStatement;
import studio.raptor.sqlparser.ast.statement.SQLTableSource;
import studio.raptor.sqlparser.dialect.mysql.visitor.MySqlASTVisitor;
import studio.raptor.sqlparser.dialect.mysql.visitor.MySqlOutputVisitor;
import studio.raptor.sqlparser.util.JdbcConstants;
import studio.raptor.sqlparser.visitor.SQLASTVisitor;

public class MySqlDeleteStatement extends SQLDeleteStatement {

  private boolean lowPriority = false;
  private boolean quick = false;
  private boolean ignore = false;

  private SQLTableSource using;
  private SQLOrderBy orderBy;
  private SQLLimit limit;

  private List<SQLCommentHint> hints;

  public MySqlDeleteStatement() {
    super(JdbcConstants.MYSQL);
  }

  public List<SQLCommentHint> getHints() {
    if (hints == null) {
      hints = new ArrayList<SQLCommentHint>();
    }
    return hints;
  }

  public int getHintsSize() {
    if (hints == null) {
      return 0;
    }

    return hints.size();
  }

  public boolean isLowPriority() {
    return lowPriority;
  }

  public void setLowPriority(boolean lowPriority) {
    this.lowPriority = lowPriority;
  }

  public boolean isQuick() {
    return quick;
  }

  public void setQuick(boolean quick) {
    this.quick = quick;
  }

  public boolean isIgnore() {
    return ignore;
  }

  public void setIgnore(boolean ignore) {
    this.ignore = ignore;
  }

  public SQLTableSource getUsing() {
    return using;
  }

  public void setUsing(SQLTableSource using) {
    this.using = using;
  }

  public SQLOrderBy getOrderBy() {
    return orderBy;
  }

  public void setOrderBy(SQLOrderBy orderBy) {
    this.orderBy = orderBy;
  }

  public SQLLimit getLimit() {
    return limit;
  }

  public void setLimit(SQLLimit limit) {
    if (limit != null) {
      limit.setParent(this);
    }
    this.limit = limit;
  }

  @Override
  protected void accept0(SQLASTVisitor visitor) {
    if (visitor instanceof MySqlASTVisitor) {
      accept0((MySqlASTVisitor) visitor);
    } else {
      throw new IllegalArgumentException(
          "not support visitor type : " + visitor.getClass().getName());
    }
  }

  public void output(StringBuffer buf) {
    new MySqlOutputVisitor(buf).visit(this);
  }

  protected void accept0(MySqlASTVisitor visitor) {
    if (visitor.visit(this)) {
      acceptChild(visitor, getTableSource());
      acceptChild(visitor, getWhere());
      acceptChild(visitor, getFrom());
      acceptChild(visitor, getUsing());
      acceptChild(visitor, orderBy);
      acceptChild(visitor, limit);
    }

    visitor.endVisit(this);
  }
}
