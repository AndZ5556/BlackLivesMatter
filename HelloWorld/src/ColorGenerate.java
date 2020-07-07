import java.awt.*;

public class ColorGenerate {
    static int colorCode;
    static Color [] colors = {Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.BLUE,
            new Color(58,24,177), new Color(146,110,174)};
    static Color getColor(){
        if(colorCode == 6){
            colorCode = 0;
        }
        else{
            colorCode++;
        }
        return colors[colorCode];
    }
}
