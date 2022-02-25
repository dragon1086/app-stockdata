package com.rocky.appstockdata.domain.validator;

import com.rocky.appstockdata.exceptions.NotAllowedException;

import java.util.List;

import static java.util.Arrays.asList;

public class OpenApiValidator {
    private final static List<String> VALID_ID_LIST = asList("woo2015", "jellybunny");
    public static void idValidator(String id) throws NotAllowedException {
        if(!VALID_ID_LIST.contains(id)){
            throw new NotAllowedException("{\"errorMessage\" :  \"허가되지 않은 id입니다.\"}");
        }
    }
}
