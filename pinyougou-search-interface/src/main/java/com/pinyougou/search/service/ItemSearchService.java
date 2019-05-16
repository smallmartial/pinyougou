package com.pinyougou.search.service;

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

}
