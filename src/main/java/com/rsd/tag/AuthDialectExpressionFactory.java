package com.rsd.tag;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.thymeleaf.context.IExpressionContext;
import org.thymeleaf.expression.IExpressionObjectFactory;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author tony
 * @data 2019-05-31
 * @modifyUser
 * @modifyDate
 */
public class AuthDialectExpressionFactory implements IExpressionObjectFactory {

    protected final Log logger = LogFactory.getLog(this.getClass());

    public static final String AUTH_EXPRESSION_NAME = "auth";

    public static final Set<String> ALL_EXPRESSION_OBJECT_NAMES;


    static {
        final Set<String> allExpressionObjectNames = new LinkedHashSet<String>();
        allExpressionObjectNames.add(AUTH_EXPRESSION_NAME);
        ALL_EXPRESSION_OBJECT_NAMES = Collections.unmodifiableSet(allExpressionObjectNames);
    }

    @Override
    public Set<String> getAllExpressionObjectNames() {
        return ALL_EXPRESSION_OBJECT_NAMES;
    }

    @Override
    public Object buildObject(IExpressionContext context, String expressionObjectName) {
        if(null == expressionObjectName) {
            return null;
        } else if(AUTH_EXPRESSION_NAME.equals(expressionObjectName)){
            return new Auth();
        } else {
            return null;
        }
    }

    @Override
    public boolean isCacheable(String expressionObjectName) {
        if(AUTH_EXPRESSION_NAME.equals(expressionObjectName)) {
            return true;
        }else {
            return false;
        }
    }
}

