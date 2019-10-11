/**
 * 新闻列表页面
 *
 */
new Vue({
    el: "#newsTable",
    data() {
        return {
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
            let _newsType = $("#newsTable").attr("newsType");


            let params = {
                pageInput: _this.pageInfo,
                sysId: 2,
                newsType: _newsType
            };

            axios
                .post(getCtx()+"/hospital/news/queryNewsPage", params)
                .then(rs => {
                    if(rs.status == 200){
                        if (rs.data.result == 200) {
                            _this.loading = false;
                            _this.pageInfo.total = rs.data.pageInfo.total;
                            _this.dataList = rs.data.page;
                        } else if(rs.data.result == 401){
                            location.href=getCtx()+'/hospital/expired';
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
                    console.log("api news error", err);
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

            if(data.isRed==1){
                return "<a style='color: #f56c6c' href='"+getCtx()+"/hospital/news/detail?id="+data.id+"'>"+data.title+"</a>"
            } else {
                return "<a href='"+getCtx()+"/hospital/news/detail?id="+data.id+"'>"+data.title+"</a>"
            }
        }
    }
});
