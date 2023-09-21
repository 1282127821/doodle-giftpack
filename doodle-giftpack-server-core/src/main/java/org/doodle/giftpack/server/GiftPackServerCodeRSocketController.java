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
public class GiftPackServerCodeRSocketController
    implements GiftPackCodeQueryOps.RSocket, GiftPackCodePageOps.RSocket {
  GiftPackServerCodeService codeService;

  @MessageMapping(GiftPackCodePageOps.RSocket.PAGE_MAPPING)
  @Override
  public Mono<GiftPackCodePageReply> page(GiftPackCodePageRequest request) {
    return Mono.empty();
  }

  @MessageExceptionHandler(GiftPackServerExceptions.Page.class)
  Mono<GiftPackCodePageReply> onPageException(GiftPackServerExceptions.Page ignored) {
    return Mono.just(
        GiftPackCodePageReply.newBuilder().setError(GiftPackErrorCode.FAILURE).build());
  }

  @MessageMapping(GiftPackCodeQueryOps.RSocket.QUERY_MAPPING)
  @Override
  public Mono<GiftPackCodeQueryReply> query(GiftPackCodeQueryRequest request) {
    return Mono.empty();
  }

  @MessageExceptionHandler(GiftPackServerExceptions.Query.class)
  Mono<GiftPackCodeQueryReply> onQueryException(GiftPackServerExceptions.Query ignored) {
    return Mono.just(
        GiftPackCodeQueryReply.newBuilder().setError(GiftPackErrorCode.FAILURE).build());
  }
}
