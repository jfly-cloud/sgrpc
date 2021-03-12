package com.sgrpc.storage.service.impl;

import com.sgrpc.storage.entity.Storage;
import com.sgrpc.storage.mapper.StorageMapper;
import com.sgrpc.storage.service.IStorageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.seata.core.context.RootContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ning
 * @since 2021-03-11
 */
@Service
public class StorageServiceImpl extends ServiceImpl<StorageMapper, Storage> implements IStorageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StorageServiceImpl.class);

    @Override
    public void decrease(Long productId, Integer count) {
        LOGGER.info("当前 XID: {}", RootContext.getXID());
        getBaseMapper().decrease(productId,count);
    }
}
