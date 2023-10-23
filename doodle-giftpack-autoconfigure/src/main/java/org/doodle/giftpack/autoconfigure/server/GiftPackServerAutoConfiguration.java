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

import java.util.stream.Collectors;
import org.doodle.broker.autoconfigure.client.BrokerClientAutoConfiguration;
import org.doodle.giftpack.server.*;
import org.springframework.beans.factory.ObjectProvider;
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
      GiftPackServerRoleLogRepo.class,
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
      MongoTemplate mongoTemplate,
      GiftPackServerMapper mapper,
      GiftPackServerContentRepo contentRepo) {
    return new GiftPackServerContentService(mongoTemplate, mapper, contentRepo);
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
      MongoTemplate mongoTemplate,
      GiftPackServerMapper mapper,
      GiftPackServerGroupRepo groupRepo,
      GiftPackServerContentService contentService,
      GiftPackServerRoleService roleService) {
    return new GiftPackServerGroupService(
        mongoTemplate, mapper, groupRepo, contentService, roleService);
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
      MongoTemplate mongoTemplate,
      GiftPackServerMapper mapper,
      GiftPackServerBatchRepo batchRepo,
      GiftPackServerContentService contentService,
      GiftPackServerPackService packService) {
    return new GiftPackServerBatchService(
        mongoTemplate, mapper, batchRepo, contentService, packService);
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
      MongoTemplate mongoTemplate,
      GiftPackServerMapper mapper,
      GiftPackServerSpecRepo specRepo,
      GiftPackServerContentService contentService,
      GiftPackServerPackService packService) {
    return new GiftPackServerSpecService(
        mongoTemplate, mapper, specRepo, contentService, packService);
  }

  @Bean
  @ConditionalOnMissingBean
  public GiftPackServerSpecListener giftPackServerSpecListener(
      GiftPackServerSpecService specService) {
    return new GiftPackServerSpecListener(specService);
  }

  @Bean
  @ConditionalOnMissingBean
  public GiftPackServerPackService giftPackServerPackService(GiftPackServerProperties properties) {
    return new GiftPackServerPackService(properties);
  }

  @Bean
  @ConditionalOnMissingBean
  public GiftPackServerPackUseService giftPackServerPackUseService(
      GiftPackServerPackService packService,
      ObjectProvider<GiftPackServerPackUseHandler> provider) {
    return new GiftPackServerPackUseService(
        packService,
        provider
            .orderedStream()
            .collect(Collectors.toMap(GiftPackServerPackUseHandler::packType, (v) -> v)));
  }

  @Bean
  @ConditionalOnMissingBean
  public GiftPackServerRoleService giftPackServerRoleService(
      GiftPackServerMapper mapper, GiftPackServerRoleLogRepo roleLogRepo) {
    return new GiftPackServerRoleService(mapper, roleLogRepo);
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

    @Bean
    @ConditionalOnMissingBean
    public GiftPackServerRoleServletController giftPackServerRoleServletController(
        GiftPackServerPackUseService packUseService) {
      return new GiftPackServerRoleServletController(packUseService);
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

    @Bean
    @ConditionalOnMissingBean
    public GiftPackServerRoleRSocketController giftPackServerRoleRSocketController(
        GiftPackServerMapper mapper, GiftPackServerPackUseService packUseService) {
      return new GiftPackServerRoleRSocketController(mapper, packUseService);
    }
  }
}
