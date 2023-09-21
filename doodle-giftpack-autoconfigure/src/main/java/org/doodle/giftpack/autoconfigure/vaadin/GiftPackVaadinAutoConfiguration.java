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
package org.doodle.giftpack.autoconfigure.vaadin;

import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.sidenav.SideNavItem;
import org.doodle.boot.vaadin.EnableVaadin;
import org.doodle.boot.vaadin.views.VaadinSideNavItemSupplier;
import org.doodle.giftpack.vaadin.GiftPackVaadinProperties;
import org.doodle.giftpack.vaadin.views.GiftPackVaadinCodeView;
import org.doodle.giftpack.vaadin.views.GiftPackVaadinGiftView;
import org.doodle.giftpack.vaadin.views.GiftPackVaadinPackView;
import org.doodle.giftpack.vaadin.views.GiftPackVaadinVisionView;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
@ConditionalOnClass(GiftPackVaadinProperties.class)
@EnableConfigurationProperties(GiftPackVaadinProperties.class)
@EnableVaadin(GiftPackVaadinProperties.PREFIX_VIEWS)
public class GiftPackVaadinAutoConfiguration {

  @Bean
  public VaadinSideNavItemSupplier giftPackSideNavView() {
    return (authenticationContext) -> {
      SideNavItem item = new SideNavItem("礼包组件");
      item.setPrefixComponent(VaadinIcon.GIFT.create());
      item.addItem(
          new SideNavItem("视觉", GiftPackVaadinVisionView.class, VaadinIcon.VIEWPORT.create()));
      item.addItem(new SideNavItem("礼物", GiftPackVaadinGiftView.class, VaadinIcon.GIFT.create()));
      item.addItem(new SideNavItem("编码", GiftPackVaadinCodeView.class, VaadinIcon.CODE.create()));
      item.addItem(
          new SideNavItem("礼包", GiftPackVaadinPackView.class, VaadinIcon.PACKAGE.create()));
      return item;
    };
  }
}
