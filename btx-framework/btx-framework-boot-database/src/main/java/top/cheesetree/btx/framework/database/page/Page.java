package top.cheesetree.btx.framework.database.page;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import top.cheesetree.btx.framework.core.model.ValueObject;

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
