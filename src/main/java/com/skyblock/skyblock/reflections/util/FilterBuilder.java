package com.skyblock.skyblock.reflections.util;

import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.Objects;
import java.util.Iterator;
import com.skyblock.skyblock.reflections.ReflectionsException;
import java.util.Collection;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class FilterBuilder implements Predicate<String>
{
    private final List<Predicate<String>> chain;
    
    public FilterBuilder() {
        this.chain = (List<Predicate<String>>)new ArrayList();
    }
    
    private FilterBuilder(final Collection<Predicate<String>> filters) {
        (this.chain = (List<Predicate<String>>)new ArrayList()).addAll((Collection)filters);
    }
    
    public FilterBuilder includePackage(final String value) {
        return this.includePattern(prefixPattern(value));
    }
    
    public FilterBuilder excludePackage(final String value) {
        return this.excludePattern(prefixPattern(value));
    }
    
    public FilterBuilder includePattern(final String regex) {
        return this.add((Predicate<String>)new Include(regex));
    }
    
    public FilterBuilder excludePattern(final String regex) {
        return this.add((Predicate<String>)new Exclude(regex));
    }
    
    @Deprecated
    public FilterBuilder include(final String regex) {
        return this.add((Predicate<String>)new Include(regex));
    }
    
    @Deprecated
    public FilterBuilder exclude(final String regex) {
        this.add((Predicate<String>)new Exclude(regex));
        return this;
    }
    
    public static FilterBuilder parsePackages(final String includeExcludeString) {
        final List<Predicate<String>> filters = (List<Predicate<String>>)new ArrayList();
        for (final String string : includeExcludeString.split(",")) {
            final String trimmed = string.trim();
            final char prefix = trimmed.charAt(0);
            final String pattern = prefixPattern(trimmed.substring(1));
            switch (prefix) {
                case '+': {
                    filters.add((Object)new Include(pattern));
                    break;
                }
                case '-': {
                    filters.add((Object)new Exclude(pattern));
                    break;
                }
                default: {
                    throw new ReflectionsException("includeExclude should start with either + or -");
                }
            }
        }
        return new FilterBuilder((Collection<Predicate<String>>)filters);
    }
    
    public FilterBuilder add(final Predicate<String> filter) {
        this.chain.add((Object)filter);
        return this;
    }
    
    public boolean test(final String regex) {
        boolean accept = this.chain.isEmpty() || this.chain.get(0) instanceof Exclude;
        for (final Predicate<String> filter : this.chain) {
            if (accept && filter instanceof Include) {
                continue;
            }
            if (!accept && filter instanceof Exclude) {
                continue;
            }
            accept = filter.test((Object)regex);
            if (!accept && filter instanceof Exclude) {
                break;
            }
        }
        return accept;
    }
    
    @Override
    public boolean equals(final Object o) {
        return this == o || (o != null && this.getClass() == o.getClass() && Objects.equals((Object)this.chain, (Object)((FilterBuilder)o).chain));
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(new Object[] { this.chain });
    }
    
    @Override
    public String toString() {
        return (String)this.chain.stream().map(Object::toString).collect(Collectors.joining((CharSequence)", "));
    }
    
    private static String prefixPattern(String fqn) {
        if (!fqn.endsWith(".")) {
            fqn += ".";
        }
        return fqn.replace((CharSequence)".", (CharSequence)"\\.").replace((CharSequence)"$", (CharSequence)"\\$") + ".*";
    }
    
    abstract static class Matcher implements Predicate<String>
    {
        final Pattern pattern;
        
        Matcher(final String regex) {
            this.pattern = Pattern.compile(regex);
        }
        
        @Override
        public int hashCode() {
            return Objects.hash(new Object[] { this.pattern });
        }
        
        @Override
        public boolean equals(final Object o) {
            return this == o || (o != null && this.getClass() == o.getClass() && Objects.equals((Object)this.pattern.pattern(), (Object)((Matcher)o).pattern.pattern()));
        }
        
        @Override
        public String toString() {
            return this.pattern.pattern();
        }
    }
    
    static class Include extends Matcher
    {
        Include(final String regex) {
            super(regex);
        }
        
        public boolean test(final String regex) {
            return this.pattern.matcher((CharSequence)regex).matches();
        }
        
        @Override
        public String toString() {
            return "+" + (Object)this.pattern;
        }
    }
    
    static class Exclude extends Matcher
    {
        Exclude(final String regex) {
            super(regex);
        }
        
        public boolean test(final String regex) {
            return !this.pattern.matcher((CharSequence)regex).matches();
        }
        
        @Override
        public String toString() {
            return "-" + (Object)this.pattern;
        }
    }
}
