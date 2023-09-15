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
import reactor.core.publisher.Mono;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class GiftPackServerHashRSocketController
    implements GiftPackHashCreateOps.RSocket,
        GiftPackHashQueryOps.RSocket,
        GiftPackHashPageOps.RSocket,
        GiftPackHashUseOps.RSocket {
  GiftPackServerHashService hashService;

  @MessageMapping(GiftPackHashCreateOps.RSocket.CREATE_MAPPING)
  @Override
  public Mono<GiftPackHashCreateReply> create(GiftPackHashCreateReply request) {
    return Mono.empty();
  }

  @MessageExceptionHandler(GiftPackServerExceptions.Create.class)
  Mono<GiftPackHashCreateReply> onCreateException(GiftPackServerExceptions.Create ignored) {
    return Mono.just(
        GiftPackHashCreateReply.newBuilder().setError(GiftPackErrorCode.FAILURE).build());
  }

  @MessageMapping(GiftPackQueryOps.RSocket.QUERY_MAPPING)
  @Override
  public Mono<GiftPackHashQueryReply> query(GiftPackHashQueryRequest request) {
    return Mono.empty();
  }

  @MessageExceptionHandler(GiftPackServerExceptions.Query.class)
  Mono<GiftPackHashQueryReply> onQueryException(GiftPackServerExceptions.Query ignored) {
    return Mono.just(
        GiftPackHashQueryReply.newBuilder().setError(GiftPackErrorCode.FAILURE).build());
  }

  @MessageMapping(GiftPackHashPageOps.RSocket.PAGE_MAPPING)
  @Override
  public Mono<GiftPackHashPageReply> page(GiftPackHashPageRequest request) {
    return Mono.empty();
  }

  @MessageExceptionHandler(GiftPackServerExceptions.Page.class)
  Mono<GiftPackHashPageReply> onPageException(GiftPackServerExceptions.Page ignored) {
    return Mono.just(
        GiftPackHashPageReply.newBuilder().setError(GiftPackErrorCode.FAILURE).build());
  }

  @MessageMapping(GiftPackHashUseOps.RSocket.USE_MAPPING)
  @Override
  public Mono<GiftPackHashUseReply> use(GiftPackHashUseRequest request) {
    return Mono.empty();
  }

  @MessageExceptionHandler(GiftPackServerExceptions.Use.class)
  Mono<GiftPackHashUseReply> onUseException(GiftPackServerExceptions.Use ignored) {
    return Mono.just(GiftPackHashUseReply.newBuilder().setError(GiftPackErrorCode.FAILURE).build());
  }
}
