package it.sevenbits.spring_boot.task_manager.web.model;

import java.util.Objects;

/**
 * Model for information about pages in application and tasks
 */
public class GetTasksMeta {
    private int total;
    private String status;
    private int page;
    private int size;
    private String order;
    private String first;
    private String last;
    private String next;
    private String prev;

    /**
     * Constructor
     *
     * @param total  count tasks in repository
     * @param status status tasks
     * @param page   number current page
     * @param size   count tasks on page
     * @param order  for sorting on date
     * @param first  first page
     * @param last   last page
     * @param next   next page
     * @param prev   preview page
     */
    public GetTasksMeta(final int total,
                        final String status,
                        final int page,
                        final int size,
                        final String order,
                        final String first,
                        final String last,
                        final String next,
                        final String prev) {
        this.total = total;
        this.status = status;
        this.page = page;
        this.size = size;
        this.order = order;
        this.first = first;
        this.last = last;
        this.next = next;
        this.prev = prev;
    }

    /**
     * Getter for status
     *
     * @return status
     */
    public String getStatus() {
        return status;
    }

    /**
     * setter for status
     *
     * @param status new status
     */
    public void setStatus(final String status) {
        this.status = status;
    }

    /**
     * Getter for page
     *
     * @return page
     */
    public int getPage() {
        return page;
    }

    /**
     * Setter for page
     *
     * @param page new page
     */
    public void setPage(final int page) {
        this.page = page;
    }

    /**
     * Getter for size
     *
     * @return size
     */
    public int getSize() {
        return size;
    }

    /**
     * Setter for size
     *
     * @param size new size
     */
    public void setSize(final int size) {
        this.size = size;
    }

    /**
     * Getter for order
     *
     * @return order
     */
    public String getOrder() {
        return order;
    }

    /**
     * setter for Order
     *
     * @param order new order
     */
    public void setOrder(final String order) {
        this.order = order;
    }

    /**
     * Getter for first page
     *
     * @return first page
     */
    public String getFirst() {
        return first;
    }

    /**
     * Setter for first page
     *
     * @param first new first page
     */
    public void setFirst(final String first) {
        this.first = first;
    }

    /**
     * Getter for last page
     *
     * @return last page
     */
    public String getLast() {
        return last;
    }

    /**
     * Setter for new last page
     *
     * @param last new last page
     */
    public void setLast(final String last) {
        this.last = last;
    }

    /**
     * Getter for next page
     *
     * @return next page
     */
    public String getNext() {
        return next;
    }

    /**
     * Setter for next page
     *
     * @param next new next page
     */
    public void setNext(final String next) {
        this.next = next;
    }

    /**
     * Getter for preview page
     *
     * @return preview page
     */
    public String getPrev() {
        return prev;
    }

    /**
     * Setter for preview page
     *
     * @param prev new preview page
     */
    public void setPrev(final String prev) {
        this.prev = prev;
    }

    /**
     * Getter for count page
     *
     * @return count page
     */
    public int getTotal() {
        return total;
    }

    /**
     * Setter for count page
     *
     * @param total new count page
     */
    public void setTotal(final int total) {
        this.total = total;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final GetTasksMeta that = (GetTasksMeta) o;
        return page == that.page &&
                size == that.size &&
                Objects.equals(status, that.status) &&
                Objects.equals(order, that.order) &&
                Objects.equals(first, that.first) &&
                Objects.equals(last, that.last) &&
                Objects.equals(next, that.next) &&
                Objects.equals(prev, that.prev);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status, page, size, order, first, last, next, prev);
    }


}
