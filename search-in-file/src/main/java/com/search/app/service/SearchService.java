package com.search.app.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.search.app.exception.DirectoryNotFoundException;

/**
 * This Service class search for a file which
 * contains user provided Search Term
 * 
 * @author Shadyaab
 * @version 1.0.0
 * @since 2017-10-17
 */

@Service
@Scope(value=BeanDefinition.SCOPE_PROTOTYPE)
public class SearchService implements ISearchService{
	
	//This is will contain all the files in which all the search term is found
	private List<String> filesContainAllSearchTerm = new ArrayList<String>();
	
	/**
	 * This function is used to read the file and stores it in a string
	 * which in turn call performSearch function for actual search hunting
	 * @throws IOException 
	 * 
	 * @Params fileName This retrieve full path of a file
	 * @Params searchTerm This retrieve array of search term 
	 * @Calls 
	 * @Return boolean This return whether the search perform is success or not.
	 */
	public boolean readTextInFile(String fileName, String [] searchTerm) throws IOException {
		
		BufferedReader br = null;
		boolean isFound = false;
		try {
			br = new BufferedReader(new FileReader(fileName));

			String currentLine;
			String input = "";
			while ((currentLine = br.readLine()) != null) {
				input += " " + currentLine;
			}
			isFound = performRegularSearch(input, searchTerm);
			
		} catch (Exception e) {
			throw e;
			
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				throw e;
			}
		}
		return isFound;
	}
	
	/**
	 * This function is responsible for checking if all the term 
	 * provided is exactly matches in the file or not.
	 * 
	 * @Params input Retrieve all the text from the file
	 * @Params words Retrieve all the word that is to be searched
	 * @Return boolean Return boolean based on successful search or not
	 */
	public boolean performRegularSearch(String input, String [] words) {
		try {
			int count = 0;
			Pattern pattern;
			
			for(int i = 0; i < words.length; i++) {
				pattern = Pattern.compile(".*\\b" + words[i] + "\\b.*", Pattern.CASE_INSENSITIVE);
				Matcher matcher = pattern.matcher(input);
				if(matcher.find()) {
					count++;
				}
			}
			if(count == words.length)
				return true;
		}
		catch(Exception e) {
			throw e;
		}
		return false;
	}
	
	/**
	 * This function is used to get all the file inside a directory 
	 * and its sub directories
	 * @throws DirectoryNotFoundException 
	 * @throws FileNotFoundException 
	 * @throws Exception 
	 * 
	 * @Params directory This is used to get the folderName where search is to be performed
	 * @Params searchTerm This is used to retrieve all the search term
	 * @Params isFirstTime This is used to check if this directory is called first time or not
	 * 	order to initialize List since service is singleton class in spring boot. 	
	 * @Return List This return list of files path that contain all search term
	 */
	public List<String> filesInFolder(File directory, String [] searchTerm) throws Exception  {
		
		
		try {
			if(!directory.isDirectory()) {
				throw new DirectoryNotFoundException();
			} 
		    for (File fileEntry : directory.listFiles()) {
		        if (fileEntry.isDirectory()) {
		        	filesInFolder(fileEntry, searchTerm);
		        } else {
		        	if(readTextInFile(fileEntry.getPath(), searchTerm)) {
		        		filesContainAllSearchTerm.add(fileEntry.getPath());
		        	}
		        }
		    }
		    return filesContainAllSearchTerm;
		} 
		catch(DirectoryNotFoundException e) {
			throw e;
		}
		catch(Exception e) {
			throw e;
		}
	}
	
}
