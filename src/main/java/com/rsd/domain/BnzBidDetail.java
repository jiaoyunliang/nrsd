package com.rsd.domain;

import com.alibaba.excel.annotation.ExcelProperty;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "BNZ_BID_DETAIL")
public class BnzBidDetail{
    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "PROJECT_ID")
    private Long projectId;

    @ExcelProperty(value = "",index = 0)
    @Column(name = "BID_NUMBER")
    private String bidNumber;

    @ExcelProperty(value = "",index = 0)
    @Column(name = "BID_ORG_NAME")
    private String bidOrgName;

    @ExcelProperty(value = "",index = 0)
    @Column(name = "BID_USER_NAME")
    private String bidUserName;

    @ExcelProperty(value = "",index = 0)
    @Column(name = "ZT_ITEM_ID")
    private String ztItemId;

    @ExcelProperty(value = "",index = 0)
    @Column(name = "MAINTAIN_ORG_NAME")
    private String maintainOrgName;

    @ExcelProperty(value = "",index = 0)
    @Column(name = "PRODUCE_ORG_NAME")
    private String produceOrgName;

    @ExcelProperty(value = "",index = 0)
    @Column(name = "ZT_ITEM_NAME")
    private String ztItemName;

    @ExcelProperty(value = "",index = 0)
    @Column(name = "PROJECT_CATEGORY_ID")
    private String projectCategoryId;

    @ExcelProperty(value = "",index = 0)
    @Column(name = "PROJECT_CATEGORY_NAME")
    private String projectCategoryName;

    @ExcelProperty(value = "",index = 0)
    @Column(name = "PROJECT_CATALOGUE_ID")
    private String projectCatalogueId;

    @ExcelProperty(value = "",index = 0)
    @Column(name = "PROJECT_CATALOGUE_NAME")
    private String projectCatalogueName;

    @ExcelProperty(value = "",index = 0)
    @Column(name = "ZT_ITEM_SPEC")
    private String ztItemSpec;

    @ExcelProperty(value = "",index = 0)
    @Column(name = "ZT_ITEM_MODEL")
    private String ztItemModel;

    @ExcelProperty(value = "",index = 0)
    @Column(name = "ZT_REGISTER_ID")
    private String ztRegisterId;

    @ExcelProperty(value = "",index = 0)
    @Column(name = "ZT_REGISTER_SPEC")
    private String ztRegisterSpec;

    @ExcelProperty(value = "",index = 0)
    @Column(name = "ZT_REGISTER_MODEL")
    private String ztRegisterModel;

    @ExcelProperty(value = "",index = 0)
    @Column(name = "ZT_ITEM_BRAND")
    private String ztItemBrand;

    @ExcelProperty(value = "",index = 0)
    @Column(name = "ZT_ITEM_MIN_NUMBER_UNIT")
    private String ztItemMinNumberUnit;

    @ExcelProperty(value = "",index = 0)
    @Column(name = "ZT_ITEM_PACK_SPEC")
    private String ztItemPackSpec;

    @ExcelProperty(value = "",index = 0)
    @Column(name = "ZT_ITEM_PACK_MATERIAL")
    private String ztItemPackMaterial;

    @ExcelProperty(value = "",index = 0)
    @Column(name = "ZT_ITEM_PERFORMACE")
    private String ztItemPerformace;

    @ExcelProperty(value = "",index = 0)
    @Column(name = "ZT_ITEM_SCOPE")
    private String ztItemScope;

    @ExcelProperty(value = "",index = 0)
    @Column(name = "ZT_DATA_CHECK_STATE")
    private String ztDataCheckState;

    @ExcelProperty(value = "",index = 0)
    @Column(name = "ZT_CATALOGUE_CHECK_STATE")
    private String ztCatalogueCheckState;

    @ExcelProperty(value = "",index = 0)
    @Column(name = "ITEM_ID")
    private String itemId;

    @ExcelProperty(value = "",index = 0)
    @Column(name = "ITEM_NAME")
    private String itemName;

    @ExcelProperty(value = "",index = 0)
    @Column(name = "ITEM_SPEC")
    private String itemSpec;

    @ExcelProperty(value = "",index = 0)
    @Column(name = "ITEM_MODEL")
    private String itemModel;

    @ExcelProperty(value = "",index = 0)
    @Column(name = "ITEM_REGISTER_ID")
    private String itemRegisterId;

    @ExcelProperty(value = "",index = 0)
    @Column(name = "ITEM_REGISTER_SPEC")
    private String itemRegisterSpec;

    @ExcelProperty(value = "",index = 0)
    @Column(name = "ITEM_REGISTER_MODEL")
    private String itemRegisterModel;

    @ExcelProperty(value = "",index = 0)
    @Column(name = "ITEM_REGISTER_VALID_DATE")
    private String itemRegisterValidDate;

    @ExcelProperty(value = "",index = 0)
    @Column(name = "ITEM_BRAND")
    private String itemBrand;

    @ExcelProperty(value = "",index = 0)
    @Column(name = "ITEM_MIN_NUMBER_UNIT")
    private String itemMinNumberUnit;

    @ExcelProperty(value = "",index = 0)
    @Column(name = "ITEM_MIN_PACK_UNIT")
    private String itemMinPackUnit;

    @ExcelProperty(value = "",index = 0)
    @Column(name = "ITEM_CONVERSION_RATIO")
    private String itemConversionRatio;

    @ExcelProperty(value = "",index = 0)
    @Column(name = "ITEM_PACK_SPEC")
    private String itemPackSpec;

    @ExcelProperty(value = "",index = 0)
    @Column(name = "ITEM_PACK_MATERIAL")
    private String itemPackMaterial;

    @ExcelProperty(value = "",index = 0)
    @Column(name = "ITEM_PERFORMANCE")
    private String itemPerformance;

    @ExcelProperty(value = "",index = 0)
    @Column(name = "ITEM_SCOPE")
    private String itemScope;

    @ExcelProperty(value = "",index = 0)
    @Column(name = "COMPOSE_NUMBER")
    private String composeNumber;

    @ExcelProperty(value = "",index = 0)
    @Column(name = "ITEM_CHECK_STATE")
    private String itemCheckState;

    @ExcelProperty(value = "",index = 0)
    @Column(name = "ITEM_CHECK_PERSON")
    private String itemCheckPerson;

    @ExcelProperty(value = "",index = 0)
    @Column(name = "ITEM_BASE_PRICE")
    private String itemBasePrice;

    @ExcelProperty(value = "",index = 0)
    @Column(name = "ITEM_QUOTE")
    private String itemQuote;

    @Column(name = "IS_DEL")
    private Integer isDel;

    @Column(name = "UPDATE_USER")
    private String updateUser;

    @Column(name = "UPDATE_TIME")
    private Date updateTime;

    @Column(name = "CREATE_USER")
    private String createUser;

    @Column(name = "CREATE_TIME")
    private Date createTime;

    /**
     * @return ID
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return PROJECT_ID
     */
    public Long getProjectId() {
        return projectId;
    }

    /**
     * @param projectId
     */
    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    /**
     * @return BID_NUMBER
     */
    public String getBidNumber() {
        return bidNumber;
    }

    /**
     * @param bidNumber
     */
    public void setBidNumber(String bidNumber) {
        this.bidNumber = bidNumber;
    }

    /**
     * @return BID_ORG_NAME
     */
    public String getBidOrgName() {
        return bidOrgName;
    }

    /**
     * @param bidOrgName
     */
    public void setBidOrgName(String bidOrgName) {
        this.bidOrgName = bidOrgName;
    }

    /**
     * @return BID_USER_NAME
     */
    public String getBidUserName() {
        return bidUserName;
    }

    /**
     * @param bidUserName
     */
    public void setBidUserName(String bidUserName) {
        this.bidUserName = bidUserName;
    }

    /**
     * @return ZT_ITEM_ID
     */
    public String getZtItemId() {
        return ztItemId;
    }

    /**
     * @param ztItemId
     */
    public void setZtItemId(String ztItemId) {
        this.ztItemId = ztItemId;
    }

    /**
     * @return MAINTAIN_ORG_NAME
     */
    public String getMaintainOrgName() {
        return maintainOrgName;
    }

    /**
     * @param maintainOrgName
     */
    public void setMaintainOrgName(String maintainOrgName) {
        this.maintainOrgName = maintainOrgName;
    }

    /**
     * @return PRODUCE_ORG_NAME
     */
    public String getProduceOrgName() {
        return produceOrgName;
    }

    /**
     * @param produceOrgName
     */
    public void setProduceOrgName(String produceOrgName) {
        this.produceOrgName = produceOrgName;
    }

    /**
     * @return ZT_ITEM_NAME
     */
    public String getZtItemName() {
        return ztItemName;
    }

    /**
     * @param ztItemName
     */
    public void setZtItemName(String ztItemName) {
        this.ztItemName = ztItemName;
    }

    /**
     * @return PROJECT_CATEGORY_ID
     */
    public String getProjectCategoryId() {
        return projectCategoryId;
    }

    /**
     * @param projectCategoryId
     */
    public void setProjectCategoryId(String projectCategoryId) {
        this.projectCategoryId = projectCategoryId;
    }

    /**
     * @return PROJECT_CATEGORY_NAME
     */
    public String getProjectCategoryName() {
        return projectCategoryName;
    }

    /**
     * @param projectCategoryName
     */
    public void setProjectCategoryName(String projectCategoryName) {
        this.projectCategoryName = projectCategoryName;
    }

    /**
     * @return PROJECT_CATALOGUE_ID
     */
    public String getProjectCatalogueId() {
        return projectCatalogueId;
    }

    /**
     * @param projectCatalogueId
     */
    public void setProjectCatalogueId(String projectCatalogueId) {
        this.projectCatalogueId = projectCatalogueId;
    }

    /**
     * @return PROJECT_CATALOGUE_NAME
     */
    public String getProjectCatalogueName() {
        return projectCatalogueName;
    }

    /**
     * @param projectCatalogueName
     */
    public void setProjectCatalogueName(String projectCatalogueName) {
        this.projectCatalogueName = projectCatalogueName;
    }

    /**
     * @return ZT_ITEM_SPEC
     */
    public String getZtItemSpec() {
        return ztItemSpec;
    }

    /**
     * @param ztItemSpec
     */
    public void setZtItemSpec(String ztItemSpec) {
        this.ztItemSpec = ztItemSpec;
    }

    /**
     * @return ZT_ITEM_MODEL
     */
    public String getZtItemModel() {
        return ztItemModel;
    }

    /**
     * @param ztItemModel
     */
    public void setZtItemModel(String ztItemModel) {
        this.ztItemModel = ztItemModel;
    }

    /**
     * @return ZT_REGISTER_ID
     */
    public String getZtRegisterId() {
        return ztRegisterId;
    }

    /**
     * @param ztRegisterId
     */
    public void setZtRegisterId(String ztRegisterId) {
        this.ztRegisterId = ztRegisterId;
    }

    /**
     * @return ZT_REGISTER_SPEC
     */
    public String getZtRegisterSpec() {
        return ztRegisterSpec;
    }

    /**
     * @param ztRegisterSpec
     */
    public void setZtRegisterSpec(String ztRegisterSpec) {
        this.ztRegisterSpec = ztRegisterSpec;
    }

    /**
     * @return ZT_REGISTER_MODEL
     */
    public String getZtRegisterModel() {
        return ztRegisterModel;
    }

    /**
     * @param ztRegisterModel
     */
    public void setZtRegisterModel(String ztRegisterModel) {
        this.ztRegisterModel = ztRegisterModel;
    }

    /**
     * @return ZT_ITEM_BRAND
     */
    public String getZtItemBrand() {
        return ztItemBrand;
    }

    /**
     * @param ztItemBrand
     */
    public void setZtItemBrand(String ztItemBrand) {
        this.ztItemBrand = ztItemBrand;
    }

    /**
     * @return ZT_ITEM_MIN_NUMBER_UNIT
     */
    public String getZtItemMinNumberUnit() {
        return ztItemMinNumberUnit;
    }

    /**
     * @param ztItemMinNumberUnit
     */
    public void setZtItemMinNumberUnit(String ztItemMinNumberUnit) {
        this.ztItemMinNumberUnit = ztItemMinNumberUnit;
    }

    /**
     * @return ZT_ITEM_PACK_SPEC
     */
    public String getZtItemPackSpec() {
        return ztItemPackSpec;
    }

    /**
     * @param ztItemPackSpec
     */
    public void setZtItemPackSpec(String ztItemPackSpec) {
        this.ztItemPackSpec = ztItemPackSpec;
    }

    /**
     * @return ZT_ITEM_PACK_MATERIAL
     */
    public String getZtItemPackMaterial() {
        return ztItemPackMaterial;
    }

    /**
     * @param ztItemPackMaterial
     */
    public void setZtItemPackMaterial(String ztItemPackMaterial) {
        this.ztItemPackMaterial = ztItemPackMaterial;
    }

    /**
     * @return ZT_ITEM_PERFORMACE
     */
    public String getZtItemPerformace() {
        return ztItemPerformace;
    }

    /**
     * @param ztItemPerformace
     */
    public void setZtItemPerformace(String ztItemPerformace) {
        this.ztItemPerformace = ztItemPerformace;
    }

    /**
     * @return ZT_ITEM_SCOPE
     */
    public String getZtItemScope() {
        return ztItemScope;
    }

    /**
     * @param ztItemScope
     */
    public void setZtItemScope(String ztItemScope) {
        this.ztItemScope = ztItemScope;
    }

    /**
     * @return ZT_DATA_CHECK_STATE
     */
    public String getZtDataCheckState() {
        return ztDataCheckState;
    }

    /**
     * @param ztDataCheckState
     */
    public void setZtDataCheckState(String ztDataCheckState) {
        this.ztDataCheckState = ztDataCheckState;
    }

    /**
     * @return ZT_CATALOGUE_CHECK_STATE
     */
    public String getZtCatalogueCheckState() {
        return ztCatalogueCheckState;
    }

    /**
     * @param ztCatalogueCheckState
     */
    public void setZtCatalogueCheckState(String ztCatalogueCheckState) {
        this.ztCatalogueCheckState = ztCatalogueCheckState;
    }

    /**
     * @return ITEM_ID
     */
    public String getItemId() {
        return itemId;
    }

    /**
     * @param itemId
     */
    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    /**
     * @return ITEM_NAME
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * @param itemName
     */
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    /**
     * @return ITEM_SPEC
     */
    public String getItemSpec() {
        return itemSpec;
    }

    /**
     * @param itemSpec
     */
    public void setItemSpec(String itemSpec) {
        this.itemSpec = itemSpec;
    }

    /**
     * @return ITEM_MODEL
     */
    public String getItemModel() {
        return itemModel;
    }

    /**
     * @param itemModel
     */
    public void setItemModel(String itemModel) {
        this.itemModel = itemModel;
    }

    /**
     * @return ITEM_REGISTER_ID
     */
    public String getItemRegisterId() {
        return itemRegisterId;
    }

    /**
     * @param itemRegisterId
     */
    public void setItemRegisterId(String itemRegisterId) {
        this.itemRegisterId = itemRegisterId;
    }

    /**
     * @return ITEM_REGISTER_SPEC
     */
    public String getItemRegisterSpec() {
        return itemRegisterSpec;
    }

    /**
     * @param itemRegisterSpec
     */
    public void setItemRegisterSpec(String itemRegisterSpec) {
        this.itemRegisterSpec = itemRegisterSpec;
    }

    /**
     * @return ITEM_REGISTER_MODEL
     */
    public String getItemRegisterModel() {
        return itemRegisterModel;
    }

    /**
     * @param itemRegisterModel
     */
    public void setItemRegisterModel(String itemRegisterModel) {
        this.itemRegisterModel = itemRegisterModel;
    }

    /**
     * @return ITEM_REGISTER_VALID_DATE
     */
    public String getItemRegisterValidDate() {
        return itemRegisterValidDate;
    }

    /**
     * @param itemRegisterValidDate
     */
    public void setItemRegisterValidDate(String itemRegisterValidDate) {
        this.itemRegisterValidDate = itemRegisterValidDate;
    }

    /**
     * @return ITEM_BRAND
     */
    public String getItemBrand() {
        return itemBrand;
    }

    /**
     * @param itemBrand
     */
    public void setItemBrand(String itemBrand) {
        this.itemBrand = itemBrand;
    }

    /**
     * @return ITEM_MIN_NUMBER_UNIT
     */
    public String getItemMinNumberUnit() {
        return itemMinNumberUnit;
    }

    /**
     * @param itemMinNumberUnit
     */
    public void setItemMinNumberUnit(String itemMinNumberUnit) {
        this.itemMinNumberUnit = itemMinNumberUnit;
    }

    /**
     * @return ITEM_MIN_PACK_UNIT
     */
    public String getItemMinPackUnit() {
        return itemMinPackUnit;
    }

    /**
     * @param itemMinPackUnit
     */
    public void setItemMinPackUnit(String itemMinPackUnit) {
        this.itemMinPackUnit = itemMinPackUnit;
    }

    /**
     * @return ITEM_CONVERSION_RATIO
     */
    public String getItemConversionRatio() {
        return itemConversionRatio;
    }

    /**
     * @param itemConversionRatio
     */
    public void setItemConversionRatio(String itemConversionRatio) {
        this.itemConversionRatio = itemConversionRatio;
    }

    /**
     * @return ITEM_PACK_SPEC
     */
    public String getItemPackSpec() {
        return itemPackSpec;
    }

    /**
     * @param itemPackSpec
     */
    public void setItemPackSpec(String itemPackSpec) {
        this.itemPackSpec = itemPackSpec;
    }

    /**
     * @return ITEM_PACK_MATERIAL
     */
    public String getItemPackMaterial() {
        return itemPackMaterial;
    }

    /**
     * @param itemPackMaterial
     */
    public void setItemPackMaterial(String itemPackMaterial) {
        this.itemPackMaterial = itemPackMaterial;
    }

    /**
     * @return ITEM_PERFORMANCE
     */
    public String getItemPerformance() {
        return itemPerformance;
    }

    /**
     * @param itemPerformance
     */
    public void setItemPerformance(String itemPerformance) {
        this.itemPerformance = itemPerformance;
    }

    /**
     * @return ITEM_SCOPE
     */
    public String getItemScope() {
        return itemScope;
    }

    /**
     * @param itemScope
     */
    public void setItemScope(String itemScope) {
        this.itemScope = itemScope;
    }

    /**
     * @return COMPOSE_NUMBER
     */
    public String getComposeNumber() {
        return composeNumber;
    }

    /**
     * @param composeNumber
     */
    public void setComposeNumber(String composeNumber) {
        this.composeNumber = composeNumber;
    }

    /**
     * @return ITEM_CHECK_STATE
     */
    public String getItemCheckState() {
        return itemCheckState;
    }

    /**
     * @param itemCheckState
     */
    public void setItemCheckState(String itemCheckState) {
        this.itemCheckState = itemCheckState;
    }

    /**
     * @return ITEM_CHECK_PERSON
     */
    public String getItemCheckPerson() {
        return itemCheckPerson;
    }

    /**
     * @param itemCheckPerson
     */
    public void setItemCheckPerson(String itemCheckPerson) {
        this.itemCheckPerson = itemCheckPerson;
    }

    /**
     * @return ITEM_BASE_PRICE
     */
    public String getItemBasePrice() {
        return itemBasePrice;
    }

    /**
     * @param itemBasePrice
     */
    public void setItemBasePrice(String itemBasePrice) {
        this.itemBasePrice = itemBasePrice;
    }

    /**
     * @return ITEM_QUOTE
     */
    public String getItemQuote() {
        return itemQuote;
    }

    /**
     * @param itemQuote
     */
    public void setItemQuote(String itemQuote) {
        this.itemQuote = itemQuote;
    }

    /**
     * @return IS_DEL
     */
    public Integer getIsDel() {
        return isDel;
    }

    /**
     * @param isDel
     */
    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    /**
     * @return UPDATE_USER
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * @param updateUser
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    /**
     * @return UPDATE_TIME
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * @return CREATE_USER
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * @param createUser
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * @return CREATE_TIME
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}