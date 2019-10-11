package com.rsd.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.rsd.domain.BnzBidDetail;
import com.rsd.service.BnzBidDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExcelListener extends AnalysisEventListener {

    private static final Logger logger = LoggerFactory.getLogger(ExcelListener.class);

    private List<BnzBidDetail> datas = new ArrayList<>(1100);

    private BnzBidDetailService bnzBidDetailService;

    private BnzBidDetail bnzBidDetail;

    public BnzBidDetail getBnzBidDetail() {
        return bnzBidDetail;
    }

    public void setBnzBidDetail(BnzBidDetail bnzBidDetail) {
        this.bnzBidDetail = bnzBidDetail;
    }

    public BnzBidDetailService getBnzBidDetailService() {
        return bnzBidDetailService;
    }

    public void setBnzBidDetailService(BnzBidDetailService bnzBidDetailService) {
        this.bnzBidDetailService = bnzBidDetailService;
    }

    @Override
    public void invoke(Object obj, AnalysisContext analysisContext) {
        System.out.println(analysisContext.getCurrentSheet());

        BnzBidDetail bnzBidDetail = new BnzBidDetail();
        bnzBidDetail.setProjectId(this.bnzBidDetail.getProjectId());
        bnzBidDetail.setCreateUser(this.bnzBidDetail.getCreateUser());
        bnzBidDetail.setCreateTime(new Date());
        bnzBidDetail.setIsDel(0);

        List row = (List)obj;

        bnzBidDetail.setBidNumber(row.get(1).toString());
        bnzBidDetail.setBidOrgName(row.get(2).toString());
        bnzBidDetail.setBidUserName(row.get(3).toString());

        bnzBidDetail.setZtItemId(row.get(4).toString());
        bnzBidDetail.setMaintainOrgName(row.get(5).toString());
        bnzBidDetail.setProduceOrgName(row.get(6).toString());
        bnzBidDetail.setZtItemName(row.get(7).toString());


        bnzBidDetail.setProjectCategoryId(row.get(8).toString());
        bnzBidDetail.setProjectCategoryName(row.get(9).toString());
        bnzBidDetail.setProjectCatalogueId(row.get(10).toString());
        bnzBidDetail.setProjectCatalogueName(row.get(11).toString());

        bnzBidDetail.setZtItemSpec(row.get(12).toString());
        bnzBidDetail.setZtItemModel(row.get(13).toString());
        bnzBidDetail.setZtRegisterId(row.get(14).toString());
        bnzBidDetail.setZtRegisterSpec(row.get(15).toString());
        bnzBidDetail.setZtRegisterModel(row.get(16).toString());

        bnzBidDetail.setZtItemBrand(row.get(17).toString());
        bnzBidDetail.setZtItemMinNumberUnit(row.get(18).toString());
        bnzBidDetail.setZtItemPackSpec(row.get(19).toString());
        bnzBidDetail.setZtItemPackMaterial(row.get(20).toString());
        bnzBidDetail.setZtItemPerformace(row.get(21).toString());
        bnzBidDetail.setZtItemScope(row.get(22).toString());
        bnzBidDetail.setZtDataCheckState(row.get(23).toString());
        bnzBidDetail.setZtCatalogueCheckState(row.get(24).toString());
        bnzBidDetail.setItemId(row.get(25).toString());
        bnzBidDetail.setItemName(row.get(26).toString());
        bnzBidDetail.setItemSpec(row.get(27).toString());
        bnzBidDetail.setItemModel(row.get(28).toString());
        bnzBidDetail.setItemRegisterId(row.get(29).toString());
        bnzBidDetail.setItemRegisterSpec(row.get(30).toString());
        bnzBidDetail.setItemRegisterModel(row.get(31).toString());
        bnzBidDetail.setItemRegisterValidDate(row.get(32).toString());
        bnzBidDetail.setItemBrand(row.get(33).toString());
        bnzBidDetail.setItemMinNumberUnit(row.get(34).toString());
        bnzBidDetail.setItemMinPackUnit(row.get(35).toString());
        bnzBidDetail.setItemConversionRatio(row.get(36).toString());
        bnzBidDetail.setItemPackSpec(row.get(37).toString());
        bnzBidDetail.setItemPackMaterial(row.get(38).toString());
        bnzBidDetail.setItemPerformance(row.get(39).toString());
        bnzBidDetail.setItemScope(row.get(40).toString());
        bnzBidDetail.setComposeNumber(row.get(41).toString());
        bnzBidDetail.setItemCheckState(row.get(42).toString());
        bnzBidDetail.setItemCheckPerson(row.get(43).toString());
        bnzBidDetail.setItemBasePrice(row.get(44).toString());
        bnzBidDetail.setItemQuote(row.get(45).toString());

        datas.add(bnzBidDetail);
        if(datas.size()>=1000){
            doSomething();
            datas = new ArrayList<>();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        if(datas.size()<1000){
            doSomething();
        }
        datas.clear();
    }
    public void doSomething(){
        try{
            this.bnzBidDetailService.saveBidDetail(this.datas);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }

    }
}
