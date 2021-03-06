class WaterFall {

    constructor(target,pageSize,currentPage) {
        this.$target = target;
        this.size = pageSize;
        this.current = currentPage;
        this.isFirst = true;
        this.isDataArrive = true;
        this.colHeight = [];
        this.total = 1;
        this.start();
    }

    listenerScroll(){
        var _this = this
        $(window).on('scroll',function(){
            if(!_this.isDataArrive){
                return
            }
            if(_this.isVisible(_this.$target)){  //判断 'id = load' 是否进入视野
                if(Math.ceil(_this.total/_this.size)>=_this.current){
                    _this.start();
                }
            }
        })
    }

    getCtx() {
        return $("title").attr("name")
    }

    getUrlParam(name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
        var r = window.location.search.substr(1).match(reg);  //匹配目标参数
        if (r != null) return unescape(r[2]); return null; //返回参数值
    }

    // 懒加载
    isVisible($el){
        var scrollH = $(window).scrollTop();
        var winH = $(window).height();
        var top = $el.offset().top;
        if(top < winH + scrollH){  //判断元素是否进入视野
            return true
        }else{
            return false
        }
    }

    // DOM 拼接
    getNode(item,fileServer){
        var tpl = ""
        tpl += "<li class='item'>";
        if(item.orgPic!=null){
            tpl += "<a href="+getCtx()+"/hospital/enterprise/detail?c=001001&id="+item.orgId+" class='link'><img src="+fileServer+item.orgPic.fileUrl+"></a>";
        } else {
            tpl += "<a href="+getCtx()+"/hospital/enterprise/detail?c=001001&id="+item.orgId+" class='link'><img src='"+getCtx()+"/hospital/images/ad_320x240_01.jpg'></a>";
        }
        //tpl += ' <h4 class="title">'+ item.shortName +'</h4>';
        tpl += "<div class='hspt_j_2'>"+item.orgName+"</div>";
        tpl += "</li>";
        return $(tpl)
    }

    // 主流程函数
    start(){
        var _this = this;
        _this.getData(function(dataList,fileServer){    // 执行 getData 使用 ajax 获取数据
            _this.isDataArrive = true
            $.each(dataList,function(index,obj){
                var $node = _this.getNode(obj,fileServer)   // 拿到的数据进行 DOM 拼接
                $node.find('img').on('load',function(){
                    $('.picture').append($node)
                    _this.waterFall($node)   // 进行瀑布流布局
                })

            })
        })
        _this.isDataArrive = false   // 即获取并添加之后，修改数据状态
    }

    getData(callback){
        var _this = this;
        var kw = this.getUrlParam('orgName');
        var data = {
            orgName:kw,
            pageInput:{
                total: 1,
                current: _this.current,
                size: _this.size,
                sort: ""
            }
        }

        var uri = this.getCtx()+'/hospital/enterprise/list';
        $.ajax({
            url: uri,
            dataType:"json",
            contentType:'application/json;charset=UTF-8',
            type:'post',
            data:JSON.stringify(data),
        }).done(function(ret){
            if(ret && ret.result == 200){
                _this.current++;
                _this.total = ret.pageInfo.total;
                callback(ret.data,ret.fileServer)  //如果数据没问题，那么生成节点并摆放好位置
            } else if(ret.result == 401){
                location.href=getCtx()+'/hospital/expired';
            } else{
                console.log('get enterprise list data')
            }
        })
    }

    waterFall($node){
        var nodeWidth = $('.item').outerWidth(true)
        if(this.isFirst){    // 首次调用的时候
            var colNum = parseInt($('.hspt_wf').width()/$('.item').outerWidth(true))
            for(var i = 0; i < colNum ; i++){
                this.colHeight[i] = 0
            }
            this.isFirst = false
        }
        var index = 0, minSumHeight = this.colHeight[0];

        for(var i = 0; i < this.colHeight.length ; i++){
            if(this.colHeight[i] < minSumHeight){
                index = i
                minSumHeight = this.colHeight[i]
            }
        }

        //节点的位置
        $node.css({
            left: nodeWidth * index,
            top: minSumHeight,
            opacity: 1
        })

        this.colHeight[index] = $node.outerHeight(true) + this.colHeight[index]

        $('.picture').height(Math.max.apply(null,this.colHeight))
    }

}