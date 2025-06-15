package com.itc.maslenitsabot.common;

import com.itc.maslenitsabot.common.meta.Descriable;
import com.itc.maslenitsabot.common.meta.HavingOuterContent;

/**
 * Станции, представленные в боте.
 *
 * @author smnadya
 * @since 2025.01.25
 */
public enum Station implements Descriable, HavingOuterContent {

    DANCING_PANS("Dancing_Pans", "Танцующие сковородки"),
    MASLENITSA_PUZZLES("Maslenitsa_Puzzles", "Масленичные пазлы"),
    BRAIDS("Braids", "Косички"),
    HAPPY_PANCAKES("Happy_Pancakes", "Весёлые блины"),
    TABLECLOTH("Tablecloth", "Скатерть-самобранка"),
    BLINOVED("Blinoved", "Блиновед"),
    WALKING_ON_STILTS("Walking_On_Stilts", "Ходьба на ходулях"),
    VALENOK_THROWING("Valenok_Throwing", "Метание валенка");

    private static final String FILE_SUFFIX = ".html";
    private static String pathToDir = "static/stations/";
    private final String key;
    private final String description;

    Station(String key, String description) {
        this.key = key;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getKey() {
        return key;
    }

    @Override
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