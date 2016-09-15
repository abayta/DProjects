package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.GalleryRepository;
import domain.Gallery;

@Component
@Transactional
public class StringToGalleryConverter implements Converter<String, Gallery> {

	@Autowired
	GalleryRepository galleryRepository;

	@Override
	public Gallery convert(String text) {
		Gallery result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = galleryRepository.findOne(id);
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
