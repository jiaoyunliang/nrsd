package com.rsd.mapper;

import com.rsd.domain.BnzSysNotice;
import com.rsd.domain.BnzSysNoticeModel;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface BnzSysNoticeMapper extends Mapper<BnzSysNotice> {

    Long getId();

    List<BnzSysNoticeModel> querySysNoticeList(@Param("model") BnzSysNotice model);



    List<BnzSysNoticeModel> queryOrgReadNoticeList(@Param("model") BnzSysNoticeModel model);

    /**
     * 通知未读数
     * @param model
     * @return
     */
    int queryNoticeUnreadNum(@Param("model") BnzSysNoticeModel model);

}