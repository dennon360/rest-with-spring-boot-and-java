package br.com.dennon.services;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
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

	public List<BookVO> findAll() {

		logger.info("Findind all Book!");

		var vo = DozerMapper.parseListObjects(repository.findAll(), BookVO.class);
		vo.stream().forEach(p -> p.add(linkTo(methodOn(BookController.class).findAll()).withSelfRel()));;

		return vo;
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
}
