package se.miun.chhe1903.dt031g.dialer;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Paths;


public class DownloadActivity extends AppCompatActivity {
    private WebView webView;
    private String fileName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        webView = findViewById(R.id.download_web_view);
        webView.setWebViewClient( new webClient());
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setJavaScriptEnabled(true);
        String url = getResources().getString(R.string.download_voices_URL);
        webView.loadUrl(url);
        webView.setDownloadListener((fileUrl, userAgent, contentDisposition, mimetype, contentLength) -> {
            fileName = fileUrl.substring(fileUrl.lastIndexOf("s/") + 2);
            new DownloadASyncTask().execute(fileUrl);
        });
    }

    public class webClient extends WebViewClient {
        public boolean  shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }
    }

    // AsyncTask
    private class DownloadASyncTask extends AsyncTask<String, Integer, String>{
        private ProgressBar downloadProgressBar;
        private ViewGroup progressBarViewGroup;
        private TextView downloadTitle;


        @Override
        protected void onPreExecute() {
            downloadProgressBar = findViewById(R.id.download_voice_file_progress);
            downloadProgressBar.getProgressDrawable().setColorFilter(
                    Color.GREEN, android.graphics.PorterDuff.Mode.SRC_IN);
            progressBarViewGroup = findViewById(R.id.voice_file_download);
            downloadTitle = findViewById(R.id.download_voice_title);
            downloadTitle.setText(getResources().getString(R.string.download_voice_title) + " " + fileName);
            animateVisibility(progressBarViewGroup, View.VISIBLE);
        }
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected String doInBackground(String... _url) {
            try {
                URL url = new URL(_url[0]);
                URLConnection connection = url.openConnection();
                connection.connect();
                InputStream response = connection.getInputStream();
                File voiceFile =  new File(Util.getInternalStorageDir(getApplicationContext()), fileName);
                try (FileOutputStream outputStream = new FileOutputStream(voiceFile, false)) {
                    int read;
                    long total = 0;
                    byte[] bytes = new byte[8192];
                    while ((read = response.read(bytes)) != -1) {
                        total += read;
                        publishProgress((int) total * 100 / connection.getContentLength());
                        Thread.sleep(200); // I suspend the thread for 200ms to actually show the progressbar
                        outputStream.write(bytes, 0, read);
                    }
                }
                String path = Util.getInternalStorageDir(getApplicationContext()) + "/voices";
                Util.unzip(voiceFile, new File(path));
            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            downloadProgressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            animateVisibility(progressBarViewGroup, View.GONE);
            Toast.makeText(getApplicationContext(), fileName + " was downloaded", Toast.LENGTH_SHORT).show();
        }
    }

    private void animateVisibility(View view, int visibility) {
        ViewGroup parent = (ViewGroup) view.getParent();

        Transition transition = new Slide(Gravity.BOTTOM);
        transition.setDuration(600);
        transition.addTarget(view);

        TransitionManager.beginDelayedTransition(parent, transition);
        view.setVisibility(visibility);
    }
}