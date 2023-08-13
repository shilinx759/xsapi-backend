package com.shilinx.xsapi.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shilinx.xsapi.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.shilinx.xsapi.model.dto.questionsubmit.QuestionSubmitQueryRequest;
import com.shilinx.xsapi.model.entity.QuestionSubmit;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shilinx.xsapi.model.entity.User;
import com.shilinx.xsapi.model.vo.QuestionSubmitVO;

import javax.servlet.http.HttpServletRequest;

/**
* @author 86181
* @description 针对表【question_submit(题目提交)】的数据库操作Service
* @createDate 2023-08-09 14:25:54
*/
public interface QuestionSubmitService extends IService<QuestionSubmit> {

    /**
     * 题目提交
     * @param questionSubmitAddRequest
     * @param loginUser
     * @return
     */
    long doQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser);


    /**
     * 获取查询条件
     *
     * @param questionSubmitQueryRequest
     * @return
     */
    QueryWrapper<QuestionSubmit> getQueryWrapper(QuestionSubmitQueryRequest questionSubmitQueryRequest);

    /**
     * 获取题目封装
     *
     * @param questionSubmit
     * @param loginUser 当前登录用户(可避免多次查询你数据库)
     * @return
     */
    QuestionSubmitVO getQuestionSubmitVO(QuestionSubmit questionSubmit, User loginUser);

    /**
     * 分页获取题目封装
     *
     * @param questionSubmitPage
     * @param loginUser 当前登录用户(可避免多次查询你数据库)
     * @return
     */
    Page<QuestionSubmitVO> getQuestionSubmitVOPage(Page<QuestionSubmit> questionSubmitPage, User loginUser);

}
