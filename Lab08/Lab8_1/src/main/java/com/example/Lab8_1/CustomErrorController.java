package com.example.Lab8_1;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public String errorPage(HttpServletRequest httpServletRequest, Model model) {
        String errorMsg = "";
        int httpErrorCode = getErrorCode(httpServletRequest);

        switch (httpErrorCode) {
            case 400:
                errorMsg = "400, Bad request";
                break;
            case 401:
                errorMsg = "401, Unauthorized";
                break;
            case 404:
                errorMsg = "404, Resource not found";
                break;
            case 500:
                errorMsg = "500, Internal server error";
                break;
            default:
                errorMsg = "error";
                break;
        }
        model.addAttribute("errorMsg", errorMsg);
        return "errorPage";
    }

    private int getErrorCode(HttpServletRequest httpServletRequest) {
        return (int) httpServletRequest.getAttribute(RequestDispatcher.ERROR_EXCEPTION);
    }
}
