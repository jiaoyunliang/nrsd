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
                size: 20,
                sort: ""
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
            let _productType = $("#productTable").attr("productTypeId");
            let _productName = $("#productTable").attr("productName");

            let params = {
                pageInput: _this.pageInfo,
                productTypeId: _productType,
                productName: _productName
            };

            axios
                .post(getCtx()+"/hospital/product/queryProductList", params)
                .then(rs => {
                    if(rs.status == 200){
                        if (rs.data.result == 200) {
                            _this.loading = false;
                            _this.pageInfo.total = rs.data.pageInfo.total;
                            _this.dataList = rs.data.page;
                            _this.fileServer = rs.data.fileServer;
                        } else {
                            _this.loading = false;
                            _this.$notify.error({
                                title: "提示",
                                message: rs.message
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
        newsHref:function (data) {
            return "<a href='"+getCtx()+"/hospital/product/view?id="+data.id+"'>"+data.productName+"</a>"
        }
    }
});
