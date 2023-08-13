package com.shilinx.xsapi.model.dto.question;

import lombok.Data;

/**
 * 评判用例
 * @author slx
 */
@Data
public class JudgeCase {
    /**
     * 输入用例
     */
    private String input;

    /**
     * 输出用例
     */
    private String output;
}
