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
public class GiftPackServerContentRSocketController
    implements GiftPackContentQueryOps.RSocket, GiftPackContentPageOps.RSocket {
  GiftPackServerMapper mapper;
  GiftPackServerContentService contentService;

  @MessageMapping(GiftPackContentPageOps.RSocket.PAGE_MAPPING)
  @Override
  public Mono<GiftPackContentPageReply> page(GiftPackContentPageRequest request) {
    return Mono.fromSupplier(request::getPage)
        .map(mapper::fromProto)
        .flatMap(contentService::pageMono)
        .map(mapper::toContentInfoList)
        .map(mapper::toContentPageReply)
        .onErrorMap(GiftPackServerExceptions.Page::new);
  }

  @MessageExceptionHandler(GiftPackServerExceptions.Page.class)
  Mono<GiftPackContentPageReply> onPageException(GiftPackServerExceptions.Page ignored) {
    return Mono.just(mapper.toContentPageError(GiftPackErrorCode.FAILURE));
  }

  @MessageMapping(GiftPackContentQueryOps.RSocket.QUERY_MAPPING)
  @Override
  public Mono<GiftPackContentQueryReply> query(GiftPackContentQueryRequest request) {
    return Mono.fromSupplier(request::getContentId)
        .flatMap(contentService::queryMono)
        .map(mapper::toProto)
        .map(mapper::toContentQueryReply)
        .onErrorMap(GiftPackServerExceptions.Query::new);
  }

  @MessageExceptionHandler(GiftPackServerExceptions.Query.class)
  Mono<GiftPackContentQueryReply> onQueryException(GiftPackServerExceptions.Query ignored) {
    return Mono.just(mapper.toContentQueryError(GiftPackErrorCode.FAILURE));
  }
}
