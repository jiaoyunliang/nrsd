/**
 * 产品资质详细页面
 * @type {boolean}
 */
Vue.use(VueViewer.default);
const maintab = new Vue({
  el: "#productquadetail",
  components: {},
  data() {
    return {
      dialogTitle: "",
      showDialog: false,
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
      searchParams: {
        orgName: ""
      },
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
        typeFlag: 2,
        quaType: 2, //产品资质
        orgName: this.searchParams.orgName,
        quaCode: this.quaCode //资质类型编码
      };
      this.$http
        .post(getCtx() + "/hospital/qua/productquadetailinfo", params, {
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
      location.href = getCtx() + "/hospital/qua/alllist?c=001005";
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
          .post(getCtx() + "/hospital/qua/deleteproductquadetailinfo", params, {
            Accept: "application/json",
            "Content-Type": "application/json"
          })
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
