package sebastiancorradi.expandabletextview;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

public class ExpandableTextView extends android.support.v7.widget.AppCompatTextView {

    private int collapsedLines;
    private int expandedLines;
    private int collapsedHeight;
    private int expandedHeight;
    private boolean collapsed = true;
    private int speed = 5;
    private boolean inited = false;

    private Canvas textCanvas;

    public ExpandableTextView(Context context) {
        super(context);
    }

    public ExpandableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inited = false;
    }

    public ExpandableTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inited = false;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (!inited) {
            setCollapsedLines(1);
            setMeasuredDimension(widthMeasureSpec, collapsedHeight);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        setExpandedLines(getLineCount());
        init();
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
    }

    public void init(){
        inited = true;
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                updateState();
            }
        });
    }
    private void updateState(){
        if (collapsed){
            expand();
        } else {
            collapse();
        }
    }

    private void expand(){
        collapsed = false;
        invalidate();
    }

    private void collapse(){
        collapsed = true;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if ((collapsed == false) && (getHeight() < expandedHeight)){
            setHeight(getHeight() + getSpeed() > expandedHeight ? expandedHeight : getHeight() + getSpeed());
            invalidate();
        }
        if ((collapsed) && (getHeight() > collapsedHeight)){
            setHeight(getHeight() - getSpeed() < collapsedHeight ? collapsedHeight : getHeight() - getSpeed());
            invalidate();
        }
    }

    public void setCollapsedLines(int collapsedLines) {
        this.collapsedLines = collapsedLines;
        collapsedHeight = (int) Math.ceil(collapsedLines * (getLineHeight() + getLineSpacingExtra()) + getPaddingBottom()  + getPaddingTop() +  getLastBaselineToBottomHeight());
    }

    private void setExpandedLines(int expandedLines) {
        this.expandedLines = expandedLines;
        expandedHeight = (int) Math.ceil(expandedLines * (getLineHeight() + getLineSpacingExtra()) + getPaddingBottom()  + getPaddingTop() + getLastBaselineToBottomHeight());
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getSpeed() {
        return speed;
    }
}
