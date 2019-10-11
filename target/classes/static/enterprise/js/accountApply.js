Vue.component("account-apply-dialog",{
    template: "#account-apply-template",
    props:{
        dialogTitle: "",
        showDialog: false,
    },
    data(){
        return {
            dialogTop: "5vh",
            formData:{
                name:'',
                enterpriseType:null,
                contact:'',
                mobile:'',
                remark:''
            }
        }
    },
    created: function() {},
    mounted: function() {},
    watch:{},
    methods:{
        onSubmit:function(){
            let _this = this;
            _this.$refs["ruleForm"].validate((valid) => {
                if (valid) {
                    axios
                        .post(getCtx()+"/enterprise/accountApply", _this.formData)
                        .then(rs => {
                            console.log('rs',rs);
                            if(rs.status==200){
                                if(rs.data.result == 200) {
                                    _this.$notify.success({
                                        title: "提示",
                                        message: '信息已提交成功！'
                                    });
                                    _this.dialogClosed();
                                } else {
                                    _this.$notify.error({
                                        title: "提示",
                                        message: rs.data.exceptionMsg
                                    });
                                    _this.dialogClosed();
                                }
                            } else {
                                _this.$notify.error({
                                    title: "提示",
                                    message: '服务器错误请与管理员联系'
                                });
                                _this.dialogClosed();
                            }
                        }).catch(err => {
                        _this.dialogClosed();
                        console.log("api accountApply error", err);
                    });

                } else {
                    return false;
                }
            });
        },
        dialogClosed: function() {
            this.$refs["ruleForm"].resetFields();
            this.formData.name='';
            this.formData.enterpriseType=null;
            this.formData.contact='';
            this.formData.mobile='';
            this.formData.remark='';
            this.$emit("update:showDialog", false);
        }
    }
});

new Vue({
    el:"#accountApply",
    data:function () {
        return {
            dialogTitle: "",
            isShowDialog: false
        }
    },
    created: function() {},
    mounted: function() {
    },
    methods:{
        handelOpenApplyDialog() {
            let _this = this;
            _this.dialogTitle = "企业账号申请";
            _this.isShowDialog = true;
        }
    }
});


