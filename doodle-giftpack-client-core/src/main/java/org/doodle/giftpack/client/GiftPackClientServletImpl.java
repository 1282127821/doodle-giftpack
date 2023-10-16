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
import org.doodle.design.giftpack.model.payload.reply.GiftPackCodePageReply;
import org.doodle.design.giftpack.model.payload.reply.GiftPackCodeQueryReply;
import org.doodle.design.giftpack.model.payload.reply.GiftPackGiftPageReply;
import org.doodle.design.giftpack.model.payload.reply.GiftPackGiftQueryReply;
import org.doodle.design.giftpack.model.payload.reply.GiftPackPackPageReply;
import org.doodle.design.giftpack.model.payload.reply.GiftPackPackQueryReply;
import org.doodle.design.giftpack.model.payload.reply.GiftPackPackUseReply;
import org.doodle.design.giftpack.model.payload.reply.GiftPackVisionPageReply;
import org.doodle.design.giftpack.model.payload.reply.GiftPackVisionQueryReply;
import org.doodle.design.giftpack.model.payload.request.GiftPackCodePageRequest;
import org.doodle.design.giftpack.model.payload.request.GiftPackCodeQueryRequest;
import org.doodle.design.giftpack.model.payload.request.GiftPackGiftPageRequest;
import org.doodle.design.giftpack.model.payload.request.GiftPackGiftQueryRequest;
import org.doodle.design.giftpack.model.payload.request.GiftPackPackPageRequest;
import org.doodle.design.giftpack.model.payload.request.GiftPackPackQueryRequest;
import org.doodle.design.giftpack.model.payload.request.GiftPackPackUseRequest;
import org.doodle.design.giftpack.model.payload.request.GiftPackVisionPageRequest;
import org.doodle.design.giftpack.model.payload.request.GiftPackVisionQueryRequest;
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

  static final ParameterizedTypeReference<Result<GiftPackVisionQueryReply>> VISION_QUERY_REPLY =
      new ParameterizedTypeReference<>() {};
  static final ParameterizedTypeReference<Result<GiftPackVisionPageReply>> VISION_PAGE_REPLY =
      new ParameterizedTypeReference<>() {};
  static final ParameterizedTypeReference<Result<GiftPackCodePageReply>> CODE_PAGE_REPLY =
      new ParameterizedTypeReference<>() {};
  static final ParameterizedTypeReference<Result<GiftPackCodeQueryReply>> CODE_QUERY_REPLY =
      new ParameterizedTypeReference<>() {};
  static final ParameterizedTypeReference<Result<GiftPackGiftPageReply>> GIFT_PAGE_REPLY =
      new ParameterizedTypeReference<>() {};
  static final ParameterizedTypeReference<Result<GiftPackGiftQueryReply>> GIFT_QUERY_REPLY =
      new ParameterizedTypeReference<>() {};
  static final ParameterizedTypeReference<Result<GiftPackPackPageReply>> PACK_PAGE_REPLY =
      new ParameterizedTypeReference<>() {};
  static final ParameterizedTypeReference<Result<GiftPackPackQueryReply>> PACK_QUERY_REPLY =
      new ParameterizedTypeReference<>() {};

  static final ParameterizedTypeReference<Result<GiftPackPackUseReply>> PACK_USE_REPLY =
      new ParameterizedTypeReference<>() {};

  @Override
  public Result<GiftPackVisionPageReply> page(GiftPackVisionPageRequest request) {
    return restTemplate
        .exchange(
            GiftPackVisionPageOps.Servlet.PAGE_MAPPING,
            HttpMethod.POST,
            new HttpEntity<>(request, createHttpHeaders()),
            VISION_PAGE_REPLY)
        .getBody();
  }

  @Override
  public Result<GiftPackVisionQueryReply> query(GiftPackVisionQueryRequest request) {
    return restTemplate
        .exchange(
            GiftPackVisionQueryOps.Servlet.QUERY_MAPPING,
            HttpMethod.POST,
            new HttpEntity<>(request, createHttpHeaders()),
            VISION_QUERY_REPLY)
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
  public Result<GiftPackGiftPageReply> page(GiftPackGiftPageRequest request) {
    return restTemplate
        .exchange(
            GiftPackGiftPageOps.Servlet.PAGE_MAPPING,
            HttpMethod.POST,
            new HttpEntity<>(request, createHttpHeaders()),
            GIFT_PAGE_REPLY)
        .getBody();
  }

  @Override
  public Result<GiftPackGiftQueryReply> query(GiftPackGiftQueryRequest request) {
    return restTemplate
        .exchange(
            GiftPackGiftQueryOps.Servlet.QUERY_MAPPING,
            HttpMethod.POST,
            new HttpEntity<>(request, createHttpHeaders()),
            GIFT_QUERY_REPLY)
        .getBody();
  }

  @Override
  public Result<GiftPackPackPageReply> page(GiftPackPackPageRequest request) {
    return restTemplate
        .exchange(
            GiftPackPackPageOps.Servlet.PAGE_MAPPING,
            HttpMethod.POST,
            new HttpEntity<>(request, createHttpHeaders()),
            PACK_PAGE_REPLY)
        .getBody();
  }

  @Override
  public Result<GiftPackPackQueryReply> query(GiftPackPackQueryRequest request) {
    return restTemplate
        .exchange(
            GiftPackPackQueryOps.Servlet.QUERY_MAPPING,
            HttpMethod.POST,
            new HttpEntity<>(request, createHttpHeaders()),
            PACK_QUERY_REPLY)
        .getBody();
  }

  @Override
  public Result<GiftPackPackUseReply> use(GiftPackPackUseRequest request) {
    return restTemplate
        .exchange(
            GiftPackPackUseOps.Servlet.USE_MAPPING,
            HttpMethod.POST,
            new HttpEntity<>(request, createHttpHeaders()),
            PACK_USE_REPLY)
        .getBody();
  }

  HttpHeaders createHttpHeaders() {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    return HttpHeaders.readOnlyHttpHeaders(headers);
  }
}
