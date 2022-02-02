package com.rocky.appstockdata.config;

import com.rocky.appstockdata.application.port.in.BuildUpService;
import com.rocky.appstockdata.application.service.BuildUpType;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class BuildUpServiceFactory {
    private final Map<BuildUpType, BuildUpService> buildUpServices = new HashMap<>();

    public BuildUpServiceFactory(List<BuildUpService> buildUpServices){
        if(CollectionUtils.isEmpty(buildUpServices)){
            throw new IllegalArgumentException("buildUpService 없음");
        }

        for(BuildUpService buildUpService : buildUpServices){
            this.buildUpServices.put(buildUpService.getBuildUpType(), buildUpService);
        }
    }

    public BuildUpService getService(BuildUpType buildUpType){
        return buildUpServices.get(buildUpType);
    }
}
