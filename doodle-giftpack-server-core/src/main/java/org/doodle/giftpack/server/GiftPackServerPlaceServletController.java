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
import org.doodle.design.giftpack.GiftPackPlacePageOps;
import org.doodle.design.giftpack.GiftPackPlaceQueryOps;
import org.doodle.design.giftpack.model.payload.reply.GiftPackPlacePageRequest;
import org.doodle.design.giftpack.model.payload.reply.GiftPackPlaceQueryRequest;
import org.doodle.design.giftpack.model.payload.request.GiftPackPlacePageReply;
import org.doodle.design.giftpack.model.payload.request.GiftPackPlaceQueryReply;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GiftPackServerPlaceServletController
    implements GiftPackPlaceQueryOps.Servlet, GiftPackPlacePageOps.Servlet {

  @PostMapping(GiftPackPlacePageOps.Servlet.PAGE_MAPPING)
  @Override
  public Result<GiftPackPlacePageReply> page(GiftPackPlacePageRequest request) {
    return Result.bad();
  }

  @PostMapping(GiftPackPlaceQueryOps.Servlet.QUERY_MAPPING)
  @Override
  public Result<GiftPackPlaceQueryReply> query(GiftPackPlaceQueryRequest request) {
    return Result.bad();
  }
}
