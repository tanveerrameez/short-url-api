package com.myself.shortenurl.persistence;

import java.util.HashMap;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.myself.shortenurl.model.ShortUrlMapping;

@Repository
public class ShortUrlMapperImpl implements ShortUrlMapper {

	private HashMap<String, ShortUrlMapping> urlMapper;
	
	public ShortUrlMapperImpl(){
		urlMapper=new HashMap<>();
	}
	public void saveUrlMapping(String shortUrl, ShortUrlMapping urlMapping) {
		//Ideally the shortUrl generated should be unique. What if it is not?
		//Do we throw exception?
		urlMapper.put(shortUrl,urlMapping);
	}
	
  public Optional<ShortUrlMapping> getOriginalUrl(String shortUrl) {
	  //increment the counter for record how many time has been invoked
	  ShortUrlMapping shortUrlMapping = null;
	    if((shortUrlMapping = urlMapper.get(shortUrl)) != null)  {
	    	shortUrlMapping.incrementInvokedCounter();
	    }
		return Optional.ofNullable(shortUrlMapping);
	}

  public synchronized void removeExpiredUrl(String shortUrl) {
	urlMapper.remove(shortUrl);
  }
}
