package com.shilinx.xsapi.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shilinx.xsapi.annotation.AuthCheck;
import com.shilinx.xsapi.common.BaseResponse;
import com.shilinx.xsapi.common.ErrorCode;
import com.shilinx.xsapi.common.ResultUtils;
import com.shilinx.xsapi.constant.UserConstant;
import com.shilinx.xsapi.exception.BusinessException;
import com.shilinx.xsapi.model.dto.question.QuestionQueryRequest;
import com.shilinx.xsapi.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.shilinx.xsapi.model.dto.questionsubmit.QuestionSubmitQueryRequest;
import com.shilinx.xsapi.model.entity.Question;
import com.shilinx.xsapi.model.entity.QuestionSubmit;
import com.shilinx.xsapi.model.entity.User;
import com.shilinx.xsapi.model.vo.QuestionSubmitVO;
import com.shilinx.xsapi.service.QuestionSubmitService;
import com.shilinx.xsapi.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 题目提交接口
 * @author 86181
 */
@RestController
@RequestMapping("/question_submit")
@Slf4j
public class QuestionSubmitController {

    @Resource
    private QuestionSubmitService questionSubmitService;

    @Resource
    private UserService userService;

    /**
     * 提交题目
     *
     * @param questionSubmitAddRequest
     * @param request
     * @return resultNum 提交记录的 ID
     */
    @PostMapping("/")
    public BaseResponse<Long> doQuestionSubmit(@RequestBody QuestionSubmitAddRequest questionSubmitAddRequest,
            HttpServletRequest request) {
        if (questionSubmitAddRequest == null || questionSubmitAddRequest.getQuestionId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 登录才能提交
        final User loginUser = userService.getLoginUser(request);
        long questionSubmitId = questionSubmitService.doQuestionSubmit(questionSubmitAddRequest, loginUser);
        return ResultUtils.success(questionSubmitId);
    }


    /**
     * 分页获取题目提交记录列表
     *
     * @param questionSubmitQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/list/page")
    public BaseResponse<Page<QuestionSubmitVO>> listQuestionByPage(@RequestBody QuestionSubmitQueryRequest questionSubmitQueryRequest,
                                                                   HttpServletRequest request) {
        long current = questionSubmitQueryRequest.getCurrent();
        long size = questionSubmitQueryRequest.getPageSize();
        //从数据库中查询原始记录信息
        Page<QuestionSubmit> questionSubmitPage = questionSubmitService.page(new Page<>(current, size),
                questionSubmitService.getQueryWrapper(questionSubmitQueryRequest));
        //获取当前登录用户
        User loginUser = userService.getLoginUser(request);
        //返回脱敏后的结果
        return ResultUtils.success(questionSubmitService.getQuestionSubmitVOPage(questionSubmitPage,loginUser));
    }

}
