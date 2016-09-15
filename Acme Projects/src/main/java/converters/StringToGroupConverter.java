package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.GroupRepository;


import domain.Group;

@Component
@Transactional
public class StringToGroupConverter implements Converter<String, Group> {

	@Autowired
	GroupRepository groupRepository;

	@Override
	public Group convert(String text) {
		Group result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = groupRepository.findOne(id);
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
