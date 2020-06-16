package br.com.logusinfo.crm.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.logusinfo.crm.backend.entity.Contact;
public interface ContactRepository extends JpaRepository<Contact, Long> {
}