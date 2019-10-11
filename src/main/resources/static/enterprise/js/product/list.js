/**
 * 产品列表页面
 *
 */
new Vue({
    el: "#productTable",
    data() {
        return {
            loading: false,
            pageInfo: {
                total: 1,
                current: 1,
                size: 10,
                sort: ""
            },
            searchParams: {
                productTypeName: "",
                productName: ""
            },
            dataList: [],
            fileServer:""
        };
    },
    created: function() {
        this.fetchData();
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
                productTypeName: _this.searchParams.productTypeName,
                productName: _this.searchParams.productName
            };

            axios
                .post(getCtx()+"/enterprise/product/queryProductList", params)
                .then(rs => {
                    if(rs.status == 200){
                        if (rs.data.result == 200) {
                            _this.loading = false;
                            _this.pageInfo.total = rs.data.pageInfo.total;
                            _this.dataList = rs.data.page;
                            _this.fileServer = rs.data.fileServer;
                        } else if(rs.data.result == 401){
                            location.href=getCtx()+'/enterprise/expired';
                        } else {
                            _this.loading = false;
                            _this.$notify.error({
                                title: "提示",
                                message: rs.data.exceptionMsg
                            });
                        }
                    } else {
                        _this.loading = false;
                        _this.$notify.error({
                            title: "提示",
                            message: '服务器错误请与管理员联系'
                        });
                    }
                }).catch(err => {
                _this.loading = false;
                console.log("api queryProductList error", err);
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
        viewHref:function (data) {
            return "<a href='"+getCtx()+"/enterprise/product/view?c=001001&id="+data.id+"'>查看</a>"
        },
        editHref:function (data) {
            return "<a href='"+getCtx()+"/enterprise/product/edit?c=001001&id="+data.id+"'>修改</a>"
        },
        openAddProduct:function () {
            location.href = getCtx()+'/enterprise/product/add?c=001001';
        }
    }
});
