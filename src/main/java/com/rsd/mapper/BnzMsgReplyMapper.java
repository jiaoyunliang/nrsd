package com.rsd.mapper;

import com.rsd.domain.BnzMsgReply;
import com.rsd.domain.BnzMsgReplyModel;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface BnzMsgReplyMapper extends Mapper<BnzMsgReply> {


    Long getId();

    List<BnzMsgReplyModel> queryMsgReplyByMsgId(@Param("model") BnzMsgReply model);

    BnzMsgReplyModel queryMsgReply(@Param("model") BnzMsgReply model);

}