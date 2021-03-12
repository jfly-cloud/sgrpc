package com.sgrpc.storage.grpc;

import com.google.protobuf.Empty;
import com.sgrpc.proto.SimpleGrpc;
import com.sgrpc.proto.StorageRequest;
import com.sgrpc.storage.service.IStorageService;
import io.grpc.stub.StreamObserver;
import io.seata.integration.grpc.interceptor.server.ServerTransactionInterceptor;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@GrpcService(interceptors = ServerTransactionInterceptor.class)
public class GrpcServerService extends SimpleGrpc.SimpleImplBase {

    private static final Logger LOGGER = LoggerFactory.getLogger(GrpcServerService.class);


    @Override
    public void sdecrease(StorageRequest req, StreamObserver<Empty> responseObserver) {
        LOGGER.info("------->扣减库存开始");
        IStorageService service = SpringUtil.getBean(IStorageService.class);
        service.decrease(req.getProductId(),req.getCount());
        Empty reply = Empty.newBuilder().build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
        LOGGER.info("------->扣减库存结束");
    }
}
