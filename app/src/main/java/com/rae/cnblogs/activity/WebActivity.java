package com.rae.cnblogs.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.rae.cnblogs.R;
import com.rae.cnblogs.dialog.impl.ShareDialog;
import com.rae.cnblogs.fragment.WebViewFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 网页
 * Created by ChenRui on 2017/1/25 0025 9:32.
 */
public class WebActivity extends SwipeBackBaseActivity {

    @BindView(R.id.tool_bar)
    Toolbar mToolbar;

    @BindView(R.id.tv_web_title)
    TextView mTitleView;

    private ShareDialog mShareDialog;

    private WebViewFragment mWebViewFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent().getData() == null) {
            finish();
        }
        setContentView(R.layout.activity_web);
        showHomeAsUp(mToolbar);
        String url = getIntent().getData().toString();
        mShareDialog = new ShareDialog(this);
        mShareDialog.setOnShareClickListener(new ShareDialog.OnShareClickListener() {
            @Override
            public void onShare(ShareDialog dialog) {
                dialog.setShareTitle(getTitle().toString());
                dialog.setShareUrl(mWebViewFragment.getUrl());
            }
        });
        mShareDialog.setExtLayoutVisibility(View.GONE);
        mWebViewFragment = WebViewFragment.newInstance(url);
        getSupportFragmentManager().beginTransaction().add(R.id.fl_content, mWebViewFragment).commit();
    }

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
        mTitleView.setText(title);
    }

    @Override
    protected int getHomeAsUpIndicator() {
        return R.drawable.ic_close;
    }

    @OnClick(R.id.img_action_bar_more)
    public void onActionMenuMoreClick() {
        mShareDialog.show();
    }
}