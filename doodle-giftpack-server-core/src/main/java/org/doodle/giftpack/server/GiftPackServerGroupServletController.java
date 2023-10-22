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
import org.doodle.design.giftpack.GIftPackGroupQueryOps;
import org.doodle.design.giftpack.GiftPackGroupPageOps;
import org.doodle.design.giftpack.model.info.GiftPackGroupInfo;
import org.doodle.design.giftpack.model.payload.reply.GiftPackGroupPageReply;
import org.doodle.design.giftpack.model.payload.reply.GiftPackGroupQueryReply;
import org.doodle.design.giftpack.model.payload.request.GiftPackGroupPageRequest;
import org.doodle.design.giftpack.model.payload.request.GiftPackGroupQueryRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class GiftPackServerGroupServletController
    implements GIftPackGroupQueryOps.Servlet, GiftPackGroupPageOps.Servlet {
  GiftPackServerGroupService groupService;

  @PostMapping(GiftPackGroupPageOps.Servlet.PAGE_MAPPING)
  @Override
  public Result<GiftPackGroupPageReply> page(GiftPackGroupPageRequest request) {
    List<GiftPackGroupInfo> groupInfos = groupService.page(request.getPage());
    GiftPackGroupPageReply reply = GiftPackGroupPageReply.builder().groupInfos(groupInfos).build();
    return Result.ok(reply);
  }

  @PostMapping(GIftPackGroupQueryOps.Servlet.QUERY_MAPPING)
  @Override
  public Result<GiftPackGroupQueryReply> query(GiftPackGroupQueryRequest request) {
    GiftPackGroupInfo groupInfo = groupService.query(request.getGroupId());
    GiftPackGroupQueryReply reply = GiftPackGroupQueryReply.builder().groupInfo(groupInfo).build();
    return Result.ok(reply);
  }

  @ExceptionHandler(Exception.class)
  ResponseEntity<Result<Void>> onException(Exception ignored) {
    return ResponseEntity.badRequest().body(Result.bad());
  }
}
