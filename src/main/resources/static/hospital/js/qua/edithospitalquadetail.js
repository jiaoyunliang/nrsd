//编辑企业资质列表
Vue.component("edit-hospital-qua-detail-component", {
  template: "#edit-hospital-qua-detail-template",
  props: {
    dialogTitle: String,
    showDialog: Boolean,
    url: String,
    hospitalDetailInfo: Object,
    refreshList: Function
  },
  data() {
    return {
      currentData: {
        quaType: "",
        quaName: "",
        orgName: "",
        isShow: "",
        validityDate: "",
        bnzFileRelation: []
      },
      rules: {
        orgName: [
          { required: true, message: "请输入企业名称", trigger: "change" },
          { max: 100, message: "长度 100 个字符以内", trigger: "blur" }
        ],
        quaType: [{ required: true, message: "请选择分组", trigger: "change" }],
        quaName: [
          { required: true, message: "请输入资质名称", trigger: "blur" },
          { max: 100, message: "长度 100 个字符以内", trigger: "blur" }
        ],
        validityDate: [
          { required: true, message: "请输入有效期", trigger: "blur" }
        ]
      },
      imgs: [],
      uploadUrl: "",
      loading: false
    };
  },
  created: function() {
    this.uploadUrl = getCtx() + "/hospital/file/upload";
  },
  mounted: function() {},
  watch: {
    //如果打开窗口为true
    showDialog: function(n, o) {
      if (n == true) {
        this.currentData = this.hospitalDetailInfo;
        this.foreachFile();
      }
    }
  },
  methods: {
    handleSearchData() {},
    handelUploadUrl() {
      return this.uploadUrl;
    },
    dialogClosed: function() {
      this.$emit("update:hospitalDetailInfo", {});
      this.$emit("update:showDialog", false);
    },
    tallDataListChanged: function() {
      this.$refs["formObject"].resetFields();
      this.currentData.bnzFileRelation = [];
      this.imgs = [];
      this.dialogClosed();
      this.refreshList();
    },
    handleBeforeUpload(file) {
      const isIMAGE =
        file.type === "image/jpeg" ||
        file.type === "image/gif" ||
        file.type === "image/png";
      const isLt1M = file.size / 1024 / 1024 < 5;
      if (!isIMAGE) {
        this.$message.error("上传文件只能是图片格式!");
      }
      if (!isLt1M) {
        this.$message.error("上传文件大小不能超过 5MB!");
      }
      return isIMAGE && isLt1M;
    },
    handlePreview(file) {},
    handleExceed(files, fileList) {
      this.$notify.warning({
        title: "提示",
        message: `当前限制选择 5 个文件`
      });
    },
    handleSuccess(response, file, fileList) {},
    beforeRemove(file, fileList) {
      return this.$confirm(`确定移除 ${file.name}？`);
    },
    afterRemove(file, fileList) {},
    foreachFile: function() {
      if (this.hospitalDetailInfo.bnzFileRelation != "undefined") {
        this.hospitalDetailInfo.bnzFileRelation.forEach(element => {
          let file = {};
          file.url = this.url + element.fileUrl;
          file.title = element.fileName;
          file.name = element.fileName;
          file.pathurl = element.fileUrl;
          this.imgs.push(file);
        });
      }
    },
    handleEditHospitalQua: function() {
      let _this = this;
      _this.loading = true;
      if (
        _this.$refs.elUpload.uploadFiles == null ||
        _this.$refs.elUpload.uploadFiles.length == 0
      ) {
        this.$notify({
          message: "文件未上传！",
          type: "warning"
        });
        return false;
      }
      _this.$refs["formObject"].validate(valid => {
        if (valid) {
          if (confirm("确认提交?")) {
            _this.currentData.bnzFileRelation = [];
            _this.$refs.elUpload.uploadFiles.forEach(element => {
              let file = {};
              if (element.response != undefined) {
                file.fileUrl = element.response.data.url;
                file.fileName = element.response.data.oldFile;
              } else {
                file.fileUrl = element.pathurl;
                file.fileName = element.name;
              }
              _this.currentData.bnzFileRelation.push(file);
            });
            _this.$http
              .post(
                getCtx() + "/hospital/qua/upadtehospitalquadetailinfo",
                this.currentData,
                {
                  Accept: "application/json",
                  "Content-Type": "application/json"
                }
              )
              .then(rs => {
                if (rs.data.result == 200) {
                  _this.loading = false;
                  _this.$notify.success({
                    title: "提示",
                    message: rs.data.message
                  });
                  _this.tallDataListChanged();
                } else {
                  _this.loading = false;
                  _this.$notify.error({
                    title: "提示",
                    message: rs.data.message
                  });
                }
              })
              .catch(err => {
                _this.loading = false;
                console.log("api upadtehospitalquadetailinfo error", err);
              });
          }
        } else {
          return false;
        }
      });
    }
  }
});
