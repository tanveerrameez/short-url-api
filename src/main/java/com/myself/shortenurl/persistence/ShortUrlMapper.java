package com.myself.shortenurl.persistence;

import java.util.Optional;

import com.myself.shortenurl.model.ShortUrlMapping;

public interface ShortUrlMapper {

	 void saveUrlMapping(String originalUrl, ShortUrlMapping urlMapping);
	 Optional<ShortUrlMapping> getOriginalUrl(String shortUrl) ;
	 void removeExpiredUrl(String shortUrl);
}
