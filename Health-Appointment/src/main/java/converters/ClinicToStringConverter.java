package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Clinic;

@Component
@Transactional
public class ClinicToStringConverter implements Converter<Clinic, String> {

	@Override
	public String convert(Clinic clinic) {
		String result;

		if (clinic == null)
			result = null;
		else
			result = String.valueOf(clinic.getId());

		return result;
	}

}
