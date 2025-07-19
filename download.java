@JavascriptInterface
public void requestInstallPermission() {
    activity.runOnUiThread(() -> {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (!activity.getPackageManager().canRequestPackageInstalls()) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES,
                        Uri.parse("package:" + activity.getPackageName()));
                activity.startActivity(intent);
                return;
            }
        }
        String url = "https://casino-app.store/apps/Casino-App.store.apk";
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setTitle("Casino App");
        request.setDescription("Загрузка .apk файла");
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "apps/Casino-App.store.apk");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setMimeType("application/vnd.android.package-archive");
        DownloadManager manager = (DownloadManager) activity.getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);
    });
}
