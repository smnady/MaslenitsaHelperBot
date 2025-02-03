package com.itc.maslenitsabot.common;

import com.itc.maslenitsabot.common.meta.Descriable;
import com.itc.maslenitsabot.common.meta.HavingOuterContent;

/**
 * Рецепты, представленные в боте.
 *
 * @author smnadya
 * @since 2025.01.25
 */
public enum Recipe implements Descriable, HavingOuterContent {

    CLASSIC("Classic_Pancakes", "Классические блины"),
    ON_KEFIR("Pancakes_On_Kefir", "Блины на кефире"),
    WITH_COTTAGE_CHEESE("Pancakes_With_Cottage_Cheese", "Блины с начинкой из творога"),
    BUCKWHEAT("Buckwheat_Pancakes", "Гречневые блины"),
    WITH_APPLES("Pancakes_With_Apples", "Блины с яблоками"),
    WITH_RED_CAVIAR("Pancakes_With_Red_Caviar", "Блины с красной икрой"),
    OAT("Oat_Pancakes", "Овсяные блины"),
    WITH_SPINACH_AND_CHEESE("Pancakes_With_Spinach_And_Cheese", "Блины с шпинатом и сыром"),
    WITH_BANANA_AND_CHOCOLATE("Pancakes_With_Banana_And_Chocolate", "Блины с бананом и шоколадом");

    private static final String PATH_TO_DIR = "src/main/resources/static/recipes.pancakes/";
    private static final String FILE_SUFFIX = ".html";
    private final String key;
    private final String description;

    Recipe(String key, String description) {
        this.key = key;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getKey() {
        return key;
    }

    public String toString() {
        return description;
    }

    @Override
    public String getPathToDirectoryWithFiles() {
        return PATH_TO_DIR;
    }

    @Override
    public String getFileTypeLikeSuffix() {
        return FILE_SUFFIX;
    }

}
