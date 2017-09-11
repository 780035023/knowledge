package com.ixinnuo.financial.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "product_info")
public class Product {
    @Id
    private Integer id;

    /**
     * 创建时间
     */
    @Column(name = "gmt_create")
    private Date gmtCreate;

    /**
     * 更新时间
     */
    @Column(name = "gmt_update")
    private Date gmtUpdate;

    /**
     * 产品名称
     */
    @Column(name = "product_name")
    private String productName;

    /**
     * 担保方式代码
     */
    @Column(name = "guarantee_type_code")
    private String guaranteeTypeCode;

    /**
     * 担保方式名称
     */
    @Column(name = "guarantee_type_name")
    private String guaranteeTypeName;

    /**
     * 最大贷款额度
     */
    @Column(name = "max_loan_amount")
    private Double maxLoanAmount;

    /**
     * 还款方式代码
     */
    @Column(name = "repayment_code")
    private String repaymentCode;

    /**
     * 还款方式名称
     */
    @Column(name = "repayment_name")
    private String repaymentName;

    /**
     * 还款期限
     */
    @Column(name = "repayment_term")
    private String repaymentTerm;

    /**
     * 利率
     */
    private String rate;

    /**
     * 产品备注
     */
    @Column(name = "product_remark")
    private String productRemark;

    /**
     * 产品状态代码
     */
    @Column(name = "status_code")
    private String statusCode;

    /**
     * 产品状态名称
     */
    @Column(name = "status_name")
    private String statusName;

    /**
     * 银行简称
     */
    @Column(name = "bank_code")
    private String bankCode;

