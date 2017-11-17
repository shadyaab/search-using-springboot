package com.search.app.service;

import java.io.File;
import java.util.List;

public interface ISearchService {
	
	List<String> filesInFolder(File directory, String [] searchTerm) throws Exception;
}
