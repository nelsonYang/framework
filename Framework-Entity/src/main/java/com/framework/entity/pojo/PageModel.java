package com.framework.entity.pojo;

import java.util.List;

/**
 *
 * @author nelson
 */
public class PageModel<T extends Entity> {

    private int totalCount;
    private int totalPage;
    private int pageCount;
    private int pageNo;
    private List<T> dataList;

    /**
     * @return the totalCount
     */
    public int getTotalCount() {
        return totalCount;
    }

    /**
     * @param totalCount the totalCount to set
     */
    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    /**
     * @return the totalPage
     */
    public int getTotalPage() {
        if (totalCount % pageCount == 0) {
            totalPage = totalCount / pageCount;
        } else {
            totalPage = (totalCount / pageCount) + 1;
        }

        return totalPage;
    }

    /**
     * @param totalPage the totalPage to set
     */
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    /**
     * @return the pageCount
     */
    public int getPageCount() {
        return pageCount;
    }

    /**
     * @param pageCount the pageCount to set
     */
    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    /**
     * @return the pageNo
     */
    public int getPageNo() {
        return pageNo;
    }

    /**
     * @param pageNo the pageNo to set
     */
    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    /**
     * @return the dataList
     */
    public List<T> getDataList() {
        return dataList;
    }

    /**
     * @param dataList the dataList to set
     */
    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }

    public int getStartIndex() {
        return (pageNo - 1) * pageCount;
    }
}
