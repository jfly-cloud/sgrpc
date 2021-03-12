package com.sgrpc.order.grpc;

import com.google.protobuf.ByteString;
import com.sgrpc.proto.*;
import io.grpc.StatusRuntimeException;
import io.seata.integration.grpc.interceptor.client.ClientTransactionInterceptor;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AccountApi {

    @GrpcClient(value = "account-server", interceptors = ClientTransactionInterceptor.class)
    private SimpleGrpc.SimpleBlockingStub simpleStub;


    public void adecrease(final Long userId, final BigDecimal money) {
        try {
            DecimalValue serialized = DecimalValue.newBuilder()
                    .setScale(money.scale())
                    .setPrecision(money.precision())
                    .setValue(ByteString.copyFrom(money.unscaledValue().toByteArray()))
                    .build();
            this.simpleStub.adecrease(AccountRequest.newBuilder().setUserId(userId).setMoney(serialized).build());
        } catch (final StatusRuntimeException e) {
            throw new RuntimeException("FAILED with " + e.getStatus().getCode());
        }
    }
}
