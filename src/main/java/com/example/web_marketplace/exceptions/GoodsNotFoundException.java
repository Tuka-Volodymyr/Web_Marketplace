package com.example.web_marketplace.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND,reason = "Goods don`t exist!")
public class GoodsNotFoundException extends RuntimeException{

}
