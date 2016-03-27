package com.tide.task;

import com.tide.bean.Project;
import com.tide.service.ProjectService;
import com.tide.service.UserService;
import com.tide.utils.DataUtils;
import com.tide.utils.DateUtils;
import com.tide.wechat.WxPayApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * 项目状态查看：超过时间或者达到目标
 * Created by wengliemiao on 16/1/24.
 */
@Component
public class ProjectStatusTask {
    private int count = 0;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;

//    @Scheduled(cron = "0/5 * * * * ?") // 每5秒执行一次
    @Scheduled(cron = "0 * * * * ?")
    public void checkStatus() {
        System.out.println(Thread.currentThread().getName()+"任务进行中。。。"+(count++));
        // 进行中的项目
        List<Project> pCrowFundingList = this.projectService.getProjectByStatus(DataUtils.I_PROJECT_CROWFUNDING);
        for (Project p : pCrowFundingList) {
            // 1、判断时间是否到期
            if(DateUtils.getDaysBetween(new Date(), p.getEnddate()) <= 0) {
                p.setStatus(DataUtils.I_PROJECT_FAIL);
                this.projectService.update(p);

                // 1.1 发送众筹失败消息通知
                this.projectService.sendBuyFailMsgToBackers(p.getId());

                // 1.2 退还款项给所有支持者
                this.projectService.refundToBackers(p.getId());
            }
            // 2、判断条件是否达成
//            Integer fundnum = this.projectService.getProjectFundNum(p.getId());
            Integer fundnum = p.getFundnum();
            fundnum = fundnum == null ? 0 : fundnum;
            if(fundnum >= p.getTargetnum()) {
                p.setStatus(DataUtils.I_PROJECT_SUCCESS);
                this.projectService.update(p);

                // 发送众筹成功消息通知
                this.projectService.sendBuySuccessMsgToBackers(p.getId());
            }
        }

        // 待审核的项目
        List<Project> pExamList = this.projectService.getProjectByStatus(DataUtils.I_PROJECT_WAIT_EXAME);
        for (Project p : pExamList) {
            if(DateUtils.getDaysBetween(new Date(), p.getEnddate()) <= 0
                    && DateUtils.getMinutesBetween(p.getReleasedate(), new Date()) > 60) { // 时间到尚未通过审核, 获取发布项目时间太长则为默拒
                p.setStatus(DataUtils.I_PROJECT_AGAINST);
                this.projectService.update(p);

                // 发送审核失败消息通知
                String openid = this.userService.getOpenidById(p.getUserid());
                String url = DataUtils.WEB_PHONE_HOMPAGE + "/front/Project/projectInfo.do?id=" + p.getId();
                String reason = "发布项目相关项未填写完整或者不符合相关条例";
                WxPayApi.sendExamineFailMsg(openid, url, p.getTitle(), reason, DateUtils.getCurrentDate1());
            }
        }
    }
}
