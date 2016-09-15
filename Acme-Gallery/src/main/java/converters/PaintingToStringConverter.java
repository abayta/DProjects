package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Painting;

@Component
@Transactional
public class PaintingToStringConverter implements Converter<Painting, String> {

	@Override
	public String convert(Painting painting) {
		String result;

		if (painting == null)
			result = null;
		else
			result = String.valueOf(painting.getId());

		return result;
	}

}
