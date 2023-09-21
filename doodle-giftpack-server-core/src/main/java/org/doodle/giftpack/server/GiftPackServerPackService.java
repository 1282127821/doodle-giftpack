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
import org.doodle.design.giftpack.model.info.PackInfo;
import reactor.core.publisher.Mono;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class GiftPackServerPackService {
  GiftPackServerPackRepo packRepo;

  public Mono<List<PackInfo>> pageMono(PageRequest request) {
    return Mono.fromCallable(() -> page(request));
  }

  public List<PackInfo> page(PageRequest request) {
    return Collections.emptyList();
  }

  public Mono<PackInfo> queryMono(String packCode) {
    return Mono.fromCallable(() -> query(packCode));
  }

  public PackInfo query(String packCode) {
    return null;
  }
}
