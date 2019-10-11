/**
 * 院内资质详细页面
 * @type {boolean}
 */
const maintab = new Vue({
  el: "#app",
  components: {},
  data() {
    return {
      loading: false,
      pageInfo: {
        total: 1,
        current: 1,
        size: 10,
        sort: ""
      },
      tableData: [],
      cols: [
        { prop: "bidNumber", label: "标书号" },
        { prop: "bidOrgName", label: "投标企业" },
        { prop: "bidUserName", label: "投标用户" },
        { prop: "ztItemId", label: "组套商品编号" },
        { prop: "ztItemName", label: "组套商品名称" },
        { prop: "projectCategoryId", label: "项目分类编码" },
        { prop: "projectCategoryName", label: "项目分类名称" },
        { prop: "projectCatalogueId", label: "项目目录编码" },
        { prop: "projectCatalogueName", label: "项目目录名称" },
        { prop: "ztItemSpec", label: "组套商品规格" },
        { prop: "ztItemModel", label: "组套商品型号" },
        { prop: "ztRegisterId", label: "组套注册证编号" },
        { prop: "ztRegisterSpec", label: "组套注册证规格" },
        { prop: "ztRegisterModel", label: "组套注册证型号" },
        { prop: "ztItemBrand", label: "组套商品商标" },
        { prop: "ztItemMinNumberUnit", label: "组套商品最小计量单位" },
        { prop: "ztItemPackSpec", label: "组套商品包装规格" },
        { prop: "ztItemPackMaterial", label: "组套商品包装材质" },
        { prop: "ztItemPerformace", label: "组套商品性能组成" },
        { prop: "ztItemScope", label: "组套商品适用范围" },
        { prop: "ztDataCheckState", label: "组套数据审核状态" },
        { prop: "ztCatalogueCheckState", label: "组套目录审核状态" },
        { prop: "composeNumber", label: "构成数量" },
        { prop: "itemBasePrice", label: "商品基准价" },
        { prop: "itemQuote", label: "商品报价" }
      ],
      projectId: "",
      variableCols: []
    };
  },
  created: function() {},
  mounted: function() {
    this.inintParam();
    this.init();
  },
  watch: {},
  methods: {
    inintParam: function() {
      this.projectId = this.$refs.projectIdInput.value;
    },
    handleSearchData() {
      this.pageInfo.current = 1;
      this.pageInfo.size = 10;
      this.pageInfo.sort = "";
      this.fetchData();
    },
    init() {
      let params = { projectId: this.projectId };
      let _this = this;
      this.$http
        .post(getCtx() + "/hospital/bid/showcolumn", params, {
          Accept: "application/json",
          "Content-Type": "application/json"
        })
        .then(rs => {
          if (rs.data.result === 200) {
            rs.data.data.forEach(x => {
              _this.cols.push({ prop: x.columnKey, label: x.columnName });
            });
            _this.handleSearchData();
          } else {
            _this.$notify.error({
              title: "提示",
              message: rs.message
            });
          }
        })
        .catch(err => {
          console.error("api showcolumn error", err);
        });
    },
    fetchData() {
      let _this = this;
      let param = {
        pageInput: _this.pageInfo,
        projectId: _this.projectId
      };
      _this.loading = true;
      this.$http
        .post(getCtx() + "/hospital/bid/queryBidDetailPage", param, {
          Accept: "application/json",
          "Content-Type": "application/json"
        })
        .then(rs => {
          _this.loading = false;
          if (rs.data.result === 200) {
            _this.$notify.success({
              title: "提示",
              message: rs.message
            });
            _this.pageInfo.total = rs.data.pageInfo.total;
            _this.tableData = rs.data.page;
          } else {
            _this.$notify.error({
              title: "提示",
              message: rs.message
            });
          }
        })
        .catch(err => {
          _this.loading = false;
          console.error("api queryBidDetailPage error", err);
        });
    },
    flexColumnWidth(col) {
      let flexWidth = 0;
      for (const char of col.label) {
        if ((char >= "A" && char <= "Z") || (char >= "a" && char <= "z")) {
          // 如果是英文字符，为字符分配8个单位宽度
          flexWidth += 8;
        } else if (char >= "\u4e00" && char <= "\u9fa5") {
          // 如果是中文字符，为字符分配20个单位宽度
          flexWidth += 24;
        } else {
          // 其他种类字符，为字符分配5个单位宽度
          flexWidth += 5;
        }
      }
      if (flexWidth < 50) {
        // 设置最小宽度
        flexWidth = 200;
      }
      if (flexWidth > 250) {
        // 设置最大宽度
        flexWidth = 250;
      }
      return flexWidth + "px";
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
    onback: function() {
      location.href = getCtx() + "/hospital/project/list?c=001004";
    }
  }
});
