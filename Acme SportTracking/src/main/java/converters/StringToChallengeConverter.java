/* Copyright (C) 2013 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 * 
 */

package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.ChallengeRepository;
import domain.Challenge;

@Component
@Transactional
public class StringToChallengeConverter implements Converter<String, Challenge> {

	@Autowired
	ChallengeRepository challengeRepository;

	@Override
	public Challenge convert(String text) {
		Challenge result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = challengeRepository.findOne(id);
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
