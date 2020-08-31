package abdulg.widget.salahny.Network;

public interface NetworkCallback<T> {
	void onResponse(T callbackValue, Exception exception);
}
