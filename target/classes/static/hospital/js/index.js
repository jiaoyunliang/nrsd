
function changeDefaultType(productTypeGroup,productType) {
    $("a[id^="+'ptype_'+productTypeGroup+"]").each(function (i,ele) {
        $(this).children().removeClass("defType");
    })
    $("#ptype_"+productTypeGroup+"_"+productType).children().addClass("defType");
    $("#productMore_"+productTypeGroup).attr("href","javascript:jumpProductMore('"+productType+"')")
    loadProduct(productTypeGroup,productType);
}

function jumpProductMore(productType) {
    location.href=getCtx()+'/hospital/product/list?productTypeId='+productType;
}

function loadProduct(productTypeGroup,productType) {

    var uri = getCtx()+'/hospital/product/queryProductForIndex';

    var param = {
        sysId:2,
        productTypeId:productType,
        isHospitalView:1,
        pageInput:{
            current: 1,
            size: 6
        }
    };
    $.ajax({
        url: uri,
        type: 'POST',
        dataType: 'json',
        contentType: "application/json",
        data: JSON.stringify(param),
        success: function(rs){
            if(rs.result==200){
                $("#viewGroup"+productTypeGroup).html("<ul></ul>");
                if(rs.data.length>0){
                    $("#viewGroup"+productTypeGroup).html(index_product_template(rs.data,rs.fileServer));
                }
            }
        }
    });

}


function fn1(item,fileServer) {
    if(item.picList.length>0){
        return fileServer+item.picList[0].fileUrl;
    } else {
        return "";
    }
}

function fn2(item) {
    return getCtx()+"/hospital/product/view?id="+item.id;
}

var index_product_template = (data,fileServer) =>
    `<ul>
		${data.map(item=>`
            <li>
                <a href="${fn2(item)}"><img src="${fn1(item,fileServer)}" style="width: 200px;height: 180px"/></a>
                <a href="${fn2(item)}"><p>${item.productName}</p></a>
            </li>
		`).join('')}
</ul>`;
