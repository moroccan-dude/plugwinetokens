package com.plugwine.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Classe contenant les paramètres de tri et de filtrage pour les requêtes de recherche.
 * 
 * @author vboriesazeau
 * @version 1.0.0
 */
public class SortFilterPagingCriteria implements Cloneable {

    /**
     * Tri ascendant
     */
    public static final String SORT_ORDER_ASC = "asc";
    /**
     * Tri descendant
     */
    public static final String SORT_ORDER_DESC = "desc";

    /**
     * Obtenir tous les résultats en une fois (pas de pagination)
     */
    public static final int ALL_PAGES = -1;

    /**
     * Ordre de tri
     */
    private String sortOrder;
    /**
     * Critère de tri (peut-être <code>null</code>)
     */
    private String sortCriteria;
    /**
     * Critères de filtrage (nom, valeur)
     */
    private Map<String, String> filterCriteria;
    /**
     * Numéro de page à afficher
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
