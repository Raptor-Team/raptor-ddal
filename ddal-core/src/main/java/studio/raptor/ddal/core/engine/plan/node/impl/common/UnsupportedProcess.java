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

package studio.raptor.ddal.core.engine.plan.node.impl.common;

import studio.raptor.ddal.common.exception.GenericException;
import studio.raptor.ddal.common.exception.code.ProcessErrCodes;
import studio.raptor.ddal.core.engine.ProcessContext;
import studio.raptor.ddal.core.engine.plan.node.ProcessNode;

/**
 * 不支持的操作节点
 *
 * @author Sam
 * @since 3.0.0
 */
public class UnsupportedProcess extends ProcessNode {

  @Override
  protected void execute(ProcessContext context) {
    throw new GenericException(ProcessErrCodes.PLAN_700, context.getOriginSql());
  }
}
