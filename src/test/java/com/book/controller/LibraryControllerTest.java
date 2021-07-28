package com.book.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.book.LibraryController;
import com.book.domain.Book;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.utils.ObjectFactory;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class LibraryControllerTest {
	
	@MockBean
	private LibraryController libraryController;
	
	@Autowired
	private MockMvc mockMvc;
	
	private Book book;
	private static final ObjectMapper om = new ObjectMapper();
	

	@Before
	public void setUp() throws Exception {
		JacksonTester.initFields(this, om);
    	book = ObjectFactory.getBook();
	}

	@After
	public void tearDown() throws Exception {
		
    	book = null;
	}

	@Test
	public void testAddBook() throws Exception {
		String expected = om.writeValueAsString(book);
		MockHttpServletResponse response =
				mockMvc.perform(post("/addBook")
						.contentType(MediaType.APPLICATION_JSON)
						.content(expected)
						).andReturn().getResponse();

		Assertions.assertEquals(HttpStatus.OK.value(),response.getStatus());

	}
	
	@Test
	public void testFindBook() throws Exception {
		MockHttpServletResponse response =
				mockMvc.perform(get("/findBook").param("isbn", "1")).andReturn().getResponse();
		Assertions.assertEquals(HttpStatus.OK.value(),response.getStatus());

	}
	
	
	@Test
	public void testUpdateBook() throws Exception {
		String expected = om.writeValueAsString(book);
		MockHttpServletResponse response =
				mockMvc.perform(put("/updateBook")
						.contentType(MediaType.APPLICATION_JSON)
						.content(expected)
						).andReturn().getResponse();

		Assertions.assertEquals(HttpStatus.OK.value(),response.getStatus());

	}
	
	@Test
	public void testDeleteBook() throws Exception {
		MockHttpServletResponse response =
				mockMvc.perform(delete("/deleteBook").param("isbn", "1")).andReturn().getResponse();
		Assertions.assertEquals(HttpStatus.OK.value(),response.getStatus());

	}
	
	@Test
	public void testSearchBooks() throws Exception {
		String expected = om.writeValueAsString(book);
		MockHttpServletResponse response =
				mockMvc.perform(get("/searchBooks").param("isbn", "1")
						.content(expected)
						).andReturn().getResponse();
		Assertions.assertEquals(HttpStatus.OK.value(),response.getStatus());

	}
	
	@Test
	public void testUploadBooks() throws Exception {
		byte[] expected = om.writeValueAsBytes(book);
		MockMultipartFile file = new MockMultipartFile("file", "file", MediaType.MULTIPART_FORM_DATA_VALUE, expected);
		MockHttpServletResponse response =
				mockMvc.perform(multipart("/uploadBooks").file(file)
						).andReturn().getResponse();

		Assertions.assertEquals(HttpStatus.OK.value(),response.getStatus());

	}
	
	
}
