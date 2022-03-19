package com.rocky.appstockdata.application.service;

import com.rocky.appstockdata.application.port.in.CompanyNameSearchUseCase;
import com.rocky.appstockdata.application.port.out.CompanyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyNameSearchService implements CompanyNameSearchUseCase {
    final CompanyRepository companyRepository;

    public CompanyNameSearchService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public List<String> getCompanyNames(String keyword) {
        return companyRepository.getCompanyNames(keyword);
    }

    @Override
    public String getRandomCompanyName() {
        return companyRepository.getRandomCompanyName();
    }
}
