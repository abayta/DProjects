package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Schedule;

@Component
@Transactional
public class ScheduleToStringConverter implements Converter<Schedule, String> {

	@Override
	public String convert(Schedule calendar) {
		String result;

		if (calendar == null)
			result = null;
		else
			result = String.valueOf(calendar.getId());

		return result;
	}

}
