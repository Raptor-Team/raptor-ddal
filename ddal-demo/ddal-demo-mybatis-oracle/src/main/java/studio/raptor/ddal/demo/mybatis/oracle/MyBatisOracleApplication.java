/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package studio.raptor.ddal.demo.mybatis.oracle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import studio.raptor.ddal.demo.mybatis.oracle.service.ClobService;

/**
 * @author Sam
 * @since 3.0.0
 */
public class MyBatisOracleApplication {

  private final static Logger LOG = LoggerFactory.getLogger(MyBatisOracleApplication.class);

  public static void main(String[] args) {
    ApplicationContext application = new ClassPathXmlApplicationContext("spring/mybatis-config.xml");
//    TableService tableService = application.getBean(TableService.class);
//    Long id = new Random().nextInt(1000) + 1000L;
//    LOG.info("#### Random id {}", id);
////    tableService.createTable(id, "Hello, Raptor!");
//    Table table = tableService.getTableById(id);
//    LOG.info("#### Query result {}", table);

    ClobService clobService = application.getBean(ClobService.class);
    clobService.getClob();
  }
}
