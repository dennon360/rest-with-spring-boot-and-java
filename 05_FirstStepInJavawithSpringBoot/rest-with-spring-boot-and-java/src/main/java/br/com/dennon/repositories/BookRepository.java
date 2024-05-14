package br.com.dennon.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.dennon.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>{

}
