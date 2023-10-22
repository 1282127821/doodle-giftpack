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
import lombok.experimental.FieldDefaults;
import org.doodle.design.common.model.PageRequest;
import org.doodle.design.giftpack.model.info.GiftPackGroupInfo;
import org.springframework.data.mongodb.core.MongoTemplate;
import reactor.core.publisher.Mono;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GiftPackServerGroupService extends GiftPackServerSeqService {
  GiftPackServerGroupRepo groupRepo;

  public GiftPackServerGroupService(
      MongoTemplate mongoTemplate, GiftPackServerGroupRepo groupRepo) {
    super(mongoTemplate, GiftPackServerGroupEntity.COLLECTION);
    this.groupRepo = groupRepo;
  }

  public Mono<GiftPackGroupInfo> queryMono(long groupId) {
    return Mono.fromCallable(() -> query(groupId));
  }

  public GiftPackGroupInfo query(long groupId) {
    return null;
  }

  public Mono<List<GiftPackGroupInfo>> pageMono(PageRequest pageRequest) {
    return Mono.fromCallable(() -> page(pageRequest));
  }

  public List<GiftPackGroupInfo> page(PageRequest pageRequest) {
    return null;
  }
}
