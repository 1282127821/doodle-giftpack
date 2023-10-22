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
import org.doodle.design.giftpack.model.info.GiftPackContentInfo;
import org.springframework.data.mongodb.core.MongoTemplate;
import reactor.core.publisher.Mono;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GiftPackServerContentService extends GiftPackServerSeqService {
  GiftPackServerContentRepo contentRepo;

  public GiftPackServerContentService(
      MongoTemplate mongoTemplate, GiftPackServerContentRepo contentRepo) {
    super(mongoTemplate, GiftPackServerContentEntity.COLLECTION);
    this.contentRepo = contentRepo;
  }

  public Mono<List<GiftPackContentInfo>> pageMono(PageRequest pageRequest) {
    return Mono.fromCallable(() -> page(pageRequest));
  }

  public List<GiftPackContentInfo> page(PageRequest pageRequest) {
    return null;
  }

  public Mono<GiftPackContentInfo> queryMono(long contentId) {
    return Mono.fromCallable(() -> query(contentId));
  }

  public GiftPackContentInfo query(long contentId) {
    return null;
  }
}
