package com.ruoyi.gb.mapper;

import java.util.List;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.gb.domain.GbAddress;

public interface GbAddressMapper extends BaseMapper<GbAddress>{
    /**
     * 查询通讯录
     * 
     * @param id 通讯录ID
     * @return 通讯录
     */
    public GbAddress selectGbAddressById(String id);

    /**
     * 查询通讯录列表
     * 
     * @param gbAddress 通讯录
     * @return 通讯录集合
     */
    public List<GbAddress> selectGbAddressList(GbAddress gbAddress);

    /**
     * 新增通讯录
     * 
     * @param gbAddress 通讯录
     * @return 结果
     */
    public int insertGbAddress(GbAddress gbAddress);

    /**
     * 修改通讯录
     * 
     * @param gbAddress 通讯录
     * @return 结果
     */
    public int updateGbAddress(GbAddress gbAddress);

    /**
     * 删除通讯录
     * 
     * @param id 通讯录ID
     * @return 结果
     */
    public int deleteGbAddressById(String id);

    /**
     * 批量删除通讯录
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteGbAddressByIds(String[] ids);
}
