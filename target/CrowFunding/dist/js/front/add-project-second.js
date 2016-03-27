/**
 * 发布项目第二步
 * Created by wengliemiao on 15/12/14.
 */
$(function(){
    $('.single-slider').jRange({
        from: 0,
        to: 100,
        step: 1,
        scale: [0,25,50,75,100],
        format: '%s',
        width: 300,
        showLabels: true,
        showScale: true
    });

    $('.range-slider').jRange({
        from: 0,
        to: 100,
        step: 1,
        scale: [0,25,50,75,100],
        format: '%s',
        width: 300,
        showLabels: true,
        isRange : true
    });

    //var hour = $("select[name=time-enddate]").val();
    //var minute = $("select[name=minute-enddate]").val();
    //var releasedate = $("input[name=releasedate]").val();
    //var enddate = $("input[name=enddate]").val();
    //var projectid = $("input[name=projectid]").val();
    //var leastnum = $("input[name=leastnum]").val();
    //
    //alert("1: " + releasedate + ", " + enddate + ", " + hour + ", " + minute + ", " + projectid + ", " + leastnum);

});

/**
 * 滑块滑动后 显示经过计算的日期
 * @param obj
 */
function fShowDate(obj) {
    var iNum = $(obj).val();
    fGetDateDetail();
    $(".tip-enddate").css("margin-left", Math.floor(100 * iNum / 60 - 10)+"%");
}

/**
 * 选择小时下拉框发生变化
 * @param obj
 */
function fChangeHour(obj) {
    fGetDateDetail();
}

/**
 * 选择分钟下拉框发生变化
 * @param obj
 */
function fChangeMinute(obj) {
    fGetDateDetail();
}

/**
 * 获取日期计算的相关参数
 */
function fGetDateDetail(iNumValue) {
    var sReaDate = $("input[name=releasedate]").val();  // 发布项目日期
    var iNum;
    if(iNumValue != null && iNumValue != undefined) {
        iNum = iNumValue;
    } else {
        iNum = $("input[name=enddaterange]").val();     // 天数
    }
    var iEndHour = $("select[name=time-enddate]").val();// 小时
    var iEndMinute = $("select[name=minute-enddate]").val();// 分钟
    var sEndDate = fGetDate(sReaDate, iNum, iEndHour, iEndMinute); // 计算结束具体日期
    return fSetEndDate(sEndDate);
}

/**
 * 显示结束日期
 * @param sEndDate
 */
function fSetEndDate(sEndDate) {
    // input 隐藏域
    $("input[name=enddate]").val(sEndDate);
    // 显示结束日期
    $(".tip-enddate").text(sEndDate);

    // 显示 xx天xx时xx分 后筹集结束
    fCalDate(sEndDate);
    return sEndDate;
}

/**
 * 计算 xx天xx时xx分结束
 * @param sEndDate
 */
function fCalDate(sEndDate) {
    var sReleaseDate = $("input[name=releasedate]").val();
    var oStartDate = new Date(Date.parse(sReleaseDate.replace(/-/g, "/")));
    var oEndDate = new Date(Date.parse(sEndDate.replace(/-/g, "/")));

    // 分钟数
    var iTime = (oEndDate.getTime() - oStartDate.getTime()) / (1000 * 60);
    var iDay = Math.floor(iTime / (24 * 60));
    var iHour = Math.floor((iTime % (24 * 60)) / 60);
    var iMinute = Math.ceil((iTime % (24 * 60)) % 60);

    iDay = (isNaN(iDay) || parseInt(iDay) < 0) ? 0 : iDay;
    iHour = (isNaN(iHour) || parseInt(iHour) < 0) ? 0 : iHour;
    iMinute = (isNaN(iMinute) || parseInt(iMinute) < 0) ? 0 : iMinute;

    if(parseInt(iMinute) == 60) {
        iHour ++;
        iMinute = 0;
    }

    if(parseInt(iHour) == 24) {
        iDay ++;
        iHour = 0;
    }

    $("#endNum").text(iDay);
    $("#endHour").text(iHour);
    $("#endMinute").text(iMinute);
}

/**
 * 日期计算: iNum, iEndHour, iEndMinute
 * @param sReaDate      发布项目日期
 * @param iNum          结束天数
 * @param iEndHour      结束小时
 * @param iEndMinute    结束分钟
 * @returns {string}
 */
function fGetDate(sReaDate, iNum, iEndHour, iEndMinute) {
    var oReaDate = new Date(Date.parse(sReaDate.replace(/-/g, "/")));
    var iEndDate = oReaDate.getTime();
    if(iNum != "") {
        iEndDate += iNum * (24 * 60 * 60 * 1000);
    }

    var oEndDate = new Date(iEndDate);
    var sYear = oEndDate.getFullYear();
    var sMonth = fAddZero(oEndDate.getMonth() + 1);
    var sDay = fAddZero(oEndDate.getDate());
    var sHour = fAddZero(iEndHour);
    var sMinute = fAddZero(iEndMinute);
    var sSecond = "00";

    if(isNaN(sYear)) {
        CommonHandler.fShowTips("请返回上一步重新发布项目");
        $(".btn-my-submit").addClass("cancel");
        $(".btn-my-submit").unbind("click");
        $(".btn-my-submit").attr("onclick", "");

        return "";
    } else {
        $(".btn-my-submit").bind("click", fSubmit);
    }

    return sYear+"-"+sMonth+"-"+sDay+" "+sHour+":"+sMinute+":"+sSecond;
}
/**
 * 小于10的前面添加0
 * @param i
 * @returns {*}
 */
function fAddZero(i){
    if(parseInt(i) < 10) {
        return "0"+i;
    }
    return i;
}

/**
 * 提交表单
 */
function fSubmit() {
    var iTargetNum = $("input[name=targetnum]").val();
    var iLeastNum = $("input[name=leastnum]").val();
    if(iTargetNum == '') {
        CommonHandler.fShowTips("筹集数不得为空");
    } else if(parseInt(iTargetNum) < parseInt(iLeastNum)) {
        var sCompany = $(".type-tip").text();
        CommonHandler.fShowTips("筹集数不得少于 "+iLeastNum + " " + sCompany);
    } else {
        var sFormUrl = 'front/Project/addProjectHandle2.do';
        $("form").attr("action", sFormUrl);
        $("form").submit();
    }
}




