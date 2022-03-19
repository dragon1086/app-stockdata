package com.rocky.appstockdata.application.port.in;

import java.util.List;

public interface CompanyNameSearchUseCase {
    List<String> getCompanyNames(String keyword);

    String getRandomCompanyName();
}
