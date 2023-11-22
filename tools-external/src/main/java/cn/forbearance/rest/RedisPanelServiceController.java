package cn.forbearance.rest;

import cn.forbearance.domain.Cursor;
import cn.forbearance.domain.RedisServer;
import cn.forbearance.service.RedisPanelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author cristina
 */
@RestController
@Api(tags = "Redis：服务面板管理")
@RequiredArgsConstructor
@RequestMapping("/api/redisPanel")
public class RedisPanelServiceController {

    private final RedisPanelService redisPanelService;

    @ApiOperation(value = "查询所有key")
    @PostMapping("/all")
    public ResponseEntity<Cursor<Object>> queryKeys(@RequestParam(required = false) String pattern,
                                                    @RequestParam int position,
                                                    @RequestParam int count,
                                                    @RequestBody RedisServer server) {
        return new ResponseEntity<>(redisPanelService.queryAllKeys(pattern, position, count, server), HttpStatus.OK);
    }

    @ApiOperation(value = "查询key")
    @PostMapping
    public ResponseEntity<Object> getValue(@RequestParam String key,
                                           @RequestBody RedisServer server) {
        return new ResponseEntity<>(redisPanelService.getValue(key, server), HttpStatus.OK);
    }

    @ApiOperation(value = "设置key")
    @PutMapping
    public ResponseEntity<Object> setValue(@RequestParam String key,
                                           @RequestParam String value,
                                           @RequestBody RedisServer server) {
        redisPanelService.setValue(key, value, server);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ApiOperation(value = "删除key")
    @DeleteMapping
    public ResponseEntity<Object> deleteKey(@RequestParam String key,
                                            @RequestBody RedisServer server) {
        redisPanelService.delete(key, server);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
