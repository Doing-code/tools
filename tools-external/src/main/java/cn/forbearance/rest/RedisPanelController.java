package cn.forbearance.rest;

import cn.forbearance.domain.Cursor;
import cn.forbearance.domain.RedisInfo;
import cn.forbearance.service.RedisPanelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * TODO 接口调用需优化为策略模式调用
 *
 * @author cristina
 */
@RestController
@Api(tags = "Redis：服务面板管理")
@RequiredArgsConstructor
@RequestMapping("/api/redisPanel")
public class RedisPanelController {

    private final RedisPanelService redisPanelService;

    @ApiOperation(value = "查询所有key")
    @PostMapping("/all")
    public ResponseEntity<Cursor<Object>> queryKeys(@RequestBody RedisInfo server) {
        return new ResponseEntity<>(redisPanelService.queryAllKeys(server), HttpStatus.OK);
    }

    @ApiOperation(value = "查询key")
    @PostMapping
    public ResponseEntity<Object> getValue(@RequestBody RedisInfo server) {
        return new ResponseEntity<>(redisPanelService.getValue(server), HttpStatus.OK);
    }

    @ApiOperation(value = "设置key")
    @PutMapping
    public ResponseEntity<Object> setValue(@RequestBody RedisInfo server) {
        redisPanelService.setValue(server);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ApiOperation(value = "删除key")
    @DeleteMapping
    public ResponseEntity<Object> deleteKey(@RequestBody RedisInfo server) {
        redisPanelService.delete(server);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
