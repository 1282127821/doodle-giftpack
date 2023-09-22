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
import org.doodle.design.giftpack.model.info.GiftInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.util.CollectionUtils;
import reactor.core.publisher.Mono;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class GiftPackServerGiftService {
  GiftPackServerGiftRepo giftRepo;
  GiftPackServerMappingService mappingService;

  public Mono<List<GiftInfo>> pageMono(PageRequest request) {
    return Mono.fromCallable(() -> page(request));
  }

  public List<GiftInfo> page(PageRequest request) {
    Page<GiftPackServerGiftEntity> page =
        giftRepo.findAll(Pageable.ofSize(request.getPageSize()).withPage(request.getPageNumber()));
    List<GiftPackServerGiftEntity> content = page.getContent();
    if (!CollectionUtils.isEmpty(content)) {
      return content.stream().map(this::query).toList();
    }
    return Collections.emptyList();
  }

  private GiftInfo query(GiftPackServerGiftEntity gift) {
    GiftInfo.GiftInfoBuilder builder = GiftInfo.builder();
    builder.giftId(gift.getGiftId());
    builder.content(gift.getContent());
    // TODO: 2023/9/22
    return builder.build();
  }

  private GiftPackServerGiftEntity queryOrElseThrow(String giftId) {
    return giftRepo.findById(giftId).orElseThrow();
  }

  public Mono<GiftInfo> queryMono(String giftId) {
    return Mono.fromCallable(() -> query(giftId));
  }

  private GiftInfo query(String giftId) {
    return query(queryOrElseThrow(giftId));
  }
}
