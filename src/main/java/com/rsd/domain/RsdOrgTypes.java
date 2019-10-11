package com.rsd.domain;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "BNZ_ORG_TYPES")
public class RsdOrgTypes {
    @Id
    @Column(name = "ORG_ID")
    private Long orgId;

    @Id
    @Column(name = "TYPE_ID")
    private Long typeId;

    /**
     * @return ORG_ID
     */
    public Long getOrgId() {
        return orgId;
    }

    /**
     * @param orgId
     */
    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    /**
     * @return TYPE_ID
     */
    public Long getTypeId() {
        return typeId;
    }

    /**
     * @param typeId
     */
    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }
}