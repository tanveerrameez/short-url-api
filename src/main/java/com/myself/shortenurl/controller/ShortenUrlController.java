package com.myself.shortenurl.controller;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.rmi.ServerException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.myself.shortenurl.exception.NoUrlMappingAvailableException;
import com.myself.shortenurl.model.ErrorResponse;
import com.myself.shortenurl.service.ShortUrlService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Controller
@RequestMapping("/shortenurl")
@Api(value = "/shortenurl", tags = {"Generates short Url"})
public class ShortenUrlController {
	public static final String URL="url";
	
	@Autowired
	private ShortUrlService shortServiceimpl;
	
	@ApiOperation(value="Generates a shortUrl", response=String.class)
	@ApiResponses(value= {@ApiResponse(code=201,message="Created"), @ApiResponse(code=422, message="Unprocessable Entity"), @ApiResponse(code=400, message="Bad Request")})
	@RequestMapping(method = { RequestMethod.GET}, produces= {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	@ResponseBody
	/**
	 * example method=GET endpoint with parameter: http://localhost:8080/shortenurl?url=http://www.google.com  
	 * @param originalUrl
	 * @param request
	 * @return
	 * @throws ServerException
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws UnknownHostException
	 */
	public ResponseEntity<String> generateShortUrl(@RequestParam(value="url", required=true) String originalUrl, HttpServletRequest request) 
			throws ServerException, MalformedURLException, IOException, UnknownHostException {
		HttpStatus status=null;
    	String hostUrl=request.getRequestURL().toString();
    	
    	//Check if URL is valid
		URL url = new URL(originalUrl);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		if (conn.getResponseCode()==HttpStatus.OK.value()) {
			
		String shortUrl = hostUrl+"/"+shortServiceimpl.generateShortUrl(originalUrl);
		status=HttpStatus.CREATED;
		return  new ResponseEntity<>(shortUrl, status);
		}
		else throw new MalformedURLException(originalUrl);
		
	}
	
	/**
	 * example method=POST endpoint: http://localhost:8080/shortenurl   POST body JSON content: {"url":"http://www.google.com"} 
	 * @param originalUrl
	 * @param request
	 * @return
	 * @throws ServerException
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws UnknownHostException
	 */
	@RequestMapping(method=RequestMethod.POST, consumes= MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<String> generateShortUrlPost(@RequestBody String originalUrl, HttpServletRequest request) 
			throws ServerException, MalformedURLException, IOException, UnknownHostException {
		HttpStatus status=null;
    	String hostUrl=request.getRequestURL().toString();
    	
    	//Check if URL is valid
    	JsonParser parser = new JsonParser();
    	JsonElement jElement  =parser.parse(originalUrl);
    	JsonObject jObject= jElement.getAsJsonObject(); 
    	originalUrl=jObject.get(URL).getAsString();
		URL url = new URL(originalUrl);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		if (conn.getResponseCode()==HttpStatus.OK.value()) {
			
		String shortUrl = hostUrl+"/"+shortServiceimpl.generateShortUrl(originalUrl);
		status=HttpStatus.CREATED;
		return  new ResponseEntity<>(shortUrl, status);
		}
		else throw new MalformedURLException(originalUrl);
		
	}
	
	@ApiOperation(value="Generates a shortUrl", response=String.class)
	@ApiResponses(value= {@ApiResponse(code=201,message="Created"), @ApiResponse(code=422, message="Unprocessable Entity"), @ApiResponse(code=400, message="Bad Request")})
	@RequestMapping(value="/{shortUrl}", method = { RequestMethod.GET},produces= MediaType.TEXT_HTML_VALUE)
	public String getOriginalUrl( @PathVariable("shortUrl") String shortlUrl) throws NoUrlMappingAvailableException   {
	    
			String originalUrl = shortServiceimpl.getOriginalUrl(shortlUrl);
		    return "redirect:"+originalUrl;
	}
	
	@ExceptionHandler({ServerException.class})
	public ResponseEntity<ErrorResponse> generalException(Exception e){
	
		ErrorResponse response=new ErrorResponse();
		response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
		response.setMessage(String.format("%s:%s",HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),e));
		
		return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler({UnknownHostException.class})
	public ResponseEntity<String> handleUnknownHostException(UnknownHostException uhe){
	
		return new ResponseEntity<>("Url "+uhe.getMessage()+ " is unknown", HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler({MalformedURLException.class})
	public ResponseEntity<String> handleMalformedUrlException(MalformedURLException mfue){
	
		return new ResponseEntity<>(mfue.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler({NoUrlMappingAvailableException.class})
	public ResponseEntity<String> handleNoUrlMappingAvailableExceptio(NoUrlMappingAvailableException numae){
	
		return new ResponseEntity<>(String.format("No mapping available for : %s",
				numae.getMessage()), HttpStatus.UNPROCESSABLE_ENTITY);
	}
	
}
