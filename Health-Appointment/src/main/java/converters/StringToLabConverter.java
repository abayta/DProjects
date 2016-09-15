package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.LabRepository;
import domain.Lab;

@Component
@Transactional
public class StringToLabConverter implements Converter<String, Lab> {

	@Autowired
	LabRepository labRepository;

	@Override
	public Lab convert(String text) {
		Lab result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = labRepository.findOne(id);
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
