package com.sjw.rpc;

import com.sjw.vo.GoodTransferVo;

/**
 * 远程rpc接口
 */
public interface DepotService {
    public void changeDepot(GoodTransferVo goodTransferVo);
}
