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

import lombok.RequiredArgsConstructor;
import org.doodle.design.giftpack.*;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

@Controller
@RequiredArgsConstructor
public class GiftPackServerGiftRSocketController
    implements GiftPackGiftQueryOps.RSocket, GiftPackGiftPageOps.RSocket {

  @MessageMapping(GiftPackGiftPageOps.RSocket.PAGE_MAPPING)
  @Override
  public Mono<GiftPackGiftPageReply> page(GiftPackGiftPageRequest request) {
    return Mono.empty();
  }

  @MessageMapping(GiftPackGiftQueryOps.RSocket.QUERY_MAPPING)
  @Override
  public Mono<GiftPackGiftQueryReply> query(GiftPackGiftQueryRequest request) {
    return Mono.empty();
  }
}