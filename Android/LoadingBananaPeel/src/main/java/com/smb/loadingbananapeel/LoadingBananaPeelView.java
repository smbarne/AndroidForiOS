package com.smb.loadingbananapeel;

import android.view.Gravity;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ViewFlipper;

/**
 * A class that extends Loading List with a third state for a banana peel.  The
 * banana peel may be a custom layout, or a card view with a given title and
 * drawable.
 *
 * @author Stephen Barnes
 */
public class LoadingBananaPeelView extends ViewFlipper {

    /**
     * Interface which listens to user input on the banana peel.
     */
    public interface BananaPeelActionListener {
        /**
         * Called when the banana peel is clicked.
         */
        public void onBananaPeelClick();
    }

    private int indexLoading, indexContentView, indexBananaPeel;
    private int bananaPeelContentViewLayoutResourceId, bananaPeelViewResourceId, bananaPeelDefaultMessageResourceId, bananaPeelDefaultImageResourceId;
    private boolean hadAttrs;

    private View contentView;
    public View getContentView() { return contentView; }
    public boolean isShowingContentView() { return getDisplayedChild() == indexContentView; }

    private View loadingView;
    public View getLoadingView() { return loadingView; }
    public boolean isShowingLoadingView() { return getDisplayedChild() == indexLoading; }

    private View bananaPeelView;
    public View getBananaPeelView() { return bananaPeelView; }
    public boolean isShowingBananaPeel() {
        return (getDisplayedChild() == indexBananaPeel);
    }

    public LoadingBananaPeelView(Context context) {
        super(context);
        init(context, null);
    }

    public LoadingBananaPeelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        hadAttrs = (attrs != null);
        if (attrs != null) {
            TypedArray styledAttributes = getContext().getApplicationContext().obtainStyledAttributes(attrs, R.styleable.LoadingBananaPeelView);

            // Load the resource IDs for each property from the styled attributes set
            bananaPeelContentViewLayoutResourceId = styledAttributes.getResourceId(R.styleable.LoadingBananaPeelView_bananaPeelContentViewLayoutResource, 0);
            bananaPeelViewResourceId = styledAttributes.getResourceId(R.styleable.LoadingBananaPeelView_bananaPeelViewResource, 0);
            bananaPeelDefaultMessageResourceId = styledAttributes.getResourceId(R.styleable.LoadingBananaPeelView_bananaPeelDefaultMessageStringResource, 0);
            bananaPeelDefaultImageResourceId = styledAttributes.getResourceId(R.styleable.LoadingBananaPeelView_bananaPeelDefaultImageResource, 0);

            // Set the default animations for changing between View Flipper children
            this.setAnimateFirstView(false);
            this.setInAnimation(AnimationUtils.loadAnimation(context, R.anim.abc_fade_in));
            this.setOutAnimation(AnimationUtils.loadAnimation(context, R.anim.abc_fade_out));

            styledAttributes.recycle();
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        boolean customContentViewLoaded = (contentView != null);

        if (!isInEditMode()) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            loadingView = inflater.inflate(R.layout.view_loading_view_progress_view, this, false);
        }

        if (!customContentViewLoaded) {
            // See if a custom content view has been specified, and if so, inflate this view and set the
            // contentView reference.
            if (setContentView(bananaPeelContentViewLayoutResourceId) == null) {
                setContentView(R.layout.view_loading_view_default_content_view);
            }
        }

        setLoadingView(loadingView);

        if (hadAttrs) {
            // See if a custom banana peel view has been specified, and if so, inflate this view and set the
            // contentView reference.
            if (bananaPeelViewResourceId != 0) {
                bananaPeelView = inflateAndAddResource(bananaPeelViewResourceId);
            } else {
                // If no custom banana peel view was specified, load the default banana peel view and set any
                // default values for the default banana peel that were specified in the attributes
                bananaPeelView = inflateAndAddResource(R.layout.view_default_banana_peel);

                setBananaPeelMessage(bananaPeelDefaultMessageResourceId);
                setBananaPeelImage(bananaPeelDefaultImageResourceId);
            }
        } else {
            // If no custom attributes are given, load the default banana peel view
            bananaPeelView = inflateAndAddResource(R.layout.view_default_banana_peel);
        }

