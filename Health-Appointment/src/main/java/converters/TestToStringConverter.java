package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Test;

@Component
@Transactional
public class TestToStringConverter implements Converter<Test, String> {

	@Override
	public String convert(Test test) {
		String result;

		if (test == null)
			result = null;
		else
			result = String.valueOf(test.getId());

		return result;
	}

}
