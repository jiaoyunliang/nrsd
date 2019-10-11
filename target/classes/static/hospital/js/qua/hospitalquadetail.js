/**
 * 院内资质详细页面
 * @type {boolean}
 */
Vue.use(VueViewer.default);
const maintab = new Vue({
  el: "#hosptialquadetail",
  components: {},
  data() {
    return {
      imageShowDialog: false,
      imageDialogTitle: "",
      showDialog: false,
      dialogTitle: "",
      url: "",
      quaType: "", //字典
      hospitalDetailInfo: {},
      searchParams: {
        orgName: ""
      },
      imgs: [],
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
    this.inintParam();
    this.handleSearchData();
  },
  watch: {},
  methods: {
    inintParam: function() {
      this.quaType = this.$refs.quaTypeInput.value;
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
        quaType: _this.quaType,
        typeFlag: 2,
        orgName: _this.searchParams.orgName
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
          console.log("api hospitalquadetailinfo error", err);
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
    foreachFile: function(filesRelation) {
      if (filesRelation != "undefined") {
        filesRelation.forEach(element => {
          let file = {};
          file.url = this.url + element.fileUrl;
          file.title = element.fileName;
          this.imgs.push(file);
        });
      }
    },
    onRefreshList: function() {
      this.fetchData();
    },
    handleSearchQuaImage: function(fileRelation) {
      let _this = this;
      _this.foreachFile(fileRelation);
      _this.imageShowDialog = true;
      _this.imageDialogTitle = "资质图片查看";
    },
    handleEditHospitalQua: function(row) {
      let _this = this;
      _this.hospitalDetailInfo = row;
      _this.showDialog = true;
      _this.dialogTitle = "编辑企业资质";
    },
    onback: function() {
      location.href = getCtx() + "/hospital/qua/alllist?c=001005";
    },
    handelDeleteHospitalQua: function(id) {
      let _this = this;
      _this.loading = true;
      let params = {
        id: id
      };
      if (confirm("确认是否删除？")) {
        this.$http
          .post(
            getCtx() + "/hospital/qua/deletehospitalquadetailinfo",
            params,
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
              _this.handleSearchData();
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
            console.log("api deletehospitalquadetailinfo error", err);
          });
      }
    }
  }
});
