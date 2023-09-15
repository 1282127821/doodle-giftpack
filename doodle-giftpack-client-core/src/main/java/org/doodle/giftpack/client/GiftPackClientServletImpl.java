/*
 * Copyright (c) 2022-present Doodle. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.doodle.giftpack.client;

import java.util.Collections;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.doodle.design.common.Result;
import org.doodle.design.giftpack.*;
import org.doodle.design.giftpack.model.payload.reply.GiftPackCreateReply;
import org.doodle.design.giftpack.model.payload.reply.GiftPackHashCreateReply;
import org.doodle.design.giftpack.model.payload.reply.GiftPackHashPageReply;
import org.doodle.design.giftpack.model.payload.reply.GiftPackHashQueryReply;
import org.doodle.design.giftpack.model.payload.reply.GiftPackHashUseReply;
import org.doodle.design.giftpack.model.payload.reply.GiftPackPageReply;
import org.doodle.design.giftpack.model.payload.reply.GiftPackQueryReply;
import org.doodle.design.giftpack.model.payload.request.GiftPackCreateRequest;
import org.doodle.design.giftpack.model.payload.request.GiftPackHashCreateRequest;
import org.doodle.design.giftpack.model.payload.request.GiftPackHashPageRequest;
import org.doodle.design.giftpack.model.payload.request.GiftPackHashQueryRequest;
import org.doodle.design.giftpack.model.payload.request.GiftPackHashUseRequest;
import org.doodle.design.giftpack.model.payload.request.GiftPackPageRequest;
import org.doodle.design.giftpack.model.payload.request.GiftPackQueryRequest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class GiftPackClientServletImpl implements GiftPackClientServlet {
  RestTemplate restTemplate;

  static final ParameterizedTypeReference<Result<GiftPackHashUseReply>> HASH_USE_RESPONSE_TYPE =
      new ParameterizedTypeReference<>() {};

  static final ParameterizedTypeReference<Result<GiftPackHashPageReply>> HASH_PAGE_RESPONSE_TYPE =
      new ParameterizedTypeReference<>() {};

  static final ParameterizedTypeReference<Result<GiftPackHashCreateReply>>
      HASH_CREATE_RESPONSE_TYPE = new ParameterizedTypeReference<>() {};

  static final ParameterizedTypeReference<Result<GiftPackHashQueryReply>> HASH_QUERY_RESPONSE_TYPE =
      new ParameterizedTypeReference<>() {};

  static final ParameterizedTypeReference<Result<GiftPackPageReply>> PACK_PAGE_RESPONSE_TYPE =
      new ParameterizedTypeReference<>() {};

  static final ParameterizedTypeReference<Result<GiftPackQueryReply>> PACK_QUERY_RESPONSE_TYPE =
      new ParameterizedTypeReference<>() {};

  static final ParameterizedTypeReference<Result<GiftPackCreateReply>> PACK_CREATE_RESPONSE_TYPE =
      new ParameterizedTypeReference<>() {};

  @Override
  public Result<GiftPackHashUseReply> use(GiftPackHashUseRequest request) {
    return this.restTemplate
        .exchange(
            GiftPackHashUseOps.Servlet.USE_MAPPING,
            HttpMethod.POST,
            new HttpEntity<>(request, createHttpHeaders()),
            HASH_USE_RESPONSE_TYPE)
        .getBody();
  }

  @Override
  public Result<GiftPackHashPageReply> page(GiftPackHashPageRequest request) {
    return this.restTemplate
        .exchange(
            GiftPackHashPageOps.Servlet.PAGE_MAPPING,
            HttpMethod.POST,
            new HttpEntity<>(request, createHttpHeaders()),
            HASH_PAGE_RESPONSE_TYPE)
        .getBody();
  }

  @Override
  public Result<GiftPackHashQueryReply> query(GiftPackHashQueryRequest request) {
    return this.restTemplate
        .exchange(
            GiftPackHashQueryOps.Servlet.QUERY_MAPPING,
            HttpMethod.POST,
            new HttpEntity<>(request, createHttpHeaders()),
            HASH_QUERY_RESPONSE_TYPE)
        .getBody();
  }

  @Override
  public Result<GiftPackHashCreateReply> create(GiftPackHashCreateRequest request) {
    return this.restTemplate
        .exchange(
            GiftPackHashCreateOps.Servlet.CREATE_MAPPING,
            HttpMethod.POST,
            new HttpEntity<>(request, createHttpHeaders()),
            HASH_CREATE_RESPONSE_TYPE)
        .getBody();
  }

  @Override
  public Result<GiftPackPageReply> page(GiftPackPageRequest request) {
    return this.restTemplate
        .exchange(
            GiftPackPageOps.Servlet.PAGE_MAPPING,
            HttpMethod.POST,
            new HttpEntity<>(request, createHttpHeaders()),
            PACK_PAGE_RESPONSE_TYPE)
        .getBody();
  }

  @Override
  public Result<GiftPackQueryReply> query(GiftPackQueryRequest request) {
    return this.restTemplate
        .exchange(
            GiftPackQueryOps.Servlet.QUERY_MAPPING,
            HttpMethod.POST,
            new HttpEntity<>(request, createHttpHeaders()),
            PACK_QUERY_RESPONSE_TYPE)
        .getBody();
  }

  @Override
  public Result<GiftPackCreateReply> create(GiftPackCreateRequest request) {
    return this.restTemplate
        .exchange(
            GiftPackCreateOps.Servlet.CREATE_MAPPING,
            HttpMethod.POST,
            new HttpEntity<>(request, createHttpHeaders()),
            PACK_CREATE_RESPONSE_TYPE)
        .getBody();
  }

  HttpHeaders createHttpHeaders() {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    return HttpHeaders.readOnlyHttpHeaders(headers);
  }
}
