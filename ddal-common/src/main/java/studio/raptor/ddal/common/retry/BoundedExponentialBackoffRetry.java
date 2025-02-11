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

package studio.raptor.ddal.common.retry;

import com.google.common.annotations.VisibleForTesting;


/**
 * Retry policy that retries a set number of times with an increasing (up to a maximum bound) sleep
 * time between retries
 */
public class BoundedExponentialBackoffRetry extends ExponentialBackoffRetry {
    private final int maxSleepTimeMs;


    /**
     * @param baseSleepTimeMs initial amount of time to wait between retries
     * @param maxSleepTimeMs  maximum amount of time to wait between retries
     * @param maxRetries      maximum number of times to retry
     */
    public BoundedExponentialBackoffRetry(int baseSleepTimeMs, int maxSleepTimeMs, int maxRetries) {
        super(baseSleepTimeMs, maxRetries);
        this.maxSleepTimeMs = maxSleepTimeMs;
    }


    @VisibleForTesting
    public int getMaxSleepTimeMs() {
        return maxSleepTimeMs;
    }


    @Override
    protected int getSleepTimeMs(int retryCount, long elapsedTimeMs) {
        return Math.min(maxSleepTimeMs, super.getSleepTimeMs(retryCount, elapsedTimeMs));
    }
}