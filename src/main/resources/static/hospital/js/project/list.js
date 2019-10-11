/**
 * 中标项目管理页面
 * @type {boolean}
 */
var list = new Vue({
  el: "#hostitallist",
  data() {
    return {
      msg: "",
      dialogTitle: "",
      isShowDialog: false,
      isShowEditDialog: false,
      loading: false,
      searchParams: {
        projectName: "",
        projectType: "1"
      },
      pageInfo: {
        total: 1,
        current: 1,
        size: 10,
        sort: ""
      },
      dataList: [],
      editAccount: {}
    };
  },
  created: function() {
    console.log("竟来了啊");
  },
  mounted: function() {},
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
        projectType: 1,
        projectName: _this.searchParams.projectName
      };
      _this.axios
        .post("/hospital/project/listinfo", params)
        .then(rs => {
          if (rs.result === 200) {
            _this.loading = false;
            _this.pageInfo.total = rs.pageInfo.total;
            _this.dataList = rs.page;
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
          console.log("api projectlist error", err);
        });
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
    handleAddProject: function() {
      let _this = this;
      _this.isShowDialog = true;
      _this.dialogTitle = "添加项目";
    },
    handleEditAccount: function(projectInfo) {},
    onRefresList: function() {
      this.fetchData();
    },
    handleDeleteProject: function(projectInfo) {}
  }
});
