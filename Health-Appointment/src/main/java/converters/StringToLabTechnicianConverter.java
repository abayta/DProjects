package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.LabTechnicianRepository;
import domain.LabTechnician;

@Component
@Transactional
public class StringToLabTechnicianConverter implements Converter<String, LabTechnician> {

	@Autowired
	LabTechnicianRepository labTechnicianRepository;

	@Override
	public LabTechnician convert(String text) {
		LabTechnician result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = labTechnicianRepository.findOne(id);
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
