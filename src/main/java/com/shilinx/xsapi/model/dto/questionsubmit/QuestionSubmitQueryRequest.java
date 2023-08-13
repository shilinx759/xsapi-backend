package com.shilinx.xsapi.model.dto.questionsubmit;

import com.shilinx.xsapi.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 查询请求
 * EqualsAndHashCode:重写父类的 Equals 和 HashCode 方法,从而判断的更准确
 * @author 86181
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class QuestionSubmitQueryRequest extends PageRequest implements Serializable {

    /**
     * 编程语言
     */
    private String language;

    /**
     * 用户 ID
     */
    private Long userId;

    /**
     * 提交状态
     */
    private Integer status;

    /**
     * 题目 id
     */
    private Long questionId;


    private static final long serialVersionUID = 1L;
}