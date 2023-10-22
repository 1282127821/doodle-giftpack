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

import java.time.LocalDateTime;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.doodle.design.giftpack.model.info.GiftPackLifecycleInfo;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Builder
@ToString
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = GiftPackServerSpecEntity.COLLECTION)
public class GiftPackServerSpecEntity {
  public static final String COLLECTION = "giftpack-specs";

  @MongoId long specId;

  String roleId;

  long contentId;

  GiftPackLifecycleInfo lifecycle;

  @Version byte dummy;

  @CreatedDate LocalDateTime createdAt;

  @LastModifiedDate LocalDateTime modifiedAt;
}
