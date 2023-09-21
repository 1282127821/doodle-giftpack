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
import org.doodle.design.common.Result;
import org.doodle.design.giftpack.GiftPackVisionPageOps;
import org.doodle.design.giftpack.GiftPackVisionQueryOps;
import org.doodle.design.giftpack.model.payload.reply.GiftPackVisionPageReply;
import org.doodle.design.giftpack.model.payload.reply.GiftPackVisionQueryReply;
import org.doodle.design.giftpack.model.payload.request.GiftPackVisionPageRequest;
import org.doodle.design.giftpack.model.payload.request.GiftPackVisionQueryRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class GiftPackServerVisionServletController
    implements GiftPackVisionQueryOps.Servlet, GiftPackVisionPageOps.Servlet {
  GiftPackServerVisionService visionService;

  @PostMapping(GiftPackVisionPageOps.Servlet.PAGE_MAPPING)
  @Override
  public Result<GiftPackVisionPageReply> page(GiftPackVisionPageRequest request) {
    return Result.bad();
  }

  @PostMapping(GiftPackVisionQueryOps.Servlet.QUERY_MAPPING)
  @Override
  public Result<GiftPackVisionQueryReply> query(GiftPackVisionQueryRequest request) {
    return Result.bad();
  }
}
