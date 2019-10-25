package com.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 存放扫雷游戏信息的类
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
    public static String saveDataSucceed;
    public static String showNowTimeError;
    public static String helpGame1;
    public static String helpGame2;
    public static String helpGame3;
    public static String helpGame4;
    public static String helpSet1;
    public static String helpSet2;
    public static String helpSet3;
    public static String helpSet4;
    public static String titleWin;
    public static String titleWinAndBreakRecord;
    public static String titleFail;

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

    @Value("${help.game1}")
    public void setHelpGame1(String helpGame1) {
        Information.helpGame1 = helpGame1;
    }

    @Value("${help.game2}")
    public void setHelpGame2(String helpGame2) {
        Information.helpGame2 = helpGame2;
    }

    @Value("${help.game3}")
    public void setHelpGame3(String helpGame3) {
        Information.helpGame3 = helpGame3;
    }

    @Value("${help.game4}")
    public void setHelpGame4(String helpGame4) {
        Information.helpGame4 = helpGame4;
    }

    @Value("${help.set1}")
    public void setHelpSet1(String helpSet1) {
        Information.helpSet1 = helpSet1;
    }

    @Value("${help.set2}")
    public void setHelpSet2(String helpSet2) {
        Information.helpSet2 = helpSet2;
    }

    @Value("${help.set3}")
    public void setHelpSet3(String helpSet3) {
        Information.helpSet3 = helpSet3;
    }

    @Value("${help.set4}")
    public void setHelpSet4(String helpSet4) {
        Information.helpSet4 = helpSet4;
    }

    @Value("${title.win}")
    public void setTitleWin(String titleWin) {
        Information.titleWin = titleWin;
    }

    @Value("${title.winAndBreakRecord}")
    public void setTitleWinAndBreakRecord(String titleWinAndBreakRecord) {
        Information.titleWinAndBreakRecord = titleWinAndBreakRecord;
    }

    @Value("${title.fail}")
    public void setTitleFail(String titleFail) {
        Information.titleFail = titleFail;
    }

    @Value("${information.saveDataSucceed}")
    public void setSaveDataSucceed(String saveDataSucceed) {
        Information.saveDataSucceed = saveDataSucceed;
    }

    @Value("${information.showNowTimeError}")
    public void setShowNowTimeError(String showNowTimeError) {
        Information.showNowTimeError = showNowTimeError;
    }
}
