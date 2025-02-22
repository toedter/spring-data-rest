/*
 * Copyright 2016-2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.data.rest.webmvc.json;

import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortArgumentResolver;
import org.springframework.data.web.SortHandlerMethodArgumentResolver;
import org.springframework.util.Assert;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * {@link HandlerMethodArgumentResolver} to resolve {@link Sort} from a {@link SortHandlerMethodArgumentResolver}
 * applying field to property mapping.
 * <p>
 * A resolved {@link Sort} is post-processed by applying Jackson field-to-property mapping. Customized fields are
 * resolved to their property names. Unknown properties are removed from {@link Sort}.
 *
 * @author Mark Paluch
 * @author Oliver Gierke
 * @since 2.6
 */
public class MappingAwareSortArgumentResolver implements HandlerMethodArgumentResolver, SortArgumentResolver {

	private final JacksonMappingAwareSortTranslator translator;
	private final SortArgumentResolver delegate;

	public MappingAwareSortArgumentResolver(JacksonMappingAwareSortTranslator translator, SortArgumentResolver delegate) {

		Assert.notNull(translator, "JacksonMappingAwareSortTranslator must not be null!");
		Assert.notNull(delegate, "Delegate SortArgumentResolver must not be null!");

		this.translator = translator;
		this.delegate = delegate;
	}

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return delegate.supportsParameter(parameter);
	}

	@Override
	public Sort resolveArgument(MethodParameter methodParameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {

		Sort sort = delegate.resolveArgument(methodParameter, mavContainer, webRequest, binderFactory);

		return sort.isUnsorted() ? sort : translator.translateSort(sort, methodParameter, webRequest);
	}
}
