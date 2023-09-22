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
import org.doodle.design.giftpack.model.info.CodeInfo;
import org.doodle.design.giftpack.model.info.GiftInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.util.CollectionUtils;
import reactor.core.publisher.Mono;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class GiftPackServerCodeService {
  GiftPackServerCodeRepo codeRepo;
  GiftPackServerMappingService mappingService;
  GiftPackServerGiftService giftService;

  public Mono<List<CodeInfo>> pageMono(PageRequest request) {
    return Mono.fromCallable(() -> page(request));
  }

  public List<CodeInfo> page(PageRequest request) {
    Page<GiftPackServerCodeEntity> page =
        codeRepo.findAll(Pageable.ofSize(request.getPageSize()).withPage(request.getPageNumber()));
    List<GiftPackServerCodeEntity> content = page.getContent();
    if (!CollectionUtils.isEmpty(content)) {
      return content.stream().map(this::query).toList();
    }
    return Collections.emptyList();
  }

  private CodeInfo query(GiftPackServerCodeEntity code) {
    CodeInfo.CodeInfoBuilder builder = CodeInfo.builder();
    builder.codeId(code.getCodeId());
    builder.packCode(code.getPackCode());
    List<GiftPackServerCodeGiftId> codeGiftIds = mappingService.queryCodeGift(code.getPackCode());
    if (!CollectionUtils.isEmpty(codeGiftIds)) {
      List<GiftInfo> giftInfos =
          codeGiftIds.stream()
              .map(GiftPackServerCodeGiftId::getGiftId)
              .map(giftService::query)
              .toList();
      builder.giftInfos(giftInfos);
    }
    return builder.build();
  }

  public Mono<CodeInfo> queryMono(String packCode) {
    return Mono.fromCallable(() -> query(packCode));
  }

  public CodeInfo query(String packCode) {
    return query(queryOrElseThrow(packCode));
  }

  private GiftPackServerCodeEntity queryOrElseThrow(String packCode) {
    return codeRepo.findById(packCode).orElseThrow();
  }
}
