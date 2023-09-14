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
package org.doodle.giftpack.autoconfigure.server;

import org.doodle.broker.autoconfigure.client.BrokerClientAutoConfiguration;
import org.doodle.giftpack.server.*;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@AutoConfiguration(after = BrokerClientAutoConfiguration.class)
@ConditionalOnClass(GiftPackServerProperties.class)
@EnableConfigurationProperties(GiftPackServerProperties.class)
@EnableMongoAuditing
@EnableMongoRepositories(
    basePackageClasses = {GiftPackServerPackRepo.class, GiftPackServerHashRepo.class})
public class GiftPackServerAutoConfiguration {

  @Bean
  @ConditionalOnMissingBean
  public GiftPackServerMapper giftPackServerMapper() {
    return new GiftPackServerMapper();
  }

  @Bean
  @ConditionalOnMissingBean
  public GiftPackServerPackService giftPackServerPackService(GiftPackServerPackRepo packRepo) {
    return new GiftPackServerPackService(packRepo);
  }

  @Bean
  @ConditionalOnMissingBean
  public GiftPackServerHashService giftPackServerHashService(GiftPackServerHashRepo hashRepo) {
    return new GiftPackServerHashService(hashRepo);
  }

  @AutoConfiguration
  @ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
  public static class ServletConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public GiftPackServerPackServletController giftPackServerPackServletController() {
      return new GiftPackServerPackServletController();
    }

    @Bean
    @ConditionalOnMissingBean
    public GiftPackServerHashServletController giftPackServerHashServletController() {
      return new GiftPackServerHashServletController();
    }
  }

  @AutoConfiguration
  public static class RSocketConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public GiftPackServerPackRSocketController giftPackServerPackRSocketController(
        GiftPackServerPackService packService) {
      return new GiftPackServerPackRSocketController(packService);
    }

    @Bean
    @ConditionalOnMissingBean
    public GiftPackServerHashRSocketController giftPackServerHashRSocketController(
        GiftPackServerHashService hashService) {
      return new GiftPackServerHashRSocketController(hashService);
    }
  }
}
