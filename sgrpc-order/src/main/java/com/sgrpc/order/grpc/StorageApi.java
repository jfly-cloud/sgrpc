package com.sgrpc.order.grpc;

import com.google.protobuf.Empty;
import com.sgrpc.proto.SimpleGrpc;
import com.sgrpc.proto.StorageRequest;
import io.grpc.StatusRuntimeException;
import io.seata.integration.grpc.interceptor.client.ClientTransactionInterceptor;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
public class StorageApi {

    @GrpcClient(value = "storage-server",interceptors = ClientTransactionInterceptor.class)
    private SimpleGrpc.SimpleBlockingStub simpleStub;


    public void sdecrease(final Long productId, final Integer count) {
        try {
            this.simpleStub.sdecrease(StorageRequest.newBuilder().setProductId(productId).setCount(count).build());
        } catch (final StatusRuntimeException e) {
            throw new RuntimeException("FAILED with " + e.getStatus().getCode());
        }
    }
}
