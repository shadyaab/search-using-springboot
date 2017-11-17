package com.search.app.controller;

import java.io.File;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.search.app.exception.NoResultFoundException;
import com.search.app.service.ISearchService;

/**
 * This is RestController which serves the request of client 
 * using http protocol
 * 
 * @author Shadyaab
 * @version 1.0.0
 * @since 2017-10-17
 */

@PropertySource("classpath:application.properties")
@RestController
public class SearchRestController {
	
	private final File directory = new File("D:\\textfile");
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
    private Environment env;
	
	/**
	 * Reference to Spring ObjectFactory to get the instance of 
	 * prototype bean (implementation class of ISearchService) on every request
	 * 
	 */
	@Autowired
	private ObjectFactory<ISearchService> factory;
	
	/**
	 * This function is called in order to accept invalid request and provide with some 
	 * proper information instead of showing some Exception/page not found error
	 * 
	 * @throws NoHandlerFoundException 
	 * 
	 */
	@RequestMapping(value = "/**")
	public void performSearch() throws NoHandlerFoundException {
		throw new NoHandlerFoundException("", "", null);
	}
	
	/**
	 * This function is called to get all the fileName which contain 
	 * the search term
	 * @throws Exception 
	 * @Params value This matches with the URL
	 * @Params method This function is call if request type is GET
	 */
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public List<String> performSearch(@RequestParam(value="terms") String term) throws Exception {
		
		List<String> outputData = null;
		String [] searchTerm = null;
		try {
			if(term.length() == 0) {
				throw new IllegalArgumentException();
			}
			
			searchTerm = term.split(",");
			
			//Getting object of service class using ObjectFactory.
			ISearchService service = factory.getObject();
			
			outputData = service.filesInFolder(directory, searchTerm);
			
			if(outputData.size() == 0) {
				throw new NoResultFoundException();
			}
		}
		catch(Exception e) {
			log.error(env.getProperty("error.GenericMessage") + e.getMessage());
			throw e;
		}
		return outputData;
	}
	
	
	
}

