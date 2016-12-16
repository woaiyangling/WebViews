package com.wzkj.webview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private WebView webView;
    private Button button;
    private Button button2;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initWebView();


    }

    private void initWebView() {
        webView = (WebView) findViewById(R.id.webview);
        button = (Button) findViewById(R.id.button);
        button2 = (Button) findViewById(R.id.button2);
        textView = (TextView) findViewById(R.id.textView);
        button.setOnClickListener(this);
        button2.setOnClickListener(this);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setDownloadListener(new MyDownload());
        webView.loadUrl("http://shouji.baidu.com");

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onReceivedError(WebView view,
                                        WebResourceRequest request,
                                        WebResourceError error) {
                //用JSP写java和jsp html    用js来写
                //把网页放在本地
                // view.loadUrl("http://404.error");
                super.onReceivedError(view, request, error);
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                textView.setText(title);
                super.onReceivedTitle(view, title);
            }
        });

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                finish();
                break;
            case R.id.button2:
                webView.reload();
                break;
        }
    }

    class MyDownload implements DownloadListener {

        @Override
        public void onDownloadStart(String url, String s1, String s2, String s3, long l) {
            if (url.endsWith(".apk")) {
                //开始进行下载
                 new HttpDownload(url).start();

                //可以用系统webkit 封装下载
//                Uri uri = Uri.parse(url);
//                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//                startActivity(intent);
            }
        }
    }
}
