package com.edsonmoreirajr.votacao;

import org.springframework.hateoas.EntityModel;

import java.util.ArrayList;
import java.util.List;

public final class HateaosUtilsForTests<T> {

    HateaosUtilsForTests() {
    }

    public static <T> List<EntityModel<T>> buildEntityModelList(List<T> content) {
        List<EntityModel<T>> entityModelList = new ArrayList<>();
        for (T object : content) {
            entityModelList.add(EntityModel.of(object));
        }
        return entityModelList;
    }
}
