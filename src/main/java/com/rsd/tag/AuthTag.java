package com.rsd.tag;

import com.rsd.domain.RsdRes;
import com.rsd.utils.CommonCacheManager;
import com.rsd.utils.Const;
import com.rsd.utils.HttpSessionManager;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.Map;
import java.util.Set;

/**
 * @author tony
 * @data 2019-05-30
 * @modifyUser
 * @modifyDate
 */
public class AuthTag extends TagSupport {


    private static final long serialVersionUID = 5814009925044365254L;

    private int sysId;

    public int getSysId() {
        return sysId;
    }

    public void setSysId(int sysId) {
        this.sysId = sysId;
    }

    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public int doEndTag() throws JspException {
        return super.doEndTag();
    }

    @Override
    public int doStartTag() throws JspException {

        String[] codes = this.code.split("\\,");

        Map<String, RsdRes> resMap = HttpSessionManager.get(Const.SESSION_ACCOUNT_RES,Map.class);

        Set<String> authAllCode = null;

        if(this.sysId==2){
            authAllCode = CommonCacheManager.get(Const.H_RES_ALL_CODE,Set.class);
        } else if (this.sysId==3){
            authAllCode = CommonCacheManager.get(Const.E_RES_ALL_CODE,Set.class);
        } else {
            return SKIP_BODY;
        }

        for(String m : codes){
            //如果不是有效CODE,则不处验证处理.
            if(!authAllCode.contains(m)){
                return EVAL_BODY_INCLUDE;
            }
            if(resMap.containsKey("r_"+m.trim())){
                return EVAL_BODY_INCLUDE;
            }
        }

        return SKIP_BODY;
    }
}
