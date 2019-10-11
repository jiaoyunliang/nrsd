
//发送信息反馈
Vue.component("add-insideMsg",{
    template: "#add-insideMsg-template",
    props:{
        dialogTitle: "",
        showDialog: false,
    },
    data(){
        return {
            dialogTop: "5vh",
            orgOptions:[],
            loading:false,
            formData:{
                title:"",
                content:"",
                orgIds:[]
            }
        }
    },
    created: function() {},
    mounted: function() {},
    watch:{
        showDialog: function(n, o) {
            this.orgOptions = [];
            this.formData.title="";
            this.formData.content="";
            this.formData.orgIds=[];
        }
    },
    methods:{
        handelQueryOrgOptions:function(key){
            let _this = this;
            if(key!=''){
                _this.loading = true;
                let params = {
                    sysId: 3,
                    orgName:key
                };
                axios
                    .post(getCtx()+"/hospital/sysMsg/queryOrgList", params)
                    .then(rs => {
                        if(rs.status == 200){
                            if (rs.data.result == 200) {
                                _this.orgOptions = rs.data.data;
                            }
                            _this.loading = false;
                        } else {
                            _this.loading = false;
                            _this.$notify.error({
                                title: "提示",
                                message: '服务器错误请与管理员联系'
                            });
                        }
                    }).catch(err => {
                    _this.loading = false;
                    console.log("api queryOrgList error", err);
                });
            } else {

            }
        },
        handelAdd:function(){
            let _this = this;
            _this.$refs["form"].validate((valid) => {
                if (valid) {
                    axios
                        .post(getCtx()+"/hospital/sysMsg/addInsideMsg", _this.formData)
                        .then(rs => {
                            if(rs.status == 200){
                                if (rs.data.result == 200) {
                                    _this.$notify.success({
                                        title: "提示",
                                        message: '消息已发送'
                                    });
                                } else if(rs.data.result == 401){
                                    location.href=getCtx()+'/hospital/expired';
                                } else {
                                    _this.$notify.error({
                                        title: "提示",
                                        message: '消息发送失败'
                                    });
                                }
                                location.reload();
                            } else {
                                _this.$notify.error({
                                    title: "提示",
                                    message: '服务器错误请与管理员联系'
                                });
                                location.reload();
                            }
                        }).catch(err => {
                        console.log("api addInsideMsg error", err);
                        location.reload();
                    });
                } else {
                    return false;
                }
            });
        },
        dialogClosed: function() {
            this.orgOptions = [];
            this.formData.title="";
            this.formData.content="";
            this.formData.orgIds=[];
            this.$emit("update:showDialog", false);
        },
    }
});

//站内消息 组件
Vue.component("msg-component", {
    template: "#msg-template",
    props:{
        isView:{
            type:String,
            default:""
        }
    },
    data() {
        return {
            dialogTitle: "",
            isShowDialog: false,
            loading: false,
            pageInfo: {
                total: 1,
                current: 1,
                size: 10,
                sort: ""
            },
            dataList: []
        };
    },
    created: function() {},
    mounted: function() {
    },
    watch: {
        isView: function(n, o) {
            if (n === "msg") {
                this.handleSearchData();
            }
        }
    },
    methods:{
        handleSearchData() {
            this.pageInfo.total = 1;
            this.pageInfo.current = 1;
            this.pageInfo.size = 10;
            this.pageInfo.sort = "";
            this.fetchData();
        },
        fetchData:function(){
            let _this = this;
            _this.loading = true;

            let params = {
                pageInput: _this.pageInfo
            };
            axios
                .post(getCtx()+"/hospital/sysMsg/queryInsideMsgPage", params)
                .then(rs => {
                    if(rs.status == 200){
                        if (rs.data.result == 200) {
                            _this.loading = false;
                            _this.pageInfo.total = rs.data.pageInfo.total;
                            _this.dataList = rs.data.page;
                        } else if(rs.data.result == 401){
                            location.href=getCtx()+'/hospital/expired';
                        } else {
                            _this.loading = false;
                            _this.$notify.error({
                                title: "提示",
                                message: rs.data.exceptionMsg
                            });
                        }
                    } else {
                        _this.loading = false;
                        _this.$notify.error({
                            title: "提示",
                            message: '服务器错误请与管理员联系'
                        });
                    }
                }).catch(err => {
                _this.loading = false;
                console.log("api queryInsideMsgPage error", err);
            });
        },
        changePageSize: function(pageSize) {
            this.pageInfo.size = pageSize;
            this.fetchData();
        },
        pageIndexChange: function(pageIndex) {
            this.pageInfo.current = pageIndex;
            this.fetchData();
        },
        titleHref:function (data) {
            return "<a href='"+getCtx()+"/hospital/sysMsg/queryInsideMsg?id="+data.id+"'>"+data.title+"</a>"
        },
        handelAdd() {
            let _this = this;
            _this.dialogTitle = "消息";
            _this.isShowDialog = true;
        }
    }
});

