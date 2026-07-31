package org.catrobat.catroid.content.actions;

import android.util.Log;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;

import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.bricks.ParticleEffectBrick;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;
import org.catrobat.catroid.stage.ParticleEffectActor;
import org.catrobat.catroid.stage.StageActivity;

import java.util.ArrayList;
import java.util.List;

public class ParticleEffectAction extends TemporalAction {

    private ParticleEffectBrick brick;
    private Scope scope;

    @Override
    protected void begin() {
        try {
            // interpret formulas
            int posX = brick.getX().interpretInteger(scope);
            int posY = brick.getY().interpretInteger(scope);
            int count = brick.getParticleCount().interpretInteger(scope);
            float duration = (float) brick.getDuration().interpretDouble(scope);
            float scale = (float) brick.getSizeScale().interpretDouble(scope);

            // textures: split CSV
            String csv = brick.getTextureNamesCsv();
            List<String> texturePaths = new ArrayList<>();
            if (csv != null && !csv.trim().isEmpty()) {
                String[] parts = csv.split(",");
                for (String p : parts) {
                    String s = p.trim();
                    if (!s.isEmpty()) texturePaths.add(s);
                }
            }

            List<com.badlogic.gdx.graphics.Texture> textures = new ArrayList<>();
            // try to load textures via file paths; fallback to null (actor will handle)
            for (String path : texturePaths) {
                try {
                    com.badlogic.gdx.graphics.Texture t = new com.badlogic.gdx.graphics.Texture(com.badlogic.gdx.Gdx.files.absolute(path));
                    textures.add(t);
                } catch (Exception e) {
                    Log.d("ParticleEffectAction", "Failed to load texture: " + path + " -> " + e.getMessage());
                }
            }

            ParticleEffectActor actor;
            if (textures.isEmpty()) {
                actor = new ParticleEffectActor(posX, posY, Math.max(1, count), duration, scale, 1f, 1f, 1f, 1f);
            } else {
                actor = new ParticleEffectActor(posX, posY, Math.max(1, count), duration, scale, textures);
            }

            if (StageActivity.stageListener != null) {
                StageActivity.stageListener.addActor(actor);
            }

        } catch (InterpretationException e) {
            Log.e("ParticleEffectAction", Log.getStackTraceString(e));
        }
    }

    public void setBrick(ParticleEffectBrick brick) {
        this.brick = brick;
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }
}
