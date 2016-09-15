package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.DoctorRepository;
import domain.Doctor;

@Component
@Transactional
public class StringToDoctorConverter implements Converter<String, Doctor> {

	@Autowired
	DoctorRepository doctorRepository;

	@Override
	public Doctor convert(String text) {
		Doctor result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = doctorRepository.findOne(id);
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
