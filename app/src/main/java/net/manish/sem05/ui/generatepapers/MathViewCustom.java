package net.manish.sem05.ui.generatepapers;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;

import net.manish.sem05.R;
import com.x5.template.Chunk;
import com.x5.template.Theme;
import com.x5.template.providers.AndroidTemplates;

public class MathViewCustom extends WebView {
    private String mText;
    private String mConfig;
    private int mEngine;

   public MathViewCustom(Context context, AttributeSet attrs) {
        super(context, attrs);
        getSettings().setJavaScriptEnabled(true);
        getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        setBackgroundColor(Color.TRANSPARENT);

        TypedArray mTypeArray = context.getTheme().obtainStyledAttributes(
                attrs,
                io.github.kexanie.library.R.styleable.MathView,
                0, 0
        );

        try { // the order of execution of setEngine() and setText() matters
            setEngine(mTypeArray.getInteger(io.github.kexanie.library.R.styleable.MathView_engine, 0));
            setText(mTypeArray.getString(io.github.kexanie.library.R.styleable.MathView_text));
        } finally {
            mTypeArray.recycle();
        }
    }



    // to disable touch event on MathView
    @Override
    public boolean onTouchEvent(MotionEvent event) {
      //  requestDisallowInterceptTouchEvent(true);
        return super.onTouchEvent(event);
    }

    private Chunk getChunk() {
        String TEMPLATE_KATEX = "katex";
        String TEMPLATE_MATHJAX = "mathjax";
        String template = TEMPLATE_KATEX;
        AndroidTemplates loader = new AndroidTemplates(getContext());
        switch (mEngine) {
            case io.github.kexanie.library.MathView.Engine.KATEX: template = TEMPLATE_KATEX; break;
            case io.github.kexanie.library.MathView.Engine.MATHJAX: template = TEMPLATE_MATHJAX; break;
        }

        return new Theme(loader).makeChunk(template);
    }

    public void setText(String text) {
        mText = text;
        Chunk chunk = getChunk();

        String TAG_FORMULA = "formula";
        String TAG_CONFIG = "config";
        chunk.set(TAG_FORMULA, mText);
        chunk.set(TAG_CONFIG, mConfig);
        this.loadDataWithBaseURL(null, chunk.toString(), "text/html", "utf-8", "about:blank");
    }

    public String getText() {
        return mText;
    }

    /**
     * Tweak the configuration of MathJax.
     * The `config` string is a call statement for MathJax.Hub.Config().
     * For example, to enable auto line breaking, you can call:
     * config.("MathJax.Hub.Config({
     *      CommonHTML: { linebreaks: { automatic: true } },
     *      "HTML-CSS": { linebreaks: { automatic: true } },
     *      SVG: { linebreaks: { automatic: true } }
     *  });");
     *
     * This method should be call BEFORE setText() and AFTER setEngine().
     * PLEASE PAY ATTENTION THAT THIS METHOD IS FOR MATHJAX ONLY.
     * @param config
     */
    public void config(String config) {
        if (mEngine == io.github.kexanie.library.MathView.Engine.MATHJAX) {
            this.mConfig = config;
        }
    }

    /**
     * Set the js engine used for rendering the formulas.
     * @param engine must be one of the constants in class Engine
     *
     * This method should be call BEFORE setText().
     */
    public void setEngine(int engine) {
        switch (engine) {
            case io.github.kexanie.library.MathView.Engine.KATEX: {
                mEngine = io.github.kexanie.library.MathView.Engine.KATEX;
                break;
            }
            case io.github.kexanie.library.MathView.Engine.MATHJAX: {
                mEngine = io.github.kexanie.library.MathView.Engine.MATHJAX;
                break;
            }
            default: mEngine = io.github.kexanie.library.MathView.Engine.KATEX;
        }
    }

    public  class Engine {
        final public static int KATEX = 0;
        final public static int MATHJAX = 1;
    }
}
