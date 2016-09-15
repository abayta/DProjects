package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.PaintingRepository;
import domain.Painting;

@Component
@Transactional
public class StringToPaintingConverter implements Converter<String, Painting> {

	@Autowired
	PaintingRepository paintingRepository;

	@Override
	public Painting convert(String text) {
		Painting result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = paintingRepository.findOne(id);
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