        cacheViewIndices();
        showLoading();
    }

    /**
     * Update the cached view indices for all states of the Loading Banana Peel view flipper.
     */
    protected void cacheViewIndices() {
        indexLoading = indexOfChild(loadingView);
        indexContentView = indexOfChild(contentView);
        indexBananaPeel = indexOfChild(bananaPeelView);
    }

    /**
     * Inflate and set the content view based on a layout resource.
     * @param contentViewLayoutResourceId the content view's layout resource id.
     * @return the inflated content view.
     */
    public View setContentView(int contentViewLayoutResourceId) {
        if (contentViewLayoutResourceId != 0) {
            View contentView = LayoutInflater.from(getContext()).inflate(contentViewLayoutResourceId, this, false);
            return setContentView(contentView);
        }
        return null;
    }

    /**
     * Set the content view.
     * @param contentView the content view to set for the loading banana peel.
     * @return the set content view.
     */
    public View setContentView(View contentView) {
        if (this.contentView != null) {
            removeView(this.contentView);
        }

        this.contentView = contentView;

        addView(contentView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        cacheViewIndices();

        return this.contentView;
    }

    /**
     * Set the current loading view.
     * @param loadingView the inflated loading view.
     * @return the current loading view.
     */
    public View setLoadingView(View loadingView) {
        if (this.loadingView != null) {
            removeView(this.loadingView);
        }
        this.loadingView = loadingView;

        LayoutParams loadingParams = generateDefaultLayoutParams();
        loadingParams.gravity = Gravity.CENTER;
        loadingParams.width = LayoutParams.WRAP_CONTENT;
        loadingParams.height = LayoutParams.WRAP_CONTENT;

        if (!isInEditMode()) {
            addView(loadingView, loadingParams);
        }
        cacheViewIndices();

        return this.contentView;
    }

    /**
     * Set the current state of the view flipper to show the loading view.
     */
    public void showLoading() {
        this.post(new Runnable() {
            @Override
            public void run() {
                setDisplayedChild(indexLoading);
            }
        });
    }

    /**
     * Set the current state of the view flipper to show the content view.
     */
    public void showContent() {
        this.post(new Runnable() {
            @Override
            public void run() {
                setDisplayedChild(indexContentView);
            }
        });
    }

    /**
     * Set a custom layout to inflate for the banana peel.
     * @param layoutResourceId - The custom layout to inflate.
     */
    public void setBananaPeelLayout(int layoutResourceId) {
        inflateAndAddResource(layoutResourceId);
    }

    /**
     * Create a banana peel with with the given message and image drawable.
     * @param messageResourceId - The string resource id for the banana peel text.
     * @param imageDrawableResourceId - The id for the drawable to be displayed.
     * @param listener - (optional) An event listener for when the banana peel is clicked.
     */
    public void setBananaPeel(int messageResourceId, int imageDrawableResourceId, final BananaPeelActionListener listener) {
        configureBananaPeel(imageDrawableResourceId, listener);
        setBananaPeelMessage(messageResourceId);
    }

    /**
     * Create a banana peel with a card view with the given message and image drawable.
     * @param message - The string for the banana peel text.
     * @param imageDrawableResourceId - The id for the drawable to be displayed.
     * @param listener - (optional) An event listener for when the banana peel is clicked.
     */
    public void setBananaPeel(String message, int imageDrawableResourceId, final BananaPeelActionListener listener) {
        configureBananaPeel(imageDrawableResourceId, listener);
        setBananaPeelMessage(message);
    }

    /**
     * Configure the image and listener for a banana peel
     * @param imageDrawableResourceId - The id for the drawable to be displayed.
     * @param listener - (optional) An event listener for when the banana peel is clicked.
     */
    private void configureBananaPeel(int imageDrawableResourceId, final BananaPeelActionListener listener) {
        setBananaPeelListener(listener);
        setBananaPeelImage(imageDrawableResourceId);
    }

    /**
     * Set the click listener for the banana peel.
     * @param listener - An event listener for when the banana peel is clicked.
     */
    public void setBananaPeelListener(final BananaPeelActionListener listener) {
        // Setup the banana peel state
        bananaPeelView.setClickable(true);
        bananaPeelView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onBananaPeelClick();
                }
            }
        });
    }

    /**
     * Update the banana peel image drawable.  If 0 is passed in for the {@code imageDrawableResourceId}
     * then the image drawable will be set to visibility {@link View#GONE}.
     * @param imageDrawableResourceId - The id for the drawable to be displayed.
     */
    public void setBananaPeelImage(int imageDrawableResourceId) {
        if (bananaPeelView != null) {
            ImageView bananaImage = (ImageView) bananaPeelView.findViewById(R.id.bananaPeel_ImageView);
            if (imageDrawableResourceId != 0) {
                bananaImage.setVisibility(VISIBLE);
                bananaImage.setImageDrawable(getContext().getApplicationContext().getResources().getDrawable(imageDrawableResourceId));

                int bananaPadding = (int)getResources().getDimension(R.dimen.BananaPeel_DefaultMargin);
                bananaImage.setPadding(bananaPadding, bananaPadding, bananaPadding, bananaPadding);
            } else {
                bananaImage.setVisibility(GONE);
            }
        }
    }

    /**
     * Update the banana peel message text. If 0 is passed in for the {@code messageResourceId}
     * then the image message textview will be set to visibility {@link View#GONE}.
     * @param messageResourceId - The resource id for the banana peel text.
     */
    public void setBananaPeelMessage(int messageResourceId) {
        if (bananaPeelView != null) {
            TextView bananaTitle = (TextView) bananaPeelView.findViewById(R.id.bananaPeel_TextView);
            if (messageResourceId != 0) {
                bananaTitle.setVisibility(VISIBLE);
                bananaTitle.setText(messageResourceId);
            } else {
                bananaTitle.setVisibility(GONE);
            }
        }
    }

    /**
     * Update the banana peel message text.
     * @param message - The string for the banana peel text.
     */
    public void setBananaPeelMessage(String message) {
        if (bananaPeelView != null) {
            TextView bananaTitle = (TextView) bananaPeelView.findViewById(R.id.bananaPeel_TextView);
            bananaTitle.setVisibility(VISIBLE);
            bananaTitle.setText(message);
        }
    }

    /**
     * Inflate the given layout and add it to the view hierarchy.  Sets the member variable
     * for referencing the inflated banana peel view.
     *
     * @param layoutResourceId - The layout resource id.
     * @return the inflated View.
     */
    private View inflateAndAddResource(int layoutResourceId) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View bananaPeel = inflater.inflate(layoutResourceId, this, false);
        addView(bananaPeel);
        cacheViewIndices();
        return bananaPeel;
    }

    /**
     * Flip the viewflipper to the banana peel state
     */
    public void showBananaPeel() {
        this.post(new Runnable() {
              @Override
              public void run() {
                  setDisplayedChild(indexBananaPeel);
              }
         });
    }
}
