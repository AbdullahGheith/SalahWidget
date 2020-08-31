package abdulg.widget.salahny.Network;

/**
 * Created by Abdullah on 10/01/2018.
 */

public abstract class APIResultListener {
    private String resultClassName;

    public abstract <T> T onResult(T result);

    public String getResultClassName(){
        return resultClassName;
    }

    public void setResultClassName(String resultClassName) {
        this.resultClassName = resultClassName;
    }
}
