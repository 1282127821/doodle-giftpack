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

import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.doodle.design.common.model.PageRequest;
import org.doodle.design.giftpack.GiftPackType;
import org.doodle.design.giftpack.model.info.GiftPackGroupInfo;
import org.doodle.design.giftpack.model.info.GiftPackInfo;
import org.doodle.design.giftpack.model.info.GiftPackLifecycleInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.util.CollectionUtils;
import reactor.core.publisher.Mono;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GiftPackServerGroupService extends GiftPackServerSeqService
    implements GiftPackServerPackUseHandler {
  GiftPackServerMapper mapper;
  GiftPackServerGroupRepo groupRepo;
  GiftPackServerContentService contentService;
  GiftPackServerRoleService roleService;

  public GiftPackServerGroupService(
      MongoTemplate mongoTemplate,
      GiftPackServerMapper mapper,
      GiftPackServerGroupRepo groupRepo,
      GiftPackServerContentService contentService,
      GiftPackServerRoleService roleService) {
    super(mongoTemplate, GiftPackServerGroupEntity.COLLECTION);
    this.mapper = mapper;
    this.groupRepo = groupRepo;
    this.contentService = contentService;
    this.roleService = roleService;
  }

  @Override
  public GiftPackType packType() {
    return GiftPackType.GROUP;
  }

  @Override
  public GiftPackInfo use(String roleId, String packCode) {
    GiftPackServerGroupEntity groupEntity = groupRepo.findByGroupCode(packCode).orElseThrow();
    GiftPackLifecycleInfo lifecycle = groupEntity.getLifecycle();
    Instant now = Instant.now();
    if (Duration.between(lifecycle.getStart(), now).isNegative()
        || Duration.between(now, lifecycle.getEnd()).isNegative()) {
      throw new IllegalStateException("不在该礼包码有效期内 " + packCode);
    }

    GiftPackServerRoleLogId roleLogId =
        GiftPackServerRoleLogId.builder()
            .roleId(roleId)
            .packCode(packCode)
            .packType(GiftPackType.GROUP)
            .packId(groupEntity.getGroupId())
            .packIndex(0)
            .build();

    if (roleService.isLogExists(roleLogId)) {
      throw new IllegalStateException("已经领取该礼包码,不能重复领取");
    }

    roleService.saveLog(roleLogId);

    GiftPackInfo packInfo = GiftPackInfo.builder().build();
    packInfo.setGroupInfo(
        mapper.toPojo(groupEntity, contentService.query(groupEntity.getContentId())));
    return packInfo;
  }

  public Mono<List<GiftPackGroupInfo>> pageMono(PageRequest pageRequest) {
    return Mono.fromCallable(() -> page(pageRequest));
  }

  public List<GiftPackGroupInfo> page(PageRequest pageRequest) {
    Page<GiftPackServerGroupEntity> page =
        groupRepo.findAll(
            Pageable.ofSize(pageRequest.getPageSize()).withPage(pageRequest.getPageNumber()));
    List<GiftPackServerGroupEntity> content = page.getContent();
    return CollectionUtils.isEmpty(content)
        ? Collections.emptyList()
        : content.stream().map(this::query).toList();
  }

  public Mono<GiftPackGroupInfo> queryMono(long groupId) {
    return Mono.fromCallable(() -> query(groupId));
  }

  public GiftPackGroupInfo query(long groupId) {
    return groupRepo.findById(groupId).map(this::query).orElse(null);
  }

  private GiftPackGroupInfo query(GiftPackServerGroupEntity groupEntity) {
    return mapper.toPojo(groupEntity, contentService.query(groupEntity.getContentId()));
  }
}
