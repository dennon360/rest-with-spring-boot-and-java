package br.com.dennon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.dennon.data.vo.v1.BookVO;
import br.com.dennon.services.BookServices;
import br.com.dennon.util.MediaType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/book/v1")
@Tag(name = "Book", description = "Endpoints for Book")
public class BookController {

	@Autowired
	private BookServices service;
	
	@GetMapping(value = "/findall", produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML })
	@Operation(summary = "Finds all books", description = "Finds all books", 
	tags = {"Book"}, 
	responses = {
			@ApiResponse(description = "Sucess", responseCode = "200",
					content = {
							@Content(schema = @Schema(implementation = BookVO.class))
						}),
			@ApiResponse(description = "Bad request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
	})
	public ResponseEntity<PagedModel<EntityModel<BookVO>>> findAll(
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "size", defaultValue = "12") Integer size,
			@RequestParam(value = "direction", defaultValue = "asc") String direction){
		
		var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
		Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "title"));
		
		return ResponseEntity.ok().body(service.findAll(pageable));
	}
	
	@GetMapping(value = "/findbyid/{id}", produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML })
	@Operation(summary = "Finds a books", description = "Finds a books", 
	tags = {"Book"}, 
	responses = {
			@ApiResponse(description = "Sucess", responseCode = "200",
					content = {
							@Content(
									mediaType = "application/json",
									array =  @ArraySchema(schema = @Schema(implementation = BookVO.class))
									)
						}),
			@ApiResponse(description = "Bad request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
	})
	public ResponseEntity<BookVO> findById(@PathVariable Long id){
		
		return ResponseEntity.ok().body(service.findById(id));
	}
	
	@PostMapping(value = "/create", 
			consumes = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML },
			produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML })
	@Operation(summary = "Creates a book", description = "Creates a book", 
	tags = {"Book"}, 
	responses = {
			@ApiResponse(description = "Sucess", responseCode = "200",
					content = {
							@Content(schema = @Schema(implementation = BookVO.class))
						}),
			@ApiResponse(description = "Bad request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
	})
	public ResponseEntity<BookVO> create(@RequestBody BookVO book){
		
		return ResponseEntity.ok().body(service.create(book));
	}
	
	@PostMapping(value = "/update",
			consumes = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML },
			produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML })
	@Operation(summary = "Update a book", description = "Creates a book", 
	tags = {"Book"}, 
	responses = {
			@ApiResponse(description = "Sucess", responseCode = "200",
					content = {
							@Content(schema = @Schema(implementation = BookVO.class))
						}),
			@ApiResponse(description = "Bad request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
	})
	public ResponseEntity<BookVO> update(@RequestBody BookVO book){
		
		return ResponseEntity.ok().body(service.update(book));
	}
	
	@DeleteMapping(value = "/delete/{id}")
	@Operation(summary = "Deletes a book", description = "Deletes a book", 
	tags = {"Book"}, 
	responses = {
			@ApiResponse(description = "Sucess", responseCode = "200",
					content = {
							@Content(schema = @Schema(implementation = BookVO.class))
						}),
			@ApiResponse(description = "Bad request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
	})
	public ResponseEntity<Void> delete(@PathVariable Long id){
		
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping(value = "/findBookByTitle/{title}"
			, produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,  MediaType.APPLICATION_YML})
	@Operation(summary = "Finds books by Name", description = "Finds books by Name",
	tags = {"Book"},
	responses = {
			@ApiResponse(description = "Success", responseCode = "200", 
					content = {
							@Content(
								mediaType = "application/json",
								array = @ArraySchema(schema = @Schema(implementation = BookVO.class))
							)
					}),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
	})
	public ResponseEntity<PagedModel<EntityModel<BookVO>>> findBooksByNames(
			@PathVariable(value = "title") String title,
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "size", defaultValue = "12") Integer size,
			@RequestParam(value = "direction", defaultValue = "asc") String direction
			){
		
		var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
		
		Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "title"));
		return ResponseEntity.ok().body(service.findBooksByNames(title, pageable));
	}
}
