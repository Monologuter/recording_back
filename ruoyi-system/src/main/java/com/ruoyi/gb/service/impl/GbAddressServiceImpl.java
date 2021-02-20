package com.ruoyi.gb.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.gb.domain.GbAddress;
import com.ruoyi.gb.mapper.GbAddressMapper;
import com.ruoyi.gb.service.IGbAddressService;

@Service
public class GbAddressServiceImpl extends ServiceImpl<GbAddressMapper, GbAddress>  implements IGbAddressService {
	 @Autowired
     private GbAddressMapper gbAddressMapper;

	    /**
	     * 查询通讯录
	     * 
	     * @param id 通讯录ID
	     * @return 通讯录
	     */
	    @Override
	    public GbAddress selectGbAddressById(String id)
	    {
	        return gbAddressMapper.selectGbAddressById(id);
	    }

	    /**
	     * 查询通讯录列表
	     * 
	     * @param gbAddress 通讯录
	     * @return 通讯录
	     */
	    @Override
	    public List<GbAddress> selectGbAddressList(GbAddress gbAddress)
	    {
	        return gbAddressMapper.selectGbAddressList(gbAddress);
	    }

	    /**
	     * 新增通讯录
	     * 
	     * @param gbAddress 通讯录
	     * @return 结果
	     */
	    @Override
	    public int insertGbAddress(GbAddress gbAddress)
	    {
	        return gbAddressMapper.insertGbAddress(gbAddress);
	    }

	    /**
	     * 修改通讯录
	     * 
	     * @param gbAddress 通讯录
	     * @return 结果
	     */
	    @Override
	    public int updateGbAddress(GbAddress gbAddress)
	    {
	        return gbAddressMapper.updateGbAddress(gbAddress);
	    }

	    /**
	     * 批量删除通讯录
	     * 
	     * @param ids 需要删除的通讯录ID
	     * @return 结果
	     */
	    @Override
	    public int deleteGbAddressByIds(String[] ids)
	    {
	        return gbAddressMapper.deleteGbAddressByIds(ids);
	    }

	    /**
	     * 删除通讯录信息
	     * 
	     * @param id 通讯录ID
	     * @return 结果
	     */
	    @Override
	    public int deleteGbAddressById(String id)
	    {
	        return gbAddressMapper.deleteGbAddressById(id);
	    }
}
