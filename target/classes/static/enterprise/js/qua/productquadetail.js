//编辑资质列表
Vue.component("edit-product-qua-component", {
  template: "#edit-product-qua-template",
  props: {
    dialogTitle: String,
    formData: Object,
    formImgs: Array,
    showDialog: Boolean
  },
  data() {
    return {
      dynamicValidateForm: { domains: [] }, //动态生成form
      currentData: {
        id: "",
        isShow: "",
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
      uploadUrl: "",
      imgs: [],
      options: [
        {
          value: 1,
          label: "是"
        },
        {
          value: 0,
          label: "否"
        }
      ],
      loading: false
    };
  },
  created: function() {
    this.uploadUrl = getCtx() + "/enterprise/file/upload";
  },
  mounted: function() {},
  watch: {
    //如果打开窗口为true
    showDialog: function(n, o) {
      if (n == true) {
        this.currentData = this.formData;
        this.imgs = this.formImgs;
        this.selectproductqua();
      }
    }
  },
  methods: {
    handelUploadUrl() {
      return this.uploadUrl;
    },
    selectproductqua() {
      let _this = this;
      let param = {
        quaCode: this.currentData.quaCode
      };
      _this.loading = true;
      this.dynamicValidateForm.domains = [];
      _this.$http
        .post(getCtx() + "/enterprise/qua/dynamicCreateForm", param, {
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
      this.$parent.imgs = [];
      this.$parent.onRefreshList();
      this.$emit("update:showDialog", false);
    },
    tallDataListChanged: function() {
      this.$refs["dynamicValidateForm"].resetFields();
      this.imgs = [];
      this.dialogClosed();
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
    handleEditProductQua: function() {
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
            _this.$refs.elUpload.uploadFiles.forEach(element => {
              if (element.response != undefined) {
                imgstr = imgstr + element.response.data.url + ",";
                imgnames = imgnames + element.response.data.oldFile + ",";
              } else {
                imgstr = imgstr + element.pathurl + ",";
                imgnames = imgnames + element.name + ",";
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
                getCtx() + "/enterprise/qua/upadteproducequadetailinfo",
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
                console.log("api upadteproducequadetailinfo error", err);
              });
          }
        } else {
          return false;
        }
      });
    }
  }
});
/**
 * 查询产品资质图片
 **/
Vue.component("watch-qua-image-component", {
  template: "#watch-qua-image-template",
  props: {
    imageDialogTitle: String,
    imageShowDialog: Boolean,
    imgs: Array
  },
  data() {
    return {
      loading: false
    };
  },
  created: function() {},
  mounted: function() {},
  watch: {},
  methods: {
    dialogClosed: function() {
      this.$parent.imgs = [];
      this.$emit("update:imageShowDialog", false);
    },
    tallDataListChanged: function() {
      this.dialogClosed();
    }
  }
});

/**
 * 产品资质页面
 * @type {boolean}
 */
Vue.use(VueViewer.default);
const maintab = new Vue({
  el: "#productquadetail",
  components: {},
  data() {
    return {
      dialogTitle: "",
      showDialog: false, //编辑资质窗口默认关闭
      imageShowDialog: false,
      imageDialogTitle: "",
      loading: false,
      quaCode: "", //资质编号（字典）
      url: "",
      pageInfo: {
        total: 1,
        current: 1,
        size: 10,
        sort: ""
      },
      currentData: {},
      imgs: [],
      imgStr: "",
      imgNames: "",
      tableData: [],
      tableLabel: [],
      dataList: []
    };
  },
  created: function() {},
  mounted: function() {
    this.initParam();
    this.handleSearchData();
  },
  watch: {},
  methods: {
    initParam: function() {
      this.quaCode = this.$refs.quaCodeInput.value;
      this.url = this.$refs.urlInput.value;
    },
    handleSearchData() {
      this.pageInfo.current = 1;
      this.pageInfo.size = 10;
      this.pageInfo.sort = "";
      this.fetchData();
    },
    fetchData() {
      let _this = this;
      _this.loading = true;
      let params = {
        pageInput: _this.pageInfo,
        quaCode: this.quaCode //资质类型编码
      };
      this.$http
        .post(getCtx() + "/enterprise/qua/productquadetailinfo", params, {
          Accept: "application/json",
          "Content-Type": "application/json"
        })
        .then(rs => {
          if (rs.data.result == 200) {
            _this.loading = false;
            _this.pageInfo.total = rs.data.pageInfo.total;
            _this.tableData = rs.data.page.tableData;
            _this.tableLabel = rs.data.page.tableLabel;
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
          console.log("api productquadetailinfo error", err);
        });
    },
    dateFormat: function(row, column) {
      let value = row.validDate;
      return value ? this.$moment(value).format("YYYY-MM-DD") : null;
    },
    handleClick(tab, event) {},
    changePageSize: function(pageSize) {
      this.pageInfo.size = pageSize;
      this.fetchData();
    },
    pageIndexChange: function(pageIndex) {
      this.pageInfo.current = pageIndex;
      this.fetchData();
    },
    typeIndex: function(index) {
      return index + (this.pageInfo.current - 1) * this.pageInfo.size + 1;
    },
    onRefreshList: function() {
      this.fetchData();
    },
    foreachFile: function() {
      if (this.imgStr != undefined) {
        let imgStr = this.imgStr.split(",");
        let imgName = this.imgNames.split(",");
        for (var i = 0; i < imgStr.length; i++) {
          if (imgStr[i] != "") {
            let url = imgStr[i];
            let name = imgName[i];
            let file = {};
            file.url = this.url + url;
            file.title = name;
            file.name = name;
            file.pathurl = url;
            this.imgs.push(file);
          }
        }
      }
    },
    onback: function() {
      location.href = getCtx() + "/enterprise/qua/productlist?c=001004";
    },
    handleSearchQuaImage: function(row) {
      let _this = this;
      _this.imgStr = row.imgstr;
      _this.imgNames = row.imgNames;
      _this.foreachFile();
      _this.imageShowDialog = true;
      _this.imageDialogTitle = "资质图片查看";
    },
    handleEditProductQua: function(row) {
      let _this = this;
      _this.currentData = row;
      _this.imgStr = row.imgstr;
      _this.imgNames = row.imgNames;
      _this.foreachFile();
      _this.showDialog = true;
      _this.dialogTitle = "编辑产品资质";
    },
    handleDeleteProductQua: function(quaid) {
      let _this = this;
      let params = {
        id: quaid
      };
      if (confirm("确认是否删除？")) {
        this.$http
          .post(
            getCtx() + "/enterprise/qua/deleteproductquadetailinfo",
            params,
            {
              Accept: "application/json",
              "Content-Type": "application/json"
            }
          )
          .then(rs => {
            if (rs.data.result == 200) {
              _this.$notify.success({
                title: "提示",
                message: rs.data.message
              });
              this.handleSearchData();
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
            console.log("api deleteproductquadetailinfo error", err);
          });
      }
    }
  }
});
