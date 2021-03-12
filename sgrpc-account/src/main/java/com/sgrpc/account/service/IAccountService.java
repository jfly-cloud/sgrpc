package com.sgrpc.account.service;

import com.sgrpc.account.entity.Account;
import com.baomidou.mybatisplus.extension.service.IService;
import java.math.BigDecimal;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ning
 * @since 2021-03-11
 */
public interface IAccountService extends IService<Account> {
    void decrease(Long userId,  BigDecimal money);
}
