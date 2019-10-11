package com.rsd.service;

import com.github.pagehelper.Page;
import com.rsd.domain.BnzNoticeRead;
import com.rsd.domain.BnzSysNotice;
import com.rsd.domain.BnzSysNoticeModel;

import java.util.List;

/**
 * @author tony
 * @data 2019-05-06
 * @modifyUser
 * @modifyDate
 */
public interface BnzSysNoticeService {

    int saveNotice(BnzSysNotice notice) throws Exception;

    int updateNotice(BnzSysNotice notice) throws Exception;

    BnzSysNotice queryNoticeById(BnzNoticeRead model) throws Exception;

    Page<List> querySysNoticePage(BnzSysNoticeModel model) throws Exception;

    Page<List> queryOrgReadNoticePage(BnzSysNoticeModel model) throws Exception;

    int queryNoticeUnreadNum(BnzSysNoticeModel model) throws Exception;

}
