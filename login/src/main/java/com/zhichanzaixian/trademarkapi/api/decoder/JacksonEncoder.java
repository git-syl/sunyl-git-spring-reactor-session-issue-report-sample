/**
 * Copyright 2012-2020 The Feign Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
/**
 * 为解决自带的Encoder报错。
 * feign.codec.EncodeException: class com.zhichanzaixian.trademarkapi.api.result.sbj.SaveParams is not a type supported by this encoder.
 *
 * feign.codec.EncodeException: Could not write request: no suitable HttpMessageConverter
 * found for request type [com.zhichanzaixian.trademarkapi.api.result.sbj.SaveParams] and content type [application/json]
 */
package com.zhichanzaixian.trademarkapi.api.decoder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import feign.RequestTemplate;
import feign.Util;
import feign.codec.EncodeException;
import feign.codec.Encoder;

import java.lang.reflect.Type;
import java.util.Collections;

public class JacksonEncoder implements Encoder {

  private final ObjectMapper mapper;

  public JacksonEncoder() {
    this(Collections.<Module>emptyList());
  }

  public JacksonEncoder(Iterable<Module> modules) {
    this(new ObjectMapper()
        .setSerializationInclusion(JsonInclude.Include.NON_NULL)
        .configure(SerializationFeature.INDENT_OUTPUT, true)
        .registerModules(modules)
            //add:
           // .setDateFormat(new SimpleDateFormat("yyyy-MM-dd"))
           // .registerModule(new ParameterNamesModule()).registerModule(new Jdk8Module())
           // .registerModule(new JavaTimeModule())
    );
  }

  public JacksonEncoder(ObjectMapper mapper) {
    this.mapper = mapper;
  }

  @Override
  public void encode(Object object, Type bodyType, RequestTemplate template) {
    try {
      JavaType javaType = mapper.getTypeFactory().constructType(bodyType);
      template.body(mapper.writerFor(javaType).writeValueAsBytes(object), Util.UTF_8);
    } catch (JsonProcessingException e) {
      throw new EncodeException(e.getMessage(), e);
    }
  }
}