package cn.forbearance.utils;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * @author cristina
 */
@Getter
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class PageResult<T> {

    private final List<T> content;

    private final long totalElements;
}
