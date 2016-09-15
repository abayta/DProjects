package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Bid;

@Component
@Transactional
public class BidToStringConverter implements Converter<Bid, String> {

	@Override
	public String convert(Bid bid) {
		String result;

		if (bid == null)
			result = null;
		else
			result = String.valueOf(bid.getId());

		return result;
	}

}
