package cn.forbearance.mnt.service.impl;

import cn.forbearance.mnt.domain.ServerDeploy;
import cn.forbearance.mnt.repository.ServerDeployRepository;
import cn.forbearance.mnt.service.ServerDeployService;
import cn.forbearance.mnt.service.dto.ServerDeployQueryCriteria;
import cn.forbearance.utils.enums.ServerTypeEnum;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * @author cristina
 */
@Service
@RequiredArgsConstructor
public class ServerDeployServiceImpl extends ServiceImpl<ServerDeployRepository, ServerDeploy> implements ServerDeployService {

    private final ServerDeployRepository serverDeployRepository;

    @Override
    public List<ServerDeploy> queryAll(ServerDeployQueryCriteria criteria) {
        LambdaQueryWrapper<ServerDeploy> queryWrapper = Wrappers.lambdaQuery(ServerDeploy.class)
                .eq(ServerDeploy::getType, criteria.getType());
        return this.list(queryWrapper);
    }

    @Override
    public ServerDeploy findById(Long id) {
        return this.getById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(ServerDeploy resources) {
        if (Objects.isNull(resources.getName())) {
            resources.setName(resources.getIp() + "@" + resources.getPort());
        }
        this.save(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ServerDeploy resources) {
        this.saveOrUpdate(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Set<Long> ids) {
        this.removeBatchByIds(ids);
    }

    @Override
    public ServerDeploy findByIp(String ip) {
        LambdaQueryWrapper<ServerDeploy> queryWrapper = Wrappers.lambdaQuery(ServerDeploy.class)
                .eq(ServerDeploy::getIp, ip);
        return this.getOne(queryWrapper);
    }

    @Override
    public Boolean testConnect(ServerDeploy resources) {
        return null;
    }
}
