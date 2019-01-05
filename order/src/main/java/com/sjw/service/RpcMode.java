package com.sjw.service;

import com.sjw.rpc.DepotService;
import com.sjw.rpc.RpcProxy;
import com.sjw.vo.GoodTransferVo;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.net.InetSocketAddress;

/**
 * 用rpc处理库存减少
 */
@Service
@Qualifier("rpc")
public class RpcMode implements IProDepot {

    private static final String IP = "127.0.0.1";
    private static final int PORT = 10002;

    public void processDepot(String goodsId, int amount) {

        DepotService service = RpcProxy.getRmoteProxyObj(DepotService.class,
                new InetSocketAddress(IP,PORT));
        GoodTransferVo goodTransferVo = new GoodTransferVo();
        goodTransferVo.setGoodsId(goodsId);
        goodTransferVo.setChangeAmount(amount);
        goodTransferVo.setInOrOut(true);
        service.changeDepot(goodTransferVo);
    }
}
