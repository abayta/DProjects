package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.LabTechnician;

@Component
@Transactional
public class LabTechnicianToStringConverter implements Converter<LabTechnician, String> {

	@Override
	public String convert(LabTechnician labTechnician) {
		String result;

		if (labTechnician == null)
			result = null;
		else
			result = String.valueOf(labTechnician.getId());

		return result;
	}

}
