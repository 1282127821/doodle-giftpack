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
package org.doodle.giftpack.server.single;

import java.util.UUID;
import org.doodle.design.giftpack.model.info.GiftPackInfo;
import org.doodle.design.hashids.Hashids;
import org.doodle.giftpack.server.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GiftPackServerApplication implements CommandLineRunner {

  @Autowired GiftPackServerContentRepo contentRepo;

  @Autowired GiftPackServerGroupRepo groupRepo;
  @Autowired GiftPackServerGroupService groupService;
  @Autowired GiftPackServerBatchRepo batchRepo;
  @Autowired GiftPackServerPackService packService;

  @Autowired GiftPackServerPackUseService packUseService;
  @Autowired GiftPackServerProperties properties;

  public static void main(String[] args) {
    SpringApplication.run(GiftPackServerApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    //    GiftPackServerContentEntity contentEntity =
    //        contentRepo.save(GiftPackServerContentEntity.builder().content("测试内容").build());
    //    GiftPackServerGroupEntity groupEntity =
    //        groupRepo.save(
    //            GiftPackServerGroupEntity.builder()
    //                .groupCode("VIP666")
    //                .contentId(contentEntity.getContentId())
    //                .lifecycle(
    //                    GiftPackLifecycleInfo.builder()
    //                        .start(Instant.now())
    //                        .end(
    //                            Instant.ofEpochMilli(
    //                                System.currentTimeMillis() + Duration.ofHours(1).toMillis()))
    //                        .build())
    //                .build());

    //    GiftPackServerContentEntity contentEntity =
    //        contentRepo.save(GiftPackServerContentEntity.builder().content("测试内容").build());
    //
    //    batchRepo.save(
    //        GiftPackServerBatchEntity.builder()
    //            .batchId(1)
    //            .batchSize(1000)
    //            .contentId(contentEntity.getContentId())
    //            .lifecycle(
    //                GiftPackLifecycleInfo.builder()
    //                    .start(Instant.now())
    //                    .end(
    //                        Instant.ofEpochMilli(
    //                            System.currentTimeMillis() + Duration.ofHours(1).toMillis()))
    //                    .build())
    //            .build());

    Hashids hashids =
        new Hashids(properties.getHashids().getSalt(), properties.getGroupCodeMaxLength() + 1);
    String code_3 = hashids.encode(1, 1, 2001);

    GiftPackInfo packInfo = packUseService.use(UUID.randomUUID().toString(), code_3);
    System.out.println(packInfo);

    //    long now = System.currentTimeMillis();
    //    for (int i = 0; i < 1000; i++) {
    //      try {
    //        GiftPackInfo packInfo = packUseService.use(UUID.randomUUID().toString(), "VIP666");
    //      } catch (Exception e) {
    //
    //      }
    //    }
    //    System.out.println("消耗时间: " + (System.currentTimeMillis() - now));
  }
}
