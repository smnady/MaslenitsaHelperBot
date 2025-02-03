package com.itc.maslenitsabot.common.meta;

/**
 * Интерфейс должны реализововать сущности, подробная информация о которых хранится
 * во внешних файлах и используется в приложении.
 *
 * @author smnadya
 * @since 2025.01.25
 */
public interface HavingOuterContent {

    String getPathToDirectoryWithFiles();

    String getFileTypeLikeSuffix();

}
