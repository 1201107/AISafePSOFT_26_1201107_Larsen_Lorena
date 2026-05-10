package com.example.AISafePSOFT_26;

import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("")
public class LandingController {

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome to the AISafePSOFT_26 made by: 1201107";
    }


}