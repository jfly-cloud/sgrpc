package com.sgrpc.account.service.impl;

import com.sgrpc.account.entity.Account;
import com.sgrpc.account.mapper.AccountMapper;
import com.sgrpc.account.service.IAccountService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.seata.core.context.RootContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ning
 * @since 2021-03-11
 */
@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements IAccountService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Override
    public void decrease(Long userId, BigDecimal money) {
        LOGGER.info("当前 XID: {}", RootContext.getXID());
        getBaseMapper().decrease(userId,money);
    }
}
