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

import java.time.Duration;
import java.time.LocalDateTime;
import org.doodle.design.hashids.Hashids;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GiftPackServerApplication implements CommandLineRunner {
  public static void main(String[] args) {
    SpringApplication.run(GiftPackServerApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    Hashids hashids =
        new Hashids(
            "GIFTPACK-SALT", 6, "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789");
    LocalDateTime now = LocalDateTime.now();
    for (int i = 0; i < 100000; i++) {
      String encode = hashids.encode(101, 1, i);
      System.out.println("ID: " + i + " " + encode);
      long[] decode = hashids.decode(encode);
      System.out.println(" 礼包ID: " + decode[0]);
      System.out.println(" 批次: " + decode[1]);
      System.out.println(" 计数: " + decode[2]);
    }
    Duration between = Duration.between(now, LocalDateTime.now());
    System.out.println("耗时:" + between.toMillis() + " ms");
  }
}
