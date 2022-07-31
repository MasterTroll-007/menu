package cz.utils;

import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

public class PageWrapper<T> {

    /**
     * The constant MAX_PAGE_ITEM_DISPLAY.
     */
    public static final int MAX_PAGE_ITEM_DISPLAY = 4;
    public static final int DEFAULT_PAGE_SIZE = 25;
    public static final int[] sizes = {DEFAULT_PAGE_SIZE, 50, 100, 1000, 10000};
    private Page<T> page;
    private List<PageItem> items;
    private int currentNumber;
    private Long total;

    /**
     * Instantiates a new Page wrapper.
     *
     * @param page the page
     */
    public PageWrapper(Page<T> page) {
        this.page = page;
        items = new ArrayList<>();

        currentNumber = page.getNumber() + 1; //start from 1 to match page.page

        int start, size;
        if (page.getTotalPages() <= MAX_PAGE_ITEM_DISPLAY) {
            start = 1;
            size = page.getTotalPages();
        } else {
            if (currentNumber <= MAX_PAGE_ITEM_DISPLAY - MAX_PAGE_ITEM_DISPLAY / 2) {
                start = 1;
                size = MAX_PAGE_ITEM_DISPLAY;
            } else if (currentNumber >= page.getTotalPages() - MAX_PAGE_ITEM_DISPLAY / 2) {
                start = page.getTotalPages() - MAX_PAGE_ITEM_DISPLAY + 1;
                size = MAX_PAGE_ITEM_DISPLAY;
            } else {
                start = currentNumber - MAX_PAGE_ITEM_DISPLAY / 2 + 1;
                size = MAX_PAGE_ITEM_DISPLAY;
            }
        }

        for (int i = 0; i < size; i++) {
            if (size - 1 == i) {
                if (currentNumber < getTotalPages() - 2) {
                    items.add(new PageItem(null, false));
                }
                items.add(new PageItem(getTotalPages(), getTotalPages() == currentNumber));
            } else {
                items.add(new PageItem(start + i, (start + i) == currentNumber));
            }
        }
    }

    /**
     * Gets items.
     *
     * @return the items
     */
    public List<PageItem> getItems() {
        return items;
    }

    /**
     * Gets number.
     *
     * @return the number
     */
    public int getNumber() {
        return currentNumber;
    }

    /**
     * Gets content.
     *
     * @return the content
     */
    public List<T> getContent() {
        return page.getContent();
    }

    /**
     * Gets size.
     *
     * @return the size
     */
    public int getSize() {
        return page.getSize();
    }

    /**
     * Gets total pages.
     *
     * @return the total pages
     */
    public int getTotalPages() {
        return page.getTotalPages();
    }

    /**
     * Is first page.
     *
     * @return the boolean
     */
    public boolean isFirst() {
        return page.isFirst();
    }

    /**
     * Is last page.
     *
     * @return the boolean
     */
    public boolean isLast() {
        return page.isLast();
    }

    /**
     * Is has previous page.
     *
     * @return the boolean
     */
    public boolean isHasPrevious() {
        return !page.isFirst();
    }

    /**
     * Is has next page.
     *
     * @return the boolean
     */
    public boolean isHasNext() {
        return !page.isLast();
    }

    /**
     * Gets total elements.
     *
     * @return the total elements
     */
    public long getTotalElements() {
        if (this.total != null)
            return this.total;
        return page.getTotalElements();
    }

    public void setTotalElements(long total) {
        this.total = total;
    }

    /**
     * The type Page item.
     */
    public class PageItem {

        private Integer number;
        private boolean current;

        /**
         * Instantiates a new Page item.
         *
         * @param number  the number
         * @param current the current
         */
        public PageItem(Integer number, boolean current) {
            this.number = number;
            this.current = current;
        }

        /**
         * Gets number.
         *
         * @return the number
         */
        public Integer getNumber() {
            return this.number;
        }

        /**
         * Is current.
         *
         * @return the boolean
         */
        public boolean isCurrent() {
            return this.current;
        }
    }

}


