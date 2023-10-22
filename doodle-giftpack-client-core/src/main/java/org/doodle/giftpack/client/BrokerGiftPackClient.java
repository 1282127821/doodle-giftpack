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
public class BrokerGiftPackClient implements GiftPackClientRSocket {
  BrokerClientRSocketRequester requester;
  BrokerFrame frame;

  public BrokerGiftPackClient(
      BrokerClientRSocketRequester requester, GiftPackClientProperties properties) {
    this.requester = requester;
    this.frame = BrokerFrameUtils.unicast(properties.getServer().getTags());
  }

  @Override
  public Mono<GiftPackRoleUseReply> use(GiftPackRoleUseRequest request) {
    return route(GiftPackRoleUseOps.RSocket.USE_MAPPING)
        .data(request)
        .retrieveMono(GiftPackRoleUseReply.class);
  }

  @Override
  public Mono<GiftPackGroupPageReply> page(GiftPackGroupPageRequest request) {
    return route(GiftPackGroupPageOps.RSocket.PAGE_MAPPING)
        .data(request)
        .retrieveMono(GiftPackGroupPageReply.class);
  }

  @Override
  public Mono<GiftPackGroupQueryReply> query(GiftPackGroupQueryRequest request) {
    return route(GIftPackGroupQueryOps.RSocket.QUERY_MAPPING)
        .data(request)
        .retrieveMono(GiftPackGroupQueryReply.class);
  }

  @Override
  public Mono<GiftPackBatchPageReply> page(GiftPackBatchPageRequest request) {
    return route(GiftPackBatchPageOps.RSocket.PAGE_MAPPING)
        .data(request)
        .retrieveMono(GiftPackBatchPageReply.class);
  }

  @Override
  public Mono<GiftPackBatchQueryReply> query(GiftPackBatchQueryRequest request) {
    return route(GiftPackBatchQueryOps.RSocket.QUERY_MAPPING)
        .data(request)
        .retrieveMono(GiftPackBatchQueryReply.class);
  }

  @Override
  public Mono<GiftPackSpecPageReply> page(GiftPackSpecPageRequest request) {
    return route(GiftPackSpecPageOps.RSocket.PAGE_MAPPING)
        .data(request)
        .retrieveMono(GiftPackSpecPageReply.class);
  }

  @Override
  public Mono<GiftPackSpecQueryReply> query(GiftPackSpecQueryRequest request) {
    return route(GiftPackSpecQueryOps.RSocket.QUERY_MAPPING)
        .data(request)
        .retrieveMono(GiftPackSpecQueryReply.class);
  }

  protected RSocketRequester.RequestSpec route(String route) {
    return requester.route(route).metadata(frame, BrokerFrameMimeTypes.BROKER_FRAME_MIME_TYPE);
  }
}
