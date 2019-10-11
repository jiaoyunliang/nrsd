package com.rsd.securityConfig;

import com.rsd.domain.RsdRes;
import com.rsd.mapper.BnzResMapper;
import com.rsd.utils.CommonCacheManager;
import com.rsd.utils.Const;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author tony
 * @data 2019-05-30
 * @modifyUser
 * @modifyDate
 */
@Component
public class ApplicationStartup implements ApplicationListener<ContextRefreshedEvent> {

    protected final Log logger = LogFactory.getLog(this.getClass());


    @Autowired
    private BnzResMapper bnzResMapper;


    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        logger.debug("--------all code start---------");
        //医院端
        Set<String> hResAllCode = new HashSet<String>();

        //企业端
        Set<String> eResAllCode = new HashSet<String>();

        RsdRes param = new RsdRes();
        param.setSysId(Const.H_SYS_ID);
        param.setHospitalFlag(1);
        List<RsdRes>  hlist = bnzResMapper.queryResList(param);
        for (RsdRes res: hlist){
            hResAllCode.add(res.getResCode());
        }

        param = new RsdRes();
        param.setSysId(Const.E_SYS_ID);
        param.setOrgFlag(1);
        List<RsdRes>  elist = bnzResMapper.queryResList(param);
        for (RsdRes res: elist){
            eResAllCode.add(res.getResCode());
        }

//        医院端所有授权code
        CommonCacheManager.put(Const.H_RES_ALL_CODE,hResAllCode);
//        企业端所有授权code
        CommonCacheManager.put(Const.E_RES_ALL_CODE,eResAllCode);
        logger.debug("--------all code end---------");

    }
}
