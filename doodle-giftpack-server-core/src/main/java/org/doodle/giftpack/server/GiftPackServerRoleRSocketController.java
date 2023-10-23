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
import org.doodle.design.giftpack.GiftPackErrorCode;
import org.doodle.design.giftpack.GiftPackRoleUseOps;
import org.doodle.design.giftpack.GiftPackRoleUseReply;
import org.doodle.design.giftpack.GiftPackRoleUseRequest;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

@Controller
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class GiftPackServerRoleRSocketController implements GiftPackRoleUseOps.RSocket {
  GiftPackServerMapper mapper;
  GiftPackServerPackUseService packUseService;

  @MessageMapping(GiftPackRoleUseOps.RSocket.USE_MAPPING)
  @Override
  public Mono<GiftPackRoleUseReply> use(GiftPackRoleUseRequest request) {
    return Mono.just(request)
        .flatMap(req -> packUseService.useMono(request.getRoleId(), req.getPackCode()))
        .map(mapper::toProto)
        .map(mapper::toRoleUseReply)
        .onErrorMap(GiftPackServerExceptions.Use::new);
  }

  @MessageExceptionHandler(GiftPackServerExceptions.Use.class)
  Mono<GiftPackRoleUseReply> onUseException(GiftPackServerExceptions.Use ignored) {
    return Mono.just(mapper.toRoleUseError(GiftPackErrorCode.FAILURE));
  }
}
