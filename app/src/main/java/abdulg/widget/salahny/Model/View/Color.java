package abdulg.widget.salahny.Model.View;

/**
 * Created by Abdullah Gheith on 15/05/2018.
 */
public class Color {
    private int r;
    private int g;
    private int b;
    private int a;

    public Color(int r, int g, int b, int a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    @Override
    public String toString() {
        return r + ";" + g + ";" + b + ";" + a;
    }

    public static Color fromString(String string){
        String[] split = string.split(";");
        int r = Integer.valueOf(split[0]);
        int g = Integer.valueOf(split[1]);
        int b = Integer.valueOf(split[2]);
        int a = Integer.valueOf(split[3]);
        Color color = new Color(r, g, b, a);
        return color;
    }

    public int getR() {
        return r;
    }

    public void setR(int r) {
        this.r = r;
    }

    public int getG() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public int toAndroidColor(){
        return android.graphics.Color.rgb(r, g, b);
    }

    public int toAndroidColorWithAlpha(){
        return android.graphics.Color.argb(a, r, g, b);
    }
}
