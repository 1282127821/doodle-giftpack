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

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.doodle.broker.client.BrokerClientRSocketRequester;
import org.doodle.design.broker.frame.BrokerFrame;
import org.doodle.design.broker.frame.BrokerFrameMimeTypes;
import org.doodle.design.broker.frame.BrokerFrameUtils;
import org.doodle.design.giftpack.*;
import org.springframework.messaging.rsocket.RSocketRequester;
import reactor.core.publisher.Mono;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BrokerGiftPackClientImpl implements GiftPackClientRSocket {
  BrokerClientRSocketRequester requester;
  BrokerFrame frame;

  public BrokerGiftPackClientImpl(
      BrokerClientRSocketRequester requester, GiftPackClientProperties properties) {
    this.requester = requester;
    this.frame = BrokerFrameUtils.unicast(properties.getServer().getTags());
  }

  @Override
  public Mono<GiftPackCodePageReply> page(GiftPackCodePageRequest request) {
    return route(GiftPackCodePageOps.RSocket.PAGE_MAPPING)
        .data(request)
        .retrieveMono(GiftPackCodePageReply.class);
  }

  @Override
  public Mono<GiftPackCodeQueryReply> query(GiftPackCodeQueryRequest request) {
    return route(GiftPackCodeQueryOps.RSocket.QUERY_MAPPING)
        .data(request)
        .retrieveMono(GiftPackCodeQueryReply.class);
  }

  @Override
  public Mono<GiftPackGiftPageReply> page(GiftPackGiftPageRequest request) {
    return route(GiftPackGiftPageOps.RSocket.PAGE_MAPPING)
        .data(request)
        .retrieveMono(GiftPackGiftPageReply.class);
  }

  @Override
  public Mono<GiftPackGiftQueryReply> query(GiftPackGiftQueryRequest request) {
    return route(GiftPackGiftQueryOps.RSocket.QUERY_MAPPING)
        .data(request)
        .retrieveMono(GiftPackGiftQueryReply.class);
  }

  @Override
  public Mono<GiftPackPackPageReply> page(GiftPackPackPageRequest request) {
    return route(GiftPackPackPageOps.RSocket.PAGE_MAPPING)
        .data(request)
        .retrieveMono(GiftPackPackPageReply.class);
  }

  @Override
  public Mono<GiftPackPackQueryReply> query(GiftPackPackQueryRequest request) {
    return route(GiftPackCodeQueryOps.RSocket.QUERY_MAPPING)
        .data(request)
        .retrieveMono(GiftPackPackQueryReply.class);
  }

  protected RSocketRequester.RequestSpec route(String route) {
    return requester.route(route).metadata(frame, BrokerFrameMimeTypes.BROKER_FRAME_MIME_TYPE);
  }
}
