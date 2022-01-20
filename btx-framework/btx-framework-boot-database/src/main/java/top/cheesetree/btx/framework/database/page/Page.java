package top.cheesetree.btx.framework.database.page;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import top.cheesetree.btx.framework.core.model.ValueObject;

/**
 * @Author: van
 * @Date: 2021/8/25 17:28
 * @Description: TODO
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Page implements ValueObject {
    /**
     * 每页大小
     */
    private long pageSize = 20;
    /**
     * 当前页码
     */
    private long pageIndex = 0;

    public Page(long pageSize, long pageIndex, String sortField, String sortOrder) {
    }
}
