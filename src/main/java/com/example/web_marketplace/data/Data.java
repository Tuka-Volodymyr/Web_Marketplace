package com.example.web_marketplace.data;

import com.example.web_marketplace.entities.Goods;

public interface Data <T>{
     void delete(T data);
     void save(T data);

}
