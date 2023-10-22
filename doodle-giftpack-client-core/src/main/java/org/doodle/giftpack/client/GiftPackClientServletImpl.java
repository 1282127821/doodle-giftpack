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
import org.doodle.design.giftpack.model.payload.reply.GiftPackBatchPageReply;
import org.doodle.design.giftpack.model.payload.reply.GiftPackBatchQueryReply;
import org.doodle.design.giftpack.model.payload.reply.GiftPackGroupPageReply;
import org.doodle.design.giftpack.model.payload.reply.GiftPackGroupQueryReply;
import org.doodle.design.giftpack.model.payload.reply.GiftPackRoleUseReply;
import org.doodle.design.giftpack.model.payload.reply.GiftPackSpecPageReply;
import org.doodle.design.giftpack.model.payload.reply.GiftPackSpecQueryReply;
import org.doodle.design.giftpack.model.payload.request.GiftPackBatchPageRequest;
import org.doodle.design.giftpack.model.payload.request.GiftPackBatchQueryRequest;
import org.doodle.design.giftpack.model.payload.request.GiftPackGroupPageRequest;
import org.doodle.design.giftpack.model.payload.request.GiftPackGroupQueryRequest;
import org.doodle.design.giftpack.model.payload.request.GiftPackRoleUseRequest;
import org.doodle.design.giftpack.model.payload.request.GiftPackSpecPageRequest;
import org.doodle.design.giftpack.model.payload.request.GiftPackSpecQueryRequest;
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

  static final ParameterizedTypeReference<Result<GiftPackRoleUseReply>> ROLE_USE_REPLY =
      new ParameterizedTypeReference<>() {};

  static final ParameterizedTypeReference<Result<GiftPackGroupPageReply>> GROUP_PAGE_REPLY =
      new ParameterizedTypeReference<>() {};

  static final ParameterizedTypeReference<Result<GiftPackGroupQueryReply>> GROUP_QUERY_REPLY =
      new ParameterizedTypeReference<>() {};

  static final ParameterizedTypeReference<Result<GiftPackBatchPageReply>> BATCH_PAGE_REPLY =
      new ParameterizedTypeReference<>() {};

  static final ParameterizedTypeReference<Result<GiftPackBatchQueryReply>> BATCH_QUERY_REPLY =
      new ParameterizedTypeReference<>() {};

  static final ParameterizedTypeReference<Result<GiftPackSpecPageReply>> SPEC_PAGE_REPLY =
      new ParameterizedTypeReference<>() {};

  static final ParameterizedTypeReference<Result<GiftPackSpecQueryReply>> SPEC_QUERY_REPLY =
      new ParameterizedTypeReference<>() {};

  @Override
  public Result<GiftPackRoleUseReply> use(GiftPackRoleUseRequest request) {
    return restTemplate
        .exchange(
            GiftPackRoleUseOps.Servlet.USE_MAPPING,
            HttpMethod.POST,
            new HttpEntity<>(request, createHttpHeaders()),
            ROLE_USE_REPLY)
        .getBody();
  }

  @Override
  public Result<GiftPackGroupPageReply> page(GiftPackGroupPageRequest request) {
    return restTemplate
        .exchange(
            GiftPackGroupPageOps.Servlet.PAGE_MAPPING,
            HttpMethod.POST,
            new HttpEntity<>(request, createHttpHeaders()),
            GROUP_PAGE_REPLY)
        .getBody();
  }

  @Override
  public Result<GiftPackGroupQueryReply> query(GiftPackGroupQueryRequest request) {
    return restTemplate
        .exchange(
            GIftPackGroupQueryOps.Servlet.QUERY_MAPPING,
            HttpMethod.POST,
            new HttpEntity<>(request, createHttpHeaders()),
            GROUP_QUERY_REPLY)
        .getBody();
  }

  @Override
  public Result<GiftPackBatchPageReply> page(GiftPackBatchPageRequest request) {
    return restTemplate
        .exchange(
            GiftPackBatchPageOps.Servlet.PAGE_MAPPING,
            HttpMethod.POST,
            new HttpEntity<>(request, createHttpHeaders()),
            BATCH_PAGE_REPLY)
        .getBody();
  }

  @Override
  public Result<GiftPackBatchQueryReply> query(GiftPackBatchQueryRequest request) {
    return restTemplate
        .exchange(
            GiftPackBatchQueryOps.Servlet.QUERY_MAPPING,
            HttpMethod.POST,
            new HttpEntity<>(request, createHttpHeaders()),
            BATCH_QUERY_REPLY)
        .getBody();
  }

  @Override
  public Result<GiftPackSpecPageReply> page(GiftPackSpecPageRequest request) {
    return restTemplate
        .exchange(
            GiftPackSpecPageOps.Servlet.PAGE_MAPPING,
            HttpMethod.POST,
            new HttpEntity<>(request, createHttpHeaders()),
            SPEC_PAGE_REPLY)
        .getBody();
  }

  @Override
  public Result<GiftPackSpecQueryReply> query(GiftPackSpecQueryRequest request) {
    return restTemplate
        .exchange(
            GiftPackSpecQueryOps.Servlet.QUERY_MAPPING,
            HttpMethod.POST,
            new HttpEntity<>(request, createHttpHeaders()),
            SPEC_QUERY_REPLY)
        .getBody();
  }

  protected HttpHeaders createHttpHeaders() {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    return HttpHeaders.readOnlyHttpHeaders(headers);
  }
}
