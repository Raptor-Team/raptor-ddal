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
import studio.raptor.sqlparser.ast.SQLExpr;
import studio.raptor.sqlparser.dialect.mysql.ast.MySqlObjectImpl;
import studio.raptor.sqlparser.dialect.mysql.visitor.MySqlASTVisitor;

public class MySqlRenameTableStatement extends MySqlStatementImpl {

  private List<Item> items = new ArrayList<Item>(2);

  public List<Item> getItems() {
    return items;
  }

  public void addItem(Item item) {
    if (item != null) {
      item.setParent(this);
    }
    this.items.add(item);
  }

  public void accept0(MySqlASTVisitor visitor) {
    if (visitor.visit(this)) {
      acceptChild(visitor, items);
    }
    visitor.endVisit(this);
  }

  public static class Item extends MySqlObjectImpl {

    private SQLExpr name;
    private SQLExpr to;

    public SQLExpr getName() {
      return name;
    }

    public void setName(SQLExpr name) {
      this.name = name;
    }

    public SQLExpr getTo() {
      return to;
    }

    public void setTo(SQLExpr to) {
      this.to = to;
    }

    @Override
    public void accept0(MySqlASTVisitor visitor) {
      if (visitor.visit(this)) {
        acceptChild(visitor, name);
        acceptChild(visitor, to);
      }
      visitor.endVisit(this);
    }

  }
}
