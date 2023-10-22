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
import org.doodle.design.giftpack.model.info.GiftPackSpecInfo;
import org.springframework.data.mongodb.core.MongoTemplate;
import reactor.core.publisher.Mono;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GiftPackServerSpecService extends GiftPackServerSeqService {
  GiftPackServerSpecRepo specRepo;

  public GiftPackServerSpecService(MongoTemplate mongoTemplate, GiftPackServerSpecRepo specRepo) {
    super(mongoTemplate, GiftPackServerSpecEntity.COLLECTION);
    this.specRepo = specRepo;
  }

  public Mono<List<GiftPackSpecInfo>> pageMono(PageRequest pageRequest) {
    return Mono.fromCallable(() -> page(pageRequest));
  }

  public List<GiftPackSpecInfo> page(PageRequest pageRequest) {
    return null;
  }

  public Mono<GiftPackSpecInfo> queryMono(long specId) {
    return Mono.fromCallable(() -> query(specId));
  }

  public GiftPackSpecInfo query(long specId) {
    return null;
  }
}
