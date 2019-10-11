//添加资质列表
Vue.component("add-qua-component", {
  template: "#add-hospital-qua-template",
  props: {
    dialogTitle: String,
    showDialog: Boolean
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
      options: [
        {
          value: "1",
          label: "是"
        },
        {
          value: "0",
          label: "否"
        }
      ],
      rules: {
        isShow: [
          { required: true, message: "请选择是否可见", trigger: "change" }
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
      dictInfoGroupOptions: [], //资质类型字典
      imgs: [],
      uploadUrl: "",
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
        this.handleSearchData();
      }
    }
  },
  methods: {
    handleSearchData() {
      this.fetchData();
    },
    handelUploadUrl() {
      return this.uploadUrl;
    },
    fetchData: function() {
      //查询企业资质类型字典
      let _this = this;
      let param = {
        dictCategoryId: 2
      };
      _this.$http
        .post(getCtx() + "/enterprise/search/dictInfo", param)
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
    dialogClosed: function() {
      this.$parent.onRefreshList();
      this.$emit("update:showDialog", false);
    },
    tallDataListChanged: function() {
      this.$refs["formObject"].resetFields();
      this.currentData.bnzFileRelation = [];
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
    handelSaveHospitalQua: function() {
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
            _this.$refs.elUpload.uploadFiles.forEach(element => {
              let file = {};
              if (element.response != undefined) {
                file.fileUrl = element.response.data.url;
                file.fileName = element.response.data.oldFile;
              }
              _this.currentData.bnzFileRelation.push(file);
            });
            _this.$http
              .post(
                getCtx() + "/enterprise/qua/inserthospitalquadetailinfo",
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
                console.log("api inserthospitalquadetailinfo error", err);
              });
          }
        } else {
          return false;
        }
      });
    }
  }
});

//资质打包
Vue.component("dopack-qua-package-component", {
  template: "#dopack-qua-package-template",
  props: {
    dialogTitle: String,
    showDialog: Boolean
  },
  data() {
    return {
      packDataState: []
    };
  },
  mounted: function() {},
  created: function() {},
  watch: {
    //如果打开窗口为true
    showDialog: function(n, o) {
      if (n == true) {
        this.fetchData();
      }
    }
  },
  methods: {
    proving1: function(e) {
      let boolean = new RegExp("^[1-9][0-9]*$").test(e.target.value);
      if (!boolean) {
        this.$message.warning("请输入正整数");
        e.target.value = "";
      }
    },
    dialogClosed: function() {
      this.$emit("update:showDialog", false);
    },
    tallDataListChanged: function() {
      this.packDataState = [];
      this.dialogClosed();
    },
    doPack: function() {
      let _this = this;
      this.loading = true;
      if (confirm("确认提交?")) {
        _this.$http
          .post(getCtx() + "/enterprise/qua/doPackage", _this.packDataState, {
            Accept: "application/json",
            "Content-Type": "application/json"
          })
          .then(rs => {
            if (rs.data.result == 200) {
              _this.loading = false;
              _this.$notify.success({
                title: "提示",
                message: rs.data.message
              });
              this.tallDataListChanged();
              let url = rs.data.url;
              window.open(url);
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
            console.log("api doPackage error", err);
          });
      }
    },
    fetchData: function() {
      //查询企业资质类型字典
      let _this = this;
      _this.$http
        .post(getCtx() + "/enterprise/qua/packageListState", {})
        .then(rs => {
          if (rs.data.result === 200) {
            _this.packDataState = rs.data.data;
          } else {
            _this.loading = false;
            _this.$notify.error({
              title: "提示",
              message: "读取数据错误"
            });
          }
        })
        .catch(err => {
          console.log("packageListState error", err);
        });
    }
  }
});

//资质打包记录
Vue.component("qua-packagerecord-component", {
  template: "#hospital-qua-packagerecord-template",
  props: {
    dialogTitle: String,
    showDialog: Boolean
  },
  data() {
    return {
      pageInfo: {
        total: 1,
        current: 1,
        size: 10,
        sort: ""
      },
      dataList: []
    };
  },
  mounted: function() {},
  created: function() {},
  watch: {
    //如果打开窗口为true
    showDialog: function(n, o) {
      if (n == true) {
        this.fetchData();
      }
    }
  },
  methods: {
    formatType: function(row, column) {
      if (row.type == 1) {
        return "企业资质";
      } else if (row.type == 2) {
        return "产品资质";
      }
    },
    dialogClosed: function() {
      this.$emit("update:showDialog", false);
    },
    tallDataListChanged: function() {
      this.dataList = [];
      this.dialogClosed();
    },
    doPack: function() {},
    handleSearchData() {
      this.pageInfo.current = 1;
      this.pageInfo.size = 10;
      this.pageInfo.sort = "";
      this.fetchData();
    },
    handleDownLoad: function(row) {
      window.open(row.packageUrl);
    },
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
    fetchData() {
      let _this = this;
      let params = {
        pageInput: _this.pageInfo
      };
      _this.loading = true;
      this.$http
        .post(getCtx() + "/enterprise/qua/packageList", params, {
          Accept: "application/json",
          "Content-Type": "application/json"
        })
        .then(rs => {
          if (rs.data.result == 200) {
            _this.loading = false;
            _this.pageInfo.total = rs.data.pageInfo.total;
            _this.dataList = rs.data.page;
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
          console.log("api packageList error", err);
        });
    }
  }
});

/**资质页面
 * @type {boolean}
 */
Vue.use(VueViewer.default);
const maintab = new Vue({
  el: "#hosptiallist",
  components: {},
  data() {
    return {
      dialogTitle: "",
      showDialog: false, //添加资质窗口默认关闭
      packDialogTitle: "",
      packShowDialog: false,
      packRecordDialogTitle: "",
      packRecordShowDialog: false,
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
  created: function() {
    this.handleSearchData();
  },
  mounted: function() {},
  watch: {},
  methods: {
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
        pageInput: _this.pageInfo
      };
      this.$http
        .post(getCtx() + "/enterprise/qua/hospitalquainfo", params, {
          Accept: "application/json",
          "Content-Type": "application/json"
        })
        .then(rs => {
          if (rs.data.result == 200) {
            _this.loading = false;
            _this.pageInfo.total = rs.data.pageInfo.total;
            _this.dataList = rs.data.page;
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
          console.log("api hospitalquainfo error", err);
        });
    },
    dateFormat: function(row, column) {
      let value = row.updateTime;
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
    handleSearchDetail: function(row) {
      location.href =
        getCtx() +
        "/enterprise/qua/quadetaillist?quaType=" +
        row.quaType +
        "&quaName=" +
        row.quaName;
    },
    handleAddHospitalQua: function() {
      let _this = this;
      _this.showDialog = true;
      _this.dialogTitle = "添加企业资质";
    },
    handleSearchPackageRecord: function() {
      let _this = this;
      _this.packRecordShowDialog = true;
      _this.packRecordDialogTitle = "打包记录";
    },
    handlePackage: function() {
      let _this = this;
      _this.packShowDialog = true;
      _this.packDialogTitle = "资质打包";
    }
  }
});
