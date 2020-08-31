package abdulg.widget.salahny.Misc;

import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

/**
 * Created by Abdullah on 09/01/2018.
 */

public class Animations {

    public static Animation FadeIn(){
        final Animation in = new AlphaAnimation(0.0f, 1.0f);
        in.setDuration(3000);
        return in;
    }

    public static Animation FadeOut(){
        final Animation out = new AlphaAnimation(1.0f, 0.0f);
        out.setDuration(3000);
        return out;
    }
}
