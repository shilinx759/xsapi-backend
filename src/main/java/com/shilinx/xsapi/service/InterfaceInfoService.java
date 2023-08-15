package com.shilinx.xsapi.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shilinx.xsapi.model.dto.interfaceinfo.InterfaceInfoQueryRequest;
import com.shilinx.xsapi.model.entity.InterfaceInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shilinx.xsapi.model.vo.InterfaceInfoVO;

import javax.servlet.http.HttpServletRequest;

/**
* @author shilinx
* @description 针对表【interface_info(接口信息)】的数据库操作Service
* @createDate 2023-08-13 16:55:53
*/
public interface InterfaceInfoService extends IService<InterfaceInfo> {

    /**
     * 校验参数是否合法
     * @param interfaceInfo
     * @param add
     */
    void validInterfaceInfo(InterfaceInfo interfaceInfo, boolean add);

    /**
     * 返回查询条件包装
     * @param interfaceInfoQueryRequest
     * @return
     */
    QueryWrapper<InterfaceInfo> getQueryWrapper(InterfaceInfoQueryRequest interfaceInfoQueryRequest);


    /**
     * 返回查询包装类
     *
     * @param interfaceInfo
     * @param request
     * @return
     */
    InterfaceInfoVO getInterfaceInfoVO(InterfaceInfo interfaceInfo, HttpServletRequest request);

    /**
     * 返回查询包装类分页
     *
     * @param interfaceInfoPage
     * @param request
     * @return
     */
    Page<InterfaceInfoVO> getInterfaceInfoVOPage(Page<InterfaceInfo> interfaceInfoPage, HttpServletRequest request);
}
