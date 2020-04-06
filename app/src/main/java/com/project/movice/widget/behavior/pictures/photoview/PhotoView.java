package com.project.movice.widget.behavior.pictures.photoview;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;


@TargetApi(9)
public class PhotoView extends ImageView {

	private final PhotoViewAttacher mAttacher;

	public PhotoView(Context context) {
		super(context);
		super.setScaleType(ScaleType.MATRIX);
		mAttacher = new PhotoViewAttacher(this);
	}

	public PhotoView(Context context, AttributeSet attr) {
		super(context, attr);
		super.setScaleType(ScaleType.MATRIX);
		mAttacher = new PhotoViewAttacher(this);
	}

	/**
	 * Returns true if the PhotoView is uangpro157 to allow zooming of Photos.
	 * 
	 * @return true if the PhotoView allows zooming.
	 */
	public boolean canZoom() {
		return mAttacher.canZoom();
	}

	/**
	 * Gets the Display Rectangle of the currently displayed Drawable. The
	 * Rectangle is relative to this View and includes all scaling and
	 * translations.
	 * 
	 * @return - RectF of Displayed Drawable
	 */
	public RectF getDisplayRect() {
		return mAttacher.getDisplayRect();
	}

	/**
	 * Returns the current scale value
	 * 
	 * @return float - current scale value
	 */
	public float getScale() {
		return mAttacher.getScale();
	}
	
	@Override
	public ScaleType getScaleType() {
		return mAttacher.getScaleType();
	}

	@Override
	public void setImageDrawable(Drawable drawable) {
		super.setImageDrawable(drawable);
		if(mAttacher != null) {
			mAttacher.update();
		}
	}

	/**
	 * Register a callback to be invoked when the Matrix has changed for this
	 * View. An example would be the user panning or scaling the Photo.
	 * 
	 * @param listener
	 *            - Listener to be registered.
	 */
	public void setOnMatrixChangeListener(PhotoViewAttacher.OnMatrixChangedListener listener) {
		mAttacher.setOnMatrixChangeListener(listener);
	}

	/**
	 * Register a callback to be invoked when the Photo displayed by this View
	 * is tapped with a single tap.
	 * 
	 * @param listener
	 *            - Listener to be registered.
	 */
	public void setOnPhotoTapListener(PhotoViewAttacher.OnPhotoTapListener listener) {
		mAttacher.setOnPhotoTapListener(listener);
	}
	
	@Override
	public void setScaleType(ScaleType scaleType) {
		mAttacher.setScaleType(scaleType);
	}

	/**
	 * Allows you to enable/disable the zoom functionality on the ImageView.
	 * When disable the ImageView reverts to using the FIT_CENTER matrix.
	 * 
	 * @param zoomable
	 *            - Whether the zoom functionality is enabled.
	 */
	public void setZoomable(boolean zoomable) {
		mAttacher.setZoomable(zoomable);
	}

	/**
	 * Zooms to the specified scale, around the focal point given.
	 * 
	 * @param scale
	 *            - Scale to zoom to
	 * @param focalX
	 *            - X Focus Point
	 * @param focalY
	 *            - Y Focus Point
	 */
	public void zoomTo(float scale, float focalX, float focalY) {
		mAttacher.zoomTo(scale, focalX, focalY);
	}

}