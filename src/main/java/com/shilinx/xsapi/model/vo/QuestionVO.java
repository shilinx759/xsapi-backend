package com.shilinx.xsapi.model.vo;

import cn.hutool.json.JSONUtil;
import com.google.gson.reflect.TypeToken;
import com.shilinx.xsapi.model.dto.question.JudgeCase;
import com.shilinx.xsapi.model.dto.question.JudgeConfig;
import com.shilinx.xsapi.model.entity.Post;
import com.shilinx.xsapi.model.entity.Question;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 返回前端题目封装类
 * 不返回给用户 judgeCase 和 answer
 * @author 86181
 */
@Data
public class QuestionVO implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 标签列表（json 数组）
     */
    private List<String> tags;

    /**
     * 题目提交数
     */
    private Integer submitNum;

    /**
     * 题目通过数
     */
    private Integer acceptedNum;

    /**
     * 判题配置(json 对象)
     */
    private JudgeConfig judgeConfig;

    /**
     * 点赞数
     */
    private Integer thumbNum;

    /**
     * 收藏数
     */
    private Integer favourNum;

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
     * 题目创建人的信息
     */
    private UserVO userVO;

    private static final long serialVersionUID = 1L;


    /**
     * 包装类转对象
     * 把与数据表直接映射的实体类和包装类中类型不一致的属性进行转换
     * @param questionVO
     * @return
     */
    public static Question voToObj(QuestionVO questionVO) {
        if (questionVO == null) {
            return null;
        }
        Question question = new Question();
        //一直的属性直接复制
        BeanUtils.copyProperties(questionVO, question);
        //实体类中为 String 包装类中为 List<String>
        List<String> tagList = questionVO.getTags();
        if (tagList != null) {
            //将列表转换为 JSON字符串
            question.setTags(JSONUtil.toJsonStr(tagList));
        }
        JudgeConfig judgeConfig = questionVO.getJudgeConfig();
        if (judgeConfig != null) {
            question.setJudgeConfig(JSONUtil.toJsonStr(judgeConfig));
        }
        return question;
    }

    /**
     * 对象转包装类
     *
     * @param question
     * @return
     */
    public static QuestionVO objToVo(Question question) {
        if (question == null) {
            return null;
        }
        QuestionVO questionVO = new QuestionVO();
        BeanUtils.copyProperties(question, questionVO);
        //把实体类中的 string tag 转换成 包装类中的列表 tagList
        List<String> tagList = JSONUtil.toList(question.getTags(), String.class);
        //把实体类的 string judgeConfig 转换成 JudgeConfig 类
        JudgeConfig judgeConfig = JSONUtil.toBean(question.getJudgeConfig(), JudgeConfig.class);
        //set 到包装类中
        questionVO.setTags(tagList);
        questionVO.setJudgeConfig(judgeConfig);
        return questionVO;
    }
}