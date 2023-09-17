package com.example.web_marketplace.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND,reason = "User don`t exist!")
public class UserNotFoundException extends RuntimeException{
}
