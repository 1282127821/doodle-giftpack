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

import org.doodle.design.giftpack.GiftPackMapper;
import org.doodle.design.giftpack.model.info.GiftPackBatchInfo;
import org.doodle.design.giftpack.model.info.GiftPackContentInfo;
import org.doodle.design.giftpack.model.info.GiftPackGroupInfo;
import org.doodle.design.giftpack.model.info.GiftPackSpecInfo;

public class GiftPackServerMapper extends GiftPackMapper {

  public GiftPackContentInfo toPojo(GiftPackServerContentEntity contentEntity) {
    return GiftPackContentInfo.builder()
        .contentId(contentEntity.getContentId())
        .content(contentEntity.getContent())
        .build();
  }

  public GiftPackGroupInfo toPojo(
      GiftPackServerGroupEntity groupEntity, GiftPackContentInfo contentInfo) {
    return GiftPackGroupInfo.builder()
        .groupId(groupEntity.getGroupId())
        .groupCode(groupEntity.getGroupCode())
        .contentInfo(contentInfo)
        .lifecycleInfo(groupEntity.getLifecycle())
        .build();
  }

  public GiftPackBatchInfo toPojo(
      GiftPackServerBatchEntity batchEntity, GiftPackContentInfo contentInfo) {
    return GiftPackBatchInfo.builder()
        .batchId(batchEntity.getBatchId())
        .batchSize(batchEntity.getBatchSize())
        .contentInfo(contentInfo)
        .lifecycleInfo(batchEntity.getLifecycle())
        .build();
  }

  public GiftPackSpecInfo toPojo(
      GiftPackServerSpecEntity specEntity, GiftPackContentInfo contentInfo) {
    return GiftPackSpecInfo.builder()
        .specId(specEntity.getSpecId())
        .roleId(specEntity.getRoleId())
        .contentInfo(contentInfo)
        .lifecycleInfo(specEntity.getLifecycle())
        .build();
  }

  public GiftPackServerRoleLogEntity toRoleLogEntity(GiftPackServerRoleLogId roleLogId) {
    return GiftPackServerRoleLogEntity.builder().logId(roleLogId).build();
  }

  public GiftPackServerBatchLogEntity toBatchLogEntity(GiftPackServerBatchLogId batchLogId) {
    return GiftPackServerBatchLogEntity.builder().logId(batchLogId).build();
  }
}
