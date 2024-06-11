package cl.bree2003.AlkeWalletM6.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFound(HttpServletRequest request, Model model){
        model.addAttribute("url", request.getRequestURL());
        model. addAttribute("errorCode", "404");
        model.addAttribute("errorMessage", "Sorry, the page you are looking for does not exist.");
        return "error";
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleGeneralError(HttpServletRequest request, Exception ex, Model model){
        model.addAttribute("url", request.getRequestURL());
        model.addAttribute("errorCode", "500");
        model.addAttribute("errorMessage", "An unexpected error ocurred. Please try again later.");
        model.addAttribute("exception", ex);
        return "error";
    }
}
