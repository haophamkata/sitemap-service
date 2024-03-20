package katalon.com.sitemapservice.dto.response;

public class PagedResult {

    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private int totalPages;

    public PagedResult(int pageNumber, int pageSize, long totalElements, int totalPages) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }

    /**
     * @return the hasMore
     */
    public boolean isHasMore() {
        return totalElements > (pageNumber) * pageSize;
    }

    /**
     * @return the hasPrevious
     */
    public boolean isHasPrevious() {
        return pageNumber > 1 && totalElements > 0;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }
}