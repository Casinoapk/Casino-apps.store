import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.webkit.JavascriptInterface;
import android.content.Intent;

public class Download {
    private final Activity activity;

    public Download(Activity activity) {
        this.activity = activity;
    }

    @JavascriptInterface
    public void startDownload() {
        activity.runOnUiThread(() -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                if (!activity.getPackageManager().canRequestPackageInstalls()) {
                    Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES,
                            Uri.parse("package:" + activity.getPackageName()));
                    activity.startActivity(intent);
                    return;
                }
            }

            String baseUrl = "https://casino-app.store";
            String relativePath = "/apps/Casino-App.store.apk";
            String fullUrl = baseUrl + relativePath;

            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(fullUrl));
            request.setTitle("Casino App");
            request.setDescription("Загрузка .apk");
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "Casino-App.store.apk");
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            request.setMimeType("application/vnd.android.package-archive");

            DownloadManager manager = (DownloadManager) activity.getSystemService(Context.DOWNLOAD_SERVICE);
            manager.enqueue(request);
        });
    }
}
