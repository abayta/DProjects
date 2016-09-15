package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.AuctionRepository;
import domain.Auction;

@Component
@Transactional
public class StringToAuctionConverter implements Converter<String, Auction> {

	@Autowired
	AuctionRepository auctionRepository;

	@Override
	public Auction convert(String text) {
		Auction result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = auctionRepository.findOne(id);
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
