package com.rae.cnblogs.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.rae.cnblogs.R;
import com.rae.cnblogs.adapter.BlogCommentItemAdapter;
import com.rae.cnblogs.presenter.CnblogsPresenterFactory;
import com.rae.cnblogs.presenter.IBlogCommentPresenter;
import com.rae.cnblogs.sdk.bean.Blog;
import com.rae.cnblogs.sdk.bean.BlogComment;
import com.rae.cnblogs.widget.PlaceholderView;
import com.rae.cnblogs.widget.RaeRecyclerView;

import java.util.List;

import butterknife.BindView;

/**
 * 评论
 * Created by ChenRui on 2016/12/15 0015 19:22.
 */
public class BlogCommentFragment extends BaseFragment implements IBlogCommentPresenter.IBlogCommentView {

    public static BlogCommentFragment newInstance(Blog blog) {

        Bundle args = new Bundle();
        args.putParcelable("blog", blog);
        BlogCommentFragment fragment = new BlogCommentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @BindView(R.id.rec_blog_comment_list)
    RaeRecyclerView mRecyclerView;

    @BindView(R.id.placeholder)
    PlaceholderView mPlaceholderView;

    private BlogCommentItemAdapter mItemAdapter;
    private IBlogCommentPresenter mCommentPresenter;

    private Blog mBlog;
//    private SwipeDownScrollerCompat mSwipeDownScrollerCompat;

    @Override
    protected int getLayoutId() {
        return R.layout.fm_blog_comment;
    }

//    @Override
//    protected int getDialogThemeId() {
//        return R.style.SlideDialog_Comment;
//    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCommentPresenter = CnblogsPresenterFactory.getBlogCommentPresenter(getContext(), this);
        if (getArguments() != null)
            mBlog = getArguments().getParcelable("blog");
    }

//    @Override
//    protected void onDialogViewCreate(View view) {
//        initView();
//    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        mItemAdapter = new BlogCommentItemAdapter();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setPullRefreshEnabled(false);
        mRecyclerView.setLoadingMoreEnabled(true);
        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoadMore() {
                mCommentPresenter.loadMore();
            }
        });

//        final View alphaView = getActivity().findViewById(R.id.view_alpha);

        mRecyclerView.setAdapter(mItemAdapter);
//        mSwipeDownScrollerCompat = new SwipeDownScrollerCompat((View) mRecyclerView.getParent().getParent(), new SwipeDownScrollerCompat.SwipeDownScrollerHandler() {
//            @Override
//            public boolean canSwipe() {
//                return mRecyclerView.isOnTop();
//            }
//
//            @Override
//            public void onComputeScrollOffset(int offset, float p) {
//                if (Float.isNaN(p) || Float.isInfinite(p)) {
//                    return;
//                }
//                float alpha = 1 - p;
//                if (alpha < 0.5) alpha = 0.2f;
//                alphaView.setAlpha(alpha);
//                Log.w("Rae", "onComputeScrollOffset, offset = " + offset + "; p = " + p);
//            }
//        });
//        mRecyclerView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                return mSwipeDownScrollerCompat.onTouchEvent(motionEvent);
//            }
//        });
    }

    @Override
    public void onStart() {
        super.onStart();
        mCommentPresenter.start();
    }

    @Override
    public void onLoadCommentSuccess(List<BlogComment> data) {
        mPlaceholderView.dismiss();
        mItemAdapter.invalidate(data);
        mItemAdapter.notifyDataSetChanged();
    }

    @Override
    public Blog getBlog() {
        return mBlog;
    }

    @Override
    public void onLoadCommentEmpty() {
        mPlaceholderView.empty();
    }

    @Override
    public void onLoadMoreCommentEmpty() {
        mRecyclerView.loadMoreComplete();
        mRecyclerView.setNoMore(true);
    }
//
//    public void show(FragmentManager fm) {
//        show(fm, "blog_comment");
//    }
}
