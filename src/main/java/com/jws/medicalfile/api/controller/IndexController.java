package com.jws.medicalfile.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/")
public class IndexController extends BaseController {
    @GetMapping(value = "index")
    public void index(HttpServletResponse response) throws IOException {
        switch (getLoggedUser().getRole().getName()) {
            case "Patient":
                response.sendRedirect("/patient/profile");
                break;
            case "Doctor":
                response.sendRedirect("/doctor/pendingvisits");
                break;
            case "Admin":
                response.sendRedirect("/admin/visitlist");
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + getLoggedUser().getRole().getName());
        }
    }
}
