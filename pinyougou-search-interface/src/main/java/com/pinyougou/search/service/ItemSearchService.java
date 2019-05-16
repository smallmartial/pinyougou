package com.pinyougou.search.service;

import java.util.List;
import java.util.Map;

/**
 * @Author smallmartial
 * @Date 2019/5/15
 * @Email smallmarital@qq.com
 */
public interface ItemSearchService {

    /**
     * 搜索
     * @param searchMap
     * @return
     */
    public Map<String,Object> search(Map searchMap);

    /**
     * 导入数据
     *
     */
    public void importList(List list);

    /**
     * 删除数据
     * @param goodsIdList
     */
    public void deleteByGoodsIds(List goodsIdList);

}
