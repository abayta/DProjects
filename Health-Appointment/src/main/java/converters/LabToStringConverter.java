package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Lab;

@Component
@Transactional
public class LabToStringConverter implements Converter<Lab, String> {

	@Override
	public String convert(Lab lab) {
		String result;
		
		if (lab == null)
			result = null;
		else
			result = String.valueOf(lab.getId());

		return result;
	}

}
