package com.sjw.vo;

/**
 * 商品库存状态实体类
 */
public class GoodDepotStat {
    private String goodsId;
    private boolean isEmpty;

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public void setEmpty(boolean empty) {
        isEmpty = empty;
    }
}
