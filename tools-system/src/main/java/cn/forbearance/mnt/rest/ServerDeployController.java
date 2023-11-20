package cn.forbearance.mnt.rest;

import cn.forbearance.mnt.domain.ServerDeploy;
import cn.forbearance.mnt.service.ServerDeployService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * @author cristina
 */
@RestController
@Api(tags = "运维：服务器管理")
@RequiredArgsConstructor
@RequestMapping("/api/serverDeploy")
public class ServerDeployController {

    private final ServerDeployService serverDeployService;

    @ApiOperation(value = "查询服务器")
    @GetMapping
    public ResponseEntity<List<ServerDeploy>> queryServerDeploy(){
        return new ResponseEntity<>(serverDeployService.queryAll(), HttpStatus.OK);
    }

    @ApiOperation(value = "新增服务器")
    @PostMapping
    public ResponseEntity<Object> createServerDeploy(@Validated @RequestBody ServerDeploy resources){
        serverDeployService.create(resources);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @ApiOperation(value = "修改服务器")
    @PutMapping
    public ResponseEntity<Object> updateServerDeploy(@Validated @RequestBody ServerDeploy resources){
        serverDeployService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ApiOperation(value = "删除Server")
    @DeleteMapping
    public ResponseEntity<Object> deleteServerDeploy(@RequestBody Set<Long> ids){
        serverDeployService.delete(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "测试连接服务器")
    @PostMapping("/testConnect")
    public ResponseEntity<Object> testConnectServerDeploy(@Validated @RequestBody ServerDeploy resources){
        return new ResponseEntity<>(serverDeployService.testConnect(resources),HttpStatus.CREATED);
    }
}
