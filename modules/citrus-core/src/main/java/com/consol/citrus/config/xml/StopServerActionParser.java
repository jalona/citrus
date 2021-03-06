/*
 * Copyright 2006-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.consol.citrus.config.xml;

import com.consol.citrus.actions.StopServerAction;
import com.consol.citrus.config.util.BeanDefinitionParserUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.ManagedList;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;

import java.util.Iterator;
import java.util.List;

/**
 * @author Christoph Deppisch
 * @since 2.2
 */
public class StopServerActionParser implements BeanDefinitionParser {

    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {
        BeanDefinitionBuilder beanDefinition = BeanDefinitionBuilder.rootBeanDefinition(StopServerAction.class);

        DescriptionElementParser.doParse(element, beanDefinition);

        BeanDefinitionParserUtils.setPropertyReference(beanDefinition, element.getAttribute("server"), "server");

        ManagedList<RuntimeBeanReference> servers = new ManagedList();
        Element serversElement = DomUtils.getChildElementByTagName(element, "servers");
        if (serversElement != null) {
            List<?> serverElements = DomUtils.getChildElementsByTagName(serversElement, "server");
            if (serverElements.size() > 0) {
                for (Iterator<?> iter = serverElements.iterator(); iter.hasNext();) {
                    Element serverElement = (Element) iter.next();
                    servers.add(new RuntimeBeanReference(serverElement.getAttribute("name")));
                }

                beanDefinition.addPropertyValue("serverList", servers);
            }
        }

        return beanDefinition.getBeanDefinition();
    }
}
