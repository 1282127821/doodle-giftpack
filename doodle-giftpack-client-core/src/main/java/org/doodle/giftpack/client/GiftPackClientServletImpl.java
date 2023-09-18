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
import org.doodle.design.giftpack.GiftPackCodePageReply;
import org.doodle.design.giftpack.model.payload.reply.GiftPackCodeCreateRequest;
import org.doodle.design.giftpack.model.payload.reply.GiftPackCodePageRequest;
import org.doodle.design.giftpack.model.payload.reply.GiftPackCodeQueryRequest;
import org.doodle.design.giftpack.model.payload.reply.GiftPackPlacePageRequest;
import org.doodle.design.giftpack.model.payload.reply.GiftPackPlaceQueryRequest;
import org.doodle.design.giftpack.model.payload.reply.GiftPackUseReply;
import org.doodle.design.giftpack.model.payload.request.GiftPackCodeCreateReply;
import org.doodle.design.giftpack.model.payload.request.GiftPackCodeQueryReply;
import org.doodle.design.giftpack.model.payload.request.GiftPackPlacePageReply;
import org.doodle.design.giftpack.model.payload.request.GiftPackPlaceQueryReply;
import org.doodle.design.giftpack.model.payload.request.GiftPackUseRequest;
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

  static final ParameterizedTypeReference<Result<GiftPackCodeCreateReply>> CODE_CREATE_REPLY =
      new ParameterizedTypeReference<>() {};

  static final ParameterizedTypeReference<Result<GiftPackCodePageReply>> CODE_PAGE_REPLY =
      new ParameterizedTypeReference<>() {};

  static final ParameterizedTypeReference<Result<GiftPackCodeQueryReply>> CODE_QUERY_REPLY =
      new ParameterizedTypeReference<>() {};

  static final ParameterizedTypeReference<Result<GiftPackPlacePageReply>> PLACE_PAGE_REPLY =
      new ParameterizedTypeReference<>() {};

  static final ParameterizedTypeReference<Result<GiftPackPlaceQueryReply>> PLACE_QUERY_REPLY =
      new ParameterizedTypeReference<>() {};

  static final ParameterizedTypeReference<Result<GiftPackUseReply>> USE_REPLY =
      new ParameterizedTypeReference<>() {};

  @Override
  public Result<GiftPackCodeCreateReply> create(GiftPackCodeCreateRequest request) {
    return restTemplate
        .exchange(
            GiftPackCodeCreateOps.Servlet.CREATE_MAPPING,
            HttpMethod.POST,
            new HttpEntity<>(request, createHttpHeaders()),
            CODE_CREATE_REPLY)
        .getBody();
  }

  @Override
  public Result<GiftPackCodePageReply> page(GiftPackCodePageRequest request) {
    return restTemplate
        .exchange(
            GiftPackCodePageOps.Servlet.PAGE_MAPPING,
            HttpMethod.POST,
            new HttpEntity<>(request, createHttpHeaders()),
            CODE_PAGE_REPLY)
        .getBody();
  }

  @Override
  public Result<GiftPackCodeQueryReply> query(GiftPackCodeQueryRequest request) {
    return restTemplate
        .exchange(
            GiftPackCodeQueryOps.Servlet.QUERY_MAPPING,
            HttpMethod.POST,
            new HttpEntity<>(request, createHttpHeaders()),
            CODE_QUERY_REPLY)
        .getBody();
  }

  @Override
  public Result<GiftPackPlacePageReply> page(GiftPackPlacePageRequest request) {
    return restTemplate
        .exchange(
            GiftPackPlacePageOps.Servlet.PAGE_MAPPING,
            HttpMethod.POST,
            new HttpEntity<>(request, createHttpHeaders()),
            PLACE_PAGE_REPLY)
        .getBody();
  }

  @Override
  public Result<GiftPackPlaceQueryReply> query(GiftPackPlaceQueryRequest request) {
    return restTemplate
        .exchange(
            GiftPackPlaceQueryOps.Servlet.QUERY_MAPPING,
            HttpMethod.POST,
            new HttpEntity<>(request, createHttpHeaders()),
            PLACE_QUERY_REPLY)
        .getBody();
  }

  @Override
  public Result<GiftPackUseReply> use(GiftPackUseRequest request) {
    return restTemplate
        .exchange(
            GiftPackUseOps.Servlet.USE_MAPPING,
            HttpMethod.POST,
            new HttpEntity<>(request, createHttpHeaders()),
            USE_REPLY)
        .getBody();
  }

  HttpHeaders createHttpHeaders() {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    return HttpHeaders.readOnlyHttpHeaders(headers);
  }
}
