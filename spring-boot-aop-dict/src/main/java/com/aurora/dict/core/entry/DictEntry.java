package com.aurora.dict.core.entry;

import java.util.HashMap;
import java.util.function.Supplier;

/**
 * 字典
 * @author xzbcode
 */
public class DictEntry extends HashMap<String, DictItemEntry> {

    private DictEntry() {
        super();
    }

    private DictEntry(int initialCapacity) {
        super(initialCapacity);
    }

    public static DictEntry createEmpty() {
        return new DictEntry();
    }

    public static DictEntry create(int initialCapacity) {
        return new DictEntry(initialCapacity);
    }

    public DictEntry addDict(String code, DictItemEntry dictItemEntry) {
        this.put(code, dictItemEntry);
        return this;
    }

    public DictEntry addDict(String code, Supplier<DictItemEntry> supplier) {
        this.put(code, supplier.get());
        return this;
    }

    public DictItemEntry getDict(String code) {
        return this.get(code);
    }

    public Object getItemValue(String code, String itemKey) {
        DictItemEntry dictItemEntry = this.getDict(code);
        if (dictItemEntry == null) {
            return null;
        }
        return dictItemEntry.getItem(itemKey);
    }

    public String getItemValueStr(String code, String itemKey) {
        DictItemEntry dictItemEntry = this.getDict(code);
        if (dictItemEntry == null) {
            return null;
        }
        return dictItemEntry.getItemStr(itemKey);
    }

    public void removeDict(String code) {
        this.remove(code);
    }

    public void clearAllDict() {
        this.clear();
    }

    public DictEntry(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
