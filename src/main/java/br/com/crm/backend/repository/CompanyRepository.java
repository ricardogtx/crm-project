package br.com.crm.backend.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.crm.backend.entity.Company;
public interface CompanyRepository extends JpaRepository<Company, Long> {
}	