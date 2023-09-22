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
package org.doodle.giftpack.server;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.doodle.design.giftpack.*;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

@Controller
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class GiftPackServerVisionRSocketController
    implements GiftPackVisionQueryOps.RSocket, GiftPackVisionPageOps.RSocket {
  GiftPackServerMapper mapper;
  GiftPackServerVisionService visionService;

  @MessageMapping(GiftPackVisionPageOps.RSocket.PAGE_MAPPING)
  @Override
  public Mono<GiftPackVisionPageReply> page(GiftPackVisionPageRequest request) {
    return Mono.fromSupplier(request::getPage)
        .map(mapper::fromProto)
        .flatMap(visionService::pageMono)
        .map(mapper::toVisionInfoList)
        .map(mapper::toProto)
        .onErrorMap(GiftPackServerExceptions.Page::new);
  }

  @MessageExceptionHandler(GiftPackServerExceptions.Page.class)
  Mono<GiftPackVisionPageReply> onPageException(GiftPackServerExceptions.Page ignored) {
    return Mono.just(mapper.toVisionPageError(GiftPackErrorCode.FAILURE));
  }

  @MessageMapping(GiftPackVisionQueryOps.RSocket.QUERY_MAPPING)
  @Override
  public Mono<GiftPackVisionQueryReply> query(GiftPackVisionQueryRequest request) {
    return Mono.fromSupplier(request::getVisionId)
        .flatMap(visionService::queryMono)
        .map(mapper::toProto)
        .map(mapper::toProto)
        .onErrorMap(GiftPackServerExceptions.Query::new);
  }

  @MessageExceptionHandler(GiftPackServerExceptions.Query.class)
  Mono<GiftPackVisionQueryReply> onQueryException(GiftPackServerExceptions.Query ignored) {
    return Mono.just(mapper.toVisionQueryError(GiftPackErrorCode.FAILURE));
  }
}
