package com.rsd.mapper;

import com.rsd.domain.BnzInsideMsg;
import com.rsd.domain.BnzInsideMsgModel;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface BnzInsideMsgMapper extends Mapper<BnzInsideMsg> {

    Long getId();

    List<BnzInsideMsgModel> queryMsgList(@Param("model") BnzInsideMsgModel model);

    List<BnzInsideMsgModel> queryMsgByOrgList(@Param("model") BnzInsideMsgModel model);
}