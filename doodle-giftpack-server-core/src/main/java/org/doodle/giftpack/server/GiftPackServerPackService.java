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

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.doodle.design.giftpack.GiftPackType;
import org.doodle.design.hashids.Hashids;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GiftPackServerPackService {
  public static final int HASHIDS_DESIGNED_LENGTH = 3;
  public static final long HASHIDS_BATCH_TYPE = 1L;
  public static final long HASHIDS_SPEC_TYPE = 2L;

  Hashids hashids;
  int groupCodeMaxLength;

  public GiftPackServerPackService(GiftPackServerProperties properties) {
    Assert.hasText(properties.getHashids().getSalt(), "Hashids salt 不能为空");
    this.hashids =
        new Hashids(properties.getHashids().getSalt(), properties.getGroupCodeMaxLength() + 1);
    this.groupCodeMaxLength = properties.getGroupCodeMaxLength();
  }

  public GiftPackType packType(String packCode) {
    if (!StringUtils.hasText(packCode)) {
      return GiftPackType.UNRECOGNIZED;
    }

    if (packCode.length() <= groupCodeMaxLength) {
      return GiftPackType.GROUP;
    }

    long[] unpacked = unpackHashIds(packCode);

    if (unpacked.length == HASHIDS_DESIGNED_LENGTH && unpacked[0] == HASHIDS_BATCH_TYPE) {
      return GiftPackType.BATCH;
    }

    if (unpacked.length == HASHIDS_DESIGNED_LENGTH && unpacked[0] == HASHIDS_SPEC_TYPE) {
      return GiftPackType.SPEC;
    }

    return GiftPackType.UNRECOGNIZED;
  }

  public long[] unpackHashIds(String packCode) {
    return hashids.decode(packCode);
  }
}
