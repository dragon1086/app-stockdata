package com.rocky.appstockdata.domain.validator;

import com.rocky.appstockdata.domain.dto.BuildUpSourceDTO;
import com.rocky.appstockdata.exceptions.BuildUpSourceException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class BuildUpSourceValidatorTest {

    @Test
    void validate_ok() {
        BuildUpSourceDTO buildUpSourceDTO = BuildUpSourceDTO.builder()
                .companyName("흥아해운")
                .buildupAmount("10000")
                .startDate("2021-10-13")
                .endDate("2021-10-15")
                .build();

        Exception exception = null;
        try{
            BuildUpSourceValidator.validate(buildUpSourceDTO);
        }catch(BuildUpSourceException e){
            exception = e;
        }

        Assertions.assertThat(exception).isNull();
    }

    @Test
    void validate_startDate_isAfter_endDate() {
        BuildUpSourceDTO buildUpSourceDTO = BuildUpSourceDTO.builder()
                .startDate("2021-10-10")
                .endDate("2021-09-30")
                .build();

        Exception exception = null;
        try{
            BuildUpSourceValidator.validate(buildUpSourceDTO);
        }catch(BuildUpSourceException e){
            exception = e;
        }

        Assertions.assertThat(exception).isNotNull();
    }

    @Test
    void validate_buildup_period_Over_5Year() {
        BuildUpSourceDTO buildUpSourceDTO = BuildUpSourceDTO.builder()
                .startDate("2017-10-10")
                .endDate("2022-10-11")
                .build();

        Exception exception = null;
        try{
            BuildUpSourceValidator.validate(buildUpSourceDTO);
        }catch(BuildUpSourceException e){
            exception = e;
        }

        Assertions.assertThat(exception).isNotNull();
    }

    @Test
    void validate_buildup_period_Under_5Year() {
        BuildUpSourceDTO buildUpSourceDTO = BuildUpSourceDTO.builder()
                .startDate("2017-10-10")
                .endDate("2022-10-09")
                .build();

        Exception exception = null;
        try{
            BuildUpSourceValidator.validate(buildUpSourceDTO);
        }catch(BuildUpSourceException e){
            exception = e;
        }

        Assertions.assertThat(exception).isNull();
    }
}