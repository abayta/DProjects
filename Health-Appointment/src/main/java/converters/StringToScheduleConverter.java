package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.ScheduleRepository;
import domain.Schedule;

@Component
@Transactional
public class StringToScheduleConverter implements Converter<String, Schedule> {

	@Autowired
	ScheduleRepository calendarRepository;

	@Override
	public Schedule convert(String text) {
		Schedule result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = calendarRepository.findOne(id);
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
