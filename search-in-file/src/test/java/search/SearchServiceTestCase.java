package search;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.search.app.exception.DirectoryNotFoundException;
import com.search.app.service.SearchService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes= SearchService.class)
public class SearchServiceTestCase {
	
	private final File directory = new File("D:\\textfile");
	
	@Autowired
	private SearchService service;
	
	@Test
	public void testSingleTermSearch() throws Exception {
		
		service = new SearchService();
		String [] searchTerm = {"order"};
		List<String> list = service.filesInFolder(directory, searchTerm);
		assertEquals("Expected result doesn't match with actual one", 4, list.size());
	}
	
	@Test
	public void testPartialTermSearch() throws Exception {
		
		service = new SearchService();
		String [] searchTerm = {"ord"};
		List<String> list = service.filesInFolder(directory, searchTerm);
		assertEquals("Expected result doesn't match with actual one", 0, list.size());
	}
	
	@Test
	public void testMultipleTermSearch() throws Exception {
		
		service = new SearchService();
		String [] searchTerm = {"order","singleton"};
		List<String> list = service.filesInFolder(directory, searchTerm);
		assertEquals("Expected result doesn't match with actual one", 3, list.size());
	}
	
	@Test(expected = NullPointerException.class)
	public void testNoTermSearch() throws Exception {
		
		service = new SearchService();
		String [] searchTerm = null;
		service.filesInFolder(directory, searchTerm);
	}
	
	@Test(expected = DirectoryNotFoundException.class)
	public void testExceptionSearch() throws Exception {
		
		//This Directory is not present - in order to get Exception
		File directory = new File("D:\\textfile22");
		
		service = new SearchService();
		String [] searchTerm = {"order","singleton"};
		service.filesInFolder(directory, searchTerm);
	}
}
