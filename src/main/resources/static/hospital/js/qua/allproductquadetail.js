/**
 * 产品资质页面
 * @type {boolean}
 */
Vue.use(VueViewer.default);
const maintab = new Vue({
  el: "#productapp",
  components: {},
  data() {
    return {
      isShowDialog: "", //是否显示查看资质图片
      imgs: [],
      action: "",
      imgStr: "", //资质图片附件信息
      imgNames: "",
      quaCode: "",
      url: "",
      orgInfoId: "",
      imageDialogTitle: "",
      loading: false,
      pageInfo: {
        total: 1,
        current: 1,
        size: 10,
        sort: ""
      },
      tableData: [],
      tableLabel: [],
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
      this.quaCode = this.$refs.quaCodeInput.value;
      this.url = this.$refs.urlInput.value;
      this.orgInfoId = this.$refs.orgInfoIdInput.value;
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
        typeFlag: 1, //企业端维护
        orgInfoId: this.orgInfoId, //企业编号
        quaCode: this.quaCode, //资质类型编码
        quaType: 2 //产品资质
      };
      this.$http
        .post(getCtx() + "/hospital/qua/allproductquadetailinfo", params, {
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
          console.log("api productquadetail error", err);
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
      if (this.imgStr != undefined) {
        let img = this.imgStr.split(",");
        let imgName = this.imgNames.split(",");
        for (var i = 0; i < img.length; i++) {
          if (img[i] != "") {
            let url = img[i];
            let name = imgName[i];
            let file = {};
            file.url = this.url + url;
            file.title = name;
            this.imgs.push(file);
          }
        }
      }
    },
    onRefresList: function() {
      this.fetchData();
    },
    handleSearchQuaImage: function(row) {
      let _this = this;
      this.imgStr = row.imgstr;
      this.imgNames = row.imgNames;
      _this.isShowDialog = true;
      _this.imageDialogTitle = "资质图片查看";
    }
  }
});
