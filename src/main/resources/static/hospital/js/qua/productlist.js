/**资质页面
 * @type {boolean}
 */
//资质打包记录
Vue.component("product-qua-list-component", {
  template: "#product-qua-list-template",
  props: {},
  data() {
    return {
      dialogTitle: "",
      showDialog: false, //添加资质窗口默认关闭
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
  isView: function(n, o) {
    if (n === "productQuaList") {
      this.handleSearchData();
    }
  },
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
        pageInput: _this.pageInfo,
        typeFlag: 2,
        quaType: 2
      };
      this.$http
        .post(getCtx() + "/hospital/qua/productquainfo", params, {
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
    handleSearchProductQuaDetail: function(row) {
      location.href =
        getCtx() +
        "/hospital/qua/productquadetail?quaCode=" +
        row.quaCode +
        "&quaName=" +
        row.quaName;
    },
    onRefreshList: function() {
      this.handleSearchData();
    },
    handelAddProductQua: function() {
      let _this = this;
      _this.showDialog = true;
      _this.dialogTitle = "添加产品资质";
    }
  }
});
