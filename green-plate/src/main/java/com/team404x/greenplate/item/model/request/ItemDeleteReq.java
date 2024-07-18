package com.team404x.greenplate.item.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ItemDeleteReq {
    private final long company_id;
    private final long item_id;
}
