package pers.jd.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import pers.jd.entity.StorageRecord;
import pers.jd.mapper.StorageRecordMapper;
import pers.jd.service.IStorageRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 仓储记录表 服务实现类
 * </p>
 *
 * @author jd
 * @since 2022-03-13
 */
@Service
public class StorageRecordServiceImpl extends ServiceImpl<StorageRecordMapper, StorageRecord> implements IStorageRecordService {
    @Resource
    StorageRecordMapper storageRecordMapper;

    /**
     * 分页查询
     */
    public List<StorageRecord> getAllPaging(Page<StorageRecord> page, QueryWrapper<StorageRecord> queryWrapper) {

        Page<StorageRecord> paging = storageRecordMapper.selectPage(page, queryWrapper);
        List<StorageRecord> pagingData = paging.getRecords();

        pagingData.forEach(System.out::println);
        return pagingData;
    }
}
