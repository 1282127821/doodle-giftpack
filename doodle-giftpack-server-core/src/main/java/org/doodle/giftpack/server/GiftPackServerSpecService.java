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
import org.doodle.design.giftpack.model.info.GiftPackInfo;
import org.doodle.design.giftpack.model.info.GiftPackSpecInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.util.CollectionUtils;
import reactor.core.publisher.Mono;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GiftPackServerSpecService extends GiftPackServerSeqService
    implements GiftPackServerPackUseHandler {
  GiftPackServerMapper mapper;
  GiftPackServerSpecRepo specRepo;
  GiftPackServerContentService contentService;
  GiftPackServerPackService packService;

  public GiftPackServerSpecService(
      MongoTemplate mongoTemplate,
      GiftPackServerMapper mapper,
      GiftPackServerSpecRepo specRepo,
      GiftPackServerContentService contentService,
      GiftPackServerPackService packService) {
    super(mongoTemplate, GiftPackServerSpecEntity.COLLECTION);
    this.mapper = mapper;
    this.specRepo = specRepo;
    this.contentService = contentService;
    this.packService = packService;
  }

  @Override
  public GiftPackType packType() {
    return GiftPackType.SPEC;
  }

  @Override
  public GiftPackInfo use(String roleId, String packCode) {
    return null;
  }

  public Mono<List<GiftPackSpecInfo>> pageMono(PageRequest pageRequest) {
    return Mono.fromCallable(() -> page(pageRequest));
  }

  public List<GiftPackSpecInfo> page(PageRequest pageRequest) {
    Page<GiftPackServerSpecEntity> page =
        specRepo.findAll(
            Pageable.ofSize(pageRequest.getPageSize()).withPage(pageRequest.getPageNumber()));
    List<GiftPackServerSpecEntity> content = page.getContent();
    return CollectionUtils.isEmpty(content)
        ? Collections.emptyList()
        : content.stream().map(this::query).toList();
  }

  public Mono<GiftPackSpecInfo> queryMono(long specId) {
    return Mono.fromCallable(() -> query(specId));
  }

  public GiftPackSpecInfo query(long specId) {
    return specRepo.findById(specId).map(this::query).orElse(null);
  }

  private GiftPackSpecInfo query(GiftPackServerSpecEntity specEntity) {
    return mapper.toPojo(specEntity, contentService.query(specEntity.getContentId()));
  }
}
