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
import org.doodle.design.giftpack.GiftPackPackPageOps;
import org.doodle.design.giftpack.GiftPackPackQueryOps;
import org.doodle.design.giftpack.model.payload.reply.GiftPackPackPageReply;
import org.doodle.design.giftpack.model.payload.reply.GiftPackPackQueryReply;
import org.doodle.design.giftpack.model.payload.request.GiftPackPackPageRequest;
import org.doodle.design.giftpack.model.payload.request.GiftPackPackQueryRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class GIftPackServerPackServletController
    implements GiftPackPackQueryOps.Servlet, GiftPackPackPageOps.Servlet {
  GiftPackServerPackService packService;

  @PostMapping(GiftPackPackPageOps.Servlet.PAGE_MAPPING)
  @Override
  public Result<GiftPackPackPageReply> page(GiftPackPackPageRequest request) {
    return Result.bad();
  }

  @PostMapping(GiftPackPackQueryOps.Servlet.QUERY_MAPPING)
  @Override
  public Result<GiftPackPackQueryReply> query(GiftPackPackQueryRequest request) {
    return Result.bad();
  }
}
