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
import java.util.Optional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.doodle.design.common.model.PageRequest;
import org.doodle.design.giftpack.model.info.CodeInfo;
import org.doodle.design.giftpack.model.info.PackInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.util.CollectionUtils;
import reactor.core.publisher.Mono;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class GiftPackServerPackService {
  GiftPackServerPackRepo packRepo;
  GiftPackServerMappingService mappingService;
  GiftPackServerCodeService codeService;

  public Mono<List<PackInfo>> pageMono(PageRequest request) {
    return Mono.fromCallable(() -> page(request));
  }

  public List<PackInfo> page(PageRequest request) {
    Page<GiftPackServerPackEntity> page =
        packRepo.findAll(Pageable.ofSize(request.getPageSize()).withPage(request.getPageNumber()));
    List<GiftPackServerPackEntity> content = page.getContent();
    if (!CollectionUtils.isEmpty(content)) {
      return content.stream().map(this::query).toList();
    }
    return Collections.emptyList();
  }

  private PackInfo query(GiftPackServerPackEntity pack) {
    PackInfo.PackInfoBuilder builder = PackInfo.builder();
    builder.packId(pack.getPackId());
    builder.lifecycle(pack.getLifecycle());
    builder.condition(pack.getCondition());
    builder.options(pack.getOptions());
    builder.detail(pack.getDetail());

    Optional<GiftPackServerCodeGiftId> codeGiftId = mappingService.queryPackCode(pack.getPackId());
    if (codeGiftId.isPresent()) {
      CodeInfo codeInfo = codeService.query(codeGiftId.get().getPackCode());
      builder.code(codeInfo);
    }

    return builder.build();
  }

  private GiftPackServerPackEntity queryOrElseThrow(String packCode) {
    return packRepo.findById(packCode).orElseThrow();
  }

  public Mono<PackInfo> queryMono(String packCode) {
    return Mono.fromCallable(() -> query(packCode));
  }

  public PackInfo query(String packCode) {
    return query(queryOrElseThrow(packCode));
  }
}
