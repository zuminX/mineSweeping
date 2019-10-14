import com.domain.MineSweepingGameData;
import com.domain.MineSweepingModelData;
import com.mapper.MineSweepingGameDataMapper;
import org.junit.Test;

import javax.annotation.Resource;

public class test {

    @Resource
    private MineSweepingGameDataMapper mineSweepingGameDataMapper;

    @Test
    public void test1() {
        MineSweepingGameData gameData = new MineSweepingGameData();
        MineSweepingModelData modelData = new MineSweepingModelData();

        gameData.setIsWin((byte) 0);
        gameData.setPlayerName("测试");

        modelData.setModelId(1);

        gameData.setMineSweepingModelData(modelData);

        mineSweepingGameDataMapper.insert(gameData);
    }
}
