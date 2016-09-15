package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.MedicalReportRepository;
import domain.MedicalReport;

@Component
@Transactional
public class StringToMedicalReportConverter implements Converter<String, MedicalReport> {

	@Autowired
	MedicalReportRepository medicalReportRepository;

	@Override
	public MedicalReport convert(String text) {
		MedicalReport result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = medicalReportRepository.findOne(id);
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
