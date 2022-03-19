package com.rocky.appstockdata.application.service;

import com.rocky.appstockdata.application.port.in.CompanyNameSearchUseCase;
import com.rocky.appstockdata.application.port.out.CompanyRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import java.util.List;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Profile("local")
class CompanyNameSearchServiceTest {
    @Autowired
    private CompanyRepository companyRepository;

    private CompanyNameSearchUseCase companyNameSearchUseCase;

    @BeforeAll
    public void setup(){
        companyNameSearchUseCase = new CompanyNameSearchService(companyRepository);
    }

    @Test
    public void getCompanyNames_Test(){
        List<String> companyName = companyNameSearchUseCase.getCompanyNames("DL건설");

        Assertions.assertThat(companyName.get(0)).isEqualTo("DL건설");
    }

    @Test
    public void getRandomCompanyName_Test(){
        for(int i = 0; i < 10; i++){
            String randomCompanyName = companyNameSearchUseCase.getRandomCompanyName();

            Assertions.assertThat(randomCompanyName).isNotEmpty();
            System.out.println("randomCompanyName = " + randomCompanyName);
        }
    }

}