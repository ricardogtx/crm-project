package br.com.logusinfo.crm.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.logusinfo.crm.backend.entity.Company;
import br.com.logusinfo.crm.backend.repository.CompanyRepository;

@Service
public class CompanyService {
	private CompanyRepository companyRepository;

	public CompanyService(CompanyRepository companyRepository) {
		this.companyRepository = companyRepository;
	}

	public List<Company> findAll() {
		return companyRepository.findAll();
	}
}