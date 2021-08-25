package top.cheesetree.btx.framework.database.page;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PageResult<T> extends Page {
    /**
     * 总记录数
     */
    private long total = 0;

    /**
     * 数据
     */
    private List<T> data;

    public PageResult(long pageIndex, long pageSize, long total, List<T> data) {
        super(pageIndex, pageSize);
        this.total = total;
        this.data = data;
    }

    /**
     * 获取总页数
     *
     * @return 总页数.
     */
    public long getPageCount() {
        if (this.total <= 0L || this.getPageSize() <= 0L) {
            return 0L;
        } else if ((this.total % this.getPageSize()) == 0L) {
            return this.total / this.getPageSize();
        } else {
            return this.total / this.getPageSize() + 1;
        }
    }

    /**
     * 获取最后一页的页数.
     *
     * @return 最后一页页数.
     */
    public long getLastPageIndex() {
        if (this.total <= 0L || this.getPageSize() <= 0L) {
            return 0L;
        } else if ((this.total % this.getPageSize()) == 0L) {
            return this.total / this.getPageSize() - 1;
        } else {
            return this.total / this.getPageSize();
        }
    }
}
