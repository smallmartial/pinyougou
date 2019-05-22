package com.pinyougou.page.service;

/**
 * @Author smallmartial
 * @Date 2019/5/18
 * @Email smallmarital@qq.com
 */
public interface ItemPageService {
    /**
     * 生成商品详情页
     * @param goodsId
     * @return
     */
    public boolean genItemHtml(Long goodsId);

    /**
     * 删除商品详细页
     * @param goodsIds
     * @return
     */
    public boolean deleteItemHtml(Long[] goodsIds);

}
