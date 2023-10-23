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

import java.util.Map;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.doodle.design.giftpack.GiftPackType;
import org.doodle.design.giftpack.model.info.GiftPackInfo;
import reactor.core.publisher.Mono;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class GiftPackServerPackUseService {
  GiftPackServerPackService packService;
  Map<GiftPackType, GiftPackServerPackUseHandler> handlerMap;

  public Mono<GiftPackInfo> useMono(String roleId, String packCode) {
    return Mono.fromCallable(() -> use(roleId, packCode));
  }

  public GiftPackInfo use(String roleId, String packCode) {
    GiftPackType packType = packService.packType(packCode);
    if (packType == GiftPackType.UNRECOGNIZED) {
      throw new IllegalArgumentException();
    }

    GiftPackServerPackUseHandler packUseHandler = handlerMap.get(packType);
    if (Objects.isNull(packUseHandler)) {
      throw new IllegalStateException("找不到对应的处理handler " + packType);
    }

    return packUseHandler.use(roleId, packCode);
  }
}
