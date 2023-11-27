package cn.forbearance.rest;

import cn.forbearance.domain.ZkNodeChildren;
import cn.forbearance.domain.ZookeeperInfo;
import cn.forbearance.service.ZookeeperPanelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.apache.zookeeper.data.Stat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author cristina
 */
@RestController
@Api(tags = "zookeeper：服务面板管理")
@RequiredArgsConstructor
@RequestMapping("/api/zookeeperPanel")
public class ZookeeperPanelController {

    private final ZookeeperPanelService zkPanelService;

    @ApiOperation(value = "查询子节点")
    @PostMapping("/child")
    public ResponseEntity<List<ZkNodeChildren>> getChildren(@RequestBody ZookeeperInfo zkInfo) {
        return new ResponseEntity<>(zkPanelService.getChildren(zkInfo), HttpStatus.OK);
    }

    @ApiOperation(value = "获取节点数据")
    @PostMapping("/data")
    public ResponseEntity<String> getData(@RequestBody ZookeeperInfo zkInfo) {
        return new ResponseEntity<>(zkPanelService.getData(zkInfo), HttpStatus.OK);
    }

    @ApiOperation(value = "获取节点信息")
    @PostMapping("/stat")
    public ResponseEntity<Stat> getStat(@RequestBody ZookeeperInfo zkInfo) {
        return new ResponseEntity<>(zkPanelService.getStat(zkInfo), HttpStatus.OK);
    }

}
