package com.rjuarez.core.model;

import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResultsPage<T> extends AbstractJsonMapping implements Iterable<T> {

    @JsonProperty("results")
    private List<T> results;

    @JsonProperty("page")
    private int page;

    @JsonProperty("total_pages")
    private int totalPages;

    @JsonProperty("total_results")
    private int totalResults;

    // needed?
    // @JsonProperty("dates")
    // private ResultDates dates = new ResultDates();

    @Override
    public Iterator<T> iterator() {
        return results.iterator();
    }

    // public static class PagedList<T> extends ArrayList<T>{
    // final ResultsPage<T> pagingResults;
    //
    // PagedList(ResultsPage<T> pagingResults) {
    // this.pagingResults = pagingResults;
    // addAll(pagingResults.results);
    // }
    //
    //
    // public ResultsPage<T> getResultPage() {
    // return pagingResults;
    // }
    // }

    public List<T> getResults() {
        return results;
    }

    public int getPage() {
        return page;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setPage(final int page) {
        this.page = page;
    }

    public void setTotalPages(final int totalPages) {
        this.totalPages = totalPages;
    }

    public void setTotalResults(final int totalResults) {
        this.totalResults = totalResults;
    }

    // public ResultDates getDates() {
    // return dates;
    // }
    //
    //
    // public void setDates(ResultDates dates) {
    // this.dates = dates;
    // }
}
