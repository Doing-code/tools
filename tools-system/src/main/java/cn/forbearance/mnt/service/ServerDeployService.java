package cn.forbearance.mnt.service;

import cn.forbearance.mnt.domain.ServerDeploy;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * @author cristina
 */
public interface ServerDeployService extends IService<ServerDeploy> {

    /**
     * 分页查询
     *
     * @param criteria 条件
     * @param pageable 分页参数
     * @return /
     */

    /**
     * 查询全部数据
     *
     * @param criteria 条件
     * @return /
     */
    List<ServerDeploy> queryAll();

    /**
     * 根据ID查询
     *
     * @param id /
     * @return /
     */
    ServerDeploy findById(Long id);

    /**
     * 创建
     *
     * @param resources /
     */
    void create(ServerDeploy resources);

    /**
     * 编辑
     *
     * @param resources /
     */
    void update(ServerDeploy resources);

    /**
     * 删除
     *
     * @param ids /
     */
    void delete(Set<Long> ids);

    /**
     * 根据IP查询
     *
     * @param ip /
     * @return /
     */
    ServerDeploy findByIp(String ip);

    /**
     * 测试登录服务器
     *
     * @param resources /
     * @return /
     */
    Boolean testConnect(ServerDeploy resources);

    /**
     * 导出数据
     *
     * @param queryAll /
     * @param response /
     * @throws IOException /
     */
}
