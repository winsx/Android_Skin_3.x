package com.v7lin.android.env.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.v7lin.android.env.EnvCallback;
import com.v7lin.android.env.EnvResBridge;

/**
 * @author v7lin E-mail:v7lin@qq.com
 */
@SuppressWarnings("deprecation")
public class CompatImageView extends ImageView implements XImageViewCall, EnvCallback<ImageView, XImageViewCall> {

	private EnvUIChanger<ImageView, XImageViewCall> mEnvUIChanger;

	public CompatImageView(Context context) {
		super(context);
	}

	public CompatImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CompatImageView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	public CompatImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
	}

	@Override
	public void setImageResource(int resId) {
		super.setImageResource(resId);

		applyAttrSrc(resId);
	}

	@Override
	public void setImageDrawable(Drawable drawable) {
		super.setImageDrawable(drawable);

		applyAttrSrc(0);
	}

	@Override
	public void setImageBitmap(Bitmap bm) {
		super.setImageBitmap(bm);

		applyAttrSrc(0);
	}

	private void applyAttrSrc(int resid) {
		applyAttr(android.R.attr.src, resid);
	}

	@Override
	public void setBackgroundColor(int color) {
		super.setBackgroundColor(color);

		applyAttrBackground(0);
	}

	@Override
	public void setBackgroundResource(int resid) {
		super.setBackgroundResource(resid);

		applyAttrBackground(resid);
	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	@Override
	public void setBackground(Drawable background) {
		super.setBackground(background);

		applyAttrBackground(0);
	}

	@Override
	public void setBackgroundDrawable(Drawable background) {
		super.setBackgroundDrawable(background);

		applyAttrBackground(0);
	}

	private void applyAttrBackground(int resid) {
		applyAttr(android.R.attr.background, resid);
	}

	private void applyAttr(int attr, int resid) {
		if (mEnvUIChanger != null) {
			mEnvUIChanger.applyAttr(this, this, attr, resid, isInEditMode());
		}
	}

	@Override
	public void scheduleImageDrawable(Drawable drawable) {
		super.setImageDrawable(drawable);
	}

	@Override
	public void scheduleForeground(Drawable foreground) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			super.setForeground(foreground);
		}
	}

	@Override
	public void scheduleBackgroundDrawable(Drawable background) {
		super.setBackgroundDrawable(background);
	}

	@Override
	public EnvUIChanger<ImageView, XImageViewCall> ensureEnvUIChanger(EnvResBridge bridge, boolean allowSysRes) {
		if (mEnvUIChanger == null) {
			mEnvUIChanger = new EnvImageViewChanger<ImageView, XImageViewCall>(getContext(), bridge, allowSysRes);
		}
		return mEnvUIChanger;
	}

	@Override
	public void scheduleSkin() {
		if (mEnvUIChanger != null) {
			mEnvUIChanger.scheduleSkin(this, this, isInEditMode());
		}
	}

	@Override
	public void scheduleFont() {
		if (mEnvUIChanger != null) {
			mEnvUIChanger.scheduleFont(this, this, isInEditMode());
		}
	}

	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		if (mEnvUIChanger != null) {
			mEnvUIChanger.scheduleSkin(this, this, isInEditMode());
			mEnvUIChanger.scheduleFont(this, this, isInEditMode());
		}
	}
}
