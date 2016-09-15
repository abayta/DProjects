package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.ClinicRepository;
import domain.Clinic;

@Component
@Transactional
public class StringToClinicConverter implements Converter<String, Clinic> {

	@Autowired
	ClinicRepository clinicRepository;

	@Override
	public Clinic convert(String text) {
		Clinic result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = clinicRepository.findOne(id);
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
