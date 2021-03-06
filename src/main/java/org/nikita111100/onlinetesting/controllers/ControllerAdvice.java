package org.nikita111100.onlinetesting.controllers;

import org.hibernate.StaleStateException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.EntityNotFoundException;
import java.util.NoSuchElementException;


@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(NullPointerException.class)
    public ModelAndView handleException(NullPointerException e) {
        e.printStackTrace();
        ModelAndView model = new ModelAndView("errors/entityException");
        model.addObject("errCode", "NullPointerException  временный перехват для отладки");
        model.addObject("errMsg", " :( ");
        return model;
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ModelAndView handleException(EntityNotFoundException e) {
        e.printStackTrace();
        ModelAndView model = new ModelAndView("errors/entityException");
        model.addObject("errCode", "не удалось найти сущность");
        model.addObject("errMsg",  " :( ");
        return model;
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ModelAndView handleException(EmptyResultDataAccessException e) {
        e.printStackTrace();
        ModelAndView model = new ModelAndView("errors/entityException");
        model.addObject("errCode", "Сущность не найдена, или была уже удалена");
        model.addObject("errMsg", " :( ");
        return model;
    }

    @ExceptionHandler(StaleStateException.class)
    public ModelAndView handleException(StaleStateException e) {
        e.printStackTrace();
        ModelAndView model = new ModelAndView("errors/entityException");
        model.addObject("errCode", "Исключение устаревшего состояния");
        model.addObject("errMsg", " :( ");
        return model;
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ModelAndView handleException(NoSuchElementException e) {
        e.printStackTrace();
        ModelAndView model = new ModelAndView("errors/entityException");
        model.addObject("errCode", "Не удалось найти сущность для сохранения");
        model.addObject("errMsg", " :( ");
        return model;
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(Exception e) {
        e.printStackTrace();
        ModelAndView model = new ModelAndView("errors/entityException");
        model.addObject("errCode", "Произошла ошибка");
        model.addObject("errMsg", " :( ");
        return model;
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ModelAndView handleException(UsernameNotFoundException e) {
        e.printStackTrace();
        ModelAndView model = new ModelAndView("errors/entityException");
        model.addObject("errCode", "Не удалось найти пользователя");
        model.addObject("errMsg", " :( ");
        return model;
    }

    @ExceptionHandler(InternalAuthenticationServiceException.class)
    public ModelAndView handleException(InternalAuthenticationServiceException e) {
        e.printStackTrace();
        ModelAndView model = new ModelAndView("errors/entityException");
        model.addObject("errCode", "UserDetailsService returned null");
        model.addObject("errMsg", " :( ");
        return model;
    }
}
