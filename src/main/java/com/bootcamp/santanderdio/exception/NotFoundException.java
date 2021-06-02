package com.bootcamp.santanderdio.exception;

import com.bootcamp.santanderdio.util.MessageUtils;

public class NotFoundException extends RuntimeException{

    public NotFoundException(){

        super(MessageUtils.STOCK_NOT_EXITS);
    }
}
