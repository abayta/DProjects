package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Auction;

@Component
@Transactional
public class AuctionToStringConverter implements Converter<Auction, String> {

	@Override
	public String convert(Auction auction) {
		String result;

		if (auction == null)
			result = null;
		else
			result = String.valueOf(auction.getId());

		return result;
	}

}
