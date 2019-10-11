package com.rsd.tag;

import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.dialect.IExpressionObjectDialect;
import org.thymeleaf.expression.IExpressionObjectFactory;
import org.thymeleaf.processor.IProcessor;
import org.thymeleaf.standard.StandardDialect;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author tony
 * @data 2019-05-30
 * @modifyUser
 * @modifyDate
 */
public class BnzDialect extends AbstractProcessorDialect implements IExpressionObjectDialect {

    private static final String DIALECT_NAME = "BnzDialect";

    private static final String PREFIX = "bnz";

    private IExpressionObjectFactory expressionObjectFactory = null;

    public BnzDialect() {
        super(DIALECT_NAME, PREFIX, StandardDialect.PROCESSOR_PRECEDENCE);
    }


    @Override
    public IExpressionObjectFactory getExpressionObjectFactory() {
        if (this.expressionObjectFactory == null) {
            this.expressionObjectFactory = new AuthDialectExpressionFactory();
        }
        return this.expressionObjectFactory;
    }


    @Override
    public Set<IProcessor> getProcessors(final String dialectPrefix) {
        return createStandardProcessorsSet(dialectPrefix);
    }


    private Set<IProcessor> createStandardProcessorsSet(String dialectPrefix) {
        LinkedHashSet<IProcessor> processors = new LinkedHashSet<IProcessor>();
//        processors.add(new SansitiveEncryptProcessor(dialectPrefix));
        return processors;
    }
}

