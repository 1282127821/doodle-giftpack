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

import java.util.Collections;
import java.util.List;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.doodle.design.common.model.PageRequest;
import org.doodle.design.giftpack.GiftPackType;
import org.doodle.design.giftpack.model.info.GiftPackBatchInfo;
import org.doodle.design.giftpack.model.info.GiftPackInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.util.CollectionUtils;
import reactor.core.publisher.Mono;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GiftPackServerBatchService extends GiftPackServerSeqService
    implements GiftPackServerPackUseHandler {
  GiftPackServerMapper mapper;
  GiftPackServerBatchRepo batchRepo;
  GiftPackServerContentService contentService;
  GiftPackServerPackService packService;

  public GiftPackServerBatchService(
      MongoTemplate mongoTemplate,
      GiftPackServerMapper mapper,
      GiftPackServerBatchRepo batchRepo,
      GiftPackServerContentService contentService,
      GiftPackServerPackService packService) {
    super(mongoTemplate, GiftPackServerBatchEntity.COLLECTION);
    this.mapper = mapper;
    this.batchRepo = batchRepo;
    this.contentService = contentService;
    this.packService = packService;
  }

  @Override
  public GiftPackType packType() {
    return GiftPackType.BATCH;
  }

  @Override
  public GiftPackInfo use(String roleId, String packCode) {
    long[] unpacked = packService.unpackHashIds(packCode);
    return null;
  }

  public Mono<List<GiftPackBatchInfo>> pageMono(PageRequest pageRequest) {
    return Mono.fromCallable(() -> page(pageRequest));
  }

  public List<GiftPackBatchInfo> page(PageRequest pageRequest) {
    Page<GiftPackServerBatchEntity> page =
        batchRepo.findAll(
            Pageable.ofSize(pageRequest.getPageSize()).withPage(pageRequest.getPageNumber()));
    List<GiftPackServerBatchEntity> content = page.getContent();
    return CollectionUtils.isEmpty(content)
        ? Collections.emptyList()
        : content.stream().map(this::query).toList();
  }

  public Mono<GiftPackBatchInfo> queryMono(long batchId) {
    return Mono.fromCallable(() -> query(batchId));
  }

  public GiftPackBatchInfo query(long batchId) {
    return batchRepo.findById(batchId).map(this::query).orElse(null);
  }

  GiftPackBatchInfo query(GiftPackServerBatchEntity batchEntity) {
    return mapper.toPojo(batchEntity, contentService.query(batchEntity.getContentId()));
  }
}
