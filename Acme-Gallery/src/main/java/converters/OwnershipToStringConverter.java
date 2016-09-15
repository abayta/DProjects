package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Ownership;

@Component
@Transactional
public class OwnershipToStringConverter implements Converter<Ownership, String> {

	@Override
	public String convert(Ownership ownership) {
		String result;

		if (ownership == null)
			result = null;
		else
			result = String.valueOf(ownership.getId());

		return result;
	}

}
