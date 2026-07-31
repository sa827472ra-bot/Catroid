package org.catrobat.catroid.stage;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.content.UserVariable; // may not exist, kept for future use

/**
 * Lightweight AnimatedTextActor used by AnimatedTextAction/Brick.
 * Features: simple outline by drawing text multiple times with offset, color and alpha support,
 * and a basic typewriter support via setDisplayedCharacters.
 */
public class AnimatedTextActor extends Actor {
	private BitmapFont font;
	private String text = "";
	private GlyphLayout layout = new GlyphLayout();
	private int textColor = 0xFFFFFFFF;
	private int outlineColor = 0xFF000000;
	private float outlineWidth = 2f; // pixels
	private int displayedCharacters = -1; // -1 means full text

	public AnimatedTextActor(BitmapFont font) {
		this.font = font;
		setColor(Color.WHITE);
	}

	public void setText(String text) {
		this.text = text == null ? "" : text;
	}

	public void setTextColor(int argb) {
		this.textColor = argb;
	}

	public void setOutlineColor(int argb) {
		this.outlineColor = argb;
	}

	public void setOutlineWidth(float width) {
		this.outlineWidth = width;
	}

	public void setDisplayedCharacters(int count) {
		this.displayedCharacters = count;
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		if (font == null) {
			return;
		}

		String drawText = text;
		if (displayedCharacters >= 0 && displayedCharacters < drawText.length()) {
			drawText = drawText.substring(0, displayedCharacters);
		}

		layout.setText(font, drawText);
		float x = getX();
		float y = getY();

		// draw outline by drawing the text multiple times offset by outlineWidth
		int steps = 8;
		float half = outlineWidth / 2f;
		for (int i = 0; i < steps; i++) {
			float angle = (float) (i * (2 * Math.PI / steps));
			float ox = (float) Math.cos(angle) * outlineWidth;
			float oy = (float) Math.sin(angle) * outlineWidth;
			font.setColor(toColor(outlineColor, parentAlpha));
			font.draw(batch, drawText, x + ox, y + layout.height + oy);
		}

		// draw main text
		font.setColor(toColor(textColor, parentAlpha));
		font.draw(batch, drawText, x, y + layout.height);
	}

	private Color toColor(int argb, float parentAlpha) {
		float a = ((argb >> 24) & 0xFF) / 255f * parentAlpha;
		float r = ((argb >> 16) & 0xFF) / 255f;
		float g = ((argb >> 8) & 0xFF) / 255f;
		float b = (argb & 0xFF) / 255f;
		return new Color(r, g, b, a);
	}
}
