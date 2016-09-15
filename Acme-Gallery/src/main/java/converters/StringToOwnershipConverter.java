package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.OwnershipRepository;
import domain.Ownership;

@Component
@Transactional
public class StringToOwnershipConverter implements Converter<String, Ownership> {

	@Autowired
	OwnershipRepository ownershipRepository;

	@Override
	public Ownership convert(String text) {
		Ownership result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = ownershipRepository.findOne(id);
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
