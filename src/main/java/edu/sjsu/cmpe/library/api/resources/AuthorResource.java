/**
 * 
 */
package edu.sjsu.cmpe.library.api.resources;



import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.yammer.dropwizard.jersey.params.LongParam;
import com.yammer.metrics.annotation.Timed;

import edu.sjsu.cmpe.library.domain.Author;
import edu.sjsu.cmpe.library.domain.Book;
import edu.sjsu.cmpe.library.dto.AuthorDto;
import edu.sjsu.cmpe.library.dto.AuthorsDto;
import edu.sjsu.cmpe.library.dto.LinkDto;
import edu.sjsu.cmpe.library.repository.BookRepositoryInterface;

//@Path("/v1/books")
@Path("/v1/books/{isbn}/authors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

public class AuthorResource {
	

    /** bookRepository instance */
    private final BookRepositoryInterface bookRepository;

    /**
     * BookResource constructor
     * 
     * @param bookRepository
     *            a BookRepository instance
     */
    public AuthorResource(BookRepositoryInterface bookRepository) {
	this.bookRepository = bookRepository;
    }
  

    @GET
    @Path("/{id}/")
    @Timed(name = "view-review")
    public Response getAuthoByIsbn(@PathParam("isbn") LongParam isbn,@PathParam("id") int id) {
	
    	Book book = bookRepository.getBookByISBN(isbn.get());
    	Author authortemp = bookRepository.getAuthorByID(isbn.get(),id);
	
	AuthorDto bookResponse = new AuthorDto(authortemp);
	bookResponse.addLink(new LinkDto("view-book", "/books/" + book.getIsbn() + "/review/" ,
		"GET"));
	


	return Response.status(201).entity(bookResponse).build();
    }
    
    @GET
    @Timed(name = "view-book")
    public Response getAuthorByIsbn(@PathParam("isbn") LongParam isbn) {
    	
    	Book book= bookRepository.getBookByISBN(isbn.get());
    	List<Author> tempauthor = book.getAuthors();
    	AuthorsDto bookResponse = new AuthorsDto(tempauthor);
    	return Response.status(201).entity(bookResponse).build();
    

}}
