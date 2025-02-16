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

    private static final String FILE_SUFFIX = ".html";

    /**
     * Переопределяется в {@code application.properties} - зависит от среды запуска.<br>
     * По умолчанию задан путь для запуска в докере.
     */
    private static String pathToDir = "resources/static/recipes.pancakes/";

    /**
     * Наименование файла с информацией о рецепте.
     */
    private final String key;

    /**
     * Пользовательское название рецепта.
     */
    private final String description;

    Recipe(String key, String description) {
        this.key = key;
        this.description = description;
    }

    public static void setPathToDir(String pathToDir) {
        Recipe.pathToDir = pathToDir;
    }

    /**
     * @return пользовательское название рецепта.
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return наименование файла с информацией о рецепте, без расширения файла.
     */
    public String getKey() {
        return key;
    }

    public String toString() {
        return description;
    }

    @Override
    public String getPathToDirectoryWithFiles() {
        return pathToDir;
    }

    @Override
    public String getFileTypeLikeSuffix() {
        return FILE_SUFFIX;
    }

}
