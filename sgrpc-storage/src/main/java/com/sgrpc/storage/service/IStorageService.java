package com.sgrpc.storage.service;

import com.sgrpc.storage.entity.Storage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ning
 * @since 2021-03-11
 */
public interface IStorageService extends IService<Storage> {

    void decrease(Long productId,Integer count);
}
