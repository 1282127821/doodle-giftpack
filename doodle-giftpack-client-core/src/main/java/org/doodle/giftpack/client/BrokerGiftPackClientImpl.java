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
  public Mono<GiftPackCodeCreateReply> create(GiftPackCodeCreateRequest request) {
    return route(GiftPackCodeCreateOps.RSocket.CREATE_MAPPING)
        .data(request)
        .retrieveMono(GiftPackCodeCreateReply.class);
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
  public Mono<GiftPackPlacePageReply> page(GiftPackPlacePageRequest request) {
    return route(GiftPackPlacePageOps.RSocket.PAGE_MAPPING)
        .data(request)
        .retrieveMono(GiftPackPlacePageReply.class);
  }

  @Override
  public Mono<GiftPackPlaceQueryReply> query(GiftPackPlaceQueryRequest request) {
    return route(GiftPackPlaceQueryOps.RSocket.QUERY_MAPPING)
        .data(request)
        .retrieveMono(GiftPackPlaceQueryReply.class);
  }

  @Override
  public Mono<GiftPackUseReply> use(GiftPackUseRequest request) {
    return route(GiftPackUseOps.RSocket.USE_MAPPING)
        .data(request)
        .retrieveMono(GiftPackUseReply.class);
  }

  protected RSocketRequester.RequestSpec route(String route) {
    return requester.route(route).metadata(frame, BrokerFrameMimeTypes.BROKER_FRAME_MIME_TYPE);
  }
}
