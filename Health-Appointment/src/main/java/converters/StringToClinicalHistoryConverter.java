package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.ClinicalHistoryRepository;
import domain.ClinicalHistory;

@Component
@Transactional
public class StringToClinicalHistoryConverter implements Converter<String, ClinicalHistory> {

	@Autowired
	ClinicalHistoryRepository clinicalHistoryRepository;

	@Override
	public ClinicalHistory convert(String text) {
		ClinicalHistory result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = clinicalHistoryRepository.findOne(id);
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
