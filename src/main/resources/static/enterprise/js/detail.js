/**
 * 企业介绍
 */
new Vue({
    el: "#detail",
    data() {
        return {
            loading: false,
            uploadPath:UPLOAD_PATH,
            form:{
                contact:'',
                contactInfo:'',
                website:'',
                brandType:null,
                remark:'',
                orgPic:{},
                brandLogo:[]

            },
            orgPic: [],
            brandLogo:[]
        };
    },
    created: function() {
        this.loadOrgDetail();
    },
    mounted: function() {},
    methods: {
        loadOrgDetail(){
            let _this = this;
            axios
                .post(getCtx()+"/enterprise/queryOrgDetail",{})
                .then(rs => {
                    if(rs.status == 200){
                        if (rs.data.result == 200) {
                            if(rs.data.data!=null){
                                _this.form = rs.data.data;
                                _this.loadPics();
                            }
                        } else if(rs.data.result == 401){
                            location.href=getCtx()+'/enterprise/expired';
                        } else {
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
            });
        },
        loadPics(){
            let fs = $(".center").attr("file_server");
            if (this.form.brandLogo != null && this.form.brandLogo.length>0) {
                this.form.brandLogo.forEach(item=>{
                    this.brandLogo.push({
                        response: {
                            data: {
                                newFile: item.fileName,
                                url: item.fileUrl
                            }
                        },
                        name: item.fileName,
                        url: fs+item.fileUrl,
                        uid: item.id
                    });
                });
            } else {
                this.brandLogo = [];
            }

            if(this.form.orgPic!=null){
                this.orgPic = [
                    {
                        response: {
                            data: {
                                newFile: this.form.orgPic.fileName,
                                url: this.form.orgPic.fileUrl
                            }
                        },
                        name: this.form.orgPic.fileName,
                        url: fs+this.form.orgPic.fileUrl,
                        uid: this.form.orgPic.id
                    }
                ];
            } else {
                this.orgPic=[];
            }
        },
        onSubmit(){
            let _this = this;
            _this.form.brandLogo = [];
            _this.form.orgPic ={};

            if (
                _this.$refs.elUploadOrg.uploadFiles == null ||
                _this.$refs.elUploadOrg.uploadFiles.length == 0
            ) {
                this.$notify({
                    message: "企业图片未上传！",
                    type: "warning"
                });
                return false;
            }

            if (
                _this.$refs.elUploadOrg.uploadFiles != null &&
                _this.$refs.elUploadOrg.uploadFiles.length != 0
            ) {
                let _uploadOrgPic = _this.$refs.elUploadOrg.uploadFiles;
                _this.form.orgPic.fileUrl = _uploadOrgPic[0].response.data.url;
                _this.form.orgPic.fileName = _uploadOrgPic[0].response.data.newFile;
            }

            let _uploadLogoPic = _this.$refs.elUploadLogo.uploadFiles;

            _uploadLogoPic.forEach(item=>{
                _this.form.brandLogo.push({fileUrl:item.response.data.url,fileName:item.response.data.newFile})
            });

            console.log('form',_this.form);
            _this.$refs["form"].validate((valid) => {
                if (valid) {
                    axios
                        .post(getCtx()+"/enterprise/saveOrgDetail",_this.form)
                        .then(rs => {
                            if(rs.status == 200){
                                if (rs.data.result == 200) {
                                    location.href=getCtx()+'/enterprise/index';
                                } else if(rs.data.result == 401){
                                    location.href=getCtx()+'/enterprise/expired';
                                } else {
                                    _this.$notify.error({
                                        title: "提示",
                                        message: rs.data.exceptionMsg
                                    });
                                }
                            } else {
                                _this.$notify.error({
                                    title: "提示",
                                    message: '服务器错误请与管理员联系'
                                });
                            }
                        }).catch(err => {
                        location.href=getCtx()+'/enterprise/index';
                    });

                } else {
                    return false;
                }
            });
        },
        back(){
            location.href=getCtx()+'/enterprise/index';
        },
        handleBeforeUpload(file) {
            const isIMAGE = (file.type === "image/jpeg") || (file.type === "image/gif") || (file.type === "image/png");
            const isLt1M = (file.size / 1024 / 1024) < 1;
            if (!isIMAGE) {
                this.$notify.warning({
                    title: "提示",
                    message: `上传文件只能是图片格式!`
                });
            }
            if (!isLt1M) {
                this.$notify.warning({
                    title: "提示",
                    message: `上传文件大小不能超过 1MB!`
                });
            }
            return isIMAGE && isLt1M;
        },
        handlePreview(file) {
        },
        handleExceed(files, brandLogo) {
            this.$notify.warning({
                title: "提示",
                message: `当前限制选择 10 个文件`
            });
        },
        handleSuccess(response, file, brandLogo) {
        },
        beforeRemove(file, brandLogo) {
        },
        handleBeforeUploadOrg(file) {
            const isIMAGE = (file.type === "image/jpeg") || (file.type === "image/gif") || (file.type === "image/png");
            const isLt1M = (file.size / 1024 / 1024) < 1;
            if (!isIMAGE) {
                this.$notify.warning({
                    title: "提示",
                    message: `上传文件只能是mp4格式!`
                });
            }
            if (!isLt1M) {
                this.$notify.warning({
                    title: "提示",
                    message: `上传文件大小不能超过 1MB!`
                });
            }
            return isIMAGE && isLt1M;
        },
        handleExceedOrg(files, orgPic) {
            this.$notify.warning({
                title: "提示",
                message: `当前限制选择 1 个文件`
            });
        },
        handleSuccessOrg(response, file, orgPic) {
        },
        beforeRemoveOrg(file, orgPic) {
        },
        fileUpload(content){
            // console.log('content',content);
            let formData = new FormData();
            formData.append('file', content.file);
            axios
                .post(content.action,formData,{ headers: { 'Content-Type': 'multipart/form-data' } })
                .then(res => {
                    console.log('res',res);
                    if(res.data.data.state!='Fail'){
                        content.onSuccess(res.data);
                    }else {
                        content.onError('配时文件上传失败，服务器端无响应');
                        this.$notify.error({
                            title: "提示",
                            message: `文件上传失败，服务器端无响应`
                        });
                    }
                }).catch(err => {
                this.$notify.error({
                    title: "提示",
                    message: `文件上传失败，服务器端无响应`
                });
            });
        }
    }
});

