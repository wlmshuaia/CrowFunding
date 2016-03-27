/**
 * 搜索相关 js 操作
 * Created by wengliemiao on 16/1/20.
 */
$(function() {
    // 点击空白关闭搜索框
    $(".search-con-cover").bind("click", fCloseSearch);
    $(".close-con a").bind("click", fCloseSearch);
});

/**
 * 点击搜索图标, 显示搜索框
 */
function fShowSearch() {
    // 显示搜索框
    $(".search-con-cover").fadeIn();
    $(".search-con").fadeIn();

    // 清空内容
    $("#hot-con").empty();

    // 显示缓冲动画
    $(".my-loader-con").show() ;

    // 获取热门标签和热门分类
    $.ajax({
        url: 'front/Search/getHot.do',
        dataType: 'json',
        success: function(data) {
            // 隐藏缓冲动画
            $(".my-loader-con").fadeOut() ;

            // 分类
            var sCateHtml = '<div class="s-con">'+
                '<div class="one-header">'+
                '分类'+
                '</div>'+
                '<div class="one-content">'+
                '<ul>';
            if(data.hotCates.length != 0) {
                $.each(data.hotCates, function(k, v){
                    sCateHtml += '<li><a href="front/search/getByType.jsp?cateid='+ v.id +'">'+ v.catename +'</a></li>';
                });
            } else {
                sCateHtml += '<li>暂无</li>';
            }
            $("#hot-con").append(sCateHtml);
            $("#hot-con li").css("margin", "0 1rem 1rem 0");
            sCateHtml += '</ul>'+
                '</div>'+
                '</div>';

            // 热门
            var sLabelHtml = '<div class="s-con">'+
                '<div class="one-header">'+
                '热门'+
                '</div>'+
                '<div class="one-content">'+
                '<ul>';
            if(data.hotLabels.length != 0) {
                $.each(data.hotLabels, function(k, v){
                    sLabelHtml += '<li><a href="front/search/getByType.jsp?labelid='+ v.id +'">'+ v.labelname +'</a></li>';
                });
            } else {
                sLabelHtml += '<li>暂无</li>';
            }
            sLabelHtml += '</ul>'+
                '</div>'+
                '</div>';
            $("#hot-con").append(sLabelHtml);
            $("#hot-con li").css("margin", "0 1rem 1rem 0");
        },
        error: function(data) {
            console.log("getHot error..."+data);
        }
    });
}

/**
 * 关闭搜索区域
 */
function fCloseSearch() {
    $(".search-con-cover").fadeOut();
    $(".search-con").fadeOut();
}

/**
 * 点击搜索
 * @param obj
 */
function fToSearch(obj) {
    var sCon = $(obj).parents(".search-con").find(".search-input-con").find("input").val();

    if(sCon != '') {
        var sUrl = encodeURI("front/search/searchResult.jsp?content=" + sCon);
        window.location.href = sUrl;
    }
}




