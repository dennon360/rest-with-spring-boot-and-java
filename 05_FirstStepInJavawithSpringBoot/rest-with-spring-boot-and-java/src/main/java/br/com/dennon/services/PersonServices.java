package br.com.dennon.services;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dennon.PersonController;
import br.com.dennon.data.vo.v1.PersonVO;
import br.com.dennon.data.vo.v2.PersonVOV2;
import br.com.dennon.exceptions.RequiredObjectIsNullException;
import br.com.dennon.exceptions.ResourceNotFoundException;
import br.com.dennon.mapper.DozerMapper;
import br.com.dennon.mapper.custom.PersonMapper;
import br.com.dennon.model.Person;
import br.com.dennon.repositories.PersonRepository;

@Service
public class PersonServices {

	private Logger logger = Logger.getLogger(PersonServices.class.getName());
	
	@Autowired
	private PersonRepository repository;
	
	@Autowired
	private PersonMapper mapper;

	public List<PersonVO> findAll() {

		logger.info("Findind all people!");

		List<PersonVO> vo =  DozerMapper.parseListObjects(repository.findAll(), PersonVO.class);
		vo.stream().forEach(p -> p.add(linkTo(methodOn(PersonController.class).findById(p.getKey())).withSelfRel()));;
		
		return vo;
	}
	
	public PersonVO create(PersonVO person) {
		
		if(person == null) throw new RequiredObjectIsNullException();
		
		logger.info("Creating one person!");
		
		var entity = DozerMapper.parseObject(person, Person.class);
		var vo = DozerMapper.parseObject(repository.save(entity), PersonVO.class);
		
		return vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
	}
	
	public PersonVOV2 createV2(PersonVOV2 person) {
		
		logger.info("Creating one person with V2!");
		
		var entity = mapper.convertVoToEntity(person);
		var vo = mapper.convertEntityToVo(repository.save(entity));
		
		return vo;
	}
	
	public PersonVO update(PersonVO person) {
		
		if (person == null) throw new RequiredObjectIsNullException();
		
		logger.info("Updating one person!");
		
		var entity = repository.findById(person.getKey()).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
		
		entity.setFirstName(person.getFirstName());
		entity.setLastName(person.getLastName());
		entity.setAddress(person.getAddress());
		entity.setGender(person.getGender());
		
		var vo = DozerMapper.parseObject(repository.save(entity), PersonVO.class);
		
		return vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
	}
	
	public void delete(Long id) {
		
		logger.info("Deleting one person!");
		
		Person obj = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
		
		repository.delete(obj);;
	}

	public PersonVO findById(Long id) {

		logger.info("Finding one person!");
		
		var entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
		
		PersonVO vo = DozerMapper.parseObject(entity, PersonVO.class);
		vo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
		return vo;
	}
}
