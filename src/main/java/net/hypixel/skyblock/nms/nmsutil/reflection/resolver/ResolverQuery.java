package net.hypixel.skyblock.nms.nmsutil.reflection.resolver;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class ResolverQuery
{
    private String name;
    private final Class<?>[] types;
    
    public ResolverQuery(final String name, final Class<?>... types) {
        this.name = name;
        this.types = types;
    }
    
    public ResolverQuery(final String name) {
        this.name = name;
        this.types = new Class[0];
    }
    
    public ResolverQuery(final Class<?>... types) {
        this.types = types;
    }
    
    public String getName() {
        return this.name;
    }
    
    public Class<?>[] getTypes() {
        return this.types;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final ResolverQuery that = (ResolverQuery)o;
        if (this.name != null) {
            if (this.name.equals((Object)that.name)) {
                return Arrays.equals((Object[])this.types, (Object[])that.types);
            }
        }
        else if (that.name == null) {
            return Arrays.equals((Object[])this.types, (Object[])that.types);
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        int result = (this.name != null) ? this.name.hashCode() : 0;
        result = 31 * result + ((this.types != null) ? Arrays.hashCode((Object[])this.types) : 0);
        return result;
    }
    
    @Override
    public String toString() {
        return "ResolverQuery{name='" + this.name + '\'' + ", types=" + Arrays.toString((Object[])this.types) + '}';
    }
    
    public static Builder builder() {
        return new Builder();
    }
    
    public static class Builder
    {
        private final List<ResolverQuery> queryList;
        
        private Builder() {
            this.queryList = (List<ResolverQuery>)new ArrayList();
        }
        
        public Builder with(final String name, final Class<?>[] types) {
            this.queryList.add((Object)new ResolverQuery(name, types));
            return this;
        }
        
        public Builder with(final String name) {
            this.queryList.add((Object)new ResolverQuery(name));
            return this;
        }
        
        public Builder with(final Class<?>[] types) {
            this.queryList.add((Object)new ResolverQuery(types));
            return this;
        }
        
        public ResolverQuery[] build() {
            return (ResolverQuery[])this.queryList.toArray((Object[])new ResolverQuery[this.queryList.size()]);
        }
    }
}
