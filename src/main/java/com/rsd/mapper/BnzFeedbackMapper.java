package com.rsd.mapper;

import com.rsd.domain.BnzFeedback;
import com.rsd.domain.BnzFeedbackModel;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface BnzFeedbackMapper extends Mapper<BnzFeedback> {

    Long getId();

    List<BnzFeedbackModel> queryFeedbackList(@Param("model") BnzFeedbackModel model);
}