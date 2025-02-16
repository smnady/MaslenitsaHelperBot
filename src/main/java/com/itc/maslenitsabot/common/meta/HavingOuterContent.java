package com.itc.maslenitsabot.common.meta;

/**
 * Интерфейс должны реализововать сущности, подробная информация о которых хранится
 * во внешних файлах и используется в приложении.
 *
 * @author smnadya
 * @since 2025.01.25
 */
public interface HavingOuterContent {

    /**
     * @return путь к директории с файлами, содержащими контент.
     */
    String getPathToDirectoryWithFiles();

    /**
     * @return расширение файла с точкой.
     * @Example: {@code .html}
     */
    String getFileTypeLikeSuffix();

}
