package com.shilinx.xsapi.model.dto.question;

import lombok.Data;

/**
 * 程序配置
 * @author slx
 */
@Data
public class JudgeConfig {
    /**
     * 时间限制(ms)
     */
    private Long timeLimit;
    /**
     * 内存限制(KB)
     */
    private Long memoryLimit;
    /**
     * 堆栈限制(KB)
     */
    private Long stackLimit;
}
