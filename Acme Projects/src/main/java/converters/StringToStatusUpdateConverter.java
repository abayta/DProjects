package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.StatusUpdateRepository;


import domain.StatusUpdate;

@Component
@Transactional
public class StringToStatusUpdateConverter implements Converter<String, StatusUpdate> {

	@Autowired
	StatusUpdateRepository statusUpdateRepository;

	@Override
	public StatusUpdate convert(String text) {
		StatusUpdate result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = statusUpdateRepository.findOne(id);
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
