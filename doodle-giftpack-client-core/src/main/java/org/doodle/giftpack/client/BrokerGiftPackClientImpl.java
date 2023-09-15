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
  public Mono<GiftPackPageReply> page(GiftPackPageRequest request) {
    return route(GiftPackPageOps.RSocket.PAGE_MAPPING)
        .data(request)
        .retrieveMono(GiftPackPageReply.class);
  }

  @Override
  public Mono<GiftPackQueryReply> query(GiftPackQueryRequest request) {
    return route(GiftPackQueryOps.RSocket.QUERY_MAPPING)
        .data(request)
        .retrieveMono(GiftPackQueryReply.class);
  }

  @Override
  public Mono<GiftPackCreateReply> create(GiftPackCreateRequest request) {
    return route(GiftPackCreateOps.RSocket.CREATE_MAPPING)
        .data(request)
        .retrieveMono(GiftPackCreateReply.class);
  }

  @Override
  public Mono<GiftPackHashUseReply> use(GiftPackHashUseRequest request) {
    return route(GiftPackHashUseOps.RSocket.USE_MAPPING)
        .data(request)
        .retrieveMono(GiftPackHashUseReply.class);
  }

  @Override
  public Mono<GiftPackHashPageReply> page(GiftPackHashPageRequest request) {
    return route(GiftPackHashPageOps.RSocket.PAGE_MAPPING)
        .data(request)
        .retrieveMono(GiftPackHashPageReply.class);
  }

  @Override
  public Mono<GiftPackHashQueryReply> query(GiftPackHashQueryRequest request) {
    return route(GiftPackHashQueryOps.RSocket.QUERY_MAPPING)
        .data(request)
        .retrieveMono(GiftPackHashQueryReply.class);
  }

  @Override
  public Mono<GiftPackHashCreateReply> create(GiftPackHashCreateReply request) {
    return route(GiftPackHashCreateOps.RSocket.CREATE_MAPPING)
        .data(request)
        .retrieveMono(GiftPackHashCreateReply.class);
  }

  protected RSocketRequester.RequestSpec route(String route) {
    return requester.route(route).metadata(frame, BrokerFrameMimeTypes.BROKER_FRAME_MIME_TYPE);
  }
}
