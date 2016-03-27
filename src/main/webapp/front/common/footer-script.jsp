<%@ page import="com.tide.utils.DataUtils" %>
<%--
  Created by IntelliJ IDEA.
  User: wengliemiao
  Date: 16/1/5
  Time: 下午2:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%--<script src="dist/js/jquery-2.1.1.min.js"></script>--%>
<%--<script src="assets/amazeui/js/amazeui.min.js"></script>--%>
<script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
<script src="http://cdn.amazeui.org/amazeui/2.5.0/js/amazeui.min.js"></script>
<script src="<%=DataUtils.DOMAIN_FILE%>/assets/layer/layer.js"></script>
<style>
    <%-- 发布项目样式选择 --%>
    .layermchild {
        background: none;
        box-shadow: none;
        color: #fff;
    }
</style>

<script>
    $(function() {
        // 底栏状态修改
        var sTitle = $("title").text();
        var aLabel = $(".footer .am-navbar-label");
        if(aLabel.length != 0) {
            $.each(aLabel, function(k, v) {
                if(sTitle == $(v).text() || sTitle == "潮客公园") {
                    var oIcon = $(v).siblings(".icon");
                    var sClassName = $(oIcon).attr("class");
                    $(oIcon).attr("class", sClassName+"-choose");
                    $(v).css("color", 'rgb(238, 162, 88)');
                    return false;
                }
            });
        }

    });

    function fShowType() {
        var sContentHtml = '<div class="project-type">' +
                '<ul>' +
                '   <li><a href="front/Project/addProject.do?type=cloth"><div class="cloth"><img src="dist/imgs/icon_cloth.png" alt=""></div></a><span>服装</span></li>' +
                '   <li><a href="front/Project/addProject.do?type=commonweal"><div class="commonweal"><img src="dist/imgs/icon_public_welfare.png" alt=""></div></a><span>公益</span></li>' +
                '</ul>' +
                '</div>';
        layer.open({
//            btn: ['取消', '确定'],
            content: sContentHtml,
            style: 'width: auto; height: auto;',
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

</script>

