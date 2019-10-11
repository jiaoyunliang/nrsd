/**
 * 院内资质页面
 * @type {boolean}
 */
Vue.use(VueViewer.default);
const maintab = new Vue({
  el: "#allhosptialquadetail",
  components: {},
  data() {
    return {
      isShowDialog: "", //是否显示查看资质图片
      imgs: [],
      action: "",
      filesRelation: [], //资质图片附件信息
      imageDialogTitle: "",
      quaType: "",
      orgInfoId: "",
      url: "",
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
    this.imageDialogTitle = "查看企业资质";
    this.isShowDialog = false;
    this.inintParam();
    this.handleSearchData();
  },
  watch: {
    isShowDialog(val) {
      if (this.isShowDialog == true) {
        this.foreachFile();
      }
    }
  },
  methods: {
    inintParam: function() {
      this.quaType = this.$refs.quaTypeInput.value;
      this.orgInfoId = this.$refs.orgInfoInput.value;
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
        typeFlag: 1,
        orgInfoId: _this.orgInfoId,
        quaType: _this.quaType
      };
      this.$http
        .post(getCtx() + "/hospital/qua/hospitalquadetailinfo", params, {
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
          console.log("api quadetaillist error", err);
        });
    },
    dateFormat: function(row, column) {
      let value = row.validityDate;
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
    dialogClosed: function() {
      //查看资质图片窗口关闭
      this.imgs = [];
      this.isShowDialog = false;
    },
    tallDataListChanged: function() {
      //关闭资质查看图片弹出窗口
      this.dialogClosed();
      this.onRefresList();
    },
    foreachFile: function() {
      if (this.filesRelation != "undefined") {
        this.filesRelation.forEach(element => {
          let file = {};
          file.url = this.url + element.fileUrl;
          file.title = element.fileName;
          file.name = element.fileName;
          file.pathurl = element.fileUrl;
          this.imgs.push(file);
        });
      }
    },
    onRefresList: function() {
      this.fetchData();
    },
    handleSearchQuaImage: function(fileRelation) {
      let _this = this;
      _this.filesRelation = fileRelation;
      _this.isShowDialog = true;
      _this.imageDialogTitleName = "资质图片查看";
    }
  }
});
