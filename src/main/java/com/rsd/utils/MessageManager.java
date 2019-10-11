package com.rsd.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Locale;

/**
 * @author tony
 * @data 2019-04-04
 * @modifyUser
 * @modifyDate
 */
@Component
public class MessageManager {

    private final static Logger logger = LogManager.getLogger(MessageManager.class);

    @Resource
    private MessageSource messageSource;


    public String getMessage(String key){
        String msg = "";
        try{
            msg = messageSource.getMessage(key, null, Locale.CHINA);
            return msg;
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return msg;
    }
}