    /**
     * 银行名称
     */
    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "file_path")
    private String filePath;

    /**
     * 准入计算模板ID
     */
    @Column(name = "access_template_id")
    private Integer accessTemplateId;

    /**
     * 产品详情
     */
    @Column(name = "product_detail")
    private String productDetail;

    /**
     * 申请资料
     */
    @Column(name = "apply_materials")
    private String applyMaterials;

    /**
     * 贷款协议
     */
    @Column(name = "loan_agreement")
    private String loanAgreement;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取创建时间
     *
     * @return gmt_create - 创建时间
     */
    public Date getGmtCreate() {
        return gmtCreate;
    }

    /**
     * 设置创建时间
     *
     * @param gmtCreate 创建时间
     */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * 获取更新时间
     *
     * @return gmt_update - 更新时间
     */
    public Date getGmtUpdate() {
        return gmtUpdate;
    }

    /**
     * 设置更新时间
     *
     * @param gmtUpdate 更新时间
     */
    public void setGmtUpdate(Date gmtUpdate) {
        this.gmtUpdate = gmtUpdate;
    }

    /**
     * 获取产品名称
     *
     * @return product_name - 产品名称
     */
    public String getProductName() {
        return productName;
    }

    /**
     * 设置产品名称
     *
     * @param productName 产品名称
     */
    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    /**
     * 获取担保方式代码
     *
     * @return guarantee_type_code - 担保方式代码
     */
    public String getGuaranteeTypeCode() {
        return guaranteeTypeCode;
    }

    /**
     * 设置担保方式代码
     *
     * @param guaranteeTypeCode 担保方式代码
     */
    public void setGuaranteeTypeCode(String guaranteeTypeCode) {
        this.guaranteeTypeCode = guaranteeTypeCode == null ? null : guaranteeTypeCode.trim();
    }

    /**
     * 获取担保方式名称
     *
     * @return guarantee_type_name - 担保方式名称
     */
    public String getGuaranteeTypeName() {
        return guaranteeTypeName;
    }

    /**
     * 设置担保方式名称
     *
     * @param guaranteeTypeName 担保方式名称
     */
    public void setGuaranteeTypeName(String guaranteeTypeName) {
        this.guaranteeTypeName = guaranteeTypeName == null ? null : guaranteeTypeName.trim();
    }

    /**
     * 获取最大贷款额度
     *
     * @return max_loan_amount - 最大贷款额度
     */
    public Double getMaxLoanAmount() {
        return maxLoanAmount;
    }

    /**
     * 设置最大贷款额度
     *
     * @param maxLoanAmount 最大贷款额度
     */
    public void setMaxLoanAmount(Double maxLoanAmount) {
        this.maxLoanAmount = maxLoanAmount;
    }

    /**
     * 获取还款方式代码
     *
     * @return repayment_code - 还款方式代码
     */
    public String getRepaymentCode() {
        return repaymentCode;
    }

    /**
     * 设置还款方式代码
     *
     * @param repaymentCode 还款方式代码
     */
    public void setRepaymentCode(String repaymentCode) {
        this.repaymentCode = repaymentCode == null ? null : repaymentCode.trim();
    }

    /**
     * 获取还款方式名称
     *
     * @return repayment_name - 还款方式名称
     */
    public String getRepaymentName() {
        return repaymentName;
    }

    /**
     * 设置还款方式名称
     *
     * @param repaymentName 还款方式名称
     */
    public void setRepaymentName(String repaymentName) {
        this.repaymentName = repaymentName == null ? null : repaymentName.trim();
    }

    /**
     * 获取还款期限
     *
     * @return repayment_term - 还款期限
     */
    public String getRepaymentTerm() {
        return repaymentTerm;
    }

    /**
     * 设置还款期限
     *
     * @param repaymentTerm 还款期限
     */
    public void setRepaymentTerm(String repaymentTerm) {
        this.repaymentTerm = repaymentTerm == null ? null : repaymentTerm.trim();
    }

    /**
     * 获取利率
     *
     * @return rate - 利率
     */
    public String getRate() {
        return rate;
    }

    /**
     * 设置利率
     *
     * @param rate 利率
     */
    public void setRate(String rate) {
        this.rate = rate == null ? null : rate.trim();
    }

    /**
     * 获取产品备注
     *
     * @return product_remark - 产品备注
     */
    public String getProductRemark() {
        return productRemark;
    }

    /**
     * 设置产品备注
     *
     * @param productRemark 产品备注
     */
    public void setProductRemark(String productRemark) {
        this.productRemark = productRemark == null ? null : productRemark.trim();
    }

    /**
     * 获取产品状态代码
     *
     * @return status_code - 产品状态代码
     */
    public String getStatusCode() {
        return statusCode;
    }

    /**
     * 设置产品状态代码
     *
     * @param statusCode 产品状态代码
     */
    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode == null ? null : statusCode.trim();
    }

    /**
     * 获取产品状态名称
     *
     * @return status_name - 产品状态名称
     */
    public String getStatusName() {
        return statusName;
    }

    /**
     * 设置产品状态名称
     *
     * @param statusName 产品状态名称
     */
    public void setStatusName(String statusName) {
        this.statusName = statusName == null ? null : statusName.trim();
    }

    /**
     * 获取银行简称
     *
     * @return bank_code - 银行简称
     */
    public String getBankCode() {
        return bankCode;
    }

    /**
     * 设置银行简称
     *
     * @param bankCode 银行简称
     */
    public void setBankCode(String bankCode) {
        this.bankCode = bankCode == null ? null : bankCode.trim();
    }

    /**
     * 获取银行名称
     *
     * @return bank_name - 银行名称
     */
    public String getBankName() {
        return bankName;
    }

    /**
     * 设置银行名称
     *
     * @param bankName 银行名称
     */
    public void setBankName(String bankName) {
        this.bankName = bankName == null ? null : bankName.trim();
    }

    /**
     * @return file_path
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * @param filePath
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath == null ? null : filePath.trim();
    }

    /**
     * 获取准入计算模板ID
     *
     * @return access_template_id - 准入计算模板ID
     */
    public Integer getAccessTemplateId() {
        return accessTemplateId;
    }

    /**
     * 设置准入计算模板ID
     *
     * @param accessTemplateId 准入计算模板ID
     */
    public void setAccessTemplateId(Integer accessTemplateId) {
        this.accessTemplateId = accessTemplateId;
    }

    /**
     * 获取产品详情
     *
     * @return product_detail - 产品详情
     */
    public String getProductDetail() {
        return productDetail;
    }

    /**
     * 设置产品详情
     *
     * @param productDetail 产品详情
     */
    public void setProductDetail(String productDetail) {
        this.productDetail = productDetail == null ? null : productDetail.trim();
    }

    /**
     * 获取申请资料
     *
     * @return apply_materials - 申请资料
     */
    public String getApplyMaterials() {
        return applyMaterials;
    }

    /**
     * 设置申请资料
     *
     * @param applyMaterials 申请资料
     */
    public void setApplyMaterials(String applyMaterials) {
        this.applyMaterials = applyMaterials == null ? null : applyMaterials.trim();
    }

    /**
     * 获取贷款协议
     *
     * @return loan_agreement - 贷款协议
     */
    public String getLoanAgreement() {
        return loanAgreement;
    }

    /**
     * 设置贷款协议
     *
     * @param loanAgreement 贷款协议
     */
    public void setLoanAgreement(String loanAgreement) {
        this.loanAgreement = loanAgreement == null ? null : loanAgreement.trim();
    }
}