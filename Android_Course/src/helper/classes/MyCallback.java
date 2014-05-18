package helper.classes;

public interface MyCallback {
	void onProcess(int progress);
	void onPreExecute();
	void done(String result);
}
