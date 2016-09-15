package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.BidRepository;
import domain.Bid;

@Component
@Transactional
public class StringToBidConverter implements Converter<String, Bid> {

	@Autowired
	BidRepository bidRepository;

	@Override
	public Bid convert(String text) {
		Bid result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = bidRepository.findOne(id);
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
