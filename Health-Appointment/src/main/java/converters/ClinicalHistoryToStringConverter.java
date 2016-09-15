package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.ClinicalHistory;

@Component
@Transactional
public class ClinicalHistoryToStringConverter implements Converter<ClinicalHistory, String> {

	@Override
	public String convert(ClinicalHistory clinicalHistory) {
		String result;

		if (clinicalHistory == null)
			result = null;
		else
			result = String.valueOf(clinicalHistory.getId());

		return result;
	}

}
