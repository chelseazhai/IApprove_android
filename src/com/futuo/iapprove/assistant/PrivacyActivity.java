package com.futuo.iapprove.assistant;

import android.os.Bundle;
import android.webkit.WebView;

import com.futuo.iapprove.R;
import com.futuo.iapprove.customwidget.IApproveNavigationActivity;
import com.futuo.iapprove.utils.AppDataSaveRestoreUtils;

public class PrivacyActivity extends IApproveNavigationActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// set content view
		setContentView(R.layout.privacy_activity_layout);

		// set subViews
		// set title
		setTitle(R.string.privacy_nav_title);

		// get privacy webView
		WebView _privacyWebView = (WebView) findViewById(R.id.termsAndPrivacy_webView);

		// load terms and privacy url
		// test by ares
		_privacyWebView.loadUrl("http://sync.walkwork.net");
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		AppDataSaveRestoreUtils.onRestoreInstanceState(savedInstanceState);

		super.onRestoreInstanceState(savedInstanceState);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		AppDataSaveRestoreUtils.onSaveInstanceState(outState);

		super.onSaveInstanceState(outState);
	}

}
