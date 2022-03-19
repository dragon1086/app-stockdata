package com.rocky.appstockdata.application.port.out;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository {
    List<String> getCompanyNames(String keyword);

    String getRandomCompanyName();
}
