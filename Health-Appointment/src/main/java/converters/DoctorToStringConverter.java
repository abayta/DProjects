package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Doctor;

@Component
@Transactional
public class DoctorToStringConverter implements Converter<Doctor, String> {

	@Override
	public String convert(Doctor doctor) {
		String result;

		if (doctor == null)
			result = null;
		else
			result = String.valueOf(doctor.getId());

		return result;
	}

}
