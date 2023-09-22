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
    basePackageClasses = {
      GiftPackServerVisionRepo.class,
      GiftPackServerGiftRepo.class,
      GiftPackServerCodeRepo.class,
      GiftPackServerPackRepo.class,
      GiftPackServerGiftVisionRepo.class,
      GiftPackServerCodeGiftRepo.class,
      GiftPackServerPackCodeRepo.class
    })
public class GiftPackServerAutoConfiguration {

  @Bean
  @ConditionalOnMissingBean
  public GiftPackServerMapper giftPackServerMapper() {
    return new GiftPackServerMapper();
  }

  @Bean
  @ConditionalOnMissingBean
  public GiftPackServerMappingService giftPackServerMappingService(
      GiftPackServerGiftVisionRepo giftVisionRepo,
      GiftPackServerCodeGiftRepo codeGiftRepo,
      GiftPackServerPackCodeRepo packCodeRepo) {
    return new GiftPackServerMappingService(giftVisionRepo, codeGiftRepo, packCodeRepo);
  }

  @Bean
  @ConditionalOnMissingBean
  public GiftPackServerVisionService giftPackServerVisionService(
      GiftPackServerMapper mapper, GiftPackServerVisionRepo visionRepo) {
    return new GiftPackServerVisionService(mapper, visionRepo);
  }

  @Bean
  @ConditionalOnMissingBean
  public GiftPackServerGiftService giftPackServerGiftService(
      GiftPackServerGiftRepo giftRepo,
      GiftPackServerMappingService mappingService,
      GiftPackServerVisionService visionService) {
    return new GiftPackServerGiftService(giftRepo, mappingService, visionService);
  }

  @Bean
  @ConditionalOnMissingBean
  public GiftPackServerCodeService giftPackServerCodeService(
      GiftPackServerCodeRepo codeRepo,
      GiftPackServerMappingService mappingService,
      GiftPackServerGiftService giftService) {
    return new GiftPackServerCodeService(codeRepo, mappingService, giftService);
  }

  @Bean
  @ConditionalOnMissingBean
  public GiftPackServerPackService giftPackServerPackService(
      GiftPackServerPackRepo packRepo,
      GiftPackServerMappingService mappingService,
      GiftPackServerCodeService codeService) {
    return new GiftPackServerPackService(packRepo, mappingService, codeService);
  }

  @AutoConfiguration
  @ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
  public static class ServletConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public GiftPackServerVisionServletController giftPackServerVisionServletController(
        GiftPackServerVisionService visionService) {
      return new GiftPackServerVisionServletController(visionService);
    }

    @Bean
    @ConditionalOnMissingBean
    public GiftPackServerGiftServletController giftPackServerGiftServletController(
        GiftPackServerGiftService giftService) {
      return new GiftPackServerGiftServletController(giftService);
    }

    @Bean
    @ConditionalOnMissingBean
    public GiftPackServerCodeServletController giftPackServerCodeServletController(
        GiftPackServerCodeService codeService) {
      return new GiftPackServerCodeServletController(codeService);
    }

    @Bean
    @ConditionalOnMissingBean
    public GIftPackServerPackServletController gIftPackServerPackServletController(
        GiftPackServerPackService packService) {
      return new GIftPackServerPackServletController(packService);
    }
  }

  @AutoConfiguration
  public static class RSocketConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public GiftPackServerVisionRSocketController giftPackServerVisionRSocketController(
        GiftPackServerMapper mapper, GiftPackServerVisionService visionService) {
      return new GiftPackServerVisionRSocketController(mapper, visionService);
    }

    @Bean
    @ConditionalOnMissingBean
    public GiftPackServerGiftRSocketController giftPackServerGiftRSocketController(
        GiftPackServerMapper mapper, GiftPackServerGiftService giftService) {
      return new GiftPackServerGiftRSocketController(mapper, giftService);
    }

    @Bean
    @ConditionalOnMissingBean
    public GiftPackServerCodeRSocketController giftPackServerCodeRSocketController(
        GiftPackServerMapper mapper, GiftPackServerCodeService codeService) {
      return new GiftPackServerCodeRSocketController(mapper, codeService);
    }

    @Bean
    @ConditionalOnMissingBean
    public GIftPackServerPackRSocketController gIftPackServerPackRSocketController(
        GiftPackServerMapper mapper, GiftPackServerPackService packService) {
      return new GIftPackServerPackRSocketController(mapper, packService);
    }
  }
}
