/**
 * 评论页面相关 js 操作
 * Created by wengliemiao on 16/3/14.
 */
$(function() {

});

/**
 * 弹出评论框
 */
function fToComment() {
    var sStyle = "width: 100%; height: 65%; padding: .5rem 1rem; border: none; outline: none; font-size: 1.8rem; resize:none";
    var sContentHtml = '<textarea style="' + sStyle + '"  name="comment" placeholder="你想说点啥......"></textarea>';
    layer.open({
        btn: ['取消', '确定'],
        content: sContentHtml,
        style: 'width: 86%; height: 15rem;',
        yes: function(index) { // 点击取消
            layer.close(index);
        },
        no: function(index) { // 点击确定
            var projectid = $("input[name=projectid]").val();
            var sComment = $("textarea[name=comment]").val();

            if(sComment == null || sComment == undefined || sComment == '') {
                CommonHandler.fShowTips("请输入内容");
            } else {
                fSubmitComment(projectid, sComment);
            }

            layer.close(index);
        }
    });
}

/**
 * 提交评论
 * @param sComment
 */
function fSubmitComment(projectid, sComment) {
    $.ajax({
        url: 'front/Project/addComment.do',
        method: 'post',
        data: {projectid: projectid, content: sComment},
        success: function(data) {
            //CommonHandler.fShowTips("评论成功, 待审核通过后显示");
            CommonHandler.fShowTips("评论成功");
            setTimeout(function() {
                window.location.reload();
            }, 1000);
        },
        error: function(data) {
            alert("评论失败");
        }
    });
}

/**
 * 点赞
 * @param obj
 */
function fToZan(obj) {
    var iCommentId = $(obj).parents(".zan-con").siblings("input[name=commentid]").val();
    $.ajax({
        url: 'front/Project/addZan.do',
        method: 'post',
        data: {commentid: iCommentId},
        success: function() {
            // 1.样式变化
            $(obj).removeClass("zan");
            $(obj).addClass("zan-success");

            // 2.数字变化
            var oZanNum = $(obj).siblings(".zan-num");
            $(oZanNum).text(parseInt($(oZanNum).text()) + 1);

            // 3.修改点击事件
            //$(obj).unbind("click");
            $(obj).attr("onclick", "fToCancelZan(this)");
        },
        error: function() {
            alert("点赞失败");
        }
    });
}

/**
 * 取消点赞
 * @param obj
 */
function fToCancelZan(obj) {
    var iCommentId = $(obj).parents(".zan-con").siblings("input[name=commentid]").val();
    $.ajax({
        url: 'front/Project/cancelZan.do',
        method: 'post',
        data: {commentid: iCommentId},
        success: function() {
            // 1.样式变化
            $(obj).removeClass("zan-success");
            $(obj).addClass("zan");

            // 2.数字变化
            var oZanNum = $(obj).siblings(".zan-num");
            $(oZanNum).text(parseInt($(oZanNum).text()) - 1);

            // 3.修改点击事件
            $(obj).attr("onclick", "fToZan(this)");
        },
        error: function() {
            alert("点赞失败");
        }
    });
}


