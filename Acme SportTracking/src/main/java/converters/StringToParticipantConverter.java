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

import repositories.ParticipantRepository;
import domain.Participant;

@Component
@Transactional
public class StringToParticipantConverter implements Converter<String, Participant> {

	@Autowired
	ParticipantRepository participantRepository;

	@Override
	public Participant convert(String text) {
		Participant result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = participantRepository.findOne(id);
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
