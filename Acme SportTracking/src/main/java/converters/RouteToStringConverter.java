/*
 * Copyright (C) 2013 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 * 
 */

package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import domain.Route;

@Component
@Transactional
public class RouteToStringConverter implements Converter<Route, String> {

	@Override
	public String convert(Route route) {
		String result;

		if (route == null)
			result = null;
		else
			result = String.valueOf(route.getId());

		return result;
	}

}