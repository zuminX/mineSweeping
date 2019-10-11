package com.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 存放迷宫提示信息的类
 */
@Component
@PropertySource(value = "classpath:properties/information.properties", encoding = "utf-8")
public class Information {
    public static String loadGameModelError;
    public static String loadGameBasicSettingError;
    public static String changeCustomizeDataError;
    public static String playerNameNotNull;
    public static String loadPlayerDataSettingError;
    public static String loadGameDataError;
    public static String playerDataError;

    @Value("${information.loadGameModelError}")
    public void setLoadGameModelError(String loadGameModelError) {
        Information.loadGameModelError = loadGameModelError;
    }

    @Value("${information.loadGameBasicSettingError}")
    public void setLoadGameBasicSettingError(String loadGameBasicSettingError) {
        Information.loadGameBasicSettingError = loadGameBasicSettingError;
    }

    @Value("${information.changeCustomizeDataError}")
    public void setChangeCustomizeDataError(String changeCustomizeDataError) {
        Information.changeCustomizeDataError = changeCustomizeDataError;
    }

    @Value("${information.playerNameNotNull}")
    public void setPlayerNameNotNull(String playerNameNotNull) {
        Information.playerNameNotNull = playerNameNotNull;
    }

    @Value("${information.loadPlayerDataSettingError}")
    public void setLoadPlayerDataSettingError(String loadPlayerDataSettingError) {
        Information.loadPlayerDataSettingError = loadPlayerDataSettingError;
    }

    @Value("${information.loadGameDataError}")
    public void setLoadGameDataError(String loadGameDataError) {
        Information.loadGameDataError = loadGameDataError;
    }

    @Value("${information.playerDataError}")
    public void setPlayerDataError(String playerDataError) {
        Information.playerDataError = playerDataError;
    }
}
