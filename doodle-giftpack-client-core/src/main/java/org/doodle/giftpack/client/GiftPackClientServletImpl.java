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
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.doodle.design.common.Result;
import org.doodle.design.giftpack.model.payload.reply.GiftPackHashPageReply;
import org.doodle.design.giftpack.model.payload.reply.GiftPackHashQueryReply;
import org.doodle.design.giftpack.model.payload.reply.GiftPackPageReply;
import org.doodle.design.giftpack.model.payload.reply.GiftPackQueryReply;
import org.doodle.design.giftpack.model.payload.request.GiftPackHashPageRequest;
import org.doodle.design.giftpack.model.payload.request.GiftPackHashQueryRequest;
import org.doodle.design.giftpack.model.payload.request.GiftPackPageRequest;
import org.doodle.design.giftpack.model.payload.request.GiftPackQueryRequest;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Mono;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class GiftPackClientServletImpl implements GiftPackClientServlet {
  RestTemplate restTemplate;

  @Override
  public Result<GiftPackHashPageReply> page(GiftPackHashPageRequest request) {
    return null;
  }

  @Override
  public Mono<GiftPackHashQueryReply> query(GiftPackHashQueryRequest request) {
    return null;
  }

  @Override
  public Result<GiftPackPageReply> page(GiftPackPageRequest request) {
    return null;
  }

  @Override
  public Result<GiftPackQueryReply> query(GiftPackQueryRequest request) {
    return null;
  }
}
