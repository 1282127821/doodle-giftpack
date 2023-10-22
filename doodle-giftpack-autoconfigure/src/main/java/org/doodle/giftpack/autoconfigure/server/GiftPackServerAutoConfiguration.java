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
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@AutoConfiguration(after = BrokerClientAutoConfiguration.class)
@ConditionalOnClass(GiftPackServerProperties.class)
@EnableConfigurationProperties(GiftPackServerProperties.class)
@EnableMongoAuditing
@EnableMongoRepositories(
    basePackageClasses = {
      GiftPackServerGroupRepo.class,
      GiftPackServerBatchRepo.class,
      GiftPackServerSpecRepo.class,
      GiftPackServerContentRepo.class,
    })
public class GiftPackServerAutoConfiguration {

  @Bean
  @ConditionalOnMissingBean
  public GiftPackServerMapper giftPackServerMapper() {
    return new GiftPackServerMapper();
  }

  @Bean
  @ConditionalOnMissingBean
  public GiftPackServerContentService giftPackServerContentService(
      MongoTemplate mongoTemplate, GiftPackServerContentRepo contentRepo) {
    return new GiftPackServerContentService(mongoTemplate, contentRepo);
  }

  @Bean
  @ConditionalOnMissingBean
  public GiftPackServerContentListener giftPackServerContentListener(
      GiftPackServerContentService contentService) {
    return new GiftPackServerContentListener(contentService);
  }

  @Bean
  @ConditionalOnMissingBean
  public GiftPackServerGroupService giftPackServerGroupService(
      MongoTemplate mongoTemplate, GiftPackServerGroupRepo groupRepo) {
    return new GiftPackServerGroupService(mongoTemplate, groupRepo);
  }

  @Bean
  @ConditionalOnMissingBean
  public GiftPackServerGroupListener giftPackServerGroupListener(
      GiftPackServerGroupService groupService) {
    return new GiftPackServerGroupListener(groupService);
  }

  @Bean
  @ConditionalOnMissingBean
  public GiftPackServerBatchService giftPackServerBatchService(
      MongoTemplate mongoTemplate, GiftPackServerBatchRepo batchRepo) {
    return new GiftPackServerBatchService(mongoTemplate, batchRepo);
  }

  @Bean
  @ConditionalOnMissingBean
  public GiftPackServerBatchListener giftPackServerBatchListener(
      GiftPackServerBatchService batchService) {
    return new GiftPackServerBatchListener(batchService);
  }

  @Bean
  @ConditionalOnMissingBean
  public GiftPackServerSpecService giftPackServerSpecService(
      MongoTemplate mongoTemplate, GiftPackServerSpecRepo specRepo) {
    return new GiftPackServerSpecService(mongoTemplate, specRepo);
  }

  @Bean
  @ConditionalOnMissingBean
  public GiftPackServerSpecListener giftPackServerSpecListener(
      GiftPackServerSpecService specService) {
    return new GiftPackServerSpecListener(specService);
  }

  @AutoConfiguration
  @ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
  public static class ServletConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public GiftPackServerContentServletController giftPackServerContentServletController(
        GiftPackServerContentService contentService) {
      return new GiftPackServerContentServletController(contentService);
    }

    @Bean
    @ConditionalOnMissingBean
    public GiftPackServerGroupServletController giftPackServerGroupServletController(
        GiftPackServerGroupService groupService) {
      return new GiftPackServerGroupServletController(groupService);
    }

    @Bean
    @ConditionalOnMissingBean
    public GiftPackServerBatchServletController giftPackServerBatchServletController(
        GiftPackServerBatchService batchService) {
      return new GiftPackServerBatchServletController(batchService);
    }

    @Bean
    @ConditionalOnMissingBean
    public GiftPackServerSpecServletController giftPackServerSpecServletController(
        GiftPackServerSpecService specService) {
      return new GiftPackServerSpecServletController(specService);
    }
  }

  @AutoConfiguration
  public static class RSocketConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public GiftPackServerContentRSocketController giftPackServerContentRSocketController(
        GiftPackServerMapper mapper, GiftPackServerContentService contentService) {
      return new GiftPackServerContentRSocketController(mapper, contentService);
    }

    @Bean
    @ConditionalOnMissingBean
    public GiftPackServerGroupRSocketController giftPackServerGroupRSocketController(
        GiftPackServerMapper mapper, GiftPackServerGroupService groupService) {
      return new GiftPackServerGroupRSocketController(mapper, groupService);
    }

    @Bean
    @ConditionalOnMissingBean
    public GiftPackServerBatchRSocketController giftPackServerBatchRSocketController(
        GiftPackServerMapper mapper, GiftPackServerBatchService batchService) {
      return new GiftPackServerBatchRSocketController(mapper, batchService);
    }

    @Bean
    @ConditionalOnMissingBean
    public GiftPackServerSpecRSocketController giftPackServerSpecRSocketController(
        GiftPackServerMapper mapper, GiftPackServerSpecService specService) {
      return new GiftPackServerSpecRSocketController(mapper, specService);
    }
  }
}
