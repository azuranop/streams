/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
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

package org.apache.streams.data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.streams.data.util.JsonUtil;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;

import static org.apache.streams.data.util.MoreoverTestUtil.test;

/**
 * Tests ability to serialize moreover json Strings
 */
@Ignore("ignore until test resources are available.")
public class MoreoverJsonActivitySerializerTest {
    JsonNode json;
    ActivitySerializer serializer = new MoreoverJsonActivitySerializer();
    ObjectMapper mapper;

    @Before
    public void setup() throws IOException {
        json = JsonUtil.getFromFile("classpath:org/apache/streams/data/moreover.json");

        mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, Boolean.FALSE);
        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, Boolean.TRUE);
        mapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, Boolean.TRUE);
    }

    @Test
    public void loadData() throws Exception {
        for (JsonNode item : json) {
            test(serializer.deserialize(getString(item)));
        }
    }


    private String getString(JsonNode jsonNode)  {
        try {
            return new ObjectMapper().writeValueAsString(jsonNode);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
