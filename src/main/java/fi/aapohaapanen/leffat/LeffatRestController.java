package fi.aapohaapanen.leffat;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LeffatRestController {

    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }
}
