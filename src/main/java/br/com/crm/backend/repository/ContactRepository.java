package br.com.crm.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.crm.backend.entity.Contact;
public interface ContactRepository extends JpaRepository<Contact, Long> {
	@Query("select c from Contact c " +
			"where lower(c.firstName) like lower(concat('%', :searchTerm, '%')) " +
			"or lower(c.lastName) like lower(concat('%', :searchTerm, '%'))")
			List<Contact> search(@Param("searchTerm") String searchTerm);
}