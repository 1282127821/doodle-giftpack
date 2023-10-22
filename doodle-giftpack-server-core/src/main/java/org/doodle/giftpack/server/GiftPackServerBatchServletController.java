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
import org.doodle.design.giftpack.GiftPackBatchPageOps;
import org.doodle.design.giftpack.GiftPackBatchQueryOps;
import org.doodle.design.giftpack.model.info.GiftPackBatchInfo;
import org.doodle.design.giftpack.model.payload.reply.GiftPackBatchPageReply;
import org.doodle.design.giftpack.model.payload.reply.GiftPackBatchQueryReply;
import org.doodle.design.giftpack.model.payload.request.GiftPackBatchPageRequest;
import org.doodle.design.giftpack.model.payload.request.GiftPackBatchQueryRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class GiftPackServerBatchServletController
    implements GiftPackBatchQueryOps.Servlet, GiftPackBatchPageOps.Servlet {
  GiftPackServerBatchService batchService;

  @PostMapping(GiftPackBatchPageOps.Servlet.PAGE_MAPPING)
  @Override
  public Result<GiftPackBatchPageReply> page(GiftPackBatchPageRequest request) {
    List<GiftPackBatchInfo> batchInfos = batchService.page(request.getPage());
    GiftPackBatchPageReply reply = GiftPackBatchPageReply.builder().batchInfos(batchInfos).build();
    return Result.ok(reply);
  }

  @PostMapping(GiftPackBatchQueryOps.Servlet.QUERY_MAPPING)
  @Override
  public Result<GiftPackBatchQueryReply> query(GiftPackBatchQueryRequest request) {
    GiftPackBatchInfo batchInfo = batchService.query(request.getBatchId());
    GiftPackBatchQueryReply reply = GiftPackBatchQueryReply.builder().batchInfo(batchInfo).build();
    return Result.ok(reply);
  }

  @ExceptionHandler(Exception.class)
  ResponseEntity<Result<Void>> onException(Exception ignored) {
    return ResponseEntity.badRequest().body(Result.bad());
  }
}
