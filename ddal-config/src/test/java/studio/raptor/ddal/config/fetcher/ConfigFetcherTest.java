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

package studio.raptor.ddal.config.fetcher;

import java.util.Iterator;
import java.util.ServiceLoader;
import org.junit.Test;

/**
 * 功能描述
 *
 * @author Charley
 * @since 1.0
 */
public class ConfigFetcherTest {

  @Test
  public void testConfigFetcher() throws Exception {
    ServiceLoader<ConfigFetcher> sl = ServiceLoader.load(ConfigFetcher.class);
    Iterator<ConfigFetcher> fetchers = sl.iterator();
    while (fetchers.hasNext()) {
      ConfigFetcher fetcher = fetchers.next();
      fetcher.getFileString("");
      fetcher.getProperties("");
    }
  }

}
