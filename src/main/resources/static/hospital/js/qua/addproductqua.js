//添加资质列表
Vue.component("add-product-qua-component", {
  template: "#add-product-qua-template",
  props: {
    dialogTitle: String,
    showDialog: Boolean,
    refreshList: Function
  },
  data() {
    return {
      dynamicValidateForm: { domains: [] }, //动态生成form
      currentData: {
        id: "",
        orgName: "",
        validDate: "",
        quaCode: "",
        quaValue: "",
        quaName: "",
        item1: "",
        item2: "",
        item3: "",
        item4: "",
        item5: ""
      },
      imgs: [],
      uploadUrl: "",
      dictInfoGroupOptions: [], //资质类型字典
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
        this.handleSearchData();
      }
    }
  },
  methods: {
    handleSearchData() {
      this.fetchData();
    },
    fetchData: function() {
      //查询企业资质类型字典
      let _this = this;
      let param = {
        dictCategoryId: 3
      };
      _this.$http
        .post(getCtx() + "/hospital/search/dictInfo", param)
        .then(rs => {
          if (rs.data.result === 200) {
            _this.dictInfoGroupOptions = rs.data.data;
          } else {
            _this.loading = false;
            _this.$notify.error({
              title: "提示",
              message: "读取数据错误"
            });
          }
        })
        .catch(err => {
          console.log("searchdictInfo error", err);
        });
    },
    selectproductqua() {
      let _this = this;
      let param = {
        quaCode: this.currentData.quaCode
      };
      _this.loading = true;
      this.dynamicValidateForm.domains = [];
      _this.$http
        .post(getCtx() + "/hospital/qua/dynamicCreateForm", param, {
          Accept: "application/json",
          "Content-Type": "application/json"
        })
        .then(rs => {
          if (rs.data.result == 200) {
            _this.loading = false;
            rs.data.formItem.formItem.forEach(element => {
              let obj = {};
              obj.label = element.label;
              obj.prop = element.prop;
              obj.type = element.type;
              this.dynamicValidateForm.domains.push(obj);
            });
            _this.quashowDialog = true;
          } else {
            _this.loading = false;
            _this.$notify.error({
              title: "提示",
              message: rs.message
            });
          }
        })
        .catch(err => {
          _this.loading = false;
          console.log("api dynamicCreateForm error", err);
        });
    },
    dialogClosed: function() {
      this.$emit("update:showDialog", false);
    },
    tallDataListChanged: function() {
      this.$refs["dynamicValidateForm"].resetFields();
      this.imgs = [];
      this.refreshList();
      this.dialogClosed();
    },
    handelUploadUrl() {
      return this.uploadUrl;
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
    handleSaveProductQua: function() {
      let _this = this;
      this.$refs["dynamicValidateForm"].validate(valid => {
        if (valid) {
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
          if (confirm("确认提交?")) {
            let imgstr = "";
            let imgnames = "";
            console.log(
              "所有的" + JSON.stringify(_this.$refs.elUpload.uploadFiles)
            );
            _this.$refs.elUpload.uploadFiles.forEach(element => {
              if (element.response != undefined) {
                imgstr = imgstr + element.response.data.url + ",";
                imgnames = imgnames + element.response.data.oldFile + ",";
              }
            });
            let quaValue = {};
            _this.dynamicValidateForm.domains.forEach(element => {
              let prop = element.prop;
              let value = element.value;
              if (prop == "validDate") {
                quaValue[prop] = _this.dateFormat(_this.currentData[prop]);
              } else {
                quaValue[prop] = _this.currentData[prop];
              }
            });
            quaValue.imgstr = imgstr;
            quaValue.imgNames = imgnames;
            _this.currentData.quaValue = JSON.stringify(quaValue);
            _this.$http
              .post(
                getCtx() + "/hospital/qua/insertproducequadetailinfo",
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
                console.log("api insertproducequadetailinfo error", err);
              });
          }
        } else {
          return false;
        }
      });
    }
  }
});
