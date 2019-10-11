/**
 * 中标项目管理页面
 * @type {boolean}
 */
const maintab = new Vue({
  el: "#app",
  components: {},
  data() {
    return {
      activeName: "",
      loading: false,
      msg: "省标结果",
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
      dataList: []
    };
  },
  created: function() {
    this.activeName = "hospitalProject";
    this.handleSearchData(1);
  },
  mounted: function() {},
  methods: {
    handleSearchData() {
      this.pageInfo.current = 1;
      this.pageInfo.size = 10;
      this.pageInfo.sort = "";
      this.fetchData();
    },
    checklisttype() {
      if (this.searchParams.projectType == 1) {
        return false;
      } else {
        return true;
      }
    },
    fetchData() {
      let _this = this;
      _this.loading = true;
      let params = {
        pageInput: _this.pageInfo,
        projectType: _this.searchParams.projectType,
        projectName: _this.searchParams.projectName
      };
      console.log(JSON.stringify(params));
      this.$http
        .post(getCtx() + "/hospital/project/listinfo", params, {
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
          console.log("api projectlist error", err);
        });
    },
    handleClick(tab, event) {
      if ("allProject" == tab.name) {
        this.activeNames = "allProject";
        this.searchParams.projectType = 2;
        this.handleSearchData();
      } else if ("hospitalProject" == tab.name) {
        this.searchParams.projectType = 1;
        this.handleSearchData();
      }
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
    handleAddProject: function() {},
    handleEditAccount: function(projectInfo) {},
    onRefresList: function() {
      this.fetchData();
    },
    handleSearchBidDetailDetail: function(row) {
      location.href = getCtx() + "/hospital/bid/list?projectId=" + row.id;
    },
    handleDeleteProject: function(projectInfo) {},
    //时间格式化
    dateFormat: function(row, column) {
      let value = row.projectTime;
      return value ? this.$moment(value).format("YYYY-MM-DD") : null;
    }
  }
});
