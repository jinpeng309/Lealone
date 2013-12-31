/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.codefollower.lealone.atomicdb.net;

import java.net.InetAddress;

import com.codefollower.lealone.atomicdb.db.ConsistencyLevel;
import com.codefollower.lealone.atomicdb.io.IVersionedSerializer;
import com.codefollower.lealone.atomicdb.service.StorageProxy;

public class WriteCallbackInfo extends CallbackInfo
{
    public final MessageOut sentMessage;
    private final ConsistencyLevel consistencyLevel;

    public WriteCallbackInfo(InetAddress target, IAsyncCallback callback, MessageOut message, IVersionedSerializer<?> serializer, ConsistencyLevel consistencyLevel)
    {
        super(target, callback, serializer);
        assert message != null;
        this.sentMessage = message;
        this.consistencyLevel = consistencyLevel;
    }

    public boolean shouldHint()
    {
        return sentMessage.verb != MessagingService.Verb.COUNTER_MUTATION
               && consistencyLevel != ConsistencyLevel.ANY
               && StorageProxy.shouldHint(target);
    }
}