package com.shilinx.xsapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shilinx.xsapi.common.ErrorCode;
import com.shilinx.xsapi.constant.CommonConstant;
import com.shilinx.xsapi.exception.BusinessException;
import com.shilinx.xsapi.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.shilinx.xsapi.model.dto.questionsubmit.QuestionSubmitQueryRequest;
import com.shilinx.xsapi.model.entity.Question;
import com.shilinx.xsapi.model.entity.QuestionSubmit;
import com.shilinx.xsapi.model.entity.User;
import com.shilinx.xsapi.model.enums.QuestionSubmitLanguageEnum;
import com.shilinx.xsapi.model.enums.QuestionSubmitStatusEnum;
import com.shilinx.xsapi.model.vo.QuestionSubmitVO;
import com.shilinx.xsapi.model.vo.QuestionVO;
import com.shilinx.xsapi.model.vo.UserVO;
import com.shilinx.xsapi.service.QuestionService;
import com.shilinx.xsapi.service.QuestionSubmitService;
import com.shilinx.xsapi.mapper.QuestionSubmitMapper;
import com.shilinx.xsapi.service.UserService;
import com.shilinx.xsapi.utils.SqlUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
* @author 86181
* @description 针对表【question_submit(题目提交)】的数据库操作Service实现
* @createDate 2023-08-09 14:25:54
*/
@Service
public class QuestionSubmitServiceImpl extends ServiceImpl<QuestionSubmitMapper, QuestionSubmit>
    implements QuestionSubmitService{

    @Resource
    private UserService userService;

    @Resource
    private QuestionService questionService;

    /**
     * 提交题目
     *
     * @param
     * @param loginUser
     * @return
     */
    @Override
    public long doQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser) {
        //校验编程语言是否合法
        String language = questionSubmitAddRequest.getLanguage();
        QuestionSubmitLanguageEnum languageEnum = QuestionSubmitLanguageEnum.getEnumByValue(language);
        if (languageEnum == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "编程语言错误");
        }
        long questionId = questionSubmitAddRequest.getQuestionId();
        // 判断实体是否存在，根据类别获取实体
        Question question = questionService.getById(questionId);
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 是否已提交题目
        long userId = loginUser.getId();
        // 每个用户串行提交题目
        QuestionSubmit questionSubmit = new QuestionSubmit();
        questionSubmit.setUserId(userId);
        questionSubmit.setQuestionId(questionId);
        questionSubmit.setCode(questionSubmitAddRequest.getCode());
        questionSubmit.setLanguage(questionSubmitAddRequest.getLanguage());
        //设置初始状态枚举,为等待中
        questionSubmit.setStatus(QuestionSubmitStatusEnum.WAITING.getValue());
        questionSubmit.setStatus(0);
        questionSubmit.setJudgeInfo("{}");
        boolean save = this.save(questionSubmit);
        if (!save) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "数据插入失败");
        }
        return questionSubmit.getId();
    }

    /**
     * 获取查询包装类
     * (用户根据哪些字段查询,根据前端得到请求对象,得到 MybatisPlus 支持的查询 QueryWrapper类)
     * @param questionSubmitQueryRequest
     * @return
     */
    @Override
    public QueryWrapper<QuestionSubmit> getQueryWrapper(QuestionSubmitQueryRequest questionSubmitQueryRequest) {
        QueryWrapper<QuestionSubmit> queryWrapper = new QueryWrapper<>();
        if (questionSubmitQueryRequest == null) {
            return queryWrapper;
        }
        //1. 使用插件 .allget 得到参数中所有的属性
        //2. 再一个一个判断用户可能根据什么来查询
        String language = questionSubmitQueryRequest.getLanguage();
        Long userId = questionSubmitQueryRequest.getUserId();
        Integer status = questionSubmitQueryRequest.getStatus();
        Long questionId = questionSubmitQueryRequest.getQuestionId();
        //因为继承了 PageRequest 就可以拿到排序相关的配置了
        String sortField = questionSubmitQueryRequest.getSortField();
        String sortOrder = questionSubmitQueryRequest.getSortOrder();

        queryWrapper.eq(ObjectUtils.isNotEmpty(language), "language", language);
        queryWrapper.eq(ObjectUtils.isNotEmpty(userId), "userId", userId);
        //用户查询的状态非法的话,就不拼接状态,也就是不能根据状态来查询而已
        queryWrapper.eq(QuestionSubmitStatusEnum.getEnumByValue(status)!=null, "status", status);
        queryWrapper.eq(ObjectUtils.isNotEmpty(questionId), "questionId", questionId);
        queryWrapper.eq("isDelete", false);
        queryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);
        return queryWrapper;
    }


    /**
     * 根据题目对象得到题目包装类
     * @param questionSubmit
     * @param loginUser
     * @return
     */
    @Override
    public QuestionSubmitVO getQuestionSubmitVO(QuestionSubmit questionSubmit,User loginUser) {
        //VO类中的方法实现:对象转封装类
        QuestionSubmitVO questionSubmitVO = QuestionSubmitVO.objToVo(questionSubmit);
        //注意:仅管理员和用户本人能看见用户自己提交的代码,即登录用户id和提交用户userId相同才能看见
        //获取当前登录用户
        long loginUserId = loginUser.getId();
        //即不是本人 又 不是管理员
        if (loginUserId != questionSubmit.getUserId() && !userService.isAdmin(loginUser)) {
            //设置本次提交记录的代码为空即可
            questionSubmitVO.setCode(null);
        }
        //是本人或者是管理员,就直接返回提交的代码信息
        return questionSubmitVO;
    }

    /**
     * 根据题目对象分页得到题目包装类的分页
     * 相当于方法的 for 循环
     * @param questionSubmitPage
     * @param loginUser
     * @return
     */
    @Override
    public Page<QuestionSubmitVO> getQuestionSubmitVOPage(Page<QuestionSubmit> questionSubmitPage, User loginUser) {
        //获取原始数据
        List<QuestionSubmit> questionSubmitList = questionSubmitPage.getRecords();
        //获取原始分页信息
        Page<QuestionSubmitVO> questionSubmitVOPage = new Page<>(questionSubmitPage.getCurrent(), questionSubmitPage.getSize(), questionSubmitPage.getTotal());
        if (CollectionUtils.isEmpty(questionSubmitList)) {
            return questionSubmitVOPage;
        }
        List<QuestionSubmitVO> questionSubmitVOList = questionSubmitList.stream().map(questionSubmit -> {
            //每个提交记录都去获取一次包装类,最后返回一个集合即可
            return getQuestionSubmitVO(questionSubmit, loginUser);
        }).collect(Collectors.toList());
        //将脱敏好的数据设置到分页数据中
        questionSubmitVOPage.setRecords(questionSubmitVOList);
        return questionSubmitVOPage;
    }

}




