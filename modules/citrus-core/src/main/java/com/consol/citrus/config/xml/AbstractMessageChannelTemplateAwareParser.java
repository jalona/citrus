/*
 * Copyright 2006-2009 ConSol* Software GmbH.
 * 
 * This file is part of Citrus.
 * 
 *  Citrus is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Citrus is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Citrus.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.consol.citrus.config.xml;

import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

public abstract class AbstractMessageChannelTemplateAwareParser extends AbstractBeanDefinitionParser {

    @Override
    protected AbstractBeanDefinition parseInternal(Element element, ParserContext parserContext) {
        BeanDefinitionBuilder builder = doParseComponent(element, parserContext);
        
        String msgChannelTemplate = element.getAttribute("message-channel-template");
        
        if (StringUtils.hasText(msgChannelTemplate)) {
            if (element.hasAttribute("channel")) {
                throw new BeanCreationException("When providing a 'message-channel-template' reference, no " +
                        "'channel' should be provided.");
            }
            builder.addPropertyReference("messageChannelTemplate", msgChannelTemplate);
        }
        
        return builder.getBeanDefinition();
    }
    
    protected abstract BeanDefinitionBuilder doParseComponent(Element element, ParserContext parserContext);
}