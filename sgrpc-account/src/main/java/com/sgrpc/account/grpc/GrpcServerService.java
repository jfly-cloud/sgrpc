package com.sgrpc.account.grpc;
import com.google.protobuf.Empty;
import com.sgrpc.account.service.IAccountService;
import com.sgrpc.proto.AccountRequest;
import com.sgrpc.proto.SimpleGrpc;
import io.grpc.stub.StreamObserver;
import io.seata.integration.grpc.interceptor.server.ServerTransactionInterceptor;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.MathContext;

@GrpcService(interceptors = ServerTransactionInterceptor.class)
public class GrpcServerService extends SimpleGrpc.SimpleImplBase {

    private static final Logger LOGGER = LoggerFactory.getLogger(GrpcServerService.class);

    @Override
    public void adecrease(AccountRequest req, StreamObserver<Empty> responseObserver) {
        LOGGER.info("------->扣减账户开始account中");
        //模拟异常，全局事务回滚
        //int i= 1/0;
        IAccountService service = SpringUtil.getBean(IAccountService.class);
        MathContext mc = new MathContext(req.getMoney().getPrecision());
        BigDecimal deserialized = new BigDecimal(new java.math.BigInteger(req.getMoney().getValue().toByteArray()),
                req.getMoney().getScale(), mc);
        service.decrease(req.getUserId(), deserialized);
        Empty reply = Empty.newBuilder().build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
        LOGGER.info("------->扣减账户结束account中");//修改订单状态，此调用会导致调用成环
    }
}
