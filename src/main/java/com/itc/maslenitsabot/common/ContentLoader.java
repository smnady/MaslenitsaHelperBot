package com.itc.maslenitsabot.common;

import com.itc.maslenitsabot.common.meta.Descriable;
import com.itc.maslenitsabot.common.meta.HavingOuterContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * Загружает информацию о сущностях из внешних ресурсов (файловой системы).
 *
 * @author smnadya
 * @since 2025.01.25
 */
public class ContentLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(ContentLoader.class);

    private static final String BASE_ERROR_MESSAGE = "Произошла ошибка при попытке чтения файла.";

    private static final Map<String, String> cache = new HashMap<>();

    public static <T extends Descriable & HavingOuterContent> String getRepresentation(T entity) {
        final String fullFileName = entity.getPathToDirectoryWithFiles() +
                entity.getKey() +
                entity.getFileTypeLikeSuffix();

        return cache.computeIfAbsent(fullFileName, ContentLoader::loadContent);
    }

    private static String loadContent(String fileName) {
        try {
            return new String(Files.readAllBytes(Paths.get(fileName)));
        } catch (NoSuchFileException e) {
            LOGGER.error(BASE_ERROR_MESSAGE + " Файла не существует: {}", fileName);
            throw new RuntimeException(e);
        } catch (IOException e) {
            LOGGER.error(BASE_ERROR_MESSAGE + " " + fileName);
            throw new RuntimeException(e);
        }
    }

}
