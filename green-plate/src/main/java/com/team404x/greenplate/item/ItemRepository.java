package com.team404x.greenplate.item;

import com.team404x.greenplate.item.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
