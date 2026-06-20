package com.example.AISafePSOFT_26.Aircraft;

import com.example.AISafePSOFT_26.Aircraft.infrastructure.CalculationsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api/hangar/view")
public class HangarViewController {
    private final CalculationsService calculationsService;

    public HangarViewController(CalculationsService calculationsService) {
        this.calculationsService = calculationsService;
    }

    @GetMapping("/utilization")
    public String utilizationPage(Model model) {
        List<HangarController.UtilizationInfo> data =
                calculationsService.getUtilizationInfo();

        System.out.println(data);
        model.addAttribute("utilizationData", data);
        return "utilization";
    }
}