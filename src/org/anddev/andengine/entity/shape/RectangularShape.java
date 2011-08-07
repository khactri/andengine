package org.anddev.andengine.entity.shape;

import org.anddev.andengine.collision.RectangularShapeCollisionChecker;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.opengl.Mesh;
import org.anddev.andengine.opengl.shader.ShaderProgram;

/**
 * (c) 2010 Nicolas Gramlich
 * (c) 2011 Zynga Inc.
 * 
 * @author Nicolas Gramlich
 * @since 11:37:50 - 04.04.2010
 */
public abstract class RectangularShape extends Shape {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	protected float mBaseWidth;
	protected float mBaseHeight;

	protected float mWidth;
	protected float mHeight;

	// ===========================================================
	// Constructors
	// ===========================================================

	public RectangularShape(final float pX, final float pY, final float pWidth, final float pHeight, final Mesh pMesh, final ShaderProgram pShaderProgram) {
		super(pX, pY, pMesh, pShaderProgram);

		this.mBaseWidth = pWidth;
		this.mBaseHeight = pHeight;

		this.mWidth = pWidth;
		this.mHeight = pHeight;

		this.mRotationCenterX = pWidth * 0.5f;
		this.mRotationCenterY = pHeight * 0.5f;

		this.mScaleCenterX = this.mRotationCenterX;
		this.mScaleCenterY = this.mRotationCenterY;
		
		this.onUpdateVertices();
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	@Override
	public float getWidth() {
		return this.mWidth;
	}

	@Override
	public float getHeight() {
		return this.mHeight;
	}

	@Override
	public float getBaseWidth() {
		return this.mBaseWidth;
	}

	@Override
	public float getBaseHeight() {
		return this.mBaseHeight;
	}

	public void setWidth(final float pWidth) {
		this.mWidth = pWidth;
		this.onUpdateVertices();
	}

	public void setHeight(final float pHeight) {
		this.mHeight = pHeight;
		this.onUpdateVertices();
	}

	public void setSize(final float pWidth, final float pHeight) {
		this.mWidth = pWidth;
		this.mHeight = pHeight;
		this.onUpdateVertices();
	}

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	public void setBaseSize() {
		if(this.mWidth != this.mBaseWidth || this.mHeight != this.mBaseHeight) {
			this.mWidth = this.mBaseWidth;
			this.mHeight = this.mBaseHeight;
			this.onUpdateVertices();
		}
	}

	@Override
	protected boolean isCulled(final Camera pCamera) { // TODO Advanced culling!
		final float x = this.mX;
		final float y = this.mY;
		return x > pCamera.getXMax()
				|| y > pCamera.getYMax()
				|| x + this.getWidth() < pCamera.getXMin()
				|| y + this.getHeight() < pCamera.getYMin();
	}

	@Override
	public void reset() {
		super.reset();
		this.setBaseSize();

		final float baseWidth = this.getBaseWidth();
		final float baseHeight = this.getBaseHeight();

		this.mRotationCenterX = baseWidth * 0.5f;
		this.mRotationCenterY = baseHeight * 0.5f;

		this.mScaleCenterX = this.mRotationCenterX;
		this.mScaleCenterY = this.mRotationCenterY;
	}

	@Override
	public boolean contains(final float pX, final float pY) {
		return RectangularShapeCollisionChecker.checkContains(this, pX, pY);
	}

	@Override
	public float[] getSceneCenterCoordinates() {
		return this.convertLocalToSceneCoordinates(this.mWidth * 0.5f, this.mHeight * 0.5f);
	}

	@Override
	public boolean collidesWith(final IShape pOtherShape) {
		if(pOtherShape instanceof RectangularShape) {
			final RectangularShape pOtherRectangularShape = (RectangularShape) pOtherShape;
			return RectangularShapeCollisionChecker.checkCollision(this, pOtherRectangularShape);
//		} else if(pOtherShape instanceof Line) {
//			final Line line = (Line) pOtherShape;
//			return RectangularShapeCollisionChecker.checkCollision(this, line); // TODO
		} else {
			return false;
		}
	}

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
