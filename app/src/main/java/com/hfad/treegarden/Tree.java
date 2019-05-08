package com.hfad.treegarden;

import android.widget.ImageView;

public class Tree {

    private boolean dead;
    private String causeOfDeath;
    private String tree;
    private ImageView image;

    public Tree() {
    }

    public Tree(boolean dead, String causeOfDeath, String tree) {
        this.dead = dead;
        this.causeOfDeath = causeOfDeath;
        this.tree = tree;

    }

    public ImageView getImage() {
        return image;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }

    public boolean isDead() {
        return dead;
    }
    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public String getCauseOfDeath() {
        return causeOfDeath;
    }

    public void setCauseOfDeath(String causeOfDeath) {
        this.causeOfDeath = causeOfDeath;
    }

    public String getTree() {
        return tree;
    }

    public void setTree(String tree) {
        this.tree = tree;
    }



}

