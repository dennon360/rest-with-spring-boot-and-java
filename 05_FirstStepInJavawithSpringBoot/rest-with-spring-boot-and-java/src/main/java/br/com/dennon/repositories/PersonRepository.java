package br.com.dennon.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.dennon.model.Person;

public interface PersonRepository extends JpaRepository<Person, Long>{

}
