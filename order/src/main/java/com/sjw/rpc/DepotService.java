package com.sjw.rpc;

import com.sjw.vo.GoodTransferVo;

/**
 * 库存系统的接口
 */
public interface DepotService {

    void changeDepot(GoodTransferVo goodTransferVo);
}
