package br.com.crm.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.crm.backend.entity.Contact;
public interface ContactRepository extends JpaRepository<Contact, Long> {
}