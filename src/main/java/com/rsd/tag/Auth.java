package com.rsd.tag;

import com.rsd.domain.RsdRes;
import com.rsd.utils.CommonCacheManager;
import com.rsd.utils.Const;
import com.rsd.utils.HttpSessionManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.Set;

/**
 * @author tony
 * @data 2019-05-31
 * @modifyUser
 * @modifyDate
 */
public class Auth {

    protected final Log logger = LogFactory.getLog(this.getClass());

    public boolean checkAuthCode(String sysId,String code){

        if(StringUtils.isEmpty(sysId) || StringUtils.isEmpty(code)){
            return false;
        }

        try {
            String[] codes = code.split("\\,");

            Map<String, RsdRes> loginUserResCodeMap = HttpSessionManager.get(Const.SESSION_ACCOUNT_RES,Map.class);

            if (loginUserResCodeMap==null){
                return false;
            }

            Set<String> authAllCode = null;

            if(StringUtils.isEmpty(sysId)){
                return false;
            } else  if(sysId.equals(Const.H_SYS_ID+"")){
                authAllCode = CommonCacheManager.get(Const.H_RES_ALL_CODE,Set.class);
            } else if (sysId.equals(Const.E_SYS_ID+"")){
                authAllCode = CommonCacheManager.get(Const.E_RES_ALL_CODE,Set.class);
            }

            for(String m : codes){
                //如果不是有效CODE,则不处验证处理.
                if(!authAllCode.contains(m)){
                    return true;
                }
                if(loginUserResCodeMap.containsKey("r_"+m.trim())){
                    return true;
                }
            }

        } catch (Exception e){
            logger.error("checkAuthCode",e);
        }

        return false;
    }
}
