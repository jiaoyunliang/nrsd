
//ue
Vue.component("ue-vue",{
    template: "#ue-template",
    props:{
        value: {
            type: String
        },
        config: {
            type: Object
        }
    },
    data(){
        return {
            ueId: "editor_" + new Date().getTime(),
            instance: null,
            ready: false
        }
    },
    created: function() {},
    mounted: function() {
        this.initEditor();
    },
    watch:{
        value: function(val, oldVal) {
            if (val != null && this.ready) {
                this.instance.setContent(val);
                this.instance.focus(true);
            }
        }
    },
    beforeDestroy() {
        if (this.instance != null && this.instance.destoryed) {
            this.instance.destory();
        }
    },
    methods:{
        initEditor() {
            let _this = this;
            this.$nextTick(() => {
                this.instance = UE.getEditor(this.ueId, this.config);
                this.instance.addListener("ready", () => {
                    this.ready = true;
                    this.$emit("ready", this.instance);
                });
            });
        },
        getUEContent: function() {
            return this.instance.getContent();
        },
        getUEContentTxt: function() {
            return this.instance.getContentTxt();
        },
        setUEContent(val) {
            this.instance.setContent(val);
        }
    }
});

/**
 * 产品列表页面
 *
 */
new Vue({
    el: "#productTable",
    data() {
        return {
            loading: false,
            uploadPath:UPLOAD_PATH,
            form:{
                productTypeId:null,
                productName:'',
                genericName:'',
                brandName:'',
                manufacturer:'',
                remark:'',
                priceList:[],
                video:{},
                picList:[]

            },
            picList: [],
            videoList:[],
            productTypeOptions:[],
            ueConfig: {
                zIndex: 3000,
                autoHeightEnabled: true,
                autoFloatEnabled: true,
                initialFrameWidth: null,
                initialFrameHeight: 250,
                maximumWords:1000,
                wordCount:false,
                elementPathEnabled : false
            }
        };
    },
    created: function() {
        this.loadProductType();
        this.loadProduct();
    },
    mounted: function() {},
    methods: {
        loadProductType(){
            let _this = this;
            axios
                .post(getCtx()+"/enterprise/product/queryProductTypeList",{})
                .then(rs => {
                    if(rs.status == 200){
                        if (rs.data.result == 200) {
                            _this.productTypeOptions = rs.data.data;
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
                console.log("api queryProductTypeList error", err);
            });
        },
        loadProduct(){
            let _this = this;
            let param = {
                id:$(".center").attr("product_id")
            }
            axios
                .post(getCtx()+"/enterprise/product/queryProductById",param)
                .then(rs => {
                    if(rs.status == 200){
                        if (rs.data.result == 200) {
                            // console.log(rs);
                            _this.form = rs.data.data;
                            if(_this.form.priceList==null){
                                _this.form.priceList=[];
                            }

                            _this.loadPics();
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
            if (this.form.picList != null && this.form.picList.length>0) {
                this.form.picList.forEach(item=>{
                    this.picList.push({
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
                this.picList = [];
            }

            if(this.form.video!=null){
                this.videoList = [
                    {
                        response: {
                            data: {
                                newFile: this.form.video.fileName,
                                url: this.form.video.fileUrl
                            }
                        },
                        name: this.form.video.fileName,
                        url: fs+this.form.video.fileUrl,
                        uid: this.form.video.id
                    }
                ];
            } else {
                this.videoList=[];
            }
        },
        editorReady(instance) {

            if(this.form.remark==null){
                instance.setContent("");
            } else {
                instance.setContent(this.form.remark);
            }


            instance.addListener("blur", () => {
                this.form.remark = instance.getContent();
            });
        },
        onSubmit(){
            let _this = this;
            _this.form.picList = [];
            _this.form.video ={};
            $("input[vd!='']").each(function(){
                $(this).removeClass("red");
            });


            if(this.form.remark.length>1000){
                this.$notify({
                    message: "产品介绍不能超过1000字符！",
                    type: "warning"
                });
                return false;
            }

            if (
                _this.$refs.elUploadPic.uploadFiles == null ||
                _this.$refs.elUploadPic.uploadFiles.length == 0
            ) {
                this.$notify({
                    message: "产品图片未上传！",
                    type: "warning"
                });
                return false;
            }

            if(_this.form.priceList.length>0){
                let flag = _this.form.priceList.findIndex(item=>{
                    return ((item.amount.trim()=='' ||item.amount.length>50) &&
                        (item.price.trim()=='' || item.price.length>50) &&
                        (item.spec.trim()=='' || item.spec.length>50));
                })
                if(flag>=0){
                    flag = '_in_'+flag;
                    $("input[vd$="+flag+"]").each(function(){
                        $(this).addClass("red");
                    });
                    _this.$notify({
                        message: "规格价格未填写！",
                        type: "warning"
                    });
                    return;
                }
            }

            if (
                _this.$refs.elUploadVideo.uploadFiles != null &&
                _this.$refs.elUploadVideo.uploadFiles.length != 0
            ) {
                let _uploadvideo = _this.$refs.elUploadVideo.uploadFiles;
                _this.form.video.fileUrl = _uploadvideo[0].response.data.url;
                _this.form.video.fileName = _uploadvideo[0].response.data.newFile;
            } else {
                _this.form.video = null;
            }

            let _uploadpics = _this.$refs.elUploadPic.uploadFiles;

            _uploadpics.forEach(item=>{
                _this.form.picList.push({fileUrl:item.response.data.url,fileName:item.response.data.newFile})
            });

            _this.$refs["form"].validate((valid) => {
                if (valid) {
                    // console.log('submit',_this.form)
                    axios
                        .post(getCtx()+"/enterprise/product/updateProduct",_this.form)
                        .then(rs => {
                            if(rs.status == 200){
                                if (rs.data.result == 200) {
                                    location.href=getCtx()+'/enterprise/product/list?c=001001';
                                } else if(rs.data.result == 401){
                                    location.href=getCtx()+'/enterprise/expired';
                                } else {
                                    _this.$notify.error({
                                        title: "提示",
                                        message: rs.data.exceptionMsg
                                    });
                                    location.href=getCtx()+'/enterprise/product/list?c=001001';
                                }
                            } else {
                                _this.loading = false;
                                _this.$notify.error({
                                    title: "提示",
                                    message: '服务器错误请与管理员联系'
                                });
                            }
                        }).catch(err => {
                        console.log("api updateProduct error", err);
                        location.href=getCtx()+'/enterprise/product/list?c=001001';
                    });

                } else {
                    return false;
                }
            });
        },
        back(){
            location.href=getCtx()+'/enterprise/product/list?c=001001';
        },
        handleRow(i){
            if(i<0){
                if(this.form.priceList.length<10){
                    this.form.priceList.push({
                        spec:'',
                        price:'',
                        amount:''
                    });
                }
            } else {
                if(this.form.priceList.length>=1){
                    this.form.priceList.splice(i,1);
                }
            }
        },
        handleBeforeUpload(file) {
            const isIMAGE = (file.type === "image/jpeg") || (file.type === "image/gif") || (file.type === "image/png");
            const isLt1M = (file.size / 1024 / 1024) < 1;

            if (!isIMAGE) {
                this.$message.error("上传文件只能是图片格式!");
            }
            if (!isLt1M) {
                this.$message.error("上传文件大小不能超过 1MB!");
            }
            return isIMAGE && isLt1M;
        },
        handlePreview(file) {
        },
        handleExceed(files, picList) {
            this.$notify.warning({
                title: "提示",
                message: `当前限制选择 10 个文件`
            });
        },
        handleSuccess(response, file, picList) {
        },
        beforeRemove(file, picList) {
        },
        handleBeforeUploadVideo(file) {
            const isIMAGE = (file.type === "video/mp4") || (file.type === "video/mpeg");
            const isLt1M = (file.size / 1024 / 1024) < 3;

            if (!isIMAGE) {
                this.$notify.warning({
                    title: "提示",
                    message: `上传文件只能是mp4格式!`
                });
            }
            if (!isLt1M) {
                this.$notify.warning({
                    title: "提示",
                    message: `上传文件大小不能超过 3MB!`
                });
            }
            return isIMAGE && isLt1M;
        },
        beforeRemoveVideo(file, videoList) {
        },
        handleExceedVideo(files, picList) {
            this.$notify.warning({
                title: "提示",
                message: `当前限制选择 1 个文件`
            });
        },
        handleSuccessVideo(response, file, videoList) {
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

