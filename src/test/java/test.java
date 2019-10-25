import com.domain.MineJButton;
import com.utils.ComponentImage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "applicationContext.xml")
public class test {

    @Test
    public void test1() {
        MineJButton[][] buttons = new MineJButton[10][];
        for (MineJButton[] button : buttons) {
            button = new MineJButton[10];
            for (MineJButton mineJButton : button) {
                mineJButton = new MineJButton();
                mineJButton.setSize(50, 50);
            }
        }
        Arrays.stream(buttons)
                .parallel()
                .forEach(mineJButtons -> Arrays.stream(mineJButtons)
                        .forEach(mineJButton -> mineJButton.setIcon(
                                ComponentImage.getGameImageIcon(ComponentImage.hideSpaceMineBufferedImage, mineJButton))));
    }
}
