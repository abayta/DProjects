package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Gallery;

@Component
@Transactional
public class GalleryToStringConverter implements Converter<Gallery, String> {

	@Override
	public String convert(Gallery gallery) {
		String result;

		if (gallery == null)
			result = null;
		else
			result = String.valueOf(gallery.getId());

		return result;
	}

}
