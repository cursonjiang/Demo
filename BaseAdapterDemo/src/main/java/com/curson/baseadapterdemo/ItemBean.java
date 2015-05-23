package com.curson.baseadapterdemo;

/**
 * Created by root on 15/4/12.
 */
public class ItemBean {

    public int ItemImageResid;
    public String ItemTitle;
    public String ItemContent;

    public ItemBean(String itemContent, int itemImageResid, String itemTitle) {
        ItemContent = itemContent;
        ItemImageResid = itemImageResid;
        ItemTitle = itemTitle;
    }
}
