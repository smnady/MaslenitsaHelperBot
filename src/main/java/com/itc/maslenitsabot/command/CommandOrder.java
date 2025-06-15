package com.itc.maslenitsabot.command;

import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;

/**
 * Константы, определяющие порядок команд.<br>
 * Порядок влияет на определение подходящей команды.
 *
 * @author smnadya
 * @since 2025.04.26
 */
public class CommandOrder {

    /**
     * Порядковый номер конкретной команды.
     */
    public static final int CO_CONCRETE = HIGHEST_PRECEDENCE + 100;

    /**
     * Приоритетный номер для конкретной подкоманды.
     * <br>
     * Определяет порядок, в котором подкоманда оценивается
     * по сравнению с другими командами, непосредственно основываясь на {@link #CO_CONCRETE}.
     */
    public static final int CO_CONCRETE_SUBCOMAND = CO_CONCRETE + 50;

    /**
     * Порядковый номер общей команды, которая может обработать любое сообщение.
     */
    public static final int CO_COMMON = HIGHEST_PRECEDENCE + 200;

}
