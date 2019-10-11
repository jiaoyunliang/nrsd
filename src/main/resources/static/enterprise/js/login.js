Vue.component("login-dialog",{
    template: "#login-template",
    props:{
        dialogTitle: "",
        showDialog: false,
    },
    data(){
        return {
            dialogTop: "5vh",
            formData:{
                username:"",
                password:""
            }
        }
    },
    created: function() {},
    mounted: function() {},
    watch:{},
    methods:{
        doLogin:function(){
            let _this = this;
            let fd = new FormData();
            fd.append("username",_this.formData.username);
            fd.append("password",_this.formData.password);

            let config = {
                headers:{
                    'Content-Type': 'application/x-www-form-urlencoded',
                    'Accept':'application/json; charset=utf-8'
                }
            };

            _this.$refs["form"].validate((valid) => {
                if (valid) {
                    axios
                        .post(getCtx()+"/enterprise/doLogin", fd,config)
                        .then(rs => {
                            if(rs.status==200){
                                if(rs.data.status == 200) {
                                    window.location.reload();
                                } else {
                                    _this.$notify.error({
                                        title: "提示",
                                        message: rs.data.msg
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
                        console.log("api doLogin error", err);
                    });
                } else {
                    return false;
                }
            });



        },
        dialogClosed: function() {
            this.formData.username='';
            this.formData.password='';
            this.$emit("update:showDialog", false);
        }
    }
});

new Vue({
    el:"#header_login",
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
        handelOpenLoginDialog() {
            let _this = this;
            _this.dialogTitle = "欢迎请登录";
            _this.isShowDialog = true;
        }
    }
});


