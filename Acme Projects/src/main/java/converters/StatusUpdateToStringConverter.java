package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import domain.StatusUpdate;

@Component
@Transactional
public class StatusUpdateToStringConverter implements Converter<StatusUpdate, String> {

	@Override
	public String convert(StatusUpdate statusUpdate) {
		String result;

		if (statusUpdate == null)
			result = null;
		else
			result = String.valueOf(statusUpdate.getId());

		return result;
	}

}