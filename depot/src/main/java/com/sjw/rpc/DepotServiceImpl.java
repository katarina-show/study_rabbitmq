package com.sjw.rpc;

import com.sjw.service.DepotManager;
import com.sjw.vo.GoodTransferVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 远程rpc接口的实现
 */
@Component
public class DepotServiceImpl implements DepotService {

    @Autowired
    private DepotManager depotManager;

    @Override
    public void changeDepot(GoodTransferVo goodTransferVo) {
        depotManager.operDepot(goodTransferVo);
    }
}
