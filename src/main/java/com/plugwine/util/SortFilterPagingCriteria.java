package com.plugwine.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Class that encapsulates all sorting and filtering for search requests
 * 
 */
public class SortFilterPagingCriteria implements Cloneable {

    /**
     * asc order
     */
    public static final String SORT_ORDER_ASC = "asc";
    /**
     * desc order
     */
    public static final String SORT_ORDER_DESC = "desc";

    /**
     * no pagination (get all at once)
     */
    public static final int ALL_PAGES = -1;

    /**
     * Sorting order
     */
    private String sortOrder;
    /**
     * sorting criteria (can be <code>null</code>)
     */
    private String sortCriteria;
    /**
     * filtering criteria (name, value...)
     */
    private Map<String, String> filterCriteria;
    /**
     * page number to return
     */
    private int page;

    public SortFilterPagingCriteria() {
        sortOrder = SORT_ORDER_ASC;
        sortCriteria = null;
        filterCriteria = new HashMap<String, String>();
        page = ALL_PAGES;
    }

    public String getSortCriteria() {
        return sortCriteria;
    }

    public void setSortCriteria(String sortCriteria) {
        this.sortCriteria = sortCriteria;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Map<String, String> getFilterCriteria() {
        return filterCriteria;
    }

    public void setFilterCriteria(Map<String, String> filterCriteria) {
        this.filterCriteria = filterCriteria;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object clone() {
        SortFilterPagingCriteria sortFilterPagingCriteria = null;
        try {
            sortFilterPagingCriteria = (SortFilterPagingCriteria) super.clone();
            sortFilterPagingCriteria.setFilterCriteria(new HashMap<String, String>());
            sortFilterPagingCriteria.getFilterCriteria().putAll(getFilterCriteria());
        } catch (CloneNotSupportedException e) {
            // Do nothing, but return null
        }
        return sortFilterPagingCriteria;
    }
}
