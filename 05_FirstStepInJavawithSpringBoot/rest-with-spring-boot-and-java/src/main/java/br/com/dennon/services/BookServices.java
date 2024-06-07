package br.com.dennon.services;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import br.com.dennon.BookController;
import br.com.dennon.data.vo.v1.BookVO;
import br.com.dennon.mapper.DozerMapper;
import br.com.dennon.model.Book;
import br.com.dennon.repositories.BookRepository;

@Service
public class BookServices {

	private Logger logger = Logger.getLogger(BookServices.class.getName());

	@Autowired
	private BookRepository repository;
	
	@Autowired
	private PagedResourcesAssembler<BookVO> assembler;

	public PagedModel<EntityModel<BookVO>> findAll(Pageable pageable) {

		logger.info("Findind all Book!");
		
		var bookPage = repository.findAll(pageable);
		
		var bookVosPage = bookPage.map(p -> DozerMapper.parseObject(p, BookVO.class));
		bookVosPage.map(p -> p.add(linkTo(methodOn(BookController.class).findById(p.getKey())).withSelfRel()));
		
		Link link = linkTo(methodOn(BookController.class).findAll(pageable.getPageNumber(), pageable.getPageSize(), "asc")).withSelfRel();
		
		return assembler.toModel(bookVosPage, link);
	}

	public BookVO findById(Long id) {

		logger.info("Findind one Book!");

		var vo = DozerMapper.parseObject(repository.findById(id).get(), BookVO.class);
		vo.add(linkTo(methodOn(BookController.class).findById(id)).withSelfRel());
		
		return vo;
	}
	
	public BookVO create(BookVO book) {
		
		logger.info("Creating one book!");
		
		var entity = DozerMapper.parseObject(book, Book.class);
		var vo = DozerMapper.parseObject(repository.save(entity), BookVO.class);
		vo.add(linkTo(methodOn(BookController.class).create(book)).withSelfRel());
		
		return vo;
	}
	
	public BookVO update(BookVO book) {
		
		logger.info("Updating one book!");
		
		var entity = DozerMapper.parseObject(repository.findById(book.getKey()).get(), Book.class);
		
		entity.setAuthor(book.getAuthor());
		entity.setTitle(book.getTitle());
		entity.setLaunchDate(book.getLaunchDate());
		entity.setPrice(book.getPrice());
		
		var vo = DozerMapper.parseObject(repository.save(entity), BookVO.class);
		vo.add(linkTo(methodOn(BookController.class).update(book)).withSelfRel());
		
		return vo;
	}
	
	public void delete(Long id) {
		
		logger.info("Deleting one book!");
		
		var entity = repository.findById(id).get();
		
		repository.delete(entity);
	}
	
	public PagedModel<EntityModel<BookVO>> findBooksByNames(String title, Pageable pageable) {

		logger.info("Findind all books!");
		
		var booksPage = repository.findBooksByName(title, pageable);

		var booksVosPage = booksPage.map(p -> DozerMapper.parseObject(p, BookVO.class));
		booksVosPage.map(p -> p.add(linkTo(methodOn(BookController.class).findById(p.getKey())).withSelfRel())); 
		
		Link link = linkTo(methodOn(BookController.class)
				.findAll(pageable.getPageNumber(), pageable.getPageSize(), "asc")).withSelfRel();
		
		return assembler.toModel(booksVosPage, link);
	}
}
