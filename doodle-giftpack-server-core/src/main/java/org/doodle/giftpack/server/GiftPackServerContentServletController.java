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

import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.doodle.design.common.Result;
import org.doodle.design.giftpack.GiftPackContentPageOps;
import org.doodle.design.giftpack.GiftPackContentQueryOps;
import org.doodle.design.giftpack.model.info.GiftPackContentInfo;
import org.doodle.design.giftpack.model.payload.reply.GiftPackContentPageReply;
import org.doodle.design.giftpack.model.payload.reply.GiftPackContentQueryReply;
import org.doodle.design.giftpack.model.payload.request.GiftPackContentPageRequest;
import org.doodle.design.giftpack.model.payload.request.GiftPackContentQueryRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class GiftPackServerContentServletController
    implements GiftPackContentQueryOps.Servlet, GiftPackContentPageOps.Servlet {
  GiftPackServerContentService contentService;

  @PostMapping(GiftPackContentPageOps.Servlet.PAGE_MAPPING)
  @Override
  public Result<GiftPackContentPageReply> page(GiftPackContentPageRequest request) {
    List<GiftPackContentInfo> contentInfos = contentService.page(request.getPage());
    GiftPackContentPageReply reply =
        GiftPackContentPageReply.builder().contentInfos(contentInfos).build();
    return Result.ok(reply);
  }

  @PostMapping(GiftPackContentPageOps.Servlet.PAGE_MAPPING)
  @Override
  public Result<GiftPackContentQueryReply> query(GiftPackContentQueryRequest request) {
    GiftPackContentInfo contentInfo = contentService.query(request.getContentId());
    GiftPackContentQueryReply reply =
        GiftPackContentQueryReply.builder().contentInfo(contentInfo).build();
    return Result.ok(reply);
  }

  @ExceptionHandler(Exception.class)
  ResponseEntity<Result<Void>> onException(Exception ignored) {
    return ResponseEntity.badRequest().body(Result.bad());
  }
}
