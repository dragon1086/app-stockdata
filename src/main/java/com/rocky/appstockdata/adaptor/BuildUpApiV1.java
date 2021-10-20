package com.rocky.appstockdata.adaptor;

import com.rocky.appstockdata.domain.BuildUp;
import com.rocky.appstockdata.domain.BuildUpSourceDTO;
import com.rocky.appstockdata.domain.Post;
import com.rocky.appstockdata.domain.validator.BuildUpSourceValidator;
import com.rocky.appstockdata.exceptions.BuildUpSourceException;
import com.rocky.appstockdata.port.in.BuildUpCalculatePort;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class BuildUpApiV1 {
    private BuildUpCalculatePort buildUpCalculateService;

    public BuildUpApiV1(BuildUpCalculatePort buildUpCalculateService) {
        this.buildUpCalculateService = buildUpCalculateService;
    }

    @GetMapping("/")
    public String index(Model model){
        return "index";
    }

    @GetMapping("/test")
    public String test(Model model){
        Post post1 = new Post(1, "lee", "book1");
        Post post2 = new Post(2, "choi", "book2");
        Post post3 = new Post(3, "kim", "book3");
        List<Post> list = new ArrayList<>();
        list.add(post1);
        list.add(post2);
        list.add(post3);
        model.addAttribute("list", list);

        return "test";
    }

    @GetMapping(value = "buildup-calculate")
    public String buildUpResultController(ModelMap modelMap,
                                          @RequestParam(value = "companyName", required = false) String companyName,
                                          @RequestParam(value = "buildupAmount", required = false) String buildupAmount,
                                          @RequestParam(value = "startDate", required = false) String startDate,
                                          @RequestParam(value = "endDate", required = false) String endDate){


        try{
            BuildUpSourceDTO buildUpSourceDTO = BuildUpSourceDTO.builder()
                    .companyName(companyName)
                    .buildupAmount(Long.parseLong(buildupAmount))
                    .startDate(startDate)
                    .endDate(endDate)
                    .build();

            BuildUpSourceValidator.validate(buildUpSourceDTO);

            BuildUp buildUp = buildUpCalculateService.calculateBuildUp(buildUpSourceDTO);

            modelMap.put("earningRate", buildUp.getEarningRate());
            modelMap.put("earningAmount", buildUp.getEarningAmount());
            modelMap.put("totalAmount", buildUp.getTotalAmount());

        }catch (BuildUpSourceException e){
            modelMap.put("isError", "true");
            modelMap.put("errorMessage", e.getMessage());
        }catch (NumberFormatException e){
            modelMap.put("isError", "true");
            modelMap.put("errorMessage", "빌드업 금액 입력 시 숫자로 입력하셔야 합니다.");
        }finally {
            return "buildUpResult";
        }

    }
}
