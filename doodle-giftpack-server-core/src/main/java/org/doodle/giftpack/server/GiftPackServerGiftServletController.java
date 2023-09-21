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
import org.doodle.design.common.Result;
import org.doodle.design.giftpack.GiftPackGiftPageOps;
import org.doodle.design.giftpack.GiftPackGiftQueryOps;
import org.doodle.design.giftpack.model.payload.reply.GiftPackGiftPageReply;
import org.doodle.design.giftpack.model.payload.reply.GiftPackGiftQueryReply;
import org.doodle.design.giftpack.model.payload.request.GiftPackGiftPageRequest;
import org.doodle.design.giftpack.model.payload.request.GiftPackGiftQueryRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GiftPackServerGiftServletController
    implements GiftPackGiftQueryOps.Servlet, GiftPackGiftPageOps.Servlet {

  @PostMapping(GiftPackGiftQueryOps.Servlet.QUERY_MAPPING)
  @Override
  public Result<GiftPackGiftPageReply> page(GiftPackGiftPageRequest request) {
    return Result.bad();
  }

  @PostMapping(GiftPackGiftPageOps.Servlet.PAGE_MAPPING)
  @Override
  public Result<GiftPackGiftQueryReply> query(GiftPackGiftQueryRequest request) {
    return Result.bad();
  }
}
