package com.dao;

import com.domain.MineModel;

import java.io.IOException;

public interface MineDao {
    MineModel findCustomizeModelByData(String rowStr, String columnStr, String mineNumberStr) throws IOException;
    void updateCustomizeModelInProperties(MineModel mineModel) throws IOException;

    void changeGameName(String name) throws IOException;

    String findNowGameName() throws IOException;

    boolean findNowOpenRecordStatus() throws IOException;

    void changeOpenRecordStatus(boolean isSelected) throws IOException;
}
