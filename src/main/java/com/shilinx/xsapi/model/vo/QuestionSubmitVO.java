package com.shilinx.xsapi.model.vo;

import cn.hutool.json.JSONUtil;
import com.shilinx.xsapi.model.dto.questionsubmit.JudgeInfo;
import com.shilinx.xsapi.model.entity.QuestionSubmit;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * 返回前端题目提交记录封装类
 * @author 86181
 */
@Data
public class QuestionSubmitVO implements Serializable {
    /**
     * 提交记录 ID
     */
    private Long id;

    /**
     * 编程语言
     */
    private String language;

    /**
     * 用户代码
     */
    private String code;

    /**
     * 判题信息
     */
    private JudgeInfo judgeInfo;

    /**
     * 判题状态 0-待判题,1-判题中,2-成功,3-失败
     */
    private Integer status;

    /**
     * 题目 id
     */
    private Long questionId;

    /**
     * 创建用户 id
     */
    private Long userId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;


    /**
     * 题目提交人的信息
     */
    private UserVO userVO;

    /**
     * 对应题目信息
     */
    private QuestionVO questionVO;

    private static final long serialVersionUID = 1L;


    /**
     * 包装类转对象
     * 把与数据表直接映射的实体类和包装类中类型不一致的属性进行转换
     * @param questionSubmitVO
     * @return
     */
    public static QuestionSubmit voToObj(QuestionSubmitVO questionSubmitVO) {
        if (questionSubmitVO == null) {
            return null;
        }
        QuestionSubmit questionSubmit = new QuestionSubmit();
        //一直的属性直接复制
        BeanUtils.copyProperties(questionSubmitVO, questionSubmit);
        //实体类中为 String 包装类中为 List<String>
        JudgeInfo judgeInfo = questionSubmitVO.getJudgeInfo();
        if (judgeInfo != null) {
            //将列表转换为 JSON字符串
            questionSubmit.setJudgeInfo(JSONUtil.toJsonStr(judgeInfo));
        }
        return questionSubmit;
    }

    /**
     * 对象转包装类,脱敏返回
     *
     * @param questionSubmit
     * @return
     */
    public static QuestionSubmitVO objToVo(QuestionSubmit questionSubmit) {
        if (questionSubmit == null) {
            return null;
        }
        QuestionSubmitVO questionSubmitVO = new QuestionSubmitVO();
        BeanUtils.copyProperties(questionSubmit, questionSubmitVO);
        //把实体类的 string judgeInfo 转换成 JudgeInfo 类
        JudgeInfo judgeInfo = JSONUtil.toBean(questionSubmit.getJudgeInfo(), JudgeInfo.class);
        //set 到包装类中
        questionSubmitVO.setJudgeInfo(judgeInfo);
        return questionSubmitVO;
    }
}