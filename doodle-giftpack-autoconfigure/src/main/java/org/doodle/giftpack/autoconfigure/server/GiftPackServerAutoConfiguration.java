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
    basePackageClasses = {GiftPackServerCodeRepo.class, GiftPackServerPlaceRepo.class})
public class GiftPackServerAutoConfiguration {

  @Bean
  @ConditionalOnMissingBean
  public GiftPackServerMapper giftPackServerMapper() {
    return new GiftPackServerMapper();
  }

  @AutoConfiguration
  @ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
  public static class ServletConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public GiftPackServerCodeServletController giftPackServerCodeServletController() {
      return new GiftPackServerCodeServletController();
    }

    @Bean
    @ConditionalOnMissingBean
    public GiftPackServerPlaceServletController giftPackServerPlaceServletController() {
      return new GiftPackServerPlaceServletController();
    }
  }

  @AutoConfiguration
  public static class RSocketConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public GiftPackServerCodeRSocketController giftPackServerCodeRSocketController() {
      return new GiftPackServerCodeRSocketController();
    }

    @Bean
    @ConditionalOnMissingBean
    public GiftPackServerPlaceRSocketController giftPackServerPlaceRSocketController() {
      return new GiftPackServerPlaceRSocketController();
    }
  }
}