//发送信息反馈
Vue.component("add-feedback",{
    template: "#add-feedback-template",
    props:{
        dialogTitle: "",
        showDialog: false,
    },
    data(){
        return {
            dialogTop: "5vh",
            formData:{
                content:""
            }
        }
    },
    created: function() {},
    mounted: function() {},
    watch:{},
    methods:{
        handelAdd:function(){
            let _this = this;

            if(_this.formData.content==''){
                _this.$notify.error({
                    title: "提示",
                    message: '内容未填写！'
                });
                return;
            };
            if(_this.formData.content.length>200){
                _this.$notify.error({
                    title: "提示",
                    message: '反馈内容请在200字以内！'
                });
                return;
            };

            axios
                .post(getCtx()+"/hospital/sysMsg/addFeedback", _this.formData)
                .then(rs => {
                    if(rs.status == 200){
                        if (rs.data.result == 200) {
                            _this.$notify.success({
                                title: "提示",
                                message: '反馈已发送'
                            });
                        } else if(rs.data.result == 401){
                            location.href=getCtx()+'/hospital/expired';
                        } else {
                            _this.$notify.error({
                                title: "提示",
                                message: '反馈发送失败'
                            });
                        }
                        _this.dialogClosed();
                    } else {
                        _this.$notify.error({
                            title: "提示",
                            message: '服务器错误请与管理员联系'
                        });
                        _this.dialogClosed();
                    }
                }).catch(err => {
                _this.dialogClosed();
                console.log("api addFeedback error", err);
            });
        },
        dialogClosed: function() {
            this.formData.content='';
            this.$emit("update:showDialog", false);
        },
    }
});

//系统通知 组件
Vue.component("notice-component", {
    template: "#notice-template",
    props:{
        isView:{
            type:String,
            default:""
        }
    },
    data() {
        return {
            dialogTitle: "",
            isShowDialog: false,
            loading: false,
            pageInfo: {
                total: 1,
                current: 1,
                size: 10,
                sort: ""
            },
            dataList: []
        };
    },
    created: function() {},
    mounted: function() {},
    watch: {
        isView: function(n, o) {
            if (n === "notice") {
                this.handleSearchData();
            }
        }
    },
    methods:{
        handleSearchData() {
            this.pageInfo.total = 1;
            this.pageInfo.current = 1;
            this.pageInfo.size = 10;
            this.pageInfo.sort = "";
            this.fetchData();
        },
        fetchData:function(){
            let _this = this;
            _this.loading = true;

            let params = {
                pageInput: _this.pageInfo
            };

            axios
                .post(getCtx()+"/hospital/sysMsg/queryOrgReadNoticePage", params)
                .then(rs => {
                    if(rs.status == 200){
                        if (rs.data.result == 200) {
                            _this.loading = false;
                            _this.pageInfo.total = rs.data.pageInfo.total;
                            _this.dataList = rs.data.page;
                        } else if(rs.data.result == 401){
                            location.href=getCtx()+'/hospital/expired';
                        } else {
                            _this.loading = false;
                            _this.$notify.error({
                                title: "提示",
                                message: rs.data.exceptionMsg
                            });
                        }
                    } else {
                        _this.loading = false;
                        _this.$notify.error({
                            title: "提示",
                            message: '服务器错误请与管理员联系'
                        });
                    }
                }).catch(err => {
                _this.loading = false;
                console.log("api queryInsideMsgPage error", err);
            });
        },
        changePageSize: function(pageSize) {
            this.pageInfo.size = pageSize;
            this.fetchData();
        },
        pageIndexChange: function(pageIndex) {
            this.pageInfo.current = pageIndex;
            this.fetchData();
        },
        titleHref:function (data) {
            if(data.accountId==null){
                return "<a style='font-weight: bold' href='"+getCtx()+"/hospital/sysMsg/queryNotice?id="+data.id+"'>"+data.title+"</a>"
            } else {
                return "<a href='"+getCtx()+"/hospital/sysMsg/queryNotice?id="+data.id+"'>"+data.title+"</a>"
            }
        },
        handelAdd() {
            let _this = this;
            _this.dialogTitle = "反馈";
            _this.isShowDialog = true;
        }
    }
});

new Vue({
    el:"#sysMsg",
    data:function () {
        return {
            activeName: ""
        }
    },
    created: function() {},
    mounted: function() {
        this.activeName = "msg";
    },
    methods:{
        handleClick(tab, event) {}
    }
})