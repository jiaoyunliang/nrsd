package com.rsd.service;

import com.github.pagehelper.Page;
import com.rsd.domain.BnzFeedback;
import com.rsd.domain.BnzFeedbackModel;

import java.util.List;

/**
 * @author tony
 * @data 2019-05-06
 * @modifyUser
 * @modifyDate
 */
public interface BnzFeedbackService {

    Page<List> queryFeedbackPage(BnzFeedbackModel model) throws Exception;

    int updateFeedback(BnzFeedback model) throws Exception;


    int saveFeedback(BnzFeedback model) throws Exception;
}
