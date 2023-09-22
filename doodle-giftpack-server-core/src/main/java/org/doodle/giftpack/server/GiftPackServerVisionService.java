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
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.doodle.design.common.model.PageRequest;
import org.doodle.design.giftpack.model.info.VisionInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.util.CollectionUtils;
import reactor.core.publisher.Mono;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class GiftPackServerVisionService {
  GiftPackServerMapper mapper;
  GiftPackServerVisionRepo visionRepo;

  public Mono<List<VisionInfo>> pageMono(PageRequest request) {
    return Mono.fromCallable(() -> page(request));
  }

  public List<VisionInfo> page(PageRequest request) {
    Page<GiftPackServerVisionEntity> page =
        visionRepo.findAll(
            Pageable.ofSize(request.getPageSize()).withPage(request.getPageNumber()));
    List<GiftPackServerVisionEntity> content = page.getContent();
    if (!CollectionUtils.isEmpty(content)) {
      return content.stream().map(this::query).toList();
    }
    return Collections.emptyList();
  }

  private VisionInfo query(GiftPackServerVisionEntity vision) {
    return mapper.toPojo(vision);
  }

  private GiftPackServerVisionEntity queryOrElseThrow(String visionId) {
    return visionRepo.findById(visionId).orElseThrow();
  }

  public Mono<VisionInfo> queryMono(String visionId) {
    return Mono.fromCallable(() -> query(visionId));
  }

  public VisionInfo query(String visionId) {
    return query(queryOrElseThrow(visionId));
  }
}
