package net.hypixel.skyblock.util;

import java.util.Iterator;
import java.util.Collections;
import java.util.List;
import java.util.Arrays;
import java.util.Collection;
import java.util.ArrayList;

public class PaginationList<T> extends ArrayList<T>
{
    private int elementsPerPage;
    
    public PaginationList(final Collection<T> collection, final int elementsPerPage) {
        super((Collection)collection);
        this.elementsPerPage = elementsPerPage;
    }
    
    public PaginationList(final int elementsPerPage) {
        this.elementsPerPage = elementsPerPage;
    }
    
    public PaginationList(final int elementsPerPage, final T... elements) {
        super((Collection)Arrays.asList((Object[])elements));
        this.elementsPerPage = elementsPerPage;
    }
    
    public int getElementsPerPage() {
        return this.elementsPerPage;
    }
    
    public void setElementsPerPage(final int elementsPerPage) {
        this.elementsPerPage = elementsPerPage;
    }
    
    public int getPageCount() {
        return (int)Math.ceil(this.size() / (double)this.elementsPerPage);
    }
    
    public List<T> getPage(final int page) {
        if (page < 1 || page > this.getPageCount()) {
            return null;
        }
        final int startIndex = (page - 1) * this.elementsPerPage;
        final int endIndex = Math.min(startIndex + this.elementsPerPage, this.size());
        return (List<T>)this.subList(startIndex, endIndex);
    }
    
    public List<List<T>> getPages() {
        final List<List<T>> pages = (List<List<T>>)new ArrayList();
        for (int i = 1; i <= this.getPageCount(); ++i) {
            pages.add((Object)this.getPage(i));
        }
        return pages;
    }
    
    public void addAll(final T[] t) {
        Collections.addAll((Collection)this, (Object[])t);
    }
    
    public String toString() {
        final StringBuilder res = new StringBuilder();
        for (int i = 1; i <= this.getPageCount(); ++i) {
            res.append("Page ").append(i).append(": ").append("\n");
            for (final T element : this.getPage(i)) {
                res.append(" - ").append((Object)element).append("\n");
            }
        }
        return res.toString();
    }
}
